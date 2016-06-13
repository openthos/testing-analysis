#!/bin/bash -x
###################### 
## $1 : disk path(/dev/sda40)
## $2 : 
disk_path=$1
boot_cmd=$2
iso_loc=$3

if [ ! -d "./android_mnt" ]; then
	mkdir  ./android_mnt
fi
mount $iso_loc  ./android_mnt;


if [ ! -d "android_disk" ]; then
	mkdir  android_disk
fi
mount -o loop,offset=32256 $disk_path  ./android_disk;


echo $disk_path
echo $boot_cmd
sleep 2
if [ "$boot_cmd" = "flashall" ];then

	rm ./android_disk/android*/system/* -r
	rm ./android_disk/android*/data/* -r
	rm ./android_disk/android*/kernel
	rm ./android_disk/android*/initrd.img
	rm ./android_disk/android*/ramdisk.img

	cp  ./android_mnt/kernel	./android_disk/android*/
	cp  ./android_mnt/initrd.img    ./android_disk/android*/
	cp  ./android_mnt/ramdisk.img   ./android_disk/android*/

	## cp grub
	##
	##[[ $iso_loc =~ "5." ]] &&  cp  -r grub5.1 ./android_disk/grub
	##[[ $iso_loc =~ "6." ]] &&  cp  -r grub6.0 ./android_disk/grub
	rm ./android_disk/grub/* -r
	if [[ $iso_loc =~ "5.1" ]];then
		cp  -r grub5.1/* ./android_disk/grub/
		mv ./android_disk/android* ./android_disk/android-2016-04-02
	fi
	if [[ $iso_loc =~ "6.0" ]];then
		cp  -r grub6.0/* ./android_disk/grub/
		mv ./android_disk/android* ./android_disk/android-2016-04-25
	fi
	##

	if [ ! -d "./system_tmp" ]; then
		mkdir   ./system_tmp;
	fi

	if [ -f "./android_mnt/system.sfs" ]; then 
		mkdir  ./sfstmp
		mount -t squashfs ./android_mnt/system.sfs  ./sfstmp;
		mount ./sfstmp/system.img ./system_tmp;
		cp   -Ra ./system_tmp/*   ./android_disk/android*/system;
		umount system_tmp;
		umount sfstmp;
		sleep 1
		rm sfstmp -r
	else
		mount ./android_mnt/system.img ./system_tmp;
		cp   -Ra ./system_tmp/*   ./android_disk/android*/system/;
		sleep 3
		umount system_tmp;
	fi
	
	sleep 2
	rm system_tmp -r;
fi
umount android_mnt;
umount android_disk;
rm android_mnt -r;
rm android_disk -r;
