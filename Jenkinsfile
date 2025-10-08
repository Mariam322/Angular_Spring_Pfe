pipeline {
    agent {
        kubernetes {
            label 'kaniko-agent'
            yaml """
apiVersion: v1
kind: Pod
spec:
  serviceAccountName: jenkins
  containers:
  - name: maven
    image: maven:3.9.9-eclipse-temurin-17
    command:
    - cat
    tty: true
  - name: node
    image: node:20
    command:
    - cat
    tty: true
  - name: kaniko
    # ✅ Image Kaniko officielle depuis Docker Hub (plus fiable que gcr.io)
    image: docker.io/kaniko-project/executor:v1.23.2-debug
    command:
    - sh
    - -c
    - "while true; do sleep 3600; done"
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

        /* === 1️⃣ Checkout Code === */
        stage('Checkout Code') {
            steps {
                git url: 'https://github.com/Mariam322/Angular_Spring_Pfe.git', branch: 'main'
            }
        }

        /* === 2️⃣ Build Backend Services === */
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

        /* === 3️⃣ Build Angular Frontend === */
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

        /* === 4️⃣ Vérifier le secret Docker Hub === */
        stage('Vérifier le secret Docker Hub') {
            steps {
                container('kaniko') {
                    sh '''
                        echo "🔍 Vérification du fichier d’authentification Kaniko..."
                        if [ -f /kaniko/.docker/config.json ]; then
                            echo "✅ Le secret regcred est bien monté."
                            cat /kaniko/.docker/config.json | grep -o '"auths"' || true
                        else
                            echo "❌ ERREUR : le secret regcred n’est pas monté !"
                            exit 1
                        fi
                    '''
                }
            }
        }

        /* === 5️⃣ Build & Push Docker Images === */
        stage('Build & Push Docker Images (Kaniko)') {
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

        /* === 6️⃣ Déploiement Kubernetes === */
        stage('Deploy to OVH Kubernetes') {
            steps {
                script {
                    withKubeConfig([credentialsId: 'kubernetes-credentials-id']) {
                        sh """
                            echo "🚀 Déploiement des manifests dans ${K8S_NAMESPACE}"
                            kubectl create namespace ${K8S_NAMESPACE} --dry-run=client -o yaml | kubectl apply -f -
                            kubectl apply -f kubernetes/eureka.yaml -n ${K8S_NAMESPACE}
                            sleep 20
                            kubectl apply -f kubernetes/gateway.yaml -n ${K8S_NAMESPACE}
                            kubectl apply -f kubernetes/compain-service.yaml -n ${K8S_NAMESPACE}
                            kubectl apply -f kubernetes/facturation-service.yaml -n ${K8S_NAMESPACE}
                            kubectl apply -f kubernetes/depense-service.yaml -n ${K8S_NAMESPACE}
                            kubectl apply -f kubernetes/bank-service.yaml -n ${K8S_NAMESPACE}
                            kubectl apply -f kubernetes/reglementaffectation-service.yaml -n ${K8S_NAMESPACE}
                            kubectl apply -f kubernetes/frontend.yaml -n ${K8S_NAMESPACE}
                        """

                        def apps = [
                            'eureka-server','gateway-service','compain-service',
                            'facturation-service','depense-service','bank-service',
                            'reglementaffectation-service','angular-frontend'
                        ]
                        apps.each { app ->
                            sh "kubectl rollout status deployment/${app} -n ${K8S_NAMESPACE} --timeout=300s"
                        }
                    }
                }
            }
        }
    }

    /* === 7️⃣ Post Actions === */
    post {
        success {
            echo '✅ Pipeline complet (backend + frontend) terminé avec succès !'
        }
        failure {
            echo '❌ Le pipeline a échoué ; vérifier les logs Jenkins.'
        }
    }
}
