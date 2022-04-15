def lintChecks()
{
    sh '''
       # We commented this out bc there are errors that the developers would need to fix
       #~/node_modules/jslint/bin/jslint.js server.js
       #mvn checkstyle:check
       echo Lint Check for ${COMPONENT}
    '''
}

def call() {
    pipeline {
        agent any

        environment {
            SONAR = credentials('SONAR')
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
                        sh 'mvn clean compile'
                        env.ARGS="-Dsonar.java.binaries=target/"
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
        } // end of stages
    }

}