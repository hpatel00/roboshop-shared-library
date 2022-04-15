def call() {
    node{
        env.APP_TYPE="nodejs"
        common.lintChecks()
        env.ARGS="-Dsonar.sources=."
        common.sonarCheck()
        common.testCases()
    }
}

//        environment {
//            SONAR = credentials('SONAR')
//            NEXUS = credentials('NEXUS')
//        }
//
//
//
//            stage('Check the Release') {
//                when{
//                    expression { env.TAG_NAME != null }
//                }
//                steps{
//                    script{
//                        env.UPLOAD_STATUS=sh(returnStdout: true, script: "curl -s -L http://172.31.11.185:8081/service/rest/repository/browse/${COMPONENT} | grep ${COMPONENT}-${TAG_NAME}.zip || true")
//                        print UPLOAD_STATUS
//                    }
//                }
//            }
//
//            stage('Prepare Artifacts') {
//                when{
//                    expression { env.TAG_NAME != null }
//                    expression {env.UPLOAD_STATUS == ""}
//                }
//                steps{
//                    sh '''
//                        npm install
//                        zip -r ${COMPONENT}-${TAG_NAME}.zip node_modules server.js
//                        curl -v -f -u ${NEXUS_USR}:${NEXUS_PSW} --upload-file ${COMPONENT}-${TAG_NAME}.zip http://172.31.11.185:8081/repository/${COMPONENT}/${COMPONENT}-${TAG_NAME}.zip
//                    '''
//                }
//            }
//
//            stage('Package Artifacts') {
//                when{
//                    expression { env.TAG_NAME != null }
//                    expression {env.UPLOAD_STATUS == ""}
//                }
//                steps{
//                    sh 'echo'
//                }
//            }
//        } // end of stages
//    }
//}