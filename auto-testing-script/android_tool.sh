#!/bin/bash -x
# 先把ssh client端的东西rsync到ssh server端。
# 然后执行
# x86tool ip  fastboot
# 其实就是
# ssh  {ip} exec  argv[1-end]


# 利用ssh rsync同步本地的东西到远端。
# 或者sftp也行，然后实现命令的转发
rip=$1
echo ${rip}
arg_s=${@:2} 
echo ${@:2}
#rsync  --delete  -avz -e ssh ~/android_auto/   root@${rip}:~/android_auto/
rsync   -avz -e ssh ./* root@${rip}:~/android_auto/
sleep 8
ssh root@${rip} "~/android_auto/${arg_s}"
sleep 2
