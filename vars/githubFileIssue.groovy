#!/usr/bin/env groovy

/**
* At the time of this implementation, issueLabel appears to be unreliable.
* Setting a value to anything other than '' causes the issue notification
* step to fail. I am not inclined to debug this, and will simply expect the
* User to rely on their own template on the Jenkins master configuration page.
*/

def call(String title = null, String status = 'failed')
{
  echo 'Filing GitHub issue with build status: $status'

  if(title == null)
  {
    title = "${env.JOB_NAME}: #${env.BUILD_NUMBER} ${status}"
  }

  step([$class: 'GitHubIssueNotifier',
        issueAppend: true,
        issueLabel: '',
        issueTitle: "$title"])

  echo 'Github issue published successfully'
}

/* TODO: allow overriding issue body
issueBody: '''
    Build \'$JOB_NAME\' has failed!

    Last 50 lines of output:

    ```
    ${OUTPUT, lines=50}
    ```

    [View full output]($BUILD_URL)''',
    issueLabel: 'Urgency-High',
    issueRepo: '<a repo>',
    issueTitle: '$JOB_NAME $BUILD_DISPLAY_NAME failed'])
*/
