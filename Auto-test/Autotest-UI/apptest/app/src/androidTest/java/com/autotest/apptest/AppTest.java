package com.autotest.apptest;

import android.os.RemoteException;
import android.support.test.uiautomator.UiAutomatorTestCase;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;

import java.io.IOException;

public class AppTest extends UiAutomatorTestCase {
    public void test00_prepare() throws RemoteException, UiObjectNotFoundException, InterruptedException, IOException {
        getUiDevice().executeShellCommand("service call audio 4 i32 3 i32 1 i32 1"); //将媒体音量调到1
    }

    public void test01_filemanager() throws RemoteException, UiObjectNotFoundException, InterruptedException, IOException {
        normalwindow("org.openthos.filemanager/.MainActivity", "",3000,0);
    }

    public void test02_settings() throws RemoteException, UiObjectNotFoundException, InterruptedException, IOException {
        normalwindow("com.android.settings/com.android.settings.Settings", "",3000,0);
    }

    public void test03_appstore() throws RemoteException, UiObjectNotFoundException, InterruptedException, IOException {
        normalwindow("org.openthos.appstore/.MainActivity", "",3000,0);
    }

    public void test04_calculator() throws RemoteException, UiObjectNotFoundException, InterruptedException, IOException {
        normalwindow("com.android.calculator2/com.android.calculator2.Calculator", "",3000,0);
    }

    public void test05_clock() throws RemoteException, UiObjectNotFoundException, InterruptedException, IOException {
        normalwindow("com.android.deskclock/com.android.deskclock.DeskClock", "",3000,0);
    }

    public void test07_email() throws RemoteException, UiObjectNotFoundException, InterruptedException, IOException {
        normalwindow("com.android.email/com.android.email.activity.Welcome", "",3000,0);
    }

    public void test08_vlc() throws RemoteException, UiObjectNotFoundException, InterruptedException, IOException {
        normalwindow("org.videolan.vlc/org.videolan.vlc.StartActivity", "",3000,0);
    }

    public void test09_fotofinder() throws RemoteException, UiObjectNotFoundException, InterruptedException, IOException {
        normalwindow("de.k3b.android.androFotoFinder/.FotoGalleryActivity", "",3000,0);
    }

    public void test10_editor() throws RemoteException, UiObjectNotFoundException, InterruptedException, IOException {
        normalwindow("org.openthos.editor/.ui.MainActivity", "",3000,0);
    }

    public void test11_privacyman() throws RemoteException, UiObjectNotFoundException, InterruptedException, IOException {
        normalwindow("org.openthos.privacyman/.AppOpsActivity", "",3000,0);
    }

    public void test12_taskmanager() throws RemoteException, UiObjectNotFoundException, InterruptedException, IOException {
        normalwindow("org.openthos.taskmanager/.MainActivity", "",3000,0);
    }

    public void test13_soundrecoder() throws RemoteException, UiObjectNotFoundException, InterruptedException, IOException {
        normalwindow("org.openthos.soundrecorder/.activities.MainActivity", "",3000,0);
    }

    public void test14_ota() throws RemoteException, UiObjectNotFoundException, InterruptedException, IOException {
        normalwindow("org.openthos.ota/.MainActivity", "",3000,0);
    }

    public void test15_mopria() throws RemoteException, UiObjectNotFoundException, InterruptedException, IOException {
        normalwindow("org.mopria.printplugin/.MopriaInfoActivity","", 3000,0);
    }

    public void test16_outlook() throws RemoteException, UiObjectNotFoundException, InterruptedException, IOException {
        normalwindow("com.microsoft.office.outlook/com.microsoft.office.outlook.MainActivity","", 3000,0);
    }

    public void test18_autocad() throws RemoteException, UiObjectNotFoundException, InterruptedException, IOException {
        normalwindow("com.autodesk.autocadws/.view.activities.StartupActivity","autoCAD.apk", 30000,1);
    }

    public void test19_wpsemail() throws RemoteException, UiObjectNotFoundException, InterruptedException, IOException {
        normalwindow("com.kingsoft.email/com.kingsoft.email.activity.setup.AccountSetupBasics", "wpsmail.apk",3000,1);
    }

    public void test20_evernote() throws RemoteException, UiObjectNotFoundException, InterruptedException, IOException {
        normalwindow("com.evernote/.ui.HomeActivity", "evernote.apk",20000,1);
    }

    public void test21_monitrade() throws RemoteException, UiObjectNotFoundException, InterruptedException, IOException {
        normalwindow("com.hexin.plat.monitrade/com.hexin.plat.android.AndroidLogoActivity", "monichaogu.apk",3000,1);
    }

    public void test22_osmonitor() throws RemoteException, UiObjectNotFoundException, InterruptedException, IOException {
        normalwindow("com.eolwral.osmonitor/com.eolwral.osmonitor.OSMonitor", "OS_Monitor.apk",3000,1);
    }

    public void test23_shadowsocks() throws RemoteException, UiObjectNotFoundException, InterruptedException, IOException {
        normalwindow("com.github.shadowsocks/.MainActivity", "",3000,0);
    }

    public void test24_canonprint() throws RemoteException, UiObjectNotFoundException, InterruptedException, IOException {
        normalwindow("jp.co.canon.bsd.ad.pixmaprint/.EulaActivity", "canon_PRINT.apk",3000,1);
    }

    public void test25_epsonprint() throws RemoteException, UiObjectNotFoundException, InterruptedException, IOException {
        normalwindow("epson.print/.WellcomeScreenActivity", "Epson_iPrint.apk",3000,1);
    }

    public void test26_hpprint() throws RemoteException, UiObjectNotFoundException, InterruptedException, IOException {
        normalwindow("com.hp.android.print/.welcome.WelcomeActivity", "hp_ePrint.apk",3000,1);
    }

    public void test27_gamepad() throws RemoteException, UiObjectNotFoundException, InterruptedException, IOException {
        normalwindow("ru.elron.gamepadtester/.ui.main.Main2Activity", "GamepadTester.apk",3000,1);
    }

    public void test28_sogouinput() throws RemoteException, UiObjectNotFoundException, InterruptedException, IOException {
        normalwindow("com.sohu.inputmethod.sogou/.SogouIMELauncher", "sougoushurufa.apk",3000,1);
    }

    public void test29_xposed() throws RemoteException, UiObjectNotFoundException, InterruptedException, IOException {
        normalwindow("de.robv.android.xposed.installer/.WelcomeActivity", "",3000,0);
    }

    public void test30_wechat() throws RemoteException, UiObjectNotFoundException, InterruptedException, IOException {
        normalwindow("com.tencent.mm/com.tencent.mm.ui.LauncherUI", "",3000,0);
    }

    public void test31_qq() throws RemoteException, UiObjectNotFoundException, InterruptedException, IOException {
        normalwindow("com.tencent.mobileqq/com.tencent.mobileqq.activity.SplashActivity", "",3000,0);
    }

    public void test32_skype() throws RemoteException, UiObjectNotFoundException, InterruptedException, IOException {
        normalwindow("com.skype.raider/.Main", "skype.apk",3000,1);
    }

    public void test33_cloudmusic() throws RemoteException, UiObjectNotFoundException, InterruptedException, IOException {
        normalwindow("com.netease.cloudmusic/com.netease.cloudmusic.activity.LoadingActivity", "",9000,0);
    }

    public void test34_bili() throws RemoteException, UiObjectNotFoundException, InterruptedException, IOException {
        normalwindow("tv.danmaku.bili/.ui.splash.SplashActivity", "bilibili.apk",9000,1);
    }

    public void test35_quickpicker() throws RemoteException, UiObjectNotFoundException, InterruptedException, IOException {
        normalwindow("com.alensw.PicFolder/com.alensw.PicFolder.GalleryActivity", "quickPic.apk",3000,1);
    }

    public void test36_podcast() throws RemoteException, UiObjectNotFoundException, InterruptedException, IOException {
        normalwindow("com.bambuna.podcastaddict/.activity.PodcastListActivity", "podcastAddict.apk",3000,1);
    }

    public void test37_sinaweibo() throws RemoteException, UiObjectNotFoundException, InterruptedException, IOException {
        normalwindow("com.sina.weibo/com.sina.weibo.VisitorMainTabActivity", "weibo.apk",18000,1);
    }

    public void test38_amazonshop() throws RemoteException, UiObjectNotFoundException, InterruptedException, IOException {
        normalwindow("com.amazon.windowshop/com.amazon.mShop.splashscreen.StartupActivity", "amazonHD.apk",3000,1);
    }

    public void test39_kindle() throws RemoteException, UiObjectNotFoundException, InterruptedException, IOException {
        normalwindow("com.amazon.kindle/com.amazon.kindle.UpgradePage", "kindle.apk",3000,1);
    }

    public void test40_ithome() throws RemoteException, UiObjectNotFoundException, InterruptedException, IOException {
        normalwindow("com.ruanmei.ithome/com.ruanmei.ithome.ui.MainActivity", "itHome.apk",15000,1);
    }

    public void test41_xuetang() throws RemoteException, UiObjectNotFoundException, InterruptedException, IOException {
        normalwindow("com.xuetangx.mobile/com.xuetangx.mobile.gui.SplashActivity", "xuetangzaixian.apk",3000,1);
    }

    public void test42_candycrush() throws RemoteException, UiObjectNotFoundException, InterruptedException, IOException {
        normalwindow("com.king.candycrushsaga/.CandyCrushSagaActivity", "candyCrush.apk",3000,1);
    }

    public void test43_itaiwanmj() throws RemoteException, UiObjectNotFoundException, InterruptedException, IOException {
        normalwindow("com.avector.itw.itwmj16hd/.Xa_spl", "itwmj.apk",10000,1);
    }

    public void test44_tairui() throws RemoteException, UiObjectNotFoundException, InterruptedException, IOException {
        normalwindow("com.disney.WMPLite/.WMWActivity", "terry.apk",3000,1);
    }

    public void test45_yx2048() throws RemoteException, UiObjectNotFoundException, InterruptedException, IOException {
        maxwindow("com.digiplex.game/com.digiplex.game.MainActivity", "2048.apk",3000,1);
    }

    public void test46_ctr2() throws RemoteException, UiObjectNotFoundException, InterruptedException, IOException {
        normalwindow("com.zeptolab.ctr2.f2p.google/com.zeptolab.ctr2.CTR2Activity", "Cut_the_Rope_2.apk",3000,1);
    }

    public void test47_ctrexperiments() throws RemoteException, UiObjectNotFoundException, InterruptedException, IOException {
        normalwindow("com.zeptolab.ctrexperiments.ads/com.zeptolab.ctr.CtrApp", "Cut_the_Rope_Experiments.apk",3000,1);
    }

    public void test48_wps() throws RemoteException, UiObjectNotFoundException, InterruptedException, IOException {
        maxwindow("cn.wps.moffice_eng/cn.wps.moffice.documentmanager.PreStartActivity", "",3000,0);
    }

    public void test49_3dmark() throws RemoteException, UiObjectNotFoundException, InterruptedException, IOException {
        maxwindow("com.futuremark.dmandroid.application/com.futuremark.flamenco.ui.splash.SplashPageActivity", "3dmark.apk",3000,1);
    }

    public void test50_gfxbenchmark() throws RemoteException, UiObjectNotFoundException, InterruptedException, IOException {
        otherwindow("com.glbenchmark.glbenchmark27/net.kishonti.app.MainActivity", "gfxbench.apk",3000,1);
    }

    public void test51_cntvhd() throws RemoteException, UiObjectNotFoundException, InterruptedException, IOException {
        maxwindow("cn.cntvhd/.StartActivity", "cntvHD.apk",3000,1);
    }

    public void test52_fennec() throws RemoteException, UiObjectNotFoundException, InterruptedException, IOException {
        otherwindow("org.mozilla.fennec_openthos/org.mozilla.gecko.BrowserApp", "",3000,0);
    }

    public void test53_word() throws RemoteException, UiObjectNotFoundException, InterruptedException, IOException {
        otherwindow("com.microsoft.office.word/com.microsoft.office.apphost.LaunchActivity", "",3000,0);
    }

    public void test54_excel() throws RemoteException, UiObjectNotFoundException, InterruptedException, IOException {
        otherwindow("com.microsoft.office.excel/com.microsoft.office.apphost.LaunchActivity", "",3000,0);
    }

    public void test55_powerpoint() throws RemoteException, UiObjectNotFoundException, InterruptedException, IOException {
        otherwindow("com.microsoft.office.powerpoint/com.microsoft.office.apphost.LaunchActivity", "",3000,0);
    }

    public void test56_pcmark() throws RemoteException, UiObjectNotFoundException, InterruptedException, IOException {
        otherwindow("com.futuremark.pcmark.android.benchmark/com.futuremark.gypsum.activity.SplashPageActivity", "pcMark.apk",3000,1);
    }

    public void test57_yunshiting() throws RemoteException, UiObjectNotFoundException, InterruptedException, IOException {
        otherwindow("com.sohuott.tv.vod/.activity.BootActivity", "",3000,0);
    }

    public void test58_sparkle() throws RemoteException, UiObjectNotFoundException, InterruptedException, IOException {
        otherwindow("com.the10tons.sparkle.free/com.the10tons.JNexusInterface", "sparkle.apk",3000,1);
    }

    public void test59_angrybirdrio() throws RemoteException, UiObjectNotFoundException, InterruptedException, IOException {
        otherwindow("com.rovio.angrybirdsrio/com.rovio.fusion.App", "Angry_Birds_Rio.apk",3000,1);
    }

    public void test60_ButtonsandScissors() throws RemoteException, UiObjectNotFoundException, InterruptedException, IOException {
        normalwindow("com.kyworks.buttonsandscissors.inapp/org.cocos2dx.cpp.AppActivity", "Buttons_and_Scissors.apk",3000,1);
    }

    public void test61_happyelements() throws RemoteException, UiObjectNotFoundException, InterruptedException, IOException {
        normalwindow("com.happyelements.AndroidAnimal.ad/com.happyelements.hellolua.MainActivity", "happyelementsAnimal.apk",3000,1);
    }

    public void test62_gameloft() throws RemoteException, UiObjectNotFoundException, InterruptedException, IOException {
        otherwindow("com.gameloft.android.ANMP.GloftA8CN.UC/.Game", "kuangyebiaoche.apk",3000,1);
    }

    public void test63_dingding() throws RemoteException, UiObjectNotFoundException, InterruptedException, IOException {
        normalwindow("com.alibaba.android.rimet/.biz.SplashActivity", "dingding_4.6.6.apk",3000,1);
    }

//特殊：
//"com.android.camera2/com.android.camera.CameraActivity" //相机

    //以窗口模式打开的应用
    private void normalwindow(String appName,String apkName,long waittime,int ifUninstall) throws RemoteException, UiObjectNotFoundException, InterruptedException,
            IOException {
        otoDisplayRun otoTest;
        otoTest = new otoDisplayRun(getUiDevice());
        otoTest.mydevice.wakeUp();
        assertTrue("screen on :can't wakeup", otoTest.mydevice.isScreenOn());
        if(ifUninstall==1) {
            otoTest.mydevice.executeShellCommand("pm install /sdcard/Download/" + apkName);
        }
        otoTest.mydevice.click(0,0);
        if(appName=="com.sina.weibo/com.sina.weibo.VisitorMainTabActivity"){
            otoTest.mydevice.executeShellCommand("am start -n com.sina.weibo/com.sina.weibo.VisitorMainTabActivity");
            sleep(12000);
        }
        otoTest.LaunchTime(appName,waittime);
        if(appName=="org.videolan.vlc/org.videolan.vlc.StartActivity"){
            sleep(5000);
            if(new UiObject(new UiSelector().text("是")).exists()) {
                new UiObject(new UiSelector().text("是")).click();
            }
        }
        otoTest.Windowtest(appName,3000);
        otoTest.mydevice.executeShellCommand("am start -n " + appName);
        sleep(waittime);
        otoTest.ClickById("android:id/mwMaximizeBtn");
        sleep(3000);
        otoTest.MoveToTop();
        otoTest.ClickById("android:id/mwMaximizeBtn");
        sleep(3000);
        otoTest.ClickById("android:id/mwMinimizeBtn");
        otoTest.mydevice.executeShellCommand("am force-stop " + appName.substring(0, appName.indexOf("/")));
        if(appName=="org.openthos.ota/.MainActivity"){
            otoTest.mydevice.executeShellCommand("am start -n org.openthos.ota/.MainActivity");
            sleep(2000);
            otoTest.ClickById("android:id/mwCloseBtn");
        }
        if(ifUninstall==1) {
            otoTest.mydevice.executeShellCommand("pm uninstall " + appName.substring(0, appName.indexOf("/")));
        }

    }
    //以全屏模式打开的应用
    private void maxwindow(String appName,String apkName,long waittime,int ifUninstall) throws RemoteException, UiObjectNotFoundException, InterruptedException,
            IOException {
        otoDisplayRun otoTest;
        otoTest = new otoDisplayRun(getUiDevice());
        otoTest.mydevice.wakeUp();
        assertTrue("screen on :can't wakeup", otoTest.mydevice.isScreenOn());
        if(ifUninstall==1) {
            otoTest.mydevice.executeShellCommand("pm install /sdcard/Download/" + apkName);
        }
        otoTest.mydevice.click(0,0);
        otoTest.LaunchTime(appName,waittime);
        if(appName=="com.digiplex.game/com.digiplex.game.MainActivity"){
            otoTest.mydevice.click(0,0);
        }else if(appName=="com.glbenchmark.glbenchmark27/net.kishonti.app.MainActivity") {
            sleep(5000);
            if (new UiObject(new UiSelector().text("接受")).exists()) {
                new UiObject(new UiSelector().text("接受")).click();
                sleep(2000);
                new UiObject(new UiSelector().text("确定")).click();
                sleep(18000);
                new UiObject(new UiSelector().text("确定")).click();
            }
        }
        otoTest.MoveToTop();
        otoTest.ClickById("android:id/mwMaximizeBtn");
        otoTest.Windowtest(appName,3000);
        otoTest.mydevice.executeShellCommand("am start -n " + appName);
        sleep(1000);
        otoTest.MoveToTop();
        otoTest.ClickById("android:id/mwMaximizeBtn");
        otoTest.ClickById("android:id/mwCloseBtn");
        otoTest.mydevice.executeShellCommand("am start -n " + appName);
        otoTest.MoveToTop();
        otoTest.ClickById("android:id/mwMinimizeBtn");
        otoTest.mydevice.executeShellCommand("am force-stop " + appName.substring(0, appName.indexOf("/")));
        if(ifUninstall==1) {
            otoTest.mydevice.executeShellCommand("pm uninstall " + appName.substring(0, appName.indexOf("/")));
        }

    }
    //强制全屏模式打开的应用
    private void otherwindow(String appName,String apkName,long waittime,int ifUninstall) throws RemoteException, UiObjectNotFoundException, InterruptedException,
            IOException {
        otoDisplayRun otoTest;
        otoTest = new otoDisplayRun(getUiDevice());
        otoTest.mydevice.wakeUp();
        assertTrue("screen on :can't wakeup", otoTest.mydevice.isScreenOn());
        /*if(ifUninstall==1) {
            otoTest.mydevice.executeShellCommand("pm install /sdcard/Download/" + apkName);
        }*/
        otoTest.LaunchTime(appName,0);
        sleep(10000);
        otoTest.mydevice.executeShellCommand("am force-stop " + appName.substring(0, appName.indexOf("/")));
        if(ifUninstall==1) {
            otoTest.mydevice.executeShellCommand("pm uninstall " + appName.substring(0, appName.indexOf("/")));
        }

    }

}
