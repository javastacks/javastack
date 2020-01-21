> 来源：http://blog.csdn.net/danchu/article/details/54986442

![image](http://img.javastack.cn/18-1-23/39717542.jpg)

语法糖（Syntactic Sugar），也称糖衣语法，指在计算机语言中添加的某种语法，这种语法对语言本身功能来说没有什么影响，只是为了方便程序员的开发，提高开发效率。说白了，语法糖就是对现有语法的一个封装。

Java作为一种与平台无关的高级语言，当然也含有语法糖，这些语法糖并不被虚拟机所支持，在编译成字节码阶段就自动转换成简单常用语法。

**一般来说Java中的语法糖主要有以下几种：** 

1. 泛型与类型擦除 
2. 自动装箱与拆箱
3. 变长参数
4. 增强for循环 
5. 内部类
6. 枚举类

**泛型与类型擦除**

Java语言并不是一开始就支持泛型的。在早期的JDK中，只能通过Object类是所有类型的父类和强制类型转换来实现泛型的功能。强制类型转换的缺点就是把编译期间的问题延迟到运行时，JVM并不能为我们提供编译期间的检查。

在JDK1.5中，Java语言引入了泛型机制。但是这种泛型机制是通过类型擦除来实现的，即Java中的泛型只在程序源代码中有效（源代码阶段提供类型检查），在编译后的字节码中自动用强制类型转换进行替代。也就是说，Java语言中的泛型机制其实就是一颗语法糖，相较与C++、C#相比，其泛型实现实在是不那么优雅。


```
/**
* 在源代码中存在泛型
*/
public static void main(String[] args) {
    Map<String,String> map = new HashMap<String,String>();
    map.put("hello","你好");
    String hello = map.get("hello");
    System.out.println(hello);
}
```

当上述源代码被编译为class文件后，泛型被擦除且引入强制类型转换


```
public static void main(String[] args) {
    HashMap map = new HashMap(); //类型擦除
    map.put("hello", "你好");
    String hello = (String)map.get("hello");//强制转换
    System.out.println(hello);
}
```


**自动装箱与拆箱**

Java中的自动装箱与拆箱指的是基本数据类型与他们的包装类型之间的相互转换。

我们知道Java是一门面向对象的语言，在Java世界中有一句话是这么说的：“万物皆对象”。但是Java中的基本数据类型却不是对象，他们不需要进行new操作，也不能调用任何方法，这在使用的时候有诸多不便。因此Java为这些基本类型提供了包装类，并且为了使用方便，提供了自动装箱与拆箱功能。自动装箱与拆箱在使用的过程中，其实是一个语法糖，内部还是调用了相应的函数进行转换。

下面代码演示了自动装箱和拆箱功能


```
public static void main(String[] args) {
    Integer a = 1;
    int b = 2;
    int c = a + b;
    System.out.println(c);
}
```

经过编译后，代码如下


```
public static void main(String[] args) {
    Integer a = Integer.valueOf(1); // 自动装箱
    byte b = 2;
    int c = a.intValue() + b;//自动拆箱
    System.out.println(c);
}
```

**变长参数**

所谓变长参数，就是方法可以接受长度不定确定的参数。变长参数特性是在JDK1.5中引入的，使用变长参数有两个条件，一是变长的那一部分参数具有相同的类型，二是变长参数必须位于方法参数列表的最后面。变长参数同样是Java中的语法糖，其内部实现是Java数组。


```
public class Varargs {
    public static void print(String... args) {
        for(String str : args){
            System.out.println(str);
        }
    }

    public static void main(String[] args) {
        print("hello", "world");
    }
}
```

编译为class文件后如下，从中可以很明显的看出变长参数内部是通过数组实现的


```
public class Varargs {
    public Varargs() {
    }

    public static void print(String... args) {
        String[] var1 = args;
        int var2 = args.length;
        //增强for循环的数组实现方式
        for(int var3 = 0; var3 < var2; ++var3) {
            String str = var1[var3];
            System.out.println(str);
        }

    }

    public static void main(String[] args) {
        //变长参数转换为数组
        print(new String[]{"hello", "world"});
    }
}
```

**增强for循环**

增强for循环与普通for循环相比，功能更强并且代码更简洁。增强for循环的对象要么是一个数组，要么实现了Iterable接口。这个语法糖主要用来对数组或者集合进行遍历，其在循环过程中不能改变集合的大小。


```
public static void main(String[] args) {
    String[] params = new String[]{"hello","world"};
    //增强for循环对象为数组
    for(String str : params){
        System.out.println(str);
    }

    List<String> lists = Arrays.asList("hello","world");
    //增强for循环对象实现Iterable接口
    for(String str : lists){
        System.out.println(str);
    }
}
```

编译后的class文件为


```
public static void main(String[] args) {
   String[] params = new String[]{"hello", "world"};
   String[] lists = params;
   int var3 = params.length;
   //数组形式的增强for退化为普通for
   for(int str = 0; str < var3; ++str) {
       String str1 = lists[str];
       System.out.println(str1);
   }

   List var6 = Arrays.asList(new String[]{"hello", "world"});
   Iterator var7 = var6.iterator();
   //实现Iterable接口的增强for使用iterator接口进行遍历
   while(var7.hasNext()) {
       String var8 = (String)var7.next();
       System.out.println(var8);
   }

}
```

**内部类**

内部类就是定义在一个类内部的类
Java语言中之所以引入内部类，是因为有些时候一个类只在另一个类中有用，我们不想让其在另外一个地方被使用。内部类之所以是语法糖，是因为其只是一个编译时的概念，一旦编译完成，编译器就会为内部类生成一个单独的class文件，名为outer$innter.class。


```
public class Outer {
    class Inner{
    }
}
```

使用javac编译后，生成两个class文件Outer.class和Outer$Inner.class，其中Outer$Inner.class的内容如下：


```
class Outer$Inner {
    Outer$Inner(Outer var1) {
        this.this$0 = var1;
    }
}
```

内部类分为四种：成员内部类、局部内部类、匿名内部类、静态内部类，每一种都有其用法，这里就不介绍了

**枚举类型**

枚举类型就是一些具有相同特性的类常量
java中类的定义使用class，枚举类的定义使用enum。在Java的字节码结构中，其实并没有枚举类型，枚举类型只是一个语法糖，在编译完成后被编译成一个普通的类。这个类继承java.lang.Enum，并被final关键字修饰。


```
public enum Fruit {
    APPLE,ORINGE
}
```

使用jad对编译后的class文件进行反编译后得到：

```
//继承java.lang.Enum并声明为final
public final class Fruit extends Enum
{

    public static Fruit[] values()
    {
        return (Fruit[])$VALUES.clone();
    }

    public static Fruit valueOf(String s)
    {
        return (Fruit)Enum.valueOf(Fruit, s);
    }

    private Fruit(String s, int i)
    {
        super(s, i);
    }
    //枚举类型常量
    public static final Fruit APPLE;
    public static final Fruit ORANGE;
    private static final Fruit $VALUES[];//使用数组进行维护

    static
    {
        APPLE = new Fruit("APPLE", 0);
        ORANGE = new Fruit("ORANGE", 1);
        $VALUES = (new Fruit[] {
            APPLE, ORANGE
        });
    }
}
```

Java远不止这些语法糖，JDK7、JDK8添加的新特性都有很多高级的语法糖。
