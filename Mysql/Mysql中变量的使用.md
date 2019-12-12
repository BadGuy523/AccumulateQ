#### 变量的类型
- MySQL中的存储过程类似java中的方法，在存储过程中也同样可以使用变量。
- mysql中的变量可以分为以下几个类型：局部变量，用户变量，会话变量，全局变量。
###### 局部变量
- MySQL中的局部变量与java中的局部变量非常类似，java中的局部变量作用域是变量所在的方法，而MySQL中的局部变量作用域是所在的存储过程。
```
# 声明变量var
declare var int(11);
# 给变量var赋值方式1
set var = 3;
# 给变量var赋值方式2：将查询结果赋值给变量
select u_id into var2 from users where u_name = 'zhangsan';
```
###### 用户变量
- 用户变量类似于java中的成员变量，java中的成员变量使用对象访问，例如：user.getName();访问User类中的name成员变量，在不做修改的前提下，无论调用多少次该方法返回值都是一样的，成员变量的作用域是所在类。MySQL中的用户变量的作用域是当前连接，声明、赋值和查询都是用@符号。
```
#变量的赋值方式一：直接赋值，方式有以下两种。
set @var1 = 2;
set @var2 := 3;
#变量的赋值方式二：将查询结果赋值给变量
select u_id into @var3 from users where u_name = 'zhangsan';
```
###### 会话变量
- 服务器为每个连接的客户端维护一系列会话变量。其作用域仅限于当前连接，即每个连接中的会话变量是独立的。以下是对于会话变量的相关操作：
```
#显示所有的会话变量
show session variables;
       
#查询会话变量的值，方式有以下三种。
show variables like '%auto_increment_increment%'; #查询变量值的通用方式
select @@auto_increment_increment;                #使用@@方式查询
select @@session.auto_increment_increment;        #使用@@session.的方式，类似于java中对象名.变量名 
select @@local.auto_increment_increment;          #使用@@local.的方式，类似于java中对象名.变量名 

#设置会话变量的值,方式有以下三种。
set auto_increment_increment=1;           #直接设置
set session auto_increment_increment=1;   #使用session关键字，设置选定的范围
set @@session.auto_increment_increment=1; #使用@@session.的方式，类似于java中对象名.变量名
set @@local.auto_increment_increment=1;   #使用@@local.的方式，类似于java中对象名.变量名
```
###### 全局变量
- 当服务启动时，它将所有全局变量初始化为默认值。其作用域为server的整个生命周期。
```
#显示所有的全局变量
show global variables;

#查询全局变量的值的两种方式
show variables like '%sql_warnings%'; #查询变量值的通用方式
select @@global.sql_warnings;         #使用@@global.的方式，类似于java中对象名.变量名


#设置全局变量的值的两种方式
set sql_warnings=FALSE;        #直接设置
set global sql_warnings=FALSE; #使用global关键字，设置选定的范围，最好加上global
set @@global.sql_warnings=OFF; #使用@@global.的方式，类似于java中对象名.变量名
```