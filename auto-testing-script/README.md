#CTS,LKP,GUI自动化测试
## 自动化安装原理
关于如何配置ssh无密码登陆以及安装过程的原理，参考[毛英明的帮助](https://github.com/xyongcn/openthos-testing/blob/master/bare_metal_autotest/android_auto/README.md)
## CTS测试
###搭建测试环境
1. 测试机(linux)需要安装jdk1.6或者1.7，不支持1.8，apt-get install openjdk-7-jdk
1. 测试机安装adb，apt-get install android-tools-adb
1. 下载自动化测试的程序，[下载地址](https://github.com/openthos/testing-analysis)
1. 测试机下载cts测试包，[下载地址]( https://dl.google.com/dl/android/cts/android-cts-5.1_r4-linux_x86-x86.zip),并将解压后的文件夹放到auto-testing-script目录下,替代原先的android-cts目录
1. 进入到auto-testing-script/cts-autotest目录中，修改根据所需configs文件，其中configs文件是所需要执行的autoTest.sh的各个参数,指定iso时使用$iso即可
1. 进入到auto-testing-script/kernelci-analysis,修改build.sh中的参数，[参考曹睿东编译帮助](kernelci-analysis/README.md)
1. 如果需要进行模拟器的测试,还需要准备一个android-x86.raw的镜像放在auto-testing-scrip文件夹下

###测试机制和安装机制
* CTS包含两套机制：
 1. 如果以及安装好了android-x86，则可以选择只测试不安装；
 1. 如果没有安装android-x86，则可以先安装android-x86，然后进行CTS测试；
* 只需要调整相应的输入参数即可以达到相应的效果;

###参数意义
1. $1: 监听端口号
1. $2: 虚拟机或者真机，v/r；
1. $3: 直接运行CTS测试或者先安装在测试，run/install；
1. $4: 待安装android-x86的机器的IP地址，如果测试环境是本地虚拟机的话，请填写localhost或者127.0.0.1；
1. $5: 磁盘的路径，真机的话，一般指定到/dev/sda40;虚拟环境的话，该参数填写虚拟磁盘的路径（raw格式）；
1. $6: CTS测试命令，eg： "-p android.acceleration --disable-reboot",双引号不能省略；
1. $7: 该参数是在安装android-x86的时候提供，如果只测试部安装，则不需要填写该参数；

###在真机中测试
* 测试举例：
 1. ./autoTest.sh 52001 r 192.168.2.16 /dev/sda40 installTest android_x86.iso "-p android.acceleration --disable-reboot"
 1. ./autoTest.sh 52001 r 192.168.2.16 /dev/sda40 run cts "-p android.acceleration --disable-reboot"

###在模拟器中测试
* 测试举例：
 1. ./autoTest.sh 52001 v localhost android-x86.raw  installTest ../xyl_android_x86_64_5.1.iso "-p android.acceleration --disable-reboot"

###多台机器安装测试
* 在configs中填写多条测试配置文件即可(参考configs)

###动态添加测试用例
* 目前测试还没有形成框架,lkp和gui测试都是写死在程序代码中了,还不支持动态添加测试用例
* 测试可以根据需要动态修改测试用例(修改configs即可)

###测试结果
* 测试结果保存在/android-cts/repository/results/中 
* 测试结果保存在/mnt/freenas/result中
* 编译结果放在/mnt/freenas/compile中
* 概要信息放在/mnt/freenas/summary中的参数

