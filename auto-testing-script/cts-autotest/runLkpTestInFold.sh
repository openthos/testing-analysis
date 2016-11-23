#!/bin/bash  -xeu
tmpTestcaseFold=$1 
localpwd=$2
ip_android=$3 
adbPort=$4
ListenPort=$5
commitId=$6
host=$7


needreboot=`grep GUI $localpwd/testcaseReboot.txt`
needreboot=${needreboot##*:}
pwdBefore=`pwd`
cd $tmpTestcaseFold
for testcase in `ls -d */|sed 's|[/]||g'`
do  
    $testcase/$testcase".sh" $ip_android $adbPort $ip_android"_"$adbPort"_"$commitId 
    cd $testcase/$ip_android"_"$adbPort"_"$commitId/*/*/*/*/*/*/
    mv * $commitId
    cd ../../../..
    mv * $host 
    cd $pwdBefore
    cd $tmpTestcaseFold
    if [ $needreboot -ne 0 ];then
        $localpwd/android_fastboot.sh  ${ip_android} bios_reboot 
        ##second boot
        echo lkp test rebooting!!
        ip_android=`nc -lp $ListenPort`
        echo "android boot success!"
        echo ${ip_android}
        adb connect ${ip_android}
        sleep 2
        adb -s $ip_android:$adbPort shell system/checkAndroidDesktop.sh
        sleep 30
    fi  
done
cd $pwdBefore
