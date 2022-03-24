#!/usr/bin/env groovy

@NonCPS
def call()
{
  Integer build_number = build_number_string as Integer

  currentBuild.rawBuild.delete()
}
