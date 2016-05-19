#!/usr/bin/env bash
sudo java \
-client \
-Xms1g \
-Xmx1g \
-cp "/home/pi/lightboard/lib/*:/home/pi/time-machine/target/classes" \
net.amarantha.timemachine.Main $*
