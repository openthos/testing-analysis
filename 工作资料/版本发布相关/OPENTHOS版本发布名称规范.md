## tuna镜像站
- 链接：https://mirrors.tuna.tsinghua.edu.cn/openthos/
### IMG镜像
- 镜像放置在185服务器上提供给tuna同步：rsync@192.168.0.185:/home/rsync/rsync/oto_img/Release/
- 后续版本都跟aosp版本保持一致
- 后续每个定版的大版本中只放最新的稳定版镜像；正在开发的版本放unstable和stable镜像
- 目录名称
   - stable:编译user版本，集成无界面gms，默认集成houdini，每个月所有版本中较稳定版本（设置中"关于设备"版本信息显示为正式版）
   - unstable:编译userdebug版本，默认集成houdini，包括gms。每个周所有版本中较稳定版本（设置中"关于设备"版本信息显示为开发版）
   - developer:编译eng版本，默认集成houdini，包括gms。每个周提供给开发人员安装升级的较稳定版本（有开发者模式，可使用Android Studio）
- 内容：
   - openthos-1.1.0.180730.img
   - openthos-1.1.0.180730.img.md5
### OTA升级
- 位置：rsync@192.168.0.185:/home/rsync/rsync/oto_img/OTA/
- 目录名称同镜像
- 由于正式版和开发版不能交叉升级，每次发布正式版时同时上传正式版和开发版两个升级包
- 内容：
   - 升级包：openthos_V1.1_stable.zip.gpg
   - 版本信息：oto_ota.ver
   - 升级内容描述：ReleaseNotes_1_8_8.md

## 百度网盘
- 链接：https://pan.baidu.com/
- 创建日期目录，将镜像、OTA升级按tuna相同目录结构上传

## fosshub
- 链接：https://devzone.fosshub.com
- 内容与tuna、百度网盘保持一致
