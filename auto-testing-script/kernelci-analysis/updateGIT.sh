#!/bin/bash

dirname_path=$(cd `dirname $0`; pwd)

build_sh="$dirname_path/build.sh"
tmp_branch="$dirname_path/tmp_branch"
source $tmp_branch/envar

#linux_repo="/media/vdb1/xly/common"
cd $linux_repo

git pull
        if [ $? -ne 0 ]; then
        echo -e "git pull ERROR!"
        exit
        fi  

#remote_branches=$(git branch -r | sed -e "1d" -e  "s/origin\///")
#echo -e "All branches: ($remote_branches)"
remote_branches="master"

for br_ in $remote_branches
do 

	old_br_com=(`cat $tmp_branch/$br_`) #old_branch_commit

br_com=(`git log -1 --pretty=format:"%H %cd" --date=raw` )

if [ "${old_br_com[0]}"x = "${br_com[0]}"x ]
then
	echo `date` nothing new
	continue
else
	echo `date` something new
	echo ${br_com[@]} > $tmp_branch/$br_
	/bin/bash $build_sh ${br_com[0]} > /mnt/freenas/summary/`date +%Y%m%d`-${br_com[0]}
fi
#for loop
done
