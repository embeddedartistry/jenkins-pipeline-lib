#!/usr/bin/env groovy

def call(boolean success) {
  fmt_state = 'FAILURE'
  msg = "Formatting changes required"

  if(success)
  {
	fmt_state = 'SUCCESS'
	msg = "Formatting meets standards"
  }

  setGithubStatus('continuous-integration/jenkins/clang-format', msg, fmt_state)
}
