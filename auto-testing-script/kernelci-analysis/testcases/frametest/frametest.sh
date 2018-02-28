#!/bin/bash -xu

#########################################################################
# File Name: frametest.sh
# Author: wangjx
# mail: wangjianxing5210@163.com
# Created Time: 2016年09月19日 星期一 09时45分57秒
#########################################################################

VIDEO=test.mp4
androidIP=$1
port=$2
foldName=$3
cd "$(dirname "$0")"
mkdir $foldName

sleep 10
adb -s $androidIP:$port shell input keyevent 66
adb -s $androidIP:$port shell input keyevent 111
adb -s $androidIP:$port install vlc.apk
adb -s $androidIP:$port shell am start -a android.intent.action.VIEW -d file:///storage/emulated/legacy/Movies/big_buck_bunny_480p_surround-fix.mp4 -t video/* -n org.videolan.vlc/org.videolan.vlc.StartActivity
sleep 5
adb -s $androidIP:$port shell input keyevent 25
sleep 1
adb -s $androidIP:$port shell input keyevent 25
sleep 1
adb -s $androidIP:$port shell input keyevent 25
sleep 1
adb -s $androidIP:$port shell input keyevent 25
sleep 1
adb -s $androidIP:$port shell input keyevent 25
sleep 1
adb -s $androidIP:$port shell input keyevent 25
sleep 1
adb -s $androidIP:$port shell input keyevent 25
sleep 1
adb -s $androidIP:$port shell input keyevent 25
sleep 1
adb -s $androidIP:$port shell input keyevent 25
sleep 1
adb -s $androidIP:$port shell input keyevent 25
sleep 1
adb -s $androidIP:$port shell input keyevent 25
sleep 20

adb -s $androidIP:$port push $VIDEO /storage/emulated/legacy/DCIM/Camera/$VIDEO
adb -s $androidIP:$port shell mkdir /data/gputrace
adb -s $androidIP:$port push gputrace-gen /data/gputrace/
adb -s $androidIP:$port shell chmod 777 /data/gputrace/gputrace-gen
#adb -s $androidIP:$port shell am start -n org.videolan.vlc/org.videolan.vlc.StartActivity
adb -s $androidIP:$port shell am start -a android.intent.action.VIEW -d file:///storage/emulated/legacy/DCIM/Camera/$VIDEO -t video/* -n org.videolan.vlc/org.videolan.vlc.StartActivity

adb -s $androidIP:$port shell < script
adb -s $androidIP:$port pull /data/gputrace/gputracelog $foldName/
adb -s $androidIP:$port shell rm /storage/emulated/legacy/DCIM/Camera/$VIDEO
adb -s $androidIP:$port shell rm /data/gputrace/gputracelog
adb -s $androidIP:$port shell rm /data/gputrace/gputrace-gen
line=`cat $foldName/gputracelog |grep drm_vblank_event|wc -l`
frame=`expr $line / 150`
echo $frame > $foldName/testResult
