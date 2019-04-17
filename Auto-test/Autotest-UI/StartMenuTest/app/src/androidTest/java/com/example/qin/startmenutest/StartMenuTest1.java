package com.example.qin.startmenutest;

import android.graphics.Rect;
import android.support.test.uiautomator.RUiAutomatorTestCase;
import android.support.test.uiautomator.RUiDevice;
import android.support.test.uiautomator.RUiObject;
import android.support.test.uiautomator.RUiScrollable;
import android.support.test.uiautomator.UiAutomatorTestCase;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.IOException;


/**
 * Created by qin on 9/7/17.
 */

public class StartMenuTest1 extends RUiAutomatorTestCase {

    public void test1_Preparation() throws UiObjectNotFoundException, IOException{
        RUiDevice uiDevice = getRUiDevice();
        UiDevice uiDevice1=getUiDevice();
        //打开设置的“未知来源”选项，为安装os monitor做准备
        uiDevice.executeShellCommand("am start -n com.android.settings/.Settings");
        sleep(2000);
        UiScrollable  setList = new UiScrollable(new UiSelector().resourceId("com.android.settings:id/dashboard"));
        UiObject securityItem = setList.getChildByText(new UiSelector().className(LinearLayout.class.getName()), "安全", true);
        securityItem.getChild(new UiSelector().text("安全")).click();
        UiScrollable  securityList = new UiScrollable(new UiSelector().resourceId("android:id/list"));
        UiObject btItem = securityList.getChildByText(new UiSelector().className(LinearLayout.class.getName()), "未知来源", true);

        UiObject btSwitch = btItem.getChild(new UiSelector().resourceId("android:id/switchWidget"));

        if(btSwitch.isChecked()==false) {
            btSwitch.click();
            new UiObject(new UiSelector().text("确定")).click();
        }
        sleep(1000);
        new UiObject(new UiSelector().resourceId("android:id/mwCloseBtn")).click();

        //在应用商店下载并安装os monitor，作为测试用例
        uiDevice.executeShellCommand("am start -n org.openthos.appstore/.MainActivity");
        new UiObject(new UiSelector().resourceId("org.openthos.appstore:id/rb_software")).click();
        new UiObject((new UiSelector().resourceId("org.openthos.appstore:id/activity_title_search_text"))).setText("monitor");
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
        install_finish.click();
        sleep(1000);
        if(!new UiObject(new UiSelector().resourceId("android:id/mwCloseBtn")).exists()){
            uiDevice.swipe(0,0,2,2,2);
        }*/
        sleep(10000);
        new UiObject(new UiSelector().resourceId("android:id/mwCloseBtn")).click();

        //打开开始菜单
        //bug2297
        //uiDevice.pressHome();
        openStartMenu();
        sleep(2000);

        //删除常用软件列表的应用，为后续常用软件测试做准备
        while (new RUiObject(new UiSelector().resourceId("com.android.systemui:id/list_view")).exists()) {
            if (new RUiObject(new UiSelector().resourceId("com.android.systemui:id/list_view")).exists()) {
                RUiScrollable functionItems = null;
                functionItems = new RUiScrollable(new UiSelector().resourceId("com.android.systemui:id/list_view"));
                functionItems.getRChildByInstance(new UiSelector().className("android.widget.LinearLayout"), 0).rightclick();
                new RUiObject(new UiSelector().text("从此列表中删除")).click();
            }
            sleep(1000);
        }
    }
    public void test2_Tools() throws UiObjectNotFoundException, IOException {
        UiDevice uiDevice = getUiDevice();

        //搜索测试
        new UiObject(new UiSelector().resourceId("com.android.systemui:id/search")).setText("计算器");
        sleep(1000);

        UiScrollable functionItems = null;
        functionItems = new UiScrollable(new UiSelector().resourceId("com.android.systemui:id/grid_view"));
        UiObject apps = null;
        apps = functionItems.getChildByInstance(new UiSelector().className("android.widget.LinearLayout"), 0);
        String app_Search = apps.getChild(new UiSelector().className("android.widget.TextView")).getText().toString();
        Log.d("AAAAAAAAA",app_Search);
        assertTrue(app_Search.equals("计算器"));

        new UiObject(new UiSelector().resourceId("com.android.systemui:id/search")).setText("");
        sleep(1000);

        //排序测试
        //A-Z 正序
        new UiObject(new UiSelector().resourceId("com.android.systemui:id/arrow_show")).click();
        new UiObject(new UiSelector().text("A-Z")).click();
        functionItems = new UiScrollable(new UiSelector().resourceId("com.android.systemui:id/grid_view"));
        apps = functionItems.getChildByInstance(new UiSelector().className("android.widget.LinearLayout"), 0);
        String app_Seq1 = apps.getChild(new UiSelector().className("android.widget.TextView")).getText().toString();
        //Log.d("AAAAAAAAA",app_Seq1);
        //assertTrue(app_Seq1.equals("920编辑器"));

        //A-Z 逆序
        new UiObject(new UiSelector().resourceId("com.android.systemui:id/sort_type")).click();
        functionItems = new UiScrollable(new UiSelector().resourceId("com.android.systemui:id/grid_view"));
        apps = functionItems.getChildByInstance(new UiSelector().className("android.widget.LinearLayout"), 0);
        String app_Seq2 = apps.getChild(new UiSelector().className("android.widget.TextView")).getText().toString();
        Log.d("AAAAAAAAA",app_Seq2);
        assertTrue(app_Seq2.equals("终端"));

        //使用频率
        new UiObject(new UiSelector().resourceId("com.android.systemui:id/arrow_show")).click();
        new UiObject(new UiSelector().text("使用频率")).click();
        new UiObject(new UiSelector().resourceId("com.android.systemui:id/sort_type")).click();
        //安装时间
        new UiObject(new UiSelector().resourceId("com.android.systemui:id/arrow_show")).click();
        new UiObject(new UiSelector().text("安装时间")).click();
        new UiObject(new UiSelector().resourceId("com.android.systemui:id/sort_type")).click();
        //默认
        new UiObject(new UiSelector().resourceId("com.android.systemui:id/arrow_show")).click();
        new UiObject(new UiSelector().text("默认")).click();
        new UiObject(new UiSelector().resourceId("com.android.systemui:id/sort_type")).click();
    }
    public void test3_AllApp() throws UiObjectNotFoundException, IOException {
        RUiDevice uiDevice = getRUiDevice();

        //单击打开应用
        new RUiObject(new UiSelector().resourceId("com.android.systemui:id/search")).setText("OS Monitor");
        sleep(1000);
        RUiScrollable functionItems = null;
        functionItems = new RUiScrollable(new UiSelector().resourceId("com.android.systemui:id/grid_view"));
        functionItems.getRChildByInstance(new UiSelector().className("android.widget.LinearLayout"), 0).leftclick();
        sleep(2000);
        assertTrue(new RUiObject(new UiSelector().text("进程")).exists());
        new RUiObject(new UiSelector().resourceId("android:id/mwCloseBtn")).click();
        //uiDevice.pressHome();
        openStartMenu();

        //菜单打开应用
        new RUiObject(new UiSelector().resourceId("com.android.systemui:id/search")).setText("OS Monitor");
        sleep(1000);
        functionItems = new RUiScrollable(new UiSelector().resourceId("com.android.systemui:id/grid_view"));
        functionItems.getRChildByInstance(new UiSelector().className("android.widget.LinearLayout"), 0).rightclick();
        new RUiObject(new UiSelector().text("打开")).click();
        sleep(2000);
        assertTrue(new RUiObject(new UiSelector().text("进程")).exists());
        new RUiObject(new UiSelector().resourceId("android:id/mwCloseBtn")).click();
        //uiDevice.pressHome();
        openStartMenu();

        //菜单手机模式
        new RUiObject(new UiSelector().resourceId("com.android.systemui:id/search")).setText("OS Monitor");
        sleep(1000);
        functionItems = new RUiScrollable(new UiSelector().resourceId("com.android.systemui:id/grid_view"));
        functionItems.getRChildByInstance(new UiSelector().className("android.widget.LinearLayout"), 0).rightclick();
        new RUiObject(new UiSelector().text("以手机模式运行")).click();
        sleep(2000);
        assertTrue(new RUiObject(new UiSelector().text("进程")).exists());
        new RUiObject(new UiSelector().resourceId("android:id/mwCloseBtn")).click();
        //uiDevice.pressHome();
        openStartMenu();

        //菜单桌面模式
        new RUiObject(new UiSelector().resourceId("com.android.systemui:id/search")).setText("OS Monitor");
        sleep(1000);
        functionItems = new RUiScrollable(new UiSelector().resourceId("com.android.systemui:id/grid_view"));
        functionItems.getRChildByInstance(new UiSelector().className("android.widget.LinearLayout"), 0).rightclick();
        new RUiObject(new UiSelector().text("以桌面模式运行")).click();
        sleep(2000);
        assertTrue(new RUiObject(new UiSelector().text("进程")).exists());
        new RUiObject(new UiSelector().resourceId("android:id/mwCloseBtn")).click();
        //uiDevice.pressHome();
        openStartMenu();

        //菜单固定到任务栏
        new RUiObject(new UiSelector().resourceId("com.android.systemui:id/search")).setText("OS Monitor");
        sleep(1000);
        functionItems = new RUiScrollable(new UiSelector().resourceId("com.android.systemui:id/grid_view"));
        functionItems.getRChildByInstance(new UiSelector().className("android.widget.LinearLayout"), 0).rightclick();
        new RUiObject(new UiSelector().text("固定到任务栏")).click();
        sleep(2000);

        //菜单解除固定
        functionItems.getRChildByInstance(new UiSelector().className("android.widget.LinearLayout"), 0).rightclick();
        new RUiObject(new UiSelector().text("解除固定")).click();
        sleep(2000);

        //菜单卸载
        functionItems.getRChildByInstance(new UiSelector().className("android.widget.LinearLayout"), 0).rightclick();
        new RUiObject(new UiSelector().text("卸载")).click();
        sleep(2000);
        assertTrue(new RUiObject(new UiSelector().text("强行停止")).exists());
        new RUiObject(new UiSelector().resourceId("android:id/mwCloseBtn")).click();
        //uiDevice.pressHome();
        openStartMenu();
    }
    public void test4_FreqApp() throws UiObjectNotFoundException, IOException {
        //确认常用软件的第一位是否时OS Monitor
        RUiDevice uiDevice = getRUiDevice();
        RUiScrollable functionItems = null;
        functionItems = new RUiScrollable(new UiSelector().resourceId("com.android.systemui:id/list_view"));
        RUiObject apps = null;
        apps = functionItems.getRChildByInstance(new UiSelector().className("android.widget.LinearLayout"), 0);
        String app_Freq = apps.getChild(new UiSelector().className("android.widget.TextView")).getText().toString();
        Log.d("AAAAAAAAA",app_Freq);
        assertTrue(app_Freq.equals("OS Monitor"));

        if (app_Freq.equals("OS Monitor")){
            //单击打开
            RUiObject app_Cal = apps.getChild(new UiSelector().className("android.widget.TextView"));
            app_Cal.leftclick();
            sleep(2000);
            assertTrue(new RUiObject(new UiSelector().text("进程")).exists());
            new RUiObject(new UiSelector().resourceId("android:id/mwCloseBtn")).click();
            //uiDevice.pressHome();
            openStartMenu();

            //菜单打开
            app_Cal.rightclick();
            new RUiObject(new UiSelector().text("打开")).click();
            sleep(2000);
            assertTrue(new RUiObject(new UiSelector().text("进程")).exists());
            new RUiObject(new UiSelector().resourceId("android:id/mwCloseBtn")).click();
            //uiDevice.pressHome();
            openStartMenu();

            //菜单手机模式
            app_Cal.rightclick();
            new RUiObject(new UiSelector().text("以手机模式运行")).click();
            sleep(2000);
            assertTrue(new RUiObject(new UiSelector().text("进程")).exists());
            new RUiObject(new UiSelector().resourceId("android:id/mwCloseBtn")).click();
            //uiDevice.pressHome();
            openStartMenu();

            //菜单桌面模式
            app_Cal.rightclick();
            new RUiObject(new UiSelector().text("以桌面模式运行")).click();
            sleep(2000);
            assertTrue(new RUiObject(new UiSelector().text("进程")).exists());
            new RUiObject(new UiSelector().resourceId("android:id/mwCloseBtn")).click();
            //uiDevice.pressHome();
            openStartMenu();

            //菜单删除
            app_Cal.rightclick();
            new RUiObject(new UiSelector().text("从此列表中删除")).click();

            if (! new RUiObject(new UiSelector().resourceId("com.android.systemui:id/lv_usually_view")).exists()) {
                assertTrue(true);
                Log.d("AAAAAAAAA","常用软件已清空");
            }else {
                functionItems = new RUiScrollable(new UiSelector().resourceId("com.android.systemui:id/lv_usually_view"));
                apps = functionItems.getRChildByInstance(new UiSelector().className("android.widget.LinearLayout"), 0);
                String app_FreqNew = apps.getChild(new UiSelector().className("android.widget.TextView")).getText().toString();
                Log.d("AAAAAAAAA","常用软件列表有其它项: "+ app_FreqNew);
                assertTrue(! app_FreqNew.equals("OS Monitor"));
            }
        }
    }
    public void test5_SysApp() throws UiObjectNotFoundException, IOException {
        //测试文件管理器打开
            UiDevice uiDevice = getUiDevice();
            new UiObject(new UiSelector().resourceId("com.android.systemui:id/file_manager")).click();
            sleep(2000);
            assertTrue(new UiObject(new UiSelector().text("计算机")).exists());
            if(!new UiObject(new UiSelector().resourceId("android:id/mwCloseBtn")).exists()){
                uiDevice.swipe(0,0,100,0,2);
            }
            new UiObject(new UiSelector().resourceId("android:id/mwCloseBtn")).click();
            //uiDevice.pressHome();
        openStartMenu();

            //测试设置打开
            new UiObject(new UiSelector().resourceId("com.android.systemui:id/system_setting")).click();
            sleep(2000);
            assertTrue(new UiObject(new UiSelector().text("蓝牙")).exists());
            new UiObject(new UiSelector().resourceId("android:id/mwCloseBtn")).click();
            //uiDevice.pressHome();
        openStartMenu();

            //测试电源打开
            new UiObject(new UiSelector().resourceId("com.android.systemui:id/power_off")).click();
            sleep(2000);
            assertTrue(new UiObject(new UiSelector().text("睡眠")).exists());
            new UiObject(new UiSelector().resourceId("com.android.powersource:id/power_close")).click();

    }
    public void test6_RemoveApp() throws UiObjectNotFoundException, IOException {
        UiDevice uiDevice = getUiDevice();
        uiDevice.executeShellCommand("am start -n org.openthos.appstore/.MainActivity");

        //卸载app
        new UiObject(new UiSelector().resourceId("org.openthos.appstore:id/rb_manager")).click();
        new UiObject(new UiSelector().text("已安装")).click();
        UiScrollable settingsList = new UiScrollable(new UiSelector().resourceId("org.openthos.appstore:id/customlistView"));
        UiObject btItem = settingsList.getChildByText(new UiSelector().className(LinearLayout.class.getName()), "OS Monitor", true);
        btItem.getChild(new UiSelector().text("卸载")).click();
        UiSelector textStop = new UiSelector().text("强行停止");
        new UiObject(textStop.fromParent(new UiSelector().text("卸载"))).click();
        new UiObject(new UiSelector().text("确定")).click();

        //移除app
        new UiObject(new UiSelector().resourceId("org.openthos.appstore:id/rb_manager")).click();
        new UiObject(new UiSelector().resourceId("org.openthos.appstore:id/download")).click();
        UiScrollable settingsList1 = new UiScrollable(new UiSelector().resourceId("org.openthos.appstore:id/customlistView"));
        UiObject btItem1 = settingsList1.getChildByText(new UiSelector().className(LinearLayout.class.getName()), "OS Monitor", true);
        btItem1.getChild(new UiSelector().text("移除")).click();
        sleep(1000);
        if(!new UiObject(new UiSelector().resourceId("android:id/mwCloseBtn")).exists()){
            uiDevice.swipe(0,0,2,2,2);
        }
        new UiObject(new UiSelector().resourceId("android:id/mwCloseBtn")).click();
    }

    public void test99_test() throws UiObjectNotFoundException{
        RUiDevice uiDevice = getRUiDevice();
        uiDevice.hover(0,0);
        uiDevice.hover(400,0);
    }

    public void openStartMenu() throws UiObjectNotFoundException, IOException {
        RUiDevice uiDevice = getRUiDevice();

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
    }
}
