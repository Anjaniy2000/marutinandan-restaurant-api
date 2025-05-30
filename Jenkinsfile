pipeline {
    agent any

    environment {
        JAVA_HOME = tool 'jdk17'
        PATH = "${JAVA_HOME}/bin:${env.PATH}"
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'develop', url: 'https://github.com/Anjaniy2000/marutinandan-restaurant-api.git'
            }
        }

        stage('Set Gradle Executable') {
            steps {
                sh 'chmod +x ./gradlew'
            }
        }
        
        // --- TEST PHASE ---
        stage('Start Test Containers') {
            steps {
                sh 'docker-compose -f docker-compose.test.yml up -d'
            }
        }

        stage('Run Test DB Upgrade') {
            steps {
                sh './gradlew upgradeDatabase --args="application-test.properties"'
            }
        }

        stage('Run Tests') {
            steps {
                sh './gradlew clean test'
            }
        }
    }

    post {
        always {
            echo '🧹 Cleaning up test containers...'
            bat 'docker-compose -f docker-compose.test.yml down -v'
        }

        success {
            echo '✅ CI pipeline completed successfully!'
        }

        failure {
            echo '❌ CI pipeline failed!'
        }
    }
}
