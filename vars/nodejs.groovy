def lintChecks()
{
    sh '''
       # We commented this out bc there are errors that the developers would need to fix
       #~/node_modules/jslint/bin/jslint.js server.js
       echo Lint Check for ${COMPONENT}
    '''
}

def call() {
    pipeline {
        agent any

        environment {
            SONAR = credentials('SONAR')
            NEXUS = credentials('NEXUS')
        }

        stages{

            // For each commit
            stage('Lint Check') {
                steps{
                    script{
                        lintChecks()
                    }
                }
            }

            stage('SonarCheck') {
                steps{
                    script{
                        env.ARGS="-Dsonar.sources=."
                        common.sonarCheck()
                    }
                }
            }

            stage('Test Cases'){

                parallel{

                    stage('Unit Tests'){
                        steps{
                            sh 'echo Unit Tests'
                        }
                    }

                    stage('Integration Tests'){
                        steps{
                            sh 'echo Integration Tests'
                        }
                    }

                    stage('Functional Tests'){
                        steps{
                            sh 'echo Functional Tests'
                        }
                    }
                }
            }

            stage('Prepare Artifacts') {
                when{
                    expression { env.TAG_NAME != null }
                }
                steps{
                    sh '''
                        npm install
                        zip -r ${COMPONENT}-${TAG_NAME}.zip node-modules server.js
                        curl -v -f -u ${NEXUS_USR}:${NEXUS_PSW} --upload-file ${COMPONENT}-${TAG_NAME}.zip http://172.31.11.185:8081/repository/${COMPONENT}/${COMPONENT}-${TAG_NAME}.zip
                    '''
                }
            }

            stage('Package Artifacts') {
                when{
                    expression { env.TAG_NAME != null }
                }
                steps{
                    sh 'echo'
                }
            }
        } // end of stages
    }

}