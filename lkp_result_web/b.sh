#!/bin/bash
cat test_top.js
echo -e  " context=\c"
./test.py
cat test_bottom.js 
