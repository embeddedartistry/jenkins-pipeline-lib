#!/usr/bin/env groovy

def call(String context, String message, String state, Boolean blueOcean = true) {
  // workaround https://issues.jenkins-ci.org/browse/JENKINS-38674
  repoUrl = gitRepoURL()
  commitSha = gitCommitHash()

  String buildResultUrl = "${env.BUILD_URL}"

  if(blueOcean)
  {
    buildResultUrl = "${env.RUN_DISPLAY_URL}"
  }

  echo "Updating status for URL: $repoUrl // commit: $commitSha // context: $context"

  step([
    $class: 'GitHubCommitStatusSetter',
    reposSource: [$class: "ManuallyEnteredRepositorySource", url: repoUrl],
    commitShaSource: [$class: "ManuallyEnteredShaSource", sha: commitSha],
    errorHandlers: [[$class: "ChangingBuildStatusErrorHandler", result: "UNSTABLE"]],
    contextSource: [$class: "ManuallyEnteredCommitContextSource", context: context],
    statusBackrefSource: [$class: "ManuallyEnteredBackrefSource", backref: "${buildResultUrl}"],
    statusResultSource: [ $class: "ConditionalStatusResultSource", results: [[$class: "AnyBuildResult", message: message, state: state]] ]
  ])
}
