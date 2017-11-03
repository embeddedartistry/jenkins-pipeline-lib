#!/usr/bin/env groovy

def call(Boolean safe = false) {
  String branch = ""

  branch = "${env.BRANCH_NAME}"

  if(safe)
  {
  	branch = branch.replaceFirst('/', '-')
  }

  echo "Git branch: ${branch}"

  return branch
}
