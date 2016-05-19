#!/usr/bin/python
#coding:utf-8
from appium import webdriver
from time import sleep
import traceback  
import sys 
def trace_back():  
    try:  
        return traceback.print_exc()  
    except:  
        return '' 
desired_caps = {}
desired_caps['platformName'] = 'Android'
desired_caps['platformVersion'] = '5.1'
desired_caps['deviceName'] = 'Android Emulator'
desired_caps['app'] = ''
desired_caps['appPackage'] = 'com.android.browser'
desired_caps['appActivity'] = '.BrowserActivity'

#desired_caps['appPackage'] = 'org.mozilla.firefox'
#desired_caps['appActivity'] = 'org.mozilla.firefox.App'

dr = webdriver.Remote('http://localhost:4723/wd/hub', desired_caps)

sleep(3)
#dr.maximize_window()
try:  
    dr.find_element_by_id('mwMaximizeBtn').click()
except:  
    print trace_back() 
#url_bar_entry
dr.find_element_by_id('url').clear()
dr.find_element_by_id('url').send_keys('http://xw.qq.com')
dr.keyevent(66)
sleep(1)
#os.popen('adb shell input keyevent 123')
dr.keyevent(123)

contexts = dr.context
print(contexts)
#dr.context('WEBVIEW_0')
dr.swipe(300, 970, 300, 20)

 

