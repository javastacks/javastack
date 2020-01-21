美国时间 09 月 25 日，Oralce 正式发布了 Java 11，这是据 Java 8 以后支持的首个长期版本。

为什么说是长期版本，看下面的官方发布的支持路线图表。

![](http://qianniu.javastack.cn/18-9-26/91229065.jpg)

可以看出 Java 8 扩展支持到 2025 年，而 Java 11 扩展支持到 2026 年。

现在大部分都在用 Java 8，Java 9 和 10 目前很少有人在用，至少我没有发现有公司在生产环境应用的，那就是找死。

现在 Java 11 长期支持，也已经包含了 9 和 10 的全部功能，9 和 10 自然就活到头了。。

那么我们来看下 从 Java 9 - 11 都有哪些重要的新特性呢？

#### 1、本地变量类型推断

这个博主已经写过一篇文章，详细的介绍了 Java 10 带来的这个新特性。

什么是局部变量类型推断？

```
var javastack = "javastack";
System.out.println(javastack);
```

大家看出来了，局部变量类型推断就是左边的类型直接使用 `var` 定义，而不用写具体的类型，编译器能根据右边的表达式自动推断类型，如上面的 `String` 。

```
var javastack = "javastack";
```

就等于：

```
String javastack = "javastack";
```

更多使用详情请参考这篇文章《[Java 10 实战第 1 篇：局部变量类型推断](https://mp.weixin.qq.com/s/4zUtQPUn5LYw43IRLm0Dwg)》，这里不再重复了。


#### 2、字符串加强

Java 11 增加了一系列的字符串处理方法，如以下所示。

```
// 判断字符串是否为空白
" ".isBlank();                // true

// 去除首尾空格
" Javastack ".strip();          // "Javastack"

// 去除尾部空格 
" Javastack ".stripTrailing();  // " Javastack"

// 去除首部空格 
" Javastack ".stripLeading();   // "Javastack "

// 复制字符串
"Java".repeat(3);             // "JavaJavaJava"

// 行数统计
"A\nB\nC".lines().count();    // 3
```

#### 3、集合加强

自 Java 9 开始，Jdk 里面为集合（List/ Set/ Map）都添加了 `of` 和 `copyOf` 方法，它们两个都用来创建不可变的集合，来看下它们的使用和区别。

**示例1：**

```
var list = List.of("Java", "Python", "C");
var copy = List.copyOf(list);
System.out.println(list == copy);   // true
```

**示例2：**

```
var list = new ArrayList<String>();
var copy = List.copyOf(list);
System.out.println(list == copy);   // false
```

示例1和2代码差不多，为什么一个为true,一个为false?

来看下它们的源码：

```
static <E> List<E> of(E... elements) {
    switch (elements.length) { // implicit null check of elements
        case 0:
            return ImmutableCollections.emptyList();
        case 1:
            return new ImmutableCollections.List12<>(elements[0]);
        case 2:
            return new ImmutableCollections.List12<>(elements[0], elements[1]);
        default:
            return new ImmutableCollections.ListN<>(elements);
    }
}

static <E> List<E> copyOf(Collection<? extends E> coll) {
    return ImmutableCollections.listCopy(coll);
}

static <E> List<E> listCopy(Collection<? extends E> coll) {
    if (coll instanceof AbstractImmutableList && coll.getClass() != SubList.class) {
        return (List<E>)coll;
    } else {
        return (List<E>)List.of(coll.toArray());
    }
}
```

可以看出 `copyOf` 方法会先判断来源集合是不是 `AbstractImmutableList` 类型的，如果是，就直接返回，如果不是，则调用 `of` 创建一个新的集合。

示例2因为用的 new 创建的集合，不属于不可变 `AbstractImmutableList` 类的子类，所以 `copyOf` 方法又创建了一个新的实例，所以为false.

> 注意：使用 of 和 copyOf 创建的集合为不可变集合，不能进行添加、删除、替换、排序等操作，不然会报 `java.lang.UnsupportedOperationException` 异常。

上面演示了 List 的 of 和 copyOf 方法，Set 和 Map 接口都有。

#### 4、Stream 加强

Stream 是 Java 8 中的新特性，Java 9 开始对 Stream 增加了以下 4 个新方法。

1) 增加单个参数构造方法，可为null

```
Stream.ofNullable(null).count(); // 0
```

2) 增加 takeWhile 和 dropWhile 方法

```
Stream.of(1, 2, 3, 2, 1)
    .takeWhile(n -> n < 3)
    .collect(Collectors.toList());  // [1, 2]
```

从开始计算，当 n < 3 时就截止。
    
```
Stream.of(1, 2, 3, 2, 1)
    .dropWhile(n -> n < 3)
    .collect(Collectors.toList());  // [3, 2, 1]
```

这个和上面的相反，一旦 n < 3 不成立就开始计算。

3）iterate重载

这个 iterate 方法的新重载方法，可以让你提供一个 Predicate (判断条件)来指定什么时候结束迭代。

如果你对 JDK 8 中的 Stream 还不熟悉，可以看之前分享的这一系列教程。

#### 5、Optional 加强

Opthonal 也增加了几个非常酷的方法，现在可以很方便的将一个 Optional 转换成一个 Stream, 或者当一个空 Optional 时给它一个替代的。

```
Optional.of("javastack").orElseThrow();     // javastack
Optional.of("javastack").stream().count();  // 1
Optional.ofNullable(null)
    .or(() -> Optional.of("javastack"))
    .get();   // javastack
```

#### 6、InputStream 加强

InputStream 终于有了一个非常有用的方法：transferTo，可以用来将数据直接传输到 OutputStream，这是在处理原始数据流时非常常见的一种用法，如下示例。

```
var classLoader = ClassLoader.getSystemClassLoader();
var inputStream = classLoader.getResourceAsStream("javastack.txt");
var javastack = File.createTempFile("javastack2", "txt");
try (var outputStream = new FileOutputStream(javastack)) {
    inputStream.transferTo(outputStream);
}
```

#### 7、HTTP Client API

这是 Java 9 开始引入的一个处理 HTTP 请求的的孵化 HTTP Client  API，该 API 支持同步和异步，而在 Java 11 中已经为正式可用状态，你可以在 `java.net` 包中找到这个 API。

来看一下 HTTP Client 的用法：

```
var request = HttpRequest.newBuilder()
    .uri(URI.create("https://javastack.cn"))
    .GET()
    .build();
var client = HttpClient.newHttpClient();

// 同步
HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
System.out.println(response.body());

// 异步
client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
    .thenApply(HttpResponse::body)
    .thenAccept(System.out::println);
```

上面的 `.GET()` 可以省略，默认请求方式为 Get！

更多使用示例可以看这个 API，后续有机会再做演示。

现在 Java 自带了这个 HTTP Client API，我们以后还有必要用 Apache 的 HttpClient 工具包吗？

#### 8、化繁为简，一个命令编译运行源代码

看下面的代码。

```
// 编译
javac Javastack.java

// 运行
java Javastack
```

在我们的认知里面，要运行一个 Java 源代码必须先编译，再运行，两步执行动作。而在未来的 Java 11 版本中，通过一个 `java` 命令就直接搞定了，如以下所示。

```
java Javastack.java
```

#### 更多新特性

- Flow API for reactive programming
- Java Module System
- Application Class Data Sharing
- Dynamic Class-File Constants
- Java REPL (JShell)
- Flight Recorder
- Unicode 10
- G1: Full Parallel Garbage Collector
- ZGC: Scalable Low-Latency Garbage Collector
- Epsilon: No-Op Garbage Collector
- Deprecate the Nashorn JavaScript Engine
- ...

#### 历史新特性文章

- Java 11 要来了，编译运行一个命令搞定
- JDK 11 发布计划来了，已确定 3个 新特性
- Java 10 实战第 1 篇：局部变量类型推断
- JDK 5 ~ 10 新特性倾情整理
- JDK 10 的 10 个新特性
- JDK 10 最重要的 5 个新特性
- JDK 9 的 9 个新特性
- JDK 9 新特性实战：简化流关闭新姿势
- JDK 8 的排序大法
- JDK 8 新特性之 Lambda 表达式
- JDK 8 新特性之函数式接口
- JDK 8 新特性之方法引用
- JDK 8 新特性之接口默认方法与静态方法
- JDK 8 新特性之 Optional
- JDK 8 新特性之重复注解
- JDK 8 新特性之 Stream 流
- JDK 8 新特性之 Stream 流（一）基础体验
- JDK 8 新特性之 Stream 流（二）关键知识点
- JDK 8 新特性之 Stream 流（三）缩减操作
- JDK 8 新特性之 Stream 流（四）并行流
- JDK 8 新特性之 Stream 流（五）映射
- JDK 8 新特性之 Stream 流（六）收集缩
- JDK 8 新特性之 Stream 流（七）流与迭代器
- JDK 8 新特性之扩展篇

更多新特性、新玩法，可以在 "Java技术栈" 微信公众号后台回复关键字：java，获取以上所有新特性文章。

#### 结束语

现在许多人还在使用 Java 8 或者 7，不过 8 在 2019 年初就会结束免费更新。现在 11 是长期支持版本，正是学习和上手 11 的好时机，写这篇文章希望能对你有所启发。

如果你喜欢的我的文章，对你有帮助，点赞转发支持一下吧~

