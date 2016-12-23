#!/bin/bash -ux
testcaseFold=../kernelci-analysis/testcases
cd $testcaseFold
localPwd=`pwd`
for testcase in `ls -d */|sed 's|[/]||g'`
do 
    cd $localPwd
    cd $testcase
    [ -f testuiauto.jar ] && rm testuiauto.jar
    cd bin || continue
    jar cvfm ../testuiauto.jar ../../MANIFEST.MF -C ../../libs ./ ./ 
done
