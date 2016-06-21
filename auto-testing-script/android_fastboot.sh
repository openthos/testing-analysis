#!/bin/bash -x

adb connect $1
wait
#sleep 2

adb -s $1:5555 shell mkdir /data/grub_tmp
wait
#sleep 1

adb -s $1:5555 shell busybox mount /dev/block/sda2 /data/grub_tmp/

wait
#sleep 1


if [ "$2" = "bios_reboot" ]
then
adb -s $1:5555 shell <<EOF
sed -i 's/set default=\"[0-9]*\"/set default=\"0\"/g' /data/grub_tmp/boot/grub/grub.cfg 
exit
EOF
fi


if [ "$2" = "reboot_bootloader" ]
then
adb -s $1:5555 shell <<EOF
sed -i 's/set default=\"[0-9]*\"/set default=\"1\"/g' /data/grub_tmp/boot/grub/grub.cfg
exit
EOF
fi
wait
#sleep 3
adb -s $1:5555 shell busybox umount /dev/block/sda2

wait
#sleep 1

adb -s $1:5555 reboot &
{
    sleep 1
    pkill adb
}
###adb reboot can not normal exit, must ctrl+c, game over 
