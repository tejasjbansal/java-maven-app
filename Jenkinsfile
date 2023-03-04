#!/usr/bin/env groovy

library identifier: 'Jenkins_shared_Lib@main', retriever: modernSCM(
        [$class: 'GitSCMSource',
         remote: 'https://github.com/tejasjbansal/Jenkins_shared_Lib.git',
         credentialsId: 'Github-id'
        ]
)



pipeline {
    agent any
    tools {
        maven 'maven-3.9'
    }
    environment {
        IMAGE_NAME = 'tejashbansal/my-repo:jma-5.0'
    }
    stages {
        stage('build app') {
            steps {
               script {
                  echo 'building application jar...'
                  buildJar()
               }
            }
        }
        stage('build image') {
            steps {
                script {
                   echo 'building docker image...'
                   buildImage(env.IMAGE_NAME)
                   dockerLogin()
                   dockerPush(env.IMAGE_NAME)
                }
            }
        }
        stage('deploy') {
            steps {
                script {
                   echo 'deploying docker image to EC2...'
                   def dockerCmd = 'docker run -p 8080:8080 -d ${IMAGE_NAME}'
                //    def shellCmd = "bash ./server-cmds.sh ${IMAGE_NAME}"
                //    def ec2Instance = "ec2-user@34.205.65.241"

                   sshagent(['ec2-server-key']) {
//                        sh "scp -o StrictHostKeyChecking=no server-cmds.sh ${ec2Instance}:/home/ec2-user"
//                        sh "scp -o StrictHostKeyChecking=no docker-compose.yaml ${ec2Instance}:/home/ec2-user"
//                        sh "ssh -o StrictHostKeyChecking=no ${ec2Instance} ${shellCmd}"
                          sh "ssh -o StrictHostKeyChecking=no ec2-user@34.205.65.241 ${dockerCmd}"
                   }
                }
            }
        }
    }
}
