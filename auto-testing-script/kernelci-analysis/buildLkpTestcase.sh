#!/bin/bash
testcaseFold=/home/oto/openthos/oto_lkp/testcase 
echo 1 | update-alternatives --config java
cd $testcaseFold
localPwd=`pwd`
source /etc/profile
for testcase in `ls -d */|sed 's|[/]||g'`
do 
    cd $localPwd
    cd $testcase
    [ -f bin/$testcase".jar" ] && rm bin/$testcase".jar"
    android create uitest-project -n $testcase -t 1 -p .
    ant build
done
echo 0 | update-alternatives --config java
