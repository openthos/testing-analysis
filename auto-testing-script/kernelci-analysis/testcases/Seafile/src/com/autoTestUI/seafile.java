package com.autoTestUI;

import java.io.IOException;
import android.os.RemoteException;
import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

public class seafile extends UiAutomatorTestCase {
	public void testseafile() throws UiObjectNotFoundException, RemoteException,
			IOException, InterruptedException{
		UiDevice device = getUiDevice();
		// wake up screen
		device.wakeUp();
		assertTrue("screen on :can't wakeup", device.isScreenOn());
		otoDisplayRun.port = "5555";
		otoDisplayRun.apppackage = "com.seafile.seadroid2";
		otoDisplayRun.appactivity = "com.seafile.seadroid2.ui.activity.BrowserActivity";
		
		String appName = "com.seafile.seadroid2/com.seafile.seadroid2.ui.activity.BrowserActivity";
		window_lib.windowtest(device, appName);
		
		otoDisplayRun.execCmd("am start -n " + appName);
		
		UiObject account=new UiObject(new UiSelector().resourceId("com.seafile.seadroid2:id/account_footer_btn"));
		account.clickAndWaitForNewWindow();

		UiObject text1=new UiObject(new UiSelector().resourceId("android:id/text1"));
		text1.clickAndWaitForNewWindow();
		
		//modify seacloud.cc
		UiObject seacloud = new UiObject(new UiSelector().resourceId("com.seafile.seadroid2:id/server_url"));
		seacloud.longClick();
		UiDevice.getInstance().pressDelete();
		sleep(500);
		seacloud.setText("https://dev.openthos.org/");
		
		//username
		UiObject emailaddr =new UiObject(new UiSelector().resourceId("com.seafile.seadroid2:id/email_address"));
		emailaddr.click();
		emailaddr.setText("asptest@126.com");
		sleep(500);
		
		//passwd
		UiObject passwd =new UiObject(new UiSelector().resourceId("com.seafile.seadroid2:id/password"));
		passwd.click();
		passwd.setText("abc123");
		sleep(500);
		
		UiObject loginbtn= new UiObject(new UiSelector().resourceId("com.seafile.seadroid2:id/login_button"));
		loginbtn.click();
		sleep(1000);
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
