```
# 找到配置文件加上如下配置跳过密码验证
skip-grant-tables
# 重启mysql服务
# 登录mysql（无需输入密码，直接enter）
mysql -u root -p
# 选择mysql数据库
use mysql; 
# 修改密码（5.6）
update user set password=password("新密码") where user="root"; 
# 修改密码（5.7）
update user set authentication_string=password("新密码") where user="root";
# 刷新数据库
flush privileges;
# 将之前修改的配置还原，并重启mysql服务
```