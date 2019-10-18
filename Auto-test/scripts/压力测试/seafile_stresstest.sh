#!/bin/bash
# 作用：对seafile服务器进行压力测试 
# 用法： ./seafile_stresstest.sh 参数1 参数2
# 参数1： 起始用户的编号，可取002~999之间的数字
# 参数2： 结束用户的编号，可取002~999之间的数字


which seaf-cli
if [ $? -ne 0 ]; then
	echo please install seaf-cli
fi

SERVER_URL=http://192.168.0.158
CONF_DIR=config
DATA_DIR=seafdata
TMP_FILE=/tmp/abc.txt
LIB_ID=86291401-ed2c-4fda-8fa8-8b3c0d87c94d

if [ ! -d $CONF_DIR ]; then mkdir $CONF_DIR; fi
if [ ! -d $DATA_DIR ]; then mkdir $DATA_DIR; fi

function s_init() {
	for ((i=$1;i<=$2;i++));do
		user=thtfpc00000$i@openthos.com
		lib_name=thtfpc$i
		mkdir $DATA_DIR/$lib_name
		seaf-cli init -c $CONF_DIR/$lib_name -d $DATA_DIR/$lib_name
	done
}

function s_deinit() {
	rm -rf config/* seafdata/*
}

function s_start() {
        for ((i=$1;i<=$2;i++));do
		user=thtfpc00000$i@openthos.com
		lib_name=thtfpc$i
		seaf-cli start -c $CONF_DIR/$lib_name
	done
}

function s_download() {
	for ((i=$1;i<=$2;i++));do
		user=thtfpc00000$i@openthos.com
		lib_name=thtfpc$i
		seaf-cli download -c $CONF_DIR/$lib_name -s $SERVER_URL -l $LIB_ID -u $user -p thtfpc
		sleep 15
	done
}

s_deinit
s_init
s_start
s_download
