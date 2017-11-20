#!/bin/bash -xe
cd "$(dirname "$0")" 
port=52000


if [ -n "$1" ] ;then
    iso=$1
    tmp=${iso#*-}
    commitId=${tmp%-*}
    echo "commitId:$commitId" > commitId.txt
fi

while read line
do 
    if [[ $line"x" == "x" ]];then 
        break
    fi
    while true
    do
	let port+=1
        lsof -i:$port || break
    done
	cmd="./autoTest.sh "
    echo $port
	#cmd=$cmd"$line"" $port"
	eval "$cmd $port $line" > "log_"$(echo $line|awk '{print $3}')".txt" 2>&1 &
	#echo "done!"
done < configs
wait

## result to json
/var/www/html/run_py.sh

pkill adb
pkill nc
kill `ps -axu | grep qemu-system-x86_64 | awk '{print $2}'`
qemupid=`ps -axu | grep qemu-system-x86_64 | awk '{print $2}'`
[ ! -n "$qemupid" ] && kill $qemupid
exit 0



# ./autoTest.sh v install localhost ../android-x86.raw "-p android.acceleration --disable-reboot" ../xyl_android_x86_64_6.0.iso
# ./autoTest.sh r install 192.168.2.82 /dev/sda40 "-p android.acceleration --disable-reboot" ../xyl_android_x86_64_6.0.iso
# ./autoTest.sh r install 192.168.2.80 /dev/sda40 "-p android.acceleration --disable-reboot" ../xyl_android_x86_64_6.0.iso
# ./autoTest.sh r install 192.168.2.17 /dev/sda40 "-p android.acceleration --disable-reboot" ../xyl_android_x86_64_6.0.iso
#v install localhost /media/aquan/000D204000041550/android-x86.raw "-p android.acceleration --disable-reboot" ../xyl_android_x86_64_6.0.iso
#v install localhost /media/aquan/000D204000041550/research/android-x86-6.0.raw "-p android.acceleration --disable-reboot" /home/aquan/git/xyl_android_x86_64_6.0.iso
#v install localhost /media/aquan/000D204000041550/android-x86.raw "-p android.acceleration --disable-reboot" /home/aquan/git/xyl_android_x86_64_6.0.iso
#v install localhost /media/aquan/000D204000041550/research/android-x86-6.0.raw "-p android.acceleration --disable-reboot" /home/aquan/git/xyl_android_x86_64_6.0.iso
