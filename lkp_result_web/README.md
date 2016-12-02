#测试结果展示



##代码部署方法：

cd ~/workdir/ 

git clone https://github.com/openthos/testing-analysis 

cd ~/workdir/testing-analysis/lkp_result_web 

执行以下脚本部署代码到/var/www/html/目录 

./creat_symbol.sh 

在浏览器输入http://localhost/result.php查看结果 


##修改并同步到github上面的方法： 

cd ~/workdir/testing-analysis/lkp_result_web 


修改代码。。。。 

执行以下脚本进行部署 

./crea_symbol.sh 


然后在浏览器输入http://localhost/result.php查看结果 

如果没有问题 

执行以下命令提交到github上面 


cd ~/workdir/testing-analysis/ 

git pull 

git add . 

git commit -am "xxx.x.x" 

git push origin master 


提交完毕 
