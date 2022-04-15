env.APP_TYPE="golang"
node{
    stage('Lint Check') {
        common.lintChecks()
    }
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
        } // end of stages
    }

}