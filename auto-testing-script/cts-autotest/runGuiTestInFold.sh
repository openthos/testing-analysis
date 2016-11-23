#!/bin/bash -exu
tmpTestcaseFold=$1 
localpwd=$2
ip_android=$3
adbPort=$4
ListenPort=$5
commitId=$6


needreboot=`grep GUI $localpwd/testcaseReboot.txt`
needreboot=${needreboot##*:}

pwdBefore=`pwd`
cd $tmpTestcaseFold
for testcase in `ls -d */|sed 's|[/]||g'`
do  
    $testcase/$testcase".sh" $ip_android $adbPort $ip_android"_"$adbPort"_"$commitId 
    if [ $needreboot -eq 1 ];then
        $localpwd/android_fastboot.sh  ${ip_android} bios_reboot 
        ##second boot
        echo gui test rebooting!!
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
