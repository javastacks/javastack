> 来源：http://www.cnblogs.com/xrq730/p/4865416.html

代码优化的最重要的作用应该是：避免未知的错误。在代码上线运行的过程中，往往会出现很多我们意想不到的错误，因为线上环境和开发环境是非常不同的，错误定位到最后往往是一个非常小的原因。然而为了解决这个错误，我们需要先自验证、再打包出待替换的class文件、暂停业务并重启，对于一个成熟的项目而言，最后一条其实影响是非常大的，这意味着这段时间用户无法访问应用。因此，在写代码的时候，从源头开始注意各种细节，权衡并使用最优的选择，将会很大程度上避免出现未知的错误，从长远看也极大的降低了工作量。

**代码优化的目标是：**

1、减小代码的体积

2、提高代码运行的效率

**代码优化细节**

**（1）尽量指定类、方法的final修饰符**

带有final修饰符的类是不可派生的。在Java核心API中，有许多应用final的例子，例如java.lang.String，整个类都是final的。为类指定final修饰符可以让类不可以被继承，为方法指定final修饰符可以让方法不可以被重写。如果指定了一个类为final，则该类所有的方法都是final的。Java编译器会寻找机会内联所有的final方法，内联对于提升Java运行效率作用重大，具体参见Java运行期优化。此举能够使性能平均提高50%。

**（2）尽量重用对象**

特别是String对象的使用，出现字符串连接时应该使用StringBuilder/StringBuffer代替。由于Java虚拟机不仅要花时间生成对象，以后可能还需要花时间对这些对象进行垃圾回收和处理，因此，生成过多的对象将会给程序的性能带来很大的影响。

**（3）尽可能使用局部变量**

调用方法时传递的参数以及在调用中创建的临时变量都保存在栈中，速度较快，其他变量，如静态变量、实例变量等，都在堆中创建，速度较慢。另外，栈中创建的变量，随着方法的运行结束，这些内容就没了，不需要额外的垃圾回收。

**（4）及时关闭流**

Java编程过程中，进行数据库连接、I/O流操作时务必小心，在使用完毕后，及时关闭以释放资源。因为对这些大对象的操作会造成系统大的开销，稍有不慎，将会导致严重的后果。

**（5）尽量减少对变量的重复计算**

明确一个概念，对方法的调用，即使方法中只有一句语句，也是有消耗的，包括创建栈帧、调用方法时保护现场、调用方法完毕时恢复现场等。所以例如下面的操作：


```
for (int i = 0; i < list.size(); i++)
{...}
```

建议替换为：


```
for (int i = 0, length = list.size(); i < length; i++)
{...}
```

这样，在list.size()很大的时候，就减少了很多的消耗

**（6）尽量采用懒加载的策略，即在需要的时候才创建**

例如：

```
String str = "aaa";
if (i == 1)
{
　　list.add(str);
}
```

建议替换为：

```
if (i == 1)
{
　　String str = "aaa";
　　list.add(str);
}
```

**（7）慎用异常**

异常对性能不利。抛出异常首先要创建一个新的对象，Throwable接口的构造函数调用名为fillInStackTrace()的本地同步方法，fillInStackTrace()方法检查堆栈，收集调用跟踪信息。只要有异常被抛出，Java虚拟机就必须调整调用堆栈，因为在处理过程中创建了一个新的对象。异常只能用于错误处理，不应该用来控制程序流程。

**（8）不要在循环中使用try...catch...，应该把其放在最外层**

根据网友们提出的意见，这一点我认为值得商榷

**（9）如果能估计到待添加的内容长度，为底层以数组方式实现的集合、工具类指定初始长度**

比如ArrayList、LinkedLlist、StringBuilder、StringBuffer、HashMap、HashSet等等，以StringBuilder为例：

- StringBuilder()　　　　　　// 默认分配16个字符的空间
- StringBuilder(int size)　　// 默认分配size个字符的空间
- StringBuilder(String str)　// 默认分配16个字符+str.length()个字符空间

可以通过类（这里指的不仅仅是上面的StringBuilder）的构造函数来设定它的初始化容量，这样可以明显地提升性能。比如StringBuilder吧，length表示当前的StringBuilder能保持的字符数量。因为当StringBuilder达到最大容量的时候，它会将自身容量增加到当前的2倍再加2，无论何时只要StringBuilder达到它的最大容量，它就不得不创建一个新的字符数组然后将旧的字符数组内容拷贝到新字符数组中----这是十分耗费性能的一个操作。试想，如果能预估到字符数组中大概要存放5000个字符而不指定长度，最接近5000的2次幂是4096，每次扩容加的2不管，那么：

- 在4096 的基础上，再申请8194个大小的字符数组，加起来相当于一次申请了12290个大小的字符数组，如果一开始能指定5000个大小的字符数组，就节省了一倍以上的空间
- 把原来的4096个字符拷贝到新的的字符数组中去
这样，既浪费内存空间又降低代码运行效率。所以，给底层以数组实现的集合、工具类设置一个合理的初始化容量是错不了的，这会带来立竿见影的效果。但是，注意，像HashMap这种是以数组+链表实现的集合，别把初始大小和你估计的大小设置得一样，因为一个table上只连接一个对象的可能性几乎为0。初始大小建议设置为2的N次幂，如果能估计到有2000个元素，设置成new HashMap(128)、new HashMap(256)都可以。

**（10）当复制大量数据时，使用System.arraycopy()命令**

**（11）乘法和除法使用移位操作**

例如：


```
for (val = 0; val < 100000; val += 5)
{
　　a = val * 8;
　　b = val / 2;
}
```

用移位操作可以极大地提高性能，因为在计算机底层，对位的操作是最方便、最快的，因此建议修改为： 


```
for (val = 0; val < 100000; val += 5)
{
　　a = val << 3;
　　b = val >> 1;
}
```

移位操作虽然快，但是可能会使代码不太好理解，因此最好加上相应的注释。

**（12）循环内不要不断创建对象引用**

例如：


```
for (int i = 1; i <= count; i++)
{
    Object obj = new Object();    
}
```

这种做法会导致内存中有count份Object对象引用存在，count很大的话，就耗费内存了，建议为改为：


```
Object obj = null;
for (int i = 0; i <= count; i++)
{
    obj = new Object();
}
```

这样的话，内存中只有一份Object对象引用，每次new Object()的时候，Object对象引用指向不同的Object罢了，但是内存中只有一份，这样就大大节省了内存空间了。

**（13）基于效率和类型检查的考虑，应该尽可能使用array，无法确定数组大小时才使用ArrayList**

**（14）尽量使用HashMap、ArrayList、StringBuilder，除非线程安全需要，否则不推荐使用Hashtable、Vector、StringBuffer，后三者由于使用同步机制而导致了性能开销**

**（15）不要将数组声明为public static final**

因为这毫无意义，这样只是定义了引用为static final，数组的内容还是可以随意改变的，将数组声明为public更是一个安全漏洞，这意味着这个数组可以被外部类所改变

**（16）尽量在合适的场合使用单例**

使用单例可以减轻加载的负担、缩短加载的时间、提高加载的效率，但并不是所有地方都适用于单例，简单来说，单例主要适用于以下三个方面：

- 控制资源的使用，通过线程同步来控制资源的并发访问
- 控制实例的产生，以达到节约资源的目的
- 控制数据的共享，在不建立直接关联的条件下，让多个不相关的进程或线程之间实现通信
 

**（17）尽量避免随意使用静态变量**

要知道，当某个对象被定义为static的变量所引用，那么gc通常是不会回收这个对象所占有的堆内存的，如：


```
public class A
{
    private static B b = new B();  
}
```

此时静态变量b的生命周期与A类相同，如果A类不被卸载，那么引用B指向的B对象会常驻内存，直到程序终止

 **（18）及时清除不再需要的会话**

为了清除不再活动的会话，许多应用服务器都有默认的会话超时时间，一般为30分钟。当应用服务器需要保存更多的会话时，如果内存不足，那么操作系统会把部分数据转移到磁盘，应用服务器也可能根据MRU（最近最频繁使用）算法把部分不活跃的会话转储到磁盘，甚至可能抛出内存不足的异常。如果会话要被转储到磁盘，那么必须要先被序列化，在大规模集群中，对对象进行序列化的代价是很昂贵的。因此，当会话不再需要时，应当及时调用HttpSession的invalidate()方法清除会话。

**（19）实现RandomAccess接口的集合比如ArrayList，应当使用最普通的for循环而不是foreach循环来遍历**

这是JDK推荐给用户的。JDK API对于RandomAccess接口的解释是：实现RandomAccess接口用来表明其支持快速随机访问，此接口的主要目的是允许一般的算法更改其行为，从而将其应用到随机或连续访问列表时能提供良好的性能。实际经验表明，实现RandomAccess接口的类实例，假如是随机访问的，使用普通for循环效率将高于使用foreach循环；反过来，如果是顺序访问的，则使用Iterator会效率更高。可以使用类似如下的代码作判断：

```
if (list instanceof RandomAccess)
{
    for (int i = 0; i < list.size(); i++){}
}
else
{
    Iterator<?> iterator = list.iterable();
    while (iterator.hasNext()){iterator.next()}
}
```

foreach循环的底层实现原理就是迭代器Iterator，参见Java语法糖1：可变长度参数以及foreach循环原理。所以后半句"反过来，如果是顺序访问的，则使用Iterator会效率更高"的意思就是顺序访问的那些类实例，使用foreach循环去遍历。

**（20）使用同步代码块替代同步方法**

这点在多线程模块中的synchronized锁方法块一文中已经讲得很清楚了，除非能确定一整个方法都是需要进行同步的，否则尽量使用同步代码块，避免对那些不需要进行同步的代码也进行了同步，影响了代码执行效率。

**（21）将常量声明为static final，并以大写命名**

这样在编译期间就可以把这些内容放入常量池中，避免运行期间计算生成常量的值。另外，将常量的名字以大写命名也可以方便区分出常量与变量

**（22）不要创建一些不使用的对象，不要导入一些不使用的类**

这毫无意义，如果代码中出现"The value of the local variable i is not used"、"The import java.util is never used"，那么请删除这些无用的内容

**（23）程序运行过程中避免使用反射**

关于，请参见反射。反射是Java提供给用户一个很强大的功能，功能强大往往意味着效率不高。不建议在程序运行过程中使用尤其是频繁使用反射机制，特别是Method的invoke方法，如果确实有必要，一种建议性的做法是将那些需要通过反射加载的类在项目启动的时候通过反射实例化出一个对象并放入内存----用户只关心和对端交互的时候获取最快的响应速度，并不关心对端的项目启动花多久时间。

**（24）使用数据库连接池和线程池**

这两个池都是用于重用对象的，前者可以避免频繁地打开和关闭连接，后者可以避免频繁地创建和销毁线程

**（25）使用带缓冲的输入输出流进行IO操作**

带缓冲的输入输出流，即BufferedReader、BufferedWriter、BufferedInputStream、BufferedOutputStream，这可以极大地提升IO效率

**（26）顺序插入和随机访问比较多的场景使用ArrayList，元素删除和中间插入比较多的场景使用LinkedList**

这个，理解ArrayList和LinkedList的原理就知道了

**（27）不要让public方法中有太多的形参**

public方法即对外提供的方法，如果给这些方法太多形参的话主要有两点坏处：

- 违反了面向对象的编程思想，Java讲求一切都是对象，太多的形参，和面向对象的编程思想并不契合
- 参数太多势必导致方法调用的出错概率增加

至于这个"太多"指的是多少个，3、4个吧。比如我们用JDBC写一个insertStudentInfo方法，有10个学生信息字段要插如Student表中，可以把这10个参数封装在一个实体类中，作为insert方法的形参

**（28）字符串变量和字符串常量equals的时候将字符串常量写在前面**

这是一个比较常见的小技巧了，如果有以下代码：


```
String str = "123";
if (str.equals("123"))
{
    ...
}
```

建议修改为：

```
String str = "123";
if ("123".equals(str))
{
    ...
}
```

这么做主要是可以避免空指针异常

**（29）请知道，在java中if (i == 1)和if (1 == i)是没有区别的，但从阅读习惯上讲，建议使用前者**

平时有人问，"if (i==1)"和"if(1==i)"有没有区别，这就要从C/C++讲起。

在C/C++中，"if (i==1)"判断条件成立，是以0与非0为基准的，0表示false，非0表示true，如果有这么一段代码：


```
int i = 2;
if (i == 1)
{
    ...
}
else
{
    ...
}
```
C/C++判断"i==1"不成立，所以以0表示，即false。但是如果：


```
int i = 2;
if (i = 1)
{
    ...
}
else
{
    ...
}
```

万一程序员一个不小心，把"if(i==1)"写成"if(i=1)"，这样就有问题了。在if之内将i赋值为1，if判断里面的内容非0，返回的就是true了，但是明明i为2，比较的值是1，应该返回的false。这种情况在C/C++的开发中是很可能发生的并且会导致一些难以理解的错误产生，所以，为了避免开发者在if语句中不正确的赋值操作，建议将if语句写为：


```
int i = 2;
if (1 == i)
{
    ...
}
else
{
    ...
}
```

这样，即使开发者不小心写成了"1=i"，C/C++编译器也可以第一时间检查出来，因为我们可以对一个变量赋值i为1，但是不能对一个常量赋值1为i。

但是，在Java中，C/C++这种"if(i=1)"的语法是不可能出现的，因为一旦写了这种语法，Java就会编译报错"Type mismatch: cannot convert from int to boolean"。但是，尽管Java的"if (i == 1)"和"if (1 == i)"在语义上没有任何区别，从阅读习惯上讲，建议使用前者会更好些。

**（30）不要对数组使用toString()方法**

看一下对数组使用toString()打印出来的是什么：


```
public static void main(String[] args)
{
    int[] is = new int[]{1, 2, 3};
    System.out.println(is.toString());
}
```

结果是：


```
[I@18a992f
```

本意是想打印出数组内容，却有可能因为数组引用is为空而导致空指针异常。不过虽然对数组toString()没有意义，但是对集合toString()是可以打印出集合里面的内容的，因为集合的父类AbstractCollections<E>重写了Object的toString()方法。

**（31）不要对超出范围的基本数据类型做向下强制转型**

这绝不会得到想要的结果：


```
public static void main(String[] args)
{
    long l = 12345678901234L;
    int i = (int)l;
    System.out.println(i);
}
```

我们可能期望得到其中的某几位，但是结果却是：


```
1942892530
```

解释一下。Java中long是8个字节64位的，所以12345678901234在计算机中的表示应该是：


```
0000 0000 0000 0000 0000 1011 0011 1010 0111 0011 1100 1110 0010 1111 1111 0010
```

一个int型数据是4个字节32位的，从低位取出上面这串二进制数据的前32位是：


```
0111 0011 1100 1110 0010 1111 1111 0010
```

这串二进制表示为十进制1942892530，所以就是我们上面的控制台上输出的内容。从这个例子上还能顺便得到两个结论：

1、整型默认的数据类型是int，long l = 12345678901234L，这个数字已经超出了int的范围了，所以最后有一个L，表示这是一个long型数。顺便，浮点型的默认类型是double，所以定义float的时候要写成""float f = 3.5f"

2、接下来再写一句"int ii = l + i;"会报错，因为long + int是一个long，不能赋值给int

 **（32）公用的集合类中不使用的数据一定要及时remove掉**

如果一个集合类是公用的（也就是说不是方法里面的属性），那么这个集合里面的元素是不会自动释放的，因为始终有引用指向它们。所以，如果公用集合里面的某些数据不使用而不去remove掉它们，那么将会造成这个公用集合不断增大，使得系统有内存泄露的隐患。

**（33）把一个基本数据类型转为字符串，基本数据类型.toString()是最快的方式、String.valueOf(数据)次之、数据+""最慢**

把一个基本数据类型转为一般有三种方式，我有一个Integer型数据i，可以使用i.toString()、String.valueOf(i)、i+""三种方式，三种方式的效率如何，看一个测试：

```
public static void main(String[] args)
{
    int loopTime = 50000;
    Integer i = 0;
    long startTime = System.currentTimeMillis();
    for (int j = 0; j < loopTime; j++)
    {
        String str = String.valueOf(i);
    }    
    System.out.println("String.valueOf()：" + (System.currentTimeMillis() - startTime) + "ms");
    startTime = System.currentTimeMillis();
    for (int j = 0; j < loopTime; j++)
    {
        String str = i.toString();
    }    
    System.out.println("Integer.toString()：" + (System.currentTimeMillis() - startTime) + "ms");
    startTime = System.currentTimeMillis();
    for (int j = 0; j < loopTime; j++)
    {
        String str = i + "";
    }    
    System.out.println("i + \"\"：" + (System.currentTimeMillis() - startTime) + "ms");
}
```

运行结果为：


```
String.valueOf()：11ms
Integer.toString()：5ms
i + ""：25ms
```

所以以后遇到把一个基本数据类型转为String的时候，优先考虑使用toString()方法。至于为什么，很简单：

- String.valueOf()方法底层调用了Integer.toString()方法，但是会在调用前做空判断
- Integer.toString()方法就不说了，直接调用了
- i + ""底层使用了StringBuilder实现，先用append方法拼接，再用toString()方法获取字符串

三者对比下来，明显是2最快、1次之、3最慢


**（34）使用最有效率的方式去遍历Map**

遍历Map的方式有很多，通常场景下我们需要的是遍历Map中的Key和Value，那么推荐使用的、效率最高的方式是：

```
public static void main(String[] args)
{
    HashMap<String, String> hm = new HashMap<String, String>();
    hm.put("111", "222");
        
    Set<Map.Entry<String, String>> entrySet = hm.entrySet();
    Iterator<Map.Entry<String, String>> iter = entrySet.iterator();
    while (iter.hasNext())
    {
        Map.Entry<String, String> entry = iter.next();
        System.out.println(entry.getKey() + "\t" + entry.getValue());
    }
}
```

如果你只是想遍历一下这个Map的key值，那用"Set<String> keySet = hm.keySet();"会比较合适一些

**（35）对资源的close()建议分开操作**

意思是，比如我有这么一段代码：

```
try
{
    XXX.close();
    YYY.close();
}
catch (Exception e)
{
    ...
}
```

建议修改为：

```
try
{
    XXX.close();
}
catch (Exception e)
{
    ...
}
try
{
    YYY.close();
}
catch (Exception e)
{
    ...
}
```

虽然有些麻烦，却能避免资源泄露。我们想，如果没有修改过的代码，万一XXX.close()抛异常了，那么就进入了catch块中了，YYY.close()不会执行，YYY这块资源就不会回收了，一直占用着，这样的代码一多，是可能引起资源句柄泄露的。而改为下面的写法之后，就保证了无论如何XXX和YYY都会被close掉

 **（36）对于ThreadLocal使用前或者使用后一定要先remove**

当前基本所有的项目都使用了线程池技术，这非常好，可以动态配置线程数、可以重用线程。

然而，如果你在项目中使用到了ThreadLocal，一定要记得使用前或者使用后remove一下。这是因为上面提到了线程池技术做的是一个线程重用，这意味着代码运行过程中，一条线程使用完毕，并不会被销毁而是等待下一次的使用。我们看一下Thread类中，持有ThreadLocal.ThreadLocalMap的引用：

```
/* ThreadLocal values pertaining to this thread. This map is maintained
 * by the ThreadLocal class. */
ThreadLocal.ThreadLocalMap threadLocals = null;
```


线程不销毁意味着上条线程set的ThreadLocal.ThreadLocalMap中的数据依然存在，那么在下一条线程重用这个Thread的时候，很可能get到的是上条线程set的数据而不是自己想要的内容。

这个问题非常隐晦，一旦出现这个原因导致的错误，没有相关经验或者没有扎实的基础非常难发现这个问题，因此在写代码的时候就要注意这一点，这将给你后续减少很多的工作量。
 
**（37）切记以常量定义的方式替代魔鬼数字，魔鬼数字的存在将极大地降低代码可读性，字符串常量是否使用常量定义可以视情况而定**

**（38）long或者Long初始赋值时，使用大写的L而不是小写的l，因为字母l极易与数字1混淆，这个点非常细节，值得注意**

 

**（39）所有重写的方法必须保留@Override注解**

这么做有三个原因：

（1）清楚地可以知道这个方法由父类继承而来

（2）getObject()和get0bject()方法，前者第四个字母是"O"，后者第四个子母是"0"，加了@Override注解可以马上判断是否重写成功

（3）在抽象类中对方法签名进行修改，实现类会马上报出编译错误


**（40）推荐使用JDK7中新引入的Objects工具类来进行对象的equals比较，直接a.equals(b)，有空指针异常的风险**

 **（41）循环体内不要使用"+"进行字符串拼接，而直接使用StringBuilder不断append**

说一下不使用"+"进行字符串拼接的原因，假如我有一个方法：

```
public String appendStr(String oriStr, String... appendStrs) {
    if (appendStrs == null || appendStrs.length == 0) {
        return oriStr;
    }
        
    for (String appendStr : appendStrs) {
        oriStr += appendStr;
    }
        
    return oriStr;
}
```

将这段代码编译之后的.class文件，使用javap -c进行反编译一下，截取关键的一部分：

![image](https://images2015.cnblogs.com/blog/801753/201703/801753-20170311184020826-1263016756.png)

意思就是每次虚拟机碰到"+"这个操作符对字符串进行拼接的时候，会new出一个StringBuilder，然后调用append方法，最后调用toString()方法转换字符串赋值给oriStr对象，即循环多少次，就会new出多少个StringBuilder()来，这对于内存是一种浪费。

 
**（42）不捕获Java类库中定义的继承自RuntimeException的运行时异常类**

异常处理效率低，RuntimeException的运行时异常类，其中绝大多数完全可以由程序员来规避，比如：

- ArithmeticException可以通过判断除数是否为空来规避
- NullPointerException可以通过判断对象是否为空来规避
- IndexOutOfBoundsException可以通过判断数组/字符串长度来规避
- ClassCastException可以通过instanceof关键字来规避
- ConcurrentModificationException可以使用迭代器来规避
 

**（43）避免Random实例被多线程使用，虽然共享该实例是线程安全的，但会因竞争同一seed导致的性能下降，JDK7之后，可以使用ThreadLocalRandom来获取随机数**

解释一下竞争同一个seed导致性能下降的原因，比如，看一下Random类的nextInt()方法实现：


```
public int nextInt() {
      return next(32);
}
```

 
调用了next(int bits)方法，这是一个受保护的方法：

```
protected int next(int bits) {
    long oldseed, nextseed;
    AtomicLong seed = this.seed;
    do {
        oldseed = seed.get();
        nextseed = (oldseed * multiplier + addend) & mask;
    } while (!seed.compareAndSet(oldseed, nextseed));
    return (int)(nextseed >>> (48 - bits));
}
```

而这边的seed是一个全局变量：


```
/**
* The internal state associated with this pseudorandom number generator.
* (The specs for the methods in this class describe the ongoing
* computation of this value.)
*/
private final AtomicLong seed;
```


多个线程同时获取随机数的时候，会竞争同一个seed，导致了效率的降低。

**（44）静态类、单例类、工厂类将它们的构造函数置为private**

这是因为静态类、单例类、工厂类这种类本来我们就不需要外部将它们new出来，将构造函数置为private之后，保证了这些类不会产生实例对象。

**后记**

优秀的代码来自每一点点小小的优化，关注每一个细节，不仅仅能提升程序运行效率，同样可以规避许多未知的问题。
