pipeline {
    agent any

    tools {
        jdk 'jdk-21'
        maven 'maven-3.8.6'
    }

    environment {
        DOCKER_REGISTRY = 'mariammseddi12'
        JAVA_HOME = '/opt/java/openjdk' 
        K8S_NAMESPACE = 'microservice'
        JENKINS_NOOP = "true"
        JENKINS_OPTS = "-Dorg.jenkinsci.plugins.durabletask.BourneShellScript.HEARTBEAT_CHECK_INTERVAL=300"
    }

    stages {
        stage('Checkout Code') {
            steps {
                git url: 'https://github.com/Mariam322/Angular_Spring_Pfe.git', branch: 'main'
            }
        }

        stage('Build and Package Backend') {
            parallel {
                stage('Build Eureka') {
                    steps {
                        withMaven(maven: 'maven-3.8.6') {
                            dir('EurekaCompain') {  
                                sh 'mvn clean package -DskipTests'
                            }
                        }
                    }
                }
                stage('Build Gateway') {
                    steps {
                        withMaven(maven: 'maven-3.8.6') {
                            dir('Gatway') {  
                                sh 'mvn clean package -DskipTests'
                            }
                        }
                    }
                }
                stage('Build Compain Service') {
                    steps {
                        withMaven(maven: 'maven-3.8.6') {
                            dir('ProjetCompain') {  
                                sh 'mvn clean package -DskipTests'
                            }
                        }
                    }
                }
                stage('Build Facturation Service') {
                    steps {
                        withMaven(maven: 'maven-3.8.6') {
                            dir('Facturation') {  
                                sh 'mvn clean package -DskipTests'
                            }
                        }
                    }
                }
                stage('Build Depense Service') {
                    steps {
                        withMaven(maven: 'maven-3.8.6') {
                            dir('Depense') {  
                                sh 'mvn clean package -DskipTests'
                            }
                        }
                    }
                }
                stage('Build Bank Service') {
                    steps {
                        withMaven(maven: 'maven-3.8.6') {
                            dir('BanqueService') {  
                                sh 'mvn clean package -DskipTests'
                            }
                        }
                    }
                }
                stage('Build ReglementAffectation Service') {
                    steps {
                        withMaven(maven: 'maven-3.8.6') {
                            dir('ReglementAffectation') {  
                                sh 'mvn clean package -DskipTests'
                            }
                        }
                    }
                }
            }
        }

        // ---------- Frontend Angular ----------
        stage('Build Angular Frontend') {
            steps {
                dir('Front/WebFront') {
                    sh 'npm config set legacy-peer-deps true'
                    sh 'npm install'
                    sh 'npm run build -- --configuration=production'
                }
            }
        }

        stage('Build Docker Images') {
            parallel {
                stage('Build Backend Images') {
                    parallel {
                        stage('Eureka Image') { steps { dir('EurekaCompain') { sh "docker build -t ${DOCKER_REGISTRY}/eureka-server ." } } }
                        stage('Gateway Image') { steps { dir('Gatway') { sh "docker build -t ${DOCKER_REGISTRY}/gateway-service ." } } }
                        stage('Compain Image') { steps { dir('ProjetCompain') { sh "docker build -t ${DOCKER_REGISTRY}/compain-service ." } } }
                        stage('Facturation Image') { steps { dir('Facturation') { sh "docker build -t ${DOCKER_REGISTRY}/facturation-service ." } } }
                        stage('Depense Image') { steps { dir('Depense') { sh "docker build -t ${DOCKER_REGISTRY}/depense-service ." } } }
                        stage('Bank Image') { steps { dir('BanqueService') { sh "docker build -t ${DOCKER_REGISTRY}/bank-service ." } } }
                        stage('ReglementAffectation Image') { steps { dir('ReglementAffectation') { sh "docker build -t ${DOCKER_REGISTRY}/reglementaffectation-service ." } } }
                    }
                }

                // Frontend Docker
                stage('Build Frontend Image') {
                    steps {
                        dir('Front/WebFront') {
                            sh "docker build -t ${DOCKER_REGISTRY}/angular-frontend ."
                        }
                    }
                }
            }
        }

        stage('Push Docker Images') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'DockerHub', passwordVariable: 'DockerHubPassword', usernameVariable: 'DockerHubUsername')]) {
                    sh """
                        docker login -u ${DockerHubUsername} -p ${DockerHubPassword}
                        docker push ${DOCKER_REGISTRY}/eureka-server
                        docker push ${DOCKER_REGISTRY}/gateway-service
                        docker push ${DOCKER_REGISTRY}/compain-service
                        docker push ${DOCKER_REGISTRY}/facturation-service
                        docker push ${DOCKER_REGISTRY}/depense-service
                        docker push ${DOCKER_REGISTRY}/bank-service
                        docker push ${DOCKER_REGISTRY}/reglementaffectation-service
                        docker push ${DOCKER_REGISTRY}/angular-frontend
                    """
                }
            }
        }

        stage('Deploy to OVH Kubernetes') {
            steps {
                script {
                    withKubeConfig([credentialsId: 'ovh-kubernetes-credentials']) {
                        sh """
                            kubectl create namespace ${K8S_NAMESPACE} --dry-run=client -o yaml | kubectl apply -f -
                            kubectl apply -f kubernetes/01-eureka.yaml -n ${K8S_NAMESPACE}
                            kubectl apply -f kubernetes/gateway.yaml -n ${K8S_NAMESPACE}
                            kubectl apply -f kubernetes/compain-service.yaml -n ${K8S_NAMESPACE}
                            kubectl apply -f kubernetes/facturation-service.yaml -n ${K8S_NAMESPACE}
                            kubectl apply -f kubernetes/depense-service.yaml -n ${K8S_NAMESPACE}
                            kubectl apply -f kubernetes/06-bank-service.yaml -n ${K8S_NAMESPACE}
                            kubectl apply -f kubernetes/07-reglementaffectation-service.yaml -n ${K8S_NAMESPACE}
                            kubectl apply -f kubernetes/frontend.yaml -n ${K8S_NAMESPACE}

                            # Vérification rollout
                            for svc in eureka-server gateway-service compain-service facturation-service depense-service bank-service reglementaffectation-service angular-frontend; do
                                kubectl rollout status deployment/$svc -n ${K8S_NAMESPACE} --timeout=300s
                            done
                        """
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
