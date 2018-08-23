#!/usr/bin/env groovy

import java.text.SimpleDateFormat

def buildNumber = BUILD_NUMBER
def buildDateStr = new SimpleDateFormat("yyyy-MM-dd-HH-mm").format(new Date())
def buildTag = "${buildDateStr}-${buildNumber}"

println "\n Job name: ${JOB_NAME}\n Build date: ${buildDateStr}\n Build number: ${buildNumber}\n Build tag: ${buildTag}"

pipeline {
    agent {
        label "ods-dev"
    }

    stages {
        stage('Commit Stage') {
            steps {
                sh "./gradlew assemble"
            }
        }

        stage('Acceptance Stage: Start Docker Container') {
            steps {
                sh "docker-compose -f docker/docker-compose.yml up -d"
            }
        }

        stage('Acceptance Stage') {
            steps {
                parallel(
                        'run unit tests': {
                            sh "./gradlew test"
                        },
                        'run integration tests': {
                            sh "./gradlew integrationTest"
                        }
                )
            }
        }
    }

    post {
        always {
            junit '*/build/test-results/**/*.xml'
            sh "docker-compose -f docker/docker-compose.yml stop"
            deleteDir()
        }
    }
}

