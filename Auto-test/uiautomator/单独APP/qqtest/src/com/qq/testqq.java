package com.qq;

import java.io.*;

import android.os.RemoteException;
import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
//import com.android.uiautomator.core.UiScrollable;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

public class testqq extends UiAutomatorTestCase {

	public void testemail() throws UiObjectNotFoundException,RemoteException{
		UiDevice device = getUiDevice();
		// wake up screen
		device.wakeUp();
		assertTrue("screen on :can't wakeup", device.isScreenOn());
	
		// start browser
		try {
			Runtime.getRuntime().exec(
					" am start -n com.tencent.mobileqq/com.tencent.mobileqq.activity.RegisterGuideActivity");
					sleep(2000);
		} catch (IOException e) {
			// TODO auto-generated catch block
			e.printStackTrace();
		}
		sleep(500);
		UiObject denglu=new UiObject(new UiSelector().resourceId("com.tencent.mobileqq:id/btn_login"));
		denglu.clickAndWaitForNewWindow();
		sleep(200);
		
		UiObject username=new UiObject(new UiSelector().text("QQ号/手机号/邮箱"));
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
				
	
	}
}
