#!/bin/bash
# 作用：用鼠标模拟点击的方式解决首次安装应用时弹出google安全对话框无法点击的问题

ADB=`which adb`

#连接测试机
if [ x"$1" = x ]; then
    echo must define param 1 : ip of guest
    exit
fi
$ADB connect $1

#获取鼠标的eventID
mouseID=`$ADB shell grep mouse0 /proc/bus/input/devices | cut -d ' ' -f 3`
echo mouseID:$mouseID

#由于命令行安装应用时很难用其它进程来进行UI操作，所以目前用应用商店安装的方式解决google安全对话框
#打开应用商店
$ADB shell am start -n org.openthos.appstore/.MainActivity

sleep 2

#获取测试机的view树
$ADB shell /system/bin/uiautomator dump /data/local/tmp/uidump.xml

#截取关键字EditText所在的位置坐标
x1=`$ADB shell cat /data/local/tmp/uidump.xml | sed 's/></\n/g' | grep EditText |sed 's/ /\n/g' | grep bounds | sed 's/]/\n/g' | sed 's/,/\n/g' | sed 's/\[/\n/g' | sed -n '2p'`
x2=`$ADB shell cat /data/local/tmp/uidump.xml | sed 's/></\n/g' | grep EditText |sed 's/ /\n/g' | grep bounds | sed 's/]/\n/g' | sed 's/,/\n/g' | sed 's/\[/\n/g' | sed -n '5p'`
xc1=`expr $x1 + $x2`
xc=`expr $xc1 / 2`
echo "$xc"

y1=`$ADB shell cat /data/local/tmp/uidump.xml | sed 's/></\n/g' | grep EditText |sed 's/ /\n/g' | grep bounds | sed 's/]/\n/g' | sed 's/,/\n/g' | sed 's/\[/\n/g' | sed -n '3p'`
y2=`$ADB shell cat /data/local/tmp/uidump.xml | sed 's/></\n/g' | grep EditText |sed 's/ /\n/g' | grep bounds | sed 's/]/\n/g' | sed 's/,/\n/g' | sed 's/\[/\n/g' | sed -n '6p'`
yc1=`expr $y1 + $y2`
yc=`expr $yc1 / 2`
echo "$yc"

#鼠标左键点击EditText的坐标位置
`$ADB shell sendevent /dev/input/$mouseID 2 0 -2000`
`$ADB shell sendevent /dev/input/$mouseID 2 1 -2000`
`$ADB shell sendevent /dev/input/$mouseID 0 0 0`
`$ADB shell sendevent /dev/input/$mouseID 2 0 $xc`
`$ADB shell sendevent /dev/input/$mouseID 2 1 $yc`
`$ADB shell sendevent /dev/input/$mouseID 0 0 0`
`$ADB shell sendevent /dev/input/$mouseID 0004 0004 00090001`
`$ADB shell sendevent /dev/input/$mouseID 0001 0272 00000001`
`$ADB shell sendevent /dev/input/$mouseID 0000 0000 00000000`
`$ADB shell sendevent /dev/input/$mouseID 0004 0004 00090001`
`$ADB shell sendevent /dev/input/$mouseID 0001 0272 00000000`
`$ADB shell sendevent /dev/input/$mouseID 0000 0000 00000000`

#搜索2048并下载安装
$ADB shell input text  "2048"
sleep 1


$ADB shell /system/bin/uiautomator dump /data/local/tmp/uidump.xml
x1=`$ADB shell cat /data/local/tmp/uidump.xml | sed 's/></\n/g' | grep app_item_install |sed 's/ /\n/g' | grep bounds | sed 's/]/\n/g' | sed 's/,/\n/g' | sed 's/\[/\n/g' | sed -n '2p'`
x2=`$ADB shell cat /data/local/tmp/uidump.xml | sed 's/></\n/g' | grep app_item_install |sed 's/ /\n/g' | grep bounds | sed 's/]/\n/g' | sed 's/,/\n/g' | sed 's/\[/\n/g' | sed -n '5p'`
xc1=`expr $x1 + $x2`
xc=`expr $xc1 / 2`
echo "$xc"

y1=`$ADB shell cat /data/local/tmp/uidump.xml | sed 's/></\n/g' | grep app_item_install |sed 's/ /\n/g' | grep bounds | sed 's/]/\n/g' | sed 's/,/\n/g' | sed 's/\[/\n/g' | sed -n '3p'`
y2=`$ADB shell cat /data/local/tmp/uidump.xml | sed 's/></\n/g' | grep app_item_install |sed 's/ /\n/g' | grep bounds | sed 's/]/\n/g' | sed 's/,/\n/g' | sed 's/\[/\n/g' | sed -n '6p'`
yc1=`expr $y1 + $y2`
yc=`expr $yc1 / 2`
echo "$yc"

`$ADB shell sendevent /dev/input/$mouseID 2 0 -2000`
`$ADB shell sendevent /dev/input/$mouseID 2 1 -2000`
`$ADB shell sendevent /dev/input/$mouseID 0 0 0`
`$ADB shell sendevent /dev/input/$mouseID 2 0 $xc`
`$ADB shell sendevent /dev/input/$mouseID 2 1 $yc`
`$ADB shell sendevent /dev/input/$mouseID 0 0 0`
`$ADB shell sendevent /dev/input/$mouseID 0004 0004 00090001`
`$ADB shell sendevent /dev/input/$mouseID 0001 0272 00000001`
`$ADB shell sendevent /dev/input/$mouseID 0000 0000 00000000`
`$ADB shell sendevent /dev/input/$mouseID 0004 0004 00090001`
`$ADB shell sendevent /dev/input/$mouseID 0001 0272 00000000`
`$ADB shell sendevent /dev/input/$mouseID 0000 0000 00000000`

#每隔5秒检测“接受”关键字是否出现，出现就点击，否则继续循环，最多循环10次
for i in $(seq 10)
do
	sleep 5
	echo 'No.'$i
	$ADB shell /system/bin/uiautomator dump /data/local/tmp/uidump.xml
	stragree=`$ADB shell cat /data/local/tmp/uidump.xml | sed 's/></\n/g' | grep 接受`
	if [ "$stragree" != "" ]
		then
			x1=`$ADB shell cat /data/local/tmp/uidump.xml | sed 's/></\n/g' | grep 接受 |sed 's/ /\n/g' | grep bounds | sed 's/]/\n/g' | sed 's/,/\n/g' | sed 's/\[/\n/g' | sed -n '2p'`
			x2=`$ADB shell cat /data/local/tmp/uidump.xml | sed 's/></\n/g' | grep 接受 |sed 's/ /\n/g' | grep bounds | sed 's/]/\n/g' | sed 's/,/\n/g' | sed 's/\[/\n/g' | sed -n '5p'`
			xc1=`expr $x1 + $x2`
			xc=`expr $xc1 / 2`
			echo "$xc"

			y1=`$ADB shell cat /data/local/tmp/uidump.xml | sed 's/></\n/g' | grep 接受 |sed 's/ /\n/g' | grep bounds | sed 's/]/\n/g' | sed 's/,/\n/g' | sed 's/\[/\n/g' | sed -n '3p'`
			y2=`$ADB shell cat /data/local/tmp/uidump.xml | sed 's/></\n/g' | grep 接受 |sed 's/ /\n/g' | grep bounds | sed 's/]/\n/g' | sed 's/,/\n/g' | sed 's/\[/\n/g' | sed -n '6p'`
			yc1=`expr $y1 + $y2`
			yc=`expr $yc1 / 2`
			echo "$yc"

			`$ADB shell sendevent /dev/input/$mouseID 2 0 -2000`
			`$ADB shell sendevent /dev/input/$mouseID 2 1 -2000`
			`$ADB shell sendevent /dev/input/$mouseID 0 0 0`
			`$ADB shell sendevent /dev/input/$mouseID 2 0 $xc`
			`$ADB shell sendevent /dev/input/$mouseID 2 1 $yc`
			`$ADB shell sendevent /dev/input/$mouseID 0 0 0`
			`$ADB shell sendevent /dev/input/$mouseID 0004 0004 00090001`
			`$ADB shell sendevent /dev/input/$mouseID 0001 0272 00000001`
			`$ADB shell sendevent /dev/input/$mouseID 0000 0000 00000000`
			`$ADB shell sendevent /dev/input/$mouseID 0004 0004 00090001`
			`$ADB shell sendevent /dev/input/$mouseID 0001 0272 00000000`
			`$ADB shell sendevent /dev/input/$mouseID 0000 0000 00000000`
			exit
	fi
done

$ADB shell am force-stop org.openthos.appstore
