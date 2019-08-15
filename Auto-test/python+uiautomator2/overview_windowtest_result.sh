#!/bin/bash
# 作用：当runWindowTest.sh执行完毕后，将result_dir目录下的内容整合到overview.txt里。

touch overview.txt
i=0
output=" "
for app in `ls result_dir`
do
    outputresult=$app
    test_list=`grep "#测试点#" result_dir/$app | awk -F ' ' '{print $2}'`
    if (($i==0));then
        for item in $test_list
        do
            output=$output','$item
        done
        echo $output > overview.txt
    fi

    for item in $test_list
    do
        tmpstr=$(sed -n "/"$item"/,/模块测试结束/p" result_dir/$app)
        if [[ $tmpstr =~ "模块通过" ]];then
            result="模块通过"
        else
            result="模块失败"
        fi
        outputresult=$outputresult','$result
    done
    echo $outputresult >> overview.txt
    i=1
done
