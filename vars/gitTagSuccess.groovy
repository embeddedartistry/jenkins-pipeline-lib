#!/usr/bin/env groovy

/**
* Create a successful build tag and push it to the server
*/
def call(String build) {
  "git tag -a '${build}' -m 'Successful nightly build ${build}'".execute()
  'git push --tags'.execute()
}
