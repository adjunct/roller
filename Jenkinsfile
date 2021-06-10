// @Image(cloudbees/codeship-jenkinsfile-step:latest)
pipeline() {
    agent { docker { image 'adoptopenjdk/maven-openjdk11:latest' } }
    stages {
      stage('Linux') {
        steps {
          sh 'echo "Hello World!"'
        }
      }
    }
  }
