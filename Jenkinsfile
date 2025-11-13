pipeline {
    agent any

    environment {
        IMAGE_NAME = "softoolshop"
    }

    stages {
        stage('Checkout SCM') {
            steps {
                checkout scm
            }
        }

        stage('Build JAR') {
            steps {
                script {
                    docker.image('maven:3.9.6-eclipse-temurin-17').inside('-v $HOME/.m2:/root/.m2') {
                        sh 'mvn clean package -DskipTests'
                        sh 'mv target/*.jar target/backend.jar'
                    }
                }
            }
        }

        stage('Build & Push Docker Image') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'dockerhub-password',
                                                     usernameVariable: 'DOCKER_HUB_USER',
                                                     passwordVariable: 'DOCKER_HUB_PASS')]) {
                        sh '''
                            echo "${DOCKER_HUB_PASS}" | docker login -u "${DOCKER_HUB_USER}" --password-stdin
                            docker build -t ${DOCKER_HUB_USER}/${IMAGE_NAME}:latest .
                            docker push ${DOCKER_HUB_USER}/${IMAGE_NAME}:latest
                        '''
                    }
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                sh 'kubectl apply -f deployment.yaml'
            }
        }
    }
}

