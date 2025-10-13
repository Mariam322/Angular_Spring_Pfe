pipeline {
  agent none
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
      agent any
      steps {
        deleteDir()
        git url: 'https://github.com/Mariam322/Angular_Spring_Pfe.git', branch: 'main'
      }
    }

    stage('Build Java JARs') {
      agent {
        kubernetes {
          yaml """
apiVersion: v1
kind: Pod
metadata:
  labels:
    app: maven-pod
spec:
  containers:
  - name: maven
    image: maven:3.9.9-eclipse-temurin-17
    command: ["cat"]
    tty: true
    resources:
      requests:
        memory: "1Gi"
        cpu: "300m"
      limits:
        memory: "2Gi"
        cpu: "700m"
  - name: jnlp
    image: jenkins/inbound-agent:latest
    command: ["cat"]
    tty: true
    resources:
      requests:
        memory: "128Mi"
        cpu: "100m"
      limits:
        memory: "256Mi"
        cpu: "200m"
"""
        }
      }
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

    stage('Build Angular Frontend') {
      agent {
        kubernetes {
          yaml """
apiVersion: v1
kind: Pod
metadata:
  labels:
    app: node-pod
spec:
  containers:
  - name: node
    image: node:20
    command: ["cat"]
    tty: true
    resources:
      requests:
        memory: "1Gi"
        cpu: "300m"
      limits:
        memory: "2Gi"
        cpu: "700m"
  - name: jnlp
    image: jenkins/inbound-agent:latest
    command: ["cat"]
    tty: true
    resources:
      requests:
        memory: "128Mi"
        cpu: "100m"
      limits:
        memory: "256Mi"
        cpu: "200m"
"""
        }
      }
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
              node --max-old-space-size=1024 ./node_modules/@angular/cli/bin/ng build --configuration=production --source-map=false
            '''
          }
        }
      }
    }

    stage('Build & Push Docker Images') {
      agent {
        kubernetes {
          yaml """
apiVersion: v1
kind: Pod
metadata:
  labels:
    app: kaniko-pod
spec:
  containers:
  - name: kaniko
    image: gcr.io/kaniko-project/executor:debug
    command: ["sleep"]
    args: ["9999999"]
    volumeMounts:
      - name: docker-config
        mountPath: /kaniko/.docker
    resources:
      requests:
        memory: "2Gi"
        cpu: "500m"
        ephemeral-storage: "10Gi"
      limits:
        memory: "4Gi"
        cpu: "1000m"
        ephemeral-storage: "20Gi"
  - name: jnlp
    image: jenkins/inbound-agent:latest
    command: ["cat"]
    tty: true
    resources:
      requests:
        memory: "128Mi"
        cpu: "100m"
      limits:
        memory: "256Mi"
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
"""
        }
      }
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
      agent {
        kubernetes {
          yaml """
apiVersion: v1
kind: Pod
metadata:
  labels:
    app: kubectl-pod
spec:
  containers:
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
  - name: jnlp
    image: jenkins/inbound-agent:latest
    command: ["cat"]
    tty: true
    resources:
      requests:
        memory: "128Mi"
        cpu: "100m"
      limits:
        memory: "256Mi"
        cpu: "200m"
"""
        }
      }
      steps {
        container('kubectl') {
          script {
            withKubeConfig([credentialsId: 'kubernetes-credentials-id']) {
              sh """
                set -e
                kubectl create namespace ${K8S_NAMESPACE} --dry-run=client -o yaml | kubectl apply -f -
                kubectl delete deployment --all -n ${K8S_NAMESPACE} || true
                kubectl delete svc --all -n ${K8S_NAMESPACE} || true

                kubectl apply -f kubernetes/eureka.yaml -n ${K8S_NAMESPACE}
                sleep 30
                kubectl apply -f kubernetes/gateway.yaml -n ${K8S_NAMESPACE}
                sleep 15
                kubectl apply -f kubernetes/compain-service.yaml -n ${K8S_NAMESPACE}
                kubectl apply -f kubernetes/facturation-service.yaml -n ${K8S_NAMESPACE}
                kubectl apply -f kubernetes/depense-service.yaml -n ${K8S_NAMESPACE}
                kubectl apply -f kubernetes/bank-service.yaml -n ${K8S_NAMESPACE}
                kubectl apply -f kubernetes/reglementaffectation-service.yaml -n ${K8S_NAMESPACE}
                kubectl apply -f kubernetes/frontend.yaml -n ${K8S_NAMESPACE}

                sleep 90
                kubectl get pods -n ${K8S_NAMESPACE} -o wide
              """
            }
          }
        }
      }
    }
  }

  post {
    success { echo '✅ Pipeline completed successfully (Build + Push + Deploy)' }
    failure { echo '❌ Pipeline failed — check Jenkins logs for details.' }
    always { echo '🧹 Cleaning workspace...' }
  }
}
