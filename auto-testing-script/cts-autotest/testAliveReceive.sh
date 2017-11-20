#!/system/bin/sh 
#cd $(dirname "$0")
cd `dirname $0`
fileName=$1 
hostType=$2
testType=$3
while true
do
    current_datetime=`date +%s`
    filedate=`stat $fileName | grep Change | awk '{print $2}'`
    filetime=`stat $fileName | grep Change | awk '{split($3,var,".");print var[1]}'`
    file_datetime=`busybox date -d "$filedate $filetime" +%s`
    timedelta=`expr $current_datetime - $file_datetime`
    #if [ "$timedelta" -gt "180" ];then
    if [ "$timedelta" -gt "15" ];then
	fileCon=`cat $fileName`
    	if [ "$fileCon"x = "Exit"x ];then
		exit 0
	fi

    	if [ "$fileCon"x = "Reboot"x ] || [ "$timedelta" -gt "60" ];then
		#reboot android
        	break
	fi

	#restart /sbin/adbd
	stop adbd; start adbd
    fi
    #sleep 60
    sleep 10
done

if [ "$hostType" = "v" ];then
    touch poweroffByTestAvlive
    if [ "$testType" = "cts" ];then
        poweroff 
    else
        reboot 
    fi
else
    touch rebootByTestAvlive
###
#    [ -f grub_tmp ] || mkdir grub_tmp
#    busybox mount /dev/block/sda2 grub_tmp/ 
#    if [ $testType == "cts" ];then
#        sed -i 's/set default=\"[0-9]*\"/set default=\"1\"/g' grub_tmp/boot/grub/grub.cfg
#    fi
###
    reboot
fi
