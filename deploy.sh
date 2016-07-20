#!/usr/bin/env bash
ip="192.168.1.61"
echo "Deploying TimeMachine to $ip"
echo
if [ "$1" != "-skipjava" ]
then
    echo "Compiling Java..."
    mvn clean package
    echo "Uploading Java..."
    sshpass -p raspberry scp -r target/time-machine pi@${ip}:
fi
echo "Uploading Python..."
sshpass -p raspberry scp -r src/main/python/ pi@${ip}:time-machine
echo "Uploading Shell Scripts..."
sshpass -p raspberry scp -r *.sh pi@${ip}:time-machine
sshpass -p raspberry ssh pi@${ip} "cd /home/pi/time-machine; chmod a+x *.sh"
#echo "Uploading Audio..."
#sshpass -p raspberry scp -r audio/ pi@${ip}:time-machine
echo
echo "Done."
echo
