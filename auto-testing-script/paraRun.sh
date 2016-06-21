#!/bin/bash -x
cd "$(dirname "$0")" 
port=52000

if [ ! -d "~/android-cts" ]; then
  	cp ../android-cts ~/ -r
fi

while read line
do
	let port+=1
	cp ../cts-autotest ~/cts-autotest"$port" -r
	cmd="/autoTest.sh "
	#cmd=$cmd"$line"" $port"
	eval "~/cts-autotest$port$cmd $line $port" &
	echo "done!"
    #rm ~/cts-autotest"$port" -r
done < configs

wait
exit



# ./autoTest.sh v install localhost ../android-x86.raw "-p android.acceleration --disable-reboot" ../xyl_android_x86_64_6.0.iso
# ./autoTest.sh r install 192.168.2.82 /dev/sda40 "-p android.acceleration --disable-reboot" ../xyl_android_x86_64_6.0.iso
# ./autoTest.sh r install 192.168.2.80 /dev/sda40 "-p android.acceleration --disable-reboot" ../xyl_android_x86_64_6.0.iso
# ./autoTest.sh r install 192.168.2.17 /dev/sda40 "-p android.acceleration --disable-reboot" ../xyl_android_x86_64_6.0.iso
#v install localhost /media/aquan/000D204000041550/android-x86.raw "-p android.acceleration --disable-reboot" ../xyl_android_x86_64_6.0.iso
#v install localhost /media/aquan/000D204000041550/research/android-x86-6.0.raw "-p android.acceleration --disable-reboot" /home/aquan/git/xyl_android_x86_64_6.0.iso
#v install localhost /media/aquan/000D204000041550/android-x86.raw "-p android.acceleration --disable-reboot" /home/aquan/git/xyl_android_x86_64_6.0.iso
#v install localhost /media/aquan/000D204000041550/research/android-x86-6.0.raw "-p android.acceleration --disable-reboot" /home/aquan/git/xyl_android_x86_64_6.0.iso