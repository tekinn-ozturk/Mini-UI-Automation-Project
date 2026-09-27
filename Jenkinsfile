// Build failure scenarios for testing build-analyzer. Pick one with the SCENARIO parameter;
// NONE runs the green smoke suite. Details and expected root causes: SCENARIOS.md

def mavenArgsFor(String scenario) {
    switch (scenario) {
        // Scenarios that only need their tagged Cucumber scenario to run
        case 'SEL_NO_SUCH_ELEMENT':
        case 'SEL_TIMEOUT':
        case 'SEL_STALE_ELEMENT':
        case 'SEL_SESSION_NOT_CREATED':
        case 'TEST_ASSERTION_FAILURE':
        case 'CUKE_UNDEFINED_STEP':
        case 'MVN_FORKED_VM_CRASH':
        case 'JAVA_NULL_POINTER':
            return "\"-Dcucumber.filter.tags=@${scenario}\""
        case 'CUKE_AMBIGUOUS_STEP':
            return '"-Dcucumber.glue=stepdefinitions,extraglue.ambiguous"'
        case 'CUKE_GHERKIN_PARSE_ERROR':
            return '"-Dcucumber.features=src/test/resources/broken-features" "-Dcucumber.filter.tags=@CUKE_GHERKIN_PARSE_ERROR"'
        case 'CUKE_HOOK_FAILURE':
            return '"-Denv=preprod"'
        case 'MVN_DEPENDENCY_NOT_FOUND':
            return '-P mvn-dependency-not-found'
        case 'MVN_COMPILATION_ERROR':
            return '-P mvn-compilation-error'
        case 'JAVA_OUT_OF_MEMORY':
            return '"-Dcucumber.filter.tags=@JAVA_OUT_OF_MEMORY" "-Dtest.jvm.args=-Xmx64m"'
        case 'JAVA_RELEASE_NOT_SUPPORTED':
            return '-P java-release-not-supported'
        case 'INFRA_TEST_ENV_UNREACHABLE':
            return '"-Denv=staging"'
        case 'INFRA_MAVEN_REPO_UNREACHABLE':
            return "-s ci\\settings-unreachable-mirror.xml \"-Dmaven.repo.local=${env.WORKSPACE}\\.m2-isolated\""
        default:
            return ''
    }
}

pipeline {

    agent any

    parameters {
        choice(name: 'SCENARIO', description: 'Failure scenario to reproduce (NONE = green build)', choices: [
                'NONE',
                'SEL_NO_SUCH_ELEMENT',
                'SEL_TIMEOUT',
                'SEL_STALE_ELEMENT',
                'SEL_SESSION_NOT_CREATED',
                'TEST_ASSERTION_FAILURE',
                'CUKE_UNDEFINED_STEP',
                'CUKE_AMBIGUOUS_STEP',
                'CUKE_GHERKIN_PARSE_ERROR',
                'CUKE_HOOK_FAILURE',
                'MVN_DEPENDENCY_NOT_FOUND',
                'MVN_COMPILATION_ERROR',
                'MVN_FORKED_VM_CRASH',
                'JAVA_NULL_POINTER',
                'JAVA_OUT_OF_MEMORY',
                'JAVA_RELEASE_NOT_SUPPORTED',
                'INFRA_TEST_ENV_UNREACHABLE',
                'INFRA_MAVEN_REPO_UNREACHABLE',
                'JENKINS_TOOL_NOT_FOUND',
                'JENKINS_CREDENTIALS_NOT_FOUND',
                'JENKINS_PIPELINE_SCRIPT_ERROR'
        ])
    }

    stages {

        stage('Prepare') {
            steps {
                script {
                    echo "Scenario: ${params.SCENARIO}"
                    if (params.SCENARIO == 'JENKINS_PIPELINE_SCRIPT_ERROR') {
                        // REPORT_FOLDER was never declared (neither as a parameter nor in environment {})
                        echo "Reports will be archived from ${env.WORKSPACE}\\${REPORT_FOLDER}"
                    }
                    env.MAVEN_ARGS_EXTRA = mavenArgsFor(params.SCENARIO)
                }
            }
        }

        stage('Integration Env Login') {
            when { expression { params.SCENARIO == 'JENKINS_CREDENTIALS_NOT_FOUND' } }
            steps {
                withCredentials([usernamePassword(credentialsId: 'qa-env-admin-credentials',
                                                  usernameVariable: 'QA_ADMIN_USER',
                                                  passwordVariable: 'QA_ADMIN_PASSWORD')]) {
                    bat 'echo Logging in to QA environment as %QA_ADMIN_USER%'
                }
            }
        }

        stage('Build and Test') {
            steps {
                bat "mvn -B clean test ${env.MAVEN_ARGS_EXTRA}"
            }
        }

        stage('Publish Allure Report') {
            when { expression { params.SCENARIO == 'JENKINS_TOOL_NOT_FOUND' } }
            steps {
                bat 'allure generate target\\allure-results --clean -o target\\allure-report'
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
