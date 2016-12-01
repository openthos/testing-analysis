#!/usr/bin/env python
#encoding:utf-8

import csv
import json
import os
import sys
from optparse import OptionParser

def insertdata(filename):
        MySelectPath = '/var/www/html/result/'+filename+'.csv'
        OutPath = '/var/www/html/lkp_web_oto/csv/'+filename+'.csv'
        if (not  os.path.exists(MySelectPath) ):
		sys.exit(0)

	csvfiler = file(MySelectPath,'rb')
	csvfilew = file(OutPath, 'wb')

	#create and write titles
	titles = ['benchmark', 'config1', 'config2','config3','kernel','compiler','commit']
	reader = csv.reader(csvfiler)
	rows= [row for row in reader]
	for item in rows[0]:
	    titles.append(item)
	writer = csv.writer(csvfilew)
	writer.writerow(titles)

	#get config data
	f = open('/var/www/html/lkp_web_oto/path/'+filename+'.txt', 'r')

	count = len(f.readlines())
	f.seek(0,0) 
	num = count 
	while count > 0:
		#match data
		file1 = f.readline().strip('\n')
		with open(file1) as f1:
		    data = json.load(f1)
		f1.close()
		#split config data
		pathstr = ''.join(file1)
		path = pathstr.split('/',14)         
		for i in range(num+1):
		    if i == 0:
		            continue
		    if (data[titles[7]] == float(rows[i][0]) and data[titles[8]] == float(rows[i][1])):
			#append data
			line = []
			n = 5
			while n<=11:
			    line.append(path[n])
			    n = n+1
			for item in rows[i]:
			    line.append(item)
			writer.writerow(line)
		count = count -1
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
	filename = options.file
	insertdata(filename)
