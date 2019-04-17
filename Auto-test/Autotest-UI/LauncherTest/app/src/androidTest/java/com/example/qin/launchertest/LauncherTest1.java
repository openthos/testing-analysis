package com.example.qin.launchertest;

import android.os.RemoteException;
import android.support.test.uiautomator.Configurator;
import android.support.test.uiautomator.RUiAutomatorTestCase;
import android.support.test.uiautomator.RUiDevice;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.RUiObject;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.view.KeyEvent;

import java.io.IOException;

/**
 * Created by qin on 17-10-17.
 */

public class LauncherTest1 extends RUiAutomatorTestCase {

    public void testlauncher() throws UiObjectNotFoundException,RemoteException{
        RUiDevice RUiDevice=getRUiDevice();
        UiDevice UiDevice=getUiDevice();
        RUiDevice.wakeUp();
        assertTrue("screen on :can't wakeup", RUiDevice.isScreenOn());

        RUiObject lch = new RUiObject(new UiSelector().resourceId("com.android.launcher3:id/launcher"));
        //新建文件夹
        RUiDevice.getInstance().click(0, 0);
        assertEquals("launcher on","com.android.launcher3",RUiDevice.getCurrentPackageName());
        System.out.println("Test:新建文件夹");
        lch.rightclick();
        new RUiObject(new UiSelector().text("新建文件夹")).click();
        RUiObject newfolder = new RUiObject(new UiSelector().text("新建文件夹1"));
        assertTrue("新建文件夹",newfolder.exists());
        //新建文件
        System.out.println("Test:新建文件");
        RUiDevice.getInstance().click(0, 0);
        assertEquals("launcher on","com.android.launcher3",RUiDevice.getCurrentPackageName());
        lch.rightclick();
        new RUiObject(new UiSelector().text("新建文件")).click();
        new RUiObject(new UiSelector().text("TXT文本文档")).click();
        new RUiObject(new UiSelector().resourceId("com.android.launcher3:id/confirm")).click();
        RUiObject newtxt = new RUiObject(new UiSelector().text("新建文件1.txt"));
        assertTrue("新建TXT文本文档",newtxt.exists());

        lch.rightclick();
        new RUiObject(new UiSelector().text("新建文件")).click();
        new RUiObject(new UiSelector().text("Word文本文档")).click();
        new RUiObject(new UiSelector().resourceId("com.android.launcher3:id/confirm")).click();
        RUiObject newdoc = new RUiObject(new UiSelector().text("新建文件1.doc"));
        assertTrue("新建Word文本文档",newdoc.exists());

        lch.rightclick();
        new RUiObject(new UiSelector().text("新建文件")).click();
        new RUiObject(new UiSelector().text("PowerPoint幻灯片文档")).click();
        new RUiObject(new UiSelector().resourceId("com.android.launcher3:id/confirm")).click();
        RUiObject newppt = new RUiObject(new UiSelector().text("新建文件1.ppt"));
        assertTrue("新建PowerPoint幻灯片文档",newppt.exists());

        lch.rightclick();
        new RUiObject(new UiSelector().text("新建文件")).click();
        new RUiObject(new UiSelector().text("Excel表格文档")).click();
        new RUiObject(new UiSelector().resourceId("com.android.launcher3:id/confirm")).click();
        RUiObject newxls = new RUiObject(new UiSelector().text("新建文件1.xls"));
        assertTrue("新建Excel表格文档",newxls.exists());

        lch.rightclick();
        new RUiObject(new UiSelector().text("新建文件")).click();
        new RUiObject(new UiSelector().resourceId("com.android.launcher3:id/cancel")).click();
        assertFalse("取消新建文件",new RUiObject(new UiSelector().text("请选择要创建的文件的格式")).exists());
        //排序
        System.out.println("Test:排序");
        lch.rightclick();
        new RUiObject(new UiSelector().text("排序")).click();
        //更改壁纸
        System.out.println("Test:更改壁纸");
        lch.rightclick();
        new RUiObject(new UiSelector().text("更改壁纸")).click();
        assertEquals("FileManager open success","org.openthos.filemanager",RUiDevice.getCurrentPackageName());
        RUiObject wallpaper = new RUiObject(new UiSelector().text("wallpaper"));
        long actiontime = Configurator.getInstance().getActionAcknowledgmentTimeout();
        Configurator.getInstance().setActionAcknowledgmentTimeout(0);
        sleep(1000);
        wallpaper.click();
        wallpaper.click();
        sleep(1000);
        new RUiObject(new UiSelector().text("wallpaper (1).jpg")).click();
        new RUiObject(new UiSelector().text("wallpaper (1).jpg")).click();
        new RUiObject(new UiSelector().resourceId("com.android.launcher3:id/set_wallpaper_button")).click();
        sleep(3000);
        //bug2139
        System.out.println(new RUiObject(new UiSelector().resourceId("android:id/mwCloseBtn")).exists());
        if(new RUiObject(new UiSelector().resourceId("android:id/mwCloseBtn")).exists())
        {
            new RUiObject(new UiSelector().resourceId("android:id/mwCloseBtn")).click();
        }
        sleep(1000);
        assertEquals("set wallpaper success","com.android.launcher3",RUiDevice.getCurrentPackageName());
        Configurator.getInstance().setActionAcknowledgmentTimeout(actiontime);
        //显示设置
        System.out.println("Test:显示设置");
        lch.rightclick();
        new RUiObject(new UiSelector().text("显示设置")).click();
        sleep(1000);
        assertEquals("settings open success","com.android.settings",RUiDevice.getCurrentPackageName());
        new RUiObject(new UiSelector().text("亮度")).click();
        sleep(1000);
        assertTrue("调节亮度",new RUiObject(new UiSelector().resourceId("com.android.systemui:id/slider")).exists());
        RUiDevice.pressKeyCode(KeyEvent.KEYCODE_ESCAPE);
        sleep(1000);
        new RUiObject(new UiSelector().text("壁纸")).click();
        sleep(500);
        //new RUiObject(new UiSelector().text("图库")).click();
        //sleep(500);
        //new RUiObject(new UiSelector().text("取消")).click();
        //sleep(500);
        new RUiObject(new UiSelector().text("设置壁纸")).click();
        sleep(500);
        new RUiObject(new UiSelector().resourceId("com.android.launcher3:id/wallpaper_list")).click();
        new RUiObject(new UiSelector().resourceId("com.android.launcher3:id/set_wallpaper_button")).click();
        sleep(500);
        new RUiObject(new UiSelector().resourceId("android:id/mwLaunchBtn")).click();
        new RUiObject(new UiSelector().text("字体大小")).click();
        assertEquals("字体大小窗口","字体大小",new RUiObject(new UiSelector().resourceId("android:id/alertTitle")).getText());
        new RUiObject(new UiSelector().text("超大")).click();
        sleep(1000);
        assertEquals("验证字体大小","超大",new RUiObject(new UiSelector().resourceId("android:id/summary")).getText());
        new RUiObject(new UiSelector().text("字体大小")).click();
        new RUiObject(new UiSelector().text("普通")).click();
        sleep(1000);
        assertEquals("验证字体大小","普通",new RUiObject(new UiSelector().resourceId("android:id/summary")).getText());
        closewindow();
        //我的电脑
        System.out.println("Test:我的电脑");
        //双击打开
        lch.click();
        Configurator.getInstance().setActionAcknowledgmentTimeout(0);
        for(int i=0;i<2;i++){
            RUiDevice.getInstance().click(55, 40);
        }
        sleep(2000);
        assertEquals("打开计算机目录","org.openthos.filemanager",RUiDevice.getCurrentPackageName());
        closewindow();
        Configurator.getInstance().setActionAcknowledgmentTimeout(actiontime);
        //右键菜单
        RUiObject mc = new RUiObject(new UiSelector().text("我的电脑"));
        mc.rightclick();
        new RUiObject(new UiSelector().text("打开")).click();
        sleep(1000);
        assertEquals("打开计算机目录","org.openthos.filemanager",RUiDevice.getCurrentPackageName());
        closewindow();
        mc.rightclick();
        new RUiObject(new UiSelector().text("关于本机")).click();
        sleep(1000);
        assertEquals("设备信息","com.android.settings",RUiDevice.getCurrentPackageName());
        closewindow();
        //UserGuide.html
        System.out.println("Test:用户手册");
        RUiObject user = new RUiObject(new UiSelector().text("UserGuide.html"));
        user.rightclick();
        new RUiObject(new UiSelector().text("打开")).click();
        if(new RUiObject(new UiSelector().text("HTML 查看程序")).exists()) {
            new RUiObject(new UiSelector().text("HTML 查看程序")).click();
        }
        sleep(500);
        new RUiObject(new UiSelector().resourceId("android:id/button_once")).click();
        sleep(500);
        closewindow();
        //打开方式
        user.rightclick();
        new RUiObject(new UiSelector().text("打开方式")).click();
        new RUiObject(new UiSelector().text("Openthos浏览器")).click();
        sleep(1000);
        RUiObject objectSide4 = new RUiObject( new UiSelector().resourceId("android:id/mwOuterBorder"));
        android.graphics.Rect myAppSide4 = objectSide4.getVisibleBounds();
        sleep(1000);
        RUiDevice.click(100, myAppSide4.top);
        RUiDevice.pressKeyCode(KeyEvent.KEYCODE_F4,2);
        //压缩UserGuide.html为zip
        System.out.println("Test:压缩");
        user.rightclick();
        new RUiObject(new UiSelector().text("压缩")).click();
        sleep(1000);
        RUiDevice.click(0,0);
        RUiObject co_name = new RUiObject(new UiSelector().resourceId("org.openthos.compress:id/et_co_name"));
        co_name.setText("usercomp");
        RUiDevice.pressEnter();
        sleep(500);
        new RUiObject(new UiSelector().resourceId("org.openthos.compress:id/bt_co_compress")).click();
        sleep(3000);
        RUiObject Uzip =new RUiObject(new UiSelector().text("usercomp.zip"));
        assertTrue("压缩zip",Uzip.exists());
        //压缩新建文件夹为.7z,并设置密码123
        lch.click();
        sleep(500);
        newfolder.rightclick();
        new RUiObject(new UiSelector().text("压缩")).click();
        new RUiObject(new UiSelector().resourceId("org.openthos.compress:id/bt_co_destination")).click();
        sleep(500);
        UiScrollable deslist = new UiScrollable(new UiSelector().resourceId("org.openthos.compress:id/lv_file_list"));
        UiObject comfile = deslist.getChildByText(new UiSelector().className("android.widget.TextView"), "Desktop",true);
        comfile.click();
        sleep(500);
        new RUiObject(new UiSelector().text("新建文件夹1")).click();
        sleep(500);
        new RUiObject(new UiSelector().resourceId("org.openthos.compress:id/bt_bottom_ok")).click();
        co_name.setText("foldercomp");
        RUiDevice.pressEnter();
        new RUiObject(new UiSelector().resourceId("org.openthos.compress:id/sp_co_type")).click();
        new RUiObject(new UiSelector().text(".7z")).click();
        new RUiObject(new UiSelector().resourceId("org.openthos.compress:id/cb_co_passwd")).click();
        new RUiObject(new UiSelector().resourceId("org.openthos.compress:id/co_passwd_visible")).click();
        RUiObject co_passwd = new RUiObject(new UiSelector().resourceId("org.openthos.compress:id/et_co_passwd"));
        co_passwd.setText("123");
        new RUiObject(new UiSelector().resourceId("org.openthos.compress:id/bt_co_compress")).click();
        sleep(3000);
        //压缩新建文件
        newtxt.rightclick();
        new RUiObject(new UiSelector().text("压缩")).click();
        sleep(1000);
        RUiDevice.click(0,0);
        co_name.setText("txtcomp");
        RUiDevice.pressEnter();
        new RUiObject(new UiSelector().resourceId("org.openthos.compress:id/sp_co_type")).click();
        new RUiObject(new UiSelector().text(".tar")).click();
        new RUiObject(new UiSelector().resourceId("org.openthos.compress:id/bt_co_compress")).click();
        sleep(3000);
        RUiObject txttar = new RUiObject(new UiSelector().text("txtcomp.tar"));
        assertTrue("压缩txt",txttar.exists());
        //剪切txt
        System.out.println("Test:剪切");
        newtxt.rightclick();
        new RUiObject(new UiSelector().text("剪切")).click();
        lch.rightclick();
        new RUiObject(new UiSelector().text("粘贴")).click();
        sleep(2000);
        assertTrue("剪切txt文件",newtxt.exists());
        //复制doc
        System.out.println("Test:复制");
        lch.click();
        newdoc.rightclick();
        new RUiObject(new UiSelector().text("复制")).click();
        lch.rightclick();
        new RUiObject(new UiSelector().text("粘贴")).click();
        sleep(2000);
        RUiObject newdoc2 = new RUiObject(new UiSelector().text("新建文件1.2.doc"));
        assertTrue("复制word文件",newdoc2.exists());
        //重命名ppt
        System.out.println("Test:重命名");
        lch.click();
        newppt.rightclick();
        sleep(500);
        new RUiObject(new UiSelector().text("重命名")).click();
        sleep(500);
        newppt.setText("ReName.ppt");
        sleep(500);
        RUiDevice.pressEnter();
        sleep(2000);
        RUiObject newppt2 =new RUiObject(new UiSelector().text("ReName.ppt"));
        assertTrue("重命名ppt",newppt2.exists());
        //xls属性
        lch.click();
        sleep(500);
        newxls.rightclick();
        sleep(500);
        new RUiObject(new UiSelector().text("属性")).click();
        sleep(1000);
        new RUiObject(new UiSelector().resourceId("com.android.launcher3:id/confirm")).click();
        //解压缩
        System.out.println("Test:解压缩");
        txttar.rightclick();
        new RUiObject(new UiSelector().text("解压")).click();
        sleep(500);
        new RUiObject(new UiSelector().resourceId("org.openthos.compress:id/bt_decompress")).click();
        sleep(500);
        new RUiObject(new UiSelector().resourceId("android:id/button1")).click();
        sleep(500);
        newfolder.click();
        RUiDevice.pressEnter();
        sleep(500);
        RUiObject fold7z = new RUiObject(new UiSelector().text("foldercomp.7z"));
        Configurator.getInstance().setActionAcknowledgmentTimeout(0);
        fold7z.click();
        fold7z.click();
        new RUiObject(new UiSelector().resourceId("org.openthos.compress:id/bt_extract_all")).click();
        new RUiObject(new UiSelector().resourceId("org.openthos.compress:id/bt_decompress")).click();
        sleep(2000);
        assertTrue("解压fold7z",newfolder.exists());
        Configurator.getInstance().setActionAcknowledgmentTimeout(actiontime);
        sleep(1000);
        closewindow();
        //删除
        System.out.println("Test:删除");
        lch.click();
        txttar.rightclick();
        new RUiObject(new UiSelector().text("删除")).click();
        new RUiObject(new UiSelector().resourceId("android:id/button1")).click();
        sleep(1000);
        newtxt.rightclick();
        new RUiObject(new UiSelector().text("删除")).click();
        new RUiObject(new UiSelector().resourceId("android:id/button1")).click();
        sleep(1000);
        newppt2.rightclick();
        new RUiObject(new UiSelector().text("删除")).click();
        new RUiObject(new UiSelector().resourceId("android:id/button1")).click();
        sleep(1000);
        Uzip.rightclick();
        new RUiObject(new UiSelector().text("删除")).click();
        new RUiObject(new UiSelector().resourceId("android:id/button1")).click();
        sleep(1000);
        lch.click();
        newfolder.rightclick();
        new RUiObject(new UiSelector().text("删除")).click();
        new RUiObject(new UiSelector().resourceId("android:id/button1")).click();
        sleep(1000);
        newdoc.rightclick();
        new RUiObject(new UiSelector().text("删除")).click();
        new RUiObject(new UiSelector().resourceId("android:id/button1")).click();
        sleep(1000);
        newdoc2.rightclick();
        new RUiObject(new UiSelector().text("删除")).click();
        new RUiObject(new UiSelector().resourceId("android:id/button1")).click();
        sleep(1000);
        newxls.rightclick();
        new RUiObject(new UiSelector().text("删除")).click();
        new RUiObject(new UiSelector().resourceId("android:id/button1")).click();
        sleep(2000);
        //回收站
        System.out.println("Test:回收站");
        RUiDevice.getInstance().click(0, 0);
        sleep(500);
        assertEquals("launcher on","com.android.launcher3",RUiDevice.getCurrentPackageName());
        Configurator.getInstance().setActionAcknowledgmentTimeout(0);
        for(int i=0;i<2;i++){
            RUiDevice.getInstance().click(55, 180);
        }
        sleep(1000);
        assertEquals("打开回收站","org.openthos.filemanager",RUiDevice.getCurrentPackageName());
        closewindow();
        Configurator.getInstance().setActionAcknowledgmentTimeout(actiontime);
        sleep(1000);
        //右键菜单
        RUiObject rb = new RUiObject(new UiSelector().text("回收站"));
        rb.rightclick();
        new RUiObject(new UiSelector().text("清空回收站")).click();
        assertTrue("清空回收站",new RUiObject(new UiSelector().text("确认清空回收站?")).exists());
        new RUiObject(new UiSelector().resourceId("android:id/button1")).click();
        sleep(1000);
        assertFalse("是",new RUiObject(new UiSelector().text("确认清空回收站?")).exists());
        rb.rightclick();
        new RUiObject(new UiSelector().text("打开")).click();
        sleep(1000);
        assertEquals("打开回收站","org.openthos.filemanager",RUiDevice.getCurrentPackageName());
        closewindow();
        rb.rightclick();
        new RUiObject(new UiSelector().text("清空回收站")).click();
        assertTrue("清空回收站",new RUiObject(new UiSelector().text("确认清空回收站?")).exists());
        new RUiObject(new UiSelector().resourceId("android:id/button2")).click();
        sleep(1000);
        assertFalse("否",new RUiObject(new UiSelector().text("确认清空回收站?")).exists());
    }

    private void closewindow() throws UiObjectNotFoundException{
        UiDevice uiDevice= getUiDevice();
        if(!new RUiObject(new UiSelector().resourceId("android:id/mwCloseBtn")).exists()){
            uiDevice.swipe(0,0,2,2,2);
        }
            new RUiObject(new UiSelector().resourceId("android:id/mwCloseBtn")).click();
    }
}
