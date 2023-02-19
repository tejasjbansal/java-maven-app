def buildJar() {
    echo "building the application..."
    sh 'mvn package'
} 

def buildImage() {
    echo "building the docker image..."
    withCredentials([usernamePassword(credentialsId: 'Docker-Id', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
        sh 'docker build -t tejashbansal/my-repo:2.0 .'
        sh "echo $PASS | docker login -u $USER --password-stdin"
        sh 'docker push tejashbansal/my-repo:2.0'
    }
} 

def deployApp() {
    echo 'deploying the application...'
} 

return this
