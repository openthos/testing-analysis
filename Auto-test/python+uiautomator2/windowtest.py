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
        print("通过")
    else :
        #test_file.write("**不通过**\n")
        print("不通过！！！")

def resize(orientation,direction):
    windowBounds=extendfunction.getParentBounds(d.dump_hierarchy(),'resource-id="android:id/caption"')
    print(windowBounds)
    
    i=direction*100
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
    print(check_windowBounds)
    print(windowBounds)
    return windowBounds


class WindowTestCase(unittest.TestCase):

    def test_00_prepare(self):
        logger.info("------------标准窗口测试：测试准备-打开应用")
        d.app_start(package)
        sleep(5)
        logger.info("------------标准窗口测试：测试准备-移动窗口到屏幕中间")
        d(resourceId="android:id/caption").drag_to(860, 90, duration=0.1)
        sleep(1)

    def test_01_resize_top(self):
        logger.info("------------标准窗口测试：调整大小-上边框")
        windowBounds = resize("top",1)
        windowBounds = resize("top",-1)

    def test_02_resize_bottom(self):
        logger.info("------------标准窗口测试：调整大小-下边框")
        windowBounds = resize("bottom",1)
        windowBounds = resize("bottom",-1)

    def test_03_resize_left(self):
        logger.info("------------标准窗口测试：调整大小-左边框")
        windowBounds = resize("left",1)
        windowBounds = resize("left",-1)

    def test_04_resize_right(self):
        logger.info("------------标准窗口测试：调整大小-右边框")
        windowBounds = resize("right",1)
        windowBounds = resize("right",-1)

    def test_05_resize_lefttop(self):
        logger.info("------------标准窗口测试：调整大小-左上边框")
        windowBounds = resize("left-top",1)
        windowBounds = resize("left-top",-1)

    def test_06_resize_leftbottom(self):
        logger.info("------------标准窗口测试：调整大小-左下边框")
        windowBounds = resize("left-bottom",1)
        windowBounds = resize("left-bottom",-1)

    def test_07_resize_righttop(self):
        logger.info("------------标准窗口测试：调整大小-右上边框")
        windowBounds = resize("right-top",1)
        windowBounds = resize("right-top",-1)

    def test_08_resize_rightbottom(self):
        logger.info("------------标准窗口测试：调整大小-右下边框")
        windowBounds = resize("right-bottom",1)
        windowBounds = resize("right-bottom",-1)

    def test_11_move_left1(self):
        logger.info("------------标准窗口测试：移动-屏幕内左移")
        caption_x,caption_y = d(resourceId="android:id/caption").center()
        windowBounds=extendfunction.getParentBounds(d.dump_hierarchy(),'resource-id="android:id/caption"')
        window_width=windowBounds['right']-windowBounds['left']
        extendevent.drag_2(d,mouseID,caption_x,caption_y,window_width/2+100,caption_y)
        check_windowBounds = {'top': windowBounds['top'], 'bottom': windowBounds['bottom'], 'left': 100,'right':100+window_width}
        sleep(0.5)
        windowBounds=extendfunction.getParentBounds(d.dump_hierarchy(),'resource-id="android:id/caption"')
        print(check_windowBounds)
        print(windowBounds)
        output_res(windowBounds==check_windowBounds)

    def test_12_move_right1(self):
        logger.info("------------标准窗口测试：移动-屏幕内右移")
        caption_x,caption_y = d(resourceId="android:id/caption").center()
        windowBounds=extendfunction.getParentBounds(d.dump_hierarchy(),'resource-id="android:id/caption"')
        window_width=windowBounds['right']-windowBounds['left']
        extendevent.drag_2(d,mouseID,caption_x,caption_y,screen_width-100-window_width/2,caption_y)
        check_windowBounds = {'top': windowBounds['top'], 'bottom': windowBounds['bottom'], 'left': screen_width-100-window_width,'right':screen_width-100}
        sleep(0.5)
        windowBounds=extendfunction.getParentBounds(d.dump_hierarchy(),'resource-id="android:id/caption"')
        print(check_windowBounds)
        print(windowBounds)
        output_res(windowBounds==check_windowBounds)

    def test_13_move_up1(self):
        logger.info("------------标准窗口测试：移动-屏幕内上移")
        caption_x,caption_y = d(resourceId="android:id/caption").center()
        windowBounds=extendfunction.getParentBounds(d.dump_hierarchy(),'resource-id="android:id/caption"')
        window_width=windowBounds['right']-windowBounds['left']
        window_height=windowBounds['bottom']-windowBounds['top']
        caption_bounds=d(resourceId="android:id/caption").info.get('visibleBounds')
        caption_height=caption_bounds['bottom']-caption_bounds['top']
        extendevent.drag_2(d,mouseID,caption_x,caption_y,screen_width/2,100)
        check_windowBounds = {'top': 100-caption_height/2, 'bottom': (100-caption_height/2)+window_height, 'left': screen_width/2-window_width/2,'right': screen_width/2+window_width/2}
        sleep(0.5)
        windowBounds=extendfunction.getParentBounds(d.dump_hierarchy(),'resource-id="android:id/caption"')
        print(check_windowBounds)
        print(windowBounds)
        output_res(windowBounds==check_windowBounds)

    def test_14_move_down1(self):
        logger.info("------------标准窗口测试：移动-屏幕内下移")
        caption_x,caption_y = d(resourceId="android:id/caption").center()
        windowBounds=extendfunction.getParentBounds(d.dump_hierarchy(),'resource-id="android:id/caption"')
        window_height=windowBounds['top']-windowBounds['bottom']
        extendevent.drag_2(d,mouseID,caption_x,caption_y,caption_x,800-window_height)

    def test_15_move_left2(self):
        logger.info("------------标准窗口测试：移动-出屏幕左移")
        caption_x,caption_y = d(resourceId="android:id/caption").center()
        extendevent.drag_2(d,mouseID,caption_x,caption_y,20,100)

    def test_16_move_right2(self):
        logger.info("------------标准窗口测试：移动-出屏幕右移")
        caption_x,caption_y = d(resourceId="android:id/caption").center()
        extendevent.drag_2(d,mouseID,caption_x,caption_y,1900,100)

    def test_17_move_leftdown(self):
        logger.info("------------标准窗口测试：移动-出屏幕左下")
        caption_x,caption_y = d(resourceId="android:id/caption").center()
        extendevent.drag_2(d,mouseID,caption_x,caption_y,100,700)

    def test_18_move_rightdown(self):
        logger.info("------------标准窗口测试：移动-出屏幕右下")
        caption_x,caption_y = d(resourceId="android:id/caption").center()
        extendevent.drag_2(d,mouseID,caption_x,caption_y,1900,700)

    def test_19_move_top(self):
        logger.info("------------标准窗口测试：移动-上边界")
        caption_x,caption_y = d(resourceId="android:id/caption").center()
        extendevent.drag_2(d,mouseID,caption_x,caption_y,800,0)
        check_windowBounds = {'top': 0, 'bottom': screen_height, 'left': 0,'right':screen_width}
        sleep(0.5)
        extendevent.click_blank(d,mouseID)
        windowBounds=extendfunction.getParentBounds(d.dump_hierarchy(),'resource-id="android:id/caption"')
        print(check_windowBounds)
        print(windowBounds)
        output_res(windowBounds==check_windowBounds)

    def test_20_move_leftside(self):
        logger.info("------------标准窗口测试：移动-左边界")

    def test_21_move_rightside(self):
        logger.info("------------标准窗口测试：移动-右边界")

    def test_22_doubleclick(self):
        logger.info("------------标准窗口测试：双击")

    def test_31_button_minimum(self):
        logger.info("------------标准窗口测试：按钮-最小化")

    def test_32_button_max(self):
        logger.info("------------标准窗口测试：按钮-最大化")

    def test_33_button_close(self):
        logger.info("------------标准窗口测试：按钮-关闭")

    def test_34_button_setting(self):
        logger.info("------------标准窗口测试：按钮-设置")

    def test_51_fullscreen_button(self):
        logger.info("------------全屏窗口测试：按钮")


if __name__ == "__main__":
    if len(sys.argv) > 1:
        WindowTestCase.package = sys.argv.pop()
        WindowTestCase.deviceIP = sys.argv.pop()
    unittest.main()
    sleep(2)
    test_file.close()
    d.service("uiautomator").stop()
