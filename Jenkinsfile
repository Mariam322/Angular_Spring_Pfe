pipeline {
    agent {
        kubernetes {
            label 'kaniko-agent'
            yaml """
apiVersion: v1
kind: Pod
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
    image: docker.io/mariammseddi12/kaniko-executor:latest
    command: ["sleep", "3600"]
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
        stage('Checkout Code') {
            steps {
                git url: 'https://github.com/Mariam322/Angular_Spring_Pfe.git', branch: 'main'
            }
        }

        stage('Build Backend Services') {
            steps {
                container('maven') {
                    script {
                        def services = [
                            "EurekaCompain", "Gatway", "ProjetCompain",
                            "Facturation", "Depense", "BanqueService",
                            "ReglementAffectation"
                        ]
                        for (svc in services) {
                            dir(svc) {
                                echo "🏗️ Building backend: ${svc}"
                                sh "mvn clean package -DskipTests ${MAVEN_COMPILER_VERSION}"
                            }
                        }
                    }
                }
            }
        }

        stage('Build Angular Frontend') {
            steps {
                container('node') {
                    dir('BankprojetFront') {
                        sh '''
                            echo "=== Build Angular ==="
                            npm config set legacy-peer-deps true
                            npm install
                            npm install @popperjs/core --save
                            npx ng build --configuration=production --source-map=false
                            echo "✅ Build Angular terminé avec succès"
                        '''
                    }
                }
            }
        }

        stage('Vérifier Secret Docker') {
            steps {
                container('kaniko') {
                    sh '''
                        echo "🔍 Vérification du secret Docker..."
                        if [ -f /kaniko/.docker/config.json ]; then
                            echo "✅ Secret Docker monté avec succès."
                        else
                            echo "❌ ERREUR : secret Docker non monté."
                            exit 1
                        fi
                    '''
                }
            }
        }

        stage('Build & Push Docker Images') {
            steps {
                container('kaniko') {
                    script {
                        def images = [
                            [dir: "EurekaCompain",           name: "eureka-server"],
                            [dir: "Gatway",                 name: "gateway-service"],
                            [dir: "ProjetCompain",          name: "compain-service"],
                            [dir: "Facturation",            name: "facturation-service"],
                            [dir: "Depense",                name: "depense-service"],
                            [dir: "BanqueService",          name: "bank-service"],
                            [dir: "ReglementAffectation",   name: "reglementaffectation-service"],
                            [dir: "BankprojetFront",        name: "angular-frontend"]
                        ]

                        for (img in images) {
                            dir(img.dir) {
                                sh """
                                    echo "🚀 Build & Push ${img.name}"
                                    /kaniko/executor \
                                      --context . \
                                      --dockerfile Dockerfile \
                                      --destination=${DOCKER_REGISTRY}/${img.name}:latest \
                                      --skip-tls-verify
                                """
                            }
                        }
                    }
                }
            }
        }
    }

    post {
        success {
            echo '✅ Pipeline complet exécuté avec succès.'
        }
        failure {
            echo '❌ Le pipeline a échoué. Vérifiez les logs Jenkins.'
        }
    }
}
