#!/usr/bin/env groovy

def call()
{
  MAX_MSG_LEN = 100

  String hash = gitCommitHash().take(7)
  String message = "`${hash}`: " + gitCommitMessage(hash, MAX_MSG_LEN)

  return message
}
