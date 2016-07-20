#!/usr/bin/env bash
echo "Booting Time Machine"
sudo python python/timemachine.py &
echo
sudo nice -n -20 java -Xms1g -Xmx1g -jar time-machine.jar
echo
