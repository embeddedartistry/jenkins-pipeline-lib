#!/usr/bin/env groovy
import hudson.scm.ChangeLogSet;
import groovy.transform.Field

@Field String changeString = ""

@NonCPS
def call(List<ChangeLogSet<? extends ChangeLogSet.Entry>> changeSet = null)
{
  MAX_MSG_LEN = 100

  if(!changeString)
  {
	for (int i = 0; i < changeSet.size(); i++)
	{
		def entries = changeSet[i].items
		for (int j = 0; j < entries.length; j++)
		{
			def entry = entries[j]
			truncated_msg = entry.msg.take(MAX_MSG_LEN)
			truncated_commit = entry.commitId.take(7)
			changeString += "- `${truncated_commit}`: ${truncated_msg} [${entry.author}]\n"
		}
	}

	if (!changeString)
	{
		changeString = " - No new changes"
	}
  }

  return changeString
}
