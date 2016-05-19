#coding:utf-8
from appium import webdriver
from time import sleep

desired_caps = {}
desired_caps['platformName'] = 'Android'
desired_caps['platformVersion'] = '5.1'
desired_caps['deviceName'] = 'Android Emulator'
desired_caps['app'] = ''
desired_caps['appPackage'] = 'com.android.email'
desired_caps['appActivity'] = 'com.android.email.activity.Welcome'

dr = webdriver.Remote('http://localhost:4723/wd/hub', desired_caps)
sleep(3)

dr.find_element_by_id('account_email').send_keys('523524709@qq.com')
dr.find_element_by_id('next').click()
elements = dr.find_elements_by_class_name("android.widget.Button")
elements[0].click()
dr.find_element_by_id('setup_fragment_content').send_keys('zhangxiaochao135')
dr.find_element_by_id('next').click()
dr.find_element_by_id('account_server').clear()
dr.find_element_by_id('account_server').send_keys('pop.qq.com')
dr.find_element_by_id('text1').click()

dr.find_element_by_xpath("//android.widget.CheckedTextView[contains(@text,'SSL/TLS ')]").click()

#security_type = dr.find_elements_by_class_name("CheckedTextView")
#security_type[0].click()
dr.find_element_by_id('next').click()
sleep(3)
dr.find_element_by_id('account_server').clear()
dr.find_element_by_id('account_server').send_keys('smtp.qq.com')
dr.find_element_by_id('text1').click()
dr.find_element_by_xpath("//android.widget.CheckedTextView[contains(@text,'SSL/TLS ')]").click()
dr.find_element_by_id('next').click()
sleep(3)

dr.find_element_by_id('next').click()
sleep(3)

dr.find_element_by_id('next').click()
dr.find_element_by_id('mwMaximizeBtn').click()
dr.find_element_by_id('mwCloseBtn').click()
dr.quit()



