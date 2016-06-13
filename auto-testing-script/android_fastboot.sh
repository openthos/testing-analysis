#!/bin/bash -x

adb connect $1

sleep 3

adb shell mkdir /data/grub_tmp

sleep 3

adb shell busybox mount /dev/block/sda2 /data/grub_tmp/

sleep 3


if [ "$2" = "bios_reboot" ]
then
adb shell <<EOF
sed -i 's/set default=\"[0-9]*\"/set default=\"0\"/g' /data/grub_tmp/boot/grub/grub.cfg 
exit
EOF
fi


if [ "$2" = "reboot_bootloader" ]
then
adb shell <<EOF
sed -i 's/set default=\"[0-9]*\"/set default=\"1\"/g' /data/grub_tmp/boot/grub/grub.cfg
exit
EOF
fi

sleep 3
adb shell busybox umount /dev/block/sda2

sleep 3

adb reboot &
{
    sleep 1
    pkill adb
}
###adb reboot can not normal exit, must ctrl+c, game over 
