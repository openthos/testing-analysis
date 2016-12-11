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
r = ''
data = []
dirlist=[]
path = '/var/www/html/result'

getdirlist(path,dirlist)
for name in dirlist:
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
