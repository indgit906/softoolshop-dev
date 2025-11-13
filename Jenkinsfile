pipeline {
    agent any
    environment {
        DOCKER_HUB_USER = 'inddocker786'
        IMAGE_NAME = 'softoolshop-dev'
    }
    stages {
        stage('Checkout SCM') {
            steps { checkout scm }
        }
        stage('Build Docker Image') {
            steps {
                sh 'docker build -t $DOCKER_HUB_USER/$IMAGE_NAME:latest .'
            }
        }
        stage('Push to Docker Hub') {
            steps {
                withCredentials([string(credentialsId: 'dockerhub-token', variable: 'DOCKER_HUB_TOKEN')]) {
                    sh '''
                    echo $DOCKER_HUB_TOKEN | docker login -u $DOCKER_HUB_USER --password-stdin
                    docker push $DOCKER_HUB_USER/$IMAGE_NAME:latest
                    '''
                }
            }
        }
        stage('Deploy to Kubernetes') {
            steps {
                sh '''
                kubectl apply -f k8s/deployment.yaml
                kubectl rollout restart deployment softoolshop
                '''
            }
        }
    }
}
