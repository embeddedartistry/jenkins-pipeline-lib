#!/usr/bin/env groovy

/**
* Create a successful build tag and push it to the server
*/
def call(String build) {
  println 'Tagging successful nightly build'
  def p = "git tag -a '${build}' -m 'Successful nightly build ${build}'".execute()
  p.waitFor()
  println 'Pushing tags'
  p = 'git push --tags'.execute()
  p.waitFor()
}
