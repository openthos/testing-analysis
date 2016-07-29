#check android desktop
#ps_=$(adb shell ps)
flag=0
while [[ $flag == 0 ]]
do
	ps |grep -i zygote
	if [ $? -eq 0 ]; then
		zygote=1
	else
		zygote=0
	fi
	ps |grep -i system_server
	if [ $? -eq 0 ]; then
		system_server=1
	else
		system_server=0
	fi
	ps |grep -i launcher
	if [ $? -eq 0 ]; then
		launcher=1
	else
		launcher=0
	fi

	if [ $zygote -eq 1 -a $system_server -eq 1 -a $launcher -eq 1 ];then
		flag=1
		echo -e "\033[32mAndroid-x86 desktop OK!\033[0m"
	else
		sleep 2
		echo -e "\033[31mAndroid-x86 desktop booting!\033[0m"
	fi
done