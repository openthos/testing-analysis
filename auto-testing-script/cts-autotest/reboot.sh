#!/bin/bash -x
cd "$(dirname "$0")"
if [ "$r_v" == "r" ];then
    ./android_fastboot.sh  ${ip_android} bios_reboot 
    ip_android=`nc -lp $ListenPort`                                                                                                                                  
    echo ${ip_android}
    adb connect ${ip_android}
    sleep 2
    adb -s $ip_android:$adbPort shell system/checkAndroidDesktop.sh
    sleep 30
else
    adb -s $ip_android:$adbPort shell poweroff
    sleep 3
    adb disconnect $ip_android:$adbPort
    $qemuCMD -vga vmware --enable-kvm -net nic -net user,hostfwd=tcp::$adbPort-:5555 $disk_path -vnc :3 &
    {
        qemuPid=$!
        nc -lp $ListenPort
        ## gui haven't been loaded completely for android_x86-5.1 
        adb connect $ip_android:$adbPort
        sleep 2
        adb -s $ip_android:$adbPort shell system/checkAndroidDesktop.sh
        sleep 30 
    }
fi
echo "@@"$ip_android
echo $ip_android > "ip_android"$ListenPort
