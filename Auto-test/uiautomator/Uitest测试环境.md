#### 安装ubuntu系统
版本无所谓，语言必须是英文
#### 下载安装jdk

  - 下载地址:http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
  
  - 选择下载版本:jdk-8u111-linux-x64.tar.gz
  
  - 解压文件:
 
  ```
  tar -zxvf jdk-8u111-linux-x64.tar.gz
  ```
  - 重命名文件并移动到/usr/lib下面: 
 
  ```
  mv jdk1.8.0_111 /usr/lib/jdk8
  ```
  - 配置环境变量
  ```
  sudo vi /etc/profile
  ```
  - 添加以下几行
  
  ```   
  export JAVA_HOME=/usr/lib/jdk8
  export JRE_HOME=$JAVA_HOME/jre    
  export CLASSPATH=.:$JAVA_HOME/lib:$JRE_HOME/lib 
  export PATH=$JAVA_HOME/bin:$PATH 
  ```  
  - 使配置生效
  
  ```
  source /etc/profile
  ```
  ```
  vi ~/.bashrc
  ```
  - 添加：
  ```
  source /etc/profile
  ```
  
  - 验证是否配置成功
  
  ```
  java -version
  ```
  出现jdk版本信息，则配置成功
  
  
#### 安装adb
#### 拷贝sdk
  ```
  scp lh@192.168.0.180:/home/lh/zlp/sdk.tar.gz .
  ```
#### 配置环境变量
   ```
   sudo vi /etc/profile
   export PATH=$PATH:/sdk解压的目录/Sdk/tools:sdk解压的目录/Sdk/platform-tools:/sdk解压的目录/Sdk/tools/bin
   ```
    - 重启电脑
    - 验证是否成功
   
   ```
   android list target
   ```
    - 显示版本为22
