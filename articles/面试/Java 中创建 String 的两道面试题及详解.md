我们知道创建一个String类型的变量一般有以下两种方法：

```
String str1 = "abcd";
String str2 = new String("abcd");
```

那么为什么会存在这两种创建方式呢，它们在内存中的表现形式各有什么区别？

下面来看下面两道经常遇到的面试题。

##### 面试题1：

```
String a = "abcd";
String b = "abcd";
System.out.println(a == b);  // true
System.out.println(a.equals(b)); // true
```

解析：

用""创建的a,b两个字符串，==和equals比较返回都为true，这是因为a,b都指向了方法区的同一个字符串。所以，当同样的一个字符串用""重复创建时只在方法区创建一次。

##### 面试题2：

```
String c = new String("abcd");
String d = new String("abcd");
System.out.println(c == d);  // false
System.out.println(c.equals(d)); // true
```

解析：

用new创建的c,d两个字符串，equals为true很简单因为equals永远比较的是值，而==为false说明两个字符串的引用不一样。用new创建的字符串每次都会在JVM堆中创建，所以c,d都对应堆中的两个不同的字符串。

关于这两道题中的""和new创建的字符串在内存中的表现形式可以看下图就明白了。

![image](https://www.programcreek.com/wp-content/uploads/2014/03/constructor-vs-double-quotes-Java-String-New-Page-650x324.png)