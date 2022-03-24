#!/usr/bin/env groovy

@NonCPS
def call()
{
  currentBuild.rawBuild.delete()
}
