package com.autoTestUI;

import java.io.IOException;
import android.os.RemoteException;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

public class termux extends UiAutomatorTestCase {
	public void testtermux() throws UiObjectNotFoundException, RemoteException,
			IOException, InterruptedException {
		UiDevice device = getUiDevice();
		// wake up screen
		device.wakeUp();
		assertTrue("screen on :can't wakeup", device.isScreenOn());
		otoDisplayRun.port = "5555";
		otoDisplayRun.apppackage = "com.termux";
		otoDisplayRun.appactivity = ".app.TermuxActivity";
		
		String appName = "com.termux/.app.TermuxActivity";
		
		Runtime.getRuntime().exec("am start -n " + appName);
		Thread.sleep(5000);
		
		boolean dumpFirstStart = new UiObject(new UiSelector().resourceId("android:id/alertTitle")).exists();
		
		if (dumpFirstStart == true) {
			UiObject sureButton = new UiObject(new UiSelector().resourceId("android:id/button2"));
			
			sureButton.click();
		}	
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
