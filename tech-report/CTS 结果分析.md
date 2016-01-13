**1、CTS测试完后结果的分析：**

结果在repository/results中，放在一个文件夹里，名字是你测试开始的时间。

分析的方法有两种：

1、可以直接从Failure Details找原因；（个人感觉应该难度较大）

2、结合源代码以及Failure Details的信息找原因

第二种方法牵扯到找测试源代码的问题，这就要对CTS源码目录以及相应生成物的命名有一定的了解。


**2、了解CTS这个工程：**

研究:android build system、CTS源码、makefile以及shell基本知识

了解Linux工程最好的入手点，就是从它的编译系统入手。

这个涉及到Linux的makefile以及android的编译系统的基本知识，具体内容还是挺多的，不过看懂了android编译系统，以后看其他Android工程应该都会得心应手。


**3、在android CTS上增加自己的test package**

资料：CTS命令的用法

有两种方法：

1、完美利用自带命令(已验证)

2、用Erin Yueh的方法 网址：http://blog.csdn.net/fnjnash/article/details/5989293

用这个方法的前提也是要彻底弄懂CTS的内部结构，不然也只能照猫画虎



**4、下来要弄明白的**

1、android的编译系统（学习下makefile以及shell基本知识）

2、CTS这个工程（看看测试包XML生成器的假设成不成立，能不能提取出来）

3、众多层次test case 的写法



**5、测试结果**

测试结果在android-cts/repository/results目录下；

测试日志在android-cts/repository/logs目录下。

**6. 失败项目重测及xml文档整合**

我们在测试一些项目的时候，完全跑一遍CTS测试，很多项都会失败fail，但是我们在对这些失败项单独测得时候，这些项目pass，这时，我们不可能再去重新完全跑一次CTS，这样既耗时，也不能确保该项一定会pass，这样，我们就可以用下面的方法来对失败项操作，做到失败项的pass结果整合。

原理：
*run cts –continue-session session_ID: run all not executed tests from a previous CTS session*

将fail项修改成not Executed项，使用该命令进行重测。

6.1. 定位

找到那些测试fail的项，对它们进行源码的修改、调试，之后进行单独测试，直到它不再fail。使用文本编译器打开result的xml文件，找到该项
*l r*
*Session            Pass          Fail          Not Executed      Start time           Plan name         Device ser(s)8*
*0                      598            2                  0     2016.01.7             NA                     MSM82*
 

6.2. 修改

找到项目之后，将[result=”fail”]改成[result=”not  Executed”],记得在xml文件的开头将fail总数和not Executed的总数根据你修改的数目进行修改

修改之后：
*cts-tf>l  r*
*Session            Pass          Fail          Not Executed      Start time           Plan name         Device ser(s)*
*0                 598           0             2                   2016.01.7            NA                     MSM82251*
 

6.3. 测试
*cts-tf>run cts –continue-session session_ID*
session_ID是之前查看result前面的ID。

运行，测试完成，结果就被整合到了原来的result集中，pass项将会把原来的fail的log在result的xml文件中也一并删除。

注意事项：CTS测试中不能对终端做任何操作。
