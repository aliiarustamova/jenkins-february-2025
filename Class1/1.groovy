template = '''
apiVersion: v1
kind: Pod
metadata:
  labels:
    run: terraform
  name: terraform
spec:
  containers:
  - command:
    - sleep
    - "3600"
    image: hashicorp/terraform
    name: terraform
'''

properties([parameters([choice(choices: ['apply', 'destroy'], description: 'Select your choice', name: 'action')])])

podTemplate(cloud: 'kubernetes', label: 'terraform', yaml: template) {
node ("terraform") {
    container ("terraform") {
    stage ("Checkout SCM") {
      git branch: 'main', url: 'https://github.com/aliiarustamova/actions-terraform.git'
    }

withCredentials([usernamePassword(credentialsId: 'aws-creds', passwordVariable: 'AWS_SECRET_ACCESS_KEY', usernameVariable: 'AWS_ACCESS_KEY_ID')]) {
    stage ("Terraform init") {
    sh "terraform init"
    }
    stage ("Terraform plan") {
    sh "terraform plan"
    }
if (params.action == "apply") {
    stage ("Terraform apply") {
    sh "terraform apply -auto-approve"
    }
}
else {
    stage ("Terraform destroy") {
    sh "terraform destroy -auto-approve"
    }
}
}
}
}
}