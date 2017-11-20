#!/bin/bash -x
##delete the crontab for avoiding rerun the updateGIT.sh while it is running
#croncmdOld="/home/oto/openthos/testing-analysis/auto-testing-script/kernelci-analysis/updateGIT.sh"
#( crontab -l | grep -v -F "$croncmdOld" ) | crontab -
echo "####################################################begin##################################################"
#[ `ps -axu | grep updateGIT.sh | wc -l` -gt 4 ] && exit

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
cd ../../../oto_external_lkp
git pull
        if [ $? -ne 0 ]; then
        echo -e "LKP test source code pull ERROR, use the previous code and testcases set!"
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
        mv $linux_repo/20* /mnt/freenas/summary/
    fi
    #for loop
    done
else
    echo "oto git pull error!"
fi

echo "####################################################end##################################################"
