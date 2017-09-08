#!/usr/bin/env groovy

def call() {
  sh "git rev-list --no-merges HEAD --max-count=1 > .git/current-commit"
  return readFile(".git/current-commit").trim()
}
