#coding:utf-8
from appium import webdriver
from time import sleep

desired_caps = {}
desired_caps['platformName'] = 'Android'
desired_caps['platformVersion'] = '5.1'
desired_caps['deviceName'] = 'Android Emulator'
desired_caps['app'] = ''
desired_caps['appPackage'] = 'com.android.calculator2'
desired_caps['appActivity'] = 'com.android.calculator2.Calculator'

dr = webdriver.Remote('http://localhost:4723/wd/hub', desired_caps)
sleep(3)

dr.find_element_by_id('com.android.calculator2:id/digit_9').click()
dr.find_element_by_id('com.android.calculator2:id/op_add').click()
dr.find_element_by_id('com.android.calculator2:id/digit_8').click()
dr.find_element_by_id('com.android.calculator2:id/eq').click()


dr.quit()

