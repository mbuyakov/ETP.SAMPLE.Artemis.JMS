@Library('smsoft-libs')_

pipeline {
    options {
        buildDiscarder logRotator(numToKeepStr: '3')
        disableConcurrentBuilds()
        ansiColor('xterm')
    }
    agent any
    tools {
        maven 'mvn'
        jdk 'jdk11.0.2'
        // avaliable jdk1.8.0_91, jdk9.0.4, jdk10.0.1, jdk-14.0.1, jdk11.0.2, jdk-13.0.2
    }
    environment {
        commiter_name = sh(script:  'printf $(git show -s --pretty=%an)',  returnStdout: true)
        cd_registry_cred = credentials('cd-registry-credentials')
        cd_registry_url = 'cd-registry.cls.sm-soft.ru'
        appDir = "${WORKSPACE}"
        app = env.GIT_URL.replaceAll('https://github.com/mbuyakov/', '').replaceAll('.git', '').toLowerCase().replace(".","-").replace("_","-")
        registry_project = 'etp'
        version = '1.0'
    }
    stages {
        stage('Initialize') {
            steps {
                colorLogger("PATH = ${PATH}", "info")
                colorLogger("M2_HOME = ${M2_HOME}", "info")
                colorLogger("commiter_name = ${commiter_name}", "info")
            }
        }
        stage('build and deploy application') {
                      when { branch "develop" }
                      environment {
                    appDir = "${WORKSPACE}"  //added for example when repo contains multiple project
                    app = env.GIT_URL.replaceAll('https://github.com/mbuyakov/', '').replaceAll('.git', '').toLowerCase().replace(".","-").replace("_","-") //added for example when repo contains multiple project
                    vs = fileExists "${app}-vs.yml"
                    svc = fileExists "${app}-svc.yml"
                      }
                      stages {
        stage('build application') {
            steps {
              sh '''
              mvn -f ${appDir}/pom.xml clean install sonar:sonar -Dsonar.host.url=http://sonarqube.adm.sm-soft.ru
                 '''

            }
        }
        stage('build image') {
            steps {
                sh '''
                    cd ${appDir}
                    docker image build -t ${app}:${version}-${BUILD_NUMBER} .
                    docker login -u ${cd_registry_cred_USR} -p ${cd_registry_cred_PSW} ${cd_registry_url}
                    docker tag ${app}:${version}-${BUILD_NUMBER} ${cd_registry_url}/${registry_project}/${app}:${version}-${BUILD_NUMBER}
                    docker push ${cd_registry_url}/${registry_project}/${app}:${version}-${BUILD_NUMBER}
                '''
            }
            post {
                always {
                    sh '''
                        docker logout ${cd_registry_url}
                        '''
                    }
                }
            }
        stage('openshift deployment') {
                        agent {
                            docker {
                                image'openshift/origin-cli'
                                args '-u root:root'
                            }
                        }
              stages {
                    stage('add configmap') {
                        steps {
                            ocAddCMManifest(app, appDir, registry_project)
                        }
                    }
                    stage('deploy to okd') {
                        steps {
                            ocDeploy(appDir, registry_project, app, cd_registry_url, version)
                        }
                        post {
                            failure {
                            ocRollback(appDir, registry_project, app)
                            }
                          }
                        }
              stage('add svc') {
                  when { expression { svc == 'true' } }
                        steps {
                          ocAddSvc(app, appDir, registry_project)
                        }

                      }
              stage('add vs') {
                  when { expression { vs == 'true' } }
                        steps {
                          ocAddVs(app, appDir, registry_project)
                        }
                    }
                }
            }
        }
     }

//insert berfore to add new application

    }
        post {
        failure {
            telegramSendNotification()
        }
    }

}
