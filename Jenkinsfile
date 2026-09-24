pipeline {

    agent any

    tools {
        maven 'Maven-3.9.9'
    }

    stages {

        stage('Build and Test') {
            steps {
                bat 'mvn -version'
                bat 'mvn clean test'
            }
        }

    }

}