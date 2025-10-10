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
    resources:
      requests:
        memory: "256Mi"
        cpu: "100m"
      limits:
        memory: "512Mi"
        cpu: "200m"

  - name: node
    image: node:20
    command: ["cat"]
    tty: true
    resources:
      requests:
        memory: "1Gi"
        cpu: "300m"
      limits:
        memory: "3Gi"
        cpu: "800m"

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
        memory: "2Gi"
        cpu: "600m"
        ephemeral-storage: "20Gi"
      limits:
        memory: "4Gi"
        cpu: "1000m"
        ephemeral-storage: "30Gi"

  - name: kubectl
    image: lachlanevenson/k8s-kubectl:v1.25.4
    command: ["cat"]
    tty: true
    resources:
      requests:
        memory: "256Mi"
        cpu: "100m"
      limits:
        memory: "512Mi"
        cpu: "200m"

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
  }

  options {
    timeout(time: 60, unit: 'MINUTES')
    disableConcurrentBuilds()
  }

  stages {

    stage('Checkout Code') {
      steps {
        deleteDir()
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
              node --max-old-space-size=4096 ./node_modules/@angular/cli/bin/ng build --configuration=production --source-map=false
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
                  --cache=true
              """
              echo "✅ ${svc.name} image built & pushed successfully."
              sleep 5
            }
          }
        }
      }
    }

    stage('Deploy to OVH Kubernetes') {
      steps {
        container('kubectl') {
          script {
            echo "🚀 Starting deployment to OVH Kubernetes..."

            withKubeConfig([credentialsId: 'kubernetes-credentials-id']) {
              sh """
                set -e
                echo "🧭 Using namespace: ${K8S_NAMESPACE}"
                kubectl create namespace ${K8S_NAMESPACE} --dry-run=client -o yaml | kubectl apply -f -

                echo "🧹 Cleaning old resources..."
                kubectl delete deployment --all -n ${K8S_NAMESPACE} || true
                kubectl delete svc --all -n ${K8S_NAMESPACE} || true

                echo "📁 Workspace content:"
                ls -R

                echo "⚙️ Deploying Eureka..."
                kubectl apply -f kubernetes/eureka.yaml -n ${K8S_NAMESPACE}
                sleep 30

                echo "⚙️ Deploying Gateway..."
                kubectl apply -f kubernetes/gateway.yaml -n ${K8S_NAMESPACE}
                sleep 15

                echo "⚙️ Deploying Compain..."
                kubectl apply -f kubernetes/compain-service.yaml -n ${K8S_NAMESPACE}

                echo "⚙️ Deploying Facturation..."
                kubectl apply -f kubernetes/facturation-service.yaml -n ${K8S_NAMESPACE}

                echo "⚙️ Deploying Depense..."
                kubectl apply -f kubernetes/depense-service.yaml -n ${K8S_NAMESPACE}

                echo "⚙️ Deploying Bank..."
                kubectl apply -f kubernetes/bank-service.yaml -n ${K8S_NAMESPACE}

                echo "⚙️ Deploying ReglementAffectation..."
                kubectl apply -f kubernetes/reglementaffectation-service.yaml -n ${K8S_NAMESPACE}

                echo "⚙️ Deploying Angular Frontend..."
                kubectl apply -f kubernetes/frontend.yaml -n ${K8S_NAMESPACE}

                echo "⏳ Waiting 90 seconds for all pods to start..."
                sleep 90

                echo "📋 Pods status:"
                kubectl get pods -n ${K8S_NAMESPACE} -o wide
              """

              // 🔍 Vérification automatique des pods
              def badPods = sh(
                script: """
                  kubectl get pods -n ${K8S_NAMESPACE} --no-headers | \
                  awk '{print \$1" "\$3}' | grep -E 'CrashLoopBackOff|Error|ImagePullBackOff|ErrImagePull' || true
                """,
                returnStdout: true
              ).trim()

              if (badPods) {
                echo "❌ The following pods failed to start:\\n${badPods}"
                error("Deployment failed — some pods did not start correctly.")
              } else {
                echo "✅ All pods are healthy and running."
              }
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
    always {
      echo '🧹 Cleaning workspace...'
    }
  }
}
