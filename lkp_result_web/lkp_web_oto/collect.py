#!/usr/bin/env python
#coding=utf-8
#create time : 2016-04-11
#modify time : 2016-04-24
#author : Huiyang.Han

#2016-04-24:multiple thread and only collect

import os
import sys
import json
import csv
from optparse import OptionParser

def collect(pathfile):
	MySelectPath = '/var/www/html/lkp_web_oto/path/'+pathfile+'.txt'
	OutPath = '/var/www/html/lkp_web_oto/csv/'+pathfile+'.csv'
	#input: a path file
	#output: nothing
	#function: collect and save to csv

	#Indicators

	FULLFILL = -1
	BINDEX = 5
	CONFIG1 = 6
	CONFIG2 = 7
	CONFIG3 = 8
	KINDEX = 9
	CINDEX = 10
	COMMIT = 11
	#print MySelectPath
	#print OutPath
	#print "Collect"
	table = []
	for path in open(MySelectPath):
		path = path.replace("\n", "")
		#print path
		if os.path.exists(path):
			jsontext = open(path).read()
			dic = json.loads(jsontext)
			#print dic
			pathsplit = path.split("/")
			dic["benchmark"] = pathsplit[BINDEX]
			dic["config1"] = pathsplit[CONFIG1]
			dic["config2"] = pathsplit[CONFIG2]
			dic["config3"] = pathsplit[CONFIG3]
			dic["kernel"] = pathsplit[KINDEX]
			dic["compiler"] = pathsplit[CINDEX]
			dic["commit"] = pathsplit[COMMIT]
			table.append(dic)
			#print dic["benchmark"]
		else:
			#print os.getcwd(), path, os.path.exists(path)
			continue
	#print table

	#print "Collect finished"

	#print "Combine"

	full = {}
	length = 0
	for dic in table:
		length += 1;
		for k,v in dic.items():
			if full.has_key(k):
				full[k].append(v)
			else:
				full[k] = [v]
				if length != 1:
					for n in range(1,length):
						full[k].append("")
					full[k].reverse()
		for k,v in full.items():
			while len(v) < length:
				full[k].append("")
	#print "Combine finished"

	"""
	#get rid of boolean metrics
	for key, value in full.items():
		#print key
		sum = 0.0
		notbool = False
		for num in value:
			#print num, sum
			sum += float(num)
			if num != 0.0 and num != 1.0:
				notbool = True
		if notbool or True:
			#print value
			value = sum / len(value)
			newdic[key] = value
	"""

	#print "Savetocsv"

	keylist = full.keys()
	special = ["benchmark", "config1", "config2", "config3", "kernel", "compiler", "commit"]
	orderlist = []#first three
	orderlist[0:6] = special
	for key in keylist:
		if not key in special:
			orderlist.append(key)
	wcsvfile = file(OutPath, 'wb')
	writer = csv.writer(wcsvfile)
	
	writer.writerow(orderlist)#write the first row : metrics
	row = len(full["benchmark"])
	col = len(keylist)
	
	for i in range(row):
		line = []
		for key in orderlist:

			line.append(full[key][i])
		writer.writerow(line)
	wcsvfile.close()

	return table
if __name__ == "__main__":
	parser = OptionParser(usage="%prog [optinos] filename")

	parser.add_option("-f", "--file",
	                action = "store",
	                dest = "file",
	                type = "string",
	                default = ".",
	                help = "Which file will you want save your information from"
	                )
	(options, args) = parser.parse_args()
	pathfile = options.file
	collect(pathfile)
