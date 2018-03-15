#!/usr/bin/env bash
rm -rf Logs500*
fuser -k 500{0,1,2,3,4,5}/tcp