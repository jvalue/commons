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

        stage('Acceptance Stage: Start CouchDB Docker Container') {
            steps {
                sh "docker/couchdb/couchdb-start.sh"
                timeout(time: 2, unit: "MINUTES") {
                    echo "Waiting until couchdb is ready."
                    waitUntil {
                        script {
                            try {
                                sleep 1
                                sh 'wget -q http://localhost:5984/ -O /dev/null'
                                return true
                            } catch (exception) {
                                return false
                            }
                        }
                    }
                }
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
            sh "docker/couchdb/couchdb-stop.sh"
            deleteDir()
        }
    }
}

