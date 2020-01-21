![image](http://img.javastack.cn/17-12-20/25802617.jpg)

最近，微信群友在讨论子类父类的转换问题，其实不难，给大家用实例来说明一下就很明了了。

我们知道Java中子类转换成父类是没有任何问题的，那父类可以转换成子类吗？

来看下面这段程序：

```
public class TestObjectConvert {

	public static void main(String[] args) {
		test1();
		test2();
	}

	private static void test1() {
		Fruit fruit1 = new Fruit();
		Apple apple1 = new Apple();
		apple1 = (Apple) fruit1; // java.lang.ClassCastException
	}

	private static void test2() {
		Fruit fruit1 = new Apple();
		Apple apple1 = new Apple();
		apple1 = (Apple) fruit1;
	}

	static class Fruit {

	}

	static class Apple extends Fruit {

	}

}
```

结果是：

```
test1：报类转异常；
test2：转换正常。
```

**所以，想让父类强制转换成子类，不是没有可能，除非父类是子类构造出来的实例，不然是不能强转的。**

为什么呢？

如上代码，如果父类实例出来的对象是Orange，Orange当然不能强制转成Apple，所以说父类只有该子类对应的实例才能强转。

在公众号回复"wx"加入微信群，可参与更多技术话题讨论。
