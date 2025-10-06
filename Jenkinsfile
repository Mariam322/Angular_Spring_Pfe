pipeline {
    agent any

    tools {
        jdk 'jdk-17'
        maven 'maven-3.8.6'
    }

    environment {
        DOCKER_REGISTRY = 'mariammseddi12'
        K8S_NAMESPACE = 'default'
        JENKINS_NOOP = "true"
        JENKINS_OPTS = "-Dorg.jenkinsci.plugins.durabletask.BourneShellScript.HEARTBEAT_CHECK_INTERVAL=300"
    }

    stages {
        stage('Checkout Code') {
            steps {
                git url: 'https://github.com/Mariam322/Angular_Spring_Pfe.git', branch: 'main'
            }
        }

        // ---------- Backend Build ----------
        stage('Build Eureka') {
            steps {
                withMaven(maven: 'maven-3.8.6') {
                    dir('EurekaCompain') { sh 'mvn clean package -DskipTests' }
                }
            }
        }

        stage('Build Gateway') {
            steps {
                withMaven(maven: 'maven-3.8.6') {
                    dir('Gatway') { sh 'mvn clean package -DskipTests' }
                }
            }
        }

        stage('Build Compain Service') {
            steps {
                withMaven(maven: 'maven-3.8.6') {
                    dir('ProjetCompain') { sh 'mvn clean package -DskipTests' }
                }
            }
        }

        stage('Build Facturation Service') {
            steps {
                withMaven(maven: 'maven-3.8.6') {
                    dir('Facturation') { sh 'mvn clean package -DskipTests' }
                }
            }
        }

        stage('Build Depense Service') {
            steps {
                withMaven(maven: 'maven-3.8.6') {
                    dir('Depense') { sh 'mvn clean package -DskipTests' }
                }
            }
        }

        stage('Build Bank Service') {
            steps {
                withMaven(maven: 'maven-3.8.6') {
                    dir('BanqueService') { sh 'mvn clean package -DskipTests' }
                }
            }
        }

        stage('Build ReglementAffectation Service') {
            steps {
                withMaven(maven: 'maven-3.8.6') {
                    dir('ReglementAffectation') { sh 'mvn clean package -DskipTests' }
                }
            }
        }

        stage('Build Documents Service') {
            steps {
                withMaven(maven: 'maven-3.8.6') {
                    dir('Documents') { sh 'mvn clean package -DskipTests' }
                }
            }
        }

        // ---------- Frontend Build ----------
        stage('Build Angular Frontend') {
            steps {
                dir('Front/WebFront') {
                    sh 'npm config set legacy-peer-deps true'
                    sh 'npm install'
                    sh 'npm run build -- --configuration=production'
                }
            }
        }

        // ---------- Docker Images ----------
        stage('Build Eureka Image') {
            steps { dir('EurekaCompain') { sh "docker build -t ${DOCKER_REGISTRY}/eureka-server ." } }
        }

        stage('Build Gateway Image') {
            steps { dir('Gatway') { sh "docker build -t ${DOCKER_REGISTRY}/gateway-service ." } }
        }

        stage('Build Compain Image') {
            steps { dir('ProjetCompain') { sh "docker build -t ${DOCKER_REGISTRY}/compain-service ." } }
        }

        stage('Build Facturation Image') {
            steps { dir('Facturation') { sh "docker build -t ${DOCKER_REGISTRY}/facturation-service ." } }
        }

        stage('Build Depense Image') {
            steps { dir('Depense') { sh "docker build -t ${DOCKER_REGISTRY}/depense-service ." } }
        }

        stage('Build Bank Image') {
            steps { dir('BanqueService') { sh "docker build -t ${DOCKER_REGISTRY}/bank-service ." } }
        }

        stage('Build ReglementAffectation Image') {
            steps { dir('ReglementAffectation') { sh "docker build -t ${DOCKER_REGISTRY}/reglementaffectation-service ." } }
        }

        stage('Build Documents Image') {
            steps { dir('Documents') { sh "docker build -t ${DOCKER_REGISTRY}/document-service ." } }
        }

        stage('Build Angular Frontend Image') {
            steps { dir('Front/WebFront') { sh "docker build -t ${DOCKER_REGISTRY}/angular-frontend ." } }
        }

        // ---------- Push Docker Images ----------
        stage('Push Docker Images') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'DockerHub', passwordVariable: 'DockerHubPassword', usernameVariable: 'DockerHubUsername')]) {
                    sh "docker login -u ${DockerHubUsername} -p ${DockerHubPassword}"
                    sh "docker push ${DOCKER_REGISTRY}/eureka-server"
                    sh "docker push ${DOCKER_REGISTRY}/gateway-service"
                    sh "docker push ${DOCKER_REGISTRY}/compain-service"
                    sh "docker push ${DOCKER_REGISTRY}/facturation-service"
                    sh "docker push ${DOCKER_REGISTRY}/depense-service"
                    sh "docker push ${DOCKER_REGISTRY}/bank-service"
                    sh "docker push ${DOCKER_REGISTRY}/reglementaffectation-service"
                    sh "docker push ${DOCKER_REGISTRY}/document-service"
                    sh "docker push ${DOCKER_REGISTRY}/angular-frontend"
                }
            }
        }

        // ---------- Deploy to Kubernetes ----------
        stage('Deploy to OVH Kubernetes') {
            steps {
                script {
                    withKubeConfig([credentialsId: 'kubernetes-credentials-id']) {
                        sh """
                            kubectl create namespace ${K8S_NAMESPACE} --dry-run=client -o yaml | kubectl apply -f -
                            kubectl apply -f kubernetes/01-eureka.yaml -n ${K8S_NAMESPACE}
                            sleep 20
                            kubectl apply -f kubernetes/gateway.yaml -n ${K8S_NAMESPACE}
                            kubectl apply -f kubernetes/compain-service.yaml -n ${K8S_NAMESPACE}
                            kubectl apply -f kubernetes/facturation-service.yaml -n ${K8S_NAMESPACE}
                            kubectl apply -f kubernetes/depense-service.yaml -n ${K8S_NAMESPACE}
                            kubectl apply -f kubernetes/06-bank-service.yaml -n ${K8S_NAMESPACE}
                            kubectl apply -f kubernetes/07-reglementaffectation-service.yaml -n ${K8S_NAMESPACE}
                            kubectl apply -f kubernetes/08-document-service.yaml -n ${K8S_NAMESPACE}
                            kubectl apply -f kubernetes/frontend.yaml -n ${K8S_NAMESPACE}
                        """

                        // Vérification rollout
                        def services = [
                            'eureka-server', 'gateway-service', 'compain-service',
                            'facturation-service', 'depense-service', 'bank-service',
                            'reglementaffectation-service', 'document-service', 'angular-frontend'
                        ]
                        services.each { service ->
                            sh "kubectl rollout status deployment/${service} -n ${K8S_NAMESPACE} --timeout=300s"
                        }
                    }
                }
            }
        }
    }

    post {
        success { echo '✅ Pipeline complet (backend + frontend) terminé avec succès !' }
        failure { echo '❌ Le pipeline a échoué, vérifier les logs Jenkins.' }
    }
}
