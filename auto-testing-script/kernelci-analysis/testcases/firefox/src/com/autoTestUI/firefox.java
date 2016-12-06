package com.autoTestUI;

import java.io.IOException;
import android.os.RemoteException;
import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

public class firefox extends UiAutomatorTestCase {
	public void testfirefox() throws UiObjectNotFoundException, RemoteException,
			IOException, InterruptedException{
		UiDevice device = getUiDevice();
		// wake up screen
		device.wakeUp();
		
		assertTrue("screen on :can't wakeup", device.isScreenOn());
		otoDisplayRun.port = "5555";
		otoDisplayRun.apppackage = "org.mozilla.firefox";
		otoDisplayRun.appactivity = "org.mozilla.firefox.App";
		
		String appName = "org.mozilla.firefox/org.mozilla.firefox.App";
		
		window_lib.windowtest(device, appName);
		
		otoDisplayRun.execCmd("am start -n " + appName);
		//open sina.com.cn
		UiObject titleObject=new UiObject(new UiSelector().resourceId("org.mozilla.firefox:id/url_bar_entry"));
		titleObject.click();
		UiDevice.getInstance().pressDelete();
		titleObject.setText("www.sina.com.cn");
		UiDevice.getInstance().pressEnter();
		Thread.sleep(5000);
		// 最大化
		UiObject objectMax = new UiObject(
				new UiSelector().resourceId("android:id/mwMaximizeBtn"));
		objectMax.click();
		Thread.sleep(5000);
		
		// swipe the window
		UiObject swipe10=new UiObject(new UiSelector().resourceId("android:id/content"));
    	swipe10.swipeUp(50);
		sleep(2000);
		
    	swipe10.swipeDown(50);
		sleep(2000);
		/*scrollable 
		UiScrollable scroll=new UiScrollable(new UiSelector().resourceId("android:id/content"));
		scroll.scrollToEnd(10,10);
		scroll.scrollToEnd(10,10);
		*/
		// open www.baidu.com
		UiObject newObject=new UiObject(new UiSelector().resourceId("org.mozilla.firefox:id/add_tab"));
		newObject.click();
		UiObject newtitleObject=new UiObject(new UiSelector().resourceId("org.mozilla.firefox:id/url_bar_entry"));
		newtitleObject.click();
		newtitleObject.setText("www.baidu.com");
		UiDevice.getInstance().pressEnter();
		Thread.sleep(2000);
		//open www.ifeng.com
		UiObject thirdObject=new UiObject(new UiSelector().resourceId("org.mozilla.firefox:id/add_tab"));
		thirdObject.click();
		UiObject ifengObject=new UiObject(new UiSelector().resourceId("org.mozilla.firefox:id/url_bar_entry"));
		ifengObject.click();
		ifengObject.setText("www.ifeng.com");
		UiDevice.getInstance().pressEnter();
		Thread.sleep(1000);
		


		// start testing itself
		/*
		 * try { Runtime.getRuntime().exec( " am start -n " + appName);
		 * 
		 * } catch (IOException e) { // TODO auto-generated catch block
		 * e.printStackTrace(); } sleep(500);
		 */
		// 强制关闭程序
		otoDisplayRun.execCmdNoSave("am force-stop " + appName.substring(0, appName.indexOf("/")));
	}

	
}
