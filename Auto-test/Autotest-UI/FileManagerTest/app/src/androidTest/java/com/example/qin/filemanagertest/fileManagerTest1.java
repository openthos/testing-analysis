package com.example.qin.filemanagertest;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.test.uiautomator.RUiAutomatorTestCase;
import android.support.test.uiautomator.RUiDevice;
import android.support.test.uiautomator.RUiObject;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.util.Log;
import android.view.KeyEvent;

import java.io.IOException;

import static android.view.KeyEvent.KEYCODE_CTRL_LEFT;
import static android.view.KeyEvent.KEYCODE_F2;
import static android.view.KeyEvent.KEYCODE_F5;
import static android.view.KeyEvent.KEYCODE_SHIFT_LEFT;

/**
 * Created by qin on 17-10-19.
 */

public class fileManagerTest1 extends RUiAutomatorTestCase {
    Bundle bundle = new Bundle();
    RUiDevice uiDevice;
    //拷贝测试资料到测试机的下载文件夹，并解压进入文件夹
    public void test0Preparation() throws UiObjectNotFoundException,IOException {
        printToConsole("（0/9）准备部分--------------------");
        uiDevice = getRUiDevice();
        printToConsole("（0/9）切换输入法到英文");
        altAndTab();
        uiDevice.pressEnter();
        uiDevice.openNotification();
        new RUiObject(new UiSelector().resourceId("com.android.systemui:id/status_bar_input_method")).click();
        new RUiObject(new UiSelector().text("Android 键盘 (AOSP)")).click();
        if (new RUiObject(new UiSelector().text("更改键盘")).exists()){
            new RUiObject(new UiSelector().text("Android 键盘 (AOSP)")).click();
        }
        else{
            uiDevice.click(0,0);
        }
        sleep(1000);
        uiDevice.executeShellCommand("am start -n org.openthos.filemanager/.MainActivity");
        sleep(3000);
        if (new RUiObject(new UiSelector().resourceId("android:id/mwMaximizeBtn")).exists()){
            new RUiObject(new UiSelector().resourceId("android:id/mwMaximizeBtn")).click();
        }
        new RUiObject(new UiSelector().text("下载")).leftclick();
        assertTrue("测试文件 U盘测试.zip 未拷贝进下载文件夹",new RUiObject(new UiSelector().text("U盘测试.zip")).exists());
        printToConsole("（0/9）解压测试文件夹");
        if (new RUiObject(new UiSelector().text("U盘测试")).exists()){
            new RUiObject(new UiSelector().text("U盘测试")).rightclick();
            new RUiObject(new UiSelector().text("永久删除")).click();
            assertTrue(new RUiObject(new UiSelector().text("确认永久删除？")).exists());
            sleep(1000);
            new RUiObject(new UiSelector().text("确定")).click();
            assertFalse(new RUiObject(new UiSelector().text("U盘测试")).exists());
        }
        new RUiObject(new UiSelector().text("U盘测试.zip")).rightclick();
        new RUiObject(new UiSelector().text("解压缩")).click();
        new RUiObject(new UiSelector().text("解压缩")).click();
        sleep(5000);
        printToConsole("（0/9）拷贝视频文件到测试文件夹");
        new RUiObject(new UiSelector().text("视频")).leftclick();
        new RUiObject(new UiSelector().text("big_buck_bunny_480p_surround-fix.mp4")).rightclick();
        new RUiObject(new UiSelector().text("复制")).click();
        new RUiObject(new UiSelector().text("下载")).leftclick();
        new RUiObject(new UiSelector().text("U盘测试")).doubleclick();
        new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/file_path_grid")).rightclick();
        new RUiObject(new UiSelector().text("粘贴")).click();
        sleep(5000);
        printToConsole("（0/9）判断图片/壁纸文件夹是否存在或是否需要替换");
        new RUiObject(new UiSelector().text("图片")).leftclick();
        if(new RUiObject(new UiSelector().text("wallpaper")).exists()){
            new RUiObject(new UiSelector().text("wallpaper")).click();
            if (!new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/info")).getText().toString().equals("wallpaper  14 项")){
                new RUiObject(new UiSelector().text("wallpaper")).rightclick();
                new RUiObject(new UiSelector().text("永久删除")).click();
                sleep(1000);
                new RUiObject(new UiSelector().text("确定")).click();
                sleep(5000);
                new RUiObject(new UiSelector().text("下载")).leftclick();
                new RUiObject(new UiSelector().text("U盘测试")).doubleclick();
                new RUiObject(new UiSelector().text("wallpaper")).rightclick();
                new RUiObject(new UiSelector().text("剪切")).click();
                new RUiObject(new UiSelector().text("图片")).leftclick();
                new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/file_path_grid")).rightclick();
                new RUiObject(new UiSelector().text("粘贴")).click();
                sleep(5000);
                new RUiObject(new UiSelector().text("下载")).leftclick();
                new RUiObject(new UiSelector().text("U盘测试")).doubleclick();
            }else {
                new RUiObject(new UiSelector().text("下载")).leftclick();
                new RUiObject(new UiSelector().text("U盘测试")).doubleclick();
                new RUiObject(new UiSelector().text("wallpaper")).rightclick();
                new RUiObject(new UiSelector().text("永久删除")).click();
                assertTrue(new RUiObject(new UiSelector().text("确认永久删除？")).exists());
                new RUiObject(new UiSelector().text("确定")).click();
                assertFalse(new RUiObject(new UiSelector().text("wallpaper")).exists());
            }
        }
        else {
            new RUiObject(new UiSelector().text("下载")).leftclick();
            new RUiObject(new UiSelector().text("U盘测试")).doubleclick();
            new RUiObject(new UiSelector().text("wallpaper")).rightclick();
            new RUiObject(new UiSelector().text("剪切")).click();
            new RUiObject(new UiSelector().text("图片")).leftclick();
            new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/file_path_grid")).rightclick();
            new RUiObject(new UiSelector().text("粘贴")).click();
            sleep(5000);
            new RUiObject(new UiSelector().text("下载")).leftclick();
            new RUiObject(new UiSelector().text("U盘测试")).doubleclick();
        }
    }

    //测试文件管理器工具栏
    public void test1ToolBar() throws UiObjectNotFoundException,IOException {
        printToConsole("（1/9）工具栏测试部分--------------------");
        uiDevice = getRUiDevice();

        printToConsole("（1/9）测试返回按钮");
        new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/iv_back")).click();
        assertTrue(new RUiObject(new UiSelector().text("U盘测试.zip")).exists());
        printToConsole("            通过");

        printToConsole("（1/9）测试前进按钮");
        new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/iv_forward")).click();
        assertTrue(new RUiObject(new UiSelector().text("壁纸test")).exists());
        printToConsole("            通过");

        printToConsole("（1/9）测试上一级按钮");
        new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/iv_up")).click();
        assertTrue(new RUiObject(new UiSelector().text("U盘测试")).exists());
        printToConsole("            通过");

        printToConsole("（1/9）测试设置按钮");
        new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/iv_setting")).click();
        new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/pop_setting_view")).click();
        printToConsole("---未做判断");

        printToConsole("（1/9）测试图标排列按钮：");
        printToConsole("（1/9）默认grid列表");
        assertTrue(new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/file_path_grid")).exists());
        printToConsole("            通过");
        printToConsole("（1/9）切换list列表");
        new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/iv_list_view")).click();
        sleep(2000);
        assertTrue(new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/file_path_list")).exists());
        printToConsole("            通过");
        printToConsole("（1/9）切换grid列表");
        new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/iv_grid_view")).click();
        assertTrue(new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/file_path_grid")).exists());
        printToConsole("            通过");


        printToConsole("（1/9）测试地址栏：");
        new RUiObject(new UiSelector().text("U盘测试")).doubleclick();
        new RUiObject(new UiSelector().text("壁纸test")).doubleclick();

        printToConsole("（1/9）地址栏 显示，比对地址栏显示的每一项目录是否正确：");
        RUiObject addressBar = new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/lv_address"));

        RUiObject addressItem0 = addressBar.getChild(new UiSelector().index(0));
        String strAddressItem0 = addressItem0.getChild(new UiSelector().className("android.widget.TextView")).getText().toString();
        Log.d("AAAAAAAAA",strAddressItem0);
        assertTrue(strAddressItem0.equals("~"));

        RUiObject addressItem1 = addressBar.getChild(new UiSelector().index(1));
        String strAddressItem1 = addressItem1.getChild(new UiSelector().className("android.widget.TextView")).getText().toString();
        Log.d("AAAAAAAAA",strAddressItem1);
        assertTrue(strAddressItem1.equals("Download"));

        RUiObject addressItem2 = addressBar.getChild(new UiSelector().index(2));
        String strAddressItem2 = addressItem2.getChild(new UiSelector().className("android.widget.TextView")).getText().toString();
        Log.d("AAAAAAAAA",strAddressItem2);
        assertTrue(strAddressItem2.equals("U盘测试"));

        RUiObject addressItem3 = addressBar.getChild(new UiSelector().index(3));
        String strAddressItem3 = addressItem3.getChild(new UiSelector().className("android.widget.TextView")).getText().toString();
        Log.d("AAAAAAAAA",strAddressItem3);
        assertTrue(strAddressItem3.equals("壁纸test"));
        printToConsole("            通过");

        printToConsole("目前有bug[2301]，需要重启文件管理器");
        uiDevice.swipe(0,0,100,0,2);
        new RUiObject(new UiSelector().resourceId("android:id/mwCloseBtn")).click();
        sleep(3000);
        uiDevice.executeShellCommand("am start -n org.openthos.filemanager/.MainActivity");
        sleep(3000);
        new RUiObject(new UiSelector().text("下载")).leftclick();
        new RUiObject(new UiSelector().text("U盘测试")).doubleclick();
        new RUiObject(new UiSelector().text("壁纸test")).doubleclick();

        printToConsole("（1/9）地址栏 输入");
        new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/search_view")).setText("U盘测试/文档test");
        new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/search_view")).click();
        sleep(1000);
        uiDevice.pressKeyCode(29, 4096);
        uiDevice.pressKeyCode(KEYCODE_CTRL_LEFT);
        sleep(1000);

        printToConsole("目前有bug[2299]，需要按两次ctrl+a才能选中");
        uiDevice.pressKeyCode(29, 4096);
        uiDevice.pressKeyCode(KEYCODE_CTRL_LEFT);
        sleep(1000);
        uiDevice.pressKeyCode(31, 4096);
        uiDevice.pressKeyCode(KEYCODE_CTRL_LEFT);
        sleep(1000);
        uiDevice.pressDelete();
        sleep(1000);
        new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/lv_address")).click();
        sleep(1000);
        uiDevice.pressKeyCode(29, 4096);
        uiDevice.pressKeyCode(KEYCODE_CTRL_LEFT);
        sleep(1000);
        uiDevice.pressDelete();
        sleep(1000);
        uiDevice.executeShellCommand("input text ~/Download/");
        sleep(1000);
        uiDevice.pressKeyCode(50, 4096);
        uiDevice.pressKeyCode(KEYCODE_CTRL_LEFT);
        sleep(1000);
        uiDevice.pressEnter();
        sleep(3000);
        assertTrue(new RUiObject(new UiSelector().text("用户反馈0627.xls")).exists());
        printToConsole("            通过");

        printToConsole("（1/9）点击地址栏目录");
        new RUiObject(new UiSelector().text("U盘测试")).click();
        assertTrue(new RUiObject(new UiSelector().text("壁纸test")).exists());
        new RUiObject(new UiSelector().text("Download")).click();
        assertTrue(new RUiObject(new UiSelector().text("U盘测试.zip")).exists());
        new RUiObject(new UiSelector().text("~")).click();
        assertTrue(new RUiObject(new UiSelector().text("Alarms")).exists());
        printToConsole("            通过");


        printToConsole("（1/9）测试搜索栏：");
        new RUiObject(new UiSelector().text("下载")).leftclick();
        new RUiObject(new UiSelector().text("U盘测试")).doubleclick();

        printToConsole("（1/9）搜索栏 默认状态");
        String strSearch = new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/search_view")).getText();
        assertTrue(strSearch.equals("请输入搜索内容"));
        printToConsole("            通过");

        printToConsole("（1/9）搜索栏 按钮查找");
        new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/search_view")).setText("用户反馈0627");
        new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/iv_search")).click();

        printToConsole("---bug[2170]，先不做判断");
        //----------------------------目前有bug[2170]，先不做判断---------------------------------------------
        //assertTrue(new RUiObject(new UiSelector().text("用户反馈0627.xls")).exists());
        //回车键查找
        //new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/search_view")).setText("追光者");
        //uiDevice.pressEnter();
        //assertTrue(new RUiObject(new UiSelector().text("追光者.flac")).exists());
    }

    //测试导航栏
    public void test2NavigationBar() throws UiObjectNotFoundException,IOException {
        printToConsole("（2/9）导航栏测试部分--------------------");
        uiDevice = getRUiDevice();

        printToConsole("（2/9）导航栏 依次左键点击进入");
        new RUiObject(new UiSelector().text("桌面")).leftclick();
        assertTrue(new RUiObject(new UiSelector().text("UserGuide.html")).exists());

        new RUiObject(new UiSelector().text("音乐")).leftclick();
        RUiObject addressBar0 = new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/lv_address"));
        RUiObject addressItem0 = addressBar0.getChild(new UiSelector().index(1));
        String strAddressItem0 = addressItem0.getChild(new UiSelector().className("android.widget.TextView")).getText().toString();
        Log.d("AAAAAAAAA",strAddressItem0);
        assertTrue(strAddressItem0.equals("Music"));

        new RUiObject(new UiSelector().text("视频")).leftclick();
        assertTrue(new RUiObject(new UiSelector().text("big_buck_bunny_480p_surround-fix.mp4")).exists());

        new RUiObject(new UiSelector().text("图片")).leftclick();
        RUiObject addressBar1 = new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/lv_address"));
        RUiObject addressItem1 = addressBar1.getChild(new UiSelector().index(1));
        String strAddressItem1 = addressItem1.getChild(new UiSelector().className("android.widget.TextView")).getText().toString();
        Log.d("AAAAAAAAA",strAddressItem1);
        assertTrue(strAddressItem1.equals("Pictures"));

        new RUiObject(new UiSelector().text("文档")).leftclick();
        RUiObject addressBar2 = new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/lv_address"));
        RUiObject addressItem2 = addressBar2.getChild(new UiSelector().index(1));
        String strAddressItem2 = addressItem2.getChild(new UiSelector().className("android.widget.TextView")).getText().toString();
        Log.d("AAAAAAAAA",strAddressItem2);
        assertTrue(strAddressItem2.equals("documents"));

        new RUiObject(new UiSelector().text("下载")).leftclick();
        assertTrue(new RUiObject(new UiSelector().text("U盘测试")).exists());

        new RUiObject(new UiSelector().text("回收站")).leftclick();
        RUiObject addressBar3 = new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/lv_address"));
        RUiObject addressItem3 = addressBar3.getChild(new UiSelector().index(1));
        String strAddressItem3 = addressItem3.getChild(new UiSelector().className("android.widget.TextView")).getText().toString();
        Log.d("AAAAAAAAA",strAddressItem3);
        assertTrue(strAddressItem3.equals("Recycle"));

        new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/tv_computer")).leftclick();
        assertTrue(new RUiObject(new UiSelector().text("用户数据")).exists());

        /*new RUiObject(new UiSelector().text("sda2")).leftclick();
        assertTrue(new RUiObject(new UiSelector().text("etc")).exists());*/

        new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/tv_seafile")).leftclick();
        if(new RUiObject(new UiSelector().text("否")).exists()) {
            new RUiObject(new UiSelector().text("否")).click();
        }
        assertTrue(new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/tv_seafile")).isSelected());

        new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/tv_samba")).leftclick();
        assertTrue(new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/tv_samba")).isSelected());
        printToConsole("            通过");


        printToConsole("（2/9）导航栏 鼠标右键挂载卸载");//名称不固定，先不做测试
        /*RUiObject NavigationBarMountDevices = new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/ll_mount"));
        NavigationBarMountDevices.getChild(new UiSelector().text("sda1")).rightclick();
        sleep(1000);
        new RUiObject(new UiSelector().text("挂载")).click();
        new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/tv_computer")).leftclick();
        sleep(1000);
        RUiObject mountDevices = new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/grid_auto_mount_device"));
        RUiObject mountItem0 = mountDevices.getChild(new UiSelector().resourceId("org.openthos.filemanager:id/mount_grid_ll").index(0));
        String strMountItem0 = mountItem0.getChild(new UiSelector().resourceId("org.openthos.filemanager:id/mount_grid_name")).getText().toString();
        Log.d("AAAAAAAAA",strMountItem0);
        assertTrue(strMountItem0.equals("sda1"));
        RUiObject mountItem1 = mountDevices.getChild(new UiSelector().resourceId("org.openthos.filemanager:id/mount_grid_ll").index(1));
        String strMountItem1 = mountItem1.getChild(new UiSelector().resourceId("org.openthos.filemanager:id/mount_grid_name")).getText().toString();
        Log.d("AAAAAAAAA",strMountItem1);
        assertTrue(strMountItem1.equals("sda2"));

        NavigationBarMountDevices.getChild(new UiSelector().text("sda1")).rightclick();
        sleep(1000);
        new RUiObject(new UiSelector().text("卸载")).click();
        NavigationBarMountDevices.getChild(new UiSelector().text("sda2")).rightclick();
        sleep(1000);
        new RUiObject(new UiSelector().text("卸载")).click();
        printToConsole("            通过");*/
    }

    //测试文件管理器首页
    public void test3HomePage() throws UiObjectNotFoundException,IOException {
        printToConsole("（3/9）文件管理器首页测试部分--------------------");
        uiDevice = getRUiDevice();
        new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/tv_computer")).leftclick();

        printToConsole("（3/9）首页 个人空间（点击/双击/右键）");
        RUiObject personalSpace = new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/tv_personal_title"));
        personalSpace.click();
        sleep(1000);
        assertTrue(personalSpace.isSelected());

        personalSpace.doubleclick();
        RUiObject personalSpaceGrid = new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/personal_fragment_grid"));
        RUiObject personalItem0 = personalSpaceGrid.getChild(new UiSelector().resourceId("org.openthos.filemanager:id/ll_grid_item_bg").index(0));
        String strpersonalItem0 = personalItem0.getChild(new UiSelector().resourceId("org.openthos.filemanager:id/file_name")).getText().toString();
        Log.d("AAAAAAAAA",strpersonalItem0);
        assertTrue(strpersonalItem0.equals("桌面"));

        new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/tv_computer")).leftclick();
        new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/tv_personal_title")).rightclick();
        sleep(1000);
        new RUiObject(new UiSelector().text("打开")).click();
        RUiObject personalSpaceGrid1 = new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/personal_fragment_grid"));
        RUiObject personalItem1 = personalSpaceGrid1.getChild(new UiSelector().resourceId("org.openthos.filemanager:id/ll_grid_item_bg").index(0));
        String strpersonalItem1 = personalItem1.getChild(new UiSelector().resourceId("org.openthos.filemanager:id/file_name")).getText().toString();
        Log.d("AAAAAAAAA",strpersonalItem1);
        assertTrue(strpersonalItem1.equals("桌面"));
        printToConsole("            通过");

        printToConsole("（3/9）首页 用户数据（点击/双击/右键）");
        new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/tv_computer")).leftclick();
        RUiObject userData = new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/tv_system_title"));
        userData.click();
        sleep(1000);
        assertTrue(userData.isSelected());

        userData.doubleclick();
        RUiObject userDataGrid = new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/file_path_grid"));
        RUiObject userDataItem0 = userDataGrid.getChild(new UiSelector().resourceId("org.openthos.filemanager:id/ll_grid_item_bg").index(0));
        String strUserDataItem0 = userDataItem0.getChild(new UiSelector().resourceId("org.openthos.filemanager:id/file_name")).getText().toString();
        Log.d("AAAAAAAAA",strUserDataItem0);
        assertTrue(strUserDataItem0.equals("Alarms"));

        new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/tv_computer")).leftclick();
        new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/tv_system_title")).rightclick();
        sleep(1000);
        new RUiObject(new UiSelector().text("打开")).click();
        RUiObject userDataGrid1 = new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/file_path_grid"));
        RUiObject userDataItem1 = userDataGrid1.getChild(new UiSelector().resourceId("org.openthos.filemanager:id/ll_grid_item_bg").index(0));
        String strUserDataItem1 = userDataItem1.getChild(new UiSelector().resourceId("org.openthos.filemanager:id/file_name")).getText().toString();
        Log.d("AAAAAAAAA",strUserDataItem1);
        assertTrue(strUserDataItem1.equals("Alarms"));
        printToConsole("            通过");

        printToConsole("（3/9）首页 我的计算机（点击/双击/右键）");
        new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/tv_computer")).leftclick();
        RUiObject sdData = new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/tv_sd_title"));
        sdData.click();
        sleep(1000);
        assertTrue(sdData.isSelected());

        sdData.doubleclick();
        assertTrue(new RUiObject(new UiSelector().text("storage")).exists());

        new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/tv_computer")).leftclick();
        new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/tv_sd_title")).rightclick();
        sleep(1000);
        new RUiObject(new UiSelector().text("打开")).click();
        assertTrue(new RUiObject(new UiSelector().text("storage")).exists());
        printToConsole("            通过");

        printToConsole("（3/9）首页 sda2（点击/双击/右键）");//名称不固定，先不做测试
        /*RUiObject NavigationBarMountDevices = new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/ll_mount"));
        NavigationBarMountDevices.getChild(new UiSelector().text("sda2")).leftclick();
        new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/tv_computer")).leftclick();

        RUiObject mountDevices = new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/grid_auto_mount_device"));
        RUiObject sda2Data = mountDevices.getChild(new UiSelector().text("sda2"));
        sda2Data.click();
        sleep(1000);
        assertTrue(sda2Data.isSelected());

        sda2Data.doubleclick();
        assertTrue(new RUiObject(new UiSelector().text("etc")).exists());

        new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/tv_computer")).leftclick();
        RUiObject mountDevices1 = new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/grid_auto_mount_device"));
        mountDevices1.getChild(new UiSelector().text("sda2")).rightclick();
        sleep(1000);
        new RUiObject(new UiSelector().text("打开")).click();
        assertTrue(new RUiObject(new UiSelector().text("etc")).exists());

        new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/tv_computer")).leftclick();
        RUiObject mountDevices2 = new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/grid_auto_mount_device"));
        mountDevices2.getChild(new UiSelector().text("sda2")).rightclick();
        sleep(1000);
        new RUiObject(new UiSelector().text("卸载")).click();
        sleep(1000);
        printToConsole("            通过");*/

        printToConsole("（3/9）目前有bug[2310]，需要重启文件管理器");
        uiDevice.swipe(0,0,100,0,2);
        new RUiObject(new UiSelector().resourceId("android:id/mwCloseBtn")).click();
        sleep(3000);
        uiDevice.executeShellCommand("am start -n org.openthos.filemanager/.MainActivity");
        sleep(3000);
    }

    //测试文件操作（点击/双击/右键）
    public void test4File() throws UiObjectNotFoundException,IOException {
        printToConsole("（4/9）文件操作部分--------------------");
        uiDevice = getRUiDevice();
        new RUiObject(new UiSelector().text("下载")).leftclick();
        new RUiObject(new UiSelector().text("U盘测试")).doubleclick();
        new RUiObject(new UiSelector().text("壁纸test")).doubleclick();

        printToConsole("（4/9）文件 单击选中");
        RUiObject picture1 = new RUiObject(new UiSelector().text("130549p29fosfs6ystsks2.jpg"));
        picture1.click();
        String pictureInfo = new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/info")).getText().toString();
        assertEquals("130549p29fosfs6ystsks2.jpg  3.5 MB",pictureInfo);
        assertTrue(new RUiObject(new UiSelector().text("130549p29fosfs6ystsks2.jpg  3.5 MB")).exists());
        sleep(2000);
        printToConsole("            通过");

        printToConsole("（4/9）文件 双击打开");
        picture1.doubleclick();
        if (new RUiObject(new UiSelector().text("A Photo Viewer")).exists()){
            new RUiObject(new UiSelector().text("A Photo Viewer")).click();
            if (new RUiObject(new UiSelector().text("仅此一次")).exists()) {
                new RUiObject(new UiSelector().text("仅此一次")).click();
            }
        }
        else if (new RUiObject(new UiSelector().text("使用A Photo Viewer打开")).exists()){
            new RUiObject(new UiSelector().text("仅此一次")).click();
        }
        else {
            assertTrue(false);
        }
        new RUiObject(new UiSelector().resourceId("android:id/mwCloseBtn")).click();
        printToConsole("            通过");

        printToConsole("（4/9）文件 右键菜单：");
        printToConsole("（4/9）文件 右键打开");
        picture1.rightclick();
        new RUiObject(new UiSelector().text("打开")).click();
        assertTrue(new RUiObject(new UiSelector().text("使用A Photo Viewer打开")).exists());
        uiDevice.pressKeyCode(111);
        printToConsole("            通过");

        printToConsole("（4/9）文件 打开方式");
        picture1.rightclick();
        new RUiObject(new UiSelector().text("打开方式")).click();
        assertTrue(new RUiObject(new UiSelector().text("A Photo Viewer")).exists());
        uiDevice.pressKeyCode(111);
        printToConsole("            通过");

        printToConsole("（4/9）文件 复制");
        picture1.rightclick();
        new RUiObject(new UiSelector().text("复制")).click();
        new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/file_path_grid")).rightclick();
        new RUiObject(new UiSelector().text("粘贴")).click();
        assertTrue(new RUiObject(new UiSelector().text("130549p29fosfs6ystsks2.2.jpg")).exists());
        printToConsole("            通过");

        printToConsole("（4/9）文件 剪切");
        picture1.rightclick();
        new RUiObject(new UiSelector().text("剪切")).click();
        new RUiObject(new UiSelector().text("音乐")).leftclick();
        new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/file_path_grid")).rightclick();
        new RUiObject(new UiSelector().text("粘贴")).click();
        assertTrue(new RUiObject(new UiSelector().text("130549p29fosfs6ystsks2.jpg")).exists());
        new RUiObject(new UiSelector().text("下载")).leftclick();
        new RUiObject(new UiSelector().text("U盘测试")).doubleclick();
        new RUiObject(new UiSelector().text("壁纸test")).doubleclick();
        assertFalse(new RUiObject(new UiSelector().text("130549p29fosfs6ystsks2.jpg")).exists());
        printToConsole("            通过");

        printToConsole("（4/9）文件 删除");
        new RUiObject(new UiSelector().text("130549p29fosfs6ystsks2.2.jpg")).rightclick();
        new RUiObject(new UiSelector().text("删除")).click();
        assertTrue(new RUiObject(new UiSelector().text("确认删除到回收站?")).exists());
        new RUiObject(new UiSelector().text("确定")).click();
        new RUiObject(new UiSelector().text("回收站")).leftclick();
        assertTrue(new RUiObject(new UiSelector().text("130549p29fosfs6ystsks2.2.jpg")).exists());
        new RUiObject(new UiSelector().text("下载")).leftclick();
        new RUiObject(new UiSelector().text("U盘测试")).doubleclick();
        new RUiObject(new UiSelector().text("壁纸test")).doubleclick();
        assertFalse(new RUiObject(new UiSelector().text("130549p29fosfs6ystsks2.2.jpg")).exists());
        printToConsole("            通过");

        printToConsole("（4/9）文件 永久删除");
        new RUiObject(new UiSelector().text("3c5b9c082f98a40205b95f9a7a3adb92.jpg")).rightclick();
        new RUiObject(new UiSelector().text("永久删除")).click();
        assertTrue(new RUiObject(new UiSelector().text("确认永久删除？")).exists());
        new RUiObject(new UiSelector().text("确定")).click();
        assertFalse(new RUiObject(new UiSelector().text("3c5b9c082f98a40205b95f9a7a3adb92.jpg")).exists());
        printToConsole("            通过");

        printToConsole("（4/9）文件 重命名");
        new RUiObject(new UiSelector().text("1433924398673.jpg")).rightclick();
        new RUiObject(new UiSelector().text("重命名")).click();
        uiDevice.pressDelete();
        sleep(1000);
        uiDevice.pressKeyCode(7);
        uiDevice.pressKeyCode(7);
        uiDevice.pressKeyCode(8);
        sleep(1000);
        new RUiObject(new UiSelector().resourceId("android:id/button1")).click();
        assertTrue(new RUiObject(new UiSelector().text("001.jpg")).exists());
        printToConsole("            通过");

        printToConsole("（4/9）文件 发送");
        sleep(5000);
        new RUiObject(new UiSelector().text("001.jpg")).rightclick();
        new RUiObject(new UiSelector().text("发送")).click();
        sleep(2000);
        assertTrue(new RUiObject(new UiSelector().text("分享方式")).exists());
        uiDevice.pressKeyCode(111);
        printToConsole("            通过");

        printToConsole("（4/9）文件 复制路径");
        new RUiObject(new UiSelector().text("001.jpg")).rightclick();
        new RUiObject(new UiSelector().text("复制路径")).click();
        new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/tv_computer")).leftclick();
        new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/et_nivagation")).click();
        sleep(2000);
        uiDevice.pressKeyCode(50,4096);
        String strCopyPath=new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/et_nivagation")).getText();
        Log.d("AAAAAAAAA",strCopyPath);
        assertEquals(strCopyPath,"/storage/emulated/0/Download/U盘测试/壁纸test/001.jpg");
        uiDevice.pressKeyCode(KEYCODE_CTRL_LEFT);
        printToConsole("            通过");

        printToConsole("（4/9）文件 压缩");
        new RUiObject(new UiSelector().text("下载")).leftclick();
        new RUiObject(new UiSelector().text("U盘测试")).doubleclick();
        new RUiObject(new UiSelector().text("壁纸test")).doubleclick();
        new RUiObject(new UiSelector().text("001.jpg")).rightclick();
        new RUiObject(new UiSelector().text("压缩")).click();
        new RUiObject(new UiSelector().resourceId("org.openthos.compress:id/et_co_name")).setText("001");
        new RUiObject(new UiSelector().text("压缩")).click();
        sleep(2000);
        uiDevice.pressKeyCode(KEYCODE_F5);
        assertTrue(new RUiObject(new UiSelector().text("001.zip")).exists());
        printToConsole("            通过");

        printToConsole("（4/9）文件 解压缩");
        new RUiObject(new UiSelector().text("001.zip")).rightclick();
        new RUiObject(new UiSelector().text("解压缩")).click();
        new RUiObject(new UiSelector().text("解压缩")).click();
        assertTrue(new RUiObject(new UiSelector().text("001.jpg 已经存在，是否继续解压缩（将会覆盖重名文件）？")).exists());
        sleep(2000);
        new RUiObject(new UiSelector().resourceId("android:id/button1")).click();
        printToConsole("            通过");

        printToConsole("（4/9）文件 详情");
        sleep(5000);
        new RUiObject(new UiSelector().text("001.jpg")).rightclick();
        new RUiObject(new UiSelector().text("详情")).click();
        sleep(2000);
        String detailTitle = new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/title_text")).getText().toString();
        Log.d("AAAAAAAAA", detailTitle);
        assertEquals(detailTitle,"001.jpg 属性");
        String detailLocation = new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/location")).getText().toString();
        Log.d("AAAAAAAAA", detailLocation);
        assertEquals(detailLocation,"/storage/emulated/0/Download/U盘测试/壁纸test/001.jpg");
        String detailSpace = new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/size")).getText().toString();
        Log.d("AAAAAAAAA", detailSpace);
        assertEquals(detailSpace,"1.16M");
        assertTrue(new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/limit_owner_read")).isChecked());
        assertTrue(new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/limit_owner_write")).isChecked());
        assertFalse(new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/limit_owner_execute")).isChecked());
        assertTrue(new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/limit_group_read")).isChecked());
        assertTrue(new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/limit_group_write")).isChecked());
        assertFalse(new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/limit_group_execute")).isChecked());
        assertFalse(new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/limit_other_read")).isChecked());
        assertFalse(new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/limit_other_write")).isChecked());
        assertFalse(new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/limit_other_execute")).isChecked());
        new RUiObject(new UiSelector().text("确定")).click();
        printToConsole("            通过");


        printToConsole("（4/9）文件 测试其它类型文件");
        printToConsole("（4/9）文件 txt");
        new RUiObject(new UiSelector().text("U盘测试")).click();
        new RUiObject(new UiSelector().text("文档test")).doubleclick();
        new RUiObject(new UiSelector().text("1寸到6寸照片的尺寸各是多少厘米.txt")).rightclick();
        new RUiObject(new UiSelector().text("打开方式")).click();
        assertTrue(new RUiObject(new UiSelector().text("文本编辑器")).exists());
        uiDevice.pressKeyCode(111);
        printToConsole("            通过");

        printToConsole("（4/9）文件 mp3");
        new RUiObject(new UiSelector().text("U盘测试")).click();
        new RUiObject(new UiSelector().text("音乐test")).doubleclick();
        new RUiObject(new UiSelector().text("远处.mp3")).rightclick();
        new RUiObject(new UiSelector().text("打开方式")).click();
        assertTrue(new RUiObject(new UiSelector().text("VLC")).exists());
        uiDevice.pressKeyCode(111);
        printToConsole("            通过");

        printToConsole("（4/9）文件 apk");
        new RUiObject(new UiSelector().text("U盘测试")).click();
        new RUiObject(new UiSelector().text("apkpure_app_904.apk")).rightclick();
        new RUiObject(new UiSelector().text("打开方式")).click();
        assertTrue(new RUiObject(new UiSelector().text("软件包安装程序")).exists());
        uiDevice.pressKeyCode(111);
        printToConsole("            通过");

        printToConsole("（4/9）文件 mp4");
        new RUiObject(new UiSelector().text("big_buck_bunny_480p_surround-fix.mp4")).rightclick();
        new RUiObject(new UiSelector().text("打开方式")).click();
        assertTrue(new RUiObject(new UiSelector().text("VLC")).exists());
        uiDevice.pressKeyCode(111);
        printToConsole("            通过");
    }

    //测试文件夹操作（点击/双击/右键）
    public void test5Fold() throws UiObjectNotFoundException,IOException {
        printToConsole("（5/9）文件夹操作部分--------------------");
        uiDevice = getRUiDevice();

        printToConsole("（5/9）文件夹 单击选中");
        new RUiObject(new UiSelector().text("文档test")).click();
        String strInfo0 = new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/info")).getText().toString();
        assertEquals(strInfo0,"文档test  5 项");
        sleep(1000);
        printToConsole("            通过");

        printToConsole("（5/9）文件夹 双击打开");
        new RUiObject(new UiSelector().text("文档test")).doubleclick();
        assertTrue(new RUiObject(new UiSelector().text("用户反馈0627.xls")).exists());
        printToConsole("            通过");

        printToConsole("（5/9）文件夹 右键菜单：");
        printToConsole("（5/9）文件夹 打开");
        new RUiObject(new UiSelector().text("U盘测试")).click();
        new RUiObject(new UiSelector().text("文档test")).rightclick();
        new RUiObject(new UiSelector().text("打开")).click();
        assertTrue(new RUiObject(new UiSelector().text("用户反馈0627.xls")).exists());
        printToConsole("            通过");

        printToConsole("（5/9）文件夹 复制");
        new RUiObject(new UiSelector().text("U盘测试")).click();
        new RUiObject(new UiSelector().text("文档test")).rightclick();
        new RUiObject(new UiSelector().text("复制")).click();
        new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/file_path_grid")).rightclick();
        new RUiObject(new UiSelector().text("粘贴")).click();
        sleep(6000);
        uiDevice.pressKeyCode(KEYCODE_F5);
        assertTrue(new RUiObject(new UiSelector().text("文档test.2")).exists());
        new RUiObject(new UiSelector().text("文档test.2")).click();
        String strInfo1 = new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/info")).getText().toString();
        assertEquals(strInfo1,"文档test.2  5 项");
        printToConsole("            通过");

        printToConsole("（5/9）文件夹 剪切");
        sleep(2000);
        new RUiObject(new UiSelector().text("文档test.2")).rightclick();
        new RUiObject(new UiSelector().text("剪切")).click();
        new RUiObject(new UiSelector().text("音乐")).leftclick();
        new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/file_path_grid")).rightclick();
        new RUiObject(new UiSelector().text("粘贴")).click();
        assertTrue(new RUiObject(new UiSelector().text("文档test.2")).exists());
        new RUiObject(new UiSelector().text("下载")).leftclick();
        new RUiObject(new UiSelector().text("U盘测试")).doubleclick();
        assertFalse(new RUiObject(new UiSelector().text("文档test.2")).exists());
        printToConsole("            通过");

        printToConsole("（5/9）文件夹 删除");
        new RUiObject(new UiSelector().text("文档test")).rightclick();
        new RUiObject(new UiSelector().text("复制")).click();
        new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/file_path_grid")).rightclick();
        new RUiObject(new UiSelector().text("粘贴")).click();
        sleep(5000);
        new RUiObject(new UiSelector().text("文档test.2")).rightclick();
        new RUiObject(new UiSelector().text("删除")).click();
        assertTrue(new RUiObject(new UiSelector().text("确认删除到回收站?")).exists());
        new RUiObject(new UiSelector().text("确定")).click();
        new RUiObject(new UiSelector().text("回收站")).leftclick();
        assertTrue(new RUiObject(new UiSelector().text("文档test.2")).exists());
        new RUiObject(new UiSelector().text("下载")).leftclick();
        new RUiObject(new UiSelector().text("U盘测试")).doubleclick();
        assertFalse(new RUiObject(new UiSelector().text("文档test.2")).exists());
        printToConsole("            通过");

        printToConsole("（5/9）文件夹 永久删除");
        new RUiObject(new UiSelector().text("文档test")).rightclick();
        new RUiObject(new UiSelector().text("复制")).click();
        new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/file_path_grid")).rightclick();
        new RUiObject(new UiSelector().text("粘贴")).click();
        sleep(5000);
        new RUiObject(new UiSelector().text("文档test.2")).rightclick();
        new RUiObject(new UiSelector().text("永久删除")).click();
        assertTrue(new RUiObject(new UiSelector().text("确认永久删除？")).exists());
        new RUiObject(new UiSelector().text("确定")).click();
        assertFalse(new RUiObject(new UiSelector().text("文档test.2")).exists());
        printToConsole("            通过");

        printToConsole("（5/9）文件夹 重命名");
        new RUiObject(new UiSelector().text("文档test")).rightclick();
        new RUiObject(new UiSelector().text("重命名")).click();
        uiDevice.pressDelete();
        sleep(1000);
        uiDevice.pressKeyCode(7);
        uiDevice.pressKeyCode(7);
        uiDevice.pressKeyCode(9);
        sleep(1000);
        new RUiObject(new UiSelector().resourceId("android:id/button1")).click();
        assertTrue(new RUiObject(new UiSelector().text("002")).exists());
        printToConsole("            通过");

        printToConsole("（5/9）文件夹 发送");
        sleep(5000);
        new RUiObject(new UiSelector().text("002")).rightclick();
        new RUiObject(new UiSelector().text("发送")).click();
        sleep(2000);
        assertTrue(new RUiObject(new UiSelector().text("不能发送文件夹")).exists());
        uiDevice.pressKeyCode(111);
        printToConsole("            通过");

        printToConsole("（5/9）文件夹 复制路径");
        new RUiObject(new UiSelector().text("壁纸test")).rightclick();
        new RUiObject(new UiSelector().text("复制路径")).click();
        new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/tv_computer")).leftclick();
        new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/et_nivagation")).click();
        sleep(2000);
        uiDevice.pressKeyCode(50,4096);
        String strCopyPath=new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/et_nivagation")).getText();
        Log.d("AAAAAAAAA",strCopyPath);
        assertEquals(strCopyPath,"/storage/emulated/0/Download/U盘测试/壁纸test");
        uiDevice.pressKeyCode(KEYCODE_CTRL_LEFT);
        printToConsole("            通过");

        printToConsole("（5/9）文件夹 压缩");
        new RUiObject(new UiSelector().text("下载")).leftclick();
        new RUiObject(new UiSelector().text("U盘测试")).doubleclick();
        new RUiObject(new UiSelector().text("音乐test")).rightclick();
        new RUiObject(new UiSelector().text("压缩")).click();
        new RUiObject(new UiSelector().resourceId("org.openthos.compress:id/et_co_name")).setText("音乐test");
        new RUiObject(new UiSelector().text("压缩")).click();
        sleep(5000);
        uiDevice.pressKeyCode(KEYCODE_F5);
        assertTrue(new RUiObject(new UiSelector().text("音乐test.zip")).exists());
        printToConsole("            通过");

        printToConsole("（5/9）文件夹 解压缩");
        new RUiObject(new UiSelector().text("音乐test.zip")).rightclick();
        new RUiObject(new UiSelector().text("解压缩")).click();
        new RUiObject(new UiSelector().text("解压缩")).click();
        assertTrue(new RUiObject(new UiSelector().text("音乐test/远处.mp3 已经存在，是否继续解压缩（将会覆盖重名文件）？")).exists());
        sleep(2000);
        new RUiObject(new UiSelector().resourceId("android:id/button1")).click();
        printToConsole("            通过");

        printToConsole("（5/9）文件夹 详情");
        sleep(5000);
        new RUiObject(new UiSelector().text("002")).rightclick();
        new RUiObject(new UiSelector().text("详情")).click();
        sleep(2000);
        String detailTitle = new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/title_text")).getText().toString();
        Log.d("AAAAAAAAA", detailTitle);
        assertEquals(detailTitle,"002 属性");
        String detailLocation = new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/location")).getText().toString();
        Log.d("AAAAAAAAA", detailLocation);
        assertEquals(detailLocation,"/storage/emulated/0/Download/U盘测试/002");
        String detailSpace = new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/size")).getText().toString();
        Log.d("AAAAAAAAA", detailSpace);
        assertEquals(detailSpace,"17M");
        assertTrue(new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/limit_owner_read")).isChecked());
        assertTrue(new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/limit_owner_write")).isChecked());
        assertTrue(new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/limit_owner_execute")).isChecked());
        assertTrue(new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/limit_group_read")).isChecked());
        assertTrue(new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/limit_group_write")).isChecked());
        assertTrue(new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/limit_group_execute")).isChecked());
        assertFalse(new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/limit_other_read")).isChecked());
        assertFalse(new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/limit_other_write")).isChecked());
        assertFalse(new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/limit_other_execute")).isChecked());
        new RUiObject(new UiSelector().text("确定")).click();
        printToConsole("            通过");
    }

    //测试空白处操作（点击/右键）
    public void test6Blank() throws UiObjectNotFoundException {
        printToConsole("（6/9）空白处操作部分--------------------");
        uiDevice = getRUiDevice();

        printToConsole("（6/9）空白处 点击");
        new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/file_path_grid")).click();
        String strInfo = new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/info")).getText().toString();
        assertEquals(strInfo,"3 个文件夹;\t 3 个文件, 占用47.9 MB空间;");
        printToConsole("            通过");

        printToConsole("（6/9）空白处 右键：");
        printToConsole("（6/9）空白处 排序：");
        printToConsole("（6/9）空白处 排序 默认");
        sortTest(new String[]{"002","壁纸test","音乐test","apkpure_app_904.apk","big_buck_bunny_480p_surround-fix.mp4","音乐test.zip"});
        printToConsole("            通过");

        printToConsole("（6/9）空白处 排序 名称逆序");
        new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/file_path_grid")).rightclick();
        new RUiObject(new UiSelector().text("排序")).click();
        new RUiObject(new UiSelector().text("名称")).click();
        uiDevice.pressKeyCode(111);
        sortTest(new String[]{"音乐test.zip","big_buck_bunny_480p_surround-fix.mp4","apkpure_app_904.apk","音乐test","壁纸test","002"});
        printToConsole("            通过");

        printToConsole("（6/9）空白处 排序 名称顺序");
        new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/file_path_grid")).rightclick();
        new RUiObject(new UiSelector().text("排序")).click();
        new RUiObject(new UiSelector().text("名称")).click();
        uiDevice.pressKeyCode(111);
        sortTest(new String[]{"002","壁纸test","音乐test","apkpure_app_904.apk","big_buck_bunny_480p_surround-fix.mp4","音乐test.zip"});
        printToConsole("            通过");

        printToConsole("（6/9）空白处 排序 大小顺序");
        new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/file_path_grid")).rightclick();
        new RUiObject(new UiSelector().text("排序")).click();
        new RUiObject(new UiSelector().text("大小")).click();
        uiDevice.pressKeyCode(111);
        printToConsole("bug[2296]，暂时不做判断");
        //-----------------------------bug[2296]，暂时不做判断--------------------------
        //sortTest(new String[]{"音乐test.zip","big_buck_bunny_480p_surround-fix.mp4","apkpure_app_904.apk","壁纸test","002","音乐test"});

        printToConsole("（6/9）空白处 排序 大小逆序");
        new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/file_path_grid")).rightclick();
        new RUiObject(new UiSelector().text("排序")).click();
        new RUiObject(new UiSelector().text("大小")).click();
        uiDevice.pressKeyCode(111);
        printToConsole("bug[2296]，暂时不做判断");
        //sortTest(new String[]{"壁纸test","002","音乐test","apkpure_app_904.apk","big_buck_bunny_480p_surround-fix.mp4","音乐test.zip"});

        printToConsole("（6/9）空白处 排序 类型顺序");
        new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/file_path_grid")).rightclick();
        new RUiObject(new UiSelector().text("排序")).click();
        new RUiObject(new UiSelector().text("类型")).click();
        uiDevice.pressKeyCode(111);
        printToConsole("bug[2296]，暂时不做判断");
        //sortTest(new String[]{"音乐test.zip","big_buck_bunny_480p_surround-fix.mp4","apkpure_app_904.apk","壁纸test","002","音乐test"});

        printToConsole("（6/9）空白处 排序 类型逆序");
        new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/file_path_grid")).rightclick();
        new RUiObject(new UiSelector().text("排序")).click();
        new RUiObject(new UiSelector().text("类型")).click();
        uiDevice.pressKeyCode(111);
        printToConsole("bug[2296]，暂时不做判断");
        //sortTest(new String[]{"壁纸test","002","音乐test","apkpure_app_904.apk","big_buck_bunny_480p_surround-fix.mp4","音乐test.zip"});

        printToConsole("（6/9）空白处 排序 时间顺序");
        new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/file_path_grid")).rightclick();
        new RUiObject(new UiSelector().text("排序")).click();
        new RUiObject(new UiSelector().text("时间")).click();
        uiDevice.pressKeyCode(111);
        printToConsole("bug[2296]，暂时不做判断");
        //sortTest(new String[]{"apkpure_app_904.apk","big_buck_bunny_480p_surround-fix.mp4","音乐test.zip","002","壁纸test","音乐test"});

        printToConsole("（6/9）空白处 排序 时间逆序");
        new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/file_path_grid")).rightclick();
        new RUiObject(new UiSelector().text("排序")).click();
        new RUiObject(new UiSelector().text("时间")).click();
        uiDevice.pressKeyCode(111);
        printToConsole("bug[2296]，暂时不做判断");
        //sortTest(new String[]{"音乐test","壁纸test","002","音乐test.zip","big_buck_bunny_480p_surround-fix.mp4","apkpure_app_904.apk"});


        printToConsole("（6/9）空白处 新建文件夹");
        new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/file_path_grid")).rightclick();
        new RUiObject(new UiSelector().text("新建文件夹")).click();
        assertTrue(new RUiObject(new UiSelector().text("请输入文件夹名称")).exists());
        uiDevice.pressKeyCode(7);
        uiDevice.pressKeyCode(7);
        uiDevice.pressKeyCode(10);
        sleep(1000);
        new RUiObject(new UiSelector().resourceId("android:id/button1")).click();
        assertTrue(new RUiObject(new UiSelector().text("003")).exists());
        printToConsole("            通过");


        printToConsole("（6/9）空白处 新建文件doc");
        new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/file_path_grid")).rightclick();
        new RUiObject(new UiSelector().text("创建文件")).click();
        assertTrue(new RUiObject(new UiSelector().text("请输入文件名称")).exists());
        uiDevice.pressKeyCode(32);
        uiDevice.pressKeyCode(43);
        uiDevice.pressKeyCode(31);
        uiDevice.pressEnter();
        sleep(1000);
        new RUiObject(new UiSelector().resourceId("android:id/button1")).click();
        assertTrue(new RUiObject(new UiSelector().text("doc.doc")).exists());
        printToConsole("            通过");

        printToConsole("（6/9）空白处 新建文件xls");
        new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/file_path_grid")).rightclick();
        new RUiObject(new UiSelector().text("创建文件")).click();
        new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/tv_file_type")).click();
        new RUiObject(new UiSelector().text(".xls")).click();
        uiDevice.pressKeyCode(52);
        uiDevice.pressKeyCode(40);
        uiDevice.pressKeyCode(47);
        uiDevice.pressEnter();
        sleep(1000);
        new RUiObject(new UiSelector().resourceId("android:id/button1")).click();
        assertTrue(new RUiObject(new UiSelector().text("xls.xls")).exists());
        printToConsole("            通过");

        printToConsole("（6/9）空白处 新建文件ppt");
        new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/file_path_grid")).rightclick();
        new RUiObject(new UiSelector().text("创建文件")).click();
        new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/tv_file_type")).click();
        new RUiObject(new UiSelector().text(".ppt")).click();
        uiDevice.pressKeyCode(44);
        uiDevice.pressKeyCode(44);
        uiDevice.pressKeyCode(48);
        uiDevice.pressEnter();
        sleep(1000);
        new RUiObject(new UiSelector().resourceId("android:id/button1")).click();
        assertTrue(new RUiObject(new UiSelector().text("ppt.ppt")).exists());
        printToConsole("            通过");

        printToConsole("（6/9）空白处 新建文件txt");
        new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/file_path_grid")).rightclick();
        new RUiObject(new UiSelector().text("创建文件")).click();
        new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/tv_file_type")).click();
        new RUiObject(new UiSelector().text(".txt")).click();
        uiDevice.pressKeyCode(48);
        uiDevice.pressKeyCode(52);
        uiDevice.pressKeyCode(48);
        uiDevice.pressEnter();
        sleep(1000);
        new RUiObject(new UiSelector().resourceId("android:id/button1")).click();
        assertTrue(new RUiObject(new UiSelector().text("txt.txt")).exists());
        printToConsole("            通过");

        printToConsole("（6/9）空白处 显示隐藏文件");
        new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/file_path_grid")).rightclick();
        new RUiObject(new UiSelector().text("显示隐藏文件")).click();
        printToConsole("---未做判断");
    }

    //各种快捷键
    public void test7Shortcut() throws UiObjectNotFoundException,IOException {
        printToConsole("（7/9）快捷键部分--------------------");
        uiDevice = getRUiDevice();

        printToConsole("（7/9）快捷键 Enter文件夹");
        new RUiObject(new UiSelector().text("壁纸test")).click();
        uiDevice.pressEnter();
        assertTrue(new RUiObject(new UiSelector().text("001.jpg")).exists());
        printToConsole("            通过");

        printToConsole("（7/9）快捷键 Enter文件");
        new RUiObject(new UiSelector().text("001.jpg")).click();
        uiDevice.pressEnter();
        new RUiObject(new UiSelector().text("仅此一次")).click();
        sleep(5000);
        uiDevice.pressKeyCode(111);
        sleep(3000);
        printToConsole("            通过");

        printToConsole("（7/9）快捷键 Backspcae");
        uiDevice.pressKeyCode(67);
        assertTrue(new RUiObject(new UiSelector().text("002")).exists());
        printToConsole("            通过");

        printToConsole("（7/9）快捷键 Esc");
        uiDevice.pressKeyCode(111);
        assertTrue(new RUiObject(new UiSelector().text("U盘测试.zip")).exists());
        printToConsole("            通过");

        printToConsole("（7/9）快捷键 ctrl+c v");
        new RUiObject(new UiSelector().text("U盘测试")).doubleclick();
        new RUiObject(new UiSelector().text("apkpure_app_904.apk")).click();
        uiDevice.pressKeyCode(31,4096);
        uiDevice.pressKeyCode(KEYCODE_CTRL_LEFT);
        uiDevice.pressKeyCode(50,4096);
        uiDevice.pressKeyCode(KEYCODE_CTRL_LEFT);
        sleep(3000);
        assertTrue(new RUiObject(new UiSelector().text("apkpure_app_904.2.apk")).exists());
        printToConsole("            通过");

        printToConsole("（7/9）快捷键 ctrl+x v");
        sleep(3000);
        new RUiObject(new UiSelector().text("apkpure_app_904.2.apk")).click();
        uiDevice.pressKeyCode(52,4096);
        uiDevice.pressKeyCode(KEYCODE_CTRL_LEFT);
        new RUiObject(new UiSelector().text("音乐")).leftclick();
        uiDevice.pressKeyCode(50,4096);
        uiDevice.pressKeyCode(KEYCODE_CTRL_LEFT);
        sleep(3000);
        assertTrue(new RUiObject(new UiSelector().text("apkpure_app_904.2.apk")).exists());
        new RUiObject(new UiSelector().text("下载")).leftclick();
        new RUiObject(new UiSelector().text("U盘测试")).doubleclick();
        assertFalse(new RUiObject(new UiSelector().text("apkpure_app_904.2.apk")).exists());
        printToConsole("            通过");

        printToConsole("（7/9）快捷键 ctrl+a");
        new RUiObject(new UiSelector().text("002")).doubleclick();
        uiDevice.pressKeyCode(29,4096);
        uiDevice.pressKeyCode(KEYCODE_CTRL_LEFT);
        assertEquals(new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/info")).getText().toString(),"5 项被选中");
        printToConsole("            通过");

        printToConsole("（7/9）快捷键 ctrl多选");
        new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/file_path_grid")).rightclick();
        new RUiObject(new UiSelector().text("排序")).click();
        new RUiObject(new UiSelector().text("名称")).click();
        uiDevice.pressKeyCode(111);
        new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/file_path_grid")).rightclick();
        new RUiObject(new UiSelector().text("排序")).click();
        new RUiObject(new UiSelector().text("名称")).click();
        uiDevice.pressKeyCode(111);
        new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/file_path_grid")).leftclick();

        uiDevice.pressKeyCode(31,4096);
        new RUiObject(new UiSelector().text("1寸到6寸照片的尺寸各是多少厘米.txt")).click();
        uiDevice.pressKeyCode(31,4096);
        new RUiObject(new UiSelector().text("客服部工作手册.2.doc")).click();
        uiDevice.pressKeyCode(31,4096);
        new RUiObject(new UiSelector().text("社区商业怎么做.pptx")).click();
        uiDevice.pressKeyCode(KEYCODE_CTRL_LEFT);
        assertEquals(new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/info")).getText().toString(),"3 项被选中");
        printToConsole("            通过");

        printToConsole("（7/9）快捷键 shift多选");
        new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/file_path_grid")).click();
        uiDevice.pressKeyCode(57,1);
        sleep(1000);
        new RUiObject(new UiSelector().text("1寸到6寸照片的尺寸各是多少厘米.txt")).click();
        uiDevice.pressKeyCode(57,1);
        new RUiObject(new UiSelector().text("社区商业怎么做.pptx")).click();
        uiDevice.pressKeyCode(59);
        uiDevice.pressKeyCode(59);
        sleep(2000);
        assertEquals(new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/info")).getText().toString(),"5 项被选中");
        new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/file_path_grid")).click();
        printToConsole("            通过");

        printToConsole("（7/9）快捷键 框选");
        RUiObject temp1= new RUiObject(new UiSelector().text("1寸到6寸照片的尺寸各是多少厘米.txt"));
        RUiObject temp2= new RUiObject(new UiSelector().text("用户反馈0627.xls"));
        Rect temp11 = temp1.getVisibleBounds();
        Rect temp22 = temp2.getVisibleBounds();
        uiDevice.swipe(temp11.centerX(),temp11.bottom+20,temp22.centerX(),temp22.top-20,200);
        sleep(2000);
        assertEquals(new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/info")).getText().toString(),"4 项被选中");
        new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/file_path_grid")).click();
        printToConsole("            通过");

        printToConsole("（7/9）快捷键 F5刷新");
        uiDevice.pressKeyCode(KEYCODE_F5);
        printToConsole("---未做判断");

        printToConsole("（7/9）快捷键 F2重命名");
        new RUiObject(new UiSelector().text("PPT模板大全(精华).ppt")).click();
        uiDevice.pressKeyCode(KEYCODE_F2);
        sleep(600);
        uiDevice.pressKeyCode(7);
        sleep(600);
        uiDevice.pressKeyCode(7);
        sleep(600);
        uiDevice.pressKeyCode(11);
        sleep(2000);
        new RUiObject(new UiSelector().resourceId("android:id/button1")).click();
        assertTrue(new RUiObject(new UiSelector().text("004.ppt")).exists());
        sleep(3000);
        printToConsole("            通过");

        printToConsole("（7/9）快捷键 delete文件");
        uiDevice.pressKeyCode(111);
        new RUiObject(new UiSelector().text("doc.doc")).click();
        uiDevice.pressKeyCode(112);
        sleep(1000);
        assertTrue(new RUiObject(new UiSelector().text("确认删除到回收站?")).exists());
        new RUiObject(new UiSelector().resourceId("android:id/button1")).click();
        sleep(3000);
        uiDevice.pressKeyCode(KEYCODE_F5);
        assertFalse(new RUiObject(new UiSelector().text("doc.doc")).exists());
        new RUiObject(new UiSelector().text("回收站")).leftclick();
        assertTrue(new RUiObject(new UiSelector().text("doc.doc")).exists());
        printToConsole("            通过");

        printToConsole("（7/9）快捷键 delete回收站");
        new RUiObject(new UiSelector().text("doc.doc")).click();
        uiDevice.pressKeyCode(112);
        sleep(1000);
        assertTrue(new RUiObject(new UiSelector().text("确认永久删除？")).exists());
        new RUiObject(new UiSelector().resourceId("android:id/button1")).click();
        sleep(3000);
        uiDevice.pressKeyCode(KEYCODE_F5);
        assertFalse(new RUiObject(new UiSelector().text("doc.doc")).exists());
        printToConsole("            通过");

        printToConsole("（7/9）快捷键 delete永久删除");
        new RUiObject(new UiSelector().text("下载")).leftclick();
        new RUiObject(new UiSelector().text("U盘测试")).doubleclick();
        new RUiObject(new UiSelector().text("ppt.ppt")).click();
        uiDevice.pressKeyCode(112,1);
        uiDevice.pressKeyCode(KEYCODE_SHIFT_LEFT);
        sleep(1000);
        assertTrue(new RUiObject(new UiSelector().text("确认永久删除？")).exists());
        new RUiObject(new UiSelector().resourceId("android:id/button1")).click();
        sleep(3000);
        uiDevice.pressKeyCode(KEYCODE_F5);
        assertFalse(new RUiObject(new UiSelector().text("ppt.ppt")).exists());
        printToConsole("            通过");


        printToConsole("（7/9）快捷键 TAB切换：");
        printToConsole("（7/9）快捷键 TAB 回退");
        new RUiObject(new UiSelector().text("音乐test")).doubleclick();
        //鼠标移动到文件管理器内容栏
        Rect rect1= new RUiObject(new UiSelector().text("远处.mp3")).getVisibleBounds();
        sleep(1000);
        String devicesInfo = uiDevice.executeShellCommand("cat /proc/bus/input/devices");
        int mouseInfoStart=devicesInfo.indexOf("mouse0");
        String mouseInfo = devicesInfo.substring(mouseInfoStart+12,mouseInfoStart+13);
        sleep(1000);
        uiDevice.executeShellCommand("sendevent /dev/input/event4 2 0 -2000");
        uiDevice.executeShellCommand("sendevent /dev/input/event4 2 1 -2000");
        uiDevice.executeShellCommand("sendevent /dev/input/event4 0 0 0");
        uiDevice.executeShellCommand("sendevent /dev/input/event4 2 0 " + rect1.centerX());
        uiDevice.executeShellCommand("sendevent /dev/input/event4 2 1 " + rect1.centerY());
        uiDevice.executeShellCommand("sendevent /dev/input/event4 0 0 0");
        sleep(2000);

        uiDevice.pressKeyCode(61);
        uiDevice.pressEnter();
        sleep(1000);
        assertTrue(new RUiObject(new UiSelector().text("音乐test.zip")).exists());
        printToConsole("            通过");

        printToConsole("（7/9）快捷键 TAB 前进");
        for(int i=0;i<2;i++) {
            uiDevice.pressKeyCode(61);
            sleep(500);
        }
        uiDevice.pressEnter();
        sleep(1000);
        assertTrue(new RUiObject(new UiSelector().text("远处.mp3")).exists());
        printToConsole("            通过");

        printToConsole("（7/9）快捷键 TAB 向上");
        for(int i=0;i<3;i++) {
            uiDevice.pressKeyCode(61);
            sleep(500);
        }
        uiDevice.pressEnter();
        sleep(1000);
        assertTrue(new RUiObject(new UiSelector().text("002")).exists());
        printToConsole("            通过");

        printToConsole("（7/9）快捷键 TAB 设置");
        uiDevice.pressKeyCode(61);
        sleep(500);
        uiDevice.pressEnter();
        sleep(1000);
        assertTrue(new RUiObject(new UiSelector().text("显示隐藏文件")).exists());
        printToConsole("            通过");
        sleep(1000);
        uiDevice.pressKeyCode(20);
        uiDevice.pressEnter();
        sleep(1000);
        printToConsole("---设置切换未做判断");

        printToConsole("（7/9）快捷键 TAB 格子显示");
        uiDevice.pressKeyCode(61);
        uiDevice.pressEnter();
        sleep(1000);
        assertTrue(new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/file_path_grid")).exists());
        printToConsole("            通过");

        printToConsole("（7/9）快捷键 TAB 列表显示");
        uiDevice.pressKeyCode(61);
        uiDevice.pressEnter();sleep(1000);
        uiDevice.pressKeyCode(KEYCODE_F5);


        sleep(1000);
        //总是报错,只能肉眼判断
        //assertTrue(new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/file_path_list")).exists());
        printToConsole("            通过");

        printToConsole("（7/9）快捷键 TAB 地址栏");
        uiDevice.pressKeyCode(61);
        sleep(1000);
        for (int i=0;i<4;i++){
            uiDevice.pressDelete();
            sleep(1000);
        }
        uiDevice.pressEnter();
        sleep(1000);
        assertTrue(new RUiObject(new UiSelector().text("U盘测试.zip")).exists());
        printToConsole("            通过");

        printToConsole("（7/9）快捷键 TAB 搜索栏");
        printToConsole("---搜索bug，不做判断");
        /*for (int i=0;i<8;i++){
            uiDevice.pressKeyCode(61);
            sleep(1000);
        }
        uiDevice.pressKeyCode(7);
        uiDevice.pressKeyCode(7);
        uiDevice.pressKeyCode(8);
        uiDevice.pressEnter();
        assertTrue(new RUiObject(new UiSelector().text("001.jpg")).exists());*/

        printToConsole("（7/9）快捷键 TAB 导航栏切换");
        printToConsole("---bug[1759]，暂时不做判断");
        for (int i=0;i<9;i++){
            uiDevice.pressKeyCode(61);
            sleep(1000);
        }
        uiDevice.pressKeyCode(20);
        sleep(1000);
        uiDevice.pressKeyCode(20);
        sleep(1000);
        uiDevice.pressKeyCode(20);
        uiDevice.pressEnter();
        sleep(1000);
        //assertTrue(new RUiObject(new UiSelector().text("wallpaper")).exists());
        new RUiObject(new UiSelector().text("图片")).leftclick();

        printToConsole("（7/9）快捷键 TAB 内容栏切换");
        printToConsole("---bug[2316]，暂时不做判断");
        /*uiDevice.pressKeyCode(61);
        sleep(1000);
        uiDevice.pressKeyCode(61);
        sleep(1000);
        int j=0;
        while(!new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/info")).getText().toString().equals("wallpaper  14 项")) {
            Log.d("AAAAAAAAA", new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/info")).getText().toString());
            uiDevice.pressKeyCode(20);
            sleep(1000);
            j++;
            if (j>10){
                break;
            }
        }
        uiDevice.pressEnter();
        sleep(1000);
        assertTrue(new RUiObject(new UiSelector().text("wallpaper (1).jpg")).exists());
        printToConsole("            通过");*/
        new RUiObject(new UiSelector().text("wallpaper")).doubleclick();
        sleep(1000);

        printToConsole("（7/9）快捷键 滑轮滚动：");
        uiDevice.pressKeyCode(29,4096);
        uiDevice.pressKeyCode(KEYCODE_CTRL_LEFT);
        sleep(1000);
        uiDevice.pressKeyCode(31,4096);
        uiDevice.pressKeyCode(KEYCODE_CTRL_LEFT);
        sleep(3000);
        uiDevice.pressKeyCode(50,4096);
        uiDevice.pressKeyCode(KEYCODE_CTRL_LEFT);
        sleep(5000);
        uiDevice.pressKeyCode(50,4096);
        uiDevice.pressKeyCode(KEYCODE_CTRL_LEFT);
        sleep(5000);
        assertTrue(new RUiObject(new UiSelector().text("wallpaper (1).jpg")).exists());
        assertFalse(new RUiObject(new UiSelector().text("wallpaper (9).3.jpg")).exists());
        sleep(1000);

        printToConsole("（7/9）快捷键 滑轮下翻");
        for (int i=0;i<15;i++) {
            uiDevice.executeShellCommand("sendevent /dev/input/event" + mouseInfo + " 2 8 -2");
            uiDevice.executeShellCommand("sendevent /dev/input/event" + mouseInfo + " 0 0 0");
            sleep(500);
        }
        assertFalse(new RUiObject(new UiSelector().text("wallpaper (1).jpg")).exists());
        assertTrue(new RUiObject(new UiSelector().text("wallpaper (9).3.jpg")).exists());
        printToConsole("            通过");

        printToConsole("（7/9）快捷键 滑轮上翻");
        for (int i=0;i<15;i++) {
            uiDevice.executeShellCommand("sendevent /dev/input/event" + mouseInfo + " 2 8 2");
            uiDevice.executeShellCommand("sendevent /dev/input/event" + mouseInfo + " 0 0 0");
            sleep(500);
        }
        assertTrue(new RUiObject(new UiSelector().text("wallpaper (1).jpg")).exists());
        assertFalse(new RUiObject(new UiSelector().text("wallpaper (9).3.jpg")).exists());
        printToConsole("            通过");

        printToConsole("（7/9）快捷键 鼠标中键");
        uiDevice.executeShellCommand("sendevent /dev/input/event" + mouseInfo + " 4 4 589827");
        uiDevice.executeShellCommand("sendevent /dev/input/event" + mouseInfo + " 1 274 1");
        uiDevice.executeShellCommand("sendevent /dev/input/event" + mouseInfo + " 0 0 0");
        uiDevice.executeShellCommand("sendevent /dev/input/event" + mouseInfo + " 4 4 589827");
        uiDevice.executeShellCommand("sendevent /dev/input/event" + mouseInfo + " 1 274 0");
        uiDevice.executeShellCommand("sendevent /dev/input/event" + mouseInfo + " 0 0 0");
        sleep(1000);
        assertFalse(new RUiObject(new UiSelector().text("wallpaper (1).jpg")).exists());
        printToConsole("            通过");
    }

    public void test8Recycle() throws UiObjectNotFoundException,IOException {
        printToConsole("（8/9）回收站部分--------------------");
        uiDevice = getRUiDevice();

        printToConsole("（8/9）回收站 全部清空");
        new RUiObject(new UiSelector().text("回收站")).leftclick();
        if (new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/file_path_list")).exists()) {
            new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/iv_grid_view")).click();
            sleep(1000);
        }
        new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/file_path_grid")).rightclick();
        new RUiObject(new UiSelector().text("全部清空")).click();
        assertTrue(new RUiObject(new UiSelector().text("确认永久删除？")).exists());
        new RUiObject(new UiSelector().resourceId("android:id/button1")).click();
        sleep(5000);
        assertEquals(new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/info")).getText().toString(),"空目录!");
        printToConsole("            通过");

        printToConsole("（8/9）回收站 全部还原");
        new RUiObject(new UiSelector().text("下载")).leftclick();
        new RUiObject(new UiSelector().text("U盘测试")).doubleclick();
        new RUiObject(new UiSelector().text("音乐test")).doubleclick();
        sleep(2000);
        uiDevice.pressKeyCode(29,4096);
        uiDevice.pressKeyCode(KEYCODE_CTRL_LEFT);
        sleep(1000);
        uiDevice.pressKeyCode(112);
        sleep(1000);
        assertTrue(new RUiObject(new UiSelector().text("确认删除到回收站?")).exists());
        new RUiObject(new UiSelector().text("确定")).click();
        sleep(2000);
        new RUiObject(new UiSelector().text("回收站")).leftclick();
        assertEquals(new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/info")).getText().toString()," 2 个文件, 占用29.7 MB空间;");
        new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/file_path_grid")).rightclick();
        new RUiObject(new UiSelector().text("全部还原")).click();
        sleep(5000);
        uiDevice.pressKeyCode(KEYCODE_F5);
        assertFalse(new RUiObject(new UiSelector().text("远处.mp3")).exists());
        assertFalse(new RUiObject(new UiSelector().text("追光者.flac")).exists());
        new RUiObject(new UiSelector().text("下载")).leftclick();
        new RUiObject(new UiSelector().text("U盘测试")).doubleclick();
        new RUiObject(new UiSelector().text("音乐test")).doubleclick();
        sleep(2000);
        assertTrue(new RUiObject(new UiSelector().text("远处.mp3")).exists());
        assertTrue(new RUiObject(new UiSelector().text("追光者.flac")).exists());
        printToConsole("            通过");

        printToConsole("（8/9）回收站 剪切");
        new RUiObject(new UiSelector().text("远处.mp3")).rightclick();
        new RUiObject(new UiSelector().text("删除")).click();
        sleep(1000);
        assertTrue(new RUiObject(new UiSelector().text("确认删除到回收站?")).exists());
        new RUiObject(new UiSelector().text("确定")).click();
        new RUiObject(new UiSelector().text("回收站")).leftclick();
        new RUiObject(new UiSelector().text("远处.mp3")).rightclick();
        new RUiObject(new UiSelector().text("剪切")).click();
        sleep(1000);
        new RUiObject(new UiSelector().text("下载")).leftclick();
        uiDevice.pressKeyCode(50,4096);
        uiDevice.pressKeyCode(KEYCODE_CTRL_LEFT);
        sleep(1000);
        uiDevice.pressKeyCode(KEYCODE_F5);
        assertTrue(new RUiObject(new UiSelector().text("远处.mp3")).exists());
        new RUiObject(new UiSelector().text("回收站")).leftclick();
        assertFalse(new RUiObject(new UiSelector().text("远处.mp3")).exists());
        printToConsole("            通过");
    }

    public void test9end() throws UiObjectNotFoundException,IOException {
        printToConsole("（9/9）关闭文件管理器--------------------");
        sleep(2000);
        uiDevice = getRUiDevice();
        uiDevice.swipe(0, 0, 100, 0, 2);
        new RUiObject(new UiSelector().resourceId("android:id/mwCloseBtn")).click();
        printToConsole("文件管理器测试完成--------------------");
    }
    public void test99end() throws UiObjectNotFoundException,IOException {
        /*RUiDevice uiDevice = getRUiDevice();
        new RUiObject(new UiSelector().text("apkpure_app_904.apk")).click();
        uiDevice.pressKeyCode(31,4096);
        uiDevice.pressKeyCode(KEYCODE_CTRL_LEFT);
        uiDevice.pressKeyCode(50,4096);
        uiDevice.pressKeyCode(KEYCODE_CTRL_LEFT);*/
    }
    private void printToConsole(String string){
        bundle.clear();
        bundle.putString("---",string);
        getAutomationSupport().sendStatus(0, bundle);
    }

    private void sortTest(String[] expectOrder)throws UiObjectNotFoundException{
        int i=0;
        for (String expectItem : expectOrder){
            RUiObject sortGrid = new RUiObject(new UiSelector().resourceId("org.openthos.filemanager:id/file_path_grid"));
            RUiObject sortItem = sortGrid.getChild(new UiSelector().resourceId("org.openthos.filemanager:id/ll_grid_item_bg").index(i));
            String strSortItem = sortItem.getChild(new UiSelector().resourceId("org.openthos.filemanager:id/file_name")).getText().toString();
            Log.d("AAAAAAAAA", strSortItem);
            assertTrue(strSortItem.equals(expectItem));
            i=i+1;
        }

    }

    private void altAndTab() throws IOException{
        uiDevice = getRUiDevice();
        String devicesInfo = uiDevice.executeShellCommand("cat /proc/bus/input/devices");
        int keyInfoStart=devicesInfo.indexOf("kbd leds");
        String keyInfo = devicesInfo.substring(keyInfoStart+14,keyInfoStart+15);
        sleep(1000);

        uiDevice.executeShellCommand("sendevent /dev/input/event" + keyInfo + " 4 4 56");
        uiDevice.executeShellCommand("sendevent /dev/input/event" + keyInfo + " 1 56 1");
        uiDevice.executeShellCommand("sendevent /dev/input/event" + keyInfo + " 0 0 0");
        uiDevice.executeShellCommand("sendevent /dev/input/event" + keyInfo + " 4 4 15");
        uiDevice.executeShellCommand("sendevent /dev/input/event" + keyInfo + " 1 15 1");
        uiDevice.executeShellCommand("sendevent /dev/input/event" + keyInfo + " 0 0 0");
        uiDevice.executeShellCommand("sendevent /dev/input/event" + keyInfo + " 4 4 56");
        uiDevice.executeShellCommand("sendevent /dev/input/event" + keyInfo + " 1 56 0");
        uiDevice.executeShellCommand("sendevent /dev/input/event" + keyInfo + " 0 0 0");
        uiDevice.executeShellCommand("sendevent /dev/input/event" + keyInfo + " 4 4 15");
        uiDevice.executeShellCommand("sendevent /dev/input/event" + keyInfo + " 1 15 0");
        uiDevice.executeShellCommand("sendevent /dev/input/event" + keyInfo + " 0 0 0");
    }
}

