#! /bin/bash -x
result=/mnt/freenas/result
testarg=default
host=$1
host=${host%/*}
rootfs=android
kconfig=android_x86
cc=gcc

ip=$1
iso=$2
tmp=${iso#*-}
kernel=${tmp%-*}

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
adb -s $ip shell /data/local/tmp/ebizzy > $result/ebizzy/$testarg/$host/$rootfs/$kconfig/$cc/$kernel/$no/output
adb -s $ip shell /data/local/tmp/nbench > $result/nbench/$testarg/$host/$rootfs/$kconfig/$cc/$kernel/$no/output
adb -s $ip shell uiautomator runtest demo1.jar -c com.browser.demobrowser > $result/browser/$testarg/$host/$rootfs/$kconfig/$cc/$kernel/$no/output
