package com.autoTestUI;

import java.io.IOException;
import android.os.RemoteException;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

public class calc extends UiAutomatorTestCase {
	public void testcalc() throws UiObjectNotFoundException, RemoteException,
			IOException, InterruptedException {
		UiDevice device = getUiDevice();
		// wake up screen
		device.wakeUp();
		assertTrue("screen on :can't wakeup", device.isScreenOn());
		
		
		otoDisplayRun.port = "5555";
		otoDisplayRun.apppackage = "com.android.calculator2";
		otoDisplayRun.appactivity = "com.android.calculator2.Calculator";

		String appName = "com.android.calculator2/com.android.calculator2.Calculator";

		window_lib.windowtest(device, appName);
		// start testing itself
		
		try { 
			Runtime.getRuntime().exec( " am start -n " + appName);
		
		UiObject buttonNum1 = new UiObject(
				new UiSelector().resourceId("com.android.calculator2:id/digit_1"));
		UiObject buttonNum5 = new UiObject(
				new UiSelector().resourceId("com.android.calculator2:id/digit_5"));
		UiObject buttonAdd = new UiObject(
				new UiSelector().resourceId("com.android.calculator2:id/op_add"));
		UiObject buttonMul = new UiObject(
				new UiSelector().resourceId("com.android.calculator2:id/op_mul"));
		UiObject buttonLparen = new UiObject(
				new UiSelector().resourceId("com.android.calculator2:id/lparen"));
		UiObject buttonRparen = new UiObject(
				new UiSelector().resourceId("com.android.calculator2:id/rparen"));
		
		// 简单测试下  (1 + 1) x 5 x 5 =  50
		buttonLparen.click();
		sleep(600);
		buttonNum1.click();
		sleep(600);		
		buttonAdd.click();
		sleep(600);
		buttonNum1.click();
		sleep(600);
		buttonRparen.click();
		sleep(600);
		
		buttonMul.click();
		sleep(600);
		buttonNum5.click();
		sleep(600);
		
		buttonMul.click();
		sleep(600);
		buttonNum5.click();
		sleep(600);
		
		UiObject resultWindow = new UiObject(
				new UiSelector().resourceId("com.android.calculator2:id/result"));
		
		String result = resultWindow.getText();
		
		if (result.compareTo("50") == 0) {
			System.out.println("testing calc pass!!!!" + result);
		} else {
			System.out.println("testing calc fail!!!!" + result);
		}

		UiObject objectClose = new UiObject(
					new UiSelector().resourceId("android:id/mwCloseBtn"));
		
		objectClose.click();
		Thread.sleep(1000);
		  
		} catch (IOException e) { // TODO auto-generated catch block
			e.printStackTrace(); 
		} 
		sleep(500);
		// 强制关闭程序
				otoDisplayRun.execCmdNoSave("am force-stop " + appName.substring(0, appName.indexOf("/")));

		
	}

	
}
