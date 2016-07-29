#!/system/bin/sh 
cd $(dirname "$0")
fileName=$1 
hostType=$2
while true
do
    current_datetime=`date +%s`
    filedate=`stat $fileName | grep Change | awk '{print $2}'`
    filetime=`stat $fileName | grep Change | awk '{split($3,var,".");print var[1]}'`
    file_datetime=`busybox date -d "$filedate $filetime" +%s`
    timedelta=`expr $current_datetime - $file_datetime`
    if [ "$timedelta" -gt "30" ];then
        break
    fi
     
    sleep 10
done

if [ $hostType = "v" ];then
    poweroff
else
    [ -f grub_tmp ] || mkdir grub_tmp
    busybox mount /dev/block/sda2 grub_tmp/ 
    sed -i 's/set default=\"[0-9]*\"/set default=\"1\"/g' grub_tmp/boot/grub/grub.cfg
    reboot
fi
