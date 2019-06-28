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
f = open('/home/qin/mydata/auto-test/tmp1234.txt')
package_list=f.read()
f.close()
package=""
windowBounds=""

def resize(orientation,windowBounds,direction):
    if (direction=="plus"):
        i=100
    else:
        i=-100
    currentApp_top=windowBounds['top']
    currentApp_bottom=windowBounds['bottom']
    currentApp_left=windowBounds['left']
    currentApp_right=windowBounds['right']
    if (orientation=="top"):
        d.drag(currentApp_right-1,currentApp_top-1,currentApp_right-1,currentApp_top+i,0.5)
    elif(orientation=="bottom"):
        d.drag(currentApp_right-1,currentApp_bottom+1,currentApp_right-1,currentApp_bottom-i,0.5)
    elif(orientation=="left"):
        d.drag(currentApp_left-1,currentApp_top+1,currentApp_left+i,currentApp_top+1,0.5)
    else:
        d.drag(currentApp_right+1,currentApp_top+1,currentApp_right-i,currentApp_top+1,0.5)

    sleep(1)



class WindowTestCase(unittest.TestCase):
    # 测试准备
    def setUp(self):
        sleep(0.5)

    # 测试后清理
    def tearDown(self):
        sleep(0.5)

    # 测试准备阶段
    # 删除常用软件列表中的应用
    def test_00_prepare(self):
        logger.info("------------标准窗口测试：上下左右调整大小")
        count=1
        list=package_list.split("\n")
        for package in list:
            d.app_start(package)
            sleep(1)
            if count==1:
                break;
            else:
                count=count+1
        windowBounds=extendfunction.getParentBounds(d.dump_hierarchy(),'resource-id="android:id/caption"')
        print(windowBounds)
        resize("top",windowBounds,"plus")
        windowBounds=extendfunction.getParentBounds(d.dump_hierarchy(),'resource-id="android:id/caption"')
        print(windowBounds)
        resize("top",windowBounds,"minus")
        windowBounds=extendfunction.getParentBounds(d.dump_hierarchy(),'resource-id="android:id/caption"')
        print(windowBounds)
        resize("bottom",windowBounds,"plus")
        windowBounds=extendfunction.getParentBounds(d.dump_hierarchy(),'resource-id="android:id/caption"')
        print(windowBounds)
        resize("bottom",windowBounds,"minus")
        windowBounds=extendfunction.getParentBounds(d.dump_hierarchy(),'resource-id="android:id/caption"')
        print(windowBounds)
        resize("left",windowBounds,"plus")
        windowBounds=extendfunction.getParentBounds(d.dump_hierarchy(),'resource-id="android:id/caption"')
        print(windowBounds)
        resize("left",windowBounds,"minus")
        windowBounds=extendfunction.getParentBounds(d.dump_hierarchy(),'resource-id="android:id/caption"')
        print(windowBounds)
        resize("right",windowBounds,"plus")
        windowBounds=extendfunction.getParentBounds(d.dump_hierarchy(),'resource-id="android:id/caption"')
        print(windowBounds)
        resize("right",windowBounds,"minus")

if __name__ == "__main__":
    if len(sys.argv) > 1:
        WindowTestCase.deviceIP = sys.argv.pop()

    unittest.main()
    sleep(2)
    test_file.close()
    d.service("uiautomator").stop()

