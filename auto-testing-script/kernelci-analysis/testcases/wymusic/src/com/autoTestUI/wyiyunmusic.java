package com.autoTestUI;

import java.io.IOException;
import android.os.RemoteException;
import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

public class wyiyunmusic extends UiAutomatorTestCase {
	public void testwyiyunmusic() throws UiObjectNotFoundException, RemoteException,
			IOException, InterruptedException {
		UiDevice device = getUiDevice();
		// wake up screen
		device.wakeUp();
		assertTrue("screen on :can't wakeup", device.isScreenOn());
		otoDisplayRun.port = "5555";
		otoDisplayRun.apppackage = "com.netease.cloudmusic";
		otoDisplayRun.appactivity = "com.netease.cloudmusic.activity.LoadingActivity";
		
		String appName = "com.netease.cloudmusic/com.netease.cloudmusic.activity.LoadingActivity";
		window_lib.windowtest(device, appName);
		
		otoDisplayRun.execCmdNoSave("am start -n " + appName);
		UiObject denglu=new UiObject(new UiSelector().resourceId("com.netease.cloudmusic:id/loginOrRegister"));
		denglu.click();
		sleep(2000);
		
		UiObject phone=new UiObject(new UiSelector().resourceId("com.netease.cloudmusic:id/login"));
		phone.clickAndWaitForNewWindow();
		
		UiObject username=new UiObject(new UiSelector().resourceId("com.netease.cloudmusic:id/phoneNumber"));
		username.click();
		username.setText("18710140362");
		sleep(200);
		
		UiObject passwd=new UiObject(new UiSelector().resourceId("com.netease.cloudmusic:id/password"));
		passwd.click();
		passwd.setText("qwer1234");
		sleep(200);	
		
		UiObject btn=new UiObject(new UiSelector().resourceId("com.netease.cloudmusic:id/login"));
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
