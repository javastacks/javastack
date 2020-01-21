群友在微信群讨论的一个话题，有点意思，特拿出来分享一下。

首先来看下面这段程序，和群友分享的大致一样。

```
public static void main(String[] args) {
    String hw = "hello world";
    
    String hello = "hello";
    final String finalWorld2 = "hello";
    final String finalWorld3 = hello;
    final String finalWorld4 = "he" + "llo";
    
    String hw1 = hello + " world";
    String hw2 = finalWorld2 + " world";
    String hw3 = finalWorld3 + " world";
    String hw4 = finalWorld4 + " world";
    
    System.out.println(hw == hw1);
    System.out.println(hw == hw2);
    System.out.println(hw == hw3);
    System.out.println(hw == hw4);
}
```

程序输出：

```
false
true
false
true
```

同样是字符串"hello"，为什么用final定义的，且个进行==操作却是true有一个是false，而没用final的却是false?

**首先来理解下宏变量：**

Java中，一个用final定义的变量，不管它是类型的变量，只要用final定义了并同时指定了初始值，并且这个初始值是在编译时就被确定下来的，那么这个final变量就是一个宏变量。编译器会把程序所有用到该变量的地方直接替换成该变量的值，也就是说编译器能对宏变量进行宏替换。

如：

```
final String a = "hello";
final String b = a;
final String c = getHello();
```

a在编译期间就能确定下来，而b、c不行，所以a是宏变量，b、c不是。

所以，再回到上面的程序，finalWorld2和finalWorld4是final定义的，也是在编译期间能确定下来的，所以它能被宏替换，编译器就会让finalWorld2和finalWorld4指向字符串池中缓存的字符串""hello world"，所以它们就是同一个对象。

