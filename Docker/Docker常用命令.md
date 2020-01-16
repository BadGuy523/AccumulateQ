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

```
- [EXPOSE详解](https://blog.csdn.net/finalheart/article/details/100751447)
- [修改容器配置](https://www.jianshu.com/p/1c4ca951849d)
- [DockerFile的各项参数](https://cloud.tencent.com/developer/news/275000)
- [参考地址1](https://www.jianshu.com/p/a84e8cf33b34)
- [参考地址2](https://blog.csdn.net/lizhiqiang1217/article/details/89070075)
- [参考地址3](https://blog.csdn.net/yufei_java/article/details/78739667)