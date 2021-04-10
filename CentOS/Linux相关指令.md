###### 系统相关信息查询
- cat /etc/redhat-release：查看操作系统版本号
- cat /proc/cpuinfo：查看CPU信息
- cat /proc/cpuinfo| grep "cpu cores"| uniq：查看CPU核心数
- uname -a：查看内核版本
- env：查看环境变量
- uptime：查看系统运行时间，用户数，负载情况
- top：查看进程活动以及一些系统状况 [参数详解](https://blog.csdn.net/ai2000ai/article/details/79786459)
- free：查看物理内存及交换区 [简单介绍](https://blog.csdn.net/zwan0518/article/details/12059213)
- df -h:查看centos磁盘空间大小
- du -sh:查看当前文件夹下所有文件大小
- du -h /data/:查看指定文件下所有文件大小
- du -h install.log:查看指定文件大小
- ifconfig或ip addr：查看网络配置
- ll --time=mtime (目录名): 展示文件信息(时间：文件内容更改时更新)(ll默认)
- ll --time=atime (目录名): 展示文件信息(时间：文件被访问时更新)
- ll --time=ctime (目录名): 展示文件信息(时间：文件状态被更改时更新)(文件权限或属性)
###### 防火墙相关指令
- firewall-cmd --state： 防火墙状态
- systemctl start firewalld.service：  开启防火墙
- systemctl stop firewalld.service：  关闭防火墙
- firewall-cmd --zone=public --add-port=3306/tcp --permanent：  开放端口
- firewall-cmd --zone=public --remove-port=3306/tcp --permanent：  禁用端口
- systemctl restart firewalld.service：  重启防火墙
- firewall-cmd --reload：   重新载入配置
- systemtcl disable firewalld： 关闭防火墙开机自启
- firewall-cmd --list-all  查看开放端口列表
###### 端口进程相关指令
- netstat -nupl： 查看所有UDP类型的端口
- netstat -ntpl： 查看所有TCP类型的端口
- lsof -i | grep pid： 根据进程pid查端口
- lsof  -i:port：根据端口port查进程
- netstat -nap | grep pid： 根据进程pid查端口
- netstat -nap | grep port：根据端口port查进程
- ps:显示当前状态处于running的进程
- ps | grep name:加搜索条件
- ps -aux：显示所有进程和其状态
- ps -aux | grep name：加搜索条件
###### 上传下载
- scp username@servername:/path/filename /Users/mac/Desktop：从服务器下载文件到本地
- scp -r username@servername:/root/（远程目录） /Users/mac/Desktop：从服务器下载整个目录
- scp /path/filename username@servername:/path：上传本地文件至服务器
- scp -r localdir username@servername:remote_dir:上传目录到服务器
- 注：目标服务器要开启写入权限。
###### CentOS7服务管理systemctl
- systemctl start xxx：服务开启
- systemctl stop xxx：服务关闭
- systemctl disable xxx：关闭服务的开机自启动
- systemctl enable xxx：开启服务的开机自启动
###### 下载安装
- rpm -qa | grep xxxx：查看rpm是否安装了某个软件
- yum list installed | grep perl：查看yum是否安装了某个软件
- rpm -qa:查看所有已安装软件名称
- rpm -ql xxxx:显示软件的安装路径
- rpm -qa | grep xxxx：列出所有安装的xxxx
- rpm -qal | grep xxxx：
###### 实用案例
- 用grep遍历目标目录下的所有包含某字符串的文件
```
# 比如：你的字符是 “some_code” ,文件夹名字是  YOUR_DIR
grep -R   "some_code"  YOUR_DIR
 
# 如果你不需要显示内容，只需要含有某个字符的文件： 
grep -lR   "some_code"  YOUR_DIR
```
- 在Linux服务器上部署SpringBoot项目:首先将SpringBoot项目打包成JAR包,通过xFTP或者其他工具将JAR包上传到Linux上,然后执行如下命令启动项目:
```
java -jar xxx.jar &
# 该命令启动jar,一旦Xshell窗口关闭,JAR就停止运行了.
# 如果想让项目在后台一直运行,通过如下命令启动JAR:
nohup java -jar xxx.jar  > consoleMsg.log 2>&1 &
# 上面的2 和 1 的意思如下:
# 0    标准输入（一般是键盘）
# 1    标准输出（一般是显示屏，是用户终端控制台）
# 2    标准错误（错误信息输出）
# 注意:consoleMsg.log文件要先创建,执行命令
touch consoleMsg.log
```
