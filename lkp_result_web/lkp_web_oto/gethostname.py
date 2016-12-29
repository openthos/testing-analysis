#!/usr/bin/python
import sys
import json
log=sys.stdin

adict={};

while True:
    line=log.readline()
    if not line : break
    line=line.strip()
    adict[line]=line

#json.dump(adict,sys.stdout) 
print json.dumps(adict) 
