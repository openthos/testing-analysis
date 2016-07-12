#! /bin/bash
result=/mnt/freenas/result
testarg=default
host=kvm
rootfs=android
kconfig=android_x86
cc=gcc
kernel=6.0

ip=$1

for i in {0..99}
do 
	if [ ! -d $result/ebizzy/$testarg/$host/$rootfs/$kconfig/$cc/$kernel/$i ]
	then 
		no=$i
		break
	fi
done
mkdir -p $result/ebizzy/$testarg/$host/$rootfs/$kconfig/$cc/$kernel/$no
mkdir -p $result/nbench/$testarg/$host/$rootfs/$kconfig/$cc/$kernel/$no
mkdir -p $result/browser/$testarg/$host/$rootfs/$kconfig/$cc/$kernel/$no

adb -s $ip push ./lkpgui /data/local/tmp
adb -s $ip shell /data/local/tmp/ebizzy > $result/ebizzy/default/$host/$rootfs/$kconfig/$cc/$kernel/$no/ebizzy.out
adb -s $ip shell /data/local/tmp/nbench > $result/nbench/default/$host/$rootfs/$kconfig/$cc/$kernel/$no/nbench.out
adb -s $ip shell uiautomator runtest demo1.jar -c com.browser.demobrowser > $result/browser/default/$host/$rootfs/$kconfig/$cc/$kernel/$no/browser.out


now=`date +%Y.%m.%d_%H.%M.%S`
tar -cvf $now"result.tar" $result 
