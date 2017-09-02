#!/usr/bin/env groovy

/**
* Delete the temporary build tag that we created
*/
def call(String build) {
  echo('Deleting temporary tag')
  sh("git tag -d '${build}'")
}

