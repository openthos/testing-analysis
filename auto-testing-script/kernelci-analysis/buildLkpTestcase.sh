#!/bin/bash -u
testcaseFold=/home/oto/openthos/oto_lkp/testcase
#echo 1 |sudo update-alternatives --config java
cd $testcaseFold
localPwd=`pwd`
for testcase in `ls -d */|sed 's|[/]||g'`
do 
    cd $localPwd
    cd $testcase
    [ -f testuiauto.jar ] && rm testuiauto.jar
    cd bin || continue
    jar cvfm ../testuiauto.jar ../../MANIFEST.MF -C ../../../libs ./ ./ 
done
#echo 0 |sudo update-alternatives --config java
