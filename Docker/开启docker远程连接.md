```
# 进入docker配置文件
vim /usr/lib/systemd/system/docker.service
# 找到如下信息
ExecStart=/usr/bin/dockerd
# 修改如下
ExecStart=/usr/bin/dockerd -H tcp://0.0.0.0:2375
# 重新加载docker配置
systemctl daemon-reload
# 重启docker
systemctl restart docker
```
- 问题：修改后使用docker命令报错(待解决)
```
Cannot connect to the Docker daemon at unix:///var/run/docker.sock. Is the docker daemon running?
```
