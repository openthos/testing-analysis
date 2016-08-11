package com.browser;

import java.io.*;
import android.os.RemoteException;
import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
//import com.android.uiautomator.core.UiScrollable;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

public class demobrowser extends UiAutomatorTestCase {

		public void testbrowser() throws UiObjectNotFoundException,RemoteException{
			UiDevice device = getUiDevice();
			// wake up screen
			device.wakeUp();
			assertTrue("screen on :can't wakeup", device.isScreenOn());
			// return home
			device.pressHome();
			sleep(1000);
			
			// start browser
			try {
				Runtime.getRuntime().exec(
						"am start -n com.android.browser/.BrowserActivity");
			} catch (IOException e) {
				// TODO auto-generated catch block
				e.printStackTrace();
			}
			sleep(1000);
			
			//clean and input www.baidu.com
			UiObject editObject=new UiObject(new UiSelector().className("android.widget.EditText"));
			editObject.click();
			UiDevice.getInstance().pressDelete();
			editObject.setText("www.baidu.com");
			UiDevice.getInstance().pressEnter();
			sleep(2000);
			
			UiObject maxObject=new UiObject(new UiSelector().resourceId("android:id/mwMaximizeBtn"));
			maxObject.click();
			sleep(1000);
			UiObject huanyuan=new UiObject(new UiSelector().resourceId("android:id/mwMaximizeBtn"));
			huanyuan.click();
			sleep(1000);
			
			
			
			//dragto bottom
			//UiScrollable listScrollable = new UiScrollable(new UiSelector().scrollable(true));
			//listScrollable.flingToEnd(3);
			
			 // UiScrollable listScrollable = new UiScrollable(new UiSelector().className("android.widget.ListView"));
			 // listScrollable.setAsVerticalList();
			 // sleep(2000);
			
			//UiScrollable scroll=new UiScrollable(new UiSelector().className("android.widget.ListView"));
			//scroll.flingBackward();
			//scroll.flingForward();
			//scroll.flingToBeginning(20);
			//scroll.flingToEnd(300);
			//sleep (2000);
		
		}
		
		public static void main (String[] args ) {
			String jarName="demo1";
			String testClass="com.browser.demobrowser";
			String testName="testbrowser";
			String androidId="4";
			new UiAutomatorHelper(jarName, testClass, testName, androidId);
			
		}
		
}
