
#CTS,LKP,GUI自动化测试
## 自动化安装原理
关于安装过程的原理, 以及如何配置ssh无密码登陆等，请参考[毛英明的帮助](https://github.com/xyongcn/openthos-testing/blob/master/bare_metal_autotest/android_auto/README.md)，此链接也有相关的代码，但最新的部分已经合并到本网页，所以请忽略链接网页内的相关代码和说明，而以本网页的为准。
## CTS测试
###搭建测试环境
1. 测试机需要将语言修改成英文
1. 测试机(linux ubuntu)需要安装jdk1.6或者1.7，不支持1.8，apt-get install openjdk-7-jdk，同时需要切换到全英文使用环境，否则脚本运行可能有问题。
1. 测试机（运行脚本的机器）安装adb，apt-get install android-tools-adb
1. 安装ntp，apt-get install ntp
1. 下载自动化测试的程序，[下载地址](https://github.com/openthos/testing-analysis)
1. 测试机下载cts测试包，[下载地址]( https://dl.google.com/dl/android/cts/android-cts-5.1_r4-linux_x86-x86.zip),并将解压后的文件夹放到auto-testing-script目录下,替代原先的android-cts目录
1. 进入到auto-testing-script/cts-autotest目录中，修改根据所需configs文件，其中configs文件是所需要执行的paraRun.sh的各个参数,指定iso时使用$iso即可（请参考paraRun.sh脚本代码）。其中paraRun.sh最终会调用autoTest.sh，用户也可以直接运行autoTest.sh，运行autoTesh.sh需要指定端口号。
1. 如果编译iso，可进入到auto-testing-script/kernelci-analysis,修改build.sh中的参数，[参考曹睿东编译帮助](kernelci-analysis/README.md)
1. 如果需要进行模拟器的测试,还需要准备一个android-x86.raw的镜像放在auto-testing-scrip文件夹下，该android-x86.raw建议16G，并且需要事先分好区，关于分区的方法可以[参考曹瑞东android-x86.raw模拟器安装](https://github.com/xyongcn/openthos-testing/blob/master/doc/Openthos4Qemu2016.md)，目前采用的方法是先按曹睿东方法手动安装一遍，将该raw拷贝到auto-testing-scrip/
1. 安装python pip，然后安装PyEmail模块
```
sudo apt-get install python-pip
pip install PyEmail
```

###测试机制和安装机制
* CTS包含三套机制：
 1. 可以只安装android-x86_64
 1. 如果已经安装好了android-x86_64，则可以选择只测试不安装，测试有4种类型——cts，lkp，gui或者all
     1. 测试全部，configs如下
     
         ```r 192.168.2.16 PC2 /dev/sda40 run all "-p android.acceleration --disable-reboot"```
     
     1. 只测cts，configs如下
     
     	   ```r 192.168.2.16 PC2 /dev/sda40 run cts "-p android.acceleration --disable-reboot" ```
     
     1. 只测gui，configs如下，包括apk测试以及帧率测试，目前状态下，将lkp的benchmark（ebizzy和nbench）也并到了gui下面
     
     	   ```r 192.168.2.16 PC2 /dev/sda40 run gui```
     
     1. 只测lkp，configs如下，不过目前lkp还未集成到平台，所以还无法测试
     
     	   ```r 192.168.2.16 PC2 /dev/sda40 run lkp```
 1. 如果没有安装android-x86_64，则可以先安装android-x86_64，然后进行测试（**建议使用**）；
* 只需要调整相应的输入参数即可以达到相应的效果;

###参数意义
1. $1: 监听端口号
1. $2: 虚拟机或者真机，v/r；
1. $3: 待安装android-x86的机器的IP地址，如果测试环境是本地虚拟机的话，请填写localhost或者127.0.0.1
1. $4: 主机名，根据需要指定，例如可以写成主板的型号；
1. $5: 磁盘的路径，真机的话，一般指定到/dev/sda40;虚拟环境的话，该参数填写虚拟磁盘的路径（raw格式）；
1. $6: 直接运行CTS测试或者先安装在测试，run/install；
1. $7: CTS测试命令，eg： "-p android.acceleration --disable-reboot",双引号不能省略；
1. $8: 该参数是在安装android-x86_64的时候提供，如果只测试部安装，则不需要填写该参数；

###在真机中测试
* 测试举例：
    * 安装并测试，其中iso的名字规则为 XXX-版本号-5.1.iso
        * 推荐用法
            * 修改configs,例如改成 一下（参考configs）：

            ```r 192.168.2.16 PC1 /dev/sda40 installTest $iso  "-p android.acceleration --disable-reboot" ```
            * 然后执行
            
            ```./paraRun.sh android_x86_64-a3fe26d154ef92a708b7faa488571899aa5bcab4-5.1.iso```
        * 如果只测一台机器，也可以直接运行autoTest.sh（不推荐）
        
            ```./autoTest.sh 52001 r 192.168.2.16 PC1 /dev/sda40 installTest android_x86_64-a3fe26d154ef92a708b7faa488571899aa5bcab4-5.1.iso "-p android.acceleration --disable-reboot" ```
    * 已经装好android-x86，不需要安装只测试
        * 推荐用法
            * 修改configs,例如改成 一下（参考configs）：
            
            ```r 192.168.2.16 PC1 /dev/sda40 run cts "-p android.acceleration --disable-reboot" ```
            * 然后执行
            
            ```./paraRun.sh ```
        * 当然也可以直接运行autoTest.sh，方法与以上类似（不推荐）

###在模拟器中测试
* 测试举例：
    * 修改configs文件
    
        ```v localhost QEMU1 android-x86_64.raw  installTest ../android_x86_64-a3fe26d154ef92a708b7faa488571899aa5bcab4-5.1.iso "-p android.acceleration --disable-reboot" ```
    * 执行paraRun.sh
    
        ``` ./paraRun.sh ```

###多台机器安装测试
* 在configs中填写多条测试配置文件，运行paraRun.sh即可(参考configs)

###动态添加测试用例
* 在auto-testing-script/kernelci-analysis/testcases/ 中加入测试用例即可

###测试结果
* CTS测试结果最先保存在/android-cts/repository/results/中，然后会拷贝到result目录
* 测试结果保存在/mnt/freenas/result中（需预先创建好此目录）
* 编译结果放在/mnt/freenas/compile中（需预先创建好此目录）
* 概要信息放在/mnt/freenas/summary中的参数（需预先创建好此目录）

###关于编译
* 目前我们在docker中编译，替换了原先曹睿东的编译方法，docker环境是王建兴所部署，修改了docker的[.bashrc](kernelci-analysis/bashrcDocker)，让docker启动后便开始进行编译，注：替换时需要改名

###注意
* 初次使用可能会碰到各种各样的问题，在此列出一些，以后继续完善
    1. 安装完毕之后，机器黑屏，无法进入到android-x86，也无法进入ubuntu，此时需要在开机后按一次esc，调出ubuntu的grub，之后选择ubuntu进入系统
    1. 测试过程中，尽量不要操作android-x86
    1. android-x86的向导页面无法自动化操作，默认不处理
    1. qemu目前不支持gpu，所以测试graphics失败属于正常
    1. 特别注意android-x86的命名规则
    1. 被测试机的IP地址最好跟mac绑定，不然可能出现重启后程序无法继续往下运行的情况

---
在此对陈刚所提的修改意见表示感谢


