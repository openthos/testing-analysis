#!/bin/bash  -xeu
tmpTestcaseFold=$1 
localpwd=$2
ip_android=$3 
adbPort=$4
ListenPort=$5
commitId=$6
host=$7
r_v=$8
qemuCMD="$9"
disk_path=${10}


cd "$(dirname "$0")"
needreboot=`grep GUI $localpwd/testcaseReboot.txt`
needreboot=${needreboot##*:}
pwdBefore=`pwd`
$localpwd/testAliveSend.sh $ip_android $adbPort $r_v &
pidAlive=$!
cd $tmpTestcaseFold
for testcase in `ls -d */|sed 's|[/]||g'`
do  
    $testcase/$testcase".sh" $ip_android $adbPort $ip_android"_"$adbPort"_"$commitId &
    pid=$!
    $localpwd/monitorAdb.sh $pid $ip_android  $ip_android"_"$adbPort"_"$commitId $testcase 30
    if [ $? -ne 0 ];then
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
        kill $pidAlive 
        $localpwd/reboot.sh $ip_android $adbPort $ListenPort $r_v $qemuCMD $disk_path
        ip_android=`cat $localpwd/"ip_android"$ListenPort`
        $localpwd/testAliveSend.sh $ip_android $adbPort $r_v &
        pidAlive=$!
    fi  
done
if [ $needreboot -eq 0 ];then
    kill $pidAlive 
    $localpwd/reboot.sh $ip_android $adbPort $ListenPort $r_v $qemuCMD $disk_path
    ip_android=`cat $localpwd/"ip_android"$ListenPort`
fi  
cd $pwdBefore 
wait
echo "@@"$ip_android
echo $ip_android > $localpwd/"ip_android"$ListenPort
