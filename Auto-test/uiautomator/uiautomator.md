#uiautomator编译及执行
##eclipse编写JAVA


##1.创建编译文件
$android create uitest-project -n demo1 -t 4 -p /home/emindsoft/workspace/demo1/

##2.修改build.xml

##3.编译
$ant build.xml=====ant -buildfile build.xml
##4.push 到测试机
$adb push demo1.jar /data/local/tmp/

##5.执行测试
$adb shell uiautomator runtest demo1.jar -c com.browser.demobrowser


