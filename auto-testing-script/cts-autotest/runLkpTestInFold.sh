#!/bin/bash  -xu
tmpTestcaseFold=$1 
cd "$(dirname "$0")"
needreboot=`grep LKP $localpwd/testcaseReboot.txt`
needreboot=${needreboot##*:}
pwdBefore=`pwd`
$localpwd/testAliveSend.sh &
pidAlive=$!
cd $tmpTestcaseFold
for testcase in `ls -d */|sed 's|[/]||g'`
do  
    $testcase/$testcase".sh" $ip_android $adbPort $ip_android"_"$adbPort"_"$commitId &
    pid=$!
    $localpwd/monitorAdb.sh $pid $ip_android  $ip_android"_"$adbPort"_"$commitId $testcase 30 $r_v
    if [ $? -ne 0 ];then
        #ps -p $pidAlive && kill $pidAlive 
        wait
        exit 1
    fi

    cd $testcase/$ip_android"_"$adbPort"_"$commitId/*/*/*/*/*/*/
    #mv * $commitId
    cd ../../../..
    mv * $host 
    cd $pwdBefore
    cd $tmpTestcaseFold
    if [ $needreboot -ne 0 ];then
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
ps -p $pidAlive && kill $pidAlive 
cd $pwdBefore 
wait
echo "@@"$ip_android
echo $ip_android > $localpwd/"ip_android"$ListenPort
