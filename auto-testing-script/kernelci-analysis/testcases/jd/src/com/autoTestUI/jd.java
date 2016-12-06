package com.autoTestUI;

import java.io.IOException;
import android.os.RemoteException;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

public class jd extends UiAutomatorTestCase {
	public void testjd() throws UiObjectNotFoundException, RemoteException,
			IOException, InterruptedException {
		UiDevice device = getUiDevice();
		// wake up screen
		device.wakeUp();
		assertTrue("screen on :can't wakeup", device.isScreenOn());
		otoDisplayRun.port = "5555";
		otoDisplayRun.apppackage = "com.jingdong.app.mall";
		otoDisplayRun.appactivity = "com.jingdong.app.mall.MainFrameActivity";
		
		String appName = "com.jingdong.app.mall/com.jingdong.app.mall.MainFrameActivity";
		
		Runtime.getRuntime().exec("am start -n " + appName);
		
		boolean dumpFirstStart = new UiObject(new UiSelector().resourceId("com.jingdong.app.mall:id/ao")).exists();
		
		if (dumpFirstStart == true) {
			UiObject stopButton = new UiObject(new UiSelector().resourceId("com.jingdong.app.mall:id/aj"));
			
			stopButton.click();
		}	
		// 强制关闭程序
		otoDisplayRun.execCmdNoSave("am force-stop " + appName.substring(0, appName.indexOf("/")));
		
		window_lib.windowtest(device, appName);
		// start testing itself
		/*
		 * try { Runtime.getRuntime().exec( " am start -n " + appName);
		 * 
		 * } catch (IOException e) { // TODO auto-generated catch block
		 * e.printStackTrace(); } sleep(500);
		 */
	}

	
}
