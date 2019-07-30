#!/bin/bash
touch overview.txt
i=0
output=" "
for app in result_dir/*.txt
do
    outputresult=$app
    test_list=$(cat result_dir/com.github.shadowsocks-result.txt | grep "#测试点#" | awk -F ' ' '{print $2}')
    if (($i==0));then
        for item in $test_list
        do
            output=$output','$item
        done
        echo $output > overview.txt
    fi

    for item in $test_list
    do
        tmpstr=$(sed -n "/"$item"/,/模块测试结束/p" $app)
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
