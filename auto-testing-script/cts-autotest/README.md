#有关一些CTS细节
## CTS测试
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
