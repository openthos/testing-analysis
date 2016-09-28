#!/bin/bash
androidIP=$1
port=$2
foldName=$3
cd "$(dirname "$0")"
mkdir $foldName
adb -s $androidIP:$port push ebizzy /data/local/tmp
adb -s $androidIP:$port shell /data/local/tmp/ebizzy > $foldName/testResult
