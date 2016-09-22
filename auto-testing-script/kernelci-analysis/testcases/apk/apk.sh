#!/bin/bash -x
androidIP=$1
port=$2
commitId=$3
cd "$(dirname "$0")"
./install_apk.sh $androidIP $port
java -jar testuiauto.jar $androidIP otoAutoTest.jar 
./uninstall_apk.sh $androidIP $port

