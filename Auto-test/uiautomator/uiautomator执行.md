#uiautomator执行步骤如下：
##1.安装系统及第三方APK
成功安装启动openthos；安装所需测试的四十多个APP

##2.提供两个jar文件
lmmuiauto.jar

otoAutoTest.jar
##3.执行
usage: java -jar *.jar targetIp targetPort otoAutoTest.jar logFile.log

eg:java -jar lmmuiauto.jar 192.168.0.105 5555 Auto-test/uiautomator/bin/otoAutoTest.jar ./a.log

可运行四十多个APP的自动测试。包括：打开，窗口滑动，窗口缩放，窗口拖动，账户登录、关闭等功能 。

所测应用列表如下：包名+用例名

		{"com.autoTestUI.calc", "testcalc"},
		{ "com.autoTestUI.clock", "testclock" },
		{ "com.autoTestUI.date", "testdate" },
		{ "com.autoTestUI.email", "testemail" },
		{ "com.autoTestUI.adobe_acrobat_dc", "testadobe_acrobat_dc" },
		{ "com.autoTestUI.gdmap", "testgdmap" },
		{ "com.autoTestUI.microsoft_execl", "testmicrosoft_execl" },
		{ "com.autoTestUI.microsoft_onenote", "testmicrosoft_onenote" },
		{ "com.autoTestUI.microsoft_outlook", "testmicrosoft_outlook" },
		{ "com.autoTestUI.microsoft_powerpoint", "testmicrosoft_powerpoint" },
		{ "com.autoTestUI.microsoft_word", "testmicrosoft_word" },
		{ "com.autoTestUI.note", "testnote" },
		{ "com.autoTestUI.tencent_video", "testtencent_video" },
		{ "com.autoTestUI.VLC", "testVLC" },
		{ "com.autoTestUI.wps", "testwps" },
		{"com.autoTestUI.music", "testmusic"},
		{ "com.autoTestUI.terminal", "testterminal" },
		{ "com.autoTestUI.termux", "testtermux" },		
		{ "com.autoTestUI.wpspro", "testwpspro" },
		{ "com.autoTestUI.wpsemail", "testwpsemail" },
		{ "com.autoTestUI.firefox", "testfirefox" },
		{ "com.autoTestUI.qq", "testqq" },
		{ "com.autoTestUI.wechat", "testwechat" },
		{ "com.autoTestUI.wyiyunmusic", "testwyiyunmusic" },
		{"com.autoTestUI.fm", "testfm"},
		{"com.autoTestUI.toutiao", "testtoutiao"},
		{"com.autoTestUI.baiduy", "testbaiduy"},
		{"com.autoTestUI.seafile", "testseafile"},
		{"com.autoTestUI.taobao", "testtaobao"},
		{"com.autoTestUI.jd", "testjd"},
		{"com.autoTestUI.angrybird", "testangrybird"},
		{"com.autoTestUI.appstore", "testappstore"},
		{"com.autoTestUI.czfilemanager", "testczfilemanager"},
		{"com.autoTestUI.esfilemanager", "testesfilemanager"},
		{"com.autoTestUI.gugepinyin", "testgugepinyin"},
		{"com.autoTestUI.meitu", "testmeitu"},
		{"com.autoTestUI.tuniu", "testtuniu"},
		{"com.autoTestUI.wandoujia", "testwandoujia"},
		{"com.autoTestUI.xiaoying", "testxiaoying"},
		{"com.autoTestUI.xiecheng", "xiecheng"},
		{"com.autoTestUI.xuetang", "testxuetang"},
		{"com.autoTestUI.yx_2048", "testyx"}




