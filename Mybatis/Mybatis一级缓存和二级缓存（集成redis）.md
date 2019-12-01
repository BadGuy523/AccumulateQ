### 一级缓存
- 默认情况下一级缓存是开启的，且是不能关闭的，一级缓存作用域在同一个SqlSession中，当执行相同的sql语句查询时，第二次及以后的查询不会从数据库查询，直接从SqlSession中的本地缓存获取
- Mybatis的内部缓存使用一个HashMap，key为hashcode+statementId+sql语句。Value为查询出来的结果集映射成的java对象
- SqlSession执行insert、update、delete等操作commit后会清空该SQLSession缓存
- MyBatis的一级缓存最大范围是SqlSession内部，有多个SqlSession或者分布式的环境下，数据库写操作会引起脏数据，建议设定缓存级别为Statement
```
mybatis:
  configuration:
    # 设置一级缓存级别
    local-cache-scope: statement
```
- statement级别判断为同一查询的条件
    1. 传入的statementId(namespace+mapperId)分别对应接口名和方法名  
    2. 查询时要求的结果集中的结果范围（结果的范围通过rowBounds.offset和rowBounds.limit表示）；  
    3. 这次查询所产生的最终要传递给JDBC java.sql.Preparedstatement的Sql语句字符串（boundSql.getSql() ）  
    4. 传递给java.sql.Statement要设置的参数值，包括顺序  
###### 事务的一些影响（session级别）
- 如果不在一个事务中的两次sql执行，会创建新的sqlSession,而使缓存失效
- 在一个事务中的两次sql执行，会共用一个sqlSession,第二次sql会直接从缓存获取数据
###### 事务的一些影响（statement级别）
- 如果不在一个事务中的两次sql执行，会创建新的sqlSession,而且两次statement不一样，而使缓存失效
- 在一个事务中的两次sql执行，会共用一个sqlSession,但两次statement不一样，，而使缓存失效
- 这也就是STATEMENT级别的一级缓存无法共享localCache的原因
### 二级缓存
- SqlSessionFactory级别的缓存，实现不同会话中数据的共享，是一个全局变量，默认是没有开启的，存储作用域为Mapper的namespace级别
```
开启步骤：
application.yml配置如下
mybatis:
  configuration:
    # 开启mybatis的二级缓存
    cache-enabled: true
mapper.xml配置如下
# 在Mapper.xml中，配置二级缓存（也支持在接口配置）
# 在标签<mapper>下面添加<cache/>标签即可
```
- mybatis的二级缓存是属于序列化，序列化的意思就是从内存中的数据传到硬盘中，这个过程就是序列化
- 第一次调用mapper下的SQL去查询用户的信息，查询到的信息会存放代该mapper对应的二级缓存区域。
- 第二次调用namespace下的mapper映射文件中，相同的sql去查询用户信息，会去对应的二级缓存内取结果
- 如果调用相同namespace下的mapepr映射文件中增删改sql，并执行了commit操作，会使该mapper对应的缓存失效
- 当关闭了SqlSession之后，才会将查询数据保存到二级缓存中（SqlSessionFactory）中，所以才有缓存命中率的说法
- MyBatis的二级缓存不适应用于映射文件中存在多表查询的情况。通常我们会为每个单表创建单独的映射文件，由于MyBatis的二级缓存是基于namespace的，多表查询语句所在的namespace无法感应到其他namespace中的语句对多表查询中涉及的表进行的修改，引发脏数据问题。
- 为了解决以上的问题，可以使用Cache-ref，让AMapper引用BMapper命名空间，这样两个映射文件对应的Sql操作都使用的是同一块缓存了
- mybatis二级缓存可以继承第三方，如redis，Redis比之一、二级缓存的好处很多，Redis可以搭建在其他服务器上，缓存容量可扩展。Redis可以灵活的使用在需要缓存的数据上，比如一些热点数据
- 当集成redis时，redisTemplate已将序列化和反序列化封装好，无需自己操作

[集成Redis参考](https://segmentfault.com/a/1190000012404111)

[缓存机制参考(包含其他第三方集成)](https://zhuanlan.zhihu.com/p/60126041)

[缓存原理参考](http://tech.dianwoda.com/2018/12/25/mybatis-huan-cun-yuan-li/?utm_source=tuicool&utm_medium=referral)

[缓存实验参考](https://blog.csdn.net/zb313982521/article/details/79689169)