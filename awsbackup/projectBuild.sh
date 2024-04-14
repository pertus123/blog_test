#!/bin/bash
sudo su
echo "success"
cd ssafy/s04p12b103/
echo "cd sfolder success"
git pull origin develop
echo "pull success"
cd backend
echo "cd backend success"
rm -rf target/
echo "rm success"
mvn package
echo "mvn success"
cd target
echo "cd target success"

var1=$(ps -ef | grep 'run_loop\.sh$')
echo "success"
second1=$(echo ${var1} | cut -d " " -f2)
echo "success"
if [ -n "${second1}" ]
then
	        result1=$(kill -9 ${second1})
		        echo process is killed.
		else
			        echo running process not found.
fi
echo "process kill success"

java -jar project-0.0.1-SNAPSHOT.jar &
echo "jar success"

#//front
cd ../../\[quasar\]\ frontend/
echo "cd fronted success"
quasar build
echo "build success"
yes |cp -arpf dist/ /var/www/html/
echo "dist move success"
#/usr/bin/mv  -f dist/ /var/www/html/dist/
sudo service nginx restart
echo "final success"
