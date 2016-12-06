package com.autoTestUI;

import java.io.IOException;
import android.os.RemoteException;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

public class xiecheng extends UiAutomatorTestCase {
	public void testxiecheng() throws UiObjectNotFoundException, RemoteException,
			IOException, InterruptedException {
		UiDevice device = getUiDevice();
		// wake up screen
		device.wakeUp();
		assertTrue("screen on :can't wakeup", device.isScreenOn());

		otoDisplayRun.port = "5555";
		otoDisplayRun.apppackage = "ctrip.android.view";
		otoDisplayRun.appactivity = "ctrip.android.view.splash.CtripSplashActivity";
		
		String appName = "ctrip.android.view/ctrip.android.view.splash.CtripSplashActivity";

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