###### 因为rabbitmq是erlang语言实现，所以需要先安装erlang依赖
- 下载安装erlang
```
wget --content-disposition https://packagecloud.io/rabbitmq/erlang/packages/el/7/erlang-20.3-1.el7.centos.x86_64.rpm/download.rpm
yum install erlang-20.3-1.el7.centos.x86_64.rpm
```
- 下载安装rabbitmq
```
wget https://dl.bintray.com/rabbitmq/all/rabbitmq-server/3.7.5/rabbitmq-server-3.7.5-1.el7.noarch.rpm
yum install rabbitmq-server-3.7.5-1.el7.noarch.rpm
```
- 拷贝rabbitmq.conf至/etc/rabbitmq目录下
```
cp /usr/share/doc/rabbitmq-server-3.6.6/rabbitmq.config.example /etc/rabbitmq/rabbitmq.config
```
- 修改rabbitmq.config配置以便可以远程访问
```
将%% {loopback_users,[]},去掉前面的%%以及最后面的,
将%% {tcp_listeners,[]},去掉前面的%%
然后重启服务即可通过15672访问rabbitmq
```
- 卸载erlang
```
# 查看erlang安装的相关列表
yum list | grep erlang
# 卸载erlang已安装的相关内容
yum -y remove erlang-*
yum remove erlang.x86_64
```