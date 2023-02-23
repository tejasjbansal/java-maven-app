#!/usr/bin/env groovy

library identifier: 'Jenkins_shared_Lib@main', retriever: modernSCM(
        [$class: 'GitSCMSource',
         remote: 'https://github.com/tejasjbansal/Jenkins_shared_Lib.git',
         credentialsId: 'Github-id'
        ]
)


def gv

pipeline {
    agent any
    tools {
        maven 'maven-3.9'
    }
    stages {
        stage("init") {
            steps {
                script {
                    gv = load "script.groovy"
                }
            }
        }
        stage("build jar") {
            steps {
                script {
                    buildJar()
                }
            }
        }
        stage("build and push image") {
            steps {
                script {
//                     buildImage 'tejashbansal/my-repo:jma-3.0'
//                     dockerLogin()
//                     dockerPush 'tejashbansal/my-repo:jma-3.0'
                }
            }
        }
        stage("deploy") {
            steps {
                script {
                    gv.deployApp()
                }
            }
        }
    }
}
