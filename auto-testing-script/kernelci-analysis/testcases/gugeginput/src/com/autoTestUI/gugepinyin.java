package com.autoTestUI;

import java.io.IOException;
import android.os.RemoteException;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

public class gugepinyin extends UiAutomatorTestCase {
	public void testgugepinyin() throws UiObjectNotFoundException, RemoteException,
			IOException, InterruptedException {
		UiDevice device = getUiDevice();
		// wake up screen
		device.wakeUp();
		assertTrue("screen on :can't wakeup", device.isScreenOn());

		otoDisplayRun.port = "5555";
		otoDisplayRun.apppackage = "com.google.android.inputmethod.pinyin";
		otoDisplayRun.appactivity = "com.google.android.apps.inputmethod.libs.framework.core.LauncherActivity";
		
		String appName = "com.google.android.inputmethod.pinyin/com.google.android.apps.inputmethod.libs.framework.core.LauncherActivity";

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
