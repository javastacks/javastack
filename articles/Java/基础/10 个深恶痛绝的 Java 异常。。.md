异常是 Java 程序中经常遇到的问题，我想每一个 Java 程序员都讨厌异常，一 个异常就是一个 BUG，就要花很多时间来定位异常问题。

> 什么是异常及异常的分类请看这篇文章：[一张图搞清楚 Java 异常机制](https://mp.weixin.qq.com/s/xbopgxZ5BEDdSvwO9ad9Xg)。

今天，栈长来列一下 Java 中经常遇到的前 10 个异常，排名不分先后。

## 1、NullPointerException

空指针异常，操作一个 null 对象的方法或属性时会抛出这个异常。具体看这篇文章：[Java 避免空指针的 5 个案例](https://mp.weixin.qq.com/s/cmkKuhEZl1qx6TXPxvA5pw)。

## 2、OutOfMemoryError

内存异常异常，这不是程序能控制的，是指要分配的对象的内存超出了当前最大的堆内存，需要调整堆内存大小（-Xmx）以及优化程序。

## 3、IOException

IO，即：input, output，我们在读写磁盘文件、网络内容的时候经常会生的一种异常，这种异常是受检查异常，需要进行手工捕获。

如文件读写会抛出 IOException：

```
public int read() throws IOException
public void write(int b) throws IOException
```

## 4、FileNotFoundException

文件找不到异常，如果文件不存在就会抛出这种异常。

如定义输入输出文件流，文件不存在会报错：

```
public FileInputStream(File file) throws FileNotFoundException
public FileOutputStream(File file) throws FileNotFoundException
```

FileNotFoundException 其实是 IOException 的子类，同样是受检查异常，需要进行手工捕获。

## 5、ClassNotFoundException

类找不到异常，Java开发中经常遇到，是不是很绝望？这是在加载类的时候抛出来的，即在类路径下不能加载指定的类。

看一个示例：

```
public static <T> Class<T> getExistingClass(ClassLoader classLoader, String className) {
  try {
     return (Class<T>) Class.forName(className, true, classLoader);
  }
  catch (ClassNotFoundException e) {
     return null;
  }
}
```

它是受检查异常，需要进行手工捕获。

## 6、ClassCastException

类转换异常，将一个不是该类的实例转换成这个类就会抛出这个异常。

如将一个数字强制转换成字符串就会报这个异常：

```
Object x = new Integer(0);
System.out.println((String)x);
```

这是运行时异常，不需要手工捕获。

## 7、NoSuchMethodException

没有这个方法异常，一般发生在反射调用方法的时候，如：

```
public Method getMethod(String name, Class<?>... parameterTypes)
    throws NoSuchMethodException, SecurityException {
    checkMemberAccess(Member.PUBLIC, Reflection.getCallerClass(), true);
    Method method = getMethod0(name, parameterTypes, true);
    if (method == null) {
        throw new NoSuchMethodException(getName() + "." + name + argumentTypesToString(parameterTypes));
    }
    return method;
}
```

它是受检查异常，需要进行手工捕获。

## 8、IndexOutOfBoundsException

索引越界异常，当操作一个字符串或者数组的时候经常遇到的异常。

![](http://qianniu.javastack.cn/18-12-12/80818264.jpg)

如图所示，它是运行时异常，不需要手工捕获。

## 9、ArithmeticException

算术异常，发生在数字的算术运算时的异常，如一个数字除以 0 就会报这个错。

```
double n = 3 / 0;
```

这个异常虽然是运行时异常，可以手工捕获抛出自定义的异常，如：

```
public static Timestamp from(Instant instant) {
    try {
        Timestamp stamp = new Timestamp(instant.getEpochSecond() * MILLIS_PER_SECOND);
        stamp.nanos = instant.getNano();
        return stamp;
    } catch (ArithmeticException ex) {
        throw new IllegalArgumentException(ex);
    }
}
```

## 10、SQLException

SQL异常，发生在操作数据库时的异常。

如下面的获取连接：

```
public Connection getConnection() throws SQLException {
    if (getUser() == null) {
        return DriverManager.getConnection(url);
    } else {
        return DriverManager.getConnection(url, getUser(), getPassword());
    }
}
```

又或者是获取下一条记录的时候：

```
boolean next() throws SQLException;
```

它是受检查异常，需要进行手工捕获。

栈长这里只列举了 10 个 Java 中最常见的基本异常，另外，栈长已经整理了 Java 系列核心知识点文章，关注Java技术栈微信公众号，在后台回复关键字：java，即可获取。

话说你遇到的最多的是哪个呢？欢迎留言分享。

> 本文原创首发于微信公众号：Java技术栈（id:javastack），关注公众号在后台回复 "java" 可获取更多，转载请原样保留本信息。
