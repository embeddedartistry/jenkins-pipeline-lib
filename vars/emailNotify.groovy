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
  buildStatus =  buildStatus ?: 'SUCCESS'

  def printChanges = false

  // Override default values based on build status
  if (buildStatus == 'STARTED') {
    buildStatus = 'Started'
  } else if (buildStatus == 'SUCCESS') {
    buildStatus = 'Successful'
    printChanges = true
  } else if (buildStatus == 'ABORTED') {
    buildStatus = 'Aborted'
  } else if (buildStatus == 'ARCHIVE_ERROR') {
    // Use default colors
    buildStatus = 'Archiving failed'
  } else if (buildStatus == 'FAILURE') {
    buildStatus = 'FAILED'
    printChanges = true
  } else if (buildStatus == 'UNSTABLE') {
    buildStatus = 'Unstable'
    printChanges = true
  }

  //Email
  String subject = "${env.JOB_NAME} #${env.BUILD_NUMBER} <b>${buildStatus}</b>"
  String details = """<p>${buildStatus}: Job '${env.JOB_NAME} #${env.BUILD_NUMBER}':</p>
    <p>See full console output at &QUOT;<a href='${env.BUILD_URL}'>${env.JOB_NAME} [${env.BUILD_NUMBER}]</a>&QUOT;</p>"""

  if(printChanges)
  {
    if(changeString)
    {
      details += "<p>Changes:<br>${changeString}</p>"
    }
    else
    {
      // Default behavior is to check for a GIT_CHANGE_LOG environment variable
      details += "<pr>Changes:<br>${env.GIT_CHANGE_LOG}</p>"
    }
  }

  details += getBuildLog(75, true)

  // Adjust setting so we can send emails to github users
  def currentVal = RecipientProviderUtilities.SEND_TO_UNKNOWN_USERS
  RecipientProviderUtilities.SEND_TO_UNKNOWN_USERS  = true

  emailext (
      subject: subject,
      body: details,
      recipientProviders: [[$class: 'CulpritsRecipientProvider'], [$class: 'RequesterRecipientProvider']]
    )

  //Restore setting
  RecipientProviderUtilities.SEND_TO_UNKNOWN_USERS = currentVal
}
