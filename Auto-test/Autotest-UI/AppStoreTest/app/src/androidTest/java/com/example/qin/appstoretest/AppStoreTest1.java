package com.example.qin.appstoretest;

import android.bluetooth.BluetoothClass;
import android.os.RemoteException;
import android.support.test.uiautomator.UiAutomatorTestCase;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.IOException;

import static android.view.KeyEvent.KEYCODE_MENU;
import static android.view.KeyEvent.KEYCODE_NUMPAD_1;
import static android.view.KeyEvent.KEYCODE_NUMPAD_2;
import static android.view.KeyEvent.KEYCODE_SOFT_RIGHT;

/**
 * Created by qin on 17-6-19.
 */

public class AppStoreTest1 extends UiAutomatorTestCase {
    int max_count = 30; //下载超时，单位（秒）

    String[] appList = {"PowerPoint","Internet 浏览器","影梭"/*,"Word","Excel","OneNote","Outlook","WPS邮箱","WPS Office","IT之家","Flash Master","Quick Picker","OtoVirtualGUI","模拟炒股","QQ","微信","搜狗输入法",
            "OS Monitor","绿色守护","泰捷视频","网易云音乐","央视影音","哔哩哔哩", "VLC","图片管理器"*/};

    String[] appList3 = {"PowerPoint","影梭",/*"Word","Excel","OneNote","Outlook","WPS邮箱","WPS Office","IT之家","Flash Master","快图浏览","OtoVirtualGUI","模拟炒股","QQ","微信","搜狗输入法",
            "OS Monitor","绿色守护","泰捷视频","网易云音乐","央视影音","哔哩哔哩", "VLC","图片管理器","2048",*/"Angry Birds"};

    String[] appList2 = {"WPS邮箱","IT之家","Flash Master","Quick Picker","OtoVirtualGUI","模拟炒股","搜狗输入法","OS Monitor","绿色守护","央视影音","哔哩哔哩","Internet 浏览器","网易云音乐"};

    String[] appList4 = {"Angry Birds","IT之家","OS Monitor"};

    public void test0preparation() throws UiObjectNotFoundException,IOException {
        UiDevice uiDevice =getUiDevice();
        uiDevice.executeShellCommand("am start -n com.android.settings/.Settings");
        UiScrollable  setList = new UiScrollable(new UiSelector().resourceId("com.android.settings:id/dashboard"));
        UiObject securityItem = setList.getChildByText(new UiSelector().className(LinearLayout.class.getName()), "安全", true);
        securityItem.getChild(new UiSelector().text("安全"));
        new UiObject(new UiSelector().text("安全")).click();
        UiScrollable  securityList = new UiScrollable(new UiSelector().resourceId("android:id/list"));
        UiObject btItem = securityList.getChildByText(new UiSelector().className(LinearLayout.class.getName()), "未知来源", true);

        UiObject btSwitch = btItem.getChild(new UiSelector().resourceId("android:id/switchWidget"));

        if(btSwitch.isChecked()==false) {
            btSwitch.click();
            new UiObject(new UiSelector().text("确定")).click();
        }
        sleep(1000);
        new UiObject(new UiSelector().resourceId("android:id/mwCloseBtn")).click();

        uiDevice.executeShellCommand("am start -n org.openthos.appstore/.MainActivity");
        sleep(5000);
        UiObject mwMaximizeBtn = new UiObject(new UiSelector().resourceId("android:id/mwMaximizeBtn"));
        if (mwMaximizeBtn.exists()) {
            new UiObject(new UiSelector().resourceId("android:id/mwMaximizeBtn")).click();
        }

        new UiObject(new UiSelector().resourceId("org.openthos.appstore:id/rb_game")).click();
        new UiObject(new UiSelector().text("游戏").fromParent(new UiSelector().text("所有"))).click();
        new UiObject(new UiSelector().text(appList4[0])).click();
        String buttonText = new UiObject(new UiSelector().resourceId("org.openthos.appstore:id/fragment_detail_download")).getText();
        if(buttonText.equals("安装")){
            removeApp(appList4[0]);
        }
        else if(buttonText.equals("打开")){
            uninstall(appList4[0],"卸载");
            removeApp(appList4[0]);
        }
        for (int i=1;i<appList4.length;i++) {
            new UiObject(new UiSelector().resourceId("org.openthos.appstore:id/rb_software")).click();
            new UiObject(new UiSelector().text("全部").fromParent(new UiSelector().text("所有"))).click();
            new UiObject(new UiSelector().text(appList4[i])).click();
            String buttonText1 = new UiObject(new UiSelector().resourceId("org.openthos.appstore:id/fragment_detail_download")).getText();
            if (buttonText1.equals("安装")) {
                removeApp(appList4[i]);
            } else if (buttonText1.equals("打开")) {
                if(appList4[i]=="Internet 浏览器"){
                    uninstall(appList4[i], "卸载更新");
                }else {
                    uninstall(appList4[i], "卸载");
                }
                removeApp(appList4[i]);
            }
        }
    }

    public void testDemo1() throws UiObjectNotFoundException,IOException {
        UiDevice uiDevice =getUiDevice();

        //暂停下载
        new UiObject(new UiSelector().resourceId("org.openthos.appstore:id/rb_game")).click();
        //UiSelector textAll = new UiSelector().text("全部");
        //new UiObject(textAll.fromParent(new UiSelector().text("所有"))).click();
        UiSelector textApp = new UiSelector().text(appList4[0]);
        new UiObject(textApp.fromParent(new UiSelector().resourceId("org.openthos.appstore:id/app_item_install"))).click();
        new UiObject(new UiSelector().resourceId("org.openthos.appstore:id/rb_manager")).click();
        new UiObject(new UiSelector().resourceId("org.openthos.appstore:id/download")).click();

        UiScrollable settingsList = new UiScrollable(new UiSelector().resourceId("org.openthos.appstore:id/customlistView"));
        UiObject btItem = settingsList.getChildByText(new UiSelector().className(LinearLayout.class.getName()), appList4[0], true);

        btItem.getChild(new UiSelector().text("暂停")).click();

        String state = btItem.getChild(new UiSelector().resourceId("org.openthos.appstore:id/item_download_downloadState")).getText();
        Log.d("AAAAAAAAAAAAAA",state);

        sleep(10000);
        String state1 = btItem.getChild(new UiSelector().resourceId("org.openthos.appstore:id/item_download_downloadState")).getText();
        Log.d("BBBBBBBBBBBBBB",state1);

        assertEquals(state,state1);

        btItem.getChild(new UiSelector().text("继续")).click();
        installInSetting(appList4[0]);
    }

    public void testDemo2() throws UiObjectNotFoundException {
        getUiDevice();
        new UiObject(new UiSelector().resourceId("org.openthos.appstore:id/rb_home")).click();
        UiSelector textApp = new UiSelector().text(appList4[1]);
        UiObject downloadApp = new UiObject(textApp.fromParent(new UiSelector().resourceId("org.openthos.appstore:id/app_item_install")));
        downloadApp.click();

        installInSetting(appList4[1]);

        /*通过点击软件和游戏页面的下载文字下载安装应用*/
        for (int i=2;i<appList4.length;i++) {
            installByText(appList4[i], "org.openthos.appstore:id/rb_software","org.openthos.appstore:id/app_item_install");
        }
    }

    public void testDemo3() throws UiObjectNotFoundException {
        getUiDevice();
        /*通过管理界面的已安装列表卸载应用*/
        for (int i=0;i<appList4.length;i++) {
            uninstall(appList4[i],"卸载");
        }

        //uninstall("Internet 浏览器","卸载更新");

        /*通过下载界面的列表移除应用*/
        for (int i=0;i<appList4.length;i++) {
            removeApp(appList4[i]);
        }
    }

    public void testDemo4() throws UiObjectNotFoundException {
/*通过进入应用详情界面点击按钮下载安装应用*/
        getUiDevice();
        /*for (int i=0;i<appList.length;i++) {
            installByButton(appList[i],"org.openthos.appstore:id/rb_software","下载");
        }*/
        installByButton(appList4[2],"org.openthos.appstore:id/rb_software","下载");
        //installByButton("2048","org.openthos.appstore:id/rb_game","下载");
        //installByButton("Angry Birds","org.openthos.appstore:id/rb_game","下载");
        //installByButton(appList4[appList4.length-1],"org.openthos.appstore:id/rb_software","更新");

        /*通过管理更新页面的更新按钮更新应用*/
        //uninstall(appList4[appList4.length-1],"卸载更新");
        //removeApp(appList4[appList4.length-1]);
        new UiObject(new UiSelector().resourceId("org.openthos.appstore:id/rb_manager")).click();
        new UiObject(new UiSelector().resourceId("org.openthos.appstore:id/update")).click();

        //UiScrollable settingsList = new UiScrollable(new UiSelector().resourceId("org.openthos.appstore:id/customlistView"));
        //UiObject btItem = settingsList.getChildByText(new UiSelector().className(LinearLayout.class.getName()), appList4[appList4.length-1], true);

        //UiObject btSwitch = btItem.getChild(new UiSelector().text("更新"));
        //btSwitch.click();

        //installInSetting(appList4[appList4.length-1]);
    }

    public void testDemo5() throws UiObjectNotFoundException{
        getUiDevice();

        //测试首页栏目
        new UiObject(new UiSelector().resourceId("org.openthos.appstore:id/rb_home")).click();
        new UiObject((new UiSelector().text("最受欢迎")).fromParent(new UiSelector().text("所有"))).click();
        //new UiObject(new UiSelector().text("央视影音")).click();
        //测试返回按钮
        //assertTrue(new UiObject(new UiSelector().text("一款视频软件")).exists());
        getUiDevice().setCompressedLayoutHeirarchy(false);
        //new UiObject(new UiSelector().resourceId("org.openthos.appstore:id/activity_title_back")).click();
        assertFalse(new UiObject(new UiSelector().text("最新推荐")).exists());
        new UiObject(new UiSelector().resourceId("org.openthos.appstore:id/activity_title_back")).click();
        //assertTrue(new UiObject(new UiSelector().text("最新推荐")).exists());
    }

    public void testDemo6() throws UiObjectNotFoundException,IOException {
        /*搜索应用并下载安装*/
        UiDevice uiDevice = getUiDevice();
        new UiObject(new UiSelector().resourceId("org.openthos.appstore:id/rb_software")).click();

        UiObject search= new UiObject((new UiSelector().resourceId("org.openthos.appstore:id/activity_title_search_text")));
        search.setText("monitor");
        uiDevice.pressEnter();
        uiDevice.pressEnter();

        UiObject open = new UiObject(new UiSelector().resourceId("org.openthos.appstore:id/app_item_install"));
        open.clickAndWaitForNewWindow(10000);
        assertTrue(new UiObject(new UiSelector().text("进程")).exists());
        uiDevice.executeShellCommand("am force-stop com.eolwral.osmonitor");

        UiObject label_manage = new UiObject(new UiSelector().resourceId("org.openthos.appstore:id/rb_manager"));
        label_manage.click();

        //uninstall(appList4[appList4.length-1],"卸载更新");
        //removeApp(appList4[appList4.length-1]);
        uninstall(appList4[2],"卸载");
        removeApp(appList4[2]);


        uiDevice.executeShellCommand("am force-stop org.openthos.appstore");
    }

    public void installByText(String appName,String labelName,String option) throws UiObjectNotFoundException {
        UiObject label_name = new UiObject(new UiSelector().resourceId(labelName));
        label_name.click();

        UiSelector textAll;
        if(labelName=="org.openthos.appstore:id/rb_game"){
            textAll = new UiSelector().text("益智游戏");
        }else {
            textAll = new UiSelector().text("全部");
        }
        UiObject allSoftware = new UiObject(textAll.fromParent(new UiSelector().text("所有")));
        allSoftware.click();
        UiSelector textApp = new UiSelector().text(appName);
        UiObject downloadApp = new UiObject(textApp.fromParent(new UiSelector().resourceId(option)));
        downloadApp.click();

        installInSetting(appName);
    }

    public void installByButton(String appName,String labelName,String option) throws UiObjectNotFoundException {
        UiObject label_name = new UiObject(new UiSelector().resourceId(labelName));
        label_name.click();

        UiSelector textAll;
        if(labelName=="org.openthos.appstore:id/rb_game"){
            textAll = new UiSelector().text("益智游戏");
        }else {
            textAll = new UiSelector().text("全部");
        }
        UiObject allSoftware = new UiObject(textAll.fromParent(new UiSelector().text("所有")));
        allSoftware.click();

        UiObject textApp = new UiObject(new UiSelector().text(appName));
        textApp.click();

        UiObject appDownloadButton = new UiObject(new UiSelector().text(option));
        appDownloadButton.click();

        installInSetting(appName);
    }

    public void installInSetting(String appname) throws UiObjectNotFoundException {
        /*if (appname!="快图浏览" && appname!="Quick Picker" && appname != "OtoVirtualGUI" && appname != "招商智远Pad" && appname !="影梭" && appname !="2048"&& appname !="Flash Master") {
            UiObject label_manager = new UiObject(new UiSelector().resourceId("org.openthos.appstore:id/rb_manager"));
            label_manager.click();
            UiObject label_manager_download = new UiObject(new UiSelector().resourceId("org.openthos.appstore:id/download"));
            label_manager_download.click();
        }*/
        UiObject install_install = new UiObject(new UiSelector().resourceId("com.android.packageinstaller:id/ok_button"));

        int count=0;
        while (!install_install.exists()) {
            if (!install_install.exists()) {
                sleep(2000);
                count++;
            }
            if(count>max_count){
                assertTrue(false);
            }
        }
        count=0;
        while (install_install.exists()) {
                if (install_install.exists()) {
                    install_install.click();
                }
            if(count>max_count){
                assertTrue(false);
            }
        }
        count=0;
        UiObject install_finish = new UiObject(new UiSelector().resourceId("com.android.packageinstaller:id/done_button"));
        while (!install_finish.exists() ) {
            if (!install_finish.exists()) {
                sleep(1000);
            }
            if(count>max_count){
                assertTrue(false);
            }
        }
        install_finish.click();
    }

    public void uninstall(String appName,String option) throws UiObjectNotFoundException {
        if(appName.equals("Quick Picker")){
            appName="快图浏览";
        }
        UiObject label_name = new UiObject(new UiSelector().resourceId("org.openthos.appstore:id/rb_manager"));
        label_name.click();

        UiObject label_name2 = new UiObject(new UiSelector().text("已安装"));
        label_name2.click();


        UiScrollable settingsList = new UiScrollable(new UiSelector().resourceId("org.openthos.appstore:id/customlistView"));
        UiObject btItem = settingsList.getChildByText(new UiSelector().className(LinearLayout.class.getName()), appName, true);

            UiObject btSwitch = btItem.getChild(new UiSelector().text("卸载"));
            btSwitch.click();

        if (option == "卸载更新"){
            UiObject uninstallButton = new UiObject(new UiSelector().text("卸载更新"));
            uninstallButton.click();
            sleep(1000);
            UiDevice.getInstance().pressEnter();
            UiDevice.getInstance().pressDPadRight();
            UiDevice.getInstance().pressEnter();
            sleep(2000);
            UiDevice.getInstance().pressDPadRight();
            UiDevice.getInstance().pressEnter();
            sleep(2000);
            UiObject setting_close = new UiObject(new UiSelector().resourceId("android:id/mwCloseBtn"));
            setting_close.click();
        }else {
            UiSelector textStop = new UiSelector().text("强行停止");
            UiObject uninstallButton = new UiObject(textStop.fromParent(new UiSelector().text("卸载")));
            uninstallButton.click();

            UiObject setting_ok = new UiObject(new UiSelector().text("确定"));
            setting_ok.click();
        }
    }

    public void removeApp(String appName) throws UiObjectNotFoundException {
        UiObject label_name = new UiObject(new UiSelector().resourceId("org.openthos.appstore:id/rb_manager"));
        label_name.click();

        UiObject label_name2 = new UiObject(new UiSelector().resourceId("org.openthos.appstore:id/download"));
        label_name2.click();

        UiScrollable settingsList = new UiScrollable(new UiSelector().resourceId("org.openthos.appstore:id/customlistView"));
        UiObject btItem = settingsList.getChildByText(new UiSelector().className(LinearLayout.class.getName()), appName, true);
        UiObject btSwitch = btItem.getChild(new UiSelector().text("移除"));
        btSwitch.click();
    }
}
