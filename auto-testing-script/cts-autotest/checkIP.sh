#!/bin/bash -exu
#check whether the ip address
count=0
ip=$1 
echo $ip
while [[ $count -ge 0 ]]
do 
    ping -c 1 $ip &>/dev/null 
    result=$?
    echo $result
    if [ $result -eq 0 ];then
        echo -e "\033[32mip is up!\033[0m"
        if [ $count -gt 0 ];then
            sleep 10  
        fi
        break
    else
        sleep 5
        echo -e "\033[31mip is down!\033[0m"
    fi
    count=$(($count+1))
    if [ $count -eq 120 ];then
        ## ip cannot access
        echo ip cannot access
        exit 1
    fi
done
exit 0
