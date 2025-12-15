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
  restartPolicy: Never

  containers:
  - name: jnlp
    image: jenkins/inbound-agent:3341.v0766d82b_dec0-1
    resources:
      requests:
        memory: "256Mi"
        cpu: "100m"
      limits:
        memory: "512Mi"

  - name: node
    image: node:20
    command: ["cat"]
    tty: true
    resources:
      requests:
        memory: "512Mi"
        cpu: "500m"
      limits:
        memory: "1Gi"

  - name: maven
    image: maven:3.9.9-eclipse-temurin-17
    command: ["cat"]
    tty: true
    resources:
      requests:
        memory: "512Mi"
        cpu: "500m"
      limits:
        memory: "1Gi"

  - name: kaniko
    image: gcr.io/kaniko-project/executor:debug
    command: ["sleep"]
    args: ["999999"]
    imagePullPolicy: Always
    resources:
      requests:
        memory: "1Gi"
        cpu: "500m"
      limits:
        memory: "2Gi"
    volumeMounts:
      - name: docker-config
        mountPath: /kaniko/.docker

  - name: kubectl
    image: lachlanevenson/k8s-kubectl:v1.25.4
    command: ["cat"]
    tty: true
    resources:
      requests:
        memory: "128Mi"
        cpu: "100m"
      limits:
        memory: "256Mi"

  volumes:
  - name: docker-config
    projected:
      sources:
      - secret:
          name: regcred
          items:
          - key: .dockerconfigjson
            path: config.json
"""
    }
  }

  environment {
    DOCKER_REGISTRY = 'docker.io/mariammseddi12'
    K8S_NAMESPACE   = 'default'
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
              export NODE_OPTIONS=--max_old_space_size=1024
              npm config set legacy-peer-deps true
              npm install
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
              [name: 'Reglement', path: 'ReglementAffectation', image: 'reglementaffectation-service'],
              [name: 'Angular', path: 'BankprojetFront', image: 'angular-frontend']
            ]

            for (svc in services) {
              sh """
                /kaniko/executor \
                  --context=dir://${WORKSPACE}/${svc.path} \
                  --dockerfile=${WORKSPACE}/${svc.path}/Dockerfile \
                  --destination=${DOCKER_REGISTRY}/${svc.image}:latest \
                  --skip-tls-verify \
                  --snapshot-mode=redo \
                  --single-snapshot \
                  --use-new-run
              """
            }
          }
        }
      }
    }

    stage('Deploy to Kubernetes VPS') {
      steps {
        container('kubectl') {
          withKubeConfig([credentialsId: 'kubernetes-vps-config']) {
            sh '''
              kubectl create namespace default --dry-run=client -o yaml | kubectl apply -f -
              kubectl apply -f kubernetes/ -n default
              kubectl get pods -n default
            '''
          }
        }
      }
    }
  }

  post {
    success {
      echo '✅ Pipeline completed successfully'
    }
    failure {
      echo '❌ Pipeline failed'
    }
    always {
      deleteDir()
    }
  }
}
