#!/usr/bin/env bash

sh ./clean.sh

mvn clean install exec:java@leader
