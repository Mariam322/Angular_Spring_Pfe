pipeline {
  agent {
    kubernetes {
      yaml """
apiVersion: v1
kind: Pod
metadata:
  labels:
    app: kaniko-pipeline
spec:
  serviceAccountName: default
  containers:

  # ========================= MAVEN =========================
  - name: maven
    image: maven:3.9.9-eclipse-temurin-17
    command: ["cat"]
    tty: true
    resources:
      requests:
        memory: "1Gi"
        cpu: "200m"
        ephemeral-storage: "4Gi"
      limits:
        memory: "2Gi"
        cpu: "400m"
        ephemeral-storage: "8Gi"

  # ========================= NODE =========================
  - name: node
    image: node:20
    command: ["cat"]
    tty: true
    resources:
      requests:
        memory: "1.5Gi"
        cpu: "300m"
        ephemeral-storage: "4Gi"
      limits:
        memory: "3Gi"
        cpu: "600m"
        ephemeral-storage: "8Gi"

  # ========================= KANIKO =========================
  - name: kaniko
    image: gcr.io/kaniko-project/executor:debug
    imagePullPolicy: Always
    command: ["sleep"]
    args: ["9999999"]
    volumeMounts:
    - name: docker-config
      mountPath: /kaniko/.docker
    resources:
      requests:
        memory: "1Gi"
        cpu: "300m"
        ephemeral-storage: "6Gi"
      limits:
        memory: "2Gi"
        cpu: "600m"
        ephemeral-storage: "12Gi"

  volumes:
  - name: docker-config
    projected:
      sources:
      - secret:
          name: regcred
          items:
          - key: .dockerconfigjson
            path: config.json
  restartPolicy: Never
"""
    }
  }

  environment {
    DOCKER_REGISTRY = 'docker.io/mariammseddi12'
    K8S_NAMESPACE = 'default'
    MAVEN_COMPILER_VERSION = '-Dmaven.compiler.plugin.version=3.11.0'
  }

  options {
    timeout(time: 60, unit: 'MINUTES')
    disableConcurrentBuilds()
  }

  stages {

    # ========================= STAGE 1 =========================
    stage('Checkout Code') {
      steps {
        deleteDir()
        git url: 'https://github.com/Mariam322/Angular_Spring_Pfe.git', branch: 'main'
      }
    }

    # ========================= STAGE 2 =========================
    stage('Build Angular Frontend') {
      steps {
        container('node') {
          dir('BankprojetFront') {
            sh '''
              echo "🧹 Cleaning temp and cache before build..."
              npm cache clean --force || true
              rm -rf /tmp/* || true

              npm config set legacy-peer-deps true
              npm install
              npm install @popperjs/core --save

              node -e "
                const fs = require('fs');
                const config = JSON.parse(fs.readFileSync('angular.json', 'utf8'));
                const project = Object.keys(config.projects)[0];
                if (config.projects[project]?.architect?.build?.configurations?.production?.budgets) {
                  delete config.projects[project].architect.build.configurations.production.budgets;
                }
                fs.writeFileSync('angular.json', JSON.stringify(config, null, 2));
              "

              echo "🚀 Building Angular app..."
              node --max-old-space-size=2048 ./node_modules/@angular/cli/bin/ng build --configuration=production --source-map=false
            '''
          }
        }
      }
    }

    # ========================= STAGE 3 =========================
    stage('Build Java JARs') {
      steps {
        container('maven') {
          sh '''
            echo "🚀 Building Java microservices..."
            mvn -B -f EurekaCompain/pom.xml clean package -DskipTests
            mvn -B -f Gatway/pom.xml clean package -DskipTests
            mvn -B -f ProjetCompain/pom.xml clean package -DskipTests
            mvn -B -f Facturation/pom.xml clean package -DskipTests
            mvn -B -f Depense/pom.xml clean package -DskipTests
            mvn -B -f BanqueService/pom.xml clean package -DskipTests
            mvn -B -f ReglementAffectation/pom.xml clean package -DskipTests
          '''
        }
      }
    }

    # ========================= STAGE 4 =========================
    stage('Build & Push Docker Images (Sequential)') {
      steps {
        container('kaniko') {
          script {
            def services = [
              [name: 'Eureka', path: 'EurekaCompain', image: 'eureka-server'],
              [name: 'Gateway', path: 'Gatway', image: 'gateway-service'],
              [name: 'Compain', path: 'ProjetCompain', image: 'compain-service'],
              [name: 'Facturation', path: 'Facturation', image: 'facturation-service'],
              [name: 'Depense', path: 'Depense', image: 'depense-service'],
              [name: 'Bank', path: 'BanqueService', image: 'bank-service'],
              [name: 'ReglementAffectation', path: 'ReglementAffectation', image: 'reglementaffectation-service'],
              [name: 'Angular', path: 'BankprojetFront', image: 'angular-frontend']
            ]

            for (svc in services) {
              echo "🚀 Building ${svc.name} image..."
              sh "rm -rf /kaniko/.cache_${svc.name} || true"
              sh """
                /kaniko/executor \
                  --context=dir:///home/jenkins/agent/workspace/Pipline_OVH/${svc.path} \
                  --dockerfile=/home/jenkins/agent/workspace/Pipline_OVH/${svc.path}/Dockerfile \
                  --destination=${DOCKER_REGISTRY}/${svc.image}:latest \
                  --skip-tls-verify \
                  --snapshot-mode=redo \
                  --cache=true \
                  --cleanup
              """
              echo "✅ ${svc.name} image built & pushed successfully."
              sleep 3
            }
          }
        }
      }
    }

    # ========================= STAGE 5 =========================
    stage('Deploy to OVH Kubernetes') {
      steps {
        script {
          echo "🚀 Starting deployment to OVH Kubernetes..."
          withKubeConfig([credentialsId: 'kubernetes-credentials-id']) {
            sh """
              kubectl create namespace ${K8S_NAMESPACE} --dry-run=client -o yaml | kubectl apply -f -
              kubectl apply -f kubernetes/ -n ${K8S_NAMESPACE}
            """
            def services = [
              'eureka-server','gateway-service','compain-service',
              'facturation-service','depense-service','bank-service',
              'reglementaffectation-service','angular-frontend'
            ]
            services.each { svc ->
              echo "⏳ Waiting for ${svc} rollout..."
              sh "kubectl rollout status deployment/${svc} -n ${K8S_NAMESPACE} --timeout=300s"
            }
          }
        }
      }
    }
  }

  # ========================= POST ACTIONS =========================
  post {
    success {
      echo '✅ Pipeline completed successfully (Build + Push + Deploy)'
    }
    failure {
      echo '❌ Pipeline failed — check Jenkins logs for details.'
    }
    always {
      echo '🧹 Cleaning workspace...'

    }
  }
}
