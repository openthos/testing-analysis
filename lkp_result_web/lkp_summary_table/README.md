1. 需要生成每次测试结果的一个表格，统一放到/var/www/html/lkp_summary_table/html_tables目录下面
2. 每个测试结果文件的命名规则是commitid_machinename.html
3. 在result.php里面增加一个form  传参数给lkp_summary_table/get_table.php
4. get_table.php根据两个参数，选取出对应的commitid_machinename.html 展示。
