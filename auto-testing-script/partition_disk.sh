#!/bin/bash -x

echo $1
tmp=$1

disk=${tmp:0:8}
#disk='/dev/sda'

part_number=${tmp:8}


fdisk $disk  << EOF
n
$part_number

+10G
p
w
EOF

sleep 3


###linux分区以后格式化命令不能够马上执行，必须重启一下系统才行。。。。
##ls -lh /dev/sda* 看不见刚建立的分区。通过partprobe 解决了问题

partprobe

sleep 2

#mkfs.ext4 {/dev/sda4}
mkfs.ext4  $tmp <<EOF
y
EOF
