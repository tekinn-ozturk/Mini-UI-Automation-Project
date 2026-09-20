pipeline {

    agent any

    stages {

        stage('Build and Test') {
            steps {
                bat 'mvn clean test'
            }
        }

    }

    post {

        always {

            cucumber buildStatus: 'UNSTABLE',
                     fileIncludePattern: '**/cucumber.json'

        }

    }
}