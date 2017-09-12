#!/usr/bin/env groovy
import hudson.scm.ChangeLogSet;

/**
 * Send notifications based on build status string
 *
 * We support one non-standard string:
 *    ARCHIVE_FAILED
 */
def call(String buildStatus = 'STARTED', String changeString = null) {
  // build status of null means successful
  buildStatus =  buildStatus ?: 'SUCCESSFUL'

  // Default values
  def color = 'RED'
  def colorCode = '#E74C3C'
  def printChanges = false

  // Override default values based on build status
  if (buildStatus == 'STARTED') {
    color = 'YELLOW'
    colorCode = '#FFFF00'
    buildStatus = 'Started'
  } else if (buildStatus == 'SUCCESSFUL') {
    color = 'GREEN'
    colorCode = '#27AE60'
    buildStatus = 'Successful'
    printChanges = true
  } else if (buildStatus == 'ABORTED') {
    color = 'GREY'
    colorCode = '#D7DBDD'
    buildStatus = 'Aborted'
  } else if (buildStatus == 'ARCHIVE_ERROR') {
    // Use default colors
    buildStatus = 'Archiving failed'
  } else if (buildStatus == 'FAILURE') {
    buildStatus = 'FAILED'
    printChanges = true
  } else if (buildStatus == 'UNSTABLE') {
    color = 'YELLOW'
    colorCode = '#FFFF00'
    buildStatus = 'Unstable'
    printChanges = true
  }

  // Slack
  String slackMsg = "<${env.BUILD_URL}|${env.JOB_NAME} #${env.BUILD_NUMBER}>: *${buildStatus}*"

  if(printChanges)
  {
    if(changeString)
    {
      slackMsg += changeString
    }
    else
    {
      // Default behavior is to check for a GIT_CHANGE_LOG environment variable
      slackMsg += "${env.GIT_CHANGE_LOG}"
    }
  }

  // I put this in for cases where Slack doesn't work - let the build continue
  try
  {
    slackSend (color: colorCode, message: "Test message")
  }
  catch (error)
  {
    echo "Slack notification failed: $error"
    currentBuild.result = 'UNSTABLE'
  }
}
