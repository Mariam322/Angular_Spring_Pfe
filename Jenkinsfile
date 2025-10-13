pipeline {
  agent none
  environment {
    DOCKER_REGISTRY = 'docker.io/mariammseddi12'
    K8S_NAMESPACE = 'default'
  }
  options {
    timeout(time: 90, unit: 'MINUTES')
    disableConcurrentBuilds()
    retry(2)
    buildDiscarder(logRotator(numToKeepStr: '10'))
  }
  stages {
    stage('Checkout Code') {
      agent any
      steps {
        deleteDir()
        git url: 'https://github.com/Mariam322/Angular_Spring_Pfe.git', branch: 'main'
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
    app: node-build
spec:
  serviceAccountName: default
  containers:
  - name: node
    image: node:20
    command: ["cat"]
    tty: true
    resources:
      requests:
        memory: "1Gi"
        cpu: "200m"
      limits:
        memory: "2Gi"
        cpu: "500m"
  restartPolicy: Never
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
              node --max-old-space-size=2048 ./node_modules/@angular/cli/bin/ng build --configuration=production --source-map=false
            '''
          }
        }
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
    app: maven-build
spec:
  serviceAccountName: default
  containers:
  - name: maven
    image: maven:3.9.9-eclipse-temurin-17
    command: ["cat"]
    tty: true
    resources:
      requests:
        memory: "1.5Gi"
        cpu: "300m"
      limits:
        memory: "2Gi"
        cpu: "600m"
  restartPolicy: Never
"""
        }
      }
      steps {
        container('maven') {
          sh '''
            for module in EurekaCompain Gatway ProjetCompain Facturation Depense BanqueService ReglementAffectation; do
              mvn -B --file $module/pom.xml clean package -DskipTests
            done
          '''
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
    app: kaniko-build
spec:
  serviceAccountName: default
  containers:
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
        cpu: "500m"
        ephemeral-storage: "10Gi"
      limits:
        memory: "4Gi"
        cpu: "1000m"
        ephemeral-storage: "20Gi"
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
              sh """
                /kaniko/executor \
                  --context=dir:///home/jenkins/agent/workspace/Pipline_OVH/${svc.path} \
                  --dockerfile=/home/jenkins/agent/workspace/Pipline_OVH/${svc.path}/Dockerfile \
                  --destination=${DOCKER_REGISTRY}/${svc.image}:latest \
                  --skip-tls-verify \
                  --snapshot-mode=redo \
                  --cache=true
              """
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
    app: kubectl-deploy
spec:
  serviceAccountName: default
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
  restartPolicy: Never
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
                kubectl delete deployment -l app=myapp -n ${K8S_NAMESPACE} || true
                kubectl delete svc -l app=myapp -n ${K8S_NAMESPACE} || true
                for f in eureka gateway compain-service facturation-service depense-service bank-service reglementaffectation-service frontend; do
                  kubectl apply -f kubernetes/$f.yaml -n ${K8S_NAMESPACE}
                  sleep 10
                done
                sleep 60
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
