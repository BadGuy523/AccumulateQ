- 在windows或mac上下载jdk(.tar.gz文件)，通过ftp或scp命令拷贝至服务器/usr/local目录下
- 解压并重命名
```
tar -xzvf jdk-8u231-linux-x64.tar.gz
mv jdk1.8.0_231 jdk18
```
- 设置环境变量配置
```
# 打开profile文件
vim /etc/profile
# 配置如下
JAVA_HOME=/usr/local/jdk18
CLASSPATH=.:$JAVA_HOME/lib.tools.jar
PATH=$JAVA_HOME/bin:$PATH
export JAVA_HOME CLASSPATH PATH
# 使配置立即生效
source /etc/profile
# 验证
java -version 
javac -version
```