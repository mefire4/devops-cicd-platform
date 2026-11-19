
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

            post {
                always {
                    junit 'app/target/surefire-reports/*.xml'
                }
            }
        }

        stage('package'){
            steps {
                echo 'Generation du fichier JAR...'
                sh 'mvn -f app/pom.xml -DskipTests package'

                archiveArtifacts artifacts: 'app/target/*.jar',
                                 fingerprint: true
            }
        }

        stage('Docker Build') {
            steps {
                sh 'docker build -t demo-cicd:${BUILD_NUMBER} app/'
            }
        }

        stage('Docker push') {
            steps {
                WithCredentials([usernamePassword(
                    credentialsId: 'ghcr-credentials',
                    usernameVariable: 'GH_USER',
                    passwordVariable: 'GH_TOKEN'
                )]) {
                    sh '''
                        echo "$GH_TOKEN" | docker login ghcr.io -u "$GH_USER" --password-stdin
                        docker tag demo-cicd:${BUILD_NUMBER} ghcr.io/mefire4/demo-cicd:${BUILD_NUMBER}
                        docker push ghcr.io/mefire4/demo-cicd:${BUILD_NUMBER}
                        docker logout ghcr.io
                    '''   
                }
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
