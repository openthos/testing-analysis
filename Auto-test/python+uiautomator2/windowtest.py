# -*- coding: UTF-8 -*-

import uiautomator2 as u2
import unittest
from time import sleep
import extendevent
import extendfunction
import sys
from logzero import logger

deviceIP=sys.argv[1]
d = u2.connect(deviceIP)
f = open('./tmp1234.txt')
package_list=f.read()
f.close()
package=sys.argv[2]
windowBounds=""


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
    if (direction=="plus"):
        i=100
    else:
        i=-100
    currentApp_top=windowBounds['top']
    currentApp_bottom=windowBounds['bottom']
    currentApp_left=windowBounds['left']
    currentApp_right=windowBounds['right']
    currentApp_hcenter=(windowBounds['left']+windowBounds['right'])/2
    currentApp_vcenter=(windowBounds['top']+windowBounds['bottom'])/2
    if (orientation=="top"):
        d.drag(currentApp_hcenter,currentApp_top-1,currentApp_hcenter,currentApp_top-1+i,0.1)
        check_windowBounds = {'top': windowBounds['top']+i, 'bottom': windowBounds['bottom'], 'left': windowBounds['left'],'right':windowBounds['right']}
    elif(orientation=="bottom"):
        d.drag(currentApp_hcenter,currentApp_bottom+1,currentApp_hcenter,currentApp_bottom+1-i,0.1)
        check_windowBounds = {'top': windowBounds['top'], 'bottom': windowBounds['bottom']-i, 'left': windowBounds['left'],'right':windowBounds['right']}
    elif(orientation=="left"):
        d.drag(currentApp_left-1,currentApp_vcenter,currentApp_left-1+i,currentApp_vcenter,0.1)
        check_windowBounds = {'top': windowBounds['top'], 'bottom': windowBounds['bottom'], 'left': windowBounds['left']+i,'right':windowBounds['right']}
    elif(orientation=="right"):
        d.drag(currentApp_right+1,currentApp_vcenter,currentApp_right+1-i,currentApp_vcenter,0.1)
        check_windowBounds = {'top': windowBounds['top'], 'bottom': windowBounds['bottom'], 'left': windowBounds['left'],'right':windowBounds['right']-i}
    elif(orientation=="left-top"):
        d.drag(currentApp_left-1,currentApp_top-1,currentApp_left-1+i,currentApp_top-1+i,0.1)
        check_windowBounds = {'top': windowBounds['top']+i, 'bottom': windowBounds['bottom'], 'left': windowBounds['left']+i,'right':windowBounds['right']}
    elif(orientation=="left-bottom"):
        d.drag(currentApp_left-1,currentApp_bottom+1,currentApp_left-1+i,currentApp_bottom+1-i,0.1)
        check_windowBounds = {'top': windowBounds['top'], 'bottom': windowBounds['bottom']-i, 'left': windowBounds['left']+i,'right':windowBounds['right']}
    elif(orientation=="right-top"):
        d.drag(currentApp_right+1,currentApp_top-1,currentApp_right+1-i,currentApp_top-1+i,0.1)
        check_windowBounds = {'top': windowBounds['top']+i, 'bottom': windowBounds['bottom'], 'left': windowBounds['left'],'right':windowBounds['right']-i}
    else:
        d.drag(currentApp_right+1,currentApp_bottom+1,currentApp_right+1-i,currentApp_bottom+1-i,0.1)
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
        d(resourceId="android:id/caption").drag_to(860, 90, duration=0.5)
        sleep(1)

    def test_01_resize_top(self):
        logger.info("------------标准窗口测试：调整大小-上边框")
        windowBounds = resize("top","plus")
        windowBounds = resize("top","minus")

    def test_02_resize_bottom(self):
        logger.info("------------标准窗口测试：调整大小-下边框")
        windowBounds = resize("bottom","plus")
        windowBounds = resize("bottom","minus")

    def test_03_resize_left(self):
        logger.info("------------标准窗口测试：调整大小-左边框")
        windowBounds = resize("left","plus")
        windowBounds = resize("left","minus")

    def test_04_resize_right(self):
        logger.info("------------标准窗口测试：调整大小-右边框")
        windowBounds = resize("right","plus")
        windowBounds = resize("right","minus")

    def test_05_resize_lefttop(self):
        logger.info("------------标准窗口测试：调整大小-左上边框")
        windowBounds = resize("left-top","plus")
        windowBounds = resize("left-top","minus")

    def test_06_resize_leftbottom(self):
        logger.info("------------标准窗口测试：调整大小-左下边框")
        windowBounds = resize("left-bottom","plus")
        windowBounds = resize("left-bottom","minus")

    def test_07_resize_righttop(self):
        logger.info("------------标准窗口测试：调整大小-右上边框")
        windowBounds = resize("right-top","plus")
        windowBounds = resize("right-top","minus")

    def test_08_resize_rightbottom(self):
        logger.info("------------标准窗口测试：调整大小-右下边框")
        windowBounds = resize("right-bottom","plus")
        windowBounds = resize("right-bottom","minus")

    def test_11_move_left1(self):
        logger.info("------------标准窗口测试：移动-屏幕内左移")
        caption_x,caption_y = d(resourceId="android:id/caption").center()
        windowBounds=extendfunction.getParentBounds(d.dump_hierarchy(),'resource-id="android:id/caption"')
        window_width=windowBounds['right']-windowBounds['left']
        window_height=windowBounds['top']-windowBounds['bottom']
        d.drag(caption_x,caption_y,window_width/2+100,caption_y,0.1)
        check_windowBounds = {'top': windowBounds['top'], 'bottom': windowBounds['bottom'], 'left': 100,'right':100+window_width}
        windowBounds=extendfunction.getParentBounds(d.dump_hierarchy(),'resource-id="android:id/caption"')
        print(check_windowBounds)
        print(windowBounds)
        output_res(windowBounds==check_windowBounds)

    def test_12_move_right1(self):
        logger.info("------------标准窗口测试：移动-屏幕内右移")
        caption_x,caption_y = d(resourceId="android:id/caption").center()
        windowBounds=extendfunction.getParentBounds(d.dump_hierarchy(),'resource-id="android:id/caption"')
        window_width=windowBounds['right']-windowBounds['left']
        window_height=windowBounds['top']-windowBounds['bottom']
        d.drag(caption_x,caption_y,1600-100-window_width/2,caption_y,0.1)
        check_windowBounds = {'top': windowBounds['top'], 'bottom': windowBounds['bottom'], 'left': 1600-100-window_width,'right':1600-100}
        windowBounds=extendfunction.getParentBounds(d.dump_hierarchy(),'resource-id="android:id/caption"')
        print(check_windowBounds)
        print(windowBounds)
        output_res(windowBounds==check_windowBounds)

    def test_13_move_up1(self):
        logger.info("------------标准窗口测试：移动-屏幕内上移")

    def test_14_move_down1(self):
        logger.info("------------标准窗口测试：移动-屏幕内下移")

    def test_15_move_left2(self):
        logger.info("------------标准窗口测试：移动-出屏幕左移")

    def test_16_move_right2(self):
        logger.info("------------标准窗口测试：移动-出屏幕右移")

    def test_17_move_leftdown(self):
        logger.info("------------标准窗口测试：移动-出屏幕左下")

    def test_18_move_rightdown(self):
        logger.info("------------标准窗口测试：移动-出屏幕右下")

    def test_19_move_top(self):
        logger.info("------------标准窗口测试：移动-上边界")

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
x, y = d(text="Settings").center()
