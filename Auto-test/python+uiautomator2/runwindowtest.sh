#!/bin/bash

trap 'onCtrlC' INT
function onCtrlC () {
    echo 'Ctrl+C is captured,stop all tests'
    exit 0
}

if [ ! -d "result_dir" ]; then
        echo "创建文件夹"
        mkdir result_dir
else
	rm result_dir/*
fi

python -m uiautomator2 init
package_list=$(/home/qin/Android/Sdk/platform-tools/adb shell pm list packages -3 | awk -F ':' '{print $2}' | grep -v "uiautomator")

sum=0
for package in $package_list
do
   sum=$((sum+1))
done

count=0
for package in $package_list
do
    count=$((count+1))
    echo "title: [$count/$sum] $package"
    python windowtest.py 192.168.0.166 "$package" 2>&1 |tee result_dir/${package}-result.txt
done
