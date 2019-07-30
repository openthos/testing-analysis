#!/usr/bin/python

import uiautomator2 as u2
from time import sleep
from lxml import etree
from lxml.etree import fromstring
import re

def getParentBounds(uidump,selector):
    xml = bytes(bytearray(uidump, encoding='utf-8'))
    d=etree.HTML(xml)
    strbounds=str(d.xpath('//node[@'+selector+']/parent::*/@bounds'))
    list1=re.split("\[|\]|,",strbounds)
    parentbounds= {'top': int(list1[3]), 'bottom': int(list1[6]), 'left': int(list1[2]),'right':int(list1[5])}
    return parentbounds

