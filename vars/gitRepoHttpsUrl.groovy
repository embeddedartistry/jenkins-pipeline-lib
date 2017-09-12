#!/usr/bin/env groovy

def call()
{
  sh "git config --get remote.origin.url > .git/remote-url"
  url = readFile(".git/remote-url").trim()

  if(url.startsWith('git@'))
  {
  	url = url - 'git@'
    url = url.replaceAll(':','/')
  	url = 'https://' + url
  }

  if(url.endsWith('.git'))
  {
  	url = url - '.git'
  }

  return url
}
