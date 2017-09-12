#!/usr/bin/env groovy
import hudson.scm.ChangeLogSet;

/**
 * Send notifications based on build status string
 *
 * We support one non-standard string:
 *    ARCHIVE_FAILED
 */
def call(String buildStatus = 'STARTED', List<ChangeLogSet<? extends ChangeLogSet.Entry>> changeSet = null) {
  // build status of null means successful
  buildStatus =  buildStatus ?: 'SUCCESSFUL'

  // Default values
  def color = 'RED'
  def colorCode = '#E74C3C'

  def changeString
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

  if(printChanges)
  {
    if(changeSet != null)
    {
      changeString = "\n\nChange log:\n" + gitChangeLog(changeSet)
    }
  }

  // Slack
  def slackMsg = "<${env.BUILD_URL}|${env.JOB_NAME} #${env.BUILD_NUMBER}>: *${buildStatus}*"

  if(changeString)
  {
    slackMsg += changeString
  }

  // I put this in for cases where Slack doesn't work - let the build continue
  try
  {
    slackSend (color: colorCode, message: slackMsg)
  }
  catch (error)
  {
    echo "Slack notification failed: $error"
  }

  //Email
  def subject = "${buildStatus}: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'"
  def details = """<p>${buildStatus}: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]':</p>
    <p>Check console output at &QUOT;<a href='${env.BUILD_URL}'>${env.JOB_NAME} [${env.BUILD_NUMBER}]</a>&QUOT;</p>"""

/* TODO
  emailext (
      to: 'bitwiseman@bitwiseman.com',
      subject: subject,
      body: details,
      recipientProviders: [[$class: 'DevelopersRecipientProvider']]
    )
*/
}
