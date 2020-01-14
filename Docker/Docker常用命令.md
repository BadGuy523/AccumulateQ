- 更改默认仓库地址
```
# 在/etc/docker/daemon.json文件(没有则创建该文件)添加如下配置
"registry-mirrors": ["https://registry.docker-cn.com"] # registry.docker-cn.com是国内的镜像网站
# 如果只是临时的从其他仓库下载镜像，可以在docker pull的时候指定镜像的全路径
docker pull your-registry-server/your/image/path
```
- [参考地址1](https://www.jianshu.com/p/a84e8cf33b34)
- [参考地址2](https://blog.csdn.net/lizhiqiang1217/article/details/89070075)
- [参考地址3](https://blog.csdn.net/yufei_java/article/details/78739667)