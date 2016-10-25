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
echo "install $file ..."
adb -s $androidIP:$port install -r $file
sleep 1
done

