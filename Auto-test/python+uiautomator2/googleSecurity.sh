#!/bin/bash

#连接测试机
/home/qin/Android/Sdk/platform-tools/adb connect 192.168.0.91:5555

#获取鼠标的eventID
mouseID=`/home/qin/Android/Sdk/platform-tools/adb shell cat /proc/bus/input/devices | grep mouse0 | sed 's/ /\n/g' | sed -n '3p'`
echo $mouseID

#由于命令行安装应用时很难用其它进程来进行UI操作，所以目前用应用商店安装的方式解决google安全对话框
#打开应用商店
/home/qin/Android/Sdk/platform-tools/adb shell am start -n org.openthos.appstore/.MainActivity

sleep 2

#获取测试机的view树
/home/qin/Android/Sdk/platform-tools/adb shell /system/bin/uiautomator dump /data/local/tmp/uidump.xml

#截取关键字EditText所在的位置坐标
x1=`/home/qin/Android/Sdk/platform-tools/adb shell cat /data/local/tmp/uidump.xml | sed 's/></\n/g' | grep EditText |sed 's/ /\n/g' | grep bounds | sed 's/]/\n/g' | sed 's/,/\n/g' | sed 's/\[/\n/g' | sed -n '2p'`
x2=`/home/qin/Android/Sdk/platform-tools/adb shell cat /data/local/tmp/uidump.xml | sed 's/></\n/g' | grep EditText |sed 's/ /\n/g' | grep bounds | sed 's/]/\n/g' | sed 's/,/\n/g' | sed 's/\[/\n/g' | sed -n '5p'`
xc1=`expr $x1 + $x2`
xc=`expr $xc1 / 2`
echo "$xc"

y1=`/home/qin/Android/Sdk/platform-tools/adb shell cat /data/local/tmp/uidump.xml | sed 's/></\n/g' | grep EditText |sed 's/ /\n/g' | grep bounds | sed 's/]/\n/g' | sed 's/,/\n/g' | sed 's/\[/\n/g' | sed -n '3p'`
y2=`/home/qin/Android/Sdk/platform-tools/adb shell cat /data/local/tmp/uidump.xml | sed 's/></\n/g' | grep EditText |sed 's/ /\n/g' | grep bounds | sed 's/]/\n/g' | sed 's/,/\n/g' | sed 's/\[/\n/g' | sed -n '6p'`
yc1=`expr $y1 + $y2`
yc=`expr $yc1 / 2`
echo "$yc"

#鼠标左键点击EditText的坐标位置
`/home/qin/Android/Sdk/platform-tools/adb shell sendevent /dev/input/$mouseID 2 0 -2000`
`/home/qin/Android/Sdk/platform-tools/adb shell sendevent /dev/input/$mouseID 2 1 -2000`
`/home/qin/Android/Sdk/platform-tools/adb shell sendevent /dev/input/$mouseID 0 0 0`
`/home/qin/Android/Sdk/platform-tools/adb shell sendevent /dev/input/$mouseID 2 0 $xc`
`/home/qin/Android/Sdk/platform-tools/adb shell sendevent /dev/input/$mouseID 2 1 $yc`
`/home/qin/Android/Sdk/platform-tools/adb shell sendevent /dev/input/$mouseID 0 0 0`
`/home/qin/Android/Sdk/platform-tools/adb shell sendevent /dev/input/$mouseID 0004 0004 00090001`
`/home/qin/Android/Sdk/platform-tools/adb shell sendevent /dev/input/$mouseID 0001 0272 00000001`
`/home/qin/Android/Sdk/platform-tools/adb shell sendevent /dev/input/$mouseID 0000 0000 00000000`
`/home/qin/Android/Sdk/platform-tools/adb shell sendevent /dev/input/$mouseID 0004 0004 00090001`
`/home/qin/Android/Sdk/platform-tools/adb shell sendevent /dev/input/$mouseID 0001 0272 00000000`
`/home/qin/Android/Sdk/platform-tools/adb shell sendevent /dev/input/$mouseID 0000 0000 00000000`

#搜索2048并下载安装
/home/qin/Android/Sdk/platform-tools/adb shell input text  "2048"
sleep 1


/home/qin/Android/Sdk/platform-tools/adb shell /system/bin/uiautomator dump /data/local/tmp/uidump.xml
x1=`/home/qin/Android/Sdk/platform-tools/adb shell cat /data/local/tmp/uidump.xml | sed 's/></\n/g' | grep app_item_install |sed 's/ /\n/g' | grep bounds | sed 's/]/\n/g' | sed 's/,/\n/g' | sed 's/\[/\n/g' | sed -n '2p'`
x2=`/home/qin/Android/Sdk/platform-tools/adb shell cat /data/local/tmp/uidump.xml | sed 's/></\n/g' | grep app_item_install |sed 's/ /\n/g' | grep bounds | sed 's/]/\n/g' | sed 's/,/\n/g' | sed 's/\[/\n/g' | sed -n '5p'`
xc1=`expr $x1 + $x2`
xc=`expr $xc1 / 2`
echo "$xc"

y1=`/home/qin/Android/Sdk/platform-tools/adb shell cat /data/local/tmp/uidump.xml | sed 's/></\n/g' | grep app_item_install |sed 's/ /\n/g' | grep bounds | sed 's/]/\n/g' | sed 's/,/\n/g' | sed 's/\[/\n/g' | sed -n '3p'`
y2=`/home/qin/Android/Sdk/platform-tools/adb shell cat /data/local/tmp/uidump.xml | sed 's/></\n/g' | grep app_item_install |sed 's/ /\n/g' | grep bounds | sed 's/]/\n/g' | sed 's/,/\n/g' | sed 's/\[/\n/g' | sed -n '6p'`
yc1=`expr $y1 + $y2`
yc=`expr $yc1 / 2`
echo "$yc"

`/home/qin/Android/Sdk/platform-tools/adb shell sendevent /dev/input/$mouseID 2 0 -2000`
`/home/qin/Android/Sdk/platform-tools/adb shell sendevent /dev/input/$mouseID 2 1 -2000`
`/home/qin/Android/Sdk/platform-tools/adb shell sendevent /dev/input/$mouseID 0 0 0`
`/home/qin/Android/Sdk/platform-tools/adb shell sendevent /dev/input/$mouseID 2 0 $xc`
`/home/qin/Android/Sdk/platform-tools/adb shell sendevent /dev/input/$mouseID 2 1 $yc`
`/home/qin/Android/Sdk/platform-tools/adb shell sendevent /dev/input/$mouseID 0 0 0`
`/home/qin/Android/Sdk/platform-tools/adb shell sendevent /dev/input/$mouseID 0004 0004 00090001`
`/home/qin/Android/Sdk/platform-tools/adb shell sendevent /dev/input/$mouseID 0001 0272 00000001`
`/home/qin/Android/Sdk/platform-tools/adb shell sendevent /dev/input/$mouseID 0000 0000 00000000`
`/home/qin/Android/Sdk/platform-tools/adb shell sendevent /dev/input/$mouseID 0004 0004 00090001`
`/home/qin/Android/Sdk/platform-tools/adb shell sendevent /dev/input/$mouseID 0001 0272 00000000`
`/home/qin/Android/Sdk/platform-tools/adb shell sendevent /dev/input/$mouseID 0000 0000 00000000`

#每隔5秒检测“接受”关键字是否出现，出现就点击，否则继续循环，最多循环10次
for i in $(seq 10)
do
	sleep 5
	echo 'No.'$i
	/home/qin/Android/Sdk/platform-tools/adb shell /system/bin/uiautomator dump /data/local/tmp/uidump.xml
	stragree=`/home/qin/Android/Sdk/platform-tools/adb shell cat /data/local/tmp/uidump.xml | sed 's/></\n/g' | grep 接受`
	if [ "$stragree" != "" ]
		then
			x1=`/home/qin/Android/Sdk/platform-tools/adb shell cat /data/local/tmp/uidump.xml | sed 's/></\n/g' | grep 接受 |sed 's/ /\n/g' | grep bounds | sed 's/]/\n/g' | sed 's/,/\n/g' | sed 's/\[/\n/g' | sed -n '2p'`
			x2=`/home/qin/Android/Sdk/platform-tools/adb shell cat /data/local/tmp/uidump.xml | sed 's/></\n/g' | grep 接受 |sed 's/ /\n/g' | grep bounds | sed 's/]/\n/g' | sed 's/,/\n/g' | sed 's/\[/\n/g' | sed -n '5p'`
			xc1=`expr $x1 + $x2`
			xc=`expr $xc1 / 2`
			echo "$xc"

			y1=`/home/qin/Android/Sdk/platform-tools/adb shell cat /data/local/tmp/uidump.xml | sed 's/></\n/g' | grep 接受 |sed 's/ /\n/g' | grep bounds | sed 's/]/\n/g' | sed 's/,/\n/g' | sed 's/\[/\n/g' | sed -n '3p'`
			y2=`/home/qin/Android/Sdk/platform-tools/adb shell cat /data/local/tmp/uidump.xml | sed 's/></\n/g' | grep 接受 |sed 's/ /\n/g' | grep bounds | sed 's/]/\n/g' | sed 's/,/\n/g' | sed 's/\[/\n/g' | sed -n '6p'`
			yc1=`expr $y1 + $y2`
			yc=`expr $yc1 / 2`
			echo "$yc"

			`/home/qin/Android/Sdk/platform-tools/adb shell sendevent /dev/input/$mouseID 2 0 -2000`
			`/home/qin/Android/Sdk/platform-tools/adb shell sendevent /dev/input/$mouseID 2 1 -2000`
			`/home/qin/Android/Sdk/platform-tools/adb shell sendevent /dev/input/$mouseID 0 0 0`
			`/home/qin/Android/Sdk/platform-tools/adb shell sendevent /dev/input/$mouseID 2 0 $xc`
			`/home/qin/Android/Sdk/platform-tools/adb shell sendevent /dev/input/$mouseID 2 1 $yc`
			`/home/qin/Android/Sdk/platform-tools/adb shell sendevent /dev/input/$mouseID 0 0 0`
			`/home/qin/Android/Sdk/platform-tools/adb shell sendevent /dev/input/$mouseID 0004 0004 00090001`
			`/home/qin/Android/Sdk/platform-tools/adb shell sendevent /dev/input/$mouseID 0001 0272 00000001`
			`/home/qin/Android/Sdk/platform-tools/adb shell sendevent /dev/input/$mouseID 0000 0000 00000000`
			`/home/qin/Android/Sdk/platform-tools/adb shell sendevent /dev/input/$mouseID 0004 0004 00090001`
			`/home/qin/Android/Sdk/platform-tools/adb shell sendevent /dev/input/$mouseID 0001 0272 00000000`
			`/home/qin/Android/Sdk/platform-tools/adb shell sendevent /dev/input/$mouseID 0000 0000 00000000`
			exit
	fi
done

/home/qin/Android/Sdk/platform-tools/adb shell am force-stop org.openthos.appstore
