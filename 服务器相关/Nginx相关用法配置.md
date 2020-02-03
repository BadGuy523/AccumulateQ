##### http访问(无ssl，无域名)
###### 简单的反向代理配置
- 首先在nginx主配置中http节点下加上以下语句 (后续内容省略此步骤)
```
# 表示包含了该目录下的所有conf后缀的配置文件，这样方便管理各项配置
include ../vhost/*.conf;
```
- 在此目录下建立xxxx.conf文件编辑如下
```
server {
    listen        80;   # 监听端口
    server_name   192.168.1.1;   # 服务器名，有域名则可填写域名
    location / {
        proxy_pass http://192.168.1.1:9091;  # 转发代理地址
    }
}

```
###### 代理多个网站及服务
- 在配置文件目录下建立xxxx.conf文件，编辑如下
```
server {
    listen        *:7071;
    # server_name   192.168.1.1;
    location / {
        proxy_pass http://192.168.1.1:9091;
    }
}
server {
    listen        *:7072;
    # server_name   192.168.1.1;
    location / {
        proxy_pass http://192.168.1.1:9092;
    }
}

```

[各项配置详解](https://blog.csdn.net/niuxitong/article/details/89327679)

[路由配置详解](https://www.cnblogs.com/jackylee92/p/6836948.html)