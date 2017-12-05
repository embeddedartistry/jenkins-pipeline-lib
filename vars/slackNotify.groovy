#!/usr/bin/env groovy
import hudson.scm.ChangeLogSet;

/**
 * Send notifications based on build status string
 *
 * We support one non-standard string:
 *    ARCHIVE_FAILED
 */
def call(String buildStatus = 'STARTED', String changeString = null, Boolean blueOcean = true) {
  // build status of null means successful
  buildStatus =  buildStatus ?: 'SUCCESS'

  // Default values
  def color = 'RED'
  def colorCode = '#E74C3C'
  def printChanges = false

  // Override default values based on build status
  if (buildStatus == 'STARTED') {
    color = 'YELLOW'
    colorCode = '#FFFF00'
    buildStatus = 'Started'
  } else if (buildStatus == 'SUCCESS') {
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

  String buildResultUrl = "${env.BUILD_URL}"

  if(blueOcean)
  {
    buildResultUrl = "${RUN_DISPLAY_URL}"
  }

  // Slack
  string jobNameSafe = "${env.JOB_NAME}"
  jobNameSafe = jobNameSafe.replaceAll("%2F", "/")
  String slackMsg = "<${buildResultUrl}|${jobNameSafe} #${env.BUILD_NUMBER}>: *${buildStatus}*\n"

  if(printChanges)
  {
    if("${env.BUILD_NUMBER}" == "1")
    {
      slackMsg += "First Build: " + gitCurrCommitLog() + "\n"
    }
    else if(changeString)
    {
      slackMsg += changeString
    }
    else if("${env.GIT_CHANGE_LOG}".contains("No new changes"))
    {
      // Check if change log is set to "No new changes" - populate with curr build
      slackMsg += "${env.GIT_CHANGE_LOG}\nBuilt commit: " + gitCurrCommitLog() + "\n"
    }
    else if("${env.GIT_CHANGE_LOG}" != "null")
    {
      // Default behavior is to check for a GIT_CHANGE_LOG environment variable
      slackMsg += "Changes:\n${env.GIT_CHANGE_LOG}"
    }
    else
    {
      // Empty change string: supply current commit
      slackMsg += "Built commit: " + gitCurrCommitLog() + "\n"
    }
  }

  echo("Sending slack message: ${slackMsg}")

  // I put this in for cases where Slack doesn't work - let the build continue
  try
  {
    slackSend (color: colorCode, message: slackMsg)
  }
  catch (error)
  {
    echo "Slack notification failed: $error"
    currentBuild.result = 'UNSTABLE'
  }
}
