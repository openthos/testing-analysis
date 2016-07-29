#!/bin/bash
### edit testTesult.xml and cts_result.xsl to add commmit id to it
foldName=$1
commitId=$2
resultFold="../android-cts/repository/results/$foldName"
cp cts_result.xsl $resultFold
sed -i 's/<Cts version="5.1_r4">/<Cts version="5.1_r4" commitid="'$commitId'">/g' $resultFold/testResult.xml
#sed -i '7d' $resultFold/testResult.xml
#sed -i -e '6a\    <Cts version="5.1_r4" commitid="'$commitId'">' $resultFold/testResult.xml

