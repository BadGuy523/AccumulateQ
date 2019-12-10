- 查看内核版本，至少3.8以上，建议3.10
```
uname -a
```
- 更新yum包
```
yum update
```
- 安装需要的软件包
```
# yum-util 提供yum-config-manager功能，另外两个是devicemapper驱动依赖的
yum install -y yum-utils device-mapper-persistent-data lvm2
```
- 设置yum源
```
yum-config-manager --add-repo http://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo
```
- 查看仓库中所有docker版本
```
yum list docker-ce --showduplicates | sort -r
```
- 选择指定版本进行安装
```
yum install docker-ce-18.03.1.ce
```
- 启动docker
```
systemctl start docker
```
- 设置开启启动
```
systemctl enable docker
```
- 查看docker版本
```
docker version
```