package com.autoTestUI;

import java.io.IOException;
import android.os.RemoteException;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

public class wpspro extends UiAutomatorTestCase {
	public void testwpspro() throws UiObjectNotFoundException, RemoteException,
			IOException, InterruptedException {
		UiDevice device = getUiDevice();
		// wake up screen
		device.wakeUp();
		assertTrue("screen on :can't wakeup", device.isScreenOn());
		otoDisplayRun.port = "5555";
		otoDisplayRun.apppackage = "com.kingsoft.moffice_pro";
		otoDisplayRun.appactivity = "cn.wps.moffice.main.local.home.PadHomeActivity";
				
		String appName = "com.kingsoft.moffice_pro/cn.wps.moffice.main.local.home.PadHomeActivity";
		
		Runtime.getRuntime().exec("am start -n " + appName);
		Thread.sleep(6000);
		
		boolean dumpFirstStart = new UiObject(new UiSelector().resourceId("com.kingsoft.moffice_pro:id/dialog_title")).exists();
		
		if (dumpFirstStart == true) {
			UiObject sureButton = new UiObject(new UiSelector().text("Try Now"));
			
			sureButton.click();
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
