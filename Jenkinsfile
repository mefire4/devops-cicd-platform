
pipeline {
    agent {
        label 'maven-agent'
    }

    stages {
        stage('Build') {
            steps {
                echo 'Compilation de notre application Java...'
                sh 'mvn -f app/pom.xml clean compile'
            }
        }

        stage('Test') {
            steps {
                echo 'Execution des tests unitaires...'
                sh 'mvn -f app/pom.xml test'
            }
        }

        stage('package'){
            steps {
                echo 'Generation du fichier JAR...'
                sh 'mvm -f app/pom.xml -DskipTests package'

                archiveArtifacts artifacts: 'app/target/*.jar',
                                 fingerprint: true
            }
        }
    }

    post {
        success {
            echo 'Pipeline termine avec succes !'
        }
        failure {
            echo 'Le pipeline a echoue. Consultez les logs.'
        }
    }
}
