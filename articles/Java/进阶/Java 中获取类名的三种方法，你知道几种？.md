之前我们分享过[Java高级篇——深入浅出Java类加载机制](https://mp.weixin.qq.com/s/uV-voSMJcjAOEsbimYgzJQ)这篇文章，今天来带你了解获取类名的三种方法。

#### 获取类名的方法

Java 中获取类名的方式主要有以下三种。

- **getName()**

返回的是虚拟机里面的class的类名表现形式。

- **getCanonicalName()**

返回的是更容易理解的类名表示。

- **getSimpleName()**

返回的是类的简称。

#### 都有什么区别？

通过一个实例来看下它们主要的区别。

```
public class TestClass {

	public static void main(String[] args) {
		// 外部普通类
		System.out.println("方法名             类名");
		System.out.println("getName            " + TestClass.class.getName());
		System.out.println("getCanonicalName   " + TestClass.class.getCanonicalName());
		System.out.println("getSimpleName      " + TestClass.class.getSimpleName());
		System.out.println();

		// 内部类
		System.out.println("getName            " + TestInnerClass.class.getName());
		System.out.println("getCanonicalName   " + TestInnerClass.class.getCanonicalName());
		System.out.println("getSimpleName      " + TestInnerClass.class.getSimpleName());
		System.out.println();

		// 数组类
		TestInnerClass[] testInnerClasses = new TestInnerClass[]{
				new TestInnerClass(),
				new TestInnerClass(),
				new TestInnerClass()
		};
		System.out.println("getName            " + testInnerClasses.getClass().getName());
		System.out.println("getCanonicalName   " + testInnerClasses.getClass().getCanonicalName());
		System.out.println("getSimpleName      " + testInnerClasses.getClass().getSimpleName());
		System.out.println();
	}

	static class TestInnerClass {

	}

}
```

程序输出以下结果。

```
方法名              类名
getName            com.test.TestClass
getCanonicalName   com.test.TestClass
getSimpleName      TestClass

getName            com.test.TestClass$TestInnerClass
getCanonicalName   com.test.TestClass.TestInnerClass
getSimpleName      TestInnerClass

getName            [Lcom.test.TestClass$TestInnerClass;
getCanonicalName   com.test.TestClass.TestInnerClass[]
getSimpleName      TestInnerClass[]
```

`[Lcom.test.TestClass$TestInnerClass;`值得说明一下。

这是一种对函数返回值和参数的编码，叫做JNI字段描述符（JavaNative Interface FieldDescriptors)。

`[` 表示数组，一个代表一维数组，比如 `[[` 代表二维数组。之后 `L` 代表类描述符，最后`;`表示类名结束。

#### 结论

1、从以上结果可以看出 getName() 和 getCanonicalName() 在获取普通类名的时候没有区别，在获取内部类和数组类有区别的。

2、getSimpleName() 在获取普通类和内部类名的时候没区别，在获取数组类的时候有区别。

