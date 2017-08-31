#!/bin/bash  -xu
tmpTestcaseFold=$1 
cd `dirname $0`
needreboot=`grep LKP $localpwd/testcaseReboot.txt`
needreboot=${needreboot##*:}
pwdBefore=`pwd`
$localpwd/testAliveSend.sh &
pidAlive=$!
cd $tmpTestcaseFold
for testcase in `ls -d */|sed 's|[/]||g'`
do
    ping $ip_android -c 1 || { echo "cannot ping ip $ip_android" ; exit 1 ; }

    [ ! -f $testcase/$testcase".sh" ] && { echo "cannot find start script" ; continue ; }
    maxtime=600
    [ -f $testcase/timeout ] && maxtime=`cat $testcase/timeout`
    timeout $maxtime $testcase/$testcase".sh" $ip_android $adbPort $ip_android"_"$adbPort"_"$commitId
    if [ $? -ne 0 ];then
	failaction.sh $ip_android  $testcase  $r_v
        ps -p $pidAlive && kill $pidAlive 
        $localpwd/reboot.sh || { echo "reboot failed" ; exit 1 ; }
        $localpwd/testAliveSend.sh &
        pidAlive=$!
	continue
    fi
        
    cd $testcase/$ip_android"_"$adbPort"_"$commitId/*/*/*/*/*/*/ || { echo "cannot find result fold" ; exit 1 ; }
    cd ../../../..
    pwd | grep $testcase/$ip_android"_"$adbPort"_"$commitId || exit 1
    [ `ls` == "$host" ] || mv * $host 
    cd $pwdBefore
    cd $tmpTestcaseFold

    if [ $needreboot -ne 0 ];then
        #kill $pidAlive 
        ps -p $pidAlive && kill $pidAlive 
        $localpwd/reboot.sh || { echo "reboot failed" ; exit 1 ; }
        $localpwd/testAliveSend.sh &
        pidAlive=$!
    fi  
#    ## in order to avoid excessive adb load, program random sleep for a while
#    sleep $(($RANDOM%200))
done

ps -p $pidAlive && kill $pidAlive 
if [ $needreboot -eq 0 ];then
    $localpwd/reboot.sh || { echo "reboot failed" ; exit 1 ; }
fi

cd $pwdBefore 
wait
exit 0
