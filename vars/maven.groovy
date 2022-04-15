def call() {
    node{
        sh 'rm -rf *'
        git branch: 'main', url: "https://github.com/hpatel00/${COMPONENT}.git"
        env.APP_TYPE="maven"
        common.lintChecks()
        sh 'mvn clean compile'
        env.ARGS="-Dsonar.java.binaries=target/"
        common.sonarCheck()
        common.testCases()

        if (env.TAG_NAME != null) {
            common.artifacts()
        }
    }
}