#!/bin/sh
testcases=$PWD/testcases

for testcase in $testcases
do
	adb push $testcase /data/tmp/$testcase
	adb shell /data/tmp/$testcase/$testcase.sh
done
