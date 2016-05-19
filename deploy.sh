#!/usr/bin/env bash
ip="192.168.3.10"
echo
echo "Deploying TimeMachine to $ip"
echo
echo "Compiling Java..."
echo
mvn clean package
echo
echo "Uploading Java..."
echo
sshpass -p raspberry scp -r target/ pi@$ip:time-machine
echo
echo "Uploading Python..."
echo
sshpass -p raspberry scp -r src/main/python pi@$ip:time-machine
echo
echo "Done."
echo
