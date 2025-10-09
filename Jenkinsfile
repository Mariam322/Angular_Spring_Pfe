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
  - name: maven
    image: maven:3.9.9-eclipse-temurin-17
    command: ["cat"]
    tty: true
  - name: node
    image: node:20
    command: ["cat"]
    tty: true
  - name: kaniko
    image: gcr.io/kaniko-project/executor:debug
    imagePullPolicy: Always
    command: ["sleep"]
    args: ["9999999"]
    volumeMounts:
    - name: docker-config
      mountPath: /kaniko/.docker
    - name: kaniko-tmp
      mountPath: /tmp
  volumes:
  - name: docker-config
    projected:
      sources:
      - secret:
          name: regcred
          items:
          - key: .dockerconfigjson
            path: config.json
  - name: kaniko-tmp
    emptyDir:
      sizeLimit: 10Gi
  restartPolicy: Never
"""
    }
  }

  environment {
    DOCKER_REGISTRY = 'docker.io/mariammseddi12'
    K8S_NAMESPACE = 'default'
    MAVEN_COMPILER_VERSION = '-Dmaven.compiler.plugin.version=3.11.0'
  }

  stages {

    stage('Checkout Code') {
      steps {
        git url: 'https://github.com/Mariam322/Angular_Spring_Pfe.git', branch: 'main'
      }
    }

    stage('Build Angular Frontend') {
      steps {
        container('node') {
          dir('BankprojetFront') {
            sh '''
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
              npx ng build --configuration=production --source-map=false
            '''
          }
        }
      }
    }

    stage('Build Java JARs') {
      steps {
        container('maven') {
          sh '''
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

    stage('Build & Push Docker Images') {
      steps {
        container('kaniko') {
          script {
            def services = [
              [path: 'EurekaCompain', image: 'eureka-server'],
              [path: 'Gatway', image: 'gateway-service'],
              [path: 'ProjetCompain', image: 'compain-service'],
              [path: 'Facturation', image: 'facturation-service'],
              [path: 'Depense', image: 'depense-service'],
              [path: 'BanqueService', image: 'bank-service'],
              [path: 'ReglementAffectation', image: 'reglementaffectation-service'],
              [path: 'BankprojetFront', image: 'angular-frontend']
            ]

            for (svc in services) {
              echo "🚀 Building ${svc.image}..."
              sh """
                rm -rf /kaniko/.cache_${svc.image} || true
                /kaniko/executor \
                  --context=dir:///home/jenkins/agent/workspace/Pipline_OVH/${svc.path} \
                  --dockerfile=/home/jenkins/agent/workspace/Pipline_OVH/${svc.path}/Dockerfile \
                  --destination=${DOCKER_REGISTRY}/${svc.image}:latest \
                  --cache-dir=/kaniko/.cache_${svc.image} \
                  --skip-tls-verify \
                  --use-new-run \
                  --single-snapshot \
                  --reproducible
              """
            }
          }
        }
      }
    }

    stage('Deploy to OVH Kubernetes') {
      steps {
        script {
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
              sh "kubectl rollout status deployment/${svc} -n ${K8S_NAMESPACE} --timeout=300s"
            }
          }
        }
      }
    }
  }

  post {
    success {
      echo '✅ Pipeline completed successfully (Build + Push + Deploy)'
    }
    failure {
      echo '❌ Pipeline failed — check Jenkins logs for details.'
    }
  }
}
