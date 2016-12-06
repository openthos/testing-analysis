package com.autoTestUI;

import java.io.IOException;
import android.os.RemoteException;
import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

public class qq extends UiAutomatorTestCase {
	public void testqq() throws UiObjectNotFoundException, RemoteException,
			IOException, InterruptedException{
		UiDevice device = getUiDevice();
		// wake up screen
		device.wakeUp();
		assertTrue("screen on :can't wakeup", device.isScreenOn());

		otoDisplayRun.port = "5555";
		otoDisplayRun.apppackage = "com.tencent.mobileqq";
		otoDisplayRun.appactivity = "com.tencent.mobileqq.activity.RegisterGuideActivity";
		
		String appName = "com.tencent.mobileqq/com.tencent.mobileqq.activity.RegisterGuideActivity";
		
		window_lib.windowtest(device, appName);
		
		otoDisplayRun.execCmdNoSave("am start -n " + appName);
	
		//sign in by username and passwd
		UiObject denglu=new UiObject(new UiSelector().resourceId("com.tencent.mobileqq:id/btn_login"));
		denglu.clickAndWaitForNewWindow();
		sleep(200);
		
		UiObject username=new UiObject(new UiSelector().className("android.widget.EditText"));
		username.click();
		username.setText("3050840977");
		sleep(200);
		
		UiObject passwd=new UiObject(new UiSelector().resourceId("com.tencent.mobileqq:id/password"));
		passwd.click();
		passwd.setText("abc123");
		sleep(200);	
		
		UiObject btn=new UiObject(new UiSelector().resourceId("com.tencent.mobileqq:id/login"));
		btn.click();
		sleep(2000);	

		// 强制关闭程序
		otoDisplayRun.execCmdNoSave("am force-stop " + appName.substring(0, appName.indexOf("/")));
		
		// start testing itself
		/*
		 * try { Runtime.getRuntime().exec( " am start -n " + appName);
		 * 
		 * } catch (IOException e) { // TODO auto-generated catch block
		 * e.printStackTrace(); } sleep(500);
		 */
	}

	
}
