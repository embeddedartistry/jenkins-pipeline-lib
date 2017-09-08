#!/usr/bin/env groovy

def call(String title = null, String label = '')
{
  echo 'Build failed! Filing GitHub issue'

  if(title == null)
  {
    title = "${env.BUILD_DISPLAY_NAME} failed"
  }

  step([$class: 'GitHubIssueNotifier',
        issueAppend: true,
        issueLabel: $label,
        issueTitle: $title])
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
