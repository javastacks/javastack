
Java 13 都快要来了，12必须跟栈长学起！

[Java 13 即将发布，新特性必须抢先看！](https://mp.weixin.qq.com/s/Gg6KKz7vhDRpzeMR8CG4DA)

Java 12 中对 switch 的语法更友好了，建议大家看下栈长在Java技术栈微信公众号分享的《[switch case 支持的 6 种数据类型！](https://mp.weixin.qq.com/s/QuchavZfEexwAgUS5qgB_Q)》，对理解 switch 更有帮助。

**那么在 Java 12 中可以怎样玩 switch?**

先来定义一个枚举类：

```
public enum Status {
    OPEN, INIT, PROCESS, PENDING, CLOSE;
}
```

Java 12 之前是这样用的：

```
private static void testSwitch1(Status status) {
    int result = 0;
    switch (status) {
        case OPEN:
            result = 1;
            break;
        case PROCESS:
            result = 2;
            break;
        case PENDING:
            result = 2;
            break;
        case CLOSE:
            result = 3;
            break;
        default:
            throw new RuntimeException("状态不正确");
    }
    System.out.println("result is " + result);
}
```

Java 12 后可以这样用：

```
private static void testSwitch2(Status status) {
    var result = switch (status) {
        case OPEN -> 1;
        case PROCESS, PENDING -> 2;
        case CLOSE -> 3;
        default -> throw new RuntimeException("状态不正确");
    };
    System.out.println("result is " + result);
}
```

示例代码看了都懂吧，是不是很骚？

Java 12 switch 有以下几点特色：

- 箭头语法 ->，类似 Java 8 中的 Lambda 表达式；
- 可以直接返回值给一个变量，并且可以不用 break 关键字；
- case 条件，多个可以写在一行，用逗号分开；
- 可以省略 break 关键字；

当然你也可以使用 break 关键字，后面跟值：

```
private static void testSwitch3(Status status) {
    var result = switch (status) {
        case OPEN -> {
            break 1;
        }
        case PROCESS, PENDING -> {
            break 2;
        }
        case CLOSE -> {
            break 3;
        }
        default -> {
            break 5;
        }
    };
    System.out.println("result is " + result);
}
```

不推荐用这种，在编译器也会显示灰色，并提示你更改。

最后，这个新特性是 Java 12 预览版中的特性：`JEP 325: Switch Expressions (Preview)`，要使用这个新特性，请切换至 Java 12 预览版。

> 参考 https://openjdk.java.net/jeps/325

好了，今天的分享就到这，收藏转发一下吧，多学习了解，日后必定有用！

历史 Java 新特性干货分享：

![](http://img.javastack.cn/20190613135450.png)
![](http://img.javastack.cn/20190613135537.png)

获取上面这份 Java 8~12 系列新特性干货文章，请在微信搜索关注微信公众号：Java技术栈，在公众号后台回复：java。

本文原创首发于微信公众号：Java技术栈（id:javastack），转载请原样保留本信息。