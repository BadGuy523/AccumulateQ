- 准备工作
```
# 安装make
yum -y install gcc automake autoconf libtool make
# 安装g++
yum install gcc gcc-c++
# 安装pcre
cd /usr/local/src
wget ftp://ftp.csx.cam.ac.uk/pub/software/programming/pcre/pcre-8.39.tar.gz 
tar -zxvf pcre-8.37.tar.gz
cd pcre-8.34
./configure
make
make install
# 安装zlib
cd /usr/local/src
 
wget http://zlib.net/zlib-1.2.11.tar.gz
tar -zxvf zlib-1.2.11.tar.gz
cd zlib-1.2.11
./configure
make
make install
# 安装openssl
cd /usr/local/src
wget https://www.openssl.org/source/openssl-1.0.1t.tar.gz
tar -zxvf openssl-1.0.1t.tar.gz
```
- 安装nginx
```
cd /usr/local
mwget http://nginx.org/download/nginx-1.16.0.tar.gz
tar -zxvf nginx-1.16.0.tar.gz
rm -rf nginx-1.16.0.tar.gz
cd nginx-1.16.0
./configure
make
make install
```
- nginx启动
```
# 方式一：nginx安装目录地址 -c nginx配置文件地址
/usr/local/nginx/sbin/nginx -c /usr/local/nginx/conf/nginx.conf
# 方式二：进入nginx的安装目录，进入/sbin并执行./nginx命令即可
cd usr/local/nginx/sbin
./nginx
```
- nginx停止
```
# 进入sbin目录下命令停止
./nginx -s stop
# 杀进程停止
ps -ef|grep nginx
kill -9 xxx
```
- 验证nginx配置文件是否正确
```
# 方法一：进入nginx安装目录sbin下，输入命令./nginx -t
# 方法二：在启动命令-c前加-t
```
- 重启nginx服务
```
# 方法一：进入nginx可执行目录sbin下，输入命令./nginx -s reload
# 方法二：查找当前nginx进程号，然后输入命令：kill -HUP 进程号 实现重启nginx服务
```