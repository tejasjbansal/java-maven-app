pipeline {
    agent any
    tools {
        maven 'Maven'
    }
    environment {
        NEW_VERSION = '1.3.0'
        SERVER_CREDENTIALS = credentials('server-credentials')
    }
    stages {
        stage('build') {
            steps {
                script {
                    echo "Building the application..."
                    echo "Building version ${NEW_VERSION}"
                }
            }
        }
        stage('test') {
            steps {
                script {
                    echo "Testing the application..."
                }
            }
        }
        stage('deploy') {
            steps {
                script {
                    echo "Deploying the application..."
                    echo "deploying with ${SERVER_CREDENTIALS}"
                }
            }
        }
    }
}
