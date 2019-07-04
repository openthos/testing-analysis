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
