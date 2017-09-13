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
  def subject = "${buildStatus}: ${env.JOB_NAME} #${env.BUILD_NUMBER}"
  def details = """<p>${buildStatus}: Job '${env.JOB_NAME} #${env.BUILD_NUMBER}':</p>
    <p>Check console output at &QUOT;<a href='${env.BUILD_URL}'>${env.JOB_NAME} [${env.BUILD_NUMBER}]</a>&QUOT;</p>"""

  emailext (
      subject: subject,
      body: details,
      recipientProviders: [[$class: 'CulpritsRecipientProvider'], [$class: 'RequesterRecipientProvider']]
    )
}
