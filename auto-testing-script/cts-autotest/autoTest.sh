#!/bin/bash -xu
# install related tools and download related packages

# run qemu
#./qemu-system-x86_64 -m 4G --enable-kvm -net nic,vlan=0 -net tap,vlan=0,ifname=tap0,script=no ../../android-x86/android_x86.iso

#jdk7_home=/usr/lib/jvm/java-7-openjdk-and64/jre/bin/java
#jdk8_home=/usr/lib/jvm/java-8-oracle/jre/bin/java
## change the java version command
## update-alternatives --config java

##########################################################################################################################
## $1 : listen port(start from 52001)
## $2 : virtual mechine or real mechine (v/r)
## $3 : ip of client linux system, if you test local android_x86, use localhost or 127.0.0.1
## $4 : host name for distinguish the machine
## $5: path of disk(/dev/sda40) or virtual disk(../rawiso/android_x86.raw)
## $6 : run android_x86(run) or install android_x86.iso(install), install and run the testcase(installTest)
## if $6 == install || $6 == installTest
    ## $7: location of android_x86.iso
## if $6 == run
    ## $7: type of test(lkp/cts/all)
        ## if $7 == cts || $7 == all
            ## $8: cts command that need to be excuted
        ## if $7 == lkp
            ## It's enough

## eg: ./autoTest.sh 52001 r 192.168.2.16 PC1 /dev/sda40 install android_x86_64-a3fe26d154ef92a708b7faa488571899aa5bcab4-5.1.iso
## eg: ./autoTest.sh 52001 r 192.168.2.16 PC1 /dev/sda40 installTest android_x86_64-a3fe26d154ef92a708b7faa488571899aa5bcab4-5.1.iso "-p android.acceleration --disable-reboot"
## eg: ./autoTest.sh 52001 r 192.168.2.16 PC1 /dev/sda40 run cts "-p android.acceleration --disable-reboot"
## eg: ./autoTest.sh 52001 r 192.168.2.16 PC1 /dev/sda40 run all "-p android.acceleration --disable-reboot"
## eg: ./autoTest.sh 52001 r 192.168.2.16 PC2 /dev/sda40 run lkp
## eg: ./autoTest.sh 52001 r 192.168.2.16 PC2 /dev/sda40 run gui
## eg: ./autoTest.sh 52001 v localhost QEMU1 /media/aquan/000D204000041550/android-x86.raw  installTest android_x86_64-a3fe26d154ef92a708b7faa488571899aa5bcab4-5.1.iso "-p android.acceleration --disable-reboot"

cd "$(dirname "$0")"
localpwd=`pwd`
export localpwd

# listening port, user should specify it when parallel tesing
ListenPort=$1
export ListenPort
adbPort=$(($ListenPort+99))
while true
do
    let adbPort+=1
    lsof -i:$adbPort || break
done
export adbPort
r_v=$2
export r_v
ip_linux_client=$3
host=$4
export host
disk_path=$5
export disk_path
run_install=$6
export run_install
ip_linux_host=`/sbin/ifconfig -a|grep inet|grep -v 127.0.0.1|grep -v inet6|awk '{print $2}'|tr -d "addr:"|tr -d "地址:"|grep 192`
export ip_linux_host
ip_android="0.0.0.0"
iso_loc="default"
testcaseFold="../kernelci-analysis/testcases"
export testcaseFold
testcaseLKP="../../../oto_external_lkp/testcase"
export testcaseLKP
testcaseCTS="../../../android-cts"
export testcaseCTS
testcaseGUI="../../../oto_external_Uitest"
export testcaseGUI
qemuCMD="/usr/local/bin/qemu-system-x86_64 -m 4G"
export qemuCMD

testType="default"

function assert()
{
    ret=$1
    if [ $ret -ne 0 ];then
        ./cpResult.sh
        ./android_fastboot.sh  ${ip_android}  reboot_bootloader
        exit 1
    fi
}

function assert_v()
{
    ret=$1
    qemuPid=$2
    if [ $ret -ne 0 ];then
        kill $qemuPid
        ./cpResult.sh
        exit 1
    fi
}


#check the ip address
./checkIP.sh $ip_linux_client
if [ $? -ne 0 ];then
    python sendEmail.py "This computer has something wrong, network is blocked, its ip is: $ip_linux_client" $ip_linux_client
    exit 1
fi


function getCommitId()
{
    tmp=${iso_loc##*/}
    tmp=${tmp#*-}
    commitId=${tmp%-*}
    export commitId
}

function tradefedMonitor()
{
    tradefedPid=$1
    sleepTimes=0
    ### sleep 23 hours, if the cts-tradefed is not exit, we kill it.
    while [ $sleepTimes -lt 460 ]
    do
        sleep 180
        ps -ax | awk '{ print $1 }' | grep -e "^${tradefedPid}$"
        if [ $? -ne 0 ];then
            return 0
        fi
        sleepTimes=$((sleepTimes+1))
    done
    if [ $sleepTimes -eq 100 ];then
        kill $tradefedPid
        return -1
    else
        return 0
    fi
}

## according to where it's virtual mechine(qemu) or real mechine, we should change the network model
if [ "$r_v" == "v" ]; then
    cp ../../../android-x86.backup.raw $disk_path
    ip_android="localhost"
    export ip_android

    if [ "$run_install" == "installTest" ] || [ "$run_install" == "install" ];then
        ## install iso and then test the android-x86
        iso_loc=$7
        export iso_loc
        getCommitId
        ./fastboot_vir.sh flashall
        ./editBoot.sh
        #assert $?


        ## install CtsDeviceAdmin.apk and active the device adminstrators, this setting will take effect after reboot
        $qemuCMD --enable-kvm -net nic -net user,hostfwd=tcp::$adbPort-:5555 $disk_path -vnc :1 &
        {
            echo v1v1v1v1!!!!!!!!!!!!!!!!!!!!!
            nc -l $ListenPort
            ## waiting for a message from android-x86, this ip address is useful in real mechine test, but in virtural mechine ,we adopt nat address mapping ,
            ## so it's just a symbol that android-x86 is running
            echo 'waiting for android boot !!!!!'
            adb connect $ip_android:$adbPort
            sleep 2
            adb -s $ip_android:$adbPort shell svc power stayon true
	    echo 'shell svc power stayon finish'
            adb -s $ip_android:$adbPort shell system/checkAndroidDesktop.sh
            echo 'system/checkAndroidDesktop.sh finish'
            sleep 30
            adb -s $ip_android:$adbPort push bin/firstlogin.jar /data/local/tmp
	    echo 'push bin/firstlogin.jar finish'
            adb -s $ip_android:$adbPort shell uiautomator runtest firstlogin.jar -c com.firstlogin.firstlogin
	    echo 'runtest firstlogin.jar finish'

            ##startmenuTest
            echo 'startmenutest'
            adb -s $ip_android:$adbPort push bin/StartMenuTest.apk /data/local/tmp/com.example.qin.startmenutest || { echo "push StartMenuTest.apk failed" ; exit 1 ; }
            adb -s $ip_android:$adbPort shell pm install -r "/data/local/tmp/com.example.qin.startmenutest"
            adb -s $ip_android:$adbPort push bin/StartMenuTest-androidTest.apk /data/local/tmp/com.example.qin.startmenutest.test || { echo "push StartMenuTest-androidTest.apk failed" ; exit 1 ; }
            adb -s $ip_android:$adbPort shell pm install -r "/data/local/tmp/com.example.qin.startmenutest.test"
            adb -s $ip_android:$adbPort shell am instrument -w -r   -e debug false -e class com.example.qin.startmenutest.StartMenuTest1 com.example.qin.startmenutest.test/android.support.test.runner.AndroidJUnitRunner
            adb -s $ip_android:$adbPort shell pm uninstall com.example.qin.startmenutest

            ##keep screen active
            ## install CtsDeviceAdmin.apk
            echo 'install CtsDeviceAdmin.apk!!!!!'
            adb -s $ip_android:$adbPort install $testcaseCTS/repository/testcases/CtsDeviceAdmin.apk
            adb -s $ip_android:$adbPort push device_policies.xml data/system/device_policies.xml
            adb -s $ip_android:$adbPort push commitId.txt data/
            adb -s $ip_android:$adbPort shell poweroff
	    sleep 5
        }
    fi

    if [ "$run_install" == "installTest" ];then
        $qemuCMD --enable-kvm -net nic -net user,hostfwd=tcp::$adbPort-:5555 $disk_path -vnc :2 &
        {
            qemuPid=$!
            nc -l $ListenPort
            ## gui haven't been loaded completely for android_x86-5.1
            adb connect $ip_android:$adbPort
            sleep 2
            adb -s $ip_android:$adbPort shell system/checkAndroidDesktop.sh
            sleep 30
            cts_cmd="$8"

             ./runGuiTestInFold.sh $testcaseFold
            assert_v $? $qemuPid
            sleep 2
            ### monitor script, if network is down, reboot to linux
            ./testAliveSend.sh &
            echo "exit" | $testcaseCTS/tools/cts-tradefed run cts -s $ip_android:$adbPort $cts_cmd &
            {
                tradefedMonitor $!
                if [ $? -eq 0 ];then
                    adb -s $ip_android:$adbPort shell poweroff
		    sleep 5
                else
                    kill qemuPid
                    cp ../../../android_x86.backup.raw $disk_path
#                    python sendEmail.py "something went wrong while run cts test in QEMU!" $ip_android
                fi
            }
        }
    fi

    if [ "$run_install" == "run" ];then

        ./editBoot.sh
        assert $?
        $qemuCMD --enable-kvm -net nic -net user,hostfwd=tcp::$adbPort-:5555 $disk_path -vnc :3 &
        {
            pid=$!
            echo v3v3v3!!!!!!!!!!!!!!!!!!!!!!!!
            nc -l $ListenPort
            echo 'waiting for android boot !!!!!'

            adb connect $ip_android:$adbPort
            sleep 2
            adb -s $ip_android:$adbPort shell system/checkAndroidDesktop.sh
            tmp=`adb -s localhost:adbPort shell cat data/commitId.txt | grep commitId -v WARNING`
            commitId=${tmp##*:}
            commitId=${commitId%?}
            sleep 5

            ### monitor script, if network is down, reboot to linux
            ./testAliveSend.sh &

            testType=$7
            export testType
            if [ "$testType" == "cts" ];then
                cts_cmd="$8"
                echo "exit" | $testcaseCTS/tools/cts-tradefed run cts -s $ip_android:$adbPort $cts_cmd &
                {
                    tradefedMonitor $!
                    if [ $? -eq 0 ];then
                        adb -s $ip_android:$adbPort shell poweroff
			sleep 5
                    else
                        cp ../../../android_x86.backup.raw $disk_path
#                        python sendEmail.py "something went wrong while run cts test in QEMU!" $ip_android
                    fi
                }
            elif [ "$testType" == "gui" ];then
                #./runGuiTestInFold.sh $testcaseFold
                #assert $?
                echo "gui test not available!"
            elif [ "$testType" == "lkp" ];then
                #./runGuiTestInFold.sh $testcaseFold
                #assert $?
                echo "lkp test not available!"
            elif [ "$testType" == "all" ];then
                cts_cmd="$8"
                #runTestInFold $testcaseGUI
                #runTestInFold $testcaseLKP
                ./runGuiTestInFold.sh $testcaseFold
                assert $?
                sleep 2
                echo "exit" | $testcaseCTS/tools/cts-tradefed run cts -s $ip_android:$adbPort $cts_cmd &
                {
                    tradefedMonitor $!
                    if [ $? -eq 0 ];then
                        adb -s $ip_android:$adbPort shell poweroff
			sleep 5
                    else
                        cp ../../../android_x86.backup.raw $disk_path
#                        python sendEmail.py "something went wrong while run cts test in QEMU!" $ip_android
                    fi
                }
            fi
        }
    fi

elif [ "$r_v" == "r" ];then
    adbPort=5555
    export adbPort
    if [ "$run_install" == "run" ];then
        ## real mechine
        rsync   -avz -e ssh ./scriptReboot1 root@${ip_linux_client}:~/;
        ssh root@${ip_linux_client} "~/scriptReboot1/reboot.sh $disk_path $ip_linux_host $ListenPort";
        echo r1r1r1!!!!!!!!!!!!!!!!
        ip_android=`nc -l $ListenPort`
        export ip_android
        echo $ip_android
        adb connect $ip_android
        sleep 2
        echo 'waiting for android boot !!!!!'
        adb -s $ip_android:5555 shell system/checkAndroidDesktop.sh

        ### get commit id
        tmp=`adb -s $ip_android:5555 shell cat data/commitId.txt | grep commitId`
        commitId=${tmp##*:}
        commitId=${commitId%?}

        echo 'testing'
        ### monitor script, if network is down, reboot to linux
        ./testAliveSend.sh &

        testType=$7
        export testType
        if [ "$testType" == "cts" ];then
            cts_cmd="$8"
            echo "exit" | $testcaseCTS/tools/cts-tradefed run cts -s $ip_android:$adbPort $cts_cmd &
            {
                tradefedMonitor $!
                if [ $? -eq 0 ];then
                    ###reboot to  linux
                    ./android_fastboot.sh  ${ip_android}  reboot_bootloader
                else
                    python sendEmail.py "something went wrong while run cts test in $host, ip is $ip_android" $ip_android
                fi
            }
        elif [ "$testType" == "gui" ];then
            #./runGuiTestInFold.sh $testcaseFold
            #ret=$?
            #ip_android=`cat "ip_android"$ListenPort`
            #assert $ret
            echo "gui test not available!"
        elif [ "$testType" == "lkp" ];then
            ./runLkpTestInFold.sh $testcaseLKP
            ret=$?
            ip_android=`cat "ip_android"$ListenPort`
            assert $ret
            echo "lkp test not available!"
        elif [ "$testType" == "all" ];then
            cts_cmd="$8"
            ./runLkpTestInFold.sh $testcaseLKP
            ret=$?
            ip_android=`cat "ip_android"$ListenPort`
            assert $ret
            ###################################################

            ###################################################
            ./reboot.sh
            ip_android=`cat "ip_android"$ListenPort`
            ./runGuiTestInFold.sh $testcaseFold
            ret=$?
            ip_android=`cat "ip_android"$ListenPort`
            assert $ret
            sleep 2
            ./testAliveSend.sh &
            echo "exit" | $testcaseCTS/tools/cts-tradefed run cts -s $ip_android:$adbPort $cts_cmd &
            {
                tradefedMonitor $!
                if [ $? -eq 0 ];then
                    ###reboot to  linux
                    ./android_fastboot.sh  ${ip_android}  reboot_bootloader
                else
                    python sendEmail.py "something went wrong while run cts test in $host, ip is $ip_android" $ip_android
                fi
            }
       fi

    elif [ "$run_install" == "installTest" ];then
        ## install android-x86 and then test
        iso_loc=$7
        export iso_loc
        getCommitId
        ./auto2.sh $ip_linux_client $iso_loc $disk_path $ListenPort $ip_linux_host
        ip_android=`timeout 600 nc -l $ListenPort`
        ping $ip_android -c 1 || { echo "cannot ping ip $ip_android" ; exit 1 ; }
        for i in {1..5}
        do
            adb connect ${ip_android} && break
            sleep 2
        done
        [ $i -eq 5 ] && { echo "adb connect failed" ; exit 1 ; }

        echo "android boot success!"
        export ip_android
        ##keep screen active
        adb -s $ip_android:$adbPort shell svc power stayon true || { echo "set svc power stayon failed" ; exit 1 ; }
	echo 'svc power finish'
        adb -s $ip_android:$adbPort shell system/checkAndroidDesktop.sh || { echo "check desktop boot failed" ;  exit 1 ; }
	echo 'check android desktop finish'
        sleep 30
        adb -s $ip_android:$adbPort push bin/firstlogin.jar /data/local/tmp || { echo "push firstlogin.jar failed" ; exit 1 ; }
	echo 'push firstlogin.jar finish'
        adb -s $ip_android:$adbPort shell uiautomator runtest firstlogin.jar -c com.firstlogin.firstlogin
	echo 'run firstlogin.jar finish'

	##startmenuTest
        echo 'startmenutest'
        adb -s $ip_android:$adbPort push bin/StartMenuTest.apk /data/local/tmp/com.example.qin.startmenutest || { echo "push StartMenuTest.apk failed" ; exit 1 ; }
        adb -s $ip_android:$adbPort shell pm install -r "/data/local/tmp/com.example.qin.startmenutest"
        adb -s $ip_android:$adbPort push bin/StartMenuTest-androidTest.apk /data/local/tmp/com.example.qin.startmenutest.test || { echo "push StartMenuTest-androidTest.apk failed" ; exit 1 ; }
        adb -s $ip_android:$adbPort shell pm install -r "/data/local/tmp/com.example.qin.startmenutest.test"
        adb -s $ip_android:$adbPort shell am instrument -w -r   -e debug false -e class com.example.qin.startmenutest.StartMenuTest1 com.example.qin.startmenutest.test/android.support.test.runner.AndroidJUnitRunner
        adb -s $ip_android:$adbPort shell pm uninstall com.example.qin.startmenutest

        echo 'install CtsDeviceAdmin.apk!!!!!'
        adb -s $ip_android:$adbPort install $testcaseCTS/repository/testcases/CtsDeviceAdmin.apk
        adb -s $ip_android:$adbPort push device_policies.xml data/system/device_policies.xml
        adb -s $ip_android:$adbPort push commitId.txt data/

        ./reboot.sh || { echo "reboot failed " ; exit 1 ; }
        cts_cmd="$8"
        ./runLkpTestInFold.sh $testcaseLKP
        ret=$?
        assert $ret
        ###################################################

        ./runGuiTestInFold.sh $testcaseFold
        ret=$?
        assert $ret

        sleep 2
        ### monitor script, if network is down, reboot to linux
        ./testAliveSend.sh &
        echo "exit" | timeout 46000 $testcaseCTS/tools/cts-tradefed run cts -s $ip_android:$adbPort $cts_cmd
        if [ $? -eq 0 ];then
            ./android_fastboot.sh  ${ip_android}  reboot_bootloader
        else
            python sendEmail.py "something went wrong while run cts test in $host, ip is $ip_android" $ip_android
        fi

    elif [ "$run_install" == "install" ];then
        ## install android-x86 and then test
        iso_loc=$7
        export iso_loc
        ./auto2.sh $ip_linux_client $iso_loc $disk_path $ListenPort $ip_linux_host;
        echo r5r5r5!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        ip_android=`nc -l $ListenPort`
        export ip_android
        echo "android boot success!"
        #sleep 30
        echo ${ip_android}
        adb connect ${ip_android}
        sleep 2
        ##keep screen active
        adb -s $ip_android:5555 shell svc power stayon true
        adb -s $ip_android:5555 shell system/checkAndroidDesktop.sh
        sleep 30
        adb -s $ip_android:5555 push bin/firstlogin.jar /data/local/tmp
        adb -s $ip_android:5555 shell uiautomator runtest firstlogin.jar -c com.firstlogin.firstlogin

        echo 'install CtsDeviceAdmin.apk!!!!!'
        adb -s $ip_android:5555 install $testcaseCTS/repository/testcases/CtsDeviceAdmin.apk
        adb -s $ip_android:5555 push device_policies.xml data/system/device_policies.xml
    	adb -s $ip_android:5555 push commitId.txt data/
        sleep 1
        echo "install finished!"
        ###reboot to  linux
        ./android_fastboot.sh  ${ip_android}  reboot_bootloader
    fi
fi

if [ "$run_install" == "install" ];then
   exit 0
fi

## copy result
./cpResult.sh

wait
exit 0
