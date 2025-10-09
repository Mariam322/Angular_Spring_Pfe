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
        memory: "400Mi"
        cpu: "100m"
      limits:
        memory: "1Gi"
        cpu: "400m"

  - name: node
    image: node:20
    command: ["cat"]
    tty: true
    resources:
      requests:
        memory: "2Gi"
        cpu: "300m"
      limits:
        memory: "6Gi"
        cpu: "1200m"

  - name: kaniko
    image: anjia0532/kaniko-project.executor:v1.23.2-debug
    imagePullPolicy: Always
    command: ["sleep"]
    args: ["9999999"]
    volumeMounts:
      - name: docker-config
        mountPath: /kaniko/.docker
    resources:
      requests:
        memory: "2Gi"
        cpu: "300m"
      limits:
        memory: "4Gi"
        cpu: "1000m"

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

  stages {

    /* 🔹 Étape 1 : Checkout du code */
    stage('Checkout Code') {
      steps {
        git url: 'https://github.com/Mariam322/Angular_Spring_Pfe.git', branch: 'main'
      }
    }

    /* 🔹 Étape 2 : Build Angular */
    stage('Build Angular Frontend') {
      steps {
        container('node') {
          dir('BankprojetFront') {
            sh '''
              npm config set legacy-peer-deps true
              npm install
              npm install @popperjs/core --save

              # Supprimer les budgets Angular (pour éviter les erreurs build)
              node -e "
                const fs = require('fs');
                const config = JSON.parse(fs.readFileSync('angular.json', 'utf8'));
                const project = Object.keys(config.projects)[0];
                if (config.projects[project]?.architect?.build?.configurations?.production?.budgets) {
                  delete config.projects[project].architect.build.configurations.production.budgets;
                }
                fs.writeFileSync('angular.json', JSON.stringify(config, null, 2));
              "

              # Build optimisé Angular
              node --max-old-space-size=2048 ./node_modules/@angular/cli/bin/ng build --configuration=production --source-map=false
            '''
          }
        }
      }
    }

    /* 🔹 Étape 3 : Build des JARs Java */
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

    /* 🔹 Étape 4 : Build & Push Docker images */
    stage('Build & Push Docker Images') {
      parallel {

        stage('Eureka Image') {
          steps {
            container('kaniko') {
              sh '''
                /kaniko/executor \
                  --context=dir:///home/jenkins/agent/workspace/Pipline_OVH/EurekaCompain \
                  --dockerfile=/home/jenkins/agent/workspace/Pipline_OVH/EurekaCompain/Dockerfile \
                  --destination=docker.io/mariammseddi12/eureka-server:latest \
                  --skip-tls-verify \
                  --snapshot-mode=redo \
                  --use-new-run \
                  --single-snapshot \
                  --no-push-cache
              '''
            }
          }
        }

        stage('Gateway Image') {
          steps {
            container('kaniko') {
              sh '''
                /kaniko/executor \
                  --context=dir:///home/jenkins/agent/workspace/Pipline_OVH/Gatway \
                  --dockerfile=/home/jenkins/agent/workspace/Pipline_OVH/Gatway/Dockerfile \
                  --destination=docker.io/mariammseddi12/gateway-service:latest \
                  --skip-tls-verify \
                  --snapshot-mode=redo \
                  --use-new-run \
                  --single-snapshot \
                  --no-push-cache
              '''
            }
          }
        }

        stage('Compain Image') {
          steps {
            container('kaniko') {
              sh '''
                /kaniko/executor \
                  --context=dir:///home/jenkins/agent/workspace/Pipline_OVH/ProjetCompain \
                  --dockerfile=/home/jenkins/agent/workspace/Pipline_OVH/ProjetCompain/Dockerfile \
                  --destination=docker.io/mariammseddi12/compain-service:latest \
                  --skip-tls-verify \
                  --snapshot-mode=redo \
                  --use-new-run \
                  --single-snapshot \
                  --no-push-cache
              '''
            }
          }
        }

        stage('Facturation Image') {
          steps {
            container('kaniko') {
              sh '''
                /kaniko/executor \
                  --context=dir:///home/jenkins/agent/workspace/Pipline_OVH/Facturation \
                  --dockerfile=/home/jenkins/agent/workspace/Pipline_OVH/Facturation/Dockerfile \
                  --destination=docker.io/mariammseddi12/facturation-service:latest \
                  --skip-tls-verify \
                  --snapshot-mode=redo \
                  --use-new-run \
                  --single-snapshot \
                  --no-push-cache
              '''
            }
          }
        }

        stage('Depense Image') {
          steps {
            container('kaniko') {
              sh '''
                /kaniko/executor \
                  --context=dir:///home/jenkins/agent/workspace/Pipline_OVH/Depense \
                  --dockerfile=/home/jenkins/agent/workspace/Pipline_OVH/Depense/Dockerfile \
                  --destination=docker.io/mariammseddi12/depense-service:latest \
                  --skip-tls-verify \
                  --snapshot-mode=redo \
                  --use-new-run \
                  --single-snapshot \
                  --no-push-cache
              '''
            }
          }
        }

        stage('Bank Image') {
          steps {
            container('kaniko') {
              sh '''
                /kaniko/executor \
                  --context=dir:///home/jenkins/agent/workspace/Pipline_OVH/BanqueService \
                  --dockerfile=/home/jenkins/agent/workspace/Pipline_OVH/BanqueService/Dockerfile \
                  --destination=docker.io/mariammseddi12/bank-service:latest \
                  --skip-tls-verify \
                  --snapshot-mode=redo \
                  --use-new-run \
                  --single-snapshot \
                  --no-push-cache
              '''
            }
          }
        }

        stage('ReglementAffectation Image') {
          steps {
            container('kaniko') {
              sh '''
                /kaniko/executor \
                  --context=dir:///home/jenkins/agent/workspace/Pipline_OVH/ReglementAffectation \
                  --dockerfile=/home/jenkins/agent/workspace/Pipline_OVH/ReglementAffectation/Dockerfile \
                  --destination=docker.io/mariammseddi12/reglementaffectation-service:latest \
                  --skip-tls-verify \
                  --snapshot-mode=redo \
                  --use-new-run \
                  --single-snapshot \
                  --no-push-cache
              '''
            }
          }
        }

        stage('Angular Image') {
          steps {
            container('kaniko') {
              sh '''
                /kaniko/executor \
                  --context=dir:///home/jenkins/agent/workspace/Pipline_OVH/BankprojetFront \
                  --dockerfile=/home/jenkins/agent/workspace/Pipline_OVH/BankprojetFront/Dockerfile \
                  --destination=docker.io/mariammseddi12/angular-frontend:latest \
                  --skip-tls-verify \
                  --snapshot-mode=redo \
                  --use-new-run \
                  --single-snapshot \
                  --no-push-cache
              '''
            }
          }
        }
      }
    }

    /* 🔹 Étape 5 : Déploiement Kubernetes */
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
