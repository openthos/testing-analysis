#!/usr/bin/python
# -*- coding: UTF-8 -*-

import uiautomator2 as u2
from time import sleep
import extendevent

d = u2.connect('192.168.0.69')
print(d.info)
mouseID=extendevent.getmouseID(d)

d.app_start("org.openthos.filemanager")
# d(resourceId="org.openthos.filemanager:id/iv_setting").click()
sleep(2)
targeticon=d(text='桌面', className='android.widget.TextView')
extendevent.left_click(d,mouseID,targeticon)
targeticon=d(text='文档', className='android.widget.TextView')
extendevent.left_click(d,mouseID,targeticon)
targeticon=d(text='下载', className='android.widget.TextView')
extendevent.left_click(d,mouseID,targeticon)
targeticon=d(text='视频', className='android.widget.TextView')
extendevent.left_click(d,mouseID,targeticon)
targeticon=d(text='音乐', className='android.widget.TextView')
extendevent.left_click(d,mouseID,targeticon)
targeticon=d(text='图片', className='android.widget.TextView')
extendevent.left_click(d,mouseID,targeticon)
targeticon=d(resourceId='org.openthos.filemanager:id/file_path_grid')
extendevent.middle_click(d,mouseID,targeticon)
sleep(5)
d.app_stop("org.openthos.filemanager")
d.service("uiautomator").stop()
