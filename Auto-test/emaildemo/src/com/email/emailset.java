package com.email;

import java.io.IOException;

import java.*;
import android.os.RemoteException;
import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiScrollable;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;


public class emailset extends UiAutomatorTestCase {
	public void testemail() throws UiObjectNotFoundException,RemoteException{
		UiDevice device = getUiDevice();
		// wake up screen
		device.wakeUp();
		assertTrue("screen on :can't wakeup", device.isScreenOn());
	
		// start browser
		try {
			Runtime.getRuntime().exec(
					" am start -n com.android.email/com.android.email.activity.Welcome");
		} catch (IOException e) {
			// TODO auto-generated catch block
			e.printStackTrace();
		}
		sleep(500);
	
		// input  email accout
		UiObject eaddress = new UiObject(new UiSelector()	.className("android.widget.EditText"));
		eaddress.click();
		eaddress.setText("asptest@126.com");
		sleep(500);
		
		UiObject button1 = new UiObject(new UiSelector().text("Next"));
		button1.click();
		sleep(500);
		
		UiObject button2 = new UiObject(new UiSelector().text("Personal (POP3)"));
		button2.click();
		sleep(500);
		//input passwd and click Next
		UiObject epasswd = new UiObject(new UiSelector().resourceId("com.android.email:id/setup_fragment_content"));
		epasswd.click();
		epasswd.setText("abc123");
		sleep(500);
		UiObject button3 = new UiObject(new UiSelector().text("Next"));
		button3.click();
		sleep(500);
		
		//modify pop server
		UiObject popserver = new UiObject(new UiSelector().text("126.com"));
		//if (popserver.exists() && popserver.isEnabled())
		//{
		//String text = popserver.getText();
		popserver.clickTopLeft();
		//for (int i=0;i<text.length();i++) {
		//	    UiDevice.getInstance().pressDelete();
		//}
		sleep(500);
		popserver.setText("pop.126.com");
	//	}
		sleep(1000);
		UiObject button4 = new UiObject(new UiSelector().resourceId("com.android.email:id/next"));
		button4.click();
		sleep(500);
		//modify smtp server
		UiObject smtpserver = new UiObject(new UiSelector().resourceId("com.android.email:id/account_server"));
		if (smtpserver.exists() && smtpserver.isEnabled())
		{
			popserver.clickTopLeft();
			sleep(500);
			smtpserver.setText("smtp.126.com");
		}
		UiObject smtpport = new UiObject(new UiSelector().resourceId("com.android.email:id/account_port"));
		smtpport.setText("25");
		UiObject button5 = new UiObject(new UiSelector().text("Next"));
		button5.click();
		sleep(1000);
		
		//click Next to well done
		UiObject button6 = new UiObject(new UiSelector().text("Next"));
		button6.click();
		
		sleep(1000);
		UiObject button7 = new UiObject(new UiSelector().text("Next"));
		button7.click();
	}
	

}
