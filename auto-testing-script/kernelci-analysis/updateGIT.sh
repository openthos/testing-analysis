#!/bin/bash -x
##delete the crontab for avoiding rerun the updateGIT.sh while it is running
croncmdOld="/home/oto/openthos/testing-analysis/auto-testing-script/kernelci-analysis/updateGIT.sh"
( crontab -l | grep -v -F "$croncmdOld" ) | crontab -

dirname_path=$(cd `dirname $0`; pwd)
cd $dirname_path

############################
## git pull test source code
git pull
        if [ $? -ne 0 ]; then
        echo -e "test source code pull ERROR, use the previous code and testcases set!"
        fi  
############################

############################
## git pull lkp test source code 
cd ../../../oto_lkp
git pull
        if [ $? -ne 0 ]; then
        echo -e "LKP test source code pull ERROR, use the previous code and testcases set!"
        fi  
cd $dirname_path
############################

############################
## git pull gui test source code 
cd ../../../oto_Uitest
git pull
        if [ $? -ne 0 ]; then
        echo -e "GUI test source code pull ERROR, use the previous code and testcases set!"
        fi  
cd $dirname_path
############################

build_sh="$dirname_path/build.sh"
tmp_branch="$dirname_path/tmp_branch"
source $tmp_branch/envar

#linux_repo="/media/vdb1/xly/common"
cd $linux_repo

git pull
if [ $? -eq 0 ]; then

    #remote_branches=$(git branch -r | sed -e "1d" -e  "s/origin\///")
    #echo -e "All branches: ($remote_branches)"
    remote_branches="master"
    
    for br_ in $remote_branches
    do 
    
    	old_br_com=(`cat $tmp_branch/$br_`) #old_branch_commit
    
    br_com=(`git log -1 --pretty=format:"%H %cd" --date=raw` )
    
    if [ "${old_br_com[0]}"x = "${br_com[0]}"x ];then
    	echo `date` nothing new
    	continue
    else
    	echo `date` something new
    	echo ${br_com[@]} > $tmp_branch/$br_
    	#/bin/bash $build_sh ${br_com[0]} > /mnt/freenas/summary/`date +%Y%m%d`-${br_com[0]}
    	/bin/bash $build_sh ${br_com[0]} > `date +%Y%m%d`-${br_com[0]}
    fi
    #for loop
    done
else
    echo "oto git pull error!"
fi

mv summary 201* /mnt/freenas/summary/

## after 10 minutes, start test again
minuteNow=`date "+%M"`
hourNow=`date "+%H"`
timeInterval=10
minuteNow=`echo $minuteNow | sed 's/^[0]//'`
hourNow=`echo $hourNow | sed 's/^[0]//'`
minuteNext=`expr $minuteNow + $timeInterval`
hourNext=$hourNow 

crontDate=`date +%y%m%d`

if [ $minuteNext -ge 60 ];then
    minuteNext=`expr $minuteNext - 60`
    hourNext=`expr $hourNext + 1`
    if [ $hourNext -ge 24 ];then
        hourNext=00
    fi
fi
len=`echo $minuteNext | wc -L`
if [ $len == "1" ];then
    minuteNext="0"$minuteNext
fi
len=`echo $hourNext | wc -L`
if [ $len == "1" ];then
    hourNext="0"$hourNext
fi


nextCrontabTime="$minuteNext $hourNext"

croncmdNew="$nextCrontabTime * * * /home/oto/openthos/testing-analysis/auto-testing-script/kernelci-analysis/updateGIT.sh >> /mnt/freenas/result/cronout$crontDate 2>&1"
( crontab -l | grep -v -F "$croncmdOld" ) | crontab -
( crontab -l | grep -v -F "$croncmdNew" ; echo "$croncmdNew" ) | crontab -

