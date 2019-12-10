- 下载maven
```
mwget http://mirrors.hust.edu.cn/apache/maven/maven-3/3.1.1/binaries/apache-maven-3.1.1-bin.tar.gz
```
- 解压并重命名
```
tar -xzvf apache-maven-3.1.1-bin.tar.gz
mv apache-maven-3.1.1 /usr/local/maven
```
- 配置环境变量
```
# 打开profile文件
vim /etc/profile 
# 配置如下
export M2_HOME=/usr/local/maven
export PATH=$PATH:$JAVA_HOME/bin:$M2_HOME/bin
# 使配置立即生效
source /etc/profile
# 验证
mvn -v
```