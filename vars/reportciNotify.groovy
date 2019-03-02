#!/usr/bin/env groovy

/**
* Create a report CI job.
*
* Requires REPORT_CI_TOKEN environment variable to be set.
*/
def call(String name) {
  sh("curl -s https://report.ci/queue.py | python -  --name '${name}'")
  sh("curl -s https://report.ci/start.py | python - ")
}
