- 下载
```
wget http://jaist.dl.sourceforge.net/project/kmphpfm/mwget/0.1/mwget_0.1.0.orig.tar.bz2
```
- 解压
```
tar -xjvf mwget_0.1.0.orig.tar.bz2
```
- 进入目录
```
cd mwget_0.1.0.orig
```
- 执行./configure
```
./configure
```
- 可能问题
```
# 问题1
# error: C++ compiler cannot create executables
# 说明没有安装c++编译器，安装即可
yum install gcc-c++
# 问题2
# 缺失open-ssl，安装即可
yum install openssl-devel
# 问题3
# configure: error: Your intltool is too old.  You need intltool 0.35.0 or later.
yum install intltool
```
- 如有问题解决后重新执行./configure
- 做最后安装
```
make
make install
```
- 安装完成后即可使用mwget下载
```
mwget https://www.python.org/ftp/python/3.7.0/Python-3.7.0.tgz
```