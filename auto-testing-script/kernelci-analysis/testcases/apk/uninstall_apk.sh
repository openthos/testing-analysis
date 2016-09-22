#!/bin/sh -xe
###################################
# filename : apkinstall
# author:  liu mingming
# date:    2016.04.06
# function: auto install apk
# Implementing Measure:sh apkinstall.sh
###################################

androidIP=$1
port=$2

#APKPATH=$(pwd)/apk
#FILELSIT=`ls $APKPATH`
FILELSIT=`ls *.apk`
for file in $FILELSIT
do
echo "uninstall $file ..."
adb -s $androidIP:$port uninstall $file
sleep 1
done

