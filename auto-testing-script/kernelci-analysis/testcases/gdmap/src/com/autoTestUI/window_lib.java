package com.autoTestUI;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.RemoteException;
import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

public class window_lib extends UiAutomatorTestCase{
	
	public static void windowtest(UiDevice device, String appName)
			throws UiObjectNotFoundException, RemoteException, IOException, InterruptedException {
 
		Date starttime;
		Date endtime;
		long launchTime;	
		int runCount = 0;
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
		otoDisplayRun.appName =otoDisplayRun.apppackage + "/" + otoDisplayRun.appactivity;
		starttime = new Date();
		System.out.println("-------------Start time： " +  format.format(starttime) + otoDisplayRun.port);
		otoDisplayRun.execCmdNoSave("am start -n " + appName);
 
		String processCmd = "ps  "+"| "+"grep " +otoDisplayRun.apppackage ;
		System.out.println("----Process judgment :  " + processCmd);
		int found = 0;
		while (runCount < 10) {

			if ( otoDisplayRun.execCmdNoSave(processCmd)  == 0) {
			    found = 1;
			    break;
			}
			Thread.sleep(500);
			runCount++;
		}
		
		// otoDisplayRun.execCmd(processCmd) ;
		endtime = new Date();
		if (found == 0) {
			System.out.println("----Time： " +  format.format(endtime) + "app not running ");
		} else {
			System.out.println("-------------结束时间： " +  format.format(endtime));
		}
		launchTime = endtime.getTime() - starttime.getTime();
		System.out.println("-------------APP launch 时间： " + launchTime +"ms");
		
		
		Thread.sleep(3000);
		

		// 改变窗体大小 左上拉动 改变大小
		UiObject objectSide = new UiObject(
				new UiSelector().resourceId("android:id/mwOuterBorder"));
		android.graphics.Rect myAppSide = objectSide.getVisibleBounds();
		device.drag(myAppSide.left, myAppSide.top, myAppSide.left + 100,
				myAppSide.top, 2);
		Thread.sleep(2000);

		// 重新通过resourceId 获取窗口边界坐标，与预计的坐标不想等……
		UiObject objectSide1 = new UiObject(
				new UiSelector().resourceId("android:id/mwOuterBorder"));
		android.graphics.Rect myAppSide1 = objectSide1.getVisibleBounds();
		// 验证上一次拖动是否成功：10 pixcel 的误差
		if (myAppSide1.left < (myAppSide.left + 40)
				|| myAppSide1.left > (myAppSide.left + 60)) {
			System.out.println(myAppSide1.left + "111向左改变窗口大小失败！" + myAppSide.left);
		}
		device.drag(myAppSide1.left, myAppSide1.top, myAppSide1.left - 100,
				myAppSide1.top, 2);
		Thread.sleep(2000);

		// 改变窗体大小 右下拉动 改变大小
		UiObject objectSide2 = new UiObject(
				new UiSelector().resourceId("android:id/mwOuterBorder"));
		android.graphics.Rect myAppSide2 = objectSide2.getVisibleBounds();
		// 验证上一次拖动是否成功：
		if (myAppSide2.left < (myAppSide.left - 10)
				|| myAppSide2.left > (myAppSide.left + 10)) {

			System.out.println("222向右改变窗口大小失败！");
		}
		device.drag(myAppSide2.right - 1, myAppSide2.bottom - 1,
				myAppSide2.right + 100, myAppSide2.bottom + 100, 2);
		Thread.sleep(2000);

		// 重新通过resourceId 获取窗口边界坐标 下面 拖动时，差几个像素点到边界 所以-2
		UiObject objectSide3 = new UiObject(
				new UiSelector().resourceId("android:id/mwOuterBorder"));
		android.graphics.Rect myAppSide3 = objectSide3.getVisibleBounds();
		device.drag(myAppSide3.right - 1, myAppSide3.bottom - 1,
				myAppSide3.right - 100, myAppSide3.bottom - 100, 2);
		Thread.sleep(2000);

		// 最大化
		UiObject objectMax = new UiObject(
				new UiSelector().resourceId("android:id/mwMaximizeBtn"));
		objectMax.click();
		Thread.sleep(1000);
		objectMax.click();
		Thread.sleep(1000);
		// 最小化
		// UiObject objectMin=new UiObject(new
		// UiSelector().resourceId("android:id/mwMinimizeBtn"));
		// objectMin.click();
		// sleep(1000);

		// 关闭程序
		UiObject objectClose = new UiObject(
				new UiSelector().resourceId("android:id/mwCloseBtn"));
		objectClose.click();
		Thread.sleep(1000);
		// 重新启动程序
		otoDisplayRun.execCmdNoSave("am start -n " + appName);

		// 拖动程序 拖动程序后， 窗口最大化/最小化等位置将无法通过resourceId获取到
		UiObject objectHead = new UiObject(
				new UiSelector().resourceId("android:id/mw_decor_header"));
		objectHead.dragTo(1000, 500, 10);
		Thread.sleep(1000);
		// 强制关闭程序
		otoDisplayRun.execCmdNoSave("am force-stop " + appName.substring(0, appName.indexOf("/")));

		device.pressHome();
	}
}
