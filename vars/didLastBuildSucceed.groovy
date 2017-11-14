#!/usr/bin/env groovy

def call(unstableIsSuccessful = true)
{
  Boolean success = false

  if(currentBuild.getPreviousBuild() != null)
  {
    if(currentBuild.getPreviousBuild().result == "SUCCESS")
    {
      success = true
    }
    else if(currentBuild.getPreviousBuild().result == "UNSTABLE" && unstableIsSuccessful)
    {
      success = true
    }
  }
  // No previous build? Proceed as if last build failed

  return success
}
