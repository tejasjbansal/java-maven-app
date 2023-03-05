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
    // environment {
    //     IMAGE_NAME = 'tejashbansal/my-repo:jma-3.0'
    // }

    stages {
         stage('increment version') {
            steps {
                script {
                    echo 'incrementing app version...'
                    sh 'mvn build-helper:parse-version versions:set \
                        -DnewVersion=\\\${parsedVersion.majorVersion}.\\\${parsedVersion.minorVersion}.\\\${parsedVersion.nextIncrementalVersion} \
                        versions:commit'
                    def matcher = readFile('pom.xml') =~ '<version>(.+)</version>'
                    def version = matcher[0][1]
                    env.IMAGE_NAME = "$version-$BUILD_NUMBER"
                }
            }
        }
//         stage('build app') {
//             steps {
//                script {
//                   echo 'building application jar...'
//                   buildJar()
//                }
//             }
//         }
        stage('build app') {
            steps {
                script {
                    echo "building the application..."
                    sh 'mvn clean package'
                }
            }
        }
        stage('build image') {
            steps {
                script {
                   echo 'building docker image...'
                   env.IMAGE_NAME = "tejashbansal/my-repo:${IMAGE_NAME}"
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
                //    def dockerCmd = 'docker run -p 8080:8080 -d ${IMAGE_NAME}'
                   def shellCmd = "bash ./server-cmds.sh ${IMAGE_NAME}"
                   def ec2Instance = "ec2-user@34.205.65.241"

                   sshagent(['ec2-server-key']) {
                       sh "scp -o StrictHostKeyChecking=no server-cmds.sh ${ec2Instance}:/home/ec2-user"
                       sh "scp -o StrictHostKeyChecking=no docker-compose.yaml ${ec2Instance}:/home/ec2-user"
                       sh "ssh -o StrictHostKeyChecking=no ${ec2Instance} ${shellCmd}"
                        //   sh "ssh -o StrictHostKeyChecking=no ec2-user@34.205.65.241 ${dockerCmd}"
                   }
                }
            }
        }
         stage('commit version update') {
            steps {
                script {
                    withCredentials([string(credentialsId: 'Jenkins-webhook',variable: 'token')]) {
                        // git config here for the first time run
                        sh 'git config --global user.email "jenkins@example.com"'
                        sh 'git config --global user.name "jenkins"'

                        sh "git remote set-url origin https://${token}@github.com/tejasjbansal/java-maven-app.git"
                        sh 'git add .'
                        sh 'git commit -m "ci: version bump"'
                        sh 'git push origin HEAD:feature/jenkinsfile-sshagent'
                    }
                }
            }
        }
    }
}
