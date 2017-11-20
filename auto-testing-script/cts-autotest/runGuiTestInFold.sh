#!/bin/bash -xu
tmpTestcaseFold=$1 
cd "$(dirname "$0")"
needreboot=`grep GUI $localpwd/testcaseReboot.txt`
needreboot=${needreboot##*:}
pwdBefore=`pwd`
$localpwd/testAliveSend.sh &
pidAlive=$!
cd $tmpTestcaseFold
for testcase in `ls -d */|sed 's|[/]||g'`
do
    ping $ip_android -c 1 || { echo "Cannot ping Android ip:$ip_android while GUI test" ; exit 1 ; }
    adb connect $ip_android:$adbPort || { echo "adb cannot connect $ip_android while GUI test" ; exit 1 ; }

    timeout 600 $testcase/$testcase".sh" $ip_android $adbPort $ip_android"_"$adbPort"_"$commitId
    [ $? -ne 0 ] && { $localpwd/failaction.sh $ip_android $testcase  $r_v ;  echo "$testcase return fault"; }

    if [ $needreboot -eq 1 ];then
        #kill $pidAlive
        ps -p $pidAlive && kill $pidAlive
        $localpwd/reboot.sh || { echo "reboot failed" ; exit 1 ; }
        $localpwd/testAliveSend.sh &
        pidAlive=$!
    fi  
done

ps -p $pidAlive && kill $pidAlive
if [ $needreboot -eq 0 ];then 
    $localpwd/reboot.sh
fi
cd $pwdBefore
wait
exit 0
