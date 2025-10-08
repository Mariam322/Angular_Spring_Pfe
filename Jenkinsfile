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
      image: gcr.io/kaniko-project/executor:v1.9.0
      command: ["/busybox/sh", "-c", "while true; do sleep 3600; done"]
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

    // =======================================================
    stage('Checkout Code') {
      steps {
        echo "🔹 Clonage du dépôt principal..."
        git url: 'https://github.com/Mariam322/Angular_Spring_Pfe.git', branch: 'main'
      }
    }

    // =======================================================
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

    // =======================================================
    stage('Build Angular Frontend') {
      steps {
        container('node') {
          dir('BankprojetFront') {
            sh '''
              echo "=== 🚧 Build Angular - Désactivation des budgets ==="
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
              echo "✅ Build Angular terminé avec succès"
            '''
          }
        }
      }
    }

    // =======================================================
    stage('Build & Push Docker Images (via Kaniko)') {
      parallel {
        stage('Eureka Image') {
          steps {
            container('kaniko') {
              dir('EurekaCompain') {
                sh """
                  echo "📦 Building Eureka image..."
                  /kaniko/executor --context `pwd` \
                    --dockerfile Dockerfile \
                    --destination=${DOCKER_REGISTRY}/eureka-server:latest \
                    --skip-tls-verify
                """
              }
            }
          }
        }

        stage('Gateway Image') {
          steps {
            container('kaniko') {
              dir('Gatway') {
                sh """
                  echo "📦 Building Gateway image..."
                  /kaniko/executor --context `pwd` \
                    --dockerfile Dockerfile \
                    --destination=${DOCKER_REGISTRY}/gateway-service:latest \
                    --skip-tls-verify
                """
              }
            }
          }
        }

        stage('Compain Image') {
          steps {
            container('kaniko') {
              dir('ProjetCompain') {
                sh """
                  echo "📦 Building Compain image..."
                  /kaniko/executor --context `pwd` \
                    --dockerfile Dockerfile \
                    --destination=${DOCKER_REGISTRY}/compain-service:latest \
                    --skip-tls-verify
                """
              }
            }
          }
        }

        stage('Facturation Image') {
          steps {
            container('kaniko') {
              dir('Facturation') {
                sh """
                  echo "📦 Building Facturation image..."
                  /kaniko/executor --context `pwd` \
                    --dockerfile Dockerfile \
                    --destination=${DOCKER_REGISTRY}/facturation-service:latest \
                    --skip-tls-verify
                """
              }
            }
          }
        }

        stage('Depense Image') {
          steps {
            container('kaniko') {
              dir('Depense') {
                sh """
                  echo "📦 Building Depense image..."
                  /kaniko/executor --context `pwd` \
                    --dockerfile Dockerfile \
                    --destination=${DOCKER_REGISTRY}/depense-service:latest \
                    --skip-tls-verify
                """
              }
            }
          }
        }

        stage('Bank Image') {
          steps {
            container('kaniko') {
              dir('BanqueService') {
                sh """
                  echo "📦 Building Bank image..."
                  /kaniko/executor --context `pwd` \
                    --dockerfile Dockerfile \
                    --destination=${DOCKER_REGISTRY}/bank-service:latest \
                    --skip-tls-verify
                """
              }
            }
          }
        }

        stage('ReglementAffectation Image') {
          steps {
            container('kaniko') {
              dir('ReglementAffectation') {
                sh """
                  echo "📦 Building ReglementAffectation image..."
                  /kaniko/executor --context `pwd` \
                    --dockerfile Dockerfile \
                    --destination=${DOCKER_REGISTRY}/reglementaffectation-service:latest \
                    --skip-tls-verify
                """
              }
            }
          }
        }

        stage('Angular Image') {
          steps {
            container('kaniko') {
              dir('BankprojetFront') {
                sh """
                  echo "📦 Building Angular image..."
                  /kaniko/executor --context `pwd` \
                    --dockerfile Dockerfile \
                    --destination=${DOCKER_REGISTRY}/angular-frontend:latest \
                    --skip-tls-verify
                """
              }
            }
          }
        }
      }
    }

    // =======================================================
    stage('Deploy to OVH Kubernetes') {
      steps {
        script {
          withKubeConfig([credentialsId: 'kubernetes-credentials-id']) {
            echo "🚀 Déploiement des services sur le cluster OVH..."
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
    success {
      echo '✅ Pipeline complet exécuté avec succès (Build + Push + Deploy) 🚀'
    }
    failure {
      echo '❌ Le pipeline a échoué — vérifie les logs Jenkins pour plus de détails.'
    }
  }
}
