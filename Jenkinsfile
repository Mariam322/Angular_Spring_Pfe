pipeline {
  agent {
    kubernetes {
      label 'kaniko-agent'
      yaml """
apiVersion: v1
kind: Pod
metadata:
  labels:
    app: kaniko-agent
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
      image: mariammseddi12/kaniko-executor:latest
      command: ["/bin/sh", "-c", "while true; do sleep 3600; done"]
      tty: true
      volumeMounts:
        - name: docker-config
          mountPath: /kaniko/.docker/
  volumes:
    - name: docker-config
      secret:
        secretName: regcred
"""
    }
  }

  environment {
    DOCKER_REGISTRY = 'mariammseddi12'
    K8S_NAMESPACE = 'default'
    MAVEN_COMPILER_VERSION = '-Dmaven.compiler.plugin.version=3.11.0'
  }

  stages {

    // ====== 🔹 Checkout ======
    stage('Checkout Code') {
      steps {
        git url: 'https://github.com/Mariam322/Angular_Spring_Pfe.git', branch: 'main'
      }
    }

    // ====== 🔹 Build Microservices ======
    stage('Build Microservices') {
      parallel {
        stage('Eureka') {
          steps { container('maven') { dir('EurekaCompain') { sh "mvn clean package -DskipTests ${MAVEN_COMPILER_VERSION}" } } }
        }
        stage('Gateway') {
          steps { container('maven') { dir('Gatway') { sh "mvn clean package -DskipTests ${MAVEN_COMPILER_VERSION}" } } }
        }
        stage('Compain') {
          steps { container('maven') { dir('ProjetCompain') { sh "mvn clean package -DskipTests ${MAVEN_COMPILER_VERSION}" } } }
        }
        stage('Facturation') {
          steps { container('maven') { dir('Facturation') { sh "mvn clean package -DskipTests ${MAVEN_COMPILER_VERSION}" } } }
        }
        stage('Depense') {
          steps { container('maven') { dir('Depense') { sh "mvn clean package -DskipTests ${MAVEN_COMPILER_VERSION}" } } }
        }
        stage('Bank') {
          steps { container('maven') { dir('BanqueService') { sh "mvn clean package -DskipTests ${MAVEN_COMPILER_VERSION}" } } }
        }
        stage('ReglementAffectation') {
          steps { container('maven') { dir('ReglementAffectation') { sh "mvn clean package -DskipTests ${MAVEN_COMPILER_VERSION}" } } }
        }
      }
    }

    // ====== 🔹 Build Angular ======
    stage('Build Angular Frontend') {
      steps {
        container('node') {
          dir('BankprojetFront') {
            sh '''
              echo "=== Build Angular - Désactivation des budgets ==="
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
                console.log('✅ Budgets désactivés');
              "

              npx ng build --configuration=production --source-map=false
              echo "✅ Build Angular terminé"
            '''
          }
        }
      }
    }

    // ====== 🔹 Build & Push Docker Images (Kaniko) ======
    stage('Build & Push Docker Images') {
      parallel {
        stage('Eureka Image') {
          steps { container('kaniko') { dir('EurekaCompain') { sh "/kaniko/executor --context `pwd` --destination=${DOCKER_REGISTRY}/eureka-server:latest --skip-tls-verify" } } }
        }
        stage('Gateway Image') {
          steps { container('kaniko') { dir('Gatway') { sh "/kaniko/executor --context `pwd` --destination=${DOCKER_REGISTRY}/gateway-service:latest --skip-tls-verify" } } }
        }
        stage('Compain Image') {
          steps { container('kaniko') { dir('ProjetCompain') { sh "/kaniko/executor --context `pwd` --destination=${DOCKER_REGISTRY}/compain-service:latest --skip-tls-verify" } } }
        }
        stage('Facturation Image') {
          steps { container('kaniko') { dir('Facturation') { sh "/kaniko/executor --context `pwd` --destination=${DOCKER_REGISTRY}/facturation-service:latest --skip-tls-verify" } } }
        }
        stage('Depense Image') {
          steps { container('kaniko') { dir('Depense') { sh "/kaniko/executor --context `pwd` --destination=${DOCKER_REGISTRY}/depense-service:latest --skip-tls-verify" } } }
        }
        stage('Bank Image') {
          steps { container('kaniko') { dir('BanqueService') { sh "/kaniko/executor --context `pwd` --destination=${DOCKER_REGISTRY}/bank-service:latest --skip-tls-verify" } } }
        }
        stage('ReglementAffectation Image') {
          steps { container('kaniko') { dir('ReglementAffectation') { sh "/kaniko/executor --context `pwd` --destination=${DOCKER_REGISTRY}/reglementaffectation-service:latest --skip-tls-verify" } } }
        }
        stage('Angular Image') {
          steps { container('kaniko') { dir('BankprojetFront') { sh "/kaniko/executor --context `pwd` --destination=${DOCKER_REGISTRY}/angular-frontend:latest --skip-tls-verify" } } }
        }
      }
    }

    // ====== 🔹 Déploiement sur Kubernetes ======
    stage('Deploy to OVH Kubernetes') {
      steps {
        script {
          withKubeConfig([credentialsId: 'kubernetes-credentials-id']) {
            sh """
              kubectl create namespace ${K8S_NAMESPACE} --dry-run=client -o yaml | kubectl apply -f -
              kubectl apply -f kubernetes/eureka.yaml -n ${K8S_NAMESPACE}
              sleep 15
              kubectl apply -f kubernetes/gateway.yaml -n ${K8S_NAMESPACE}
              kubectl apply -f kubernetes/compain-service.yaml -n ${K8S_NAMESPACE}
              kubectl apply -f kubernetes/facturation-service.yaml -n ${K8S_NAMESPACE}
              kubectl apply -f kubernetes/depense-service.yaml -n ${K8S_NAMESPACE}
              kubectl apply -f kubernetes/bank-service.yaml -n ${K8S_NAMESPACE}
              kubectl apply -f kubernetes/reglementaffectation-service.yaml -n ${K8S_NAMESPACE}
              kubectl apply -f kubernetes/frontend.yaml -n ${K8S_NAMESPACE}
            """

            def services = [
              'eureka-server', 'gateway-service', 'compain-service',
              'facturation-service', 'depense-service', 'bank-service',
              'reglementaffectation-service', 'angular-frontend'
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
    success { echo '✅ Pipeline complet exécuté avec succès (Build + Push + Deploy)' }
    failure { echo '❌ Le pipeline a échoué — consulte les logs Jenkins pour les détails.' }
  }
}
