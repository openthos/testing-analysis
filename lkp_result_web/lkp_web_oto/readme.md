# lkp_web_oto程序说明
### 1、index.html:
设置输出图表样式，将收集到的用户输入（xAxis、benchmarks）提交到getMETRIC.php
### 2、getMETRIC.php:
根据用户输入（xAxis、benchmarks）跳转至不同的页面进行展示
* 当xAxis选择benchmarks并且benchmarks选择ALL时，执行benchmarks_count.sh脚本，对所有benchmark的commit次数信息利用柱状图进行展示
* 当xAxis、benchmarks选择其他选项时，展示指定benchmark的所有metric列表，点击任意一个metric，将xaxis、benchmarks、count信息提交到chart.php

### 3、benchmarks_count.sh:
获取path目录下所有benchmark的名字，并分别统计不同benchmark的全部commit次数和无重复commit次数，利用柱状图进行可视化显示
### 4、chart.php:
根据benchmark得到对应csv文件路径，根据count信息得到要进行展示的metric名字，将csv文件路径、输出文件路径、metric名字、benchmark、编译器、xaxis信息作为参数执行python genhtml.py脚本
### 5、genhtml.py:
读取csv数据，对指定benchmark、指定metric的信息利用折线图进行展示
### 6、getfilelist.py:
扫描测试用例结果路径下所有的avg.json，将路径保存在以测试用例命名的txt文件中
### 7、insertdata.py:（被collect.py替代）
根据“测试用例.txt”文件中的路径信息，对lkp测试结果csv的配置信息进行补充，生成新的csv
### 8、collect.py:
根据每一个benchmark测试结果的avg.json文件，在/var/www/html/lkp_web_oto/csv中生成相应的$benchmark.csv文件

# 运行流程：
### 1、apache安装
sudo apt-get install apache2
### 2、安装php
sudo apt-get install php5

sudo apt-get install libapache2-mod-php5
### 3、重启apache2
sudo /etc/init.d/apache2 restart
### 4、将lkp_web_oto文件夹放到/var/www/html目录下面
sudo su 进入root用户

cp -R lkp_web_oto /var/www/html
### 5、修改/var/www/html/lkp_web_oto/cache权限
chmod -R 777 cache 
### 6、数据预处理
cd /var/www/html/lkp_web_oto

python getfilelist.py -f ebizzy（测试用例名字）在/var/www/html/lkp_web_oto/path下生成ebizzy（测试用例名字）测试结果路径文件 

Python collect.py -f ebizzy（测试用例名字）在/var/www/html/lkp_web_oto/csv下生成ebizzy（测试用例名字）测试结果完整csv文件
(Python insertdata.py -f ebizzy（测试用例名字）在/var/www/html/lkp_web_oto/csv下生成ebizzy（测试用例名字）测试结果完整csv文件) 

**注意**

1. getfilelist.py和collect.py(insertdata.py)中MySelectPath根据实际情况进行修改，分别为测试用例测试结果根路径和测试用例测试结果csv

1. collect.py中代码需要根据实际情况进行修改
    * 查看path文件夹下ebizzy.txt内容格式：
/var/www/html/result/ebizzy/200%-5x-5s/pc1-Z8302/ubuntu/defconfig/gcc-5/3c57ab41ca9ecc75c0e1d8b7fe698d7d71f74d4e/avg.json
    * ebizzy、200%-5x-5s、pc1-Z8302、ubuntu、defconfig、gcc-5、3c57ab41ca9ecc75c0e1d8b7fe698d7d71f74d4e是配置信息，在路径中为第5到11项
    * 将代码进行如下修改：
        BINDEX = 5
	CONFIG1 = 6
	CONFIG2 = 7
	CONFIG3 = 8
	KINDEX = 9
	CINDEX = 10
	COMMIT = 11

1. insertdata.py中代码需要根据实际情况进行修改

    * 查看path文件夹下ebizzy.txt内容格式：
/var/www/html/result/ebizzy/200%-5x-5s/pc1-Z8302/ubuntu/defconfig/gcc-5/3c57ab41ca9ecc75c0e1d8b7fe698d7d71f74d4e/avg.json
    * ebizzy、200%-5x-5s、pc1-Z8302、ubuntu、defconfig、gcc-5、3c57ab41ca9ecc75c0e1d8b7fe698d7d71f74d4e是配置信息，在路径中为第5到11项
    * 将代码中n的循环定为5到11，即：
        n = 5  while n<=11

### 7、浏览器访问：
http://localhost（或者IP）/lkp_web_oto/
