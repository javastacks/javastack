
![](http://img.javastack.cn/18-6-5/61544442.jpg)

前阵子，我们分享了《[Java中的基本数据类型转换](https://mp.weixin.qq.com/s/dZDatrORXPTkA4HNp66mnQ)》这篇文章，对许多粉丝还是有带来帮助的，今天讲一下 Java 包装类的的由来，及自动装箱、拆箱的概念和原理。

#### 什么是包装类型

Java 设计当初就提供了 8 种 基本数据类型及对应的 8 种包装数据类型。我们知道 Java 是一种面向对象编程的高级语言，所以包装类型正是为了解决基本数据类型无法面向对象编程所提供的。

**下面是基本数据类型与对应的包装类型。**

基本数据类型 | 包装类型
---|---
byte | Byte
boolean | Boolean
short | Short
char | Character
int | Integer
long | Long
float | Float
double | Double

**下面是包装类型的继承结构图。**

![](http://img.javastack.cn/18-6-5/80489463.jpg)

从以上图表可以对基本类型和包装类型有一个全面的了解。

#### 包装类应用场景

**1、集合类泛型只能是包装类；**

```
// 编译报错
List<int> list1 = new ArrayList<>();

// 正常
List<Integer> list2 = new ArrayList<>();
```

**2、成员变量不能有默认值；**

```
private int status;
```

基本数据类型的成员变量都有默认值，如以上代码 status 默认值为 0，如果定义中 0 代表失败，那样就会有问题，这样只能使用包装类 Integer，它的默认值为 null,所以就不会有默认值影响。

**3、方法参数允许定义空值；**

```
private static void test1(int status){
	System.out.println(status);
}
```

看以上代码，方法参数定义的是基本数据类型 int，所以必须得传一个数字过来，不能传 null，很多场合我们希望是能传递 null 的，所以这种场合用包装类比较合适。

还有更多应用场景就不一一例举了，欢迎留言共同探讨包装类的更多的应用场景。

#### 自动装箱、拆箱

Java 5 增加了自动装箱、拆箱机制，提供基本数据类型和包装类型的相互转换操作。

**自动装箱**

自动装箱即自动将基本数据类型转换成包装类型，在 Java 5 之前，要将基本数据类型转换成包装类型只能这样做，看下面的代码。

```
Integer i1 = new Integer(8);

Integer i2 = Integer.valueOf(8);

// 自动装箱
Integer i3 = 8;
```

以上 3 种都可以进行转换，但在 Java 5 之前第 3 种方法是编译失败的，第 3 种方法也正是现在的自动装箱功能。另外，第一种构造器方法也不推荐使用了，已经标为废弃了。

其实自动装箱的原理就是调用包装类的 valueOf 方法，如第 2 个方法中的 Integer.valueOf 方法。

**自动拆箱**

自动拆箱即自动将包装类型转换成基本数据类型，与自动装箱相反，有装就有拆，很好理解。

```
// 自动拆箱
int i4 = i3;

int i5 = i3.intValue();
```

继续上面的例子，把 i3 赋值给 i4 就是实现的自动拆箱功能，自动装箱的原理就是调用包装类的 xxValue 方法，如 i5 中的 Integer 的 intValue 方法。

自动装箱、拆箱不只是体现在以上的例子，在方法接收参数、对象设置参数时都能自动装箱拆箱。

**需要注意的是，关于 Integer，-128 ~ 127 会有缓存，对比这个范围的值的对象是一个坑，这个在阿里巴巴规范中也有提及。** 详细请参考《[IntegerCache的妙用和陷阱](https://mp.weixin.qq.com/s/PnVkrMzYeOiepPKjl4MKVA)》这篇文章。

