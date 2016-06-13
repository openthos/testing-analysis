cd autoFrame/replace

if [ ! -d "./android_disc" ]; then
			mkdir  ./android_disc
fi
mount -o loop,offset=32256 ../../rawiso/android_x86.raw ./android_disc/


if [ "$1" = "flashall" ]; then
	cp  ./kernel        ./android_disk/android-2016-02-29/kernel
	cp  ./initrd.img    ./android_disk/android-2016-02-29/initrd.img
	cp  ./ramdisk.img   ./android_disk/android-2016-02-29/ramdisk.img
	mkdir   ./system_tmp
	sleep 2
	umount ./system_tmp
	mount ./system.img ./system_tmp
	rm -rf ./android_disk/android-2016-02-29/system/*
	cp   -Ra ./system_tmp/*   ./android_disk/android-2016-02-29/system
	umount ./system_tmp
fi


if [ "$1" = "replace-kernel" ]; then
	cp  ./kernel        ./android_disk/android-2016-02-29/kernel
fi

if [ "$1" = "replace-initrd" ]; then
	cp  ./initrd.img    ./android_disk/android-2016-02-29/initrd.img
fi

if [ "$1" = "repalce-ramdisk" ]; then
	cp  ./ramdisk.img   ./android_disk/android-2016-02-29/ramdisk.img
fi

if [ "$1" = "replace-system" ]; then
	if [ ! -d "./android_disk" ]; then
		mkdir   ./system_tmp
	fi
	umount ./system_tmp
	mount ./system.img ./system_tmp
	rm -rf ./android_disk/android-2016-02-29/system/*
	cp -Ra ./system_tmp/*   ./android_disk/android-2016-02-29/system
	umount ./system_tmp
fi

if [ "$1" == "reboot" ]; then

fi

if [ "$1" = "reboot-bootloader" ]; then

fi

if [ "$1" = "boot" ]; then
	## do nothing
fi


################
## add scripts to init.sh

line2bottom=`tail android_disc/android-2016-02-29/system/etc/init.sh -n 2 |head -n 1`
if [ "$line2bottom" == "" ]; then
	sed '$d' -i ./android_disc/android-2016-02-29/system/etc/init.sh
else
	sed '$d' -i ./android_disc/android-2016-02-29/system/etc/init.sh
	sed '$d' -i ./android_disc/android-2016-02-29/system/etc/init.sh
	sed '$d' -i ./android_disc/android-2016-02-29/system/etc/init.sh
	sed '$d' -i ./android_disc/android-2016-02-29/system/etc/init.sh
	sed '$d' -i ./android_disc/android-2016-02-29/system/etc/init.sh
fi
echo "ip=\`getprop | grep ipaddress\`
	ip=\${ip##*\[}
	ip=\${ip%]*}
	echo \$ip | nc -q 0 $ip_linux_host 5556
	return 0" >> ./android_disc/android-2016-02-29/system/etc/init.sh

cd ../..
