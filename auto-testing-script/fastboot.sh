#!/bin/bash -x
###################### 
#
#fastboot.sh
#####################
cd  ~/android_auto


mkdir ./android_disk
umount ./android_disk
#mount {/dev/sda3}   ./android_disk
mount $1  ./android_disk
sleep 3

#mkdir  ./android_disk/{android-2016-02-29}/
mkdir  ./android_disk/android-2016-02-29/
mkdir -p ./android_disk/android-2016-02-29/system
mkdir -p ./android_disk/android-2016-02-29/data

echo $1
echo $2
sleep 2
if [ "$2" = "flashall" ]
then
cp  ./kernel        ./android_disk/android-2016-02-29/kernel
cp  ./initrd.img    ./android_disk/android-2016-02-29/initrd.img
cp  ./ramdisk.img   ./android_disk/android-2016-02-29/ramdisk.img

mkdir   ./system_tmp
sleep 2
umount ./system_tmp

if [ -f "./system.sfs" ]; then 
mkdir  ./sfstmp
mount -t squashfs ./system.sfs  ./sfstmp
sleep 3
cp ./sfstmp/system.img   .
fi 


mount ./system.img ./system_tmp
sleep 3
rm -rf ./android_disk/android-2016-02-29/system/*
cp   -Ra ./system_tmp/*   ./android_disk/android-2016-02-29/system
sleep 3
umount ./system_tmp
sleep 3
fi


if [ "$2" = "kernel" ]
then
cp  ./kernel        ./android_disk/android-2016-02-29/kernel
fi



if [ "$2" = "initrd" ]
then
cp  ./initrd.img    ./android_disk/android-2016-02-29/initrd.img
fi



if [ "$2" = "ramdisk" ]
then
cp  ./ramdisk.img   ./android_disk/android-2016-02-29/ramdisk.img
fi



if [ "$2" = "system" ]
then
mkdir   ./system_tmp
umount ./system_tmp
mount ./system.img ./system_tmp
sleep 3
rm -rf ./android_disk/android-2016-02-29/system/*
cp   -Ra ./system_tmp/*   ./android_disk/android-2016-02-29/system
sleep 3
umount ./system_tmp
sleep 3
fi



if [ "$2" = "uefi_reboot" ]
then
#efibootmgr -n {4}
efibootmgr -n 1
reboot
fi


if [ "$2" = "bios_reboot" ]
then
#$1
sed -i 's/set default=\"[0-9]*\"/set default=\"0\"/g' /boot/grub/grub.cfg
sleep 3
reboot
fi



if [ "$2" = "reboot_bootloader" ]
then
sed -i 's/set default=\"[0-9]*\"/set default=\"1\"/g' /boot/grub/grub.cfg
sleep 3
reboot
fi
