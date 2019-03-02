#!/usr/bin/env groovy

/**
* Update the Report CI status.
*
* reportciNotify() must be called first in order for this function to work properly.
*
* Requires REPORT_CI_TOKEN environment variable to be set.
*/
def call(String include, String framework = null) {
  execString = "curl -s https://report.ci/upload.py | python - --include='${include}'"

  if(framework?.trim())
  {
  	execString += " --framework=${framework}"
  }

  sh(execString)
}
