- 更改默认仓库地址
```
# 在/etc/docker/daemon.json文件(没有则创建该文件)添加如下配置
"registry-mirrors": ["https://registry.docker-cn.com"] # registry.docker-cn.com是国内的镜像网站
# 如果只是临时的从其他仓库下载镜像，可以在docker pull的时候指定镜像的全路径
docker pull your-registry-server/your/image/path
```
- jar包制作docker镜像
```
# 在jar包所在目录下创建Dockerfile
FROM java:8     #项目依赖，需要一个jdk8
ADD docker-demo.jar docker-demo.jar     #第一个参数为jar包名称，第二个为打包后的自定义名称
EXPOSE 8081     #项目运行时的端口（暴露容器提供服务的端口）
ENTRYPOINT ["java","-jar","/docker-demo.jar"]   #运行jar包的指令
# EXPOSE在dockerfile中暴露出容器将要提供服务所开放的端口。run的时候直接 docker run -d --net=host  image:tag
# 这样可以不手动-p指定端口映射关系，更简洁了，从而使EXPOSE发挥最大的作用。而不是简单的随机映射宿主机&&起到注释作用。
# 而这么做不灵活点在于宿主机的端口被EXPOSE固定。如果宿主机端口被其他进程占用，就port already in use了。

# 另一种方式,建立一个data目录，用作后面的虚拟机和宿主机的地址映射
FROM java:8
run mkdir -p /data/   
EXPOSE 8082
ENTRYPOINT ["java","-jar","/data/demo.jar"]


```
- 创建docker镜像命令
```
# .表示当前路径，在Dockerfile所在路径下执行
docker build -t code_demo_image .
```
- 根据镜像创建docker容器
```
# --name为容器名称
# -d表示后台运行容器，并返回容器ID，也即启动守护式容器
# -P:若Dockerfile中指定了EXPOSE端口，则可使用-P随机端口映射
# -p：指定端口映射，有以下四种格式：
# ip:hostPort:containerPort
# ip::containerPort
# hostPort:containerPort
# containerPort
# 几种创建方式
docker run --name code-demo-9091 -d -p 9091:8081 code_demo_image
docker run --name code-demo-9092 -d -P code_demo_image
# 经实验，此种创建方式也会自动随机映射端口
docker run --name code-demo-9093 -d -p 8081 code_demo_image
# --network=host,表示不做端口映射，宿主机端口与容器端口一一对应
# -v /dir:/dir2 表示宿主机目录挂载到容器目录，：前面为宿主机，后面为容器
# /bin/bash的作用是因为docker后台必须运行一个进程，否则容器就会退出，在这里表示启动容器后启动bash
docker run -d --network=host --name computing-ioc-ma2 -v /opt/computing-ioc-ma:/data 69a3a8ff9980 /bin/bash

```
- 进入docker容器
```
docker exec -it container_name /bin/bash
```
- docker日志处理（按时间3天分割日志，并定时清理超过30天的日志）
```
#split_docker_logs_by_three_day.sh执行文件
logs=`find /var/lib/docker/containers/ -name *-json.log`  #查询所有以-json.log结尾的文件
for docker_log in $logs   #循环日志文件
do
        DATE=`date +%F`   #获取当前日期
        cp $docker_log $docker_log.$DATE    #复制原日志文件，文件名加上当前日期
        echo "" > $docker_log     #清空原日志文件
done

#rm_docker_log_by_one_month.sh执行文件
logs=`find /var/lib/docker/containers/`     #查询目录下所有文件
for log in $logs    #循环文件
do
        FILE=${log%%.*}   #以最后一个.分割文件，取左边部分
        DATE=${log##*.}   #以最后一个.分割文件，取右边部分
        if echo $DATE | grep -Eq "[0-9]{4}-[0-9]{2}-[0-9]{2}" && date -d $DATE +%Y%m%d > /dev/null 2>&1   #判断右边部分是否为日期格式
        then
                days=$((($(date +%s)-$(date +%s -d $DATE))/86400))    #若为日期格式，则判断与当前日期所差天数
                if [ $days -gt 30 ]     #若大于30天   则删除日志文件
                then
                        rm -rf $log
                fi
        fi
done
```
- centos定时任务执行以上两个脚本
```
crontab -e      #编辑定时任务

#定时任务内容
* * */3 * * /bin/bash /opt/log_split_schedule/split_docker_log_by_three_day.sh
* * */1 * * /bin/bash /opt/log_split_schedule/rm_docker_log_by_one_month.sh

#加上可执行权限
chmod +x rm_docker_log_by_one_month.sh
```
- [shell命令判断日期格式是否正确](https://blog.csdn.net/weixin_30725315/article/details/97744504)
- [shell命令计算日期之间的天数](https://blog.csdn.net/mydriverc2/article/details/78592107)
- [shell命令截取文件名和文件目录](https://blog.csdn.net/u010670689/article/details/53425111)
- [shell命令echo创建文件及追加内容](https://blog.csdn.net/wwwlyj123321/article/details/81669342)
- [shell命令docker日志清理与分割](https://blog.csdn.net/GX_1_11_real/article/details/84537720)

- [EXPOSE详解](https://blog.csdn.net/finalheart/article/details/100751447)
- [修改容器配置](https://www.jianshu.com/p/1c4ca951849d)
- [DockerFile的各项参数](https://cloud.tencent.com/developer/news/275000)
- [参考地址1](https://www.jianshu.com/p/a84e8cf33b34)
- [参考地址2](https://blog.csdn.net/lizhiqiang1217/article/details/89070075)
- [参考地址3](https://blog.csdn.net/yufei_java/article/details/78739667)
