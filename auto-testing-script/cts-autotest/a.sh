#!/bin/bash -x
tmp=`adb shell cat data/commitId.txt | grep commitId`
commitId=${tmp##*:}
commitId=${commitId%?}
ListenPort=52001
tmp=`find "testlog"$ListenPort".txt" | xargs grep "Created result dir"`
resultDirName=${tmp##* }
ip_android=192.168.2.68
if [ $resultDirName"x" != "x" ];then
    if [[ ! -d  /mnt/freenas/result/cts/default/$ip_android/android/android_x86/gcc/$commitId ]];then
        mkdir -p /mnt/freenas/result/cts/default/$ip_android/android/android_x86/gcc/$commitId
    fi
    #cp -r ../android-cts/repository/results/$resultDirName /mnt/freenas/result/cts/default/$ip_android/android/android_x86/gcc/
fi
