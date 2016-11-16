#!/usr/bin/python
#coding=utf-8
#python TmpTojson.py tmpResultToJson(参数1文件) 参数2路径

import json
import sys
import collections
import string

r = open(sys.argv[2] + '/testResult.json' , 'wb')
dic = collections.OrderedDict()

with open(sys.argv[1]) as f:
    content = f.read().splitlines()
    for s in content:
        num = s.find(':')
        key = s[:num]
        value = s[num+1:]
        if(key.count('launchtime') or key.count('result')):
            dic[key] = string.atoi(value)
        elif(key.count('runtime')):
            dic[key] = string.atof(value)
        else:
            dic[key] = value
r.write(json.dumps(dic,indent=1))
