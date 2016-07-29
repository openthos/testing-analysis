#!/bin/bash  -x
cd "$(dirname "$0")"
#user="root"
#cp2host="aquan@192.168.2.39:/"
#cp2port="22"

testingUser="root"
testingIP="166.111.131.12"
testingPort="6622"
testingFold="/home/oto/cts"

testedUser="oto"
testedIP="166.111.131.12"
testedPort="7022"
testedFold=""

localUser="aquan"
localFold="/home/aquan/cts/cts-autotest"

if [ -n "$1" ] ;then
    isoLoc=$1
    scp -P $testingPort $isoLoc $testingUser@$testingIP:$testingFold/android_x86_64-5.1.iso
fi

rsync  -avz -e "ssh -p $testingPort" ./* $testingUser@$testingIP:$testingFold/cts-autotest/
#scp -P $testingPort $localFold/testAll.sh $testingUser@$testingIP:$testingFold/cts-autotest/
ssh -p $testingPort $testingUser@$testingIP $testingFold/cts-autotest/testAll.sh

