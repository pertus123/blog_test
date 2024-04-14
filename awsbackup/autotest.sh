#!/bin/bash
var1=$(ps -ef | grep 'project-0.0.1-SNAPSHOT.jar$')
second1=$(echo ${var1} | cut -d " " -f2)
echo ${second1}
echo "tomcat" | sudo -S apt-get update
if [ -n "${second1}" ]
then
	        result1=$(sudo kill -9 ${second1})
		        echo process is killed.
		else
			echo running process not found.
fi
java -jar /opt/tomcat/apache-tomcat-8.5.59/backend/target/project-0.0.1-SNAPSHOT.jar 2>> /dev/null >> /dev/null &
