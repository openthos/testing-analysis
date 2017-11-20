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
    cd $pwdBefore
    cd $tmpTestcaseFold
    ping $ip_android -c 1 || { echo "Cannot ping Android ip:$ip_android while LKP test" ; exit 1 ; }

    test_adb=`adb -s $ip_android:$adbPort shell "ps |grep -i .sh" 2>&1`
    if [ "${test_adb:0:13}"x = "error: closed"x ];then
	echo "ADB had a serious error: 'error: closed', rebooting"

	cd $pwdBefore
        ps -p $pidAlive && kill -9 $pidAlive #I had to use "kill -9 PID"
	echo "Reboot" > alive.txt
	#ADB "error: closed", adb can not run "adb shell", but it can do "adb push" or "adb pull", Strange, huh? 
        adb -s $ip_android:$adbPort push alive.txt data/
        if [[ $? -ne 0 ]];then
            adb disconnect $ip_android:$adbPort
            adb connect ${ip_android}
            adb -s $ip_android:$adbPort push alive.txt data/
        fi

	$localpwd/reboot.sh || { echo "reboot failed" ; exit 1 ; }
        $localpwd/testAliveSend.sh &
        pidAlive=$!
    fi


    [ ! -f $testcase/$testcase".sh" ] && { echo "cannot find start script" ; continue ; }

    maxtime=600
    [ -f $testcase/timeout ] && maxtime=`cat $testcase/timeout`
    timeout $maxtime $testcase/$testcase".sh" $ip_android $adbPort $ip_android"_"$adbPort"_"$commitId
    if [ $? -ne 0 ];then
	failaction.sh $ip_android  $testcase  $r_v
	echo "$testcase return fault"
    fi
        
    cd $testcase/$ip_android"_"$adbPort"_"$commitId/*/*/*/*/*/*/ || { echo "cannot find result fold" ; continue ; }
    cd ../../../..
    pwd | grep $testcase/$ip_android"_"$adbPort"_"$commitId || continue

    #baiduyun/192.168.0.54_5555_cea721e7a0f121fd4db468f872c74f44123ca344/baiduyun/baiduyun/1x/laptop1-pc/ubuntu/defconfig/
    #testcase result dir, why?
    [ "`ls`" == "$host" ] || mv * $host 

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
