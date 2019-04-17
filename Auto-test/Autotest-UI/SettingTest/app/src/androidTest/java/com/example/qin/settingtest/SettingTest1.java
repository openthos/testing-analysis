package com.example.qin.settingtest;

import android.graphics.Point;
import android.graphics.Rect;
import android.support.test.espresso.core.deps.guava.base.Strings;
import android.support.test.uiautomator.Configurator;
import android.support.test.uiautomator.UiAutomatorTestCase;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.io.IOException;

/**
 * Created by qin on 18-4-26.
 */

public class SettingTest1 extends UiAutomatorTestCase {
    UiDevice uiDevice;
    public void test11_WLAN() throws UiObjectNotFoundException, IOException {
        uiDevice = getUiDevice();
        startSetting();
        UiScrollable setList = new UiScrollable(new UiSelector().resourceId("com.android.settings:id/dashboard"));
        UiObject securityItem = setList.getChildByText(new UiSelector().className(LinearLayout.class.getName()), "WLAN", true);
        securityItem.getChild(new UiSelector().text("WLAN")).click();


        //------------------------测试开关------------------------
        if(new UiObject(new UiSelector().text("已关闭")).exists()){
            new UiObject(new UiSelector().resourceId("com.android.settings:id/switch_widget")).click();
            sleep(2000);
        }
        new UiObject(new UiSelector().resourceId("com.android.settings:id/switch_widget")).click();
        assertTrue(new UiObject(new UiSelector().text("已关闭")).exists());
        sleep(2000);
        new UiObject(new UiSelector().resourceId("com.android.settings:id/switch_widget")).click();
        assertTrue(new UiObject(new UiSelector().text("已开启")).exists());
        sleep(5000);


        //------------------------设置wifi（DHCP+显示密码）------------------------
        //选择oslab
        UiScrollable  wlanList = new UiScrollable(new UiSelector().resourceId("android:id/list"));
        wlanList.getChildByText(new UiSelector().className(LinearLayout.class.getName()), "oslab", true).click();

        //输入密码
        new UiObject(new UiSelector().resourceId("com.android.settings:id/password")).setText("gcf10l-jjmqq");
        //显示密码
        new UiObject(new UiSelector().resourceId("com.android.settings:id/show_password")).click();
        assertTrue(new UiObject(new UiSelector().text("gcf10l-jjmqq")).exists());
        //高级选项
        new UiObject(new UiSelector().resourceId("com.android.settings:id/wifi_advanced_togglebox")).click();
        //ip设置为dhcp
        //穿透问题 [bug 1333]
        new UiObject(new UiSelector().resourceId("com.android.settings:id/ip_settings")).click();
        Rect rectDHCP = new UiObject(new UiSelector().text("DHCP")).getVisibleBounds();
        uiDevice.click(rectDHCP.right,rectDHCP.centerY());
        sleep(1000);
        assertTrue(new UiObject(new UiSelector().text("DHCP")).exists() && new UiObject(new UiSelector().text("显示密码")).exists());
        //连接wifi
        //bug2410
        Rect connect = new UiObject(new UiSelector().text("连接")).getVisibleBounds();
        uiDevice.click((connect.centerX()+connect.left)/2,connect.centerY());

        sleep(5000);
        String oslabStatus="";
        UiObject oslabitem = wlanList.getChildByText(new UiSelector().className(LinearLayout.class.getName()), "oslab", true);
        oslabStatus = oslabitem.getChild(new UiSelector().resourceId("android:id/summary")).getText().toString();
        while (oslabStatus.equals("正在获取IP地址...")|| oslabStatus.equals("正在连接...")) {
            Log.d("AAAAAA",oslabStatus);
            sleep(2000);
            oslabStatus = oslabitem.getChild(new UiSelector().resourceId("android:id/summary")).getText().toString();
        }
        assertEquals("已保存",oslabStatus);
        wlanList.getChildByText(new UiSelector().className(LinearLayout.class.getName()), "oslab", true).click();
        new UiObject(new UiSelector().text("取消保存")).click();
        sleep(20000);


        //------------------------静态ip+不显示密码------------------------
        //选择oslab
        wlanList.getChildByText(new UiSelector().className(LinearLayout.class.getName()), "oslab", true).click();
        //输入密码
        new UiObject(new UiSelector().resourceId("com.android.settings:id/password")).setText("gcf10l-jjmqq");
        //不显示密码
        assertFalse(new UiObject(new UiSelector().text("gcf10l-jjmqq")).exists());
        //高级选项
        new UiObject(new UiSelector().resourceId("com.android.settings:id/wifi_advanced_togglebox")).click();
        //ip设置为静态ip
        //穿透问题 [bug 1333]
        new UiObject(new UiSelector().resourceId("com.android.settings:id/ip_settings")).click();
        Rect rectManual = new UiObject(new UiSelector().text("静态")).getVisibleBounds();
        uiDevice.click(rectManual.right-1,rectManual.centerY());
        sleep(1000);

        //设置ip地址
        UiScrollable  staticIPList = new UiScrollable(new UiSelector().className("android.widget.ScrollView"));
        staticIPList.getChildByText(new UiSelector().className(LinearLayout.class.getName()), "IP地址", true).getChild(new UiSelector().resourceId("com.android.settings:id/ipaddress")).setText("192.168.0.12");
        //设置网关
        staticIPList.getChildByText(new UiSelector().className(LinearLayout.class.getName()), "网关", true).getChild(new UiSelector().resourceId("com.android.settings:id/gateway")).setText("192.168.0.1");
        //设置网络前缀长度
        staticIPList.getChildByText(new UiSelector().className(LinearLayout.class.getName()), "网络前缀长度", true).getChild(new UiSelector().resourceId("com.android.settings:id/network_prefix_length")).setText("24");
        //设置DNS1
        staticIPList.getChildByText(new UiSelector().className(LinearLayout.class.getName()), "DNS 1", true).getChild(new UiSelector().resourceId("com.android.settings:id/dns1")).setText("192.168.0.1");
        //设置DNS2
        //staticIPList.getChildByText(new UiSelector().className(LinearLayout.class.getName()), "DNS 2", true).getChild(new UiSelector().resourceId("com.android.settings:id/dns2")).setText("192.168.0.2");
        //连接wifi
        //new UiObject(new UiSelector().text("连接")).click();
        Rect connect1=new UiObject(new UiSelector().text("连接")).getVisibleBounds();
        uiDevice.click(connect1.centerX(),connect1.centerY());
        sleep(2000);
        if(new UiObject(new UiSelector().text("蓝牙")).exists()){
            new UiObject(new UiSelector().text("WLAN")).click();
        }

        oslabitem = wlanList.getChildByText(new UiSelector().className(LinearLayout.class.getName()), "oslab", true);
        oslabStatus = oslabitem.getChild(new UiSelector().resourceId("android:id/summary")).getText().toString();
        while (oslabStatus.equals("正在获取IP地址...")|| oslabStatus.equals("正在连接...")) {
            Log.d("AAAAAA",oslabStatus);
            sleep(2000);
            oslabStatus = oslabitem.getChild(new UiSelector().resourceId("android:id/summary")).getText().toString();
        }
        assertEquals("已保存",oslabStatus);
        new UiObject(new UiSelector().text("oslab")).click();
        String showIP = new UiObject(new UiSelector().resourceId("com.android.settings:id/value")).getText().toString();
        assertEquals(showIP,"192.168.0.12");
        new UiObject(new UiSelector().text("取消保存")).click();
        sleep(20000);

        //------------------------未保存网络其它设置------------------------
        //输入错误密码
        //wlanList.getChildByText(new UiSelector().className(LinearLayout.class.getName()), "oslab", true).click();
        //输入密码
        /*new UiObject(new UiSelector().resourceId("com.android.settings:id/show_password")).click();
        new UiObject(new UiSelector().resourceId("com.android.settings:id/password")).setText("gcf10l-12345");
        sleep(1000);
        Rect connect3=new UiObject(new UiSelector().text("连接")).getVisibleBounds();
        uiDevice.click(connect3.centerX(),connect3.bottom-2);
        sleep(60000);
        oslabitem = wlanList.getChildByText(new UiSelector().className(LinearLayout.class.getName()), "oslab", true);
        oslabStatus = oslabitem.getChild(new UiSelector().resourceId("android:id/summary")).getText().toString();
        assertEquals("身份验证出现问题",oslabStatus);
        new UiObject(new UiSelector().text("oslab")).click();
        new UiObject(new UiSelector().text("取消保存")).click();*/
        //点击取消
        wlanList.getChildByText(new UiSelector().className(LinearLayout.class.getName()), "oslab", true).click();
        //new UiObject(new UiSelector().text("取消")).click();
        Rect connect2=new UiObject(new UiSelector().text("取消")).getVisibleBounds();
        uiDevice.click((connect2.centerX()+connect2.left)/2,connect2.centerY());
        assertTrue(new UiObject(new UiSelector().text("已开启")).exists());
    }

    public void test12_Bluetooth() throws UiObjectNotFoundException, IOException {
        uiDevice = getUiDevice();
        if(!new UiObject(new UiSelector().text("WLAN")).exists()){
            uiDevice.pressKeyCode(111);
        }
        if(new UiObject(new UiSelector().description("向上导航")).exists()){
            new UiObject(new UiSelector().description("向上导航")).click();
        }
        UiScrollable setList = new UiScrollable(new UiSelector().resourceId("com.android.settings:id/dashboard"));
        //------------------------蓝牙测试------------------------
        UiObject securityItem = setList.getChildByText(new UiSelector().className(LinearLayout.class.getName()), "蓝牙", true);
        securityItem.getChild(new UiSelector().text("蓝牙")).click();
        if(new UiObject(new UiSelector().text("已开启")).exists()){
            new UiObject(new UiSelector().resourceId("com.android.settings:id/switch_widget")).click();
            sleep(2000);
        }
        String info = new UiObject(new UiSelector().resourceId("android:id/empty")).getText().toString();
        assertEquals("开启蓝牙后，您的设备可以与附近的其他蓝牙设备通信。",info);
        new UiObject(new UiSelector().resourceId("com.android.settings:id/switch_widget")).click();
        sleep(2000);
        if (!new UiObject(new UiSelector().resourceId("android:id/summary")).exists()){
            uiDevice.swipe(0,600,0,100,20);
        }
        info = new UiObject(new UiSelector().resourceId("android:id/summary")).getText().toString();
        assertEquals("在 THTF T Series 上开启蓝牙设置后，附近的设备将可以检测到该设备。",info);
    }

    public void test15_Display() throws UiObjectNotFoundException, IOException {
        uiDevice = getUiDevice();
        if(new UiObject(new UiSelector().description("向上导航")).exists()) {
            new UiObject(new UiSelector().description("向上导航")).click();
        }
        UiScrollable setList = new UiScrollable(new UiSelector().resourceId("com.android.settings:id/dashboard"));
        UiObject securityItem = setList.getChildByText(new UiSelector().className(LinearLayout.class.getName()), "显示", true);
        securityItem.getChild(new UiSelector().text("显示")).click();
        sleep(2000);

        //------------------------测试亮度------------------------
        new UiObject(new UiSelector().text("亮度")).click();
        sleep(1000);
        Rect rectBrightness = new UiObject(new UiSelector().resourceId("com.android.systemui:id/slider")).getVisibleBounds();
        uiDevice.swipe(rectBrightness.centerX(),rectBrightness.centerY(),rectBrightness.centerX()+300,rectBrightness.centerY(),10);
        sleep(1000);
        String brightnessInfo = uiDevice.executeShellCommand("settings get system screen_brightness");
        Log.d("AAAAA",brightnessInfo);
        assertEquals("255\n",brightnessInfo);
        uiDevice.swipe(rectBrightness.centerX(),rectBrightness.centerY(),1,rectBrightness.centerY(),10);
        sleep(1000);
        brightnessInfo = uiDevice.executeShellCommand("settings get system screen_brightness");
        Log.d("AAAAA",brightnessInfo);
        assertEquals("1\n",brightnessInfo);
        new UiObject(new UiSelector().resourceId("com.android.systemui:id/slider")).click();
        sleep(1000);
        brightnessInfo = uiDevice.executeShellCommand("settings get system screen_brightness");
        Log.d("AAAAA",brightnessInfo);
        assertEquals("128\n",brightnessInfo);
        uiDevice.pressKeyCode(111);
        sleep(2000);

        //------------------------测试壁纸------------------------
        //文件管理中选图
        new UiObject(new UiSelector().text("壁纸")).click();
        new UiObject(new UiSelector().text("设置壁纸")).click();
        new UiObject(new UiSelector().text("选择图片")).click();
        new UiObject(new UiSelector().text("文件管理")).click();
        sleep(2000);
        long actiontime = Configurator.getInstance().getActionAcknowledgmentTimeout();
        Configurator.getInstance().setActionAcknowledgmentTimeout(0);
        new UiObject(new UiSelector().text("用户数据")).click();
        new UiObject(new UiSelector().text("用户数据")).click();
        sleep(1000);
        new UiObject(new UiSelector().text("Pictures")).click();
        new UiObject(new UiSelector().text("Pictures")).click();
        sleep(1000);
        new UiObject(new UiSelector().text("wallpaper")).click();
        new UiObject(new UiSelector().text("wallpaper")).click();
        sleep(1000);
        new UiObject(new UiSelector().text("wallpaper (7).jpg")).click();
        new UiObject(new UiSelector().text("wallpaper (7).jpg")).click();
        sleep(2000);
        Configurator.getInstance().setActionAcknowledgmentTimeout(actiontime);
        new UiObject(new UiSelector().text("设置壁纸")).click();
        sleep(5000);
        //默认图片
        new UiObject(new UiSelector().text("设置壁纸")).click();
        new UiObject(new UiSelector().description("第1张壁纸，共1张")).click();
        new UiObject(new UiSelector().text("设置壁纸")).click();
        sleep(5000);
        new UiObject(new UiSelector().description("向上导航")).click();

        //------------------------字体大小------------------------
        new UiObject(new UiSelector().text("字体大小")).click();
        new UiObject(new UiSelector().text("小")).click();
        UiScrollable  displayList = new UiScrollable(new UiSelector().resourceId("android:id/list"));
        UiObject fontItem = displayList.getChildByText(new UiSelector().className(LinearLayout.class.getName()), "字体大小", true);
        String fontStatus = fontItem.getChild(new UiSelector().resourceId("android:id/summary")).getText().toString();
        assertEquals("小",fontStatus);
        String fontInfo = uiDevice.executeShellCommand("settings get system font_scale");
        Log.d("AAAAA",fontInfo);
        assertEquals("0.85\n",fontInfo);

        new UiObject(new UiSelector().text("字体大小")).click();
        new UiObject(new UiSelector().text("大")).click();
        fontStatus = fontItem.getChild(new UiSelector().resourceId("android:id/summary")).getText().toString();
        assertEquals("大",fontStatus);
        fontInfo = uiDevice.executeShellCommand("settings get system font_scale");
        Log.d("AAAAA",fontInfo);
        assertEquals("1.15\n",fontInfo);

        new UiObject(new UiSelector().text("字体大小")).click();
        new UiObject(new UiSelector().text("超大")).click();
        fontStatus = fontItem.getChild(new UiSelector().resourceId("android:id/summary")).getText().toString();
        assertEquals("超大",fontStatus);
        fontInfo = uiDevice.executeShellCommand("settings get system font_scale");
        Log.d("AAAAA",fontInfo);
        assertEquals("1.3\n",fontInfo);

        new UiObject(new UiSelector().text("字体大小")).click();
        new UiObject(new UiSelector().text("普通")).click();
        fontStatus = fontItem.getChild(new UiSelector().resourceId("android:id/summary")).getText().toString();
        assertEquals("普通",fontStatus);
        fontInfo = uiDevice.executeShellCommand("settings get system font_scale");
        Log.d("AAAAA",fontInfo);
        assertEquals("1.0\n",fontInfo);

        new UiObject(new UiSelector().text("字体大小")).click();
        new UiObject(new UiSelector().text("取消")).click();
        fontStatus = fontItem.getChild(new UiSelector().resourceId("android:id/summary")).getText().toString();
        assertEquals("普通",fontStatus);
        fontInfo = uiDevice.executeShellCommand("settings get system font_scale");
        Log.d("AAAAA",fontInfo);
        assertEquals("1.0\n",fontInfo);

        //------------------------显示缩放------------------------
        String dpiDefault = uiDevice.executeShellCommand("wm density");
        String expect120="";
        String expect160="";
        String expect240="";
        dpiDefault=dpiDefault.substring(18,21);
        if (dpiDefault.equals("120")){
            expect120="Physical density: 120\n";
            expect160="Physical density: 120\nOverride density: 160\n";
            expect240="Physical density: 120\nOverride density: 240\n";
        }else if (dpiDefault.equals("160")){
            expect120="Physical density: 160\nOverride density: 120\n";
            expect160="Physical density: 160\n";
            expect240="Physical density: 160\nOverride density: 240\n";
        }else{
            assertTrue(false);
        }

        new UiObject(new UiSelector().text("显示缩放")).click();
        if(! new UiObject(new UiSelector().text("笔记本 1366x768/ 1280x720 屏幕")).isSelected()){
            new UiObject(new UiSelector().text("笔记本 1920x1080 屏幕")).click();
            sleep(3000);
            new UiObject(new UiSelector().text("显示缩放")).click();
        }
        new UiObject(new UiSelector().text("笔记本 1366x768/ 1280x720 屏幕")).click();
        sleep(3000);
        UiObject dpiItem = displayList.getChildByText(new UiSelector().className(LinearLayout.class.getName()), "显示缩放", true);
        String dpiStatus = dpiItem.getChild(new UiSelector().resourceId("android:id/summary")).getText().toString();
        assertEquals("笔记本 1366x768/ 1280x720 屏幕",dpiStatus);
        String dpiInfo = uiDevice.executeShellCommand("wm density");
        Log.d("AAAAA",dpiInfo);
        assertEquals(expect120,dpiInfo);

        if(dpiDefault.equals("160")) {
            new UiObject(new UiSelector().text("显示缩放")).click();
            new UiObject(new UiSelector().text("笔记本 2560x1440或以上屏幕")).click();
            sleep(3000);
            dpiStatus = dpiItem.getChild(new UiSelector().resourceId("android:id/summary")).getText().toString();
            assertEquals("笔记本 2560x1440或以上屏幕", dpiStatus);
            dpiInfo = uiDevice.executeShellCommand("wm density");
            Log.d("AAAAA", dpiInfo);
            assertEquals(expect240, dpiInfo);
        }

        new UiObject(new UiSelector().text("显示缩放")).click();
        new UiObject(new UiSelector().text("笔记本 1920x1080 屏幕")).click();
        sleep(3000);
        dpiStatus = dpiItem.getChild(new UiSelector().resourceId("android:id/summary")).getText().toString();
        assertEquals("笔记本 1920x1080 屏幕",dpiStatus);
        dpiInfo = uiDevice.executeShellCommand("wm density");
        Log.d("AAAAA",dpiInfo);
        assertEquals(expect160,dpiInfo);
        new UiObject(new UiSelector().text("显示缩放")).click();
        new UiObject(new UiSelector().text("取消")).click();
        fontStatus = fontItem.getChild(new UiSelector().resourceId("android:id/summary")).getText().toString();
        assertEquals("笔记本 1920x1080 屏幕",dpiStatus);
        dpiInfo = uiDevice.executeShellCommand("wm density");
        Log.d("AAAAA",dpiInfo);
        assertEquals("Physical density: 160\n",dpiInfo);
    }

    public void test16_Notice() throws UiObjectNotFoundException, IOException {
        uiDevice = getUiDevice();

        if (new UiObject(new UiSelector().description("向上导航")).exists()) {
            new UiObject(new UiSelector().description("向上导航")).click();
        }
        UiScrollable setList = new UiScrollable(new UiSelector().resourceId("com.android.settings:id/dashboard"));
        UiObject securityItem = setList.getChildByText(new UiSelector().className(LinearLayout.class.getName()), "提示音和通知", true);
        securityItem.getChild(new UiSelector().text("提示音和通知")).click();

        //------------------------媒体音量------------------------
        //最大
        Rect musicRect = new UiObject(new UiSelector().text("媒体音量")).getFromParent(new UiSelector().resourceId("android:id/seekbar")).getVisibleBounds();
        uiDevice.click(musicRect.right-1,musicRect.centerY());
        String theVolume = verifyVolume("STREAM_MUSIC");
        assertEquals("15",theVolume);
        //最小
        uiDevice.click(musicRect.left+1,musicRect.centerY());
        theVolume = verifyVolume("STREAM_MUSIC");
        assertEquals("0,",theVolume);
        //中间
        uiDevice.click(musicRect.centerX(),musicRect.centerY());
        theVolume = verifyVolume("STREAM_MUSIC");
        assertEquals("7,",theVolume);

        //------------------------闹钟音量------------------------
        //最大
        Rect musicRect2 = new UiObject(new UiSelector().text("闹钟音量")).getFromParent(new UiSelector().resourceId("android:id/seekbar")).getVisibleBounds();
        uiDevice.click(musicRect2.right-1,musicRect2.centerY());
        theVolume = verifyVolume("STREAM_ALARM");
        assertEquals("7\n",theVolume);
        //最小
        uiDevice.click(musicRect2.left+1,musicRect2.centerY());
        theVolume = verifyVolume("STREAM_ALARM");
        assertEquals("0\n",theVolume);
        // 1/3处
        uiDevice.click(musicRect2.left+(musicRect2.right-musicRect2.left)/7*3,musicRect2.centerY());
        theVolume = verifyVolume("STREAM_ALARM");
        assertEquals("2\n",theVolume);

        //------------------------通知音量------------------------
        //最大
        Rect musicRect3 = new UiObject(new UiSelector().text("通知音量")).getFromParent(new UiSelector().resourceId("android:id/seekbar")).getVisibleBounds();
        uiDevice.click(musicRect3.right-1,musicRect3.centerY());
        theVolume = verifyVolume("STREAM_DTMF");
        assertEquals("15",theVolume);
        //最小
        uiDevice.click(musicRect3.left+1,musicRect3.centerY());
        theVolume = verifyVolume("STREAM_DTMF");
        assertEquals("0\n",theVolume);
        // 2/3处
        uiDevice.click(musicRect3.left+(musicRect3.right-musicRect3.left)/3*2,musicRect3.centerY());
        theVolume = verifyVolume("STREAM_DTMF");
        assertEquals("9\n",theVolume);

        //------------------------默认通知铃声------------------------
        new UiObject(new UiSelector().text("默认通知铃声")).click();
        if (new UiObject(new UiSelector().text("无")).isChecked()){
            new UiObject(new UiSelector().text("取消")).click();
        } else {
            new UiObject(new UiSelector().text("无")).click();
            new UiObject(new UiSelector().text("确定")).click();
        }
        new UiObject(new UiSelector().text("默认通知铃声")).click();
        new UiObject(new UiSelector().text("Argon")).click();
        new UiObject(new UiSelector().text("确定")).click();
        new UiObject(new UiSelector().text("默认通知铃声")).click();
        assertTrue(new UiObject(new UiSelector().text("Argon")).isChecked());
        assertFalse(new UiObject(new UiSelector().text("无")).isChecked());
        new UiObject(new UiSelector().text("无")).click();
        new UiObject(new UiSelector().text("确定")).click();

        //------------------------其他提示音------------------------
        new UiObject(new UiSelector().text("其他提示音")).click();
        //屏幕锁定提示音
        UiScrollable otherSoundList = new UiScrollable(new UiSelector().resourceId("android:id/list"));
        UiObject Item1 = otherSoundList.getChildByText(new UiSelector().className(LinearLayout.class.getName()), "屏幕锁定提示音", true);
        UiObject switchitem1 = Item1.getChild(new UiSelector().resourceId("android:id/switchWidget"));
        if (switchitem1.isChecked()){
            switchitem1.click();
            sleep(1000);
        }
        assertFalse(switchitem1.isChecked());
        switchitem1.click();
        sleep(1000);
        assertTrue(switchitem1.isChecked());

        //触摸提示音
        UiObject Item2 = otherSoundList.getChildByText(new UiSelector().className(LinearLayout.class.getName()), "触摸提示音", true);
        UiObject switchitem2 = Item2.getChild(new UiSelector().resourceId("android:id/switchWidget"));
        if (switchitem2.isChecked()){
            switchitem2.click();
            sleep(1000);
        }
        assertFalse(switchitem2.isChecked());
        switchitem2.click();
        sleep(1000);
        assertTrue(switchitem2.isChecked());
        new UiObject(new UiSelector().description("向上导航")).click();
        sleep(2000);

        //------------------------应用通知------------------------
        //默认 非屏蔽非优先
        new UiObject(new UiSelector().text("应用通知")).click();
        UiScrollable  appNotificationList = new UiScrollable(new UiSelector().resourceId("android:id/list"));
        appNotificationList.getChildByText(new UiSelector().className(RelativeLayout.class.getName()), "NotificationTest", true).click();
        UiScrollable notificationSettingList = new UiScrollable(new UiSelector().resourceId("android:id/list"));
        UiObject Itemforbid = notificationSettingList.getChildByText(new UiSelector().className(LinearLayout.class.getName()), "屏蔽", true);
        UiObject switchitemforbid = Itemforbid.getChild(new UiSelector().resourceId("android:id/switchWidget"));
        if (switchitemforbid.isChecked()){
            switchitemforbid.click();
            sleep(1000);
        }
        UiObject Itemfirst = notificationSettingList.getChildByText(new UiSelector().className(LinearLayout.class.getName()), "优先", true);
        UiObject switchitemfirst = Itemfirst.getChild(new UiSelector().resourceId("android:id/switchWidget"));
        if (switchitemfirst.isChecked()){
            switchitemfirst.click();
            sleep(1000);
        }
        sleep(1000);
        uiDevice.pressKeyCode(111);
        UiObject notificationTestItem = appNotificationList.getChildByText(new UiSelector().className(RelativeLayout.class.getName()), "NotificationTest", true);
        assertFalse(notificationTestItem.getChild(new UiSelector().resourceId("android:id/text1")).exists());
        closeSetting();
        startnotification();
        new UiObject(new UiSelector().text("清除所有")).click();
        uiDevice.executeShellCommand("am start -n com.example.test.notificationtest/.MainActivity");
        sleep(1000);
        new UiObject(new UiSelector().text("Send notice")).click();
        sleep(2000);
        new UiObject(new UiSelector().resourceId("android:id/mwCloseBtn")).click();
        getscreenshots();
        sleep(5000);
        startnotification();
        UiScrollable notificationList = new UiScrollable(new UiSelector().resourceId("com.android.systemui:id/notification_stack_scroller"));
        UiObject Itemnotify1 = notificationList.getChildByInstance(new UiSelector().className(FrameLayout.class.getName()), 1);
        String notifyTitle1 = Itemnotify1.getChild(new UiSelector().resourceId("android:id/title")).getText().toString();
        Log.d("AAAA",notifyTitle1);
        assertEquals("已抓取屏幕截图。",notifyTitle1);
        UiObject Itemnotify2 = notificationList.getChildByInstance(new UiSelector().className(FrameLayout.class.getName()), 7);
        String notifyTitle2 = Itemnotify2.getChild(new UiSelector().resourceId("android:id/title")).getText().toString();
        Log.d("AAAA",notifyTitle2);
        assertEquals("1",notifyTitle2);
        new UiObject(new UiSelector().text("清除所有")).click();
        //屏蔽通知
        startSetting();
        new UiObject(new UiSelector().text("提示音和通知")).click();
        new UiObject(new UiSelector().text("应用通知")).click();
        appNotificationList.getChildByText(new UiSelector().className(RelativeLayout.class.getName()), "NotificationTest", true).click();
        switchitemforbid.click();
        sleep(1000);
        uiDevice.pressKeyCode(111);
        notificationTestItem = appNotificationList.getChildByText(new UiSelector().className(RelativeLayout.class.getName()), "NotificationTest", true);
        String notificationInfo = notificationTestItem.getChild(new UiSelector().resourceId("android:id/text1")).getText().toString();
        assertEquals("屏蔽",notificationInfo);
        closeSetting();
        uiDevice.executeShellCommand("am start -n com.example.test.notificationtest/.MainActivity");
        sleep(1000);
        new UiObject(new UiSelector().text("Send notice")).click();
        sleep(2000);
        new UiObject(new UiSelector().resourceId("android:id/mwCloseBtn")).click();
        sleep(2000);
        startnotification();
        assertFalse(new UiObject(new UiSelector().text("1")).exists());
        new UiObject(new UiSelector().text("清除所有")).click();
        //通知优先
        startSetting();
        new UiObject(new UiSelector().text("提示音和通知")).click();
        new UiObject(new UiSelector().text("应用通知")).click();
        appNotificationList.getChildByText(new UiSelector().className(RelativeLayout.class.getName()), "NotificationTest", true).click();
        switchitemforbid.click();
        sleep(1000);
        switchitemfirst.click();
        sleep(1000);
        uiDevice.pressKeyCode(111);
        notificationTestItem = appNotificationList.getChildByText(new UiSelector().className(RelativeLayout.class.getName()), "NotificationTest", true);
        notificationInfo = notificationTestItem.getChild(new UiSelector().resourceId("android:id/text1")).getText().toString();
        assertEquals("优先",notificationInfo);
        closeSetting();
        uiDevice.executeShellCommand("am start -n com.example.test.notificationtest/.MainActivity");
        sleep(1000);
        new UiObject(new UiSelector().text("Send notice")).click();
        sleep(1000);
        new UiObject(new UiSelector().text("Send notice")).click();
        sleep(2000);
        new UiObject(new UiSelector().resourceId("android:id/mwCloseBtn")).click();
        getscreenshots();
        sleep(5000);
        startnotification();
        notificationList = new UiScrollable(new UiSelector().resourceId("com.android.systemui:id/notification_stack_scroller"));
        Itemnotify1 = notificationList.getChildByInstance(new UiSelector().className(FrameLayout.class.getName()), 1);
        notifyTitle1 = Itemnotify1.getChild(new UiSelector().resourceId("android:id/title")).getText().toString();
        Log.d("AAAA",notifyTitle1);
        assertEquals("2",notifyTitle1);
        Itemnotify2 = notificationList.getChildByInstance(new UiSelector().className(FrameLayout.class.getName()), 5);
        notifyTitle2 = Itemnotify2.getChild(new UiSelector().resourceId("android:id/title")).getText().toString();
        Log.d("AAAA",notifyTitle2);
        assertEquals("已抓取屏幕截图。",notifyTitle2);
        new UiObject(new UiSelector().text("清除所有")).click();
        //使用通知权
        startSetting();
        new UiObject(new UiSelector().text("提示音和通知")).click();
        new UiObject(new UiSelector().text("通知使用权")).click();
        UiScrollable useNotifyList = new UiScrollable(new UiSelector().resourceId("android:id/list"));
        UiObject tvItem = useNotifyList.getChildByText(new UiSelector().className(LinearLayout.class.getName()), "云视听悦厅TV", true);
        UiObject tvCheckbox =tvItem.getChild(new UiSelector().resourceId("com.android.settings:id/checkbox"));
        if(tvCheckbox.isChecked()){
            tvCheckbox.click();
        }
        new UiObject(new UiSelector().text("云视听悦厅TV")).click();
        assertTrue(new UiObject(new UiSelector().text("要向云视听悦厅TV授予此权限吗？")).exists());
        new UiObject(new UiSelector().text("取消")).click();
        assertFalse(tvCheckbox.isChecked());
        new UiObject(new UiSelector().text("云视听悦厅TV")).click();
        assertTrue(new UiObject(new UiSelector().text("要向云视听悦厅TV授予此权限吗？")).exists());
        new UiObject(new UiSelector().text("确定")).click();
        assertTrue(tvCheckbox.isChecked());
        new UiObject(new UiSelector().text("云视听悦厅TV")).click();
        assertFalse(tvCheckbox.isChecked());
        new UiObject(new UiSelector().description("向上导航")).click();
    }

    public void test17_PowerManager() throws UiObjectNotFoundException, IOException {
        uiDevice = getUiDevice();
        if (new UiObject(new UiSelector().description("向上导航")).exists()) {
            new UiObject(new UiSelector().description("向上导航")).click();
        }
        UiScrollable setList = new UiScrollable(new UiSelector().resourceId("com.android.settings:id/dashboard"));
        UiObject securityItem = setList.getChildByText(new UiSelector().className(LinearLayout.class.getName()), "电源管理", true);
        securityItem.getChild(new UiSelector().text("电源管理")).click();

        //------------------------节能设置------------------------
        new UiObject(new UiSelector().text("节能设置")).click();
        new UiObject(new UiSelector().text("高效")).click();
        new UiObject(new UiSelector().text("确定")).click();
        new UiObject(new UiSelector().text("节能设置")).click();
        assertTrue(new UiObject(new UiSelector().text("高效")).isChecked());
        new UiObject(new UiSelector().text("平衡")).click();
        new UiObject(new UiSelector().text("确定")).click();
        new UiObject(new UiSelector().text("节能设置")).click();
        assertTrue(new UiObject(new UiSelector().text("平衡")).isChecked());
        new UiObject(new UiSelector().text("取消")).click();
    }

    public void test20_Security() throws UiObjectNotFoundException, IOException {
        uiDevice = getUiDevice();
        if (new UiObject(new UiSelector().description("向上导航")).exists()) {
            new UiObject(new UiSelector().description("向上导航")).click();
        }
        UiScrollable setList = new UiScrollable(new UiSelector().resourceId("com.android.settings:id/dashboard"));
        UiObject securityItem = setList.getChildByText(new UiSelector().className(LinearLayout.class.getName()), "安全", true);
        securityItem.getChild(new UiSelector().text("安全")).click();
        UiScrollable securityList = new UiScrollable(new UiSelector().resourceId("android:id/list"));

        //------------------------屏幕锁定方式------------------------
        //图案
        new UiObject(new UiSelector().text("屏幕锁定方式")).click();
        new UiObject(new UiSelector().text("图案")).click();
        Rect rectPattern = new UiObject(new UiSelector().resourceId("com.android.settings:id/lockPattern")).getVisibleBounds();
        Point[] p = getPoints(rectPattern);
        uiDevice.swipe(p,20);
        new UiObject(new UiSelector().text("继续")).click();
        uiDevice.swipe(p,20);
        new UiObject(new UiSelector().text("确认")).click();
        new UiObject(new UiSelector().text("完成")).click();
        closeSetting();
        lockScreen();

        uiDevice.pressEnter();
        if(! new UiObject(new UiSelector().resourceId("com.android.systemui:id/lockPatternView")).exists()){
            sleep(5000);
            uiDevice.pressEnter();
        }
        Rect rectPattern1 = new UiObject(new UiSelector().resourceId("com.android.systemui:id/lockPatternView")).getVisibleBounds();
        Point[] p1 = getPoints(rectPattern1);
        uiDevice.swipe(p1,20);
        sleep(2000);

        startSetting();
        securityItem = setList.getChildByText(new UiSelector().className(LinearLayout.class.getName()), "安全", true);
        securityItem.getChild(new UiSelector().text("安全")).click();
        new UiObject(new UiSelector().text("屏幕锁定方式")).click();
        rectPattern = new UiObject(new UiSelector().resourceId("com.android.settings:id/lockPattern")).getVisibleBounds();
        p = getPoints(rectPattern);
        uiDevice.swipe(p,20);
        new UiObject(new UiSelector().text("PIN码")).click();
        new UiObject(new UiSelector().resourceId("com.android.settings:id/password_entry")).setText("0123456789");
        new UiObject(new UiSelector().text("继续")).click();
        new UiObject(new UiSelector().resourceId("com.android.settings:id/password_entry")).setText("0123456789");
        new UiObject(new UiSelector().text("确定")).click();
        closeSetting();
        lockScreen();

        uiDevice.pressEnter();
        if(! new UiObject(new UiSelector().text("ABC")).exists()){
            sleep(5000);
            uiDevice.pressEnter();
        }
        for(int i=7;i<17;i++) {
            uiDevice.pressKeyCode(i);
        }
        uiDevice.pressEnter();
        sleep(2000);

        lockScreen();
        uiDevice.pressEnter();
        if(! new UiObject(new UiSelector().text("ABC")).exists()){
            sleep(5000);
            uiDevice.pressEnter();
        }
        for(int i=0;i<10;i++){
            new UiObject(new UiSelector().text(Integer.toString(i))).click();
        }
        uiDevice.pressEnter();
        sleep(2000);

        startSetting();
        securityItem = setList.getChildByText(new UiSelector().className(LinearLayout.class.getName()), "安全", true);
        securityItem.getChild(new UiSelector().text("安全")).click();
        new UiObject(new UiSelector().text("屏幕锁定方式")).click();
        new UiObject(new UiSelector().resourceId("com.android.settings:id/password_entry")).setText("0123456789");
        new UiObject(new UiSelector().text("继续")).click();
        new UiObject(new UiSelector().text("密码")).click();
        new UiObject(new UiSelector().resourceId("com.android.settings:id/password_entry")).setText("abc123");
        new UiObject(new UiSelector().text("继续")).click();
        new UiObject(new UiSelector().resourceId("com.android.settings:id/password_entry")).setText("abc123");
        new UiObject(new UiSelector().text("确定")).click();
        closeSetting();
        lockScreen();

        uiDevice.pressEnter();
        if(! new UiObject(new UiSelector().resourceId("com.android.systemui:id/passwordEntry")).exists()){
            sleep(5000);
            uiDevice.pressEnter();
        }
        new UiObject(new UiSelector().resourceId("com.android.systemui:id/passwordEntry")).setText("abc123");
        uiDevice.pressEnter();
        sleep(2000);

        startSetting();
        securityItem = setList.getChildByText(new UiSelector().className(LinearLayout.class.getName()), "安全", true);
        securityItem.getChild(new UiSelector().text("安全")).click();
        new UiObject(new UiSelector().text("屏幕锁定方式")).click();
        new UiObject(new UiSelector().resourceId("com.android.settings:id/password_entry")).setText("abc123");
        new UiObject(new UiSelector().text("继续")).click();
        new UiObject(new UiSelector().text("无")).click();
        assertTrue(new UiObject(new UiSelector().text("设备保护功能将无法再发挥作用。")).exists());
        new UiObject(new UiSelector().text("确定")).click();
        closeSetting();
        lockScreen();

        sleep(3000);
        uiDevice.pressEnter();
        sleep(3000);
        uiDevice.pressEnter();

        startSetting();
        securityItem = setList.getChildByText(new UiSelector().className(LinearLayout.class.getName()), "安全", true);
        securityItem.getChild(new UiSelector().text("安全")).click();

        //------------------------机主信息------------------------
        new UiObject(new UiSelector().text("机主信息")).click();

        //非勾选状态
        if(new UiObject(new UiSelector().text("在锁定屏幕上显示机主信息")).isChecked()) {
            new UiObject(new UiSelector().text("在锁定屏幕上显示机主信息")).click();
        }
        assertFalse(new UiObject(new UiSelector().resourceId("com.android.settings:id/owner_info_edit_text")).isEnabled());
        //勾选状态
        new UiObject(new UiSelector().text("在锁定屏幕上显示机主信息")).click();
        assertTrue(new UiObject(new UiSelector().resourceId("com.android.settings:id/owner_info_edit_text")).isEnabled());
        new UiObject(new UiSelector().resourceId("com.android.settings:id/owner_info_edit_text")).setText("test message");
        sleep(2000);
        closeSetting();
        lockScreen();
        sleep(1000);
        String ownerInfo = new UiObject(new UiSelector().resourceId("com.android.systemui:id/owner_info")).getText();
        assertEquals("test message",ownerInfo);
        sleep(3000);
        uiDevice.pressEnter();
        sleep(3000);
        uiDevice.pressEnter();

        startSetting();
        securityItem = setList.getChildByText(new UiSelector().className(LinearLayout.class.getName()), "安全", true);
        securityItem.getChild(new UiSelector().text("安全")).click();
        new UiObject(new UiSelector().text("机主信息")).click();
        new UiObject(new UiSelector().resourceId("com.android.settings:id/owner_info_edit_text")).setText("");
        new UiObject(new UiSelector().text("在锁定屏幕上显示机主信息")).click();
        new UiObject(new UiSelector().description("向上导航")).click();

        //------------------------显示密码------------------------
        UiObject showpasswdItem = securityList.getChildByText(new UiSelector().className(LinearLayout.class.getName()), "显示密码", true);
        UiObject showpasswdSwitch = showpasswdItem.getChild(new UiSelector().resourceId("android:id/switchWidget"));
        if(showpasswdSwitch.isChecked()) {
            new UiObject(new UiSelector().text("显示密码")).click();
        }
        assertFalse(showpasswdSwitch.isChecked());
        new UiObject(new UiSelector().text("显示密码")).click();
        sleep(1000);
        assertTrue(showpasswdSwitch.isChecked());

        //------------------------设备管理器------------------------
        new UiObject(new UiSelector().text("设备管理器")).click();
        //激活
        UiScrollable deviceManagerList = new UiScrollable(new UiSelector().resourceId("android:id/list"));
        UiObject outlookItem = deviceManagerList.getChildByText(new UiSelector().className(LinearLayout.class.getName()), "Outlook设备政策", true);
        UiObject outlookCheckbox = outlookItem.getChild(new UiSelector().resourceId("com.android.settings:id/checkbox"));
        if(outlookCheckbox.isChecked()){
            new UiObject(new UiSelector().text("Outlook设备政策")).click();
            new UiObject(new UiSelector().text("取消激活")).click();
            sleep(5000);
            if(new UiObject(new UiSelector().text("确定")).exists());
            {
                new UiObject(new UiSelector().text("确定")).click();
            }
        }
        sleep(3000);
        new UiObject(new UiSelector().text("Outlook设备政策")).click();
        assertTrue(new UiObject(new UiSelector().text("监视屏幕解锁尝试次数")).exists());
        new UiObject(new UiSelector().text("激活")).click();
        UiObject outlookCheckboxNEW = outlookItem.getChild(new UiSelector().resourceId("com.android.settings:id/checkbox"));
        //assertTrue(outlookCheckboxNEW.isChecked());
        //取消激活
        sleep(3000);
        new UiObject(new UiSelector().text("Outlook设备政策")).click();
        new UiObject(new UiSelector().text("取消激活")).click();
        sleep(7000);
        if(new UiObject(new UiSelector().text("确定")).exists());
        {
            new UiObject(new UiSelector().text("确定")).click();
        }
        sleep(1000);
        new UiObject(new UiSelector().description("向上导航")).click();

        //------------------------未知来源------------------------
        UiObject unknownsourceItem = securityList.getChildByText(new UiSelector().className(LinearLayout.class.getName()), "未知来源", true);
        UiObject unknownsourceSwitch = unknownsourceItem.getChild(new UiSelector().resourceId("android:id/switchWidget"));
        //关闭未知来源
        if(unknownsourceSwitch.isChecked()){
            unknownsourceSwitch.click();
        }
        sleep(1000);
        closeSetting();
        sleep(1000);
        //在应用商店下载并安装os monitor，验证安装时是否会提示禁止安装
        uiDevice.executeShellCommand("am start -n org.openthos.appstore/.MainActivity");
        sleep(5000);
        new UiObject(new UiSelector().resourceId("org.openthos.appstore:id/rb_software")).click();
        sleep(5000);
        new UiObject((new UiSelector().resourceId("org.openthos.appstore:id/activity_title_search_text"))).setText("monitor");
        uiDevice.pressEnter();
        uiDevice.pressEnter();
        new UiObject(new UiSelector().resourceId("org.openthos.appstore:id/app_item_install")).click();
        UiObject install_install = new UiObject(new UiSelector().resourceId("com.android.packageinstaller:id/ok_button"));
        UiObject forbid_install = new UiObject(new UiSelector().text("禁止安装"));
        int count=0;
        while (!install_install.exists() && !forbid_install.exists()) {
            if (!install_install.exists() && !forbid_install.exists()) {
                sleep(2000);
                count++;
            }
            if(count>30){
                assertTrue(false);
            }
        }
        count=0;
        if(forbid_install.exists()){
            assertTrue(true);
            new UiObject(new UiSelector().text("取消")).click();
            uiDevice.pressKeyCode(134,2);
        }else {
            assertTrue(false);
        }
        //打开未知来源
        startSetting();
        securityItem = setList.getChildByText(new UiSelector().className(LinearLayout.class.getName()), "安全", true);
        securityItem.getChild(new UiSelector().text("安全")).click();
        unknownsourceSwitch.click();
        new UiObject(new UiSelector().text("确定")).click();
        sleep(1000);
        closeSetting();
        sleep(1000);
        //在应用商店下载并安装os monitor，验证安装时是否可直接安装
        uiDevice.executeShellCommand("am start -n org.openthos.appstore/.MainActivity");
        sleep(2000);
        new UiObject(new UiSelector().resourceId("org.openthos.appstore:id/rb_software")).click();
        sleep(2000);
        new UiObject((new UiSelector().resourceId("org.openthos.appstore:id/activity_title_search_text"))).setText("monitor");
        uiDevice.pressEnter();
        uiDevice.pressEnter();
        new UiObject(new UiSelector().resourceId("org.openthos.appstore:id/app_item_install")).click();
        install_install = new UiObject(new UiSelector().resourceId("com.android.packageinstaller:id/ok_button"));
        forbid_install = new UiObject(new UiSelector().text("禁止安装"));
        count=0;
        while (!install_install.exists() && !forbid_install.exists()) {
            if (!install_install.exists() && !forbid_install.exists()) {
                sleep(2000);
                count++;
            }
            if(count>30){
                assertTrue(false);
            }
        }
        count=0;
        if(new UiObject(new UiSelector().text("软件包安装程序")).exists()){
            assertTrue(true);
            new UiObject(new UiSelector().text("取消")).click();
            new UiObject(new UiSelector().text("管理")).click();
            new UiObject(new UiSelector().text("下载")).click();
            UiScrollable downloadList = new UiScrollable(new UiSelector().resourceId("org.openthos.appstore:id/customlistView"));
            UiObject osmonitorItem = downloadList.getChildByText(new UiSelector().className(LinearLayout.class.getName()), "OS Monitor", true);
            osmonitorItem.getChild(new UiSelector().text("移除")).click();
            uiDevice.pressKeyCode(134,2);
        }else {
            assertTrue(false);
        }
        startSetting();
        securityItem = setList.getChildByText(new UiSelector().className(LinearLayout.class.getName()), "安全", true);
        securityItem.getChild(new UiSelector().text("安全")).click();

        //------------------------凭据存储------------------------
        //存储类型
        UiObject typeItem = securityList.getChildByText(new UiSelector().className(LinearLayout.class.getName()), "存储类型", true);
        String type = typeItem.getChild(new UiSelector().resourceId("android:id/summary")).getText().toString();
        assertEquals("仅限软件",type);
        //信任的凭据
        securityList.getChildByText(new UiSelector().className(LinearLayout.class.getName()), "信任的凭据", true).click();
        sleep(3000);
        assertTrue(new UiObject(new UiSelector().text("AC Camerfirma S.A.")).exists());
        new UiObject(new UiSelector().description("向上导航")).click();
        //从SD卡安装
        securityList.getChildByText(new UiSelector().className(LinearLayout.class.getName()), "从SD卡安装", true).click();
        sleep(1000);
        assertTrue(new UiObject(new UiSelector().text("内部存储空间")).exists());
        uiDevice.pressKeyCode(111);
        //清除凭据
        UiObject cleanCerItem = securityList.getChildByText(new UiSelector().className(LinearLayout.class.getName()), "清除凭据", true);
        sleep(1000);
        assertFalse(cleanCerItem.isEnabled());

        //------------------------高级------------------------
        //信任的代理

        //屏幕固定
        securityList.getChildByText(new UiSelector().className(LinearLayout.class.getName()), "屏幕固定", true).click();
        if(new UiObject(new UiSelector().text("已关闭")).exists()){
            new UiObject(new UiSelector().text("已关闭")).click();
        }
        sleep(1000);
        assertTrue(new UiObject(new UiSelector().text("取消固定屏幕时锁定设备")).exists());
        new UiObject(new UiSelector().text("已开启")).click();
        sleep(1000);
        assertTrue(new UiObject(new UiSelector().resourceId("com.android.settings:id/screen_pinning_description")).exists());
        new UiObject(new UiSelector().description("向上导航")).click();
        //有权查看使用情况的应用
        securityList.getChildByText(new UiSelector().className(LinearLayout.class.getName()), "有权查看使用情况的应用", true).click();
        assertTrue(new UiObject(new UiSelector().resourceId("android:id/list")).exists());
        new UiObject(new UiSelector().description("向上导航")).click();
    }

    public void test21_account() throws UiObjectNotFoundException, IOException {
        uiDevice = getUiDevice();
        closeSetting();
        sleep(1000);
        installapp("WPS邮箱");
        sleep(2000);
        startSetting();
        UiScrollable setList = new UiScrollable(new UiSelector().resourceId("com.android.settings:id/dashboard"));
        UiObject securityItem = setList.getChildByText(new UiSelector().className(LinearLayout.class.getName()), "帐户", true);
        securityItem.getChild(new UiSelector().text("帐户")).click();
        UiScrollable accountList = new UiScrollable(new UiSelector().resourceId("android:id/list"));

        //------------------------添加帐户------------------------
        accountList.getChildByText(new UiSelector().className(LinearLayout.class.getName()), "添加帐户", true).click();
        sleep(1000);
        UiScrollable addaccountList = new UiScrollable(new UiSelector().resourceId("android:id/list"));
        addaccountList.getChildByText(new UiSelector().className(LinearLayout.class.getName()), "公司", true).click();
        new UiObject(new UiSelector().resourceId("com.kingsoft.email:id/account_email")).setText("openthostest@126.com");
        uiDevice.pressEnter();
        new UiObject(new UiSelector().resourceId("com.kingsoft.email:id/account_password")).setText("openthos1");
        new UiObject(new UiSelector().text("登录")).click();
        sleep(30000);
        assertTrue(new UiObject(new UiSelector().text("收件箱")).exists());
        uiDevice.pressKeyCode(111);
        sleep(1000);

        //------------------------同步数据------------------------
        //自动同步数据
        new UiObject(new UiSelector().description("更多选项")).click();
        UiObject syncCheckbox = new UiObject(new UiSelector().resourceId("android:id/checkbox"));
        if (!syncCheckbox.isChecked()){
            syncCheckbox.click();
            new UiObject(new UiSelector().text("确定")).click();
        }else {
            uiDevice.pressKeyCode(111);
            sleep(2000);
        }
        accountList.getChildByText(new UiSelector().className(LinearLayout.class.getName()), "IMAP", true).click();
        new UiObject(new UiSelector().description("更多选项")).click();
        //获取当前时间
        String[] dates = uiDevice.executeShellCommand("date +%F").split("-");
        String year = dates[0];
        int month = Integer.parseInt(dates[1]);
        int date = Integer.parseInt(dates[2].split("\n")[0]);
        String time = uiDevice.executeShellCommand("date +%R").split("\n")[0];
        String nowtime = "上次同步时间：" + year + "/" + month + "/" + date + " " + time;
        //立即同步
        new UiObject(new UiSelector().text("立即同步")).click();
        sleep(5000);
        UiScrollable imapList = new UiScrollable(new UiSelector().resourceId("android:id/list"));
        UiObject openthostest126 = imapList.getChildByText(new UiSelector().className(LinearLayout.class.getName()), "openthostest@126.com", true);
        String syncStatus = openthostest126.getChild(new UiSelector().resourceId("android:id/summary")).getText().toString();
        assertEquals(nowtime,syncStatus);
        //取消自动同步数据
        new UiObject(new UiSelector().description("向上导航")).click();
        new UiObject(new UiSelector().description("更多选项")).click();
        new UiObject(new UiSelector().text("自动同步数据")).click();
        assertTrue(new UiObject(new UiSelector().text("要关闭自动同步数据功能吗？")).exists());
        new UiObject(new UiSelector().text("确定")).click();
        accountList.getChildByText(new UiSelector().className(LinearLayout.class.getName()), "IMAP", true).click();
        syncStatus = openthostest126.getChild(new UiSelector().resourceId("android:id/summary")).getText().toString();
        assertEquals("同步功能已关闭",syncStatus);
        //帐户设置
        new UiObject(new UiSelector().text("邮箱账号设置")).click();
        sleep(1000);
        assertTrue(new UiObject(new UiSelector().text("手势密码")).exists());
        uiDevice.pressKeyCode(111);
        sleep(5000);
        //移除帐户
        new UiObject(new UiSelector().text("openthostest@126.com")).click();
        new UiObject(new UiSelector().description("更多选项")).click();
        new UiObject(new UiSelector().text("移除帐户")).click();
        assertTrue(new UiObject(new UiSelector().text("要移除帐户吗？")).exists());
        new UiObject(new UiSelector().text("移除帐户")).click();
        assertTrue(new UiObject(new UiSelector().text("添加帐户")).exists());
        assertFalse(new UiObject(new UiSelector().text("IMAP")).exists());
        closeSetting();
        removeapp("WPS邮箱");
        startSetting();
        new UiObject(new UiSelector().text("帐户")).click();
    }



    public void test23_languageAndInput() throws UiObjectNotFoundException, IOException {
        uiDevice = getUiDevice();
        if(new UiObject(new UiSelector().description("向上导航")).exists()) {
            new UiObject(new UiSelector().description("向上导航")).click();
        }
        new UiObject(new UiSelector().text("语言和输入法")).click();
        //------------------------语言------------------------
        //切换英文
        UiScrollable languageHomeList = new UiScrollable(new UiSelector().resourceId("android:id/list"));
        UiObject languageItem = languageHomeList.getChildByText(new UiSelector().className(LinearLayout.class.getName()), "语言", true);
        languageItem.click();
        UiScrollable languageList = new UiScrollable(new UiSelector().resourceId("android:id/list"));
        languageList.getChildByText(new UiSelector().className(LinearLayout.class.getName()), "English (United States)", true).click();
        assertTrue(new UiObject(new UiSelector().text("Spell checker")).exists());
        String languageInfo = languageItem.getChild(new UiSelector().resourceId("android:id/summary")).getText().toString();
        assertEquals("English (United States)",languageInfo);
        //切换中文
        languageItem.click();
        languageList.getChildByText(new UiSelector().className(LinearLayout.class.getName()), "中文 (简体)", true).click();
        assertTrue(new UiObject(new UiSelector().text("拼写检查工具")).exists());
        languageInfo = languageItem.getChild(new UiSelector().resourceId("android:id/summary")).getText().toString();
        assertEquals("中文 (简体)",languageInfo);
    }


    public String verifyVolume(String command) throws IOException{
        uiDevice = getUiDevice();
        String audioInfo = uiDevice.executeShellCommand("dumpsys audio");
        int InfoStart=audioInfo.indexOf(command);
        String volumeInfo = audioInfo.substring(InfoStart,InfoStart+180);
        int volumeInfoStart1= volumeInfo.indexOf("2 (speaker):");
        String theVolume = volumeInfo.substring(volumeInfoStart1+13,volumeInfoStart1+15);
        return theVolume;
    }
    public Point[] getPoints(Rect rectPattern){
        int dx=(rectPattern.right-rectPattern.left)/6;
        int dy=(rectPattern.bottom-rectPattern.top)/6;
        Point point1 = new Point(rectPattern.left+dx,rectPattern.top+dy);
        Point point2 = new Point(rectPattern.left+dx*3,rectPattern.top+dy);
        Point point3 = new Point(rectPattern.left+dx*5,rectPattern.top+dy);
        Point point4 = new Point(rectPattern.centerX(),rectPattern.centerY());
        Point point5 = new Point(rectPattern.left+dx*5,rectPattern.top+dy*5);
        Point point6 = new Point(rectPattern.left+dx*3,rectPattern.top+dy*5);
        Point point7 = new Point(rectPattern.left+dx,rectPattern.top+dy*5);
        Point point8 = new Point(rectPattern.left+dx,rectPattern.top+dy*3);
        Point point9 = new Point(rectPattern.left+dx*5,rectPattern.top+dy*3);
        Point[] p = {point1,point2,point3,point4,point5,point6,point7,point8,point9};
        return p;
    }
    public void closeSetting() throws UiObjectNotFoundException {
        uiDevice = getUiDevice();
        sleep(1000);
        uiDevice.swipe(0,0,100,1,2);
        new UiObject(new UiSelector().resourceId("android:id/mwCloseBtn")).click();
        sleep(2000);
    }
    public void startSetting() throws UiObjectNotFoundException,IOException{
        uiDevice = getUiDevice();
        sleep(1000);
        if(new UiObject(new UiSelector().resourceId("com.android.systemui:id/notification_panel")).exists()){
            uiDevice.pressKeyCode(111);
            sleep(1000);
        }
        String devicesInfo = uiDevice.executeShellCommand("cat /proc/bus/input/devices");
        int mouseInfoStart=devicesInfo.indexOf("mouse0");
        String mouseInfo = devicesInfo.substring(mouseInfoStart+12,mouseInfoStart+13);
        uiDevice.executeShellCommand("sendevent /dev/input/event" + mouseInfo + " 2 0 0");
        uiDevice.executeShellCommand("sendevent /dev/input/event" + mouseInfo + " 2 1 -200");
        uiDevice.executeShellCommand("sendevent /dev/input/event" + mouseInfo + " 0 0 0");
        sleep(1000);
        uiDevice.executeShellCommand("sendevent /dev/input/event" + mouseInfo + " 4 4 589825");
        uiDevice.executeShellCommand("sendevent /dev/input/event" + mouseInfo + " 1 272 1");
        uiDevice.executeShellCommand("sendevent /dev/input/event" + mouseInfo + " 0 0 0");
        uiDevice.executeShellCommand("sendevent /dev/input/event" + mouseInfo + " 4 4 589825");
        uiDevice.executeShellCommand("sendevent /dev/input/event" + mouseInfo + " 1 272 0");
        uiDevice.executeShellCommand("sendevent /dev/input/event" + mouseInfo + " 0 0 0");
        uiDevice.executeShellCommand("am start -n com.android.settings/.Settings");
        sleep(2000);

        if(new UiObject(new UiSelector().resourceId("android:id/mwMaximizeBtn")).exists()){
            new UiObject(new UiSelector().resourceId("android:id/mwMaximizeBtn")).click();
        }
    }
    public void lockScreen() throws UiObjectNotFoundException,IOException{
        uiDevice = getUiDevice();
        sleep(2000);

        String devicesInfo = uiDevice.executeShellCommand("cat /proc/bus/input/devices");
        int mouseInfoStart=devicesInfo.indexOf("mouse0");
        String mouseInfo = devicesInfo.substring(mouseInfoStart+12,mouseInfoStart+13);

        uiDevice.executeShellCommand("sendevent /dev/input/event" + mouseInfo + " 2 0 -2000");
        uiDevice.executeShellCommand("sendevent /dev/input/event" + mouseInfo + " 2 1 2000");
        uiDevice.executeShellCommand("sendevent /dev/input/event" + mouseInfo + " 0 0 0");
        uiDevice.executeShellCommand("sendevent /dev/input/event" + mouseInfo + " 2 0 200");
        uiDevice.executeShellCommand("sendevent /dev/input/event" + mouseInfo + " 2 1 -200");
        uiDevice.executeShellCommand("sendevent /dev/input/event" + mouseInfo + " 0 0 0");
        uiDevice.executeShellCommand("sendevent /dev/input/event" + mouseInfo + " 2 0 -2000");
        uiDevice.executeShellCommand("sendevent /dev/input/event" + mouseInfo + " 2 1 2000");
        uiDevice.executeShellCommand("sendevent /dev/input/event" + mouseInfo + " 0 0 0");
        uiDevice.executeShellCommand("sendevent /dev/input/event" + mouseInfo + " 2 0 5");
        uiDevice.executeShellCommand("sendevent /dev/input/event" + mouseInfo + " 2 1 -5");
        uiDevice.executeShellCommand("sendevent /dev/input/event" + mouseInfo + " 0 0 0");
        sleep(1000);
        uiDevice.executeShellCommand("sendevent /dev/input/event" + mouseInfo + " 4 4 589825");
        uiDevice.executeShellCommand("sendevent /dev/input/event" + mouseInfo + " 1 272 1");
        uiDevice.executeShellCommand("sendevent /dev/input/event" + mouseInfo + " 0 0 0");
        uiDevice.executeShellCommand("sendevent /dev/input/event" + mouseInfo + " 4 4 589825");
        uiDevice.executeShellCommand("sendevent /dev/input/event" + mouseInfo + " 1 272 0");
        uiDevice.executeShellCommand("sendevent /dev/input/event" + mouseInfo + " 0 0 0");

        sleep(1000);
        new UiObject(new UiSelector().text("电源")).click();
        new UiObject(new UiSelector().text("锁定")).click();
        sleep(2000);
    }

    public void installapp(String appname) throws UiObjectNotFoundException,IOException{
        uiDevice=getUiDevice();
        uiDevice.executeShellCommand("am start -n org.openthos.appstore/.MainActivity");
        sleep(2000);
        new UiObject(new UiSelector().resourceId("org.openthos.appstore:id/rb_software")).click();
        sleep(2000);
        new UiObject((new UiSelector().resourceId("org.openthos.appstore:id/activity_title_search_text"))).setText(appname);
        uiDevice.pressEnter();
        uiDevice.pressEnter();

        new UiObject(new UiSelector().resourceId("org.openthos.appstore:id/app_item_install")).click();
        /*UiObject install_install = new UiObject(new UiSelector().resourceId("com.android.packageinstaller:id/ok_button"));
        int count=0;
        while (!install_install.exists()) {
            if (!install_install.exists()) {
                sleep(2000);
                count++;
            }
            if(count>30){
                assertTrue(false);
            }
        }
        count=0;

        //安装应用，一直点ok_button直到安装完成，最后点完成按钮
        while (install_install.exists()) {
            if (install_install.exists()) {
                install_install.click();
            }
            if(count>30){
                assertTrue(false);
            }
        }
        count=0;
        UiObject install_finish = new UiObject(new UiSelector().resourceId("com.android.packageinstaller:id/done_button"));
        while (!install_finish.exists() ) {
            if (!install_finish.exists()) {
                sleep(1000);
            }
            if(count>30) {
                assertTrue(false);
            }
        }
        install_finish.click();*/
        sleep(20000);
        uiDevice.pressKeyCode(134,2);
    }

    public void removeapp(String appname) throws UiObjectNotFoundException, IOException {
        uiDevice = getUiDevice();
        uiDevice.executeShellCommand("am start -n org.openthos.appstore/.MainActivity");
        sleep(2000);
        new UiObject(new UiSelector().resourceId("org.openthos.appstore:id/rb_manager")).click();
        new UiObject(new UiSelector().text("已安装")).click();
        UiScrollable settingsList = new UiScrollable(new UiSelector().resourceId("org.openthos.appstore:id/customlistView"));
        UiObject btItem = settingsList.getChildByText(new UiSelector().className(LinearLayout.class.getName()), appname, true);
        btItem.getChild(new UiSelector().text("卸载")).click();
        UiSelector textStop = new UiSelector().text("强行停止");
        new UiObject(textStop.fromParent(new UiSelector().text("卸载"))).click();
        new UiObject(new UiSelector().text("确定")).click();

        //移除app
        new UiObject(new UiSelector().resourceId("org.openthos.appstore:id/rb_manager")).click();
        new UiObject(new UiSelector().resourceId("org.openthos.appstore:id/download")).click();
        UiScrollable settingsList1 = new UiScrollable(new UiSelector().resourceId("org.openthos.appstore:id/customlistView"));
        UiObject btItem1 = settingsList1.getChildByText(new UiSelector().className(LinearLayout.class.getName()), appname, true);
        btItem1.getChild(new UiSelector().text("移除")).click();
        sleep(1000);
        uiDevice.pressKeyCode(134,2);
    }
    private void getscreenshots() throws IOException{
        uiDevice=getUiDevice();
        String devicesInfo = uiDevice.executeShellCommand("cat /proc/bus/input/devices");
        int mouseInfoStart=devicesInfo.indexOf("sysrq kbd leds");
        String mouseInfo = devicesInfo.substring(mouseInfoStart+20,mouseInfoStart+21);

        uiDevice.executeShellCommand("sendevent /dev/input/event" + mouseInfo + " 4 4 183");
        uiDevice.executeShellCommand("sendevent /dev/input/event" + mouseInfo + " 1 99 1");
        uiDevice.executeShellCommand("sendevent /dev/input/event" + mouseInfo + " 0 0 0");
        uiDevice.executeShellCommand("sendevent /dev/input/event" + mouseInfo + " 4 4 183");
        uiDevice.executeShellCommand("sendevent /dev/input/event" + mouseInfo + " 1 99 0");
        uiDevice.executeShellCommand("sendevent /dev/input/event" + mouseInfo + " 0 0 0");
    }
    private void startnotification(){
        uiDevice=getUiDevice();
        uiDevice.pressKeyCode(KeyEvent.KEYCODE_TAB,2);
        uiDevice.pressKeyCode(KeyEvent.KEYCODE_ENTER);
        uiDevice.openNotification();
    }
}
