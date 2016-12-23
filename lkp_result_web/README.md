#测试结果展示



##代码第一次部署方法：

cd ~/workdir/ 

git clone https://github.com/openthos/testing-analysis 

cd ~/workdir/testing-analysis/lkp_result_web 

执行以下脚本部署代码到/var/www/html/目录 

./creat_symbol.sh 

在浏览器输入http://localhost/result.php查看结果 


##部署完成后修改并同步到github上面的方法： 

cd /var/www/html/ 


修改代码。。。。 

然后在浏览器输入http://localhost/result.php 查看结果，调试 



如果没有问题 

执行以下以下命令，提交到github上面 


```
cp ./*.sh ./*.php ./*.js ./*.py    ~/workdir/testing-analysis/lkp_result_web                
cp lkp_web_oto/ -R  ~/workdir/testing-analysis/lkp_result_web

cd ~/workdir/testing-analysis/ 

git pull 

git add . 

git commit -am "xxx.x.x" 

git push origin master 
```


提交完毕 

