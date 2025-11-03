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

  - name: kubectl
    image: lachlanevenson/k8s-kubectl:v1.25.4
    command: ["cat"]
    tty: true

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
              npm run build
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
            mvn -B -f BanqueService/pom.xml clean package -DskipTests
            mvn -B -f Depense/pom.xml clean package -DskipTests
            mvn -B -f Facturation/pom.xml clean package -DskipTests
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
              [name: 'Eureka', path: 'EurekaCompain', image: 'eureka-server'],
              [name: 'Gateway', path: 'Gatway', image: 'gateway-service'],
              [name: 'Compain', path: 'ProjetCompain', image: 'compain-service'],
              [name: 'Bank', path: 'BanqueService', image: 'bank-service'],
              [name: 'Depense', path: 'Depense', image: 'depense-service'],
              [name: 'Facturation', path: 'Facturation', image: 'facturation-service'],
              [name: 'ReglementAffectation', path: 'ReglementAffectation', image: 'reglementaffectation-service'],
              [name: 'Angular', path: 'BankprojetFront', image: 'angular-frontend']
            ]

            for (svc in services) {
              echo "🚀 Building ${svc.name} image..."
              sh "rm -rf /kaniko/.cache_${svc.name} || true"
              sh """
                /kaniko/executor \
                  --context=dir://${WORKSPACE}/${svc.path} \
                  --dockerfile=${WORKSPACE}/${svc.path}/Dockerfile \
                  --destination=${DOCKER_REGISTRY}/${svc.image}:latest \
                  --skip-tls-verify \
                  --snapshot-mode=redo \
                  --cache=true
              """
              echo "✅ ${svc.name} image built & pushed successfully."
            }
          }
        }
      }
    }

    stage('Deploy to VPS Kubernetes') {
      steps {
        container('kubectl') {
          script {
            echo "🚀 Starting deployment to VPS Kubernetes cluster..."

            withKubeConfig([credentialsId: 'kubernetes-vps-config']) {
              sh """
                set -e
                echo "🧭 Using namespace: ${K8S_NAMESPACE}"
                kubectl create namespace ${K8S_NAMESPACE} --dry-run=client -o yaml | kubectl apply -f -

                echo "⚙️ Applying Kubernetes manifests..."

                # 1️⃣ Déployer les bases de données d'abord
                kubectl apply -f kubernetes/compain-db.yaml -n ${K8S_NAMESPACE}
                kubectl apply -f kubernetes/bank-db.yaml -n ${K8S_NAMESPACE}
                kubectl apply -f kubernetes/depense-db.yaml -n ${K8S_NAMESPACE}
                kubectl apply -f kubernetes/facturation-db.yaml -n ${K8S_NAMESPACE}
                kubectl apply -f kubernetes/reglemetnaffecatation-db.yaml -n ${K8S_NAMESPACE}

                echo "⏳ Waiting for databases to initialize..."
                sleep 40

                # 2️⃣ Déployer les microservices Spring Boot
                kubectl apply -f kubernetes/eureka.yaml -n ${K8S_NAMESPACE}
                kubectl apply -f kubernetes/gateway.yaml -n ${K8S_NAMESPACE}
                kubectl apply -f kubernetes/compain-service.yaml -n ${K8S_NAMESPACE}
                kubectl apply -f kubernetes/bank-service.yaml -n ${K8S_NAMESPACE}
                kubectl apply -f kubernetes/depense-service.yaml -n ${K8S_NAMESPACE}
                kubectl apply -f kubernetes/facturation-service.yaml -n ${K8S_NAMESPACE}
                kubectl apply -f kubernetes/reglementaffectation-service.yaml -n ${K8S_NAMESPACE}

                echo "⏳ Waiting for microservices to register with Eureka..."
                sleep 60

                # 3️⃣ Déployer le frontend Angular
                kubectl apply -f kubernetes/frontend.yaml -n ${K8S_NAMESPACE}

                echo "✅ Deployment complete."
                kubectl get pods -n ${K8S_NAMESPACE} -o wide
              """
            }
          }
        }
      }
    }
  }

  post {
    success {
      echo '✅ Pipeline completed successfully (DBs + Eureka + Gateway + Services + Frontend)'
    }
    failure {
      echo '❌ Pipeline failed — check Jenkins logs for details.'
    }
    always {
      echo '🧹 Cleaning workspace...'
    }
  }
}
