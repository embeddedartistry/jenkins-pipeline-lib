#!/usr/bin/env groovy

import org.jenkinsci.plugins.github.GitHubPlugin
import org.jenkinsci.plugins.github.config.GitHubPluginConfig
import org.jenkinsci.plugins.github.config.GitHubServerConfig

// Inspiration drawn from: https://github.com/jenkinsci/github-issues-plugin/blob/master/src/dev/assets/work/init.groovy
// If you create a token with an ID of `github-oath-token`, you won't need to supply an argument

def call(String credentialsID = 'github-oauth-token')
{
  repoUrl = gitRepoHttpsUrl()

  echo "Creating a GitHubPluginConfig for credential $credentialsID"
  // configure github plugin
  GitHubPluginConfig pluginConfig = GitHubPlugin.configuration()
  GitHubServerConfig serverConfig = new GitHubServerConfig(credentialsID)
  pluginConfig.setConfigs([serverConfig])
  pluginConfig.save()

  echo "Registering GithubProjectProperty for repo URL $repoUrl"
  // Set a project property for the Github URL
  properties([[$class: 'GithubProjectProperty', projectUrlStr: repoUrl]])
}
