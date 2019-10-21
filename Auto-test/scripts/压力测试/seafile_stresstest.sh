#!/bin/bash
# 作用：对seafile服务器进行压力测试 
# 用法： ./seafile_stresstest.sh 参数1 参数2
# 参数1： 起始用户的编号，可取002~999之间的数字
# 参数2： 结束用户的编号，可取002~999之间的数字


which seaf-cli
if [ $? -ne 0 ]; then
	echo please install seaf-cli
	exit 1
fi

SERVER_URL=http://192.168.0.158
CONF_DIR=config
DATA_DIR=seafdata
LIB_ID=86291401-ed2c-4fda-8fa8-8b3c0d87c94d

if [ ! -d $CONF_DIR ]; then mkdir $CONF_DIR; fi
if [ ! -d $DATA_DIR ]; then mkdir $DATA_DIR; fi

function s_init() {
	i=$1;
	while [ $i -le $2 ]; do
		user=`printf "thtfpc%08d@openthos.com" $i`
		lib_name=`printf "thtfpc%03d" $i`
		i=$(($i+1))
		mkdir $DATA_DIR/$lib_name
		seaf-cli init -c $CONF_DIR/$lib_name -d $DATA_DIR/$lib_name
	done
}

function s_deinit() {
	rm -rf config/* seafdata/*
}

function s_start() {
	i=$1;
	while [ $i -le $2 ]; do
		user=`printf "thtfpc%08d@openthos.com" $i`
		lib_name=`printf "thtfpc%03d" $i`
		i=$(($i+1))
		echo $user $lib_name
		seaf-cli start -c $CONF_DIR/$lib_name
	done
}

function s_download() {
	i=$1;
	while [ $i -le $2 ]; do
		user=`printf "thtfpc%08d@openthos.com" $i`
		lib_name=`printf "thtfpc%03d" $i`
		i=$(($i+1))
		seaf-cli download -c $CONF_DIR/$lib_name -s $SERVER_URL -l $LIB_ID -u $user -p thtfpc
		sleep 15	# avoid errors of "Too Many Request"
	done
}

s_deinit $1 $2
s_init $1 $2
s_start $1 $2
s_download $1 $2
