package com.autoTestUI;

import java.io.IOException;
import android.os.RemoteException;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

public class email extends UiAutomatorTestCase {
	static String objStr[][] = {
		{"Personal (POP3)", "个人(POP3)"},
		{"Next", "下一步"}
	};
	public void testemail() throws UiObjectNotFoundException, RemoteException,
			IOException, InterruptedException {
		UiDevice device = getUiDevice();
		// wake up screen
		device.wakeUp();
		assertTrue("screen on :can't wakeup", device.isScreenOn());

		otoDisplayRun.port = "5555";
		otoDisplayRun.apppackage = "com.android.email";
		otoDisplayRun.appactivity = "com.android.email.activity.Welcome";
		String appName = "com.android.email/com.android.email.activity.Welcome";

		window_lib.windowtest(device, appName);
		
		otoDisplayRun.execCmdNoSave(
				" am start -n " + appName);

		//获取语言
		UiObject title = new UiObject(
				new UiSelector().resourceId("android:id/mwTitle"));
		
		int language = 0;//0: en  1:cn
		
		String langStr = title.getText();
		System.out.println(langStr);
		if (langStr.equals("Email")) {
			language = 0;
		} else {
			language = 1;
		}
		System.out.println(langStr + language);
		// input  email accout
		UiObject eaddress = new UiObject(new UiSelector().className("android.widget.EditText"));
		eaddress.longClick();
		eaddress.setText("asptest@126.com");
		sleep(500);
		
		//UiObject button1 = new UiObject(new UiSelector().text("Next"));
		UiObject button1 = new UiObject(
				new UiSelector().resourceId("com.android.email:id/next"));
		button1.click();
		sleep(500);
		
		UiObject button2 = new UiObject(new UiSelector().text(objStr[0][language]));
		button2.click();
		sleep(500);
		//input passwd and click Next
		UiObject epasswd = new UiObject(new UiSelector().resourceId("com.android.email:id/setup_fragment_content"));
		epasswd.longClick();
		epasswd.setText("abc123");
		sleep(500);
		UiObject button3 = new UiObject(new UiSelector().text(objStr[1][language]));
		button3.click();
		sleep(500);
		
		//modify pop server
		UiObject popserver = new UiObject(new UiSelector().text("126.com"));
		//if (popserver.exists() && popserver.isEnabled())
		//{
		//String text = popserver.getText();
		sleep(500);
		popserver.longClick();
		sleep(500);
		popserver.setText("pop.126.com");
	//	}
		sleep(1000);
		UiObject button4 = new UiObject(new UiSelector().resourceId("com.android.email:id/next"));
		button4.click();
		sleep(5000);
		//modify smtp server
		UiObject smtpserver = new UiObject(new UiSelector().resourceId("com.android.email:id/account_server"));
		if (smtpserver.exists() && smtpserver.isEnabled())
		{
			popserver.longClick();
			sleep(500);
			smtpserver.setText("smtp.126.com");
			sleep(500);
		}
		UiObject smtpport = new UiObject(new UiSelector().resourceId("com.android.email:id/account_port"));
		smtpport.longClick();
		smtpport.setText("25");
		sleep(500);
		UiObject button5 = new UiObject(new UiSelector().text(objStr[1][language]));
		button5.click();
		sleep(1000);
		
		//click Next to well done
		UiObject button6 = new UiObject(new UiSelector().text(objStr[1][language]));
		button6.click();
		
		sleep(1000);
		UiObject button7 = new UiObject(new UiSelector().text(objStr[1][language]));
		button7.click();
		
		UiObject objectClose = new UiObject(
				new UiSelector().resourceId("android:id/mwCloseBtn"));
		objectClose.click();
		Thread.sleep(1000);
		
		// 强制关闭程序
		otoDisplayRun.execCmdNoSave("am force-stop " + appName.substring(0, appName.indexOf("/")));
	
	}
}
