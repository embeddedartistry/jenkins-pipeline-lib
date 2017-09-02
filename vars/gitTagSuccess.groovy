#!/usr/bin/env groovy

/**
* Create a successful build tag and push it to the server
*/
def call(String build) {
  echo('Tagging successful nightly build')
  sh("git tag -a '${build}' -m 'Successful nightly build ${build}'")
  sh("git push --tags")
}
