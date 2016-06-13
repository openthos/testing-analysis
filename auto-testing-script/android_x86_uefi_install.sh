#!/bin/bash -x
########################################
#
#android_x86_uefi_install.sh
#可以在本地进行测试
#########################################
cd ~/android_auto 
umount /boot/efi
#mount /dev/{sda7} /boot/efi
mount /dev/sda1 /boot/efi

rm -rf /boot/efi/boot
rm -rf  /boot/efi/EFI/android_boot

mkdir -p /boot/efi/boot/grub/
cp ./grub.cfg /boot/efi/boot/grub

cp -R  ./android_boot  /boot/efi/EFI


#efibootmgr -c -d /dev/{sda} -p  {1}  -l  \\EFI\\android_boot\\grubX64.efi  -L  android_x86
efibootmgr -c -d /dev/sda -p  1  -l  \\EFI\\android_boot\\grubX64.efi  -L  android_x86
### -p 参数应该和esp相对应  /dev/sda1
