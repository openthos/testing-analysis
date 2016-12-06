package com.autoTestUI;

import java.io.IOException;
import android.os.RemoteException;
import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

public class fm extends UiAutomatorTestCase {
	public void testfm() throws UiObjectNotFoundException, RemoteException,
			IOException, InterruptedException{
		UiDevice device = getUiDevice();
		// wake up screen
		device.wakeUp();
		assertTrue("screen on :can't wakeup", device.isScreenOn());
		
		otoDisplayRun.port = "5555";
		otoDisplayRun.apppackage = "com.ximalaya.ting.android";
		otoDisplayRun.appactivity = "com.ximalaya.ting.android.activity.MainActivity";
		
		String appName = "com.ximalaya.ting.android/com.ximalaya.ting.android.activity.MainActivity";

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
