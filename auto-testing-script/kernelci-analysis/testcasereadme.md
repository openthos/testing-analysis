#如何添加测试用例
---

* ### 测试用例在上传之前，必须在adb连接两台机器的情况下测试通过，主要用于检测测试用例中adb没有加-s参数的情况

* ### 不允许进行adb connect 以及adb disconnect操作（测试框架已经帮我们连接上adb，不需要我们操作）

* ### 所有拷贝进android-x86中的文件，在测试结束后全部删除卸载或者


* 位置：auto-testing-script/kernelci-analysis/testcases/
* 功能：测试框架只为测试用例提供一个全新的android-x86，不提供额外的设置，所需要或依赖的apk等其他程序需要在测试脚本自己解决
* 目录命名规则：每一个测试需要在上述位置建立一个目录，目录名没有限制
* 测试程序命名规则：在自己建好的目录中添加所有用到的测试文件，其中必须包括一个与目录名同名的.sh文件，例如目录名叫“ebizzy”，那么该脚本名称位“ebizzy.sh”，该文件为测试入口，框架只需要调用该脚本便可以完成整个测试
* 框架会为测试程序提供三个参数，ip，端口，一个文件夹名，ip和端口在adb操作android-x86时使用，不仅连接adb时使用，而是所有的adb操作必须加上ip和端口号（例如`adb push`必须写成 `adb -s $androidIP:$port`的形式）
* 下面是测试用例的模板：
```
#!/bin/bash                                                                     
androidIP=$1
port=$2
foldName=$3
cd "$(dirname "$0")"
```
* 前五行是所有测试必须相同的，拷贝过去即可，后面的可以根据自己的需求来写，例如ebizzy测试：
```
#!/bin/bash                                                                     
androidIP=$1
port=$2
foldName=$3
cd "$(dirname "$0")"
adb -s $androidIP:$port push ebizzy /data/local/tmp
adb -s $androidIP:$port shell /data/local/tmp/ebizzy > $foldName/restResult
```
* 目前所有的测试的结果只有一个文件，所以统一命名为了testResult，并放入`$foldName`，当然后期如果结果是多个文件，那么可以根据自己的需要进行命名，只需要放入`$foldName`文件夹即可


---
目前先到的就这些，编写测试用例的人员有别的需求也可以找**敖权**协商
