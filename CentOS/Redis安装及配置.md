- 下载并解压重命名
```
mwget http://download.redis.io/releases/redis-stable.tar.gz
tar -zxvf redis-stable.tar.gz 
mv redis-stable /usr/local/redis
```
- 编译安装
```
cd redis
make MALLOC=libc
# 生成src目录后
cd src
make install
# 测试
./redis-server 
启动成功安装成功
```
- 相关配置
```
# 修改/usr/local/redis下的redis.conf,修改如下
# 后台运行配置
daemonize yes
# 远程连接
bind 0.0.0.0
# 设置密码
requirepass 123
```
- 以修改后的配置运行
```
# 启动redis服务
cd /usr/local/redis/src
./redis-server ../redis.conf
# 启动redis客户端
./redis-cli
# 密码认证
auth 123
# 可以操作redis了
```
- 开机自启动
```
# 先关闭redis服务，找到进程号kill掉
ps -aux | grep redis
kill -9 xxxx

# 修改/usr/local/redis/utils/redis_init_script启动脚本以下内容
REDISPORT=6379
EXEC=/usr/local/bin/redis-server
CLIEXEC=/usr/local/bin/redis-cli

PIDFILE=/var/run/redis_${REDISPORT}.pid
CONF="/etc/redis/${REDISPORT}.conf"

# 复制一份redis-server到/usr/local/bin/(如果此目录下没有)

# 在/etc下创建redis目录
mkdir /etc/redis

# 将redis.conf文件复制一份到/etc/redis目录下并命名为6379.conf
cp redis.conf /etc/redis/6379.conf

# 将redis启动脚本redis_init_script复制一份到/etc/init.d目录下并命名为redisd
cp redis_init_script /etc/init.d/redisd

# 将redisd加入服务并设置开机自启动
chkconfig redisd on

# 使用vim编辑redisd文件，在第一行加入如下两行注释

#!/bin/sh
# chkconfig:   2345 90 10
# description:  Redis is a persistent key-value database
#注释的意思是，redis服务必须在运行级2，3，4，5下被启动或关闭，启动的优先级是90，关闭的优先级是10。

# 修改init.d目录下的redis启动脚本
$CLIEXEC -a "密码"  -p $REDISPORT shutdown

```

