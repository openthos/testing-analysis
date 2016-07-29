#!/bin/bash
## $1: androidLocation,which sda? eg:/dev/sda4
## $2: ip_linux_host

## eg: ./reboot.sh /dev/sda5 192.168.2.36

androidLocation=$1
ip_linux_host=$2
ListenPort=$3

if [ ! -d "android_disk" ]; then
	mkdir  android_disk
fi
#mount /dev/sda4 android_disk;
mount $androidLocation android_disk;

#line2bottom=`tail android_disk/android*/system/etc/init.sh -n 2 |head -n 1`
line1bottom=`tail android_disk/android*/system/etc/init.sh -n 1`

sed '$d' -i ./android_disk/android*/system/etc/init.sh

#echo \$ip | nc -q 0 $ip_linux_host 5556
if [ "$line1bottom" == "return 0" ]; then
	echo "ip=\`getprop | grep ipaddress\`
	ip=\${ip##*\[}
	ip=\${ip%]*}
	nc -w 2 $ip_linux_host $ListenPort << EOF
    \$ip
EOF
	return 0" >> ./android_disk/android*/system/etc/init.sh
else
    sed '$d' -i ./android_disk/android*/system/etc/init.sh
    sed '$d' -i ./android_disk/android*/system/etc/init.sh
    echo "nc -w 2 $ip_linux_host $ListenPort << EOF
    \$ip
EOF
    return 0" >> ./android_disk/android*/system/etc/init.sh
fi
umount android_disk;
