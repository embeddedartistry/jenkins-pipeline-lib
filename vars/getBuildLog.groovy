#!/usr/bin/env groovy

@NonCPS
def call(int lines = 75)
{
  //Intro line + begin code block
  String text = "Last ${lines} of output:\n```\n"

  // getLog returns a list of strings. We need to manually add newlines
  def list = currentBuild.rawBuild.getLog(lines)
  for(int i = 0; i < list.size(); i++)
  {
  	text += list[i]
  	text += "\n"
  }

  // End code block
  text += "```\n"

  return text
}
