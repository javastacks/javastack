
## 什么是函数式接口

先来看看传统的创建线程是怎么写的


```
Thread t1 = new Thread(new Runnable() {
	@Override
	public void run() {
		System.out.println("t1");
	}
});
t1.start();
```

再来看看使用了函数式接口是怎么写的


```
Thread t2 = new Thread(() -> System.out.println("t2"));
t2.start();
```

Runnable接口直接可以使用Lambda表达式来编写，这是因为Runnable接口是一个函数式接口，来看看Runnable的源码。

```
@FunctionalInterface
public interface Runnable {

    public abstract void run();
    
}
```
发现该接口加上了函数式接口的定义注解：`@FunctionalInterface`，表明该接口是一个函数式接口。


```
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface FunctionalInterface {
    
}
```


在JDK8中，除了Runnbale接口，还有像Comparator、Callable等接口都加上了该注解定义为函数式接口。

## 内置函数式接口

JDK8提供了几个内置的函数式接口，用在了许多API的地方，都可以拿来用，可以满足大部分应用。

```
//Consumer<T> - T作为输入，执行某种动作但没有返回值
Consumer<String> con = (x) -> {
	System.out.println(x);
};
con.accept("hello world");

//Supplier<T> - 没有任何输入，返回T
Supplier<String> supp = () -> {
	return "Supplier";
};
System.out.println(supp.get());

//Predicate<T> -T作为输入，返回的boolean值作为输出
Predicate<String> pre = (x) -> {
	System.out.print(x);
	return x.startsWith("op");
};
System.out.println(": " + pre.test("op, hello World"));

// Function<T, R> -T作为输入，返回的R作为输出
Function<String, String> function = (x) -> {
	System.out.print(x + ": ");
	return "Function";
};
System.out.println(function.apply("hello world"));

//BinaryOperator<T> -两个T作为输入，返回一个T作为输出，对于“reduce”操作很有用
BinaryOperator<String> bina = (x, y) -> {
	System.out.print(x + " " + y);
	return "BinaryOperator";
};
System.out.println("  " + bina.apply("hello ", "world"));
```



## 自定义函数式接口

#### 1、自定义一个函数式接口

```
@FunctionalInterface
public interface CalcInterface<N, V> {	
	V operation(N n1, N n2);
}
```

这里只有一个抽象方法，@FunctionalInterface注解可以不用写，至于为什么可以往下看。

#### 2、新建一个引用函数式接口的类

```
public static class NumberOperation<N extends Number, V extends Number> {

	private N n1;
	private N n2;

	public NumberOperation(N n1, N n2) {
		this.n1 = n1;
		this.n2 = n2;
	}

	public V calc(CalcInterface<N, V> ci) {
		V v = ci.operation(n1, n2);
		return v;
	}

}
```

#### 3、测试函数式接口

```
private static void testOperationFnInterface() {
        NumberOperation<Integer, Integer> np = new NumberOperation(13, 10);
    
	CalcInterface<Integer, Integer> addOper1 = (n1, n2) -> {
		return n1 + n2;
	};
	CalcInterface<Integer, Integer> multiOper1 = (n1, n2) -> {
		return n1 * n2;
	};
	System.out.println(np.calc1(addOper1));
	System.out.println(np.calc1(multiOper1));
	
	// 上面的可以简写为
	System.out.println(np.calc1((n1, n2) -> n1 + n2));
	System.out.println(np.calc1((n1, n2) -> n1 * n2));
}
```

最后输出：

```
23
130
23
130
```

## 函数式接口规范

1、@FunctionalInterface标识为一个函数式接口只能用在只有一个抽象方法的接口上。

2、接口中的静态方法、默认方法、覆盖了Object类的方法都不算抽象方法。

3、@FunctionalInterface注解不是必须的，如果该接口只有一个抽象方法可以不写，它默认就符合函数式接口，但建议都写上该注解，编译器会检查该接口是否符合函数式接口的规范。


## 举例说明

正确的函数式接口。

```
@FunctionalInterface
public interface CalcInterface<N, V> {	
	V operation(N n1, N n2);
}
```

加了几个符合函数式的方法也没事，编译器也不会报错。

```
@FunctionalInterface
public interface CalcInterface<N, V> {		

	V operation(N n1, N n2);
   
	public boolean equals(Object object);

	public default void defaultMethod() {

	}

	public static void staticMethod() {

	}
}
```

这个没用@FunctionalInterface函数式接口，有两个抽象方法，不能用于Lambda表达式。

```
public interface CalcInterface<N, V> {	
	V operation(N n1, N n2);
	V operation2(N n1, N n2);
}
```

这个有两个抽象方法的用@FunctionalInterface注解的函数式接口编译会报错。

```
@FunctionalInterface
public interface CalcInterface<N, V> {	
	V operation(N n1, N n2);
	V operation2(N n1, N n2);
}
```

这个没有一个抽象方法，编译报错。

```
public interface CalcInterface<N, V> {	
}
```
