#!/usr/bin/env python
#coding=utf-8

import os
import json
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

def count(li,data):
    dic={}
    dic['tbox']=li[0]
    dic['pass']=li.count('1')
    dic['failed']=li.count('0')
    dic['untest']=li.count(None)
    dic['sum']=len(li)-1
    data.append(dic)
    return

def geturl(fn,start,end):
    startIndex=fn.index(start)
    if(startIndex>=0):
        startIndex += len(start)
    endIndex=fn.index(end)
    return fn[startIndex:endIndex]

if(os.path.exists('table/'+sys.argv[1]+'_summary.json')):
    sys.exit(0)
else:
    d = open('table/'+sys.argv[1]+'_summary.json' , 'wb')

r = ''
data = []

list1=['qemu1']
list2=['pc1-Z8302']
list3=['pc2-Z8000']
list4=['laptop1-T43U']
list5=['laptop2-T45']

path = '/var/www/html/result'
dirlist=[]
getdirlist(path,dirlist)
for name in dirlist:
    t = []
    dic = {}
    findfiles(path + '/' + name,t)
    for fn in t:
        if(sys.argv[1] in fn and 'testResult.json' in fn):
            pn = fn.split('/')
            dic['testcase'] = name
            dic[pn[7]] = getresult(fn,r)
            dic[pn[7]+'url']=geturl(fn,'/var/www/html/','testResult.json')
    if(dic):
        dic.update(dic)
        data.append(dic)
        list1.append(dic.get('qemu1'))
        list2.append(dic.get('pc1-Z8302'))
        list3.append(dic.get('pc2-Z8000'))
        list4.append(dic.get('laptop1-T43U'))
        list5.append(dic.get('laptop2-T45'))

for li in [list1,list2,list3,list4,list5]:
    count(li,data)

d.write(json.dumps(data,indent=1))
