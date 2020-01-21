我们都知道 `switch` 用来走流程分支，大多情况下用来匹配单个值，如下面的例子所示：

```
/**
 * @from 微信公众号：Java技术栈
 * @author 栈长
 */
private static void test(int value) {
    switch (value) {
        case 1:
            System.out.println("1");
            break;
        case 2:
            System.out.println("1");
            break;
        case 3:
            System.out.println("1");
            break;
        case 4:
            System.out.println("1");
            break;
        case 5:
            System.out.println("1");
            break;
        case 6:
            System.out.println("0");
            break;
        case 7:
            System.out.println("0");
            break;
        default:
            System.out.println("-1");
    }
}
```

相关阅读：[switch case 支持的 6 种数据类型](https://mp.weixin.qq.com/s/QuchavZfEexwAgUS5qgB_Q)。

大概的意思就是，周一到周五输出：1，周六到周日输出：0，默认输出-1。

这样写，很多重复的逻辑，冗余了。

也许这个例子不是很合适，用 if/ else 更恰当，但这只是个例子，实际开发中肯定会有某几个 case 匹配同一段逻辑的情况。

那么，如何让多个 case 匹配同一段逻辑呢？

如下面例子所示：

```
/**
 * @from 微信公众号：Java技术栈
 * @author 栈长
 */
private static void test(int value) {
    switch (value) {
        case 1: case 2: case 3: case 4: case 5:
            System.out.println("1");
            break;
        case 6: case 7:
            System.out.println("0");
            break;
        default:
            System.out.println("-1");
    }
}
```

把相同逻辑的 case 放一起，最后一个 case 写逻辑就行了。

格式化后就是这样了：

```
/**
 * @from 微信公众号：Java技术栈
 * @author 栈长
 */
private static void test(int value) {
    switch (value) {
        case 1: 
        case 2: 
        case 3: 
        case 4: 
        case 5:
            System.out.println("1");
            break;
        case 6: 
        case 7:
            System.out.println("0");
            break;
        default:
            System.out.println("-1");
    }
}
```

是不是很骚？

其实这不是最合适的最好的写法，在 Java 12 中还可以更骚。

在 Java 12 中可以用逗号来分开多个值，还能用 `lambda` 表达式，甚至还能省略 break，使用 `switch` 更方便，具体看这篇文章：[Java 12 骚操作， switch居然还能这样玩](https://mp.weixin.qq.com/s/EY-2gqbbynshCshRlM3Qsw)，或者关注微信公众号：Java技术栈，在后台回复 "新特性" 获取这篇文章。

