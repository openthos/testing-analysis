#!/bin/bash -x

#ip_linux="192.168.2.16"
#ip_android="192.168.2.58"
#android_iso_for_test="xyl_android_x86_64_5.1.iso"
#diskpart_for_android="/dev/sda40"

ip_linux=$1
android_iso_for_test=$2
diskpart_for_android=$3
ListenPort=$4

## my ip
ip_linux_host=`ifconfig -a|grep inet|grep -v 127.0.0.1|grep -v inet6|awk '{print $2}'|tr -d "addr:"`



####this line can  be annotation 
##read -p "please input the ip_linux:" ip_linux
echo ${ip_linux}

###copy  the android iso you want to test , to this dir
    
cp  ${android_iso_for_test}  ./android_x86.iso


####a small bug has fixed
ssh root@${ip_linux} "rm -rf /root/android_auto/"
####a small bug has fixed

./android_tool.sh ${ip_linux}  partition_disk.sh ${diskpart_for_android}

## add by aoquan
#ssh root@${ip_linux} "~/android_auto/script/edit_init.sh ${ip_linux_host}"

#./android_tool.sh ${ip_linux}  fastboot.sh  ${diskpart_for_android}  reboot_bootloader




# ip_linux=`nc -lp 5556`
# echo "linux reboot done!"
# sleep 5

./android_tool.sh ${ip_linux}  android_x86_iso_install.sh ${diskpart_for_android} 

## add by aoquan
ssh root@${ip_linux} "~/android_auto/scriptReboot2/reboot.sh $diskpart_for_android $ip_linux_host $ListenPort"
sleep 2

./android_tool.sh ${ip_linux}  android_x86_grub_install.py ${diskpart_for_android} 

./android_tool.sh ${ip_linux}  fastboot.sh  ${diskpart_for_android}  bios_reboot 