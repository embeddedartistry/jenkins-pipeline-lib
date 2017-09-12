#!/usr/bin/env groovy

/**
* At the time of this implementation, issueLabel appears to be unreliable.
* Setting a value to anything other than '' causes the issue notification
* step to fail. I am not inclined to debug this, and will simply expect the
* User to rely on their own template on the Jenkins master configuration page.
*/

def call(String title = null, Boolean useDefaultBody = false, String changeString = null)
{
  echo "Filing GitHub issue for ${env.JOB_NAME}"

  if(title == null)
  {
    title = "${env.JOB_NAME}: #${env.BUILD_NUMBER} Failed"
  }

  if(useDefaultBody)
  {
    step([$class: 'GitHubIssueNotifier',
      issueAppend: true,
      issueLabel: '',
      issueTitle: "$title"])
  }
  else
  {
    // Default to environment
    String changes = "${env.GIT_CHANGE_LOG}"

    if(changeString)
    {
      //Override if the user supplied one
      changes = changeString
    }

    String build_log = getBuildLog()
    String body = "Build `${env.JOB_NAME}` #${env.BUILD_NUMBER} Failed\n\n[View full output](${env.BUILD_URL})\n\n**Change log:**\n${changes}\n\n${build_log}"

    step([$class: 'GitHubIssueNotifier',
      issueAppend: true,
      issueLabel: '',
      issueTitle: "$title",
      issueBody: "$body"])
  }

  echo 'Github issue published successfully'
}
