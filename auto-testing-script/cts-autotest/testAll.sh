#!/bin/bash 
cd "$(dirname "$0")" 
#./autoTest.sh r 192.168.0.70 /dev/sda40 run all "-p android.acceleration --disable-reboot" > testlog.txt 2>&1
./autoTest.sh r 192.168.0.70 /dev/sda40 installTest ../android_x86_64-5.1.iso "--plan CTS --disable-reboot" > testlog.txt 2>&1
#./autoTest.sh r 192.168.0.70 /dev/sda40 installTest ../android_x86_64-5.1.iso "-p android.acceleration --disable-reboot" > testlog.txt 2>&1


#user="oto"
#cp2host="aquan@166.111.197:/"
#cp2port="11281"
#user="root"
#cp2host="aquan@192.168.2.39:/"
#cp2port="22"

testingUser="oto"
testingIP="166.111.131.12"
testingPort="6622"
testingFold="/home/$testingUser/cts/"


remoteUser="aquan"
remoteHost="166.111.68.197"
remotePort="11281"
remoteFold="/home/aquan/"

result="cts/android-cts/repository/results/*.zip"

[ -f /home/$testingUser/$result ] && scp -P $remotePort /home/$testingUser/$result $remoteUser@$remoteHost:$remoteFold
[ -f /home/$testingUser/cts/cts-autotest/*result.tar ] && scp -P $remotePort /home/$testingUser/cts/cts-autotest/*result.tar $remoteUser@$remoteHost:$remoteFold

rm /home/$testingUser/cts/cts-autotest/*result.tar >> testlog.txt 2>&1
rm /home/$testingUser/$result >> testlog.txt 2>&1
