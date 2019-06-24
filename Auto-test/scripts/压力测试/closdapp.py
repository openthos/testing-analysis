import uiautomator2 as u2
import unittest
from time import sleep
import extendevent
import sys
from logzero import logger

d = u2.connect("192.168.0.73")
d.app_stop_all()
