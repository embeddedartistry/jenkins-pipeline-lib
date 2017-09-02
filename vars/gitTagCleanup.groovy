#!/usr/bin/env groovy

/**
* Delete the temporary build tag that we created
*/
def call(String build) {
  println 'Deleting temporary tag'
  def p = "git tag -d '${build}'".execute()
  p.waitFor()
}

