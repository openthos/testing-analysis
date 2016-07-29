#!/bin/bash -x
otoid=$1
result=/mnt/freenas
ctsautotest=../cts-autotest
iso=/mnt/freenas/work/out/target/product/x86_64/android_x86_64-$otoid-5.1.iso
#iso=/mnt/freenas/work/out/target/product/x86_64/android_x86_64-042a1f66011f4f4e2cf710885f3d55bd8a882eb8-5.1.iso

cd `dirname $0`
start=$(date "+%s")
./compile.sh $otoid > $result/compile/`date +%Y%m%d`-$otoid 2>&1
now=$(date "+%s")
time=$((now-start))
echo "compile time:$time s"
#cp /home/aquan/cts/android-x86.raw /mnt/freenas/android-x86.raw
cp ../android-x86.raw ../cts-autotest/
$ctsautotest/paraRun.sh $iso
now1=$(date "+%s")
time=$((now1-now))
echo "deploy time:$time s"
#./test.sh
now=$(date "+%s")
time=$((now-now1))
num=3
echo "exec $num testcases"
#echo "test time:$time s"
