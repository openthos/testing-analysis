# -*- coding: UTF-8 -*-

import uiautomator2 as u2
import unittest
from time import sleep
import extendevent
import sys
from logzero import logger

deviceIP=sys.argv[1]
d = u2.connect(deviceIP)
mouseID=extendevent.getmouseID(d)
d.app_install('https://github.com/openthos/testing-analysis/raw/master/Auto-test/test_apk/startmenu_testapk.apk')
class StartmenuTestCase(unittest.TestCase):
    # 测试准备
    def setUp(self):
        extendevent.click_blank(d,mouseID)
        d.app_start("com.android.calculator2")
        extendevent.open_startmenu(d,mouseID)
        sleep(0.5)
    # 测试后清理
    def tearDown(self):
        extendevent.click_blank(d,mouseID)
        d.app_stop("com.android.calculator2")


    # 测试准备阶段
    # 删除常用软件列表中的应用
    def test_00_prepare(self):
        logger.info("------------开始菜单：测试准备-删除常用软件列表的应用")
        common_software_list = d(resourceId="com.android.systemui:id/list_view")
        while common_software_list.child(className="android.widget.LinearLayout").exists:
            extendevent.right_click(d,mouseID,common_software_list.child(instance=0))
            d(text="从此列表中删除").click()
            sleep(1)


    # 测试开始菜单搜索栏
    def test_01_search(self):
        logger.info("------------开始菜单：搜索栏")
        d(resourceId='com.android.systemui:id/search').set_text("os monitor")
        sleep(1)
        first_icon = d(resourceId="com.android.systemui:id/grid_view").child(instance=0)
        text_of_first_icon = first_icon.child(className="android.widget.TextView").get_text()
        print(text_of_first_icon == "OS Monitor")
        assert (text_of_first_icon == "OS Monitor")
        print(not d(resourceId="com.android.systemui:id/grid_view").child(instance=4).exists)
        assert (not d(resourceId="com.android.systemui:id/grid_view").child(instance=4).exists)


    # 测试开始菜单排序栏
    def test_02_sort(self):
        logger.info("------------开始菜单：排序")
        logger.info("------------------------默认排序")
        d(resourceId="com.android.systemui:id/arrow_show").click()
        d(text="默认").click()
        tmpstr = d(resourceId='com.android.systemui:id/sort_type').get_text()
        print(tmpstr == u'默认')
        assert tmpstr == u'默认'
        print("暂不验证")
        sleep(2)
        logger.info("------------------------安装时间正序")
        d(resourceId="com.android.systemui:id/arrow_show").click()
        d(text="安装时间").click()
        tmpstr = d(resourceId='com.android.systemui:id/sort_type').get_text()
        print(tmpstr == u"安装时间")
        assert tmpstr == u"安装时间"
        sleep(1)
        first_icon = d(resourceId="com.android.systemui:id/grid_view").child(instance=0)
        text_of_first_icon = first_icon.child(className="android.widget.TextView").get_text()
        print(text_of_first_icon == "OS Monitor")
        assert text_of_first_icon == "OS Monitor"
        logger.info("------------------------安装时间逆序")
        d(resourceId="com.android.systemui:id/arrow_direct").click()
        print("暂不验证")
        logger.info("------------------------使用频率正序")
        d(resourceId="com.android.systemui:id/arrow_show").click()
        d(text="使用频率").click()
        tmpstr = d(resourceId='com.android.systemui:id/sort_type').get_text()
        print(tmpstr == u"使用频率")
        assert tmpstr == u"使用频率"
        print("暂不验证")
        logger.info("------------------------使用频率逆序")
        d(resourceId="com.android.systemui:id/arrow_direct").click()
        print("暂不验证")
        logger.info("------------------------A-Z正序")
        d(resourceId="com.android.systemui:id/arrow_show").click()
        d(text="A - Z").click()
        tmpstr = d(resourceId='com.android.systemui:id/sort_type').get_text()
        print(tmpstr == "A - Z")
        assert tmpstr == "A - Z"
        print("暂不验证")
        logger.info("------------------------A-Z逆序")
        d(resourceId="com.android.systemui:id/arrow_direct").click()
        print("暂不验证")


    # 测试开始菜单所有应用列表
    def test_03_allapp_single_click(self):
        logger.info("------------开始菜单：所有应用列表")
        logger.info("------------------------单击打开")
        d(resourceId='com.android.systemui:id/search').set_text("os monitor")
        sleep(1)
        first_icon = d(resourceId="com.android.systemui:id/grid_view").child(instance=0)
        text_of_first_icon = first_icon.child(className="android.widget.TextView").click()
        sleep(2)
        print(d(resourceId="com.eolwral.osmonitor:id/action_bar").exists)
        assert d(resourceId="com.eolwral.osmonitor:id/action_bar").exists
        d.app_stop("com.eolwral.osmonitor")

    def test_04_allapp_rightclick_open(self):
        logger.info("------------------------右键-打开")
        d(resourceId='com.android.systemui:id/search').set_text("os monitor")
        sleep(1)
        first_icon = d(resourceId="com.android.systemui:id/grid_view").child(instance=0)
        extendevent.right_click(d,mouseID,first_icon.child(className="android.widget.TextView"))
        sleep(1)
        d(text="打开").click()
        sleep(2)
        print(d(resourceId="com.eolwral.osmonitor:id/action_bar").exists)
        assert d(resourceId="com.eolwral.osmonitor:id/action_bar").exists
        d.app_stop("com.eolwral.osmonitor")

    def test_05_allapp_rightclick_phonemode(self):
        logger.info("------------------------右键-以手机模式运行")
        d(resourceId='com.android.systemui:id/search').set_text("os monitor")
        sleep(1)
        first_icon = d(resourceId="com.android.systemui:id/grid_view").child(instance=0)
        extendevent.right_click(d,mouseID,first_icon.child(className="android.widget.TextView"))
        sleep(1)
        d(text="以手机模式运行").click()
        sleep(2)
        print(d(resourceId="com.eolwral.osmonitor:id/action_bar").exists)
        assert d(resourceId="com.eolwral.osmonitor:id/action_bar").exists
        d.app_stop("com.eolwral.osmonitor")

    def test_06_allapp_rightclick_desktopmode(self):
        logger.info("------------------------右键-以桌面模式运行")
        d(resourceId='com.android.systemui:id/search').set_text("os monitor")
        sleep(1)
        first_icon = d(resourceId="com.android.systemui:id/grid_view").child(instance=0)
        extendevent.right_click(d,mouseID,first_icon.child(className="android.widget.TextView"))
        sleep(1)
        d(text="以桌面模式运行").click()
        sleep(2)
        print(d(resourceId="com.eolwral.osmonitor:id/action_bar").exists)
        assert d(resourceId="com.eolwral.osmonitor:id/action_bar").exists
        d.app_stop("com.eolwral.osmonitor")

    def test_07_allapp_rightclick_dock(self):
        logger.info("------------------------右键-固定到任务栏")
        d(resourceId='com.android.systemui:id/search').set_text("os monitor")
        sleep(1)
        first_icon = d(resourceId="com.android.systemui:id/grid_view").child(instance=0)
        extendevent.right_click(d,mouseID,first_icon.child(className="android.widget.TextView"))
        sleep(1)
        d(text="固定到任务栏").click()
        sleep(2)
        print("暂不验证")

    def test_08_allapp_rightclick_undock(self):
        logger.info("------------------------右键-解除固定")
        d(resourceId='com.android.systemui:id/search').set_text("os monitor")
        sleep(1)
        first_icon = d(resourceId="com.android.systemui:id/grid_view").child(instance=0)
        extendevent.right_click(d,mouseID,first_icon.child(className="android.widget.TextView"))
        sleep(1)
        d(text="解除固定").click()
        sleep(2)
        print("暂不验证")

    def test_09_allapp_rightclick_uninstall(self):
        logger.info("------------------------右键-卸载")
        d(resourceId='com.android.systemui:id/search').set_text("os monitor")
        sleep(1)
        first_icon = d(resourceId="com.android.systemui:id/grid_view").child(instance=0)
        extendevent.right_click(d,mouseID,first_icon.child(className="android.widget.TextView"))
        sleep(1)
        d(text="卸载").click()
        sleep(2)
        print(d(text="要卸载此应用吗？").exists)
        assert d(text="要卸载此应用吗？").exists
        d(text="取消").click()


    # 测试开始菜单常用应用列表
    def test_10_freqapp_single_click(self):
        logger.info("------------开始菜单：常用应用列表")
        logger.info("------------------------单击打开")
        d(resourceId='com.android.systemui:id/search').set_text("时钟")
        d(resourceId="com.android.systemui:id/grid_view").child(instance=0).click()
        sleep(2)
        closebutton = d(text="时钟").sibling(resourceId="android:id/close_window")
        if closebutton.exists:
            closebutton.click()
        else:
            extendevent.click_blank(d,mouseID)
            closebutton.click()
        sleep(1)
        extendevent.open_startmenu(d,mouseID)
        sleep(1)
        common_software_list = d(resourceId="com.android.systemui:id/list_view")
        common_software_list.child(instance=0).click()
        sleep(2)
        print(d(resourceId="com.eolwral.osmonitor:id/action_bar").exists)
        assert d(resourceId="com.eolwral.osmonitor:id/action_bar").exists
        d.app_stop("com.eolwral.osmonitor")

    def test_11_freqapp_rightclick_open(self):
        logger.info("------------------------右键-打开")
        common_software_list = d(resourceId="com.android.systemui:id/list_view")
        extendevent.right_click(d,mouseID,common_software_list.child(instance=0))
        d(text="打开").click()
        sleep(2)
        print(d(resourceId="com.eolwral.osmonitor:id/action_bar").exists)
        assert d(resourceId="com.eolwral.osmonitor:id/action_bar").exists
        d.app_stop("com.eolwral.osmonitor")

    def test_12_freqapp_rightclick_phonemode(self):
        logger.info("------------------------右键-以手机模式运行")
        common_software_list = d(resourceId="com.android.systemui:id/list_view")
        extendevent.right_click(d,mouseID,common_software_list.child(instance=0))
        d(text="以手机模式运行").click()
        sleep(2)
        print(d(resourceId="com.eolwral.osmonitor:id/action_bar").exists)
        assert d(resourceId="com.eolwral.osmonitor:id/action_bar").exists
        d.app_stop("com.eolwral.osmonitor")


    def test_13_freqapp_rightclick_desktopmode(self):
        logger.info("------------------------右键-以桌面模式运行")
        common_software_list = d(resourceId="com.android.systemui:id/list_view")
        extendevent.right_click(d,mouseID,common_software_list.child(instance=0))
        d(text="以桌面模式运行").click()
        sleep(2)
        print(d(resourceId="com.eolwral.osmonitor:id/action_bar").exists)
        assert d(resourceId="com.eolwral.osmonitor:id/action_bar").exists
        d.app_stop("com.eolwral.osmonitor")

    def test_14_freqapp_rightclick_delete(self):
        logger.info("------------------------右键-从此列表中删除")
        common_software_list = d(resourceId="com.android.systemui:id/list_view")
        extendevent.right_click(d,mouseID,common_software_list.child(instance=0))
        d(text="从此列表中删除").click()
        sleep(2)
        print(not common_software_list.child(text="OS Monitor").exists)
        assert not common_software_list.child(text="OS Monitor").exists

    
    # 测试文件管理器快捷按钮
    def test_15_filemanager(self):
        logger.info("------------文件管理器快捷按钮")
        d(resourceId="com.android.systemui:id/file_manager").click()
        sleep(2)
        print(d(text="个人空间").exists)
        assert d(text="个人空间").exists
        closebutton = d(text="文件管理").sibling(resourceId="android:id/close_window")
        if closebutton.exists:
            closebutton.click()
        else:
            extendevent.click_blank(d,mouseID)
            closebutton.click()

    # 测试设置快捷按钮  
    def test_16_setting(self):
        logger.info("------------设置快捷按钮")
        d(resourceId="com.android.systemui:id/system_setting").click()
        sleep(2)
        print(d(text="网络和互联网").exists)
        assert d(text="网络和互联网").exists
        closebutton = d(text="设置").sibling(resourceId="android:id/close_window")
        if closebutton.exists:
            closebutton.click()
        else:
            extendevent.click_blank(d,mouseID)
            closebutton.click()


    # 测试电源快捷按钮
    def test_17_power(self):
        logger.info("------------电源快捷按钮")
        d(resourceId="com.android.systemui:id/power_off").click()
        sleep(2)
        print(d(text="睡眠").exists)
        assert d(text="睡眠").exists
        d(resourceId="com.android.systemui:id/power_close").click()


    # 结束清理
    def test_99_end(self):
        logger.info("------------结束清理：卸载os monitor")
        d.shell("pm uninstall com.eolwral.osmonitor")

if __name__ == "__main__":
    if len(sys.argv) > 1:
        StartmenuTestCase.deviceIP = sys.argv.pop()

    unittest.main()
    sleep(2)
    d.service("uiautomator").stop()

