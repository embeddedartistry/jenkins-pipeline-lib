#!/usr/bin/env groovy

def call(String hash, int truncate = 0) {
  sh "git log -n 1 --pretty=format:%s ${hash} > .git/commit-msg"
  String msg = readFile(".git/commit-msg").trim()

  if(truncate)
  {
  	msg = msg.take(truncate)
  }

  return msg
}
