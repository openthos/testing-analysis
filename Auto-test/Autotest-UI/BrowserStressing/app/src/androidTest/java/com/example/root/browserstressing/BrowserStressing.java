package com.example.root.browserstressing;

import android.support.test.uiautomator.UiAutomatorTestCase;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.util.Log;
import android.widget.RelativeLayout;

import java.io.IOException;

/**
 * Created by root on 18-7-13.
 */

public class BrowserStressing extends UiAutomatorTestCase {
    String[] urllist = {"https://www.baidu.com/",
    "http://news.baidu.com/",
    "https://map.baidu.com/",
    "http://youku.com/",
    "https://www.bilibili.com/",
    "https://www.bilibili.com/video/av25612343/",
    "https://weibo.com/",
    "https://www.xiami.com/play?ids=/song/playlist/id/1/type/9#loaded",
    "https://www.panda.tv/"};

    public void test1_Preparation() throws UiObjectNotFoundException, IOException {
        UiDevice uiDevice = getUiDevice();
        uiDevice.executeShellCommand("am start -n com.android.settings/.Settings");
        sleep(2000);
        new UiObject(new UiSelector().text("应用")).click();
        new UiObject(new UiSelector().text("正在运行")).click();
        new UiObject(new UiSelector().text("全部")).click();
        UiScrollable  appList = new UiScrollable(new UiSelector().resourceId("android:id/list"));
        appList.getChildByText(new UiSelector().className("android.widget.GridLayout"), "Openthos浏览器", true).click();
        new UiObject(new UiSelector().text("清除数据")).click();
        new UiObject(new UiSelector().text("确定")).click();
        new UiObject(new UiSelector().resourceId("android:id/mwCloseBtn")).click();
        sleep(2000);
    }

    public void test2_Preparation() throws UiObjectNotFoundException, IOException {
        UiDevice uiDevice = getUiDevice();

        uiDevice.executeShellCommand("am start -n org.mozilla.fennec_openthos/org.mozilla.gecko.BrowserApp");
        sleep(2000);
        new UiObject(new UiSelector().resourceId("org.mozilla.fennec_openthos:id/tablet_add_tab")).click();
        for (int i=0;i<urllist.length;i++){
            new UiObject(new UiSelector().resourceId("org.mozilla.fennec_openthos:id/tablet_add_tab")).click();
            new UiObject(new UiSelector().resourceId("org.mozilla.fennec_openthos:id/url_bar_title")).click();
            new UiObject(new UiSelector().resourceId("org.mozilla.fennec_openthos:id/url_edit_text")).setText(urllist[i]);
            uiDevice.pressEnter();
        }

    }
}
