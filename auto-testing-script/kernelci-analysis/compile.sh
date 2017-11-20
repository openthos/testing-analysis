#!/bin/bash -x
otoid=$1
android_repo=/mnt/freenas/work
/usr/sbin/ntpdate ntp.tuna.tsinghua.edu.cn
cd $android_repo
../OTO/repo sync
#source build/envsetup.sh
#lunch android_x86_64-eng
#make -j8 iso_img
#if [ $? -ne 0 ];then
#    make clean
#    source build/envsetup.sh
#    lunch android_x86_64-eng
#    make -j8 iso_img
#fi
echo "sync finished!"
if [ -f  /mnt/freenas/compile/compileFinished ];then 
    rm /mnt/freenas/compile/compileFinished
fi

#docker start oto
docker start oto-test

count=0
while true
do
    ## compileFinished is a flag which means the compile is finished
    if [ -f  /mnt/freenas/compile/compileFinished ];then
        if [ "`cat  /mnt/freenas/compile/compileFinished`" == "compile fail" ];then
            echo "compile failed"
            rm /mnt/freenas/compile/compileFinished
            exit -1
        else
            echo "compile success"
            rm /mnt/freenas/compile/compileFinished
            break
        fi
    fi
    ## if the compile continued above 6 hours, we think it would be some proble in compile and exit the program
    if [ $count -eq 360 ];then
        exit -1
    fi
    count=$((count+1))
    sleep 60
done
echo "compile finished!"
    
mv out/target/product/x86_64/android_x86_64.iso out/target/product/x86_64/android_x86_64-$otoid-5.1.iso
exit
