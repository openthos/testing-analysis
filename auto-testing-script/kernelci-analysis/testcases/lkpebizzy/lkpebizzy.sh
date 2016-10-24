#!/bin/bash -x
androidIP=$1
port=$2
foldName=$3
cd "$(dirname "$0")"
mkdir $foldName
#adb -s $androidIP:$port push ebizzy /data/local/tmp
#adb -s $androidIP:$port shell /data/local/tmp/ebizzy > $foldName/testResult


if  [  -d $foldName  ];then
      rm -rf  $foldName/* 
fi

adb -s $androidIP:$port  shell  rm -rf  /data/lkp4android
adb -s $androidIP:$port  shell  mkdir /data/lkp4android

adb -s $androidIP:$port   push ./lkp4android /data/lkp4android/

adb -s $androidIP:$port   shell busybox chmod +x /data/lkp4android/ch.sh

adb -s $androidIP:$port   shell /data/lkp4android/ch.sh deploy_test

adb -s $androidIP:$port pull  /result/ebizzy/200%-8x-5s/elwin-virtual-machine/ubuntu/defconfig/gcc-5/4.4.0-22-generic/1/  $foldName/
