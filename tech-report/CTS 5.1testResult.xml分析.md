测试版本：CTS 5.1
 
1. 最新的CTS有100090个测试用例
2. 最新的CTS有211个package,其中105个重复出现两次，去掉重复为106个包。
3. 测试类型分类 
 - 1) Instrumentation test: 使用APK和Instrumentation进行测试，大部分都是
 - 2) Host Test:没有APK，主要使用adb以及一些jar包进行测试，比如hosttestlib.jar等等，共19个，列表如 下：CtsAdbTests,CtsDevicePolicyManagerTestCases,android.core.vm-tests- tf,CtsJdwp, CtsHostsideNetworkTests,CtsAppSecurityTests,CtsUsbTests,CtsHostJank,CtsMonkeyTestCases
 - 3) Junit device test: 使用Junit的方法进行测试，共1个，CtsJdwp
 - 4) New wrapped native test: CtsNativeOpenGLTestCases

| 包名    |   用例数 | 包简介 |
| :-------- | --------:| :--: |
|android.JobScheduler	|4	| 5.0开始提供的API,可以设定程序在特殊的条件下运行|
|android.aadb	|11	|  ADB是Android系统提供的调试工具|
|android.acceleration	|6	|硬件加速|
|android.accessibility	|30	|辅助功能|
|android.accessibilityservice	|59	|Accessibility服务|
|android.accounts	|31	|Android账户管理|
|android.admin	|55	|Android 设备管理员|
|android.adminhostside	|30	|
|android.animation	|82	|动画|
|android.app	|260	|应用|
|android.appwidget	|18	|AppWidget就是我们平常在桌面上见到的那种一个个的小窗口，利用这个小窗口可以给用户提供一些方便快捷的操作。|
|android.bionic	|943	|Bionic库|
|android.bluetooth	|9	|蓝牙
|android.calendarcommon	|1	|日历
|android.content	|610	|应用程序组件
|android.core.tests.libcore.package.com	|26	|
|android.core.tests.libcore.package.conscrypt	|140	|
|android.core.tests.libcore.package.dalvik	|65	|
|android.core.tests.libcore.package.harmony_annotation	|14	|
|android.core.tests.libcore.package.harmony_beans	|65	|
|android.core.tests.libcore.package.harmony_java_io	|1047	|
|android.core.tests.libcore.package.harmony_java_lang	|1145	|
|android.core.tests.libcore.package.harmony_java_math	|745	|
|android.core.tests.libcore.package.harmony_java_net	|457	|
|android.core.tests.libcore.package.harmony_java_nio	|3770	|
|android.core.tests.libcore.package.harmony_java_text	|404	|
|android.core.tests.libcore.package.harmony_java_util	|1934	|
|android.core.tests.libcore.package.harmony_javax_security	|182	|
|android.core.tests.libcore.package.harmony_logging	|319	|
|android.core.tests.libcore.package.harmony_sql	|832	|
|android.core.tests.libcore.package.jsr166	|2881	|
|android.core.tests.libcore.package.libcore	|4068	|
|android.core.tests.libcore.package.okhttp	|599	|
|android.core.tests.libcore.package.org	|11531	|
|android.core.tests.libcore.package.sun	|3	|
|android.core.tests.libcore.package.tests	|1703	|
|android.core.vm-tests-tf	|3101	|
|android.database	|261	|Android 数据库
|android.display	|12	|display架构
|android.dpi	|11	|分辨率
|android.dreams	|1	|
|android.drm	|47	|drm框架，该框架能够把不同的实现统一起来，为上层提供相同的接口|
|android.effect	|6	|
|android.gesture	|29	|手势识别
|android.graphics	|946	|图形
|android.graphics2	|1	|图形
|android.hardware	|206	|硬件
|android.host.dumpsys	|2	|Android系统中dumpsys工具的应用
|android.host.jdwpsecurity	|1	|jdwp安全
|android.host.security	|116	|安全
|android.host.theme	|1	|主题
|android.jdwp	|257	|jdwp线程
|android.jni	|62	|上层Java要调用底层的C/C++函数库必须通过Java的JNI来实现|
|android.keystore	|84	|app软件签名
|android.location	|88	|位置
|android.location2	|12	|位置
|android.media	|1036	|媒体
|android.mediastress	|120	|媒体
|android.nativemedia.sl	|10	|原生media
|android.nativemedia.xa	|2	|原生media
|android.nativeopengl	|3	|原生opengl  OpenGL（全写Open Graphics Library）是个定义了一个跨编程语言、跨平台的编程接口规格的专业的图形程序接口。它用于三维图像（二维的亦可），是一个功能强大，调用方便的底层图形库。|
|android.ndef	|11	|"NFC Data Exchange Format : NFC数据交换格式，NFC组织约定的NFC tag中的数据格式。|
|android.net	|136	|android网络
|android.net.hostsidenetwork	|1	|测试VPN的功能|
|android.opengl	|37	|OpenGL 是个专业的3D程序接口，是一个功能强大，调用方便的底层3D图形库。|
|android.openglperf	|3	|
|android.os	|373	|
|android.permission	|181	|
|android.permission2	|21	|
|android.preference	|4	|
|android.preference2	|69	|
|android.print	|21	|
|android.provider	|291	|
|android.renderscript	|659	|
|android.renderscriptlegacy	|15	|
|android.rscpp	|25	|
|android.sax	|4	|
|android.security	|106	|
|android.signature	|2	|
|android.speech	|9	|
|android.telephony	|74	|
|android.tests.appsecurity	|54	|
|android.text	|696	|
|android.textureview	|5	|
|android.theme	|21	|
|android.tv	|49	|
|android.uiautomation	|3	|
|android.uirendering	|29	|
|android.usb	|1	|
|android.util	|195	|
|android.view	|642	|
|android.webkit	|196	|
|android.widget	|1015	|
|com.android.cts.browserbench	|1	|
|com.android.cts.dram	|26	|
|com.android.cts.filesystemperf	|8	|
|com.android.cts.jank	|5	|
|com.android.cts.opengl	|9	|
|com.android.cts.simplecpu	|8	|
|com.android.cts.tvproviderperf	|2	|
|com.android.cts.ui	|1	|
|com.android.cts.uihost	|2	|
|com.android.cts.videoperf	|5	|
|com.drawelements.deqp.gles3	|37360	|
|com.drawelements.deqp.gles31	|17212	|
|zzz.android.monkey	|9	|monkey|
