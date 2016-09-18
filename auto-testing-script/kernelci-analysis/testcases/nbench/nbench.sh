#!/bin/bash
androidIP=$1
port=$2
commitId=$3
cd "$(dirname "$0")"
adb -s $androidIP:$port push nbench /data/local/tmp
adb -s $androidIP:$port shell /data/local/tmp/nbench
