#!/bin/bash -x 
cd "$(dirname "$0")"
adb devices | grep "$ip_android:$adbPort"
if [[ $? -ne 0 ]];then
    adb connect $ip_android
fi

echo "alive heart" > alive.txt
adb -s $ip_android:$adbPort push alive.txt data/ 
adb -s $ip_android:$adbPort push testAliveReceive.sh data/
adb -s $ip_android:$adbPort shell "busybox nohup data/testAliveReceive.sh alive.txt $r_v $testType" &

do_exit()
{
	echo "Exit" > alive.txt
	adb -s $ip_android:$adbPort push alive.txt data/
	if [[ $? -ne 0 ]];then
	    #echo "adb push failed, network does not work!"
	    adb disconnect $ip_android:$adbPort
	    adb connect ${ip_android}

	    #do it again
	    adb -s $ip_android:$adbPort push alive.txt data/
	fi

  	adb disconnect $ip_android:$adbPort
	echo "testAliveSend.sh exit"
}
#Catch the exit SIG, "kill PID" instead of "kill -9 PID"
trap do_exit exit  

while true
do
    sleep 10
    echo "alive heart" > alive.txt
    ### if run in qemu. ip_android=localhost, ping localhost is always success, so we need consider this two situation seperately
    if [ "$r_v" = "v" ];then
        netstat -tunlp | grep $adbPort
        if [[ $? -eq 0 ]];then
            adb -s $ip_android:$adbPort push alive.txt data/
        else
            break
        fi
    else
        ping $ip_android -c 2 > /dev/null 
        con1=$?
        #adb devices | grep $ip_android
        #con2=$?
        #if [ $con1 -eq 0 -a $con2 -eq 0 ];then
        if [ $con1 -eq 0 ];then
	    adb -s $ip_android:$adbPort push alive.txt data/
	    if [[ $? -ne 0 ]];then
		    #echo "adb push failed, network does not work!"
		    adb disconnect $ip_android:$adbPort
		    adb connect ${ip_android}

		    #do it again
		    adb -s $ip_android:$adbPort push alive.txt data/
	    fi
	else
	    #Failed to ping
	    break
        fi
    fi
done

##
#adb devices | grep "$ip_android:$adbPort"
#if [[ $? -eq 0 ]];then
#    adb disconnect $ip_android:$adbPort
#fi
##
exit 0
