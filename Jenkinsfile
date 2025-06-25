template = '''
apiVersion: v1
kind: Pod
metadata:
  labels:
    run: docker
  name: docker
spec:
  volumes:
  - name: docker
    hostPath:
      path: /var/run/docker.sock
  containers:
  - command:
    - sleep
    - "3600"
    image: docker
    name: docker
    volumeMounts: 
    - mountPath: /var/run/docker.sock
      name: docker
'''

podTemplate(cloud: 'kubernetes', label: 'docker', yaml: template) {
node ("docker") {
    container ("docker") {
    stage ("Checkout SCM") {
      git branch: 'main', url: 'https://github.com/aliiarustamova/jenkins-february-2025.git'
    }

withCredentials([usernamePassword(credentialsId: 'docker-creds', passwordVariable: 'DOCKER_PASS', usernameVariable: 'DOCKER_USER')]) {
    stage ("Docker Login") {
    sh "docker login -u ${DOCKER_USER} -p ${DOCKER_PASS}"
    }
    stage ("Docker Build") {
        sh "docker build -t ${DOCKER_USER}/myapache:2.0.0 ."
    }

    stage ("Docker Push") {
        sh "docker push ${DOCKER_USER}/myapache:2.0.0"
    }
    }
}
}
}