#!/bin/bash

trap 'onCtrlC' INT
function onCtrlC () {
    echo 'Ctrl+C is captured,stop all tests'
    exit 0
}

python -m uiautomator2 init
package_list=$(/home/qin/Android/Sdk/platform-tools/adb shell pm list packages -3 | awk -F ':' '{print $2}' | grep -v "uiautomator")
for package in $package_list
do
    echo "$package"
    python windowtest.py 192.168.0.166 "$package"
done
