#!/bin/bash -xu
## delete the packaging

testcaseFold=./
cd $testcaseFold
localPwd=`pwd`
for testcase in `ls -d */|sed 's|[/]||g'`
do 
#    sed -i '/cd \.\./d' $testcase/$testcase".sh"
#    sed -i '/cd bin/d' $testcase/$testcase".sh" 
#    sed -i '/jar cvfm \.\.\/testuiauto\.jar \.\.\/\.\.\/MANIFEST\.MF -C \.\.\/\.\.\/libs \.\/ \.\//d' $testcase/$testcase".sh"
    git add $testcase/$testcase".sh"
done
