# -*- coding: UTF-8 -*-

import uiautomator2 as u2
import unittest
from time import sleep
import extendevent
import extendfunction
import sys
from logzero import logger
import re

deviceIP=sys.argv[1]
d = u2.connect(deviceIP)
package=sys.argv[2]
mouseID=extendevent.getmouseID(d)
screen_res=str(d.shell("wm size"))
screen_res1=screen_res.replace("\\n"," ")
list1=re.split(" |x",screen_res1)
screen_width=int(list1[2])
screen_height=int(list1[3])-72
step=0.5
print(screen_width)
print(screen_height)


def output_res(cond):
    if cond :
        #test_file.write("通过\n")
        print("------------通过------------")
    else :
        #test_file.write("**不通过**\n")
        print("---------不通过！！！---------")

def resize(orientation,i):
    windowBounds=extendfunction.getParentBounds(d.dump_hierarchy(),'resource-id="android:id/caption"')
    print("实际："+str(windowBounds))
    
    currentApp_hcenter=(windowBounds['left']+windowBounds['right'])/2
    currentApp_vcenter=(windowBounds['top']+windowBounds['bottom'])/2
    if (orientation=="top"):
        extendevent.drag(d,mouseID,currentApp_hcenter,windowBounds['top']-1,currentApp_hcenter,windowBounds['top']-1+i)
        check_windowBounds = {'top': windowBounds['top']+i, 'bottom': windowBounds['bottom'], 'left': windowBounds['left'],'right':windowBounds['right']}
    elif(orientation=="bottom"):
        extendevent.drag(d,mouseID,currentApp_hcenter,windowBounds['bottom']+1,currentApp_hcenter,windowBounds['bottom']+1-i)
        check_windowBounds = {'top': windowBounds['top'], 'bottom': windowBounds['bottom']-i, 'left': windowBounds['left'],'right':windowBounds['right']}
    elif(orientation=="left"):
        extendevent.drag(d,mouseID,windowBounds['left']-1,currentApp_vcenter,windowBounds['left']-1-i,currentApp_vcenter)
        check_windowBounds = {'top': windowBounds['top'], 'bottom': windowBounds['bottom'], 'left': windowBounds['left']-i,'right':windowBounds['right']}
    elif(orientation=="right"):
        extendevent.drag(d,mouseID,windowBounds['right']+1,currentApp_vcenter,windowBounds['right']+1-i,currentApp_vcenter)
        check_windowBounds = {'top': windowBounds['top'], 'bottom': windowBounds['bottom'], 'left': windowBounds['left'],'right':windowBounds['right']-i}
    elif(orientation=="left-top"):
        extendevent.drag(d,mouseID,windowBounds['left']-1,windowBounds['top']-1,windowBounds['left']-1+i,windowBounds['top']-1+i)
        check_windowBounds = {'top': windowBounds['top']+i, 'bottom': windowBounds['bottom'], 'left': windowBounds['left']+i,'right':windowBounds['right']}
    elif(orientation=="left-bottom"):
        extendevent.drag(d,mouseID,windowBounds['left']-1,windowBounds['bottom']+1,windowBounds['left']-1+i,windowBounds['bottom']+1-i)
        check_windowBounds = {'top': windowBounds['top'], 'bottom': windowBounds['bottom']-i, 'left': windowBounds['left']+i,'right':windowBounds['right']}
    elif(orientation=="right-top"):
        extendevent.drag(d,mouseID,windowBounds['right']+1,windowBounds['top']-1,windowBounds['right']+1-i,windowBounds['top']-1+i)
        check_windowBounds = {'top': windowBounds['top']+i, 'bottom': windowBounds['bottom'], 'left': windowBounds['left'],'right':windowBounds['right']-i}
    else:
        extendevent.drag(d,mouseID,windowBounds['right']+1,windowBounds['bottom']+1,windowBounds['right']+1-i,windowBounds['bottom']+1-i)
        check_windowBounds = {'top': windowBounds['top'], 'bottom': windowBounds['bottom']-i, 'left': windowBounds['left'],'right':windowBounds['right']-i}

    sleep(0.5)
    windowBounds=extendfunction.getParentBounds(d.dump_hierarchy(),'resource-id="android:id/caption"')
    output_res(windowBounds==check_windowBounds)
    print("期望："+str(check_windowBounds))
    print("实际："+str(windowBounds))
    return windowBounds


class WindowTestCase(unittest.TestCase):

    def test_00_prepare(self):
        logger.info("------------标准窗口测试：测试准备-打开应用")
        d.app_start(package)
        d(resourceId="android:id/caption").exists(timeout=30)
        if (not d(resourceId="android:id/caption").exists):
            extendevent.click_blank(d,mouseID)
        if (not d(resourceId="android:id/maximize_window").exists):
            sleep(0.5)
            d(resourceId="android:id/setting_window").click()
            d(text="标准模式").click()
            sleep(1)
            d(text="OK").click()
            sleep(0.5)
            d.app_start(package)
            d(resourceId="android:id/caption").exists(timeout=30)
        d(resourceId="android:id/caption").drag_to(screen_width/2, 100, duration=0.1)
        sleep(0.5)
        windowBounds=extendfunction.getParentBounds(d.dump_hierarchy(),'resource-id="android:id/caption"')
        window_width=windowBounds['right']-windowBounds['left']
        if window_width<700:
            windowBounds = resize("right",-800+window_width)

    def test_01_resize_top(self):
        logger.info("------------标准窗口测试：调整大小-上边框")
        windowBounds = resize("top",100)
        windowBounds = resize("top",-100)

    def test_02_resize_bottom(self):
        logger.info("------------标准窗口测试：调整大小-下边框")
        windowBounds = resize("bottom",100)
        windowBounds = resize("bottom",-100)

    def test_03_resize_left(self):
        logger.info("------------标准窗口测试：调整大小-左边框")
        windowBounds = resize("left",100)
        windowBounds = resize("left",-100)

    def test_04_resize_right(self):
        logger.info("------------标准窗口测试：调整大小-右边框")
        windowBounds = resize("right",100)
        windowBounds = resize("right",-100)

    def test_05_resize_lefttop(self):
        logger.info("------------标准窗口测试：调整大小-左上边框")
        windowBounds = resize("left-top",100)
        windowBounds = resize("left-top",-100)

    def test_06_resize_leftbottom(self):
        logger.info("------------标准窗口测试：调整大小-左下边框")
        windowBounds = resize("left-bottom",100)
        windowBounds = resize("left-bottom",-100)

    def test_07_resize_righttop(self):
        logger.info("------------标准窗口测试：调整大小-右上边框")
        windowBounds = resize("right-top",100)
        windowBounds = resize("right-top",-100)

    def test_08_resize_rightbottom(self):
        logger.info("------------标准窗口测试：调整大小-右下边框")
        windowBounds = resize("right-bottom",100)
        windowBounds = resize("right-bottom",-100)

    def test_11_move_left1(self):
        logger.info("------------标准窗口测试：移动-屏幕内左移")
        sleep(0.5)
        caption_x,caption_y = d(resourceId="android:id/caption").center()
        windowBounds=extendfunction.getParentBounds(d.dump_hierarchy(),'resource-id="android:id/caption"')
        window_width=windowBounds['right']-windowBounds['left']
        extendevent.drag_2(d,mouseID,caption_x,caption_y,window_width/2+100,caption_y)
        check_windowBounds = {'top': windowBounds['top'], 'bottom': windowBounds['bottom'], 'left': 100,'right':100+window_width}
        sleep(0.5)
        windowBounds=extendfunction.getParentBounds(d.dump_hierarchy(),'resource-id="android:id/caption"')
        print("期望："+str(check_windowBounds))
        print("实际："+str(windowBounds))
        output_res(windowBounds==check_windowBounds)

    def test_12_move_right1(self):
        logger.info("------------标准窗口测试：移动-屏幕内右移")
        sleep(0.5)
        caption_x,caption_y = d(resourceId="android:id/caption").center()
        windowBounds=extendfunction.getParentBounds(d.dump_hierarchy(),'resource-id="android:id/caption"')
        window_width=windowBounds['right']-windowBounds['left']
        extendevent.drag_2(d,mouseID,caption_x,caption_y,screen_width-100-window_width/2,caption_y)
        check_windowBounds = {'top': windowBounds['top'], 'bottom': windowBounds['bottom'], 'left': screen_width-100-window_width,'right':screen_width-100}
        sleep(0.5)
        windowBounds=extendfunction.getParentBounds(d.dump_hierarchy(),'resource-id="android:id/caption"')
        print("期望："+str(check_windowBounds))
        print("实际："+str(windowBounds))
        output_res(windowBounds==check_windowBounds)

    def test_13_move_up1(self):
        logger.info("------------标准窗口测试：移动-屏幕内上移")
        sleep(0.5)
        caption_x,caption_y = d(resourceId="android:id/caption").center()
        windowBounds=extendfunction.getParentBounds(d.dump_hierarchy(),'resource-id="android:id/caption"')
        window_width=windowBounds['right']-windowBounds['left']
        window_height=windowBounds['bottom']-windowBounds['top']
        caption_bounds=d(resourceId="android:id/caption").info.get('visibleBounds')
        caption_height=caption_bounds['bottom']-caption_bounds['top']
        extendevent.drag_2(d,mouseID,caption_x,caption_y,screen_width/2,100)
        check_windowBounds = {'top': 100-caption_height/2-1, 'bottom': (100-caption_height/2)-1+window_height, 'left': screen_width/2-window_width/2,'right': screen_width/2+window_width/2}
        sleep(0.5)
        windowBounds=extendfunction.getParentBounds(d.dump_hierarchy(),'resource-id="android:id/caption"')
        print("期望："+str(check_windowBounds))
        print("实际："+str(windowBounds))
        output_res(windowBounds==check_windowBounds)

    def test_14_move_down1(self):
        logger.info("------------标准窗口测试：移动-屏幕内下移")
        sleep(0.5)
        caption_x,caption_y = d(resourceId="android:id/caption").center()
        windowBounds=extendfunction.getParentBounds(d.dump_hierarchy(),'resource-id="android:id/caption"')
        window_height=windowBounds['top']-windowBounds['bottom']
        extendevent.drag_2(d,mouseID,caption_x,caption_y,caption_x,800-window_height)

    def test_15_move_left2(self):
        logger.info("------------标准窗口测试：移动-出屏幕左移")
        sleep(0.5)
        caption_x,caption_y = d(resourceId="android:id/caption").center()
        extendevent.drag_2(d,mouseID,caption_x,caption_y,300,100)

    def test_16_move_right2(self):
        logger.info("------------标准窗口测试：移动-出屏幕右移")
        sleep(0.5)
        caption_x,caption_y = d(resourceId="android:id/caption").center()
        extendevent.drag_2(d,mouseID,caption_x,caption_y,screen_width-300,100)

    def test_17_move_leftdown(self):
        logger.info("------------标准窗口测试：移动-出屏幕左下")
        sleep(0.5)
        caption_x,caption_y = d(resourceId="android:id/caption").center()
        extendevent.drag_2(d,mouseID,caption_x,caption_y,100,700)

    def test_18_move_rightdown(self):
        logger.info("------------标准窗口测试：移动-出屏幕右下")
        sleep(0.5)
        caption_x,caption_y = d(resourceId="android:id/caption").center()
        extendevent.drag_2(d,mouseID,caption_x,caption_y,1900,700)

    def test_19_move_top(self):
        logger.info("------------标准窗口测试：移动-上边界")
        sleep(0.5)
        caption_x,caption_y = d(resourceId="android:id/caption").center()
        extendevent.drag_2(d,mouseID,caption_x,caption_y,800,0)
        check_windowBounds = {'top': 0, 'bottom': screen_height, 'left': 0,'right':screen_width}
        sleep(0.5)
        windowBounds=extendfunction.getParentBounds(d.dump_hierarchy(),'resource-id="android:id/caption"')
        print("期望："+str(check_windowBounds))
        print("实际："+str(windowBounds))
        output_res(windowBounds==check_windowBounds)

    def test_20_move_leftside(self):
        logger.info("------------标准窗口测试：移动-左边界")
        sleep(0.5)
        caption_x,caption_y = d(resourceId="android:id/caption").center()
        extendevent.drag_2(d,mouseID,caption_x,caption_y,0,10)
        sleep(0.5)
        check_windowBounds = {'top': 0, 'bottom': screen_height, 'left': 0,'right':screen_width/2}
        windowBounds=extendfunction.getParentBounds(d.dump_hierarchy(),'resource-id="android:id/caption"')
        print("期望："+str(check_windowBounds))
        print("实际："+str(windowBounds))
        output_res(windowBounds==check_windowBounds)

    def test_21_move_rightside(self):
        logger.info("------------标准窗口测试：移动-右边界")
        sleep(0.5)
        caption_x,caption_y = d(resourceId="android:id/caption").center()
        extendevent.drag_2(d,mouseID,caption_x,caption_y,screen_width,10)
        sleep(0.5)
        check_windowBounds = {'top': 0, 'bottom': screen_height, 'left': screen_width/2,'right':screen_width}
        windowBounds=extendfunction.getParentBounds(d.dump_hierarchy(),'resource-id="android:id/caption"')
        print("期望："+str(check_windowBounds))
        print("实际："+str(windowBounds))
        output_res(windowBounds==check_windowBounds)

    def test_22_doubleclick(self):
        logger.info("------------标准窗口测试：双击")
        sleep(0.5)
        caption_x,caption_y = d(resourceId="android:id/caption").center()
        extendevent.drag_2(d,mouseID,caption_x,caption_y,screen_width/2,100)
        sleep(0.5)
        extendevent.double_click(d,mouseID,d(resourceId="android:id/caption"))
        sleep(0.5)
        check_windowBounds = {'top': 0, 'bottom': screen_height, 'left': 0,'right':screen_width}
        windowBounds=extendfunction.getParentBounds(d.dump_hierarchy(),'resource-id="android:id/caption"')
        print("期望："+str(check_windowBounds))
        print("实际："+str(windowBounds))
        output_res(windowBounds==check_windowBounds)


    def test_31_button_minimum(self):
        logger.info("------------标准窗口测试：按钮-最小化")
        sleep(0.5)
        d(resourceId="android:id/minimize_window").click()
        sleep(0.5)
        output_res(d(text="回收站").exists)
        d.app_stop(package)
        sleep(0.5)
        d.app_start(package)
        caption_x,caption_y = d(resourceId="android:id/caption").center()
        extendevent.drag_2(d,mouseID,caption_x,caption_y,screen_width/2,100)

    def test_32_button_max(self):
        logger.info("------------标准窗口测试：按钮-最大化")
        sleep(0.5)
        windowBounds=extendfunction.getParentBounds(d.dump_hierarchy(),'resource-id="android:id/caption"')
        window_width=windowBounds['right']-windowBounds['left']
        if window_width<700:
            windowBounds = resize("right",-800+window_width)
        d(resourceId="android:id/maximize_window").click()
        sleep(0.5)
        check_windowBounds = {'top': 0, 'bottom': screen_height, 'left': 0,'right':screen_width}
        windowBounds=extendfunction.getParentBounds(d.dump_hierarchy(),'resource-id="android:id/caption"')
        print("期望："+str(check_windowBounds))
        print("实际："+str(windowBounds))
        output_res(windowBounds==check_windowBounds)
        d(resourceId="android:id/maximize_window").click()


    def test_33_button_close(self):
        logger.info("------------标准窗口测试：按钮-关闭")
        sleep(0.5)
        d(resourceId="android:id/close_window").click()
        sleep(0.5)
        output_res(d(text="回收站").exists)
        d.app_start(package)

    def test_34_button_setting(self):
        logger.info("------------标准窗口测试：按钮-设置")
        sleep(0.5)
        caption_x,caption_y = d(resourceId="android:id/caption").center()
        d(resourceId="android:id/setting_window").click()
        output_res(d(text="标准模式").exists)
        output_res(d(text="全屏模式").exists)
        output_res(d(text="手机模式（竖屏）").exists)
        output_res(d(text="手机模式（横屏）").exists)
        d.click(caption_x,caption_y)

    def test_51_fullscreen_change(self):
        logger.info("------------全屏窗口测试：切换")
        sleep(0.5)
        app_name=str(d(resourceId="android:id/title_window").get_text())
        print(app_name)
        d(resourceId="android:id/setting_window").click()
        d(text="全屏模式").click()
        sleep(1)
        d(text="OK").click()
        sleep(0.5)
        d.app_start("com.android.calculator2")
        sleep(1)
        extendevent.open_startmenu(d,mouseID)
        sleep(1)
        d(resourceId='com.android.systemui:id/search').set_text(app_name)
        sleep(1)
        d(resourceId="com.android.systemui:id/grid_view").child(instance=0).click()
        sleep(0.5)
        extendevent.click_blank(d,mouseID)
        sleep(0.5)
        check_windowBounds = {'top': 0, 'bottom': screen_height, 'left': 0,'right':screen_width}
        windowBounds=extendfunction.getParentBounds(d.dump_hierarchy(),'resource-id="android:id/caption"')
        print("期望："+str(check_windowBounds))
        print("实际："+str(windowBounds))
        output_res(windowBounds==check_windowBounds)
        d.shell("sendevent /dev/input/event"+mouseID+" 2 0 0")
        d.shell("sendevent /dev/input/event"+mouseID+" 2 1 200")
        d.shell("sendevent /dev/input/event"+mouseID+" 0 0 0")
        sleep(0.5)
        output_res(not d(resourceId="android:id/caption").exists)
        sleep(0.5)
        d.shell("sendevent /dev/input/event"+mouseID+" 2 0 -2000")
        d.shell("sendevent /dev/input/event"+mouseID+" 2 1 -2000")
        d.shell("sendevent /dev/input/event"+mouseID+" 0 0 0")
        sleep(0.5)
        output_res(d(resourceId="android:id/caption").exists)

    def test_52_fullscreen_drag(self):
        logger.info("------------全屏窗口测试：拖拽")
        sleep(0.5)
        caption_x,caption_y = d(resourceId="android:id/caption").center()
        extendevent.drag_2(d,mouseID,caption_x,caption_y,screen_width/2,200)
        sleep(0.5)
        d.shell("sendevent /dev/input/event"+mouseID+" 2 0 -2000")
        d.shell("sendevent /dev/input/event"+mouseID+" 2 1 -2000")
        d.shell("sendevent /dev/input/event"+mouseID+" 0 0 0")
        sleep(0.5)
        check_windowBounds = {'top': 0, 'bottom': screen_height, 'left': 0,'right':screen_width}
        windowBounds=extendfunction.getParentBounds(d.dump_hierarchy(),'resource-id="android:id/caption"')
        print("期望："+str(check_windowBounds))
        print("实际："+str(windowBounds))
        output_res(windowBounds==check_windowBounds)

    def test_53_fullscreen_button_minimum(self):
        logger.info("------------全屏窗口测试：按钮-最小化")
        sleep(0.5)
        d(resourceId="android:id/minimize_window").click()
        sleep(0.5)
        output_res(d(text="回收站").exists)
        d.app_stop_all()
        d.app_stop("com.android.calculator2")
        sleep(0.5)
        d.app_start(package)

    def test_54_fullscreen_button_close(self):
        logger.info("------------全屏窗口测试：按钮-关闭")
        sleep(0.5)
        extendevent.click_blank(d,mouseID)
        sleep(0.5)
        d(resourceId="android:id/close_window").click()
        sleep(0.5)
        output_res(d(text="回收站").exists)
        d.app_stop(package)
        sleep(0.5)
        d.app_start(package)

    def test_61_phonemode_change(self):
        logger.info("------------强制竖屏模式测试：切换")
        sleep(0.5)
        d(resourceId="android:id/setting_window").click()
        d(text="手机模式（竖屏）").click()
        sleep(0.5)
        d(text="OK").click()
        sleep(0.5)
        d.app_start(package)
        sleep(1)
        windowBounds=extendfunction.getParentBounds(d.dump_hierarchy(),'resource-id="android:id/caption"')
        print("实际："+str(windowBounds))
        output_res((windowBounds['right']-windowBounds['left'])==405 and (windowBounds['bottom']-windowBounds['top'])==720)

    def test_62_phonemode_move_left1(self):
        logger.info("------------强制竖屏模式测试：移动-屏幕内左移")
        sleep(0.5)
        caption_x,caption_y = d(resourceId="android:id/caption").center()
        extendevent.drag_2(d,mouseID,caption_x,caption_y,screen_width/2, 100)
        sleep(0.5)
        windowBounds=extendfunction.getParentBounds(d.dump_hierarchy(),'resource-id="android:id/caption"')
        caption_x,caption_y = d(resourceId="android:id/caption").center()
        extendevent.drag_2(d,mouseID,caption_x,caption_y,100+202.5,caption_y)
        check_windowBounds = {'top': windowBounds['top'], 'bottom': windowBounds['top']+720, 'left': 100,'right':100+405}
        sleep(0.5)
        windowBounds=extendfunction.getParentBounds(d.dump_hierarchy(),'resource-id="android:id/caption"')
        print("期望："+str(check_windowBounds))
        print("实际："+str(windowBounds))
        output_res(windowBounds==check_windowBounds)

    def test_63_phonemode_move_rightbottom(self):
        logger.info("------------强制竖屏模式测试：移动-右下")
        sleep(0.5)
        caption_x,caption_y = d(resourceId="android:id/caption").center()
        extendevent.drag_2(d,mouseID,caption_x,caption_y,screen_width-300,screen_height-300)

    def test_64_phonemode_move_top(self):
        logger.info("------------强制竖屏模式测试：移动-上边界")
        sleep(0.5)
        caption_x,caption_y = d(resourceId="android:id/caption").center()
        extendevent.drag_2(d,mouseID,caption_x,caption_y,800,0)
        sleep(0.5)
        windowBounds=extendfunction.getParentBounds(d.dump_hierarchy(),'resource-id="android:id/caption"')
        check_windowBounds = {'top': 0, 'bottom': 720, 'left': windowBounds['left'],'right':windowBounds['left']+405}
        print("期望："+str(check_windowBounds))
        print("实际："+str(windowBounds))
        output_res(windowBounds==check_windowBounds) 

    def test_65_phonemode_doubleclick(self):
        logger.info("------------强制竖屏模式测试：双击")
        sleep(0.5)
        windowBounds=extendfunction.getParentBounds(d.dump_hierarchy(),'resource-id="android:id/caption"')
        extendevent.double_click(d,mouseID,d(resourceId="android:id/caption"))
        sleep(0.5)
        check_windowBounds = {'top': 0, 'bottom': 720, 'left': windowBounds['left'],'right':windowBounds['left']+405}
        windowBounds=extendfunction.getParentBounds(d.dump_hierarchy(),'resource-id="android:id/caption"')
        print("期望："+str(check_windowBounds))
        print("实际："+str(windowBounds))
        output_res(windowBounds==check_windowBounds)

    def test_71_desktopmode_change(self):
        logger.info("------------强制横屏模式测试：切换")
        sleep(0.5)
        d(resourceId="android:id/setting_window").click()
        d(text="手机模式（横屏）").click()
        sleep(0.5)
        d(text="OK").click()
        sleep(0.5)
        d.app_start(package)
        sleep(1)
        windowBounds=extendfunction.getParentBounds(d.dump_hierarchy(),'resource-id="android:id/caption"')
        print("实际："+str(windowBounds))
        output_res((windowBounds['right']-windowBounds['left'])==960 and (windowBounds['bottom']-windowBounds['top'])==540)

    def test_72_desktopmode_move_left1(self):
        logger.info("------------强制横屏模式测试：移动-屏幕内左移")
        sleep(0.5)
        caption_x,caption_y = d(resourceId="android:id/caption").center()
        extendevent.drag_2(d,mouseID,caption_x,caption_y,screen_width/2, 100)
        sleep(0.5)
        caption_x,caption_y = d(resourceId="android:id/caption").center()
        windowBounds=extendfunction.getParentBounds(d.dump_hierarchy(),'resource-id="android:id/caption"')
        extendevent.drag_2(d,mouseID,caption_x,caption_y,480+100,caption_y)
        check_windowBounds = {'top': windowBounds['top'], 'bottom': windowBounds['top']+540, 'left': 100,'right':100+960}
        sleep(0.5)
        windowBounds=extendfunction.getParentBounds(d.dump_hierarchy(),'resource-id="android:id/caption"')
        print("期望："+str(check_windowBounds))
        print("实际："+str(windowBounds))
        output_res(windowBounds==check_windowBounds)

    def test_73_desktopmode_move_rightbottom(self):
        logger.info("------------强制横屏模式测试：移动-右下")
        sleep(0.5)
        caption_x,caption_y = d(resourceId="android:id/caption").center()
        extendevent.drag_2(d,mouseID,caption_x,caption_y,screen_width-300,screen_height-300)

    def test_74_desktopmode_move_top(self):
        logger.info("------------强制横屏模式测试：移动-上边界")
        sleep(0.5)
        windowBounds=extendfunction.getParentBounds(d.dump_hierarchy(),'resource-id="android:id/caption"')
        caption_x,caption_y = d(resourceId="android:id/caption").center()
        extendevent.drag_2(d,mouseID,caption_x,caption_y,screen_width/2,0)
        sleep(0.5)
        windowBounds=extendfunction.getParentBounds(d.dump_hierarchy(),'resource-id="android:id/caption"')
        check_windowBounds = {'top': 0, 'bottom': 540, 'left': windowBounds['left'],'right':windowBounds['left']+960}
        print("期望："+str(check_windowBounds))
        print("实际："+str(windowBounds))
        output_res(windowBounds==check_windowBounds)

    def test_75_desktopmode_doubleclick(self):
        logger.info("------------强制横屏模式测试：双击")
        sleep(0.5)
        windowBounds=extendfunction.getParentBounds(d.dump_hierarchy(),'resource-id="android:id/caption"')
        extendevent.double_click(d,mouseID,d(resourceId="android:id/caption"))
        sleep(0.5)
        check_windowBounds = {'top': 0, 'bottom': 540, 'left': windowBounds['left'],'right':windowBounds['left']+960}
        windowBounds=extendfunction.getParentBounds(d.dump_hierarchy(),'resource-id="android:id/caption"')
        print("期望："+str(check_windowBounds))
        print("实际："+str(windowBounds))
        output_res(windowBounds==check_windowBounds)

    def test_99_end(self):
        logger.info("------------清理结束")
        sleep(0.5)
        d(resourceId="android:id/setting_window").click()
        d(text="标准模式").click()
        sleep(1)
        d(text="OK").click()
        sleep(1)
        d.app_stop_all()

if __name__ == "__main__":
    if len(sys.argv) > 1:
        WindowTestCase.package = sys.argv.pop()
        WindowTestCase.deviceIP = sys.argv.pop()
    unittest.main()
    sleep(2)
    test_file.close()
    d.service("uiautomator").stop()
