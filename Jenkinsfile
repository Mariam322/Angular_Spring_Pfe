pipeline {
     agent {
        label 'my-jenkins-jenkins-agent'
    }

    tools {
        jdk 'jdk-21'
        maven 'maven-3.8.6'
    }

    environment {
        DOCKER_REGISTRY = 'mariammseddi12'
        JAVA_HOME = '/opt/java/openjdk' 
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
                    dir('EurekaCompain') { bat 'mvn clean package -DskipTests' }
                }
            }
        }

        stage('Build Gateway') {
            steps {
                withMaven(maven: 'maven-3.8.6') {
                    dir('Gatway') { bat 'mvn clean package -DskipTests' }
                }
            }
        }

        stage('Build Compain Service') {
            steps {
                withMaven(maven: 'maven-3.8.6') {
                    dir('ProjetCompain') { bat 'mvn clean package -DskipTests' }
                }
            }
        }

        stage('Build Facturation Service') {
            steps {
                withMaven(maven: 'maven-3.8.6') {
                    dir('Facturation') { bat 'mvn clean package -DskipTests' }
                }
            }
        }

        stage('Build Depense Service') {
            steps {
                withMaven(maven: 'maven-3.8.6') {
                    dir('Depense') { bat 'mvn clean package -DskipTests' }
                }
            }
        }

        stage('Build Bank Service') {
            steps {
                withMaven(maven: 'maven-3.8.6') {
                    dir('BanqueService') { bat 'mvn clean package -DskipTests' }
                }
            }
        }

        stage('Build ReglementAffectation Service') {
            steps {
                withMaven(maven: 'maven-3.8.6') {
                    dir('ReglementAffectation') { bat 'mvn clean package -DskipTests' }
                }
            }
        }

        stage('Build Documents Service') {
            steps {
                withMaven(maven: 'maven-3.8.6') {
                    dir('Documents') { bat 'mvn clean package -DskipTests' }
                }
            }
        }

        // ---------- Frontend Build ----------
        stage('Build Angular Frontend') {
            steps {
                dir('Front/WebFront') {
                    bat 'npm config set legacy-peer-deps true'
                    bat 'npm install'
                    bat 'npm run build -- --configuration=production'
                }
            }
        }

        // ---------- Docker Images ----------
        stage('Build Eureka Image') {
            steps { dir('EurekaCompain') { bat "docker build -t ${DOCKER_REGISTRY}/eureka-server ." } }
        }

        stage('Build Gateway Image') {
            steps { dir('Gatway') { bat "docker build -t ${DOCKER_REGISTRY}/gateway-service ." } }
        }

        stage('Build Compain Image') {
            steps { dir('ProjetCompain') { bat "docker build -t ${DOCKER_REGISTRY}/compain-service ." } }
        }

        stage('Build Facturation Image') {
            steps { dir('Facturation') { bat "docker build -t ${DOCKER_REGISTRY}/facturation-service ." } }
        }

        stage('Build Depense Image') {
            steps { dir('Depense') { bat "docker build -t ${DOCKER_REGISTRY}/depense-service ." } }
        }

        stage('Build Bank Image') {
            steps { dir('BanqueService') { bat "docker build -t ${DOCKER_REGISTRY}/bank-service ." } }
        }

        stage('Build ReglementAffectation Image') {
            steps { dir('ReglementAffectation') { bat "docker build -t ${DOCKER_REGISTRY}/reglementaffectation-service ." } }
        }

        stage('Build Documents Image') {
            steps { dir('Documents') { bat "docker build -t ${DOCKER_REGISTRY}/document-service ." } }
        }

        stage('Build Angular Frontend Image') {
            steps { dir('Front/WebFront') { bat "docker build -t ${DOCKER_REGISTRY}/angular-frontend ." } }
        }

        // ---------- Push Docker Images ----------
        stage('Push Docker Images') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'DockerHub', passwordVariable: 'DockerHubPassword', usernameVariable: 'DockerHubUsername')]) {
                    bat "docker login -u ${DockerHubUsername} -p ${DockerHubPassword}"
                    bat "docker push ${DOCKER_REGISTRY}/eureka-server"
                    bat "docker push ${DOCKER_REGISTRY}/gateway-service"
                    bat "docker push ${DOCKER_REGISTRY}/compain-service"
                    bat "docker push ${DOCKER_REGISTRY}/facturation-service"
                    bat "docker push ${DOCKER_REGISTRY}/depense-service"
                    bat "docker push ${DOCKER_REGISTRY}/bank-service"
                    bat "docker push ${DOCKER_REGISTRY}/reglementaffectation-service"
                    bat "docker push ${DOCKER_REGISTRY}/document-service"
                    bat "docker push ${DOCKER_REGISTRY}/angular-frontend"
                }
            }
        }

        // ---------- Deploy to Kubernetes ----------
        stage('Deploy to OVH Kubernetes') {
            steps {
                script {
                    withKubeConfig([credentialsId: 'kubernetes-credentials-id']) {
                        bat """
                            kubectl create namespace ${K8S_NAMESPACE} --dry-run=client -o yaml | kubectl apply -f -
                            kubectl apply -f kubernetes/01-eureka.yaml -n ${K8S_NAMESPACE}
                            timeout /t 20 /nobreak
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
                            bat "kubectl rollout status deployment/${service} -n ${K8S_NAMESPACE} --timeout=300s"
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
