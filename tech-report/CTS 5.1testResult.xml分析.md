##测试版本：CTS 5.1
 
1. 最新的CTS有100090个测试用例
2. Testresult.xml有211个package,其中105个重复出现两次，去掉重复为106个包。
3. 测试类型分类 
 - 1) Instrumentation test: 使用APK和Instrumentation进行测试，大部分都是
 - 2) Host Test:没有APK，主要使用adb以及一些jar包进行测试，比如hosttestlib.jar等等，共19个，如 ：CtsAdbTests,CtsDevicePolicyManagerTestCases,android.core.vm-tests- tf,CtsJdwp, CtsHostsideNetworkTests,CtsAppSecurityTests,CtsUsbTests,CtsHostJank,CtsMonkeyTestCases
 - 3) Junit device test: 使用Junit的方法进行测试，共1个，CtsJdwp
 - 4) New wrapped native test: CtsNativeOpenGLTestCases



| 包名    |   用例数 | 包简介 |
----- | ---- | ----
|android.JobScheduler	|4	| 5.0开始提供的API,可以设定程序在特殊的条件下运行|
|android.aadb	|11	|  ADB是Android系统提供的调试工具|
|android.acceleration	|6	|硬件加速|
|android.accessibility	|30	|辅助功能|
|android.accessibilityservice	|59	|Accessibility服务|
|android.accounts	|31	|Android账户管理|
|android.admin	|55	|Android 设备管理员|
|android.adminhostside	|30	|Android提供了设备管理API，我们可以通过这个API实现远程删除数据，设置锁屏密码等系统级操作|
|android.animation	|82	|动画|
|android.app	|260	|应用|
|android.appwidget	|18	|AppWidget就是我们平常在桌面上见到的那种一个个的小窗口，利用这个小窗口可以给用户提供一些方便快捷的操作。|
|android.bionic	|943	|Bionic库|
|android.bluetooth	|9	|蓝牙
|android.calendarcommon	|1	|日历
|android.content	|610	|应用程序组件
|android.core.tests.libcore.package.com	|26	|libcore是java核心库相关的内容|
|android.core.tests.libcore.package.conscrypt	|140	|libcore是java核心库相关的内容|
|android.core.tests.libcore.package.dalvik	|65	|libcore是java核心库相关的内容|
|android.core.tests.libcore.package.harmony_annotation	|14	|libcore是java核心库相关的内容|
|android.core.tests.libcore.package.harmony_beans	|65	|libcore是java核心库相关的内容|
|android.core.tests.libcore.package.harmony_java_io	|1047	|libcore是java核心库相关的内容|
|android.core.tests.libcore.package.harmony_java_lang	|1145	|libcore是java核心库相关的内容|
|android.core.tests.libcore.package.harmony_java_math	|745	|libcore是java核心库相关的内容|
|android.core.tests.libcore.package.harmony_java_net	|457	|libcore是java核心库相关的内容|
|android.core.tests.libcore.package.harmony_java_nio	|3770	|libcore是java核心库相关的内容|
|android.core.tests.libcore.package.harmony_java_text	|404	|libcore是java核心库相关的内容|
|android.core.tests.libcore.package.harmony_java_util	|1934	|libcore是java核心库相关的内容|
|android.core.tests.libcore.package.harmony_javax_security	|182	|libcore是java核心库相关的内容|
|android.core.tests.libcore.package.harmony_logging	|319	|libcore是java核心库相关的内容|
|android.core.tests.libcore.package.harmony_sql	|832	|libcore是java核心库相关的内容|
|android.core.tests.libcore.package.jsr166	|2881	|libcore是java核心库相关的内容|
|android.core.tests.libcore.package.libcore	|4068	|libcore是java核心库相关的内容|
|android.core.tests.libcore.package.okhttp	|599	|libcore是java核心库相关的内容|
|android.core.tests.libcore.package.org	|11531	|libcore是java核心库相关的内容|
|android.core.tests.libcore.package.sun	|3	|libcore是java核心库相关的内容|
|android.core.tests.libcore.package.tests	|1703	|libcore是java核心库相关的内容|
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
|android.nativeopengl	|3	|原生opengl  OpenGL|
|android.ndef	|11	|"NFC Data Exchange Format : NFC数据交换格式，NFC组织约定的NFC tag中的数据格式。|
|android.net	|136	|android网络
|android.net.hostsidenetwork	|1	|测试VPN的功能|
|android.opengl	|37	|OpenGL 是个专业的3D程序接口，是一个功能强大，调用方便的底层3D图形库。|
|android.openglperf	|3	|
|android.os	|373	|android.os的相关的API的测试，太多了，比如AsyncTask测试，比如Binder相关测试|
|android.permission	|181	| 测试android相关的权限相关测试|
|android.permission2	|21	|两类权限测试，一类是验证andrioid:maxSdkVersion相关的权限行为，主要包括android.permission.VIBRATE和android.permission.FLASHLIGHT，另一类是验证录制系统音频输出的权限测试|
|android.preference	|4	|设置程序里面的一些属性
|android.preference2	|69	|设置程序里面的一些属性
|android.print	|21	| 测试android的打印相关功能|
|android.provider	|291	|测试各种content provider的功能，比如测试browser中各种书签，历史访问记录|
|android.renderscript	|659	|google release的图形渲染得编程语言|
|android.renderscriptlegacy	|15	|google release的图形渲染得编程语言|
|android.rscpp	|25	|Rederscript的相关API测试，这是一种google release的图形渲染得编程语言|
|android.sax	|4	| 测试android.saxAPI的相关功能，主要是xml解析相关的|
|android.security	|106	|ndroid的各种关于安全的功能的测试|
|android.signature	|2	|
|android.speech	|9	|主要测试speech中TextToSpeech这个类的各种API，主要是android的将文本转换为语言的功能|
|android.telephony	|74	|对telephony（通信）的一些功能的测试|
|android.tests.appsecurity	|54	|android的key的一些测试，比如keyset的一些基本feature|
|android.text	|696	|测试android.text里面的一些API，比如AlteredCharSequence，Spanned等等|
|android.textureview	|5	|TextureViewTestActivity的启动的一些测试|
|android.theme	|21	| 针对各种各样的theme进行测试，主要测试有没有action bar
|android.tv	|49	| tv的相关测试
|android.uiautomation	|3	| 测试Uiautomation的API，比如uiAutomation.getWindowContentFrameStats
|android.uirendering	|29	| ui渲染得一些测试，比如BitmapFilterTests，ExactCanvasTests，FontRenderingTests等等
|android.usb	|1	|测试USB串口的号码与ro.serialno是否一致
|android.util	|195	|关于android.util功能的一些测试，比如AndroidExceptionTest，AndroidRuntimeExceptionTest，ArrayMapTest，DebugUtilsTest，DisplayMetricsTest等等|
|android.view	|642	|各种view的组件以及组件对外部事件的相应的一些测试
|android.webkit	|196	|webkit的一些主要功能的测试，比如CookieManagerTest，DateSorterTest，HttpAuthHandlerTest等等
|android.widget	|1015	|集中主要的widget的测试,比如AbsListViewTest,AbsSeekBarTest,AbsSpinnerTest,AbsoluteLayoutTest等等
|com.android.cts.browserbench	|1	| 打开一个带网址的activiy然后等待反应时间
|com.android.cts.dram	|26	|测试1s之内memcpy功能耗费多少屏
|com.android.cts.filesystemperf	|8	|文件系统的随机读写测试
|com.android.cts.jank	|5	|
|com.android.cts.opengl	|9	|一些OpenGL的benchmark测试
|com.android.cts.simplecpu	|8	| 让CPU做一些排序啊乘法之类的运算
|com.android.cts.tvproviderperf	|2	| TV方面的feature的performance测试
|com.android.cts.ui	|1	|测试一个ScrollingActivity是否能划到顶端和底端
|com.android.cts.uihost	|2	| 测试APK的安装时间
|com.android.cts.videoperf	|5	| 测试video编解码的速度
|com.drawelements.deqp.gles3	|37360	| 测试OpenGL ES的相关功能
|com.drawelements.deqp.gles31	|17212	| 测试OpenGL ES的相关功能
|zzz.android.monkey	|9	|monkey|在com.android.cts.monkey上运行monkey
