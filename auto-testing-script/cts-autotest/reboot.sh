#!/bin/bash -x
cd "$(dirname "$0")"
if [ "$r_v" == "r" ];then
    ./android_fastboot.sh  ${ip_android} bios_reboot
    ncPid=`lsof -i:$ListenPort | grep nc | awk '{print $2}'`
    [ ! -n "$ncPid" ] || kill $ncPid
    ip_android=`timeout 180 nc -l $ListenPort` || { echo "wait ip failed" ; exit 1 ; }
    ping $ip_android -c 1 || { echo "cannot ping ip $ip_android, boot failed" ; exit 1 ; }
    for i in {1..5}
    do
        adb connect ${ip_android} && break
        sleep 2 
    done
    [ $i -eq 5 ] && { echo "adb connect failed" ; exit 1 ; }
    echo $ip_android
    for i in {1..5}
    do
	sleep 5
        adb -s $ip_android:$adbPort shell system/checkAndroidDesktop.sh && break
	echo "check desktop boot failed"
    done
    [ $i -eq 5 ] && { echo "check desktop boot failed and will return ubuntu" ; exit 1 ; }
    sleep 20
else
    adb -s $ip_android:$adbPort shell poweroff
    sleep 3
    adb disconnect $ip_android:$adbPort
    sleep 1
    qemuPid=`ps -axu |grep qemu | grep kvm | awk '{print $2}'`
    [ ! -n "$qemuPid" ] || kill $qemuPid

    $qemuCMD -vga vmware --enable-kvm -net nic -net user,hostfwd=tcp::$adbPort-:5555 $disk_path -vnc :3 &
    {
        qemuPid=$!
        nc -l $ListenPort &
        ncPid=$!
        waitTime=0
        while true
        do 
            if [ $waitTime -eq 10 ];then
                echo qemu reboot fail while GUI test
                kill $qemuPid 
                kill $ncPid 
                exit 1
            fi
            
            sleep 20
            ps -p $ncPid 
            if [ $? -eq 0 ];then
                let waitTime+=1 
            else 
                break
            fi
        done
        
        ## gui haven't been loaded completely for android_x86-5.1 
        adb connect $ip_android:$adbPort
        sleep 2
        adb -s $ip_android:$adbPort shell system/checkAndroidDesktop.sh
        sleep 30 
    }
fi
#echo "@@"$ip_android
#echo $ip_android > "ip_android"$ListenPort
export ip_android
