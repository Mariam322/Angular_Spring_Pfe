pipeline {
  agent {
    kubernetes {
      label 'kaniko-agent'
      defaultContainer 'jnlp'
      yaml """
apiVersion: v1
kind: Pod
metadata:
  labels:
    app: kaniko
spec:
  containers:
    - name: kaniko
      image: mariammseddi12/kaniko-executor:latest
      command:
        - cat
      tty: true
      volumeMounts:
        - name: kaniko-secret
          mountPath: /kaniko/.docker/
    - name: maven
      image: maven:3.9.6-eclipse-temurin-17
      command: ['cat']
      tty: true
    - name: node
      image: node:18
      command: ['cat']
      tty: true
  volumes:
    - name: kaniko-secret
      secret:
        secretName: regcred
"""
    }
  }

  environment {
    DOCKER_REGISTRY = "docker.io/mariammseddi12"
    IMAGE_NAME = "angular-spring-pfe"
  }

  stages {
    stage('Build Angular') {
      steps {
        container('node') {
          sh '''
          cd frontend
          npm install
          npm run build
          '''
        }
      }
    }

    stage('Build Spring Boot') {
      steps {
        container('maven') {
          sh '''
          cd backend
          mvn clean package -DskipTests
          '''
        }
      }
    }

    stage('Build & Push Docker Image') {
      steps {
        container('kaniko') {
          sh '''
          /kaniko/executor \
            --context `pwd` \
            --dockerfile Dockerfile \
            --destination=$DOCKER_REGISTRY/$IMAGE_NAME:latest \
            --verbosity=info
          '''
        }
      }
    }
  }
}
