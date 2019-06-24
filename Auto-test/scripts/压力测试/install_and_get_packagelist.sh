#!/bin/bash
for i in appstoreapk/*.apk;do /home/qin/Android/Sdk/platform-tools/adb shell pm install /sdcard/Download/$i;done
/home/qin/Android/Sdk/platform-tools/adb shell pm list packages | grep -v "android" |grep -v "google"|grep -v "uiautomator"|grep -v "openthos" > tmp1234.txt
sed -i "s/package://g" tmp1234.txt
