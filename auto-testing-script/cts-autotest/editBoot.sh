#!/bin/bash -exu
disk_path=$1
ip_linux_host=$2
ListenPort=$3 


if [ ! -d "android_disk" ]; then
    mkdir  android_disk
fi
mount -o loop,offset=32256 $disk_path android_disk;
########################################
## modify init.sh
line2bottom=`tail android_disk/android*/system/etc/init.sh -n 1`
sed '$d' -i ./android_disk/android*/system/etc/init.sh

#echo \$ip | nc -q 0 $ip_linux_host $ListenPort
if [ "$line2bottom" == "return 0" ]; then
    echo "ip=\`getprop | grep ipaddress\`
    ip=\${ip##*\[}
    ip=\${ip%]*}
    if [ \$ip ];then
    nc -w 2 $ip_linux_host $ListenPort << EOF
\$ip
EOF
    fi
        return 0" >> ./android_disk/android*/system/etc/init.sh
else
    sed '$d' -i ./android_disk/android*/system/etc/init.sh
    sed '$d' -i ./android_disk/android*/system/etc/init.sh
    sed '$d' -i ./android_disk/android*/system/etc/init.sh
    sed '$d' -i ./android_disk/android*/system/etc/init.sh
    echo "nc -w 2 $ip_linux_host $ListenPort << EOF
\$ip
EOF
    fi
        return 0" >> ./android_disk/android*/system/etc/init.sh
fi
sleep 2
umount android_disk;
exit 0
