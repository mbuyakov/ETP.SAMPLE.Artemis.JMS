# OPENSHIFT.TEMPLATE
Template repo for openshift application

Пример сделан в основном для ситуации
1 репо = 1 приложение

в корне находится pom, Dockerfile, манифесты k8s/okd


## **Важно!!**

Необходимо строго соблюдать правило наименования:

Названия репозитория (приводится к строчным буквам с заменой точек на минусы) = наимtнование манифестов с соответсвующими суффиксами = наименование labels  и имени контейнера:

например:

Репозиторий **OPENSHIFT.TEMPLATE**:

deployment: **openshift-template.yml**

в самом файле:

```
app: openshift-template
```


```
containers:
  - name: openshift-template
```
configmap: **openshift-template-cm.yml**

в самом файле:

```
name: openshift-template-cm

```
service: **openshift-template-svc.yml**

в самом файле:
```
name: openshift-template

```
virtualservice: **openshift-template-vs.yml**

в самом файле:
```
name: openshift-template
```

## **Jenkinsfile**

сборка и деплой производятся из ветки develop

Требуется изменить следующите параметры:

registry_project = 'etp'

version = '1.0'

jdk 'jdk11.0.2'


#### если в репе несколько приложений
заменить
appDir, app на наименование папки и приложения

```
        stage('build and deploy application') {
                      when { branch "develop" }
                      environment {
                    appDir = "${WORKSPACE}"  //added for example when repo contains multiple project
                    app = env.JOB_BASE_NAME.toLowerCase().replace(".","-") //added for example when repo contains multiple project
                      }
```                    

перед комментарием выставить следующий блок для нового приложения

//insert code before comment to add new application

```
        stage('build and deploy application') {
                      when { branch "develop" }
                      environment {
                    appDir = "${WORKSPACE}"  //added for example when repo contains multiple project
                    app = env.GIT_URL.replaceAll('https://github.com/mbuyakov/', '').replaceAll('.git', '').toLowerCase().replace(".","-") //added for example when repo contains multiple project
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

```
