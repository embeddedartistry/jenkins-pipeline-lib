#!/usr/bin/env groovy

def call(String tag, String artifactPattern)
{
  url = gitRepoURL()
  hash = gitCommitHash()

  echo "Creating a release for repo $url with tag $tag"
  githubRelease(
    releaseTag: "$tag",
    repoURL: "$url",
    commitish: gitCommitHash(),
    releaseName: "Release $tag",
    releaseBody: "Release $tag uploaded by the Embedded Artistry Jenkins server.",
    isPreRelease: true,
    isDraftRelease: false,
    artifactPatterns: "$artifactPattern"
  )
}

