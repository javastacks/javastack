
现在 Java 9 被遗弃了直接升级到了 Java 10，之前也发过 Java 10 新特性的文章，现在是开始实战 Java 10 的时候了。

今天要实战的是 Java 10 中最重要的特性：局部变量类型推断，大家都知道是 `var` 关键字，但具体怎么使用，及要注意什么要点呢？

我们通过几个例子来讲解局部变量类型推断这个新特性！


### 什么是局部变量类型推断

```
var javastack = "javastack";
System.out.println(javastack);
```

大家看出来了，局部变量类型推断就是左边的类型直接使用 `var` 定义，而不用写具体的类型，编译器能根据右边的表达式自动推断类型，如上面的 `String` 。

```
var javastack = "javastack";
```

就等于：

```
String javastack = "javastack";
```

### 局部变量类型推断使用示例

既然叫局部变量类型推断，以只能用在局部变量中，下面给出更多使用示例。

**1、字面量定义局部变量**

```
private static void testVar() {
	var javastack = "javastack";
	System.out.println(javastack);
}
```

**2、接收方法返回值定义局部变量**


```
private static void testMethod() {
	var javastack = getJavastack();
	System.out.println(javastack);
}

public static String getJavastack() {
	return "javastack";
}
```

**3、循环中定义局部变量**

```
private static void testLoop() {
	for (var i = 0; i < 3; i++) {
		for (var m = 10; m < 15; m++) {
			System.out.println(i + m);
		}
	}
}
```

**4、泛型结合局部变量**

```
private static void testGeneric() {
	// 表达式1
	List<String> list1 = new ArrayList<>();
	list1.add("javastack");

	// 表达式2
	var list2 = new ArrayList<>();
	list2.add(2018);

	// 表达式3
	var list3 = new ArrayList<String>();
	list3.add("javastack");
}
```

表达式1后面 `<>` 里面 jdk 1.7+开始是不用带具体类型的，在接口中指明就行了。

表达式2中如果使用 `var` 的话，`<>` 里面默认会是 `Object` 的，所以可以添加任意类型。

表达式3中在 `<>` 强制使用了 String 来指定泛型。


### 局部变量类型推断不能用在以下场景

**1、类成员变量类型**

```
// 编译报错
private var javastack = "Java技术栈";
```

**2、方法返回类型**

```
/**
 * 编译报错
 * @return
 */
public static var getJavastack(){
 	return "Java技术栈";
}
```

**3、Lambda 表达式**

```
private static void testLambda() {
	Runnable runnable = () -> System.out.println("javastack");

	// 编译报错
	// var runnable = () -> System.out.println("javastack");
}
```

以上 3 种场景是肯定不能使用 `var` 的，其他场合有待验证。


### 局部变量类型推断优缺点

**优点：简化代码**

```
CopyOnWriteArrayList list1 = new CopyOnWriteArrayList();
ConcurrentModificationException cme1 = new ConcurrentModificationException();
DefaultServiceUnavailableRetryStrategy strategy1 = new
		DefaultServiceUnavailableRetryStrategy();

var list2 = new CopyOnWriteArrayList<>();
var cme2 = new ConcurrentModificationException();
var strategy2 = new DefaultServiceUnavailableRetryStrategy();
```

从以上代码可以看出，很长的定义类型会显得代码很冗长，使用 var 大大简化了代码编写，同时类型统一显得代码很对齐。

**缺点：掩盖类型**

```
var token = new JsonParserDelegate(parser).currentToken();
```

看以上代码，不进去看返回结果类型，谁知道返回的类型是什么？所以这种情况最好别使用 `var`，而使用具体的抽象类、接口或者实例类型。

### var关键字原理

var其实就是 Java 10 增加的一种语法糖而已，在编译期间会自动推断实际类型，其编译后的字节码和实际类型一致，如以下例子所示。


```
private static void testByteCode() {
	String javastack1 = "javastack";
	var javastack2 = "javastack";
}
```

编译成字节码后：

```
private static testByteCode()V
L0
LINENUMBER 22 L0
LDC "javastack"
ASTORE 0
L1
LINENUMBER 23 L1
LDC "javastack"
ASTORE 1
L2
LINENUMBER 24 L2
RETURN
L3
LOCALVARIABLE javastack1 Ljava/lang/String; L1 L3 0
LOCALVARIABLE javastack2 Ljava/lang/String; L2 L3 1
MAXSTACK = 1
MAXLOCALS = 2
```

可以看出 `javastack1` 和 `javastack2` 都是虚拟机所认识的的本地变量类型：`java.lang.String`，虚拟机并不认识 var,  所以 `var` 并不神奇。

OK，本次 Java 10 局部变量类型推断实战文章就到这里了，后续带来更多的 Java 10 的实战方面的文章。


