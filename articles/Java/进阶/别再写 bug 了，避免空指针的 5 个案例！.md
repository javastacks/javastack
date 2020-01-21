空指针是我们 Java 开发人员经常遇到的一个基本异常，这是一个极其普遍但似乎又无法根治的问题。

本文，栈长将带你了解什么是空指针，还有如何有效的避免空指针。

## 什么是空指针？

当一个变量的值为 null 时，在 Java 里面表示一个不存在的空对象，没有实际内容，没有给它分配内存，null 也是对象成员变量的默认值。

所以，一个对象如果没有进行初始化操作，这时候，如果你调用这个对象的方法或者变量，就会出现空指针异常。

如下面示例会发生空指针异常：

```
Object object = null;
String string = object.toString();
```

![](http://qianniu.javastack.cn/18-12-12/46377586.jpg)

从类结构图来看，空指针它是属于运行时异常 `RuntimeException` 的子类，它不是捕获型的，只有在程序运行时才可能报出来，而且会造成程序中断。

> 什么是运行时异常及异常的分类请看这篇文章：[一张图搞清楚 Java 异常机制](https://mp.weixin.qq.com/s/xbopgxZ5BEDdSvwO9ad9Xg)。

## 如何避免空指针？

下面说几个空指针的几个最常见的案例及解决之道。

#### 1、字符串比较，常量放前面

```
if(status.equals(SUCCESS)){
    
}
```

这个时候 status 可能为 null 造成空指针异常，应该把常量放前面，就能避免空指针异常。

```
if(SUCCESS.equals(status)){
    
}
```

这个应该在各种开发规范里面都会提到，也是最基础的。

#### 2、初始化默认值

在对象初始化的时候给它一个默认值或者默认构造实现，如：

```
User user = new User();
String name = StringUtils.EMPTY;
```

#### 3、返回空集合

在返回一个集合的话，默认会是 null，统一规范返回一个空集合。

举个 List 例子，如：

```
public List getUserList(){
    List list = userMapper.gerUserList();
    return list == null ? new ArrayList() : list;
}
```

这样接收方就不用担心空指针异常了，也不会影响业务。

#### 4、断言

断言是用来检查程序的安全性的，在使用之前进行检查条件，如果不符合条件就报异常，符合就继续。

Java 中自带的断言关键字：assert，如：

```
assert name == null : "名称不能为空";
```

输出：

```
Exception in thread "main" java.lang.AssertionError: 名称不正确
```

不过默认是不启动断言检查的，需要要带上 JVM 参数：-enableassertions 才能生效。

Java 中这个用的很少，建议使用 Spring 中的，更强大，更方便好用。

Spring中的用法：

```
Assert.notNull(name,"名称不能为空");
```

#### 5、Optional

Optional 是 JDK 8 新增的新特性，再也不用 != null 来判断了，这个在一个对象里面的多个子对象连续判断的时候非常有用。

这里不再详细介绍了，具体看这篇文章：[JDK8新特性之Optional](https://mp.weixin.qq.com/s/uXw4eTZqLfj871FlciPh6Q)。

这里大概介绍了 5 种，其实还有更多，如何避免空指针，一是要注意代码编写规范，二是要提高代码素养。在Java技术栈微信公众号后台回复关键字：Java，可以获取更多栈长整理的 Java 系列技术干货。

大家都有什么高见，欢迎留言分享！

> 本文原创首发于微信公众号：Java技术栈（id:javastack），关注公众号在后台回复 "java" 可获取更多，转载请原样保留本信息。