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
        memory: "1Gi"
        cpu: "250m"
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
        memory: "1Gi"
        cpu: "250m"
      limits:
        memory: "3Gi"
        cpu: "800m"

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

              # Supprimer les budgets dans angular.json (optimisation)
              node -e "
                const fs = require('fs');
                const config = JSON.parse(fs.readFileSync('angular.json', 'utf8'));
                const project = Object.keys(config.projects)[0];
                if (config.projects[project]?.architect?.build?.configurations?.production?.budgets) {
                  delete config.projects[project].architect.build.configurations.production.budgets;
                }
                fs.writeFileSync('angular.json', JSON.stringify(config, null, 2));
              "

              # Build Angular avec mémoire optimisée
              node --max-old-space-size=2048 ./node_modules/@angular/cli/bin/ng build --configuration=production --source-map=false
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
      parallel {

        stage('Eureka Image') {
          steps {
            container('kaniko') {
              sh 'rm -rf /kaniko/.cache_Eureka || true'
              sh """
                /kaniko/executor \
                  --context=dir:///home/jenkins/agent/workspace/Pipline_OVH/EurekaCompain \
                  --dockerfile=/home/jenkins/agent/workspace/Pipline_OVH/EurekaCompain/Dockerfile \
                  --destination=${DOCKER_REGISTRY}/eureka-server:latest \
                  --skip-tls-verify
              """
            }
          }
        }

        stage('Gateway Image') {
          steps {
            container('kaniko') {
              sh 'rm -rf /kaniko/.cache_Gateway || true'
              sh """
                /kaniko/executor \
                  --context=dir:///home/jenkins/agent/workspace/Pipline_OVH/Gatway \
                  --dockerfile=/home/jenkins/agent/workspace/Pipline_OVH/Gatway/Dockerfile \
                  --destination=${DOCKER_REGISTRY}/gateway-service:latest \
                  --skip-tls-verify
              """
            }
          }
        }

        stage('Compain Image') {
          steps {
            container('kaniko') {
              sh 'rm -rf /kaniko/.cache_Compain || true'
              sh """
                /kaniko/executor \
                  --context=dir:///home/jenkins/agent/workspace/Pipline_OVH/ProjetCompain \
                  --dockerfile=/home/jenkins/agent/workspace/Pipline_OVH/ProjetCompain/Dockerfile \
                  --destination=${DOCKER_REGISTRY}/compain-service:latest \
                  --skip-tls-verify
              """
            }
          }
        }

        stage('Facturation Image') {
          steps {
            container('kaniko') {
              sh 'rm -rf /kaniko/.cache_Facturation || true'
              sh """
                /kaniko/executor \
                  --context=dir:///home/jenkins/agent/workspace/Pipline_OVH/Facturation \
                  --dockerfile=/home/jenkins/agent/workspace/Pipline_OVH/Facturation/Dockerfile \
                  --destination=${DOCKER_REGISTRY}/facturation-service:latest \
                  --skip-tls-verify
              """
            }
          }
        }

        stage('Depense Image') {
          steps {
            container('kaniko') {
              sh 'rm -rf /kaniko/.cache_Depense || true'
              sh """
                /kaniko/executor \
                  --context=dir:///home/jenkins/agent/workspace/Pipline_OVH/Depense \
                  --dockerfile=/home/jenkins/agent/workspace/Pipline_OVH/Depense/Dockerfile \
                  --destination=${DOCKER_REGISTRY}/depense-service:latest \
                  --skip-tls-verify
              """
            }
          }
        }

        stage('Bank Image') {
          steps {
            container('kaniko') {
              sh 'rm -rf /kaniko/.cache_Bank || true'
              sh """
                /kaniko/executor \
                  --context=dir:///home/jenkins/agent/workspace/Pipline_OVH/BanqueService \
                  --dockerfile=/home/jenkins/agent/workspace/Pipline_OVH/BanqueService/Dockerfile \
                  --destination=${DOCKER_REGISTRY}/bank-service:latest \
                  --skip-tls-verify
              """
            }
          }
        }

        stage('ReglementAffectation Image') {
          steps {
            container('kaniko') {
              sh 'rm -rf /kaniko/.cache_Reglement || true'
              sh """
                /kaniko/executor \
                  --context=dir:///home/jenkins/agent/workspace/Pipline_OVH/ReglementAffectation \
                  --dockerfile=/home/jenkins/agent/workspace/Pipline_OVH/ReglementAffectation/Dockerfile \
                  --destination=${DOCKER_REGISTRY}/reglementaffectation-service:latest \
                  --skip-tls-verify
              """
            }
          }
        }

        stage('Angular Image') {
          steps {
            container('kaniko') {
              sh """
                /kaniko/executor \
                  --context=dir:///home/jenkins/agent/workspace/Pipline_OVH/BankprojetFront \
                  --dockerfile=/home/jenkins/agent/workspace/Pipline_OVH/BankprojetFront/Dockerfile \
                  --destination=${DOCKER_REGISTRY}/angular-frontend:latest \
                  --skip-tls-verify
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
