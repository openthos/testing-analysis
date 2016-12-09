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

d = open('data.json' , 'wb')
r = ''
data = []
path = '/var/www/html/result'
files = os.listdir(path)

for name in files:
    t = []
    dic = collections.OrderedDict({"测试用例":name})
    findfiles(path + '/' + name,t)
    for fn in t:
        pn = fn.split('/')
        if(sys.argv[1] in fn and '.json' in fn):
            dic[pn[7]] = getresult(fn,r)
    dic.update(dic)
    data.append(dic)
d.write(json.dumps(data,indent=1))
