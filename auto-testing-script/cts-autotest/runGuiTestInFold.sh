#!/bin/bash -xu
tmpTestcaseFold=$1 
cd "$(dirname "$0")"
needreboot=`grep GUI $localpwd/testcaseReboot.txt`
needreboot=${needreboot##*:}
pwdBefore=`pwd`
cd $tmpTestcaseFold
$localpwd/testAliveSend.sh &
pidAlive=$!
for testcase in `ls -d */|sed 's|[/]||g'`
do  
    adb connect $ip_android:$adbPort
    $testcase/$testcase".sh" $ip_android $adbPort $ip_android"_"$adbPort"_"$commitId &
    pid=$!
    $localpwd/monitorAdb.sh $pid $ip_android $ip_android"_"$adbPort"_"$commitId $testcase 15 $r_v
    if [ $? -ne 0 ];then 
        wait
        exit 1
    fi 
    if [ $needreboot -eq 1 ];then
        #kill $pidAlive
        ps -p $pidAlive && kill $pidAlive
        $localpwd/reboot.sh
        ip_android=`cat $localpwd/"ip_android"$ListenPort`
        $localpwd/testAliveSend.sh &
        pidAlive=$!
    fi  
done
if [ $needreboot -eq 0 ];then 
    ps -p $pidAlive && kill $pidAlive
    $localpwd/reboot.sh
    ip_android=`cat $localpwd/"ip_android"$ListenPort`
fi  
cd $pwdBefore
wait
echo "@@"$ip_android 
echo $ip_android > $localpwd/"ip_android"$ListenPort
