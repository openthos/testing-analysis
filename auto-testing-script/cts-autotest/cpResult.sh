#!/bin/bash -eux
## cp the result to a specified fold
###########################################

## set some parameter
result=/mnt/freenas/result
testarg=default
rootfs=android
kconfig=android_x86
cc=gcc
kernel=$commitId 

##########################################    
for i in {0..99}
do
        if [ ! -d $result/2048/$testarg/$host/$rootfs/$kconfig/$cc/$kernel/$i ]
        then
                no=$i
                break
        fi
done
#######################################
function mvGuiResult
{
    tmpTestcaseFold=$1
    for testcase in `ls $tmpTestcaseFold`
    do
        if [ -d $tmpTestcaseFold/$testcase/$ip_android"_"$adbPort"_"$kernel ];then
            mkdir -p $result/$testcase/$testarg/$host/$rootfs/$kconfig/$cc/$kernel/$no
            mv $tmpTestcaseFold/$testcase/$ip_android"_"$adbPort"_"$kernel/* $result/$testcase/$testarg/$host/$rootfs/$kconfig/$cc/$kernel/$no
            rm -r $tmpTestcaseFold/$testcase/$ip_android"_"$adbPort"_"$kernel
       fi
    done
}
function mvLkpResult
{
    tmpTestcaseFold=$1
    for testcase in `ls $tmpTestcaseFold`
    do
        if [ -d $tmpTestcaseFold/$testcase/$ip_android"_"$adbPort"_"$kernel ];then
            cp -r $tmpTestcaseFold/$testcase/$ip_android"_"$adbPort"_"$kernel/* $result/ 
            rm -r $tmpTestcaseFold/$testcase/$ip_android"_"$adbPort"_"$kernel
       fi
    done
}

mvLkpResult $testcaseLKP
#mvLkpGuiResult $testcaseGUI
mvGuiResult $testcaseFold

#########################################
#if [ $run_install == "installTest" ] || [ $cts_cmd == "cts" ] || [ $cts_cmd == "all" ];then
if [ $run_install == "installTest" ] || [ $testType == "cts" ] || [ $testType  == "all" ];then
    tmp=`find "testlog"$ListenPort".txt" | xargs grep -a "Created result dir"`
    resultDirName=${tmp##* }
    ### edit result, add commit id
    ./addCommitId.sh $resultDirName $commitId $testcaseCTS
    if [ $resultDirName"x" != "x" ];then
        if [[ ! -d  $result/cts/$testarg/$host/$rootfs/$kconfig/$cc/$kernel ]];then
            mkdir -p $result/cts/$testarg/$host/$rootfs/$kconfig/$cc/$kernel
        fi
        cp -r $testcaseCTS/repository/results/$resultDirName $result/cts/$testarg/$host/$rootfs/$kconfig/$cc/$kernel
        mv "testlog"$ListenPort".txt" $result/cts/$testarg/$host/$rootfs/$kconfig/$cc/$kernel/$resultDirName/cmdLog
    fi 
fi 
wait
exit 0

