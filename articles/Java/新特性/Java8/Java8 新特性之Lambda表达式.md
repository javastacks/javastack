
## 什么是Lambda表达式

Java 8的一个大亮点是引入Lambda表达式，使用它设计的代码会更加简洁。当开发者在编写Lambda表达式时，也会随之被编译成一个函数式接口。

## Lambda语法

一行执行语句的写法：

```
(parameters) -> expression
```

如果有多行执行语句，可以加上`{}`
```
(parameters) -> { statements; }
```

如：

```
public int add(int x, int y) {
    return x + y;
}
```

转换成Lambda表达式有以下几种写法：

```
// 指定参数类型及return
(int x, int y) -> { return x + y; }

// 指定参数类型，不指定return
(int x, int y) -> x + y;

// 不指定参数类型和return，编译器会自动推断
(x, y) -> x + y; 

```

## Lambda用途

#### 1、只有一个抽象方法的函数式接口

Lambda表达式的目标类型是函数式接口，什么是函数式接口之后会讲。

下面拿创建线程来举例，用lambda表达式可以有以下几种写法。

```
public static void main(String[] args) {
	new Thread(new Runnable() {
		@Override
		public void run() {
			System.out.println("t1");
		}
	}).start();

	Runnable runnable = () -> System.out.println("t2");
	new Thread(runnable).start();

	new Thread(() -> System.out.println("t3")).start();

	new Thread(() -> run("t4")).start();

	new Thread(() -> {
		String str = "t5";
		System.out.println(str);
	}).start();

}

private static void run(String str) {
	System.out.println(str);
}
```

最后输出：

```
t1
t2
t3
t4
t5
```

#### 2、集合批量操作

下面打印list集合的两种写法是等价的。

```
List<String> list = Arrays.asList("a","b","c");
for(String str : list){
	System.out.println(str);
}

list.forEach((e) -> System.out.println(e));
```

#### 3、流操作

下面是流查询list集合中等于`"a"`的数量。

```
list.stream().filter((e) -> "a".equals(e)).count();
```

更多的Lambda表达式及关于流更多内容之后陆续会讲到。

