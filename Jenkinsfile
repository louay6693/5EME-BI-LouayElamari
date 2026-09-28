pipeline {
    agent any

    tools {
        maven 'Maven-3.9.16'
    }

    environment {
        SONAR_HOST = 'http://192.168.33.10:9000'
        SONAR_KEY  = '5EME-BI-LouayElamari'
    }

    stages {

        stage('Checkout') {
            steps { checkout scm }
        }

        stage('Build, Test & SonarQube') {
            steps {
                dir('backend') {
                    withCredentials([string(credentialsId: 'sonar-token', variable: 'SONAR_TOKEN')]) {
                        sh '''
                            mvn -B clean verify \
                              org.sonarsource.scanner.maven:sonar-maven-plugin:5.0.0.4389:sonar \
                              -Dsonar.projectKey=$SONAR_KEY \
                              -Dsonar.host.url=$SONAR_HOST \
                              -Dsonar.token=$SONAR_TOKEN
                        '''
                    }
                }
            }
            post {
                always {
                    junit allowEmptyResults: false, testResults: 'backend/target/surefire-reports/*.xml'
                }
            }
        }

        stage('Prepare .env') {
            steps {
                withCredentials([
                    usernamePassword(credentialsId: 'dockerhub', usernameVariable: 'DH_USER', passwordVariable: 'DH_PASS'),
                    string(credentialsId: 'mysql-password', variable: 'DB_PASS')
                ]) {
                    sh '''
                        printf "MYSQL_ROOT_PASSWORD=%s\\n" "$DB_PASS"          >  .env
                        printf "MYSQL_DATABASE=gestion_projets\\n"             >> .env
                        printf "MYSQL_USER=devops\\n"                          >> .env
                        printf "MYSQL_PASSWORD=%s\\n" "$DB_PASS"               >> .env
                        printf "DOCKERHUB_USER=%s\\n" "$DH_USER"               >> .env
                        printf "IMAGE_PREFIX=louayelamari-5bi-devops\\n" >> .env
                        printf "API_URL=http://192.168.33.10:8081\\n"          >> .env
                    '''
                }
            }
        }

        stage('Docker Build') {
            steps {
                sh 'docker compose build'
            }
        }

        stage('Docker Push') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerhub', usernameVariable: 'DH_USER', passwordVariable: 'DH_PASS')]) {
                    sh '''
                        echo "$DH_PASS" | docker login -u "$DH_USER" --password-stdin
                        docker compose push backend frontend
                        docker logout
                    '''
                }
            }
        }

        stage('Deploy') {
            steps {
                sh 'docker compose up -d --remove-orphans'
            }
        }
    }

    post {
        success { echo 'Pipeline terminé : application déployée sur http://192.168.33.10:4200' }
        failure { echo 'Pipeline en échec, consulte les logs' }
        always  { sh 'rm -f .env' }
    }
}