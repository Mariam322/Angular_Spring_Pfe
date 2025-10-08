pipeline {
    agent {
        kubernetes {
            label 'kaniko-agent'
            yaml """
apiVersion: v1
kind: Pod
spec:
  serviceAccountName: jenkins
  imagePullSecrets:
    - name: regcred
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
    image: gcr.io/kaniko-project/executor:latest
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
        stage('Checkout Code') {
            steps {
                echo "📦 Clonage du dépôt Git..."
                git url: 'https://github.com/Mariam322/Angular_Spring_Pfe.git', branch: 'main'
            }
        }

        stage('Build Backend Services') {
            steps {
                container('maven') {
                    script {
                        def services = [
                            "EurekaCompain",
                            "Gatway",
                            "ProjetCompain",
                            "Facturation",
                            "Depense",
                            "BanqueService",
                            "ReglementAffectation"
                        ]
                        for (svc in services) {
                            dir(svc) {
                                echo "🏗️ Compilation du backend : ${svc}"
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
                        echo "⚙️ Installation des dépendances Angular..."
                        sh '''
                            npm config set legacy-peer-deps true
                            npm install
                            npm install @popperjs/core --save
                            npx ng build --configuration=production --source-map=false
                        '''
                        echo "✅ Build Angular terminé avec succès."
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
                                echo "🚀 Construction et push de l’image Docker : ${img.name}"
                                sh """
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
            echo '✅ Pipeline exécuté avec succès.'
        }
        failure {
            echo '❌ Le pipeline a échoué. Consultez les logs Jenkins pour les détails.'
        }
        always {
            echo '🏁 Fin du pipeline.'
        }
    }
}
