#!/usr/bin/env groovy

def gv

pipeline {
    agent any
    parameters {
        choice(name: 'VERSION', choices: ['1.1.0', '1.2.0', '1.3.0'], description: '')
        booleanParam(name: 'executeTests', defaultValue: true, description: '')
    }
    stages {
        stage("init") {
            steps {
                script {
                    echo "Init the application..."
//                    gv = load "script.groovy"
                }
            }
        }
        stage("build") {
            steps {
                script {
                    echo "Building the application..."
//                     gv.buildApp()
                }
            }
        }
        stage("test") {
            when {
                expression {
                    params.executeTests
                }
            }
            steps {
                script {
                    echo "testing the application..."
//                     gv.testApp()
                }
            }
        }
        stage("deploy") {
            steps {
                script {
                    env.ENV = input message: "Select the environment to deploy to", ok: "Done", parameters: [choice(name: 'ONE', choices: ['dev', 'staging', 'prod'], description: '')]
                    echo "Deploying the application..."
//                     gv.deployApp()
                    echo "Deploying to ${ENV}"
                }
            }
        }
    }
}
