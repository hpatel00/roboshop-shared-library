def call() {
    node{
        sh 'rm -rf *'
        git branch: 'main', url: "https://github.com/hpatel00/${COMPONENT}.git"
        env.APP_TYPE="nodejs"
        common.lintChecks()
        env.ARGS="-Dsonar.sources=."
        common.sonarCheck()
        common.testCases()

        if (env.TAG_NAME != null) {
            common.artifacts()
        }
    }
}
