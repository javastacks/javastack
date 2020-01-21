在我们工作、学习、以及研究 JVM 过程当中，不可避免的要查看 Java 字节码，通过查看字节码可以了解一个类的编译结果，也能通过编译器层面来分析一个类的性能。

字节码文件是不能直接打开的，下面栈长教大家几种简单的方法如何查看 Java 字节码。

#### 1、使用 javap 命令查看字节码

命令格式如下：

> javap -c xxx.class

JDK 提供的了，不解释，来，演示操作一把：

```
$ javap -c Test.class
Compiled from "Test.java"
public class com.test.Test {
  public com.test.Test();
    Code:
       0: aload_0
       1: invokespecial #8                  // Method java/lang/Object."<init>":()V
       4: return

  public static void main(java.lang.String[]);
    Code:
       0: return
}
```

如上所示，可以看到 Test 类的的字节码。

#### 2、Intellij IDEA 中查看字节码

Intellij IDEA 直接集成了一个工具菜单，可以直接查看字节码，打开 `ByteCode` 插件窗口方法如下：

> View -> Show bytecode

![](http://img.javastack.cn/20191205114156.png)
![](http://img.javastack.cn/20191205114048.png)

如图所示，可以看到 String 类的的字节码。

是不是很方便？

如果看不到这个菜单，那可能你当前的类没有编译，需要你编译一下。

不会用 Intellij IDEA 的可以关注公众号：Java技术栈，在后台回复：IDEA，可以获取栈长整理的历史教程，都是无废话干货。

想当初从 Eclipse 转 IDEA 数次都失败，现在早习惯了，说实话写代码是真智能，基友搭配，效率翻倍。

#### 3、Eclipse 中查看字节码

在 Eclipse 中查看字节码稍显麻烦，需要安装插件，并打开插件窗口，安装和使用方式如下：

![](http://img.javastack.cn/20191205134348.png)

**Name：** bytecode

**Location：** http://andrei.gmxhome.de/eclipse

安装完后需要重启 Eclipse 才能生效。

Eclipse 打开 `ByteCode` 插件窗口：

> Window-> Show View -> Other -> Java -> Bytecode 

![](http://img.javastack.cn/20191205134934.png)

如图所示，可以看到 String 类的的字节码。

当然，市面上还有很多其他的小工具可以查看字节码，这里就不再一一介绍了，有兴趣的可以去了解下，栈长介绍的这三种方式应该够用了吧。

看完有没有涨姿势呢？

老铁，可以拿出去装逼了，在看、转发一波！

