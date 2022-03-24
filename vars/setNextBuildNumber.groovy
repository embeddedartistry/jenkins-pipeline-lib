#!/usr/bin/env groovy

@NonCPS
def updateBuildNumber(build_number) {
  def job = Jenkins.instance.getItemByFullName(env.JOB_NAME, Job.class)
  job.nextBuildNumber = build_number
  job.saveNextBuildNumber()
  return true
}
