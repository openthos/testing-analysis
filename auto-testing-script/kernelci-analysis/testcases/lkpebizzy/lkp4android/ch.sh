#!/system/xbin/sh
export PATH=/bin:/usr/bin:/system/xbin:/sbin:/vendor/bin:/system/sbin:/system/bin

mount -o remount,rw /

cd "$(dirname "$0")" 

program_deploy()
{
mkdir /bin/
mkdir -p /usr/bin/


ln -s /system/xbin/sh  /bin/sh
#ln -s /system/xbin/busybox  /bin/gzip
ln -s  /system/xbin/busybox /usr/bin/time
ln -s  /system/xbin/kill /bin/kill
ln -s  /system/xbin/ps  /bin/ps



rm -rf android_lkp

tar zxf android_lkp.tar.gz

rm -rf   /lkp/  /lkp_orig/  /result/
ln -s  $(pwd)/android_lkp/lkp /
ln -s $(pwd)/android_lkp/lkp_orig/ /
ln -s  $(pwd)/android_lkp/result/ /


cp  cat /bin/cat
chmod +x /bin/cat

cp  gzip /bin/gzip
chmod +x /bin/gzip


cp tee /usr/bin/
chmod +x /usr/bin/tee

cp nproc /usr/bin/
chmod +x  /usr/bin/nproc

cp truncate  /usr/bin
chmod +x /usr/bin/truncate


rm -f /lkp_orig/lkp-tests-master/monitors/event/wakeup
rm -f /lkp_orig/lkp-tests-master/monitors/event/wait

cp wakeup   /lkp_orig/lkp-tests-master/monitors/event/
chmod +x  /lkp_orig/lkp-tests-master/monitors/event/wakeup

cd  /lkp_orig/lkp-tests-master/monitors/event/
ln -s wakeup  ./wait

cd  -
cp ebizzy  /lkp/benchmarks/ebizzy/ebizzy
chmod +x  /lkp/benchmarks/ebizzy/ebizzy


chmod +x  -R  /lkp  /result  /lkp_orig


export TMP=/tmp/lkp-root
mkdir  -p $TMP



}


do_test()
{


export  LKP_SRC=/lkp_orig/lkp-tests-master 
export  PATH=$PATH:$LKP_SRC/bin
export  BENCHMARK_ROOT=/lkp/benchmarks
export  RESULT_ROOT=/result/ebizzy/200%-8x-5s/elwin-virtual-machine/ubuntu/defconfig/gcc-5/4.4.0-22-generic/1/
export  TMP_RESULT_ROOT=$RESULT_ROOT
export  TMP=/tmp/lkp-root


if  [ ! -d $LKP_SRC  ];then
      program_deploy 
fi



cd $RESULT_ROOT 
mv job.sh ../
rm -rf ./*
mv ../job.sh ./

cd  $TMP
rm -rf ./*


$RESULT_ROOT/job.sh run_job
sleep 10
$LKP_SRC/bin/post-run
sleep 10
$LKP_SRC/monitors/event/wakeup job-finished
}



deploy_test()
{
program_deploy
do_test
}

"$@"
