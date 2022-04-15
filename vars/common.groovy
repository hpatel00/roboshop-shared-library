def lintChecks() {
    stage{
        if (env.APP_TYPE="nodejs") {
            sh '''
               # We commented this out bc there are errors that the developers would need to fix
               #~/node_modules/jslint/bin/jslint.js server.js
               echo Lint Check for ${COMPONENT}
            '''
        }
        else if (env.APP_TYPE="maven") {
            sh '''
               # We commented this out bc there are errors that the developers would need to fix
               #~/node_modules/jslint/bin/jslint.js server.js
               #mvn checkstyle:check
               echo Lint Check for ${COMPONENT}
            '''
        }
        else if (env.APP_TYPE="python") {
            sh '''
               # We commented this out bc there are errors that the developers would need to fix
               #~/node_modules/jslint/bin/jslint.js server.js
               #pylint *.py
               echo Lint Check for ${COMPONENT}
            '''
        }
        else if (env.APP_TYPE="golang") {
            sh '''
               # We commented this out bc there are errors that the developers would need to fix
               #~/node_modules/jslint/bin/jslint.js server.js
               echo Lint Check for ${COMPONENT}
            '''
        }
    }
}

def sonarCheck() {
    sh '''
       #sonar-scanner -Dsonar.host.url=http://172.31.9.186:9000 -Dsonar.projectKey=${COMPONENT} -Dsonar.login=${SONAR_USR} -Dsonar.password=${SONAR_PSW} ${ARGS}
       #sonar-quality-gate.sh ${SONAR_USR} ${SONAR_PSW} 172.31.9.186 ${COMPONENT}
       echo Sonar Checks for ${COMPONENT}
    '''
}

def testCases() {
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
}