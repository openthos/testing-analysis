#CTS自动化测试
## 自动化安装原理
关于如何配置ssh无密码登陆以及安装过程的原理，参考[毛英明的帮助](https://github.com/xyongcn/openthos-testing/blob/master/bare_metal_autotest/android_auto/README.md)
## CTS测试
###搭建测试环境
1. 测试机(linux)需要安装jdk1.6或者1.7，不支持1.8
1. 测试机安装adb，apt-get install android-tools-adb
1. 测试机下载安装android-sdk,[下载地址](http://developer.android.com/sdk/index.html)
1. 测试机下载cts测试包，[下载地址]( https://dl.google.com/dl/android/cts/android-cts-5.1_r4-linux_x86-x86.zip)
1. 下载自动化测试的程序，[下载地址](https://github.com/aoquan/cts-autotest.git)
1. 将以上3个包解压后，放在同同一级目录下面

###测试机制和安装机制
* CTS包含两套机制：
 1. 如果以及安装好了android-x86，则可以选择只测试不安装；
 1. 如果没有安装android-x86，则可以先安装android-x86，然后进行CTS测试；
* 只需要调整相应的输入参数即可以达到相应的效果;

###参数意义
1. $1: 虚拟机或者真机，v/r；
1. $2: 直接运行CTS测试或者先安装在测试，run/install；
1. $3: 待安装android-x86的机器的IP地址，如果测试环境是本地虚拟机的话，请填写localhost或者127.0.0.1；
1. $4: 磁盘的路径，真机的话，一般指定到/dev/sda40;虚拟环境的话，该参数填写虚拟磁盘的路径（raw格式）；
1. $5: CTS测试命令，eg： "-p android.acceleration --disable-reboot",双引号不能省略；
1. $6: 该参数是在安装android-x86的时候提供，如果只测试部安装，则不需要填写该参数；

###在真机中测试
* 测试举例：
 1. ./autoTest.sh r install 192.168.2.16 /dev/sda40 "-p android.acceleration --disable--reboot" android_x86.iso
 1. ./autoTest.sh r run 192.168.2.16 /dev/sda40 "-p android.acceleration --disable--reboot"

###在模拟器中测试
* 测试举例：
 1. ./autoTest.sh v install localhost android-x86-6.0.raw "-p android.acceleration --disable-reboot" android_x86.iso
 1. ./autoTest.sh v run localhost android-x86-6.0.raw "-p android.acceleration --disable-reboot"

###多台机器安装测试
* 目前多台机器测试还停留在串行测试阶段
* 用户需要根据自己的需要编写shell，可以参考run.sh脚本

###并行安装测试
安装android-x86较为耗时，为了提高程序性能，采用并行安装与测试

####并行安装
毛英明已经测试通过，还没有集成在本程序中来...

####并行测试
1. 使用adb链接上多台设备；
1. 启动测试程序，即运行cts-tradefed
1. 输入测试命令，加上参数 --all-devices
* 注意事项：/tmp和工作目录必须在同一个分区上面；由于CTS需要在/tmp下创建硬链接，如果不在同一个分区上，则使硬链接创建不上，程序出错；

###一些技术细节
* 对android-x86初始环境进行设置(技术的一些细节，程序已经默认帮我们完成了设置，使用者可以不用理会)
* 参考[刘明明帮助](https://github.com/openthos/openthos/wiki/cts%E6%B5%8B%E8%AF%95)
 1. 如果需要执行设备管理方面的兼容性测试，则在测试机上安装"CtsDeviceAdmin.apk",但是需要人工操作授权，程序采用修改配置文件(/data/system/device_policies.xml)的方法来达到授权的效果，不过需要重启才能生效，这就导致了我们看到的测试过程中重启了两次;
 1. 初次安装，就是出厂设置
 1. 手机语言默认也是英语
 1. 默认没有锁屏
 1. 由于采用wifi链接，一次不需要启动USB调试
 1. 保持唤醒：adb shell svc power stayon true
 1. android-x86的adb服务默认是开启的，因此不需要配置

###测试结果
测试结果保存在/android-cts/repository/results/中
