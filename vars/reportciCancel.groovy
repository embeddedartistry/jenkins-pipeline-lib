#!/usr/bin/env groovy

/**
* Cancel the report CI job
*
* reportciNotify() must be called first in order for this function to work properly.
*
* Requires REPORT_CI_TOKEN environment variable to be set.
*/
def call(String include, String framework = null) {
  sh("curl -s https://report.ci/cancel.py | python - ")
}
