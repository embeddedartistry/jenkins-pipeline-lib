#!/usr/bin/env groovy

def call(Boolean safe = false) {
  String branch = "${env.BRANCH_NAME}"

  if(safe)
  {
    branch = branch.replaceAll("/", "-")
    branch = branch.replaceAll("\\\\", "-")
  }

  return branch
}
