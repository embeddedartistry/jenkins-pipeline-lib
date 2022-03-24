#!/usr/bin/env groovy

@NonCPS
def setNextBuildNumber(String build_number_string) {
  Integer build_number = build_number_string as Integer
  def job = Jenkins.instance.getItemByFullName(env.JOB_NAME, Job.class)
  job.nextBuildNumber = build_number
  job.saveNextBuildNumber()
  return true
}
