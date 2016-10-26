#!/bin/bash -ex
otoid=$1
result=/mnt/freenas
ctsautotest=../cts-autotest
iso=/mnt/freenas/work/out/target/product/x86_64/android_x86_64-$otoid-5.1.iso
#iso=/mnt/freenas/work/out/target/product/x86_64/android_x86_64-042a1f66011f4f4e2cf710885f3d55bd8a882eb8-5.1.iso

cd `dirname $0`
start=$(date "+%s")
echo 'start compile'
compileLogName=`date +%Y%m%d`-$otoid
./compile.sh $otoid $compileLogName > $result/compile/$compileLogName 2>&1

if [ $? -ne 0 ];
then
	exit
fi

now=$(date "+%s")
time=$((now-start))
echo "compile time:$time s"

#cp ../android-x86.raw ../cts-autotest/
$ctsautotest/paraRun.sh $iso
now1=$(date "+%s")
time=$((now1-now))
echo "deploy time:$time s"
now=$(date "+%s")
time=$((now-now1))
num=3
echo "exec $num testcases"
#echo "test time:$time s"
