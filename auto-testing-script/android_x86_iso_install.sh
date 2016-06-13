#!/bin/bash -x
##################### 
#      安装android_x86
#android_x86_iso_install.sh
#####################
cd ~/android_auto
mkdir ./android_mnt
sleep 2
umount ./android_mnt
sleep 3 
#mount ./{android_x86.iso}  ./android_mnt
mount ./android_x86.iso  ./android_mnt

sleep 5

cp  ./android_mnt/kernel         .
cp  ./android_mnt/initrd.img     .
cp  ./android_mnt/ramdisk.img    .
cp  ./android_mnt/system.img     .
cp  ./android_mnt/system.sfs     .

sleep 3
umount ./android_mnt
#./partition_disk.sh $1

#mkfs.ext4 {/dev/sda4}
mkfs.ext4  $1 <<EOF
y
EOF

sleep 5

./fastboot.sh  $1 flashall
