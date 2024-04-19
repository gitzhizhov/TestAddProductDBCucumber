node('unix') {
    stage('git checkout') {
    checkout scm
    }
    stage('run tests') {
    withMaven(globalMavenSettingsConfig: '', jdk: '', maven: 'Default', mavenSettingsConfig: '', traceability: true) {
    sh 'mvn clean test'
         }
}
   stage('allure-report') {
   allure includeProperties: false, jdk: '', results: [[path: 'target/reports/allure-results']]
   }
}