#!/usr/bin/python
# -*- coding: UTF-8 -*-

import uiautomator2 as u2
from time import sleep
import extendevent

d = u2.connect('192.168.0.69')
mouseID=extendevent.getmouseID(d)

targeticon=d(resourceId='org.openthos.filemanager:id/file_path_grid')
extendevent.roller_down(d,mouseID,targeticon,7)
sleep(5)
d.service("uiautomator").stop()
