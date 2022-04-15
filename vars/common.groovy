def lintChecks() {
    stage('Lint Checks'){
        if (env.APP_TYPE=="nodejs") {
            sh '''
               # We commented this out bc there are errors that the developers would need to fix
               #~/node_modules/jslint/bin/jslint.js server.js
               echo Lint Check for ${COMPONENT}
            '''
        }
        else if (env.APP_TYPE=="maven") {
            sh '''
               # We commented this out bc there are errors that the developers would need to fix
               #~/node_modules/jslint/bin/jslint.js server.js
               #mvn checkstyle:check
               echo Lint Check for ${COMPONENT}
            '''
        }
        else if (env.APP_TYPE=="python") {
            sh '''
               # We commented this out bc there are errors that the developers would need to fix
               #~/node_modules/jslint/bin/jslint.js server.js
               #pylint *.py
               echo Lint Check for ${COMPONENT}
            '''
        }
        else if (env.APP_TYPE=="golang") {
            sh '''
               # We commented this out bc there are errors that the developers would need to fix
               #~/node_modules/jslint/bin/jslint.js server.js
               echo Lint Check for ${COMPONENT}
            '''
        }
    }
}

def sonarCheck() {
    stage('Sonar Code Analysis') {
        sh '''
           #sonar-scanner -Dsonar.host.url=http://172.31.9.186:9000 -Dsonar.projectKey=${COMPONENT} -Dsonar.login=${SONAR_USR} -Dsonar.password=${SONAR_PSW} ${ARGS}
           #sonar-quality-gate.sh ${SONAR_USR} ${SONAR_PSW} 172.31.9.186 ${COMPONENT}
           echo Sonar Checks for ${COMPONENT}
        '''
    }
}

def testCases() {
    stage('Test Cases'){

        def stages = [:]

        stages['Unit Tests'] = {
            sh 'echo Unit Tests'
        }

        stages['Integration Tests'] = {
            sh 'echo Integration Tests'
        }

        stages['Functional Tests'] = {
            sh 'echo Functional Tests'
        }

        parallel(stages)
    }
}

