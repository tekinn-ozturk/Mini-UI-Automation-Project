pipeline {

    agent any

    tools {
        maven 'Maven-3.9.9'
    }

    stages {

        stage('Build and Test') {
            steps {
                bat 'mvn clean test'
            }
        }

    }

    post {

        always {

            cucumber(
                buildStatus: 'UNSTABLE',
                fileIncludePattern: 'target/reports/cucumber.json'
            )

            publishHTML([
                allowMissing: false,
                alwaysLinkToLastBuild: true,
                keepAll: true,
                reportDir: 'target/reports',
                reportFiles: 'cucumber-report.html',
                reportName: 'Cucumber HTML Report'
            ])

        }

    }

}