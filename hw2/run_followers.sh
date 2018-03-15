#!/usr/bin/env bash

mvn exec:java@follower -Dexec.args="5001 false 5000" &
sleep 1
mvn exec:java@follower -Dexec.args="5002 false 5000" &
sleep 1
mvn exec:java@follower -Dexec.args="5003 false 5000" &
sleep 1
mvn exec:java@follower -Dexec.args="5004 false 5000" &