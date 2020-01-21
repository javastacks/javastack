
## 什么是Kotlin

Kotlin是一个基于JVM的新的编程语言，2010年由IntelliJ IDEA所在的JetBrains公司开发，自2012年以来一直开源。

Kotlin可以编译成Java字节码，也可以编译成JavaScript，方便在没有JVM的设备上运行。

Kotlin是面向对象和功能编程功能的JVM和Android的通用、开源、静态的实用的编程语言。它专注于交互性、安全性及清晰度和工具的支持。


## 设计理念

1、创建一种兼容Java的语言

2、让它比Java更安全，能够静态检测常见的陷阱。如：引用空指针

3、让它比Java更简洁，通过支持variable type inference，higher-order functions (closures)，extension functions，mixins and first-class delegation等实现。

4、让它比最成熟的竞争对手Scala语言更加简单。

## Kotlin优势

1、简洁: 大大减少样板代码的数量。

2、安全: 避免空指针异常等整个类的错误。

3、互操作性: 充分利用 JVM、Android 和浏览器的现有库。

4、工具友好: 可用任何 Java IDE 或者使用命令行构建。

## 代码转换

Java代码：

```
public class JavaCode {
    public String toJSON(Collection<Integer> collection) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Iterator<Integer> iterator = collection.iterator();
        while (iterator.hasNext()) {
            Integer element = iterator.next();
            sb.append(element);
            if (iterator.hasNext()) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
```

转换成Kotlin代码：

```
fun toJSON(collection: Collection<Int>): String {
    val sb = StringBuilder()
    sb.append("[")
    val iterator = collection.iterator()
    while (iterator.hasNext())
    {
        val element = iterator.next()
        sb.append(element)
        if (iterator.hasNext())
        {
            sb.append(", ")
        }
    }
    sb.append("]")
    return sb.toString()
}
```

看起来，Kotlin就像是一个简化的Java版本。Java和Kotlin两个语言可以互相转换。

## 未来发展

在Google I/O 2017中，Google 宣布 Kotlin 成为 Android 官方开发语言。

需要注意的是目前在国内用的比较少，Kotlin也是比较赶潮流的语言，在开发语言中排名和影响力也比较落后，从Java转到Kotlin也非常容易，所有，有兴趣的同学可以研究试玩下。