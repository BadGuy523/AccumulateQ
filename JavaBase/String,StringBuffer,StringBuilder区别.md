### 各自特点
1. String
- String是对象而不是基本类型，重写了equals方法和hashCode方法
- String底层是一个final类型的字符数组，所以它的值是不可变的，每次对String的操作都会产生新的String对象，造成内存浪费
2. StringBuffer
- 是字符串可变对象，可以对字符串进行操作，修改时不会新建一个对象
- 执行效率较慢，但是线程安全
- 没有重写equals方法和hashCode方法
3. StringBuilder
- 也是字符串可变对象，和StringBuffer一样
- 执行效率较高，但是线程不安全
- 没有重写equals方法和hashCode方法
### 字符串拼接问题
- String定义的字符串保存在常量池中，进行+操作时不能直接在原有基础上拼接，每次+操作会隐式的在堆上new一个和原字符串相同的StringBuilder对象，再调用append方法进行拼接
```String s1 = "a";s1 = s1 + "b";```等同于```StringBuilder s2 = new StringBuilder("a");s2.append("b");```
- 所以在进行大量字符串拼接时，如果使用String，就会频繁的创建StringBuilder对象，造成对内存的浪费，所以需要使用StringBuilder对象
### 线程安全问题