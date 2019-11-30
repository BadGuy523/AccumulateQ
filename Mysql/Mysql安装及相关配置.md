##### Windows环境
###### 安装
- 在此[镜像地址](http://mirrors.sohu.com/mysql/MySQL-5.6/)下载mysql-5.6.43-winx64.msi文件后进行安装
- 配置my-default.ini或者my.ini
```
[mysqld]
#设置服务器字符集为utf8
character_set_server=utf8
collation-server=utf8_general_ci
#设置mysql的安装目录
basedir = D:\mysql-server
#设置mysql的数据文件存放目录
datadir = D:\mysql-server\data
#设置mysql服务所绑定的端口
port = 3306
#设置mysql允许的最大连接数
max_connections=15

[client]    
#设置客户端字符集
default-character-set=utf8
```
- 打开cmd进入C:\Program Files\MySQL\MySQL Server 5.6\bin执行mysql指令提示Can't connect to MySQL server on localhost (10061)
- 进入windows服务发现并没有mysql服务
- 以管理员身份打开cmd进入C:\Program Files\MySQL\MySQL Server 5.6\bin执行mysqld --install  Mysql5.6 ，执行成功后提示Service  Successfully Installed
- 进入windows服务启动mysql服务即可
###### 配置
- 设置密码
```
# 用root账号登陆mysql
mysql -u root
# 选择mysql数据库
use mysql
# 修改root用户密码
update user set password=password('123') where user='root';
# 刷新数据库配置
flush privileges;
# 退出
quit
# 用root账号进行密码登录
mysql -u root -p
```
- 配置远程访问
```
# 设置远程访问权限
grant all privileges on *.* to root@'%' identified by '123456';
# 刷新数据库配置
flush privileges;
```