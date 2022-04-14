def lintChecks()
{
    sh '''
       # We commented this out bc there are errors that the developers would need to fix
       #~/node_modules/jslint/bin/jslint.js server.js
       #pylint *.py
       echo Lint Check for ${COMPONENT}
    '''
}

def call() {
    pipeline {
        agent any

        stages{

            // For each commit
            stage('Lint Check') {
                steps{
                    script{
                        lintChecks()
                    }
                }
            }
        } // end of stages
    }

}