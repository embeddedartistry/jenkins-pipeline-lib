#!/usr/bin/env groovy
import hudson.scm.ChangeLogSet;

/**
* Create a temporary git tag at the start of the build process
* This is used by the versioning library for the embedded-artistry framework
* By using an annotated tag, we can populate the build version details from git
*/
def call(String build) {
  println 'Creating a temporary build tag for versioning'
  println "git tag -a '${build}' -m 'Creating tag to build ${build}'"
  def p = ["git", "tag", "-a", "'${build}'", "-m", "'Creating tag to build ${build}'"].execute()
  p.waitFor()
}

