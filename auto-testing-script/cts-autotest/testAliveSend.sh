#!/bin/bash -ex 
cd "$(dirname "$0")"
adb devices | grep "$ip_android:$adbPort"
if [[ $? -eq 1 ]];then
    #adb connect $ip_android
    exit
fi
adb -s $ip_android:$adbPort push alive.txt data/ 
adb -s $ip_android:$adbPort push testAliveReceive.sh data/
adb -s $ip_android:$adbPort shell "busybox nohup data/testAliveReceive.sh alive.txt $r_v $testType" &

while true
do
    sleep 60
    echo "alive heart" > alive.txt
    ### if run in qemu. ip_android=localhost, ping localhost is always success, so we need consider this two situation seperately
    if [ $r_v = "v" ];then
        netstat -tunlp | grep $adbPort
        if [[ $? -eq 0 ]];then
            adb -s $ip_android:$adbPort push alive.txt data/
        else
            break
        fi
    else
        ping $ip_android -c 1 > /dev/null 
        con1=$?
        adb devices | grep $ip_android
        con2=$?
        if [ $con1 -eq 0 -a $con2 -eq 0 ];then
            adb -s $ip_android:$adbPort push alive.txt data/
        else
            #echo "adb push failed, network does not work!"
            break
        fi
    fi
done

adb devices | grep "$ip_android:$adbPort"
if [[ $? -eq 0 ]];then
    adb disconnect $ip_android:$adbPort
fi
exit 0
