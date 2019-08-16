## 自动化测试编写规则

### 扩展事件
- uiautomator本身不支持鼠标事件，为方便测试，我们准备了一套方法以模拟各种鼠标事件，文件是这个目录下的extendevent.py，方法包括：
   - 获取鼠标设备的ID
   - 单击鼠标左键、右键、中键
   - 双击鼠标左键
   - 鼠标滚轮上翻和下翻
   - 鼠标打开开始菜单
   - 鼠标点击空白处
   - 鼠标拖拽
   - 鼠标拖拽移动窗口（需要滑动两次）
- 后续其它事件也可以往里添加

### 扩展功能
-文件是这个目录下的extendfunction.py，方法包括：
   - 获取父节点的边界坐标

### 结果输出

### 注意事项
- 识别测试控件时尽量使用resource id，如果必须使用text，尽量选择中文模式下也显示为英文的控件，方便后续测试英文版本
- 使用uiautomator2.connect连接设备时尽量不直接用地址，改用命令行传过来的参数，方便后续用脚本统一测试各种设备
```
deviceIP=sys.argv[1]
d = u2.connect(deviceIP)
……
if __name__ == "__main__":
    if len(sys.argv) > 1:
        StartmenuTestCase.deviceIP = sys.argv.pop()

    unittest.main()
```

## 使用方法

#### 解决首次安装应用时google验证的问题

```
./googleSecurity.sh 192.168.0.69	# 参数为测试机的IP地址
	# 要求测试机是联网的，因为要用到应用商店下载应用
```

#### 第三方应用窗口测试

```
pip install lxml	# 如没有安装lxml，需要先安装它
./runWindowtest.sh 192.168.0.69		# 参数为测试机的IP地址
	# 在增加了兼容模式的系统上运行尚有问题，可能是点到了兼容模式按钮
```

#### 第三方应用窗口测试结果概览

```
./overview_windowtest_result.sh	# 结果在overview.txt文件
```

#### 其它的测试脚本

```
# 注意：测试前修改脚本里的测试机地址
# 注意：要求测试机的系统语言为简体中文
adb connect IP			# 连接到测试机的IP
python -m uiautomator2 init	
python mousedemo1.py	# 演示鼠标操作
python rolldemo.py		# 模拟鼠标滚轮，要求文件管理器为打开的状态
python startmenu_test.py IP	# 测试开始菜单，IP为测试机的地址，结果输出到startmenu.md
```

