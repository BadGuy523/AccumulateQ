### 触发器(trigger)
###### 什么是触发器
- 触发器就是某个表发生一个事件（增删改操作），然后自动的执行预先编译好的SQL语句，执行相关操作。触发器事件跟触发器中的SQL语句是原子性的（要么同时执行，要么同时不执行），这样保证了数据的完整性。
###### 优点
- 安全性。可以基于数据库的值使用户具有操作数据库的某种权利。
- 审计。可以跟踪用户对数据库的操作。   
- 实现复杂的非标准的数据库相关完整性规则。触发器可以对数据库中相关的表进行连环更新。
- 触发器能够拒绝或回退那些破坏相关完整性的变化，取消试图进行数据更新的事务。
- 当插入一个与其主健不匹配的外部键时，这种触发器会起作用。
- 同步实时地复制表中的数据。
- 自动计算数据值，如果数据的值达到了一定的要求，则进行特定的处理。
###### 弊端:
- 增加程序的复杂度，有些业务逻辑在代码中处理，有些业务逻辑用触发器处理，会使后期维护变得困难.
- 如果需要变动整个数据集而数据集数据量又较大时，触发器效果会非常低.
- 对于批量操作并不适合使用触发器 
- 使用触发器实现的业务逻辑在出现问题时很难进行定位，特别是设计到多个触发器的情况 协同开发时，写业务层代码如果不清楚数据库 触发器的细节，容易搞不清到底触发了那些触发器 大量使用触发器会导致代码结构容易被打乱，阅读源码困难.
###### 实例来说明用法
- 建立三张表格：user,goods,order模拟用户下单与库存之间的数据交互
```
CREATE TABLE `user` (
  `id` varchar(100) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `account` varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
)
CREATE TABLE `goods` (
  `id` varchar(100) NOT NULL COMMENT '主键',
  `goods_name` varchar(100) DEFAULT NULL COMMENT '商品名称',
  `num` int(11) DEFAULT NULL COMMENT '商品数量',
  PRIMARY KEY (`id`)
);
CREATE TABLE `order` (
  `id` varchar(100) NOT NULL COMMENT '主键',
  `goods_id` varchar(100) DEFAULT NULL COMMENT '商品id',
  `goods_num` int(11) DEFAULT NULL COMMENT '商品数量',
  `user_id` varchar(100) DEFAULT NULL COMMENT '用户id',
  PRIMARY KEY (`id`)
)
```
- 在向order中插入数据时修改goods表中对应的商品数量
```
DROP TRIGGER IF EXISTS order_insert;
CREATE TRIGGER order_insert
AFTER	INSERT ON `order`
FOR EACH ROW
BEGIN
	DECLARE goodsNum INT(11);
	DECLARE goodsId VARCHAR(100);
	SET goodsNum = new.goods_num;
	SET goodsId = new.goods_id;
	UPDATE goods SET num = (num - goodsNum) WHERE id = goodsId;
END
```
- 其余用法，如触发前判断并中止操作？
```
-- 尝试回滚
DROP TRIGGER IF EXISTS order_insert;
CREATE TRIGGER order_insert
AFTER	INSERT ON `order`
FOR EACH ROW
BEGIN
	DECLARE goodsNum INT(11);
	DECLARE goodsId VARCHAR(100);
	SET goodsNum = new.goods_num;
	SET goodsId = new.goods_id;
	IF goodsNum <= (SELECT num from goods WHERE id = goodsId) THEN
		UPDATE goods SET num = (num - goodsNum) WHERE id = goodsId;
	ELSE
		ROLLBACK;
	END IF;
END
-- 由于在触发器中不允许显式或隐式的事务操作，所以以上写法是不正确的
-- 隐式提交事务的sql如下
 ALTER FUNCTION, ALTER PROCEDURE, ALTER TABLE, BEGIN, CREATE DATABASE, 
 CREATE FUNCTION, CREATE INDEX, CREATE PROCEDURE, CREATE TABLE, DROP DATABASE, 
 DROP FUNCTION, DROP INDEX, DROP PROCEDURE, DROP TABLE, LOAD MASTER DATA, 
 LOCK TABLES, RENAME TABLE, SET AUTOCOMMIT=1, START TRANSACTION, 
 TRUNCATE TABLE, UNLOCK TABLES.
-- 另一种达到回滚目的的写法
DROP TRIGGER IF EXISTS order_insert;
CREATE TRIGGER order_insert
AFTER	INSERT ON `order`
FOR EACH ROW
BEGIN
	DECLARE goodsNum INT(11);
	DECLARE goodsId VARCHAR(100);
	SET goodsNum = new.goods_num;
	SET goodsId = new.goods_id;
	IF goodsNum <= (SELECT num from goods WHERE id = goodsId) THEN
		UPDATE goods SET num = (num - goodsNum) WHERE id = goodsId;
	ELSE
		DELETE FROM `order` WHERE id = new.id;
	END IF;
END
-- 以上写法在不满足条件时数据库会报错来终止插入操作，如配合jdbc操作也许需要捕获异常来判断执行流程
```
###### 配合mybatis如何使用
- 可在mybatis xml文件中编写触发器，在程序中调用
###### 关于触发器的一些命令操作
- 查看触发器
```
-- 方式1
select * from information_schema.`triggers`;
select * from information_schema.`triggers` where trigger_name = 'xxx';
-- 方式2(先选择数据库)
use test;
show triggers;
```
- 删除触发器
```
drop trigger trigger_name;
```
### 存储过程(Stored Procedure)
###### 什么是存储过程
- 存储过程就是作为可执行对象存放在数据库中的一个或多个SQL命令。 
通俗来讲：存储过程其实就是能完成一定操作的一组SQL语句。
###### 优点
- 存储过程只在创造时进行编译，以后每次执行存储过程都不需再重新编译，而一般SQL语句每执行一次就编译一次,所以使用存储过程可提高数据库执行速度。
- 当对数据库进行复杂操作时，可将此复杂操作用存储过程封装起来与数据库提供的事务处理结合一起使用。
- 存储过程可以重复使用,可减少数据库开发人员的工作量。
- 安全性高,可设定只有某些用户才具有对指定存储过程的使用权
###### 缺点
- SQL本身是一种结构化查询语言，但不是面向对象的的，本质上还是过程化的语言，面对复杂的业务逻辑，过程化的处理会很吃力。同时SQL擅长的是数据查询而非业务逻辑的处理，如果如果把业务逻辑全放在存储过程里面，违背了这一原则。
- 如果需要对输入存储过程的参数进行更改，或者要更改由其返回的数据，则您仍需要更新程序集中的代码以添加参数、更新调用，等等，这时候估计会比较繁琐了。
- 开发调试复杂，由于IDE的问题，存储过程的开发调试要比一般程序困难。   - 没办法应用缓存。虽然有全局临时表之类的方法可以做缓存，但同样加重了数据库的负担。如果缓存并发严重，经常要加锁，那效率实在堪忧。
- 不支持群集，数据库服务器无法水平扩展，或者数据库的切割（水平或垂直切割）。数据库切割之后，存储过程并不清楚数据存储在哪个数据库中。
###### 存储过程写法(根据以上示例)
- 无参数写法
```
//创建存储过程
CREATE PROCEDURE pro ()
BEGIN
	SELECT * from `user`;
END;

//调用存储过程
CALL pro ()
```
- 有参数写法
```
CREATE PROCEDURE pro(IN targetId VARCHAR(100),OUT loginName VARCHAR(255))
# 提示信息
COMMENT '根据id查询用户'
# 指明只有定义此sql的人才能执行，默认也是这个
SQL SECURITY DEFINER
BEGIN
	SELECT account INTO loginName from `user` WHERE id = targetId;
END

CALL pro ('1',@loginName);
SELECT @loginName;
```
- 存储函数
```
# 创建函数
CREATE FUNCTION getLoginName(userId VARCHAR(11)) RETURNS VARCHAR(255)
BEGIN
RETURN (SELECT account FROM `user` WHERE id = userId);
END
# 调用函数并取值
SELECT getLoginName('1')
```
[存储过程语法拓展](https://blog.csdn.net/yanluandai1985/article/details/83656374)

[自定义函数](https://www.cnblogs.com/progor/p/8871480.html)

###### 存储过程中提交事务问题
- 如果存储过程中没有执行commit，那么spring容器一旦发生了事务回滚，存储过程执行的操作也会回滚。如果存储过程执行了commit，那么数据库自身的事务此时已提交，这时即使在spring容器中托管了事务，并且由于其他原因导致service代码中产生异常而自动回滚，但此存储过程是不会回滚，因为数据自身的事务已在存储过程执行完毕前提交了，  也就是说此时spring回滚对存储过程的操作是无效的了。