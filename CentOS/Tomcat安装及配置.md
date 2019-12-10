- 下载tomcat
```
mwget http://mirrors.tuna.tsinghua.edu.cn/apache/tomcat/tomcat-8/v8.5.49/bin/apache-tomcat-8.5.49.tar.gz
```
- 解压及重命名
```
tar -xzvf apache-tomcat-8.5.49.tar.gz
mv apache-tomcat-8.5.49 tomcat85
```
- 启动
```
# 进入bin目录下
./startup.sh
```
- 查看日志
```
# 进入logs目录下
tail -f catalina.out
```