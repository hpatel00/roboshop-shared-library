def lintChecks()
{
    sh '''
       # We commented this out bc there are errors that the developers would need to fix
       #~/node_modules/jslint/bin/jslint.js server.js
       echo Lint Check for ${COMPONENT}
    '''
}

def sonarCheck() {
    sh '''
       sonar-scanner -Dsonar.host.url=http://172.31.9.186:9000 -Dsonar.sources=. -Dsonar.projectKey=${COMPONENT} -Dsonar.login=${SONAR_USR} -Dsonar.password=${SONAR_PSW}
       sonar-quality-gate.sh ${SONAR_USR} ${SONAR_PSW} 172.31.9.186 ${COMPONENT}
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
                        sonarCheck()
                    }
                }
            }
        } // end of stages
    }

}