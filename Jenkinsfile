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
    image: docker.io/kaniko-project/executor:v1.23.2-debug
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

    tools {
        jdk 'jdk-17'
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
                            grep -o '"auths"' /kaniko/.docker/config.json || true
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
                                      --insecure \
                                      --skip-tls-verify
                                """
                            }
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
                            echo "🚀 Déploiement dans ${K8S_NAMESPACE}"
                            kubectl create namespace ${K8S_NAMESPACE} --dry-run=client -o yaml | kubectl apply -f -
                            for f in kubernetes/*.yaml; do
                                echo "📦 Déploiement de $f"
                                kubectl apply -f $f -n ${K8S_NAMESPACE}
                            done
                        """
                        def apps = [
                            'eureka-server','gateway-service','compain-service',
                            'facturation-service','depense-service','bank-service',
                            'reglementaffectation-service','angular-frontend'
                        ]
                        apps.each { app ->
                            sh "kubectl rollout status deployment/${app} -n ${K8S_NAMESPACE} --timeout=300s || true"
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
