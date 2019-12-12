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
CREATE TABLE "user" (
  "id" varchar(100) NOT NULL,
  "name" varchar(100) DEFAULT NULL,
  "age" int(11) DEFAULT NULL,
  "account" varchar(100) DEFAULT NULL,
  "password" varchar(100) DEFAULT NULL,
  PRIMARY KEY ("id")
)
CREATE TABLE "goods" (
  "id" varchar(100) NOT NULL COMMENT '主键',
  "goods_name" varchar(100) DEFAULT NULL COMMENT '商品名称',
  "num" int(11) DEFAULT NULL COMMENT '商品数量',
  PRIMARY KEY ("id")
)
CREATE TABLE "order" (
  "id" varchar(100) NOT NULL COMMENT '主键',
  "goods_id" varchar(100) DEFAULT NULL COMMENT '商品id',
  "goods_num" int(11) DEFAULT NULL COMMENT '商品数量',
  "user_id" varchar(100) DEFAULT NULL COMMENT '用户id',
  PRIMARY KEY ("id")
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
```
###### 配合mybatis如何使用？
- 
###### 关于触发器的一些命令行操作？
- 