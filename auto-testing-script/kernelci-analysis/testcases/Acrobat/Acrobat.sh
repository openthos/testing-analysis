#!/bin/bash -x
androidIP=$1
port=$2
foldName=$3
cd "$(dirname "$0")"
./install_apk.sh $androidIP $port
mkdir $foldName
#touch $foldName/testResult
java -jar testuiauto.jar $androidIP $port otoAutoTest.jar $foldName
adb -s $androidIP:$port shell pm uninstall -k com.adobe.reader 
python TmpTojson.py $foldName/tmpResultToJson $foldName
