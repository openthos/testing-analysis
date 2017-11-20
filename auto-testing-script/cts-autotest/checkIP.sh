#!/bin/bash -xu
#check whether the ip address
count=0
ip=$1 
echo $ip
[ $ip == "localhost" ] && exit 0

while true 
do 
    if [ $count -eq 20 ];then
        ## ip cannot access
        echo ip:$ip cannot access
        exit 1
    fi
    count=$(($count+1))
    ping -c 1 $ip &>/dev/null 
    result=$?
    echo $result
    if [ $result -eq 0 ];then
        echo -e "\033[32mip:$ip is up!\033[0m"
	ssh $ip "ls"
	if [ $? -ne 0 ];then
	    #To Ubuntu
	    ./android_fastboot.sh $ip reboot_bootloader
	    sleep 60
	    continue
	fi
        if [ $count -gt 1 ];then
            sleep 10  
        fi
        break
    else
        sleep 5
        echo -e "\033[31mip:$ip is down!\033[0m"
    fi
done
[ -f "mailSent"$ip ] && rm "mailSent"$ip
exit 0
