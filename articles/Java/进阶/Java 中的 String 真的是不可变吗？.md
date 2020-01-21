我们都知道 Java 中的 String 类的设计是不可变的，来看下 String 类的源码。

```
public final class String
    implements java.io.Serializable, Comparable<String>, CharSequence {
    
    /** The value is used for character storage. */
    private final char value[];
    
    /** Cache the hash code for the string */
    private int hash; // Default to 0
    
    // ...
    
}
```

可以看出 String 类是 final 类型的，String 不能被继承。其值 value 也就是对字符数组的封装，即 char[]，其值被定义成 private final 的，说明不能通过外界修改，即不可变。

### String 真的 "不可变 " 吗？

来看下面这个例子。

```
String str = "Python";
System.out.println(str); // Python

str = "Java";
System.out.println(str); // Java

str = str.substring(1);
System.out.println(str); // ava
```

**你有可能会问：str 不是由 Python 变成 Java 了吗？然后通过 substring 方法变成 ava 了吗？**

这其实是初学者的一个误区，从上面看 String 的结构可以得知字符串是由字符数组构成的，str 只是一个引用而已，第一次引用了 "Python"，后面变成了 "Java"，而 substring 也是用 Arrays.copyOfRange 方法重新复制字符数组构造了一个新的字符串。

![](http://img.javastack.cn/18-9-12/688492.jpg)

所以说，这里的字符串并不是可变，只是变更了字符串引用。

关于 substring 在 JDK 各个版本的差异可以看这篇文章《[注意：字符串substring方法在jkd6,7,8中的差异](https://mp.weixin.qq.com/s/3KrBept61jDAtheR27gFJA)》，也可以去看 substring 的各个版本的源码。

### String 真的真的真的 "不可变 " 吗？

上面的例子肯定是不可变的，下面这个就尴尬了。

```
String str = "Hello Python";
System.out.println(str); // Hello Python

Field field = String.class.getDeclaredField("value");
field.setAccessible(true);

char[] value = (char[])field.get(str);
value[6] = 'J';
value[7] = 'a';
value[8] = 'v';
value[9] = 'a';
value[10] = '!';
value[11] = '!';
System.out.println(str); // Hello Java!!
```

通过反射，我们改变了底层的字符数组的值，实现了字符串的 “不可变” 性，这是一种骚操作，不建议这么使用，违反了 Java 对 String 类的不可变设计原则，会造成一些安全问题。

是不是又涨姿势了？分享给你的朋友们吧！

