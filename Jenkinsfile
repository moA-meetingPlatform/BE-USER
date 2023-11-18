def dockerHubRepo="awdfaf/be-user"
def dockerHubCredentialsId="dockerhub_cred"
def branch="develop"
def commitMsg="BE-USER CI/CD Pipeline Job"
def directoryPath="/home/jenkins/agent/workspace/BE-USER"
def githubEmail="awdfaf@kakao.com"
def githubKey="github-key"
def githubSSHURL="git@github.com:moA-meetingPlatform/BE-USER.git"
def imageTag="dev-74"

podTemplate(yaml: '''
              apiVersion: v1
              kind: Pod
              spec:
                securityContext:
                    fsGroup: 1000
                volumes:
                - name: docker-socket
                  emptyDir: {}
                containers:
                - name: docker
                  image: docker:23.0.5-git
                  readinessProbe:
                    exec:
                      command: [sh, -c, "ls -S /var/run/docker.sock"]
                  command:
                  - sleep
                  args:
                  - 99d
                  volumeMounts:
                  - name: docker-socket
                    mountPath: /var/run
                - name: docker-daemon
                  image: docker:23.0.5-dind
                  securityContext:
                    privileged: true
                  volumeMounts:
                  - name: docker-socket
                    mountPath: /var/run
''')
{
    node(POD_LABEL) {

        stage('Checkout') {

            container('docker'){
                checkout scm
            }

        }

        stage('Docker Build') {

            dir(path: "${directoryPath}") {
                container('docker') {
                    sh "docker buildx build --platform=linux/amd64 --build-arg COLOR=${imageTag} -t ${dockerHubRepo}:${imageTag} ./"
                }
            }


        }

        stage('Docker Hub Push') {

            container('docker') {
                docker.withRegistry('https://index.docker.io/v1/', dockerHubCredentialsId) {
                    sh "docker push ${dockerHubRepo}:${imageTag}"
                }
            }

        }

        stage('Deploy'){
            container('docker'){

                git branch: "${branch}",
                    credentialsId: "${githubKey}",
                    url: "${githubSSHURL}"

                withCredentials([sshUserPrivateKey(credentialsId: "${githubKey}", keyFileVariable: 'CERT')]) {

                    sh ("""
                        mkdir -p ~/.ssh && chmod 700 ~/.ssh &&  cp -prf ${CERT} ~/.ssh/id_rsa && chmod 600 ~/.ssh/id_rsa
                        git config --global --add safe.directory '/home/jenkins/agent/workspace/BE-USER'
                        git config --global user.email ${githubEmail}
                        eval `ssh-agent -s` && ssh-add ~/.ssh/id_rsa
                        ssh-keyscan -H github.com >> ~/.ssh/known_hosts
                        sed -i 's/tag:.*/tag: ${imageTag}/g' ${directoryPath}/helm-charts/values.yaml
                        git add ${directoryPath}/helm-charts/values.yaml
                        git commit -m '${commitMsg}'
                        git push -u origin ${branch}
                    """)
                }
            }
        }
    }
}
