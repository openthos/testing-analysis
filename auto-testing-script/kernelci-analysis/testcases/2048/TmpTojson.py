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
        elif(key.count('url')):
            n = value.rfind('_')
            if(value.count('localhost')):
                dic[key] = 'http://os.cs.tsinghua.edu.cn/openthos/result/2048/default/qemu1/android/android_x86/gcc/' + value[n+1:] +'/0/testResult'
            elif(value.count('192.168.0.115')):
                dic[key] = 'http://os.cs.tsinghua.edu.cn/openthos/result/2048/default/pc1-Z8302/android/android_x86/gcc/' + value[n+1:] +'/0/testResult'
            elif(value.count('192.168.0.198')):
                dic[key] = 'http://os.cs.tsinghua.edu.cn/openthos/result/2048/default/pc2-Z8000/android/android_x86/gcc/' + value[n+1:] +'/0/testResult'
            elif(value.count('192.168.0.195')):
                dic[key] = 'http://os.cs.tsinghua.edu.cn/openthos/result/2048/default/laptop1-T43U/android/android_x86/gcc/' + value[n+1:] +'/0/testResult'
            else:
                dic[key] = 'http://os.cs.tsinghua.edu.cn/openthos/result/2048/default/laptop2-T45/android/android_x86/gcc/' + value[n+1:] +'/0/testResult'
        else:
            dic[key] = value
r.write(json.dumps(dic,indent=1))
