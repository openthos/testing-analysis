# -*- coding: UTF-8 -*-

import uiautomator2 as u2
import unittest
from time import sleep
import extendevent
import sys
from logzero import logger

d = u2.connect("192.168.0.73")
f = open('/home/qin/mydata/auto-test/tmp1234.txt')
package_list=f.read()
f.close()

count=1
list=package_list.split("\n")
for package in list:
    d.app_start(package)
    sleep(1)
    if count==20:
        exit()
    else:
        count=count+1
