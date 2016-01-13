**CTS测试完后结果的分析：**

结果在repository/results中，放在一个文件夹里，名字是你测试开始的时间。

分析的方法有两种：

1、可以直接从Failure Details找原因；（个人感觉应该难度较大）

2、结合源代码以及Failure Details的信息找原因

第二种方法牵扯到找测试源代码的问题，这就要对CTS源码目录以及相应生成物的命名有一定的了解。


**了解CTS这个工程：**

研究:android build system、CTS源码、makefile以及shell基本知识

了解Linux工程最好的入手点，就是从它的编译系统入手。

这个涉及到Linux的makefile以及android的编译系统的基本知识，具体内容还是挺多的，不过看懂了android编译系统，以后看其他Android工程应该都会得心应手。


**在android CTS上增加自己的test package**

资料：CTS命令的用法

有两种方法：

1、完美利用自带命令(已验证)

2、用Erin Yueh的方法 网址：http://blog.csdn.net/fnjnash/article/details/5989293

用这个方法的前提也是要彻底弄懂CTS的内部结构，不然也只能照猫画虎



**接下来要弄明白的**

1、android的编译系统（学习下makefile以及shell基本知识）

2、CTS这个工程（看看测试包XML生成器的假设成不成立，能不能提取出来）

3、众多层次test case 的写法
