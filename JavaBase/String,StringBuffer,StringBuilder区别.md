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
- demo代码
```
/**
 * @description: String, StringBuffer, StringBuilder测试
 * @author: BadGuy
 * @date: 2019-11-25 20:14
 **/
public class StringTest {

    public static void main(String[] args) throws InterruptedException {
        StringBuilder builder = new StringBuilder("");
        for (int i = 0;i <5; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 1000; j++) {
                        builder.append("a");
                    }
                    System.out.println(Thread.currentThread().getName() + "执行完毕！");
                }
            }).start();
        }
        Thread.sleep(100);
        System.out.println("字符串长度为："+builder.length());

        StringBuffer buffer = new StringBuffer("");
        for (int i = 0;i <5; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 1000; j++) {
                        buffer.append("a");
                    }
                    System.out.println(Thread.currentThread().getName() + "执行完毕！");
                }
            }).start();
        }
        Thread.sleep(100);
        System.out.println("字符串长度为："+buffer.length());
    }
```
- 执行结果
```
Thread-0执行完毕！
Thread-1执行完毕！
Thread-2执行完毕！
Thread-3执行完毕！
Thread-4执行完毕！
字符串长度为：4996
Thread-5执行完毕！
Thread-8执行完毕！
Thread-9执行完毕！
Thread-6执行完毕！
Thread-7执行完毕！
字符串长度为：5000
```
- 分析
1. 使用StringBuilder执行后的字符串长度不等于5000且每次执行后长度都不一定相同
2. 以下为StringBuilder的append方法源码
```
    @Override
    public StringBuilder append(String str) {
        super.append(str);
        return this;
    }
```
3. 以下为StringBuffer的append方法源码
```
    @Override
    public synchronized StringBuffer append(String str) {
        toStringCache = null;
        super.append(str);
        return this;
    }
```
4. StringBuffer和StringBuilder都继承于AbstractStringBuilder，以下为部分源码
```
abstract class AbstractStringBuilder implements Appendable, CharSequence {
    /**
     * The value is used for character storage.
     */
    char[] value;

    /**
     * The count is the number of characters used.
     */
    int count;
    
    public AbstractStringBuilder append(String str) {
        if (str == null)
            return appendNull();
        int len = str.length();
        ensureCapacityInternal(count + len);
        str.getChars(0, len, value, count);
        count += len;
        return this;
    }
}
```
5. 由此可以看出在AbstractStringBuilder的成员变量count在多线程中，使用StringBuilder不一定能获取到正确值，而StringBuffer的append方法是加了锁的，count作为共享资源，不会被多个线程同时使用，在某个线程使用时，其他线程会等待，所以最后count累加后的值才会是正确值，但是因此StringBuffer效率没有StringBuilder高

[参考网址](https://www.jianshu.com/p/3f6a46145fd9)