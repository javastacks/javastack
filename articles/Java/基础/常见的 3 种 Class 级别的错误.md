
## ClassNotFoundException

很明显，这个错误是`找不到类异常`，即在当前classpath路径下找不到这个类。

ClassNotFoundException继承了Exception，是必须捕获的异常，所以这个异常一般发生在显示加载类的时候，如下面两种方式显示来加载类并要捕获异常。

```
public static void main(String[] args) {
    try {
        Class.forName("com.User");
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    }

    try {
        Test.class.getClassLoader().loadClass("com.User");
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    }

}
```
当无法找到对应的类时都会抛出ClassNotFoundException异常。

```
java.lang.ClassNotFoundException: com.User
	at java.net.URLClassLoader.findClass(URLClassLoader.java:381)
	at java.lang.ClassLoader.loadClass(ClassLoader.java:424)
	at sun.misc.Launcher$AppClassLoader.loadClass(Launcher.java:331)
	at java.lang.ClassLoader.loadClass(ClassLoader.java:357)
	at com.test.user.Test.main(Test.java:15)
```

## NoClassDefFoundError

这是虚拟机隐式加载类出现的异常。

这个异常继承了Error类，一般发生在引用的类不存在，即类、方法或者属性引用了某个类或者接口，如果目标引用不存在就会抛出这个异常。

```
import org.jdom2.input.DOMBuilder;
public class MyDomBuilder extends DOMBuilder{

}
```
```
public static void main(String[] args) {
    MyDomBuilder builder = new MyDomBuilder();
}
```
MyDomBuilder继承了DOMBuilder，如果把DOMBuilder所属的jar包范围设置为provided，即运行时找不到DOMBuilder类就会报错。
```
Exception in thread "main" java.lang.NoClassDefFoundError: org/jdom2/input/DOMBuilder
	at java.lang.ClassLoader.defineClass1(Native Method)
	at java.lang.ClassLoader.defineClass(ClassLoader.java:763)
	at java.security.SecureClassLoader.defineClass(SecureClassLoader.java:142)
	at java.net.URLClassLoader.defineClass(URLClassLoader.java:467)
	at java.net.URLClassLoader.access$100(URLClassLoader.java:73)
	at java.net.URLClassLoader$1.run(URLClassLoader.java:368)
	at java.net.URLClassLoader$1.run(URLClassLoader.java:362)
	at java.security.AccessController.doPrivileged(Native Method)
	at java.net.URLClassLoader.findClass(URLClassLoader.java:361)
	at java.lang.ClassLoader.loadClass(ClassLoader.java:424)
	at sun.misc.Launcher$AppClassLoader.loadClass(Launcher.java:331)
	at java.lang.ClassLoader.loadClass(ClassLoader.java:357)
	at com.test.user.Test.main(Test.java:8)
Caused by: java.lang.ClassNotFoundException: org.jdom2.input.DOMBuilder
	at java.net.URLClassLoader.findClass(URLClassLoader.java:381)
	at java.lang.ClassLoader.loadClass(ClassLoader.java:424)
	at sun.misc.Launcher$AppClassLoader.loadClass(Launcher.java:331)
	at java.lang.ClassLoader.loadClass(ClassLoader.java:357)
	... 13 more
```
先报出ClassNotFoundException，然后引发NoClassDefFoundError。

所以，以上两个错误都要确保加载类或者引用类都要在classpath路径下。

## ClassCastException

类转换异常，这个错误一般发生在一个对象强制转换类型的时候，如将一个String强制转换成Integer就会报这个错。

这个异常继承了运行时异常RuntimeException，不需要捕获的异常。为了避免报这个错，在转换之间可以先用instanceof判断下是不是该类的引用再转换。如果是集合类型，最好指定集合里面的泛型。


```
public static void main(String[] args) {
    Object str = "123";
    Integer i = (Integer)str;
}
```

字符串"123"强制转换成Integer，然后报错。

```
Exception in thread "main" java.lang.ClassCastException: java.lang.String cannot be cast to java.lang.Integer
	at com.test.user.Test.main(Test.java:9)
```

