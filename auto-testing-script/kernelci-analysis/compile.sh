#!/bin/bash -e
otoid=$1
android_repo=/mnt/freenas/work

/usr/sbin/ntpdate cn.ntp.org.cn
cd $android_repo
../OTO/repo sync
source build/envsetup.sh
lunch android_x86_64-eng
make -j8 iso_img
if [ $? -ne 0 ];then
    make clean
    source build/envsetup.sh
    lunch android_x86_64-eng
    make -j8 iso_img
fi
    
mv out/target/product/x86_64/android_x86_64.iso out/target/product/x86_64/android_x86_64-$otoid-5.1.iso
exit
