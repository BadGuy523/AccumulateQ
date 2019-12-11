- 原因
    1. mac下没有配置maven的环境变量
- mac系统的环境变量，加载顺序如下
```
a. /etc/profile
b. /etc/paths
c. ~/.bash_profile
d. ~/.bash_login
e. ~/.profile
f. ~/.bashrc
# a和b是系统级别的，系统启动就会加载
# 其余是用户级别的，c,d,e按照从前往后的顺序读取，如果c文件存在，则后面几个文件就会忽略不读了 
# ~/.bashrc没有上述规则，它是bash shell打开的时候载入
# 建议在c中添加环境变量
```
- 修改步骤
```
# 打开c文件
vim ~/.bash_profile
# 加入以下内容(这里用的是idea自带的maven插件)
export IDEA_MAVEN=/Applications/IntelliJ\ IDEA.app/Contents/plugins/maven/lib/maven3
export PATH=$PATH:$IDEA_MAVEN/bin
# 刷新配置文件
source ~/.bash_profile
```
- 可能遇到的问题(执行权限不够)
```
/Applications/IntelliJ IDEA.app/Contents/plugins/maven/lib/maven3/bin/mvn: Permission denied
```
- 解决方案
```
# 进入以下目录
cd /Applications/IntelliJ\ IDEA.app/Contents/plugins/maven/lib/maven3/bin
# 给mvn增加执行权限(a:所有用户 +:增加权限 x:执行权限)
chmod a+x mvn
```