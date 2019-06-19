## 自动化测试工具位置
   - 192.168.0.180:/home/lh/jiangqin/autotest/project
   - 可以直接运行测试程序，目录是192.168.0.180:/home/lh/jiangqin/autotest/直接运行，运行的格式是sh ./runTest.sh 192.168.0.xx
## 注意事项
   - 测试文件在src/androidTest/java/com.example...目录下。运行时先在下方的terminal中adb connect测试设备，之后右键public class...,选择run...开始测试
#### 文件管理器
   - 执行文件管理器测试前，请先将autotest目录下的U盘测试.zip拷贝到测试机的Download目录下（直接运行的不需要拷贝）
#### 设置
   - 执行设置测试前，请先将autotest目录下的NotificationTest.apk拷贝到测试机并安装（直接运行的不需要安装）
   - 执行设置测试前，请先将开机密码设置为无
   - 执行设置测试前，请先打开一下通知中心和应用商店，再关闭，否则测试时可能会出问题

## 报错
### java.lang.SecurityException
问题：   
运行自动化测试时报错longMsg=java.lang.SecurityException: Permission Denial: getIntentSender() from pid=8427, uid=2000, (need uid=1000) is not allowed to send as package android    
解决：   
有些无障碍服务打开时运行uiautomator会出现这个错误，比如TalkBack、Tincore KeyMapper等，可以在设置 ->无障碍中关闭这些服务
