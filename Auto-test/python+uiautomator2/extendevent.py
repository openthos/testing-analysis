#!/usr/bin/python
# -*- coding: UTF-8 -*-

import uiautomator2 as u2
from time import sleep

def getmouseID(d):
    mouseinfo=d.shell("cat /proc/bus/input/devices | grep mouse0")
    event_id=mouseinfo[0][24:26]
    # print(event_id)
    return event_id

def right_click(d,event_id,targeticon):
    # find the position to right click
    target_bounds=targeticon.info.get('visibleBounds')
    x_position=(target_bounds['left']+target_bounds['right'])/2
    y_position=(target_bounds['top']+target_bounds['bottom'])/2
    
    # move mouse to the left top of the screen
    d.shell("sendevent /dev/input/event"+event_id+" 2 0 -2000")
    d.shell("sendevent /dev/input/event"+event_id+" 2 1 -2000")
    d.shell("sendevent /dev/input/event"+event_id+" 0 0 0")
    # move mouse to the position to right click
    d.shell("sendevent /dev/input/event"+event_id+" 2 0 "+str(x_position))
    d.shell("sendevent /dev/input/event"+event_id+" 2 1 "+str(y_position))
    d.shell("sendevent /dev/input/event"+event_id+" 0 0 0")
    # send event of right click
    d.shell("sendevent /dev/input/event"+event_id+" 0004 0004 00090002;"
            +"sendevent /dev/input/event"+event_id+" 0001 0273 00000001;"
            +"sendevent /dev/input/event"+event_id+" 0000 0000 00000000;"
            +"sendevent /dev/input/event"+event_id+" 0004 0004 00090002;"
            +"sendevent /dev/input/event"+event_id+" 0001 0273 00000000;"
            +"sendevent /dev/input/event"+event_id+" 0000 0000 00000000")
    return True

def left_click(d,event_id,targeticon):
    # find the position to left click
    target_bounds=targeticon.info.get('visibleBounds')
    x_position=(target_bounds['left']+target_bounds['right'])/2 
    y_position=(target_bounds['top']+target_bounds['bottom'])/2

    # move mouse to the left top of the screen
    d.shell("sendevent /dev/input/event"+event_id+" 2 0 -2000")
    d.shell("sendevent /dev/input/event"+event_id+" 2 1 -2000")
    d.shell("sendevent /dev/input/event"+event_id+" 0 0 0")
    # move mouse to the position to left click
    d.shell("sendevent /dev/input/event"+event_id+" 2 0 "+str(x_position))
    d.shell("sendevent /dev/input/event"+event_id+" 2 1 "+str(y_position))
    d.shell("sendevent /dev/input/event"+event_id+" 0 0 0")
    # send event of left click
    d.shell("sendevent /dev/input/event"+event_id+" 0004 0004 00090001;"
            +"sendevent /dev/input/event"+event_id+" 0001 0272 00000001;"
            +"sendevent /dev/input/event"+event_id+" 0000 0000 00000000;"
            +"sendevent /dev/input/event"+event_id+" 0004 0004 00090001;"
            +"sendevent /dev/input/event"+event_id+" 0001 0272 00000000;"
            +"sendevent /dev/input/event"+event_id+" 0000 0000 00000000")
    return True

def middle_click(d,event_id,targeticon):
    # find the position to left click
    target_bounds=targeticon.info.get('visibleBounds')
    x_position=(target_bounds['left']+target_bounds['right'])/2
    y_position=(target_bounds['top']+target_bounds['bottom'])/2

    # move mouse to the left top of the screen
    d.shell("sendevent /dev/input/event"+event_id+" 2 0 -2000")
    d.shell("sendevent /dev/input/event"+event_id+" 2 1 -2000")
    d.shell("sendevent /dev/input/event"+event_id+" 0 0 0")  
    # move mouse to the position to middle click
    d.shell("sendevent /dev/input/event"+event_id+" 2 0 "+str(x_position))
    d.shell("sendevent /dev/input/event"+event_id+" 2 1 "+str(y_position))
    d.shell("sendevent /dev/input/event"+event_id+" 0 0 0")
    # send event of middle click
    d.shell("sendevent /dev/input/event"+event_id+" 0004 0004 589827;"
            +"sendevent /dev/input/event"+event_id+" 0001 0274 00000001;"
            +"sendevent /dev/input/event"+event_id+" 0000 0000 00000000;"
            +"sendevent /dev/input/event"+event_id+" 0004 0004 589827;"
            +"sendevent /dev/input/event"+event_id+" 0001 0274 00000000;"
            +"sendevent /dev/input/event"+event_id+" 0000 0000 00000000")
    return True

def double_click(d,event_id,targeticon):
    x_position,y_position=targeticon.center()
    # move mouse to the left top of the screen
    d.shell("sendevent /dev/input/event"+event_id+" 2 0 -2000")
    d.shell("sendevent /dev/input/event"+event_id+" 2 1 -2000")
    d.shell("sendevent /dev/input/event"+event_id+" 0 0 0")
    # move mouse to the position to double click
    d.shell("sendevent /dev/input/event"+event_id+" 2 0 "+str(x_position))
    d.shell("sendevent /dev/input/event"+event_id+" 2 1 "+str(y_position))
    d.shell("sendevent /dev/input/event"+event_id+" 0 0 0")
    # double click
    d.shell("sendevent /dev/input/event"+event_id+" 4 4 589825")
    d.shell("sendevent /dev/input/event"+event_id+" 1 272 1")
    d.shell("sendevent /dev/input/event"+event_id+" 0 0 0")
    d.shell("sendevent /dev/input/event"+event_id+" 4 4 589825")
    d.shell("sendevent /dev/input/event"+event_id+" 1 272 0")
    d.shell("sendevent /dev/input/event"+event_id+" 0 0 0")
    sleep(0.3)
    d.shell("sendevent /dev/input/event"+event_id+" 4 4 589825")
    d.shell("sendevent /dev/input/event"+event_id+" 1 272 1")
    d.shell("sendevent /dev/input/event"+event_id+" 0 0 0")
    d.shell("sendevent /dev/input/event"+event_id+" 4 4 589825")
    d.shell("sendevent /dev/input/event"+event_id+" 1 272 0")
    d.shell("sendevent /dev/input/event"+event_id+" 0 0 0")
    return True

def roller_down(d,event_id,targeticon,step):
    # find the position to roll
    target_bounds=targeticon.info.get('visibleBounds')
    x_position=(target_bounds['left']+target_bounds['right'])/2
    y_position=(target_bounds['top']+target_bounds['bottom'])/2

    # move mouse to the left top of the screen
    d.shell("sendevent /dev/input/event"+event_id+" 2 0 -2000")
    d.shell("sendevent /dev/input/event"+event_id+" 2 1 -2000")
    d.shell("sendevent /dev/input/event"+event_id+" 0 0 0")
    # move mouse to the position to roll
    d.shell("sendevent /dev/input/event"+event_id+" 2 0 "+str(x_position))
    d.shell("sendevent /dev/input/event"+event_id+" 2 1 "+str(y_position))
    d.shell("sendevent /dev/input/event"+event_id+" 0 0 0")
    # send event of roll
    for i in range(1,step+1):
        d.shell("sendevent /dev/input/event" + event_id + " 2 8 -1")
        d.shell("sendevent /dev/input/event" + event_id + " 0 0 0")
        sleep(0.5)

    return True

def roller_up(d,event_id,targeticon,step):
    # find the position to roll
    target_bounds=targeticon.info.get('visibleBounds')
    x_position=(target_bounds['left']+target_bounds['right'])/2
    y_position=(target_bounds['top']+target_bounds['bottom'])/2

    # move mouse to the left top of the screen
    d.shell("sendevent /dev/input/event"+event_id+" 2 0 -2000")
    d.shell("sendevent /dev/input/event"+event_id+" 2 1 -2000")
    d.shell("sendevent /dev/input/event"+event_id+" 0 0 0")
    # move mouse to the position to roll
    d.shell("sendevent /dev/input/event"+event_id+" 2 0 "+str(x_position))
    d.shell("sendevent /dev/input/event"+event_id+" 2 1 "+str(y_position))
    d.shell("sendevent /dev/input/event"+event_id+" 0 0 0")
    # send event of roll
    for i in range(1,step+1):
        d.shell("sendevent /dev/input/event" + event_id + " 2 8 1")
        d.shell("sendevent /dev/input/event" + event_id + " 0 0 0")
        sleep(0.5)

    return True

def open_startmenu(d,event_id):
    # move mouse to the left bottom of the screen
    d.shell("sendevent /dev/input/event"+event_id+" 2 0 -2000")
    d.shell("sendevent /dev/input/event"+event_id+" 2 1 2000")
    d.shell("sendevent /dev/input/event"+event_id+" 0 0 0")
    # move mouse to the position to left click
    d.shell("sendevent /dev/input/event"+event_id+" 2 0 20")
    d.shell("sendevent /dev/input/event"+event_id+" 2 1 -20")
    d.shell("sendevent /dev/input/event"+event_id+" 0 0 0")
    # send event of left click
    d.shell("sendevent /dev/input/event"+event_id+" 0004 0004 00090001;"
            +"sendevent /dev/input/event"+event_id+" 0001 0272 00000001;"
            +"sendevent /dev/input/event"+event_id+" 0000 0000 00000000;"
            +"sendevent /dev/input/event"+event_id+" 0004 0004 00090001;"
            +"sendevent /dev/input/event"+event_id+" 0001 0272 00000000;"
            +"sendevent /dev/input/event"+event_id+" 0000 0000 00000000")
    return True

def click_blank(d,event_id):
    # move mouse to the right top of the screen
    d.shell("sendevent /dev/input/event"+event_id+" 2 0 2000")
    d.shell("sendevent /dev/input/event"+event_id+" 2 1 -2000")
    d.shell("sendevent /dev/input/event"+event_id+" 0 0 0")
    # send event of left click
    d.shell("sendevent /dev/input/event"+event_id+" 0004 0004 00090001;"
            +"sendevent /dev/input/event"+event_id+" 0001 0272 00000001;"
            +"sendevent /dev/input/event"+event_id+" 0000 0000 00000000;"
            +"sendevent /dev/input/event"+event_id+" 0004 0004 00090001;"
            +"sendevent /dev/input/event"+event_id+" 0001 0272 00000000;"
            +"sendevent /dev/input/event"+event_id+" 0000 0000 00000000")
    return True

def drag(d,event_id,sx,sy,ex,ey):
    # move mouse to the left top of the screen
    d.shell("sendevent /dev/input/event"+event_id+" 2 0 -2000")
    d.shell("sendevent /dev/input/event"+event_id+" 2 1 -2000")
    d.shell("sendevent /dev/input/event"+event_id+" 0 0 0")
    # move mouse to the position to left click
    d.shell("sendevent /dev/input/event"+event_id+" 2 0 "+str(sx))
    d.shell("sendevent /dev/input/event"+event_id+" 2 1 "+str(sy))
    d.shell("sendevent /dev/input/event"+event_id+" 0 0 0")
    # send event of drag
    d.shell("sendevent /dev/input/event"+event_id+" 4 4 589825")
    d.shell("sendevent /dev/input/event"+event_id+" 1 272 1")
    d.shell("sendevent /dev/input/event"+event_id+" 0 0 0")
    d.shell("sendevent /dev/input/event"+event_id+" 2 0 "+str(ex-sx))
    d.shell("sendevent /dev/input/event"+event_id+" 2 1 "+str(ey-sy))
    d.shell("sendevent /dev/input/event"+event_id+" 0 0 0")
    d.shell("sendevent /dev/input/event"+event_id+" 4 4 589825")
    d.shell("sendevent /dev/input/event"+event_id+" 1 272 0")
    d.shell("sendevent /dev/input/event"+event_id+" 0 0 0")
    return True

def drag_2(d,event_id,sx,sy,ex,ey):
    # 移动窗口用这个（移动窗口时需要用移动两次，否则无效）
    # move mouse to the left top of the screen
    d.shell("sendevent /dev/input/event"+event_id+" 2 0 -2000")
    d.shell("sendevent /dev/input/event"+event_id+" 2 1 -2000")
    d.shell("sendevent /dev/input/event"+event_id+" 0 0 0")
    # move mouse to the position to left click
    d.shell("sendevent /dev/input/event"+event_id+" 2 0 "+str(sx))
    d.shell("sendevent /dev/input/event"+event_id+" 2 1 "+str(sy))
    d.shell("sendevent /dev/input/event"+event_id+" 0 0 0")
    # send event of drag
    d.shell("sendevent /dev/input/event"+event_id+" 4 4 589825")
    d.shell("sendevent /dev/input/event"+event_id+" 1 272 1")
    d.shell("sendevent /dev/input/event"+event_id+" 0 0 0")
    d.shell("sendevent /dev/input/event"+event_id+" 2 0 1")
    d.shell("sendevent /dev/input/event"+event_id+" 2 1 1")
    d.shell("sendevent /dev/input/event"+event_id+" 0 0 0")
    d.shell("sendevent /dev/input/event"+event_id+" 2 0 "+str(ex-sx))
    d.shell("sendevent /dev/input/event"+event_id+" 2 1 "+str(ey-sy))
    d.shell("sendevent /dev/input/event"+event_id+" 0 0 0")
    d.shell("sendevent /dev/input/event"+event_id+" 4 4 589825")
    d.shell("sendevent /dev/input/event"+event_id+" 1 272 0")
    d.shell("sendevent /dev/input/event"+event_id+" 0 0 0")
    return True

def hover_00(d,event_id):
    # 移动窗口用这个（移动窗口时需要用移动两次，否则无效）
    # move mouse to the left top of the screen
    d.shell("sendevent /dev/input/event"+event_id+" 2 0 -2000")
    d.shell("sendevent /dev/input/event"+event_id+" 2 1 -2000")
    d.shell("sendevent /dev/input/event"+event_id+" 0 0 0")
    sleep(0.5)
    return True

