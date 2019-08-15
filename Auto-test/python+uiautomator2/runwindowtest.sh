#!/bin/bash
# 作用：对测试机上的所有第三方应用进行窗口测试(调用windowtest.py)，结果放在result_dir目录

ADB=`which adb`

#指定测试机
if [ x"$1" = x ]; then
    echo must define param 1 : ip of guest
    exit
fi

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
package_list=`$ADB shell pm list packages -3 | awk -F ':' '{print $2}' | grep -v "uiautomator"`

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
    python windowtest.py $1 "$package" 2>&1 |tee result_dir/${package}-result.txt
done
