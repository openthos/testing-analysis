package com.autotest.apptest;

import android.support.test.uiautomator.UiAutomatorTestCase;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class otoDisplayRun extends UiAutomatorTestCase {

    UiDevice mydevice;
    otoDisplayRun(UiDevice device){
        mydevice = device;
    }

    final int CLICK_ID = 1111;
    final int CLICK_TEXT = 2222;

    public boolean ClickById(String id) throws UiObjectNotFoundException{
        return ClickByInfo(CLICK_ID,id);
    }

    public void MoveToTop() throws UiObjectNotFoundException {
        SolveProblems();
        UiObject objectSide4 = new UiObject( new UiSelector().resourceId("android:id/mwOuterBorder"));
        android.graphics.Rect myAppSide4 = objectSide4.getVisibleBounds();
        sleep(1000);
        mydevice.click(100, myAppSide4.top);
    }

    private boolean ClickByInfo(int CLICK,String str) throws UiObjectNotFoundException{
        UiSelector uiselector = null;
        switch(CLICK)
        {
            case CLICK_ID:
                uiselector = new UiSelector().resourceId(str);
                break;
            case CLICK_TEXT:
                uiselector = new UiSelector().text(str);
                break;
            default:
                return false;
        }
        UiObject myobject = new UiObject(uiselector);
        int i = 0;
        while(!myobject.exists() && i < 5){
			SolveProblems();
            sleep(1000);
            if(i == 4){
                TakeScreen(str.substring(str.indexOf('/')+1)+"----not find");
                System.out.println("----------[failed]:" + str + " not find !!!测试未通过");
                return false;
            }
            i++;
        }
        try {
            myobject.click();
        } catch (UiObjectNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return true;
    }

	private void SolveProblems() throws UiObjectNotFoundException{
        sleep(3000);
        if(new UiObject(new UiSelector().text("接受")).exists()) {
            new UiObject(new UiSelector().text("接受")).click();
        }
        if(new UiObject(new UiSelector().text("是")).exists()){
            new UiObject(new UiSelector().text("是")).click();
        }
        if(new UiObject(new UiSelector().text("确定")).exists()) {
            new UiObject(new UiSelector().text("确定")).click();
        }
        if(new UiObject(new UiSelector().text("下次再说")).exists()) {
            new UiObject(new UiSelector().text("下次再说")).click();
        }
        if(new UiObject(new UiSelector().text("忽略此版本")).exists()) {
            new UiObject(new UiSelector().text("忽略此版本")).click();
        }
        if(new UiObject(new UiSelector().text("以后再说")).exists()) {
            new UiObject(new UiSelector().text("以后再说")).click();
        }
        if(new UiObject(new UiSelector().text("稍后")).exists()) {
            new UiObject(new UiSelector().text("稍后")).click();
        }
        if(new UiObject(new UiSelector().text("同意")).exists()) {
            new UiObject(new UiSelector().text("同意")).click();
        }
	}

    public void TakeScreen(String descript){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        Date time = new Date();
        String timestr = format.format(time);
        File files = new File("/sdcard/Pictures/"+timestr+"_"+descript+".jpg");
        mydevice.takeScreenshot(files);
    }

    public void Windowtest(String appName,long waittime)
            throws UiObjectNotFoundException, IOException, InterruptedException {

        // 改变窗体大小 将左边框向右侧拉动 改变大小
        if(!new UiObject(new UiSelector().resourceId("android:id/mwOuterBorder")).exists()){
            SolveProblems();
        }
        UiObject objectSide = new UiObject(new UiSelector().resourceId("android:id/mwOuterBorder"));
        android.graphics.Rect myAppSide = objectSide.getVisibleBounds();
        mydevice.drag(myAppSide.left, myAppSide.top, myAppSide.left + 100,myAppSide.top, 2);
        Thread.sleep(waittime);

        // 重新通过resourceId 获取窗口边界坐标，与预计的坐标不相等
        UiObject objectSide1 = new UiObject(new UiSelector().resourceId("android:id/mwOuterBorder"));
        android.graphics.Rect myAppSide1 = objectSide1.getVisibleBounds();
        // 验证上一次拖动是否成功：10 pixcel 的误差
        if (myAppSide1.left < (myAppSide.left + 40)|| myAppSide1.left > (myAppSide.left + 60)) {
            System.out.println("----------[failed]:"+myAppSide.left+"->"+myAppSide1.left + "拖动左边框向右改变窗口大小失败！");
        }

        // 改变窗体大小 将左边框向左拉动 改变大小
        mydevice.drag(myAppSide1.left, myAppSide1.top, myAppSide1.left - 100,myAppSide1.top, 2);
        Thread.sleep(waittime);

        // 重新通过resourceId 获取窗口边界坐标
        UiObject objectSide2 = new UiObject(new UiSelector().resourceId("android:id/mwOuterBorder"));
        android.graphics.Rect myAppSide2 = objectSide2.getVisibleBounds();
        // 验证上一次拖动是否成功
        if (myAppSide2.left < (myAppSide.left - 10)|| myAppSide2.left > (myAppSide.left + 10)) {
            System.out.println("----------[failed]:"+myAppSide2.left+"->"+myAppSide.left+"拖动左边框向左改变窗口大小失败！");
        }

        //拖动右下角，扩大窗口
        mydevice.drag(myAppSide2.right - 1, myAppSide2.bottom - 1,myAppSide2.right + 100, myAppSide2.bottom + 100, 2);
        Thread.sleep(waittime);

        // 重新通过resourceId 获取窗口边界坐标 下面拖动时，差几个像素点到边界 所以-2
        UiObject objectSide3 = new UiObject(new UiSelector().resourceId("android:id/mwOuterBorder"));
        android.graphics.Rect myAppSide3 = objectSide3.getVisibleBounds();
        // 验证上一次拖动是否成功
        if (myAppSide3.right < (myAppSide2.right + 40)|| myAppSide3.right > (myAppSide2.right + 60)) {
            System.out.println("----------[failed]:"+myAppSide2.right+"->"+myAppSide3.right+"拖拽右下角扩大窗口失败！");
        }

        mydevice.drag(myAppSide3.right - 1, myAppSide3.bottom - 1,myAppSide3.right - 100, myAppSide3.bottom - 100, 2);
        Thread.sleep(waittime);

        // 拖动程序 拖动程序后， 窗口最大化/最小化等位置将无法通过resourceId获取到
        UiObject objectHead = new UiObject(new UiSelector().resourceId("android:id/mw_decor_header"));
        objectHead.dragTo(1000, 500, 10);
        Thread.sleep(waittime);

        // 强制关闭程序
        mydevice.executeShellCommand("am force-stop " + appName.substring(0, appName.indexOf("/")));
    }

    public boolean LaunchTime (String appName,long waittime) throws IOException {
        Date starttime;
        Date endtime;
        long launchTime;
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");

        starttime = new Date();
        System.out.println("----------Start time： " +  format.format(starttime));
        System.out.println("starttime:" +  System.currentTimeMillis());
        mydevice.executeShellCommand("am start -n " + appName);
        sleep(waittime);
        /*String windowinfo = mydevice.executeShellCommand("dumpsys window w | grep name=" + appName);
        System.out.println(windowinfo);
        System.out.println(windowinfo.substring(windowinfo.indexOf("name="),1));
        assertEquals(appName,windowinfo.substring(windowinfo.indexOf("name="),1));*/

        endtime = new Date();
        System.out.println("----------结束时间： " +  format.format(endtime));
        System.out.println("endtime:" +  System.currentTimeMillis());

        launchTime = endtime.getTime() - starttime.getTime();
        System.out.println("----------APP launch 时间： " + launchTime +"ms");
        sleep(2000);
        return true;
    }

}
