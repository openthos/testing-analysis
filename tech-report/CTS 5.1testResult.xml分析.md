测试版本：CTS 5.1
 
进入cts
#cd /opt/android-cts/tools
#cts-tf>list plans    查看得知此版本含有21个plan

testResult.xml  结果汇总如下，通过数据得知pass率不足五分之一
Tests Passed	37105
Tests Failed	3535
Tests Timed out	0
Tests Not Executed	159521

分析Test Package，得知共211个package.其中有105个包是重复出现两次的。去掉重复后，测试结果数据如下,pass率提高至三分之一：
----- | ---- | ----
Tests Passed	|37105
Tests Failed	|3535
Tests Timed out|	0
Tests Not Executed |59450	总用例数为100090

