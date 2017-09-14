#!/usr/bin/env groovy

@NonCPS
def call(int lines = 75, Boolean useHTML = false)
{

  String openBlock = "```"
  String closeBlock = "```"
  String text = ""
  String newline = "\n"

  if(useHTML)
  {
    openBlock = "<pre>"
    closeBlock = "</pre>"
    newline = "<br>"
    text += "<p>"
  }

  //Intro line + begin code block
  text += "Last ${lines} of output:${newline}${openBlock}${newline}"

  // getLog returns a list of strings. We need to manually add newlines
  def list = currentBuild.rawBuild.getLog(lines)
  for(int i = 0; i < list.size(); i++)
  {
  	text += list[i]
  	text += "${newline}"
  }

  // End code block
  text += "${closeBlock}${newline}"

  if(useHTML)
  {
    text += "</p>"
  }

  return text
}
