

最近发现微信群里面有些群友在讨论类的初始化顺序，如类的静态变量、成员变量、静态代码块、非静态代码块、构造器，及继承父类时，它们的初始化顺序都是怎样的，下面我通过例子来说明这个情况，以免被人误导。

#### 示例1：测试单类的初始化顺序

```
public class ClassInitOrderTest {

	public static String staticField = "static field";

	static {
		System.out.println(staticField);
		System.out.println("static block");
	}

	private String field = "member field";

	{
		System.out.println(field);
		System.out.println("non-static block");
	}

	public ClassInitOrderTest() {
		System.out.println("constructor");
	}

	public static void main(String[] args) {
		new ClassInitOrderTest();
	}

}
```

程序输出：


```
static field
static block
member field
non-static block
constructor
```

可以得出以下结论，单类的初始化顺序为：

> **静态变量 > 静态初始块 > 成员变量 > 非静态初始块 > 构造器**


#### 示例2：测试类继承的初始化顺序

```
class Parent {

	private static String parentStaticField = "parent static field";

	static {
		System.out.println(parentStaticField);
		System.out.println("parent static block");
	}

	private String parentField = "parent member field";

	{
		System.out.println(parentField);
		System.out.println("parent non-static block");
	}

	public Parent() {
		System.out.println("parent constructor");
	}

}

public class Child extends Parent {

	private static String childStaticField = "child static field";

	static {
		System.out.println(childStaticField);
		System.out.println("child static block");
	}

	private String childField = "child member field";

	{
		System.out.println(childField);
		System.out.println("child non-static block");
	}

	public Child() {
		System.out.println("child constructor");
	}

	public static void main(String[] args) {
		new Child();
	}

}
```

程序输出：


```
parent static field
parent static block
child static field
child static block
parent member field
parent non-static block
parent constructor
child member field
child non-static block
child constructor
```

可以得出以下结论，单类的初始化顺序为：

> **父类静态变量 > 父类静态初始块 > 子类静态变量 > 子类静态初始块 > 父类成员变量 > 父类非静态初始块 > 父类构造器 > 子类成员变量 > 子类非静态初始块 > 子类构造器**


#### 示例3：测试成员变量、初始块的初始化顺序

从上面两个例子可以看出，父类的静态变量和静态初始块肯定是先于子类加载的。但同一个类中的静态变量与静态初始块之间，成员变量与初始块之间的顺序一定是变量先于初始块吗？继续演示！

```
public class TestOrder {

	private static A a = new A();

	static {
		System.out.println("static block");
	}

	private static B b = new B();

	public static void main(String[] args) {
		new TestOrder();
	}

}

class A {
	public A() {
		System.out.println("static field A");
	}
}

class B {
	public B() {
		System.out.println("static field B");
	}
}
```

程序输出：


```
static field A
static block
static field B
```

可以得出以下结论，单类的初始化顺序为：

> **静态变量A > 静态初始块 > 静态变量B**

所以，它们的在类中的顺序就决定了它们的初始化顺序，而不是变量一定会优先于初始块。


