#!/usr/bin/env groovy

/**
* Create a temporary git tag at the start of the build process
* This is used by the versioning library for the embedded-artistry framework
* By using an annotated tag, we can populate the build version details from git
*/
def call(String build) {
  echo('Creating temporary tag')
  sh("git tag -a '${build}' -m 'Creating tag to build ${build}'")
}

