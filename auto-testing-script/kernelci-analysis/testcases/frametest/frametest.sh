#########################################################################
# File Name: frametest.sh
# Author: wangjx
# mail: wangjianxing5210@163.com
# Created Time: 2016年09月19日 星期一 09时45分57秒
#########################################################################
#!/bin/bash
VIDEO=test.mp4
androidIP=$1
port=$2
foldName=$3
cd "$(dirname "$0")"
mkdir $foldName
adb -s $androidIP:$port push ./app-debug.apk /data/local/tmp/com.example.qin.gamehelper
adb -s $androidIP:$port shell pm install -r "/data/local/tmp/com.example.qin.gamehelper"
adb -s $androidIP:$port push ./app-debug-androidTest.apk /data/local/tmp/com.example.qin.gamehelper.test
adb -s $androidIP:$port shell pm install -r "/data/local/tmp/com.example.qin.gamehelper.test"
adb -s $androidIP:$port shell am instrument -w -r   -e debug false -e class com.example.qin.closeVolume.closeVolume#testDemo com.example.qin.gamehelper.test/android.support.test.runner.AndroidJUnitRunner
adb -s $androidIP:$port shell pm uninstall com.example.qin.gamehelper
adb -s $androidIP:$port install vlc.apk
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
