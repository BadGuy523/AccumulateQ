- 安装必要组件
```
yum install –y autoconf automake imake libxml2-devel expat-devel cmake gcc gcc-c++ libaio libaio-devel bzr bison libtool ncurses5-devel
```
- 下载mysql
```
mwget https://dev.mysql.com//Downloads/MySQL-5.7/mysql-5.7.11-linux-glibc2.5-x86_64.tar.gz
```
- 解压并重命名
```
tar zxvf mysql-5.7.11-linux-glibc2.5-x86_64.tar.gz
mv mysql-5.7.11-linux-glibc2.5-x86_64/ /usr/local/mysql
```
- 进入安装文件夹下的support-files，将MySQL配置文件拷贝到etc下
```
cd support-files/
cp my-default.cnf /etc/my.cnf
```
- 编辑etc下的配置文件
```
vim /etc/my.cnf
# [mysqld]下添加
character-set-client-handshake = FALSE 
character-set-server = utf8mb4
collation-server = utf8mb4_unicode_ci
# [client]下添加
default-character-set=utf8mb4
```
- 复制support-files下的mysql.server到/etc/init.d(实现开机自动执行)并进行修改
```
cp mysql.server /etc/init.d/mysql
vim /etc/init.d/mysql
# 修改如下
basedir=/usr/local/mysql
datadir=/usr/local/mysql/data
```
- 创建Linux新用户
```
#建立一个mysql的组
groupadd mysql 
#建立mysql用户，并且把用户放到mysql组
useradd -r -g mysql mysql 
#给mysql用户设置一个密码
passwd mysql mysql123
#给目录/usr/local/mysql 更改拥有者
chown -R mysql:mysql /home/mysql57/mysql/ 
```
- 初始化 mysql 的数据库（记录生成的密码）
```
cd /usr/local/mysql/bin
./mysqld --initialize --user=mysql --basedir=/usr/local/mysql --datadir=/usr/local/mysql/data
```
- 给数据库加密
```
./mysql_ssl_rsa_setup --datadir=/usr/local/mysql/data
# 启动mysql(为了不让进程卡主，可在启动mysql的命令后加上&代表此进程在后台运行)
./mysqld_safe --user=mysql &
```
- 登陆mysql并配置
```
# 登陆
cd /usr/local/mysql/bin/
./mysql -uroot -p （输入临时密码）
# 修改密码
set password=password('/* 新密码 */'); 
# 开启远程登陆
grant all privileges on *.* to root@'%' identified by '/* 新密码 */';
# 查看修改
use mysql;  // 进入数据库
select host,user from user; 【多出1条远程登录用户记录】
# 刷新数据库配置
flush privileges; 
```
- 在Linux命令行中将3306端口打开
```
/sbin/iptables -I INPUT -p tcp --dport 3306 -j ACCEPT
firewall-cmd --zone=public --add-port=3306/tcp --permanent
firewall-cmd --reload
```
- 设置开机自启动
```
# 添加服务mysql
chkconfig --add mysql
# 设置mysql服务为自动
chkconfig mysql on
```
- 将mysql添加到环境变量
```
# 修改/etc/profile,加上如下两句
PATH=$PATH:/usr/local/mysql/bin
export PATH
# 执行 命令source /etc/profile或 执行点命令 ./profile使其修改生效，执行完可通过echo $PATH命令查看是否添加成功。
```