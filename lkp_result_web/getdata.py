#!/usr/bin/env python
#coding=utf-8

import os
import json
import collections
import sys

def findfiles(path, t):
    files = os.listdir(path)
    for f in files:
        npath = path + '/' + f
        if(os.path.isfile(npath)):
            t.append(npath)
        if(os.path.isdir(npath)):
            if (f[0] == '.'):
                pass
            else:
                findfiles(npath, t)
    return

def getresult(fn, r):
    with open (fn) as resfile:
        for line in resfile:
            if('result' in line):
                r=line[-4]
    return r

def getdirlist(path,dirlist):
    files = os.listdir(path)
    for n in files:
        if(os.path.isdir(path + '/' + n)):
            if(n[0] == '.'):
                pass
            else:
                dirlist.append(n)
    return

d = open('data.json' , 'wb')
r = '无测试结果'
data = []
dirlist=[]
path = '/var/www/html/result'

getdirlist(path,dirlist)
for name in dirlist:
    t = []
    dic = collections.OrderedDict()
    findfiles(path + '/' + name,t)
    for fn in t:
        if(sys.argv[1] in fn and 'testResult.json' in fn):
            pn = fn.split('/')
            dic['测试用例'] = name
            dic[pn[7]] = getresult(fn,r)
    if(dic):
        dic.update(dic)
        data.append(dic)
d.write(json.dumps(data,indent=1))
