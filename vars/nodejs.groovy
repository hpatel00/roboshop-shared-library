def call() {
    node{
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
