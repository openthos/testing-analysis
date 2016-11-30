#!/usr/bin/env python
#coding=utf-8
 
import os
from optparse import OptionParser

def scan_save(file,prefix=None):
    MySelectPath = '/var/www/html/result/'+file
    OutPath = '/var/www/html/lkp_web_oto/path/'+file+'.txt'
    files_list=[]
     
    for root, sub_dirs, files in os.walk(MySelectPath):
        for special_file in files:
            if  prefix:
                if special_file.startswith(prefix):
                    files_list.append(os.path.join(root,special_file))
            else:
                files_list.append(os.path.join(root,special_file))


    f = open(OutPath,'w')                      
    for file in files_list:
        f.write(file)
        f.write("\n")
    f.close()

if __name__ == "__main__":
	parser = OptionParser(usage="%prog [optinos] filename")

	parser.add_option("-f", "--file",
	                action = "store",
	                dest = "file",
	                type = "string",
	                default = ".",
	                help = "Which file will you want read information from"
	                )
	(options, args) = parser.parse_args()
	file = options.file
	scan_save(file,prefix="avg")
