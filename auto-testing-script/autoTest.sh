#!/bin/bash
# install related tools and download related packages

# run qemu
#./qemu-system-x86_64 -m 4G --enable-kvm -net nic,vlan=0 -net tap,vlan=0,ifname=tap0,script=no ../../android-x86/android_x86.iso 

#jdk7_home=/usr/lib/jvm/java-7-openjdk-and64/jre/bin/java
#jdk8_home=/usr/lib/jvm/java-8-oracle/jre/bin/java 
## change the java version command
## update-alternatives --config java

##########################################################################################################################
## $1 : virtual mechine or real mechine (v/r)
## $2 : run android_x86(run) or install android_x86.iso(install)
## $3 : ip of client linux system, if you test local android_x86, use localhost or 127.0.0.1
## $4 : path of disk(/dev/sda40) or virtual disk(../rawiso/android_x86.raw)
## $5 : cts command("-p android.acceleration --disable-reboot")
## if test mechine has been installed android_x86, on the other world, $2=run, this 5 parameter above is enough
########################################################################################################################
## but if we need to install a new android_x86, we should also specify the path of android_x86.iso, so $6 is needed  
## $6 : path of android_x86.iso(../rawiso/android_x86.iso)
#########################################################################################################################
## eg: ./autoTest.sh r install 192.168.2.16 /dev/sda40 "-p android.acceleration --disable--reboot" android_x86.iso
## eg: ./autoTest.sh v install localhost /media/aquan/000D204000041550/research/android-x86-6.0.raw "-p android.acceleration --disable-reboot" android_x86.iso

r_v=$1
run_install=$2
ip_linux_client=$3
disk_path=$4
cts_cmd=$5

ip_linux_host=`/sbin/ifconfig -a|grep inet|grep -v 127.0.0.1|grep -v inet6|awk '{print $2}'|tr -d "addr:"`

function EditBoot()
{
	if [ ! -d "android_disk" ]; then
		mkdir  android_disk
	fi
	mount -o loop,offset=32256 $disk_path android_disk;
    ########################################
    ## modify init.sh
    line2bottom=`tail android_disk/android*/system/etc/init.sh -n 2 |head -n 1`
    sed '$d' -i ./android_disk/android*/system/etc/init.sh
	sed '$d' -i ./android_disk/android*/system/etc/init.sh

	#echo \$ip | nc -q 0 $ip_linux_host 5556
	if [ "$line2bottom" == "" ]; then
		echo "ip=\`getprop | grep ipaddress\`
		ip=\${ip##*\[}
		ip=\${ip%]*}
		nc -w 2 $ip_linux_host 5556 << EOF
    	\$ip
EOF
		return 0" >> ./android_disk/android*/system/etc/init.sh
	else
    	sed '$d' -i ./android_disk/android*/system/etc/init.sh
    	sed '$d' -i ./android_disk/android*/system/etc/init.sh
    	echo "nc -w 2 $ip_linux_host 5556 << EOF
    	\$ip
EOF
    	return 0" >> ./android_disk/android*/system/etc/init.sh
	fi
	umount android_disk;
}

## according to where it's virtual mechine(qemu) or real mechine, we should change the network model
if [ "$r_v" == "v" ]; then
	#if [ "$2" == "raw" ];then
	if [ "$run_install" == "install" ];then
		## install iso and then test the android-x86
		iso_loc=$6
		if [ ! -d "~/android_auto" ]; then
			mkdir ~/android_auto
		fi
		#cp iso_loc ./android_x86.iso
		./fastboot_vir.sh $disk_path flashall $iso_loc;
		EditBoot

		## install CtsDeviceAdmin.apk and active the device adminstrators, this setting will take effect after reboot 
		qemu-system-x86_64 -m 2G --enable-kvm -net nic -net user,hostfwd=tcp::5558-:5555 $disk_path &
		{
			ip_android_v=`nc -lp 5556`
			## waiting for a message from android-x86, this ip address is useful in real mechine test, but in virtural mechine ,we adopt nat address mapping ,
			## so it's just a symbol that android-x86 is running 
	        echo 'waiting for android boot !!!!!'  
            sleep 60
			adb connect localhost:5558
            ##keep screen active
            adb shell svc power stayon true
			## install CtsDeviceAdmin.apk
            echo 'install CtsDeviceAdmin.apk!!!!!'
            adb install ../android-cts/repository/testcases/CtsDeviceAdmin.apk
            adb push device_policies.xml data/system/device_policies.xml
            adb shell poweroff
		}

	fi

	#sleep 40
	if [ "$run_install" == "run" ]|| [ "$run_install" == "install" ];then

		EditBoot
		qemu-system-x86_64 -m 2G --enable-kvm -net nic -net user,hostfwd=tcp::5558-:5555 $disk_path &
		{
			ip_android_v=`nc -lp 5556`
			## waiting for a message from android-x86, this ip address is useful in real mechine test, but in virtural mechine ,we adopt nat address mapping ,
			## so it's just a symbol that android-x86 is running 
	        echo 'waiting for android boot !!!!!'  
            sleep 60

            ## gui haven't been loaded completely for android_x86-5.1 
            echo 'testing'
			adb connect localhost:5558
			echo "exit" | ../android-cts/tools/cts-tradefed run cts $cts_cmd 
            adb shell poweroff
		}
	fi
elif [ "$r_v" == "r" ];then

	if [ "$run_install" == "run" ];then
		## real mechine

		rsync   -avz -e ssh ./script root@${ip_linux_client}:~/;
		ssh root@${ip_linux_client} "~/script/reboot.sh $disk_path $ip_linux_host";

		##ssh root@${ip_linux_client}
		## return to linux_host command
		ip_android_r=`nc -lp 5556`
		echo $ip_android_r
		adb connect $ip_android_r
		## firstly, modify the grub
		adb shell mkdir data/linux
		adb shell busybox mount /dev/block/sda2 data/linux;
		adb shell sed -i '/set default=\"[0-9]\"/c''set default=\"2\"' data/linux/boot/grub/grub.cfg
		#../android-cts/tools/cts-tradefed run cts --plan CTS --disable-reboot 
		echo 'waiting for android boot !!!!!'  
		sleep 15 
    	echo 'testing'

		echo "exit" | ../android-cts/tools/cts-tradefed run cts $cts_cmd
		adb shell busybox umount data/linux;
		adb shell rm -r data/linux
		adb shell reboot &
    	{
    	    ## for adb shell reboot will wait for the android_x86 reboot, so we have to kill this process  
    	    sleep 1
    	    pkill adb
    	}
    elif [ "$run_install" == "install" ];then
    	## install android-x86 and then test
    	iso_loc=$6
    	./auto2.sh $ip_linux_client $iso_loc $disk_path;
    	ip_android=`nc -lp 5556`
		echo "android boot success!"
		sleep 30
		echo ${ip_android}
		adb connect ${ip_android}
		sleep 5

        ##keep screen active
        adb shell svc power stayon true
        echo 'install CtsDeviceAdmin.apk!!!!!'
        adb install ../android-cts/repository/testcases/CtsDeviceAdmin.apk
        adb push device_policies.xml data/system/device_policies.xml
		./android_fastboot.sh  ${ip_android} bios_reboot 

        ##second boot 
    	ip_android=`nc -lp 5556`
		echo "android boot success!"
		sleep 30
		echo ${ip_android}
		adb connect ${ip_android}
		sleep 5
        
		echo 'testing'
		echo "exit" | ../android-cts/tools/cts-tradefed run cts $cts_cmd
		###reboot to  linux
		./android_fastboot.sh  ${ip_android}  reboot_bootloader
	fi
fi
