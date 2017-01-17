# lkp_web_oto程序说明
### 1、getfilelist.py:
扫描测试结果路径（/var/www/html/result）下所有benchmark的avg.json路径，保存到以对应benchmark命名的文本文件中，将以benchmark命名的文本文件保存到path路径(/var/www/html/lkp_web_oto/path)下
### 2、collect.py
根据路径读取每个benchmark所有json数据，将json数据转换为csv数据，保存到以对应benchmark命名的csv文件中，将以benchmark命名的csv文件保存到csv路径下（/var/www/html/lkp_web_oto/csv）
### 3、index.html:
将指定的benchmark信息提交到getMETRIC.php
### 4、getMETRIC.php:
读取指定benchmark的csv文件，展示所有的metric列表，指定一个hostname，选择任意数目的metric，将benchmarks、hostname、count信息提交到chart.php
### 5、chart.php:
读取指定benchmark的csv文件，根据count信息得到要进行展示的metric名字，将benchmark、hostname、metric名字作为参数传递给chart_3args.php
### 6、chart_3args.php:
利用GET获取benchmark、hostname、metric名字，并将csv文件路径、输出文件路径、metric名字、benchmark、编译器、xaxis信息作为参数执行python genhtml.py脚本
### 7、genhtml.py:
读取指定benchmark的csv数据，对指定benchmark、指定metric的信息利用折线图进行展示

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

**注意**

1. getfilelist.py和collect.py中MySelectPath根据实际情况进行修改，分别为测试用例测试结果根路径和测试用例测试结果csv文件路径

1. collect.py中代码需要根据实际情况进行修改

    * 查看path文件夹下ebizzy.txt内容格式：
/var/www/html/result/ebizzy/200%-5x-5s/pc1-Z8302/ubuntu/defconfig/gcc-5/3c57ab41ca9ecc75c0e1d8b7fe698d7d71f74d4e/avg.json
    * ebizzy、200%-5x-5s、pc1-Z8302、ubuntu、defconfig、gcc-5、3c57ab41ca9ecc75c0e1d8b7fe698d7d71f74d4e是配置信息，在路径中为第5到11项
    * 将代码中BINDEX、CONFIG1、CONFIG2、CONFIG3、KINDEX、CINDEX、COMMIT定为5到11
    
### 7、浏览器访问：
http://localhost（或者IP）/lkp_web_oto/
 
Python insertdata.py -f ebizzy（测试用例名字）在/var/www/html/lkp_web_oto/csv下生成ebizzy（测试用例名字）测试结果完整csv文件 
### 8、测试结果分析过程中各脚本的相互关系如下
![测试结果可视化显示脚本相互关系](https://github.com/openthos/testing-analysis/blob/master/lkp_result_web/lkp_web_oto/image/script_relation2.png )
