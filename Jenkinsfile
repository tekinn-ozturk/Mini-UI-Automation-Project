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

            publishHTML([
                allowMissing: false,
                alwaysLinkToLastBuild: true,
                keepAll: true,
                reportDir: 'target/reports',
                reportFiles: 'cucumber-report.html',
                reportName: 'Cucumber Report'
            ])

        }
    }
}