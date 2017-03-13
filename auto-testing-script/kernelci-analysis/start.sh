#!/bin/bash -xu
[ `ps -axu | grep updateGIT.sh | wc -l` -gt 1 ] && exit 
cd `dirname $0`
crontDate=`date +%y%m%d`
./updateGIT.sh >> /mnt/freenas/result/cronout$crontDate 2>&1
