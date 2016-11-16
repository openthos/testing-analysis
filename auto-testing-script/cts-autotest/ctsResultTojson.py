#coding=utf-8
#!/usr/bin/python

#python ctsResultTojson.py 参数1(xml文件) 参数2(结果存放目录)

from lxml import etree
import json
import collections
import sys

f = open(sys.argv[2] + '/cts_result.json','wb')
tree = etree.parse(sys.argv[1])
root = tree.getroot()
root1 = root.xpath('TestPackage')
dic = collections.OrderedDict()

def try_find_child(parent,init_str):
    ret_str = init_str
    ret_str += str(parent.get('name')) + '.'
    if parent.get('result') == None:
        for child in parent:
            try_find_child(child,ret_str)
    elif parent.get('result') == 'pass' :
        dic[ret_str + 'result'] = 1
        dic[ret_str + 'starttime'] = parent.get('starttime')
        dic[ret_str + 'endtime'] = parent.get('endtime')
    elif parent.get('result') == 'fail' :
        dic[ret_str + 'result'] = 0
        dic[ret_str + 'starttime'] = parent.get('starttime')
        dic[ret_str + 'endtime'] = parent.get('endtime')
        for child in parent:
            dic[ret_str + 'errorMessage'] = child.get('message')

for element in root1:
    dic[element.get('abi') + element.get('appPackageName') + '.url'] = 'http://os.cs.tsinghua.edu.cn/openthos/' + sys.argv[2] + '/' + sys.argv[1] + '#' + element.get('appPackageName')
    init_str = '{abi}.{app_package}#'.format(abi=element.get('abi'),app_package=element.get('appPackageName'))
    for subelement  in element.xpath('./*'):
        try_find_child(subelement,init_str)

f.write(json.dumps(dic,indent=1))
