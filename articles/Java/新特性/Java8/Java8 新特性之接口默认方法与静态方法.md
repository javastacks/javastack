
## 接口默认方法与静态方法

有这样一些场景，如果一个接口要添加一个方法，那所有的接口实现类都要去实现，而某些实现类根本就不需要实现这个方法也要写一个空实现，所以接口默认方法就是为了解决这个问题。

接口静态方法类似于默认方法，但是我们不能在实现类中覆盖它们，可以避免默认方法在实现类中被覆盖实现。

之前讲过的JDK8的list.foreache方法，看下源码，其实也是java.lang.Iterable接口的默认方法。

```
default void forEach(Consumer<? super T> action) {
    Objects.requireNonNull(action);
    for (T t : this) {
        action.accept(t);
    }
}
```

## 简单的例子

默认方法以`default`修饰，静态方法用`static`。

```
public static void main(String[] args) {
	IUserInterface userInterface = new UserServiceImpl();
	System.out.println(userInterface.getDefaultUser1());
	System.out.println(IUserInterface.getDefaultUser2());
}

interface IUserInterface {

	default User getDefaultUser1() {
		return new User("Susan1", 11);
	}

	static User getDefaultUser2() {
		return new User("Susan2", 22);
	}

}

static class UserServiceImpl implements IUserInterface {

}
```

## 注意要点

1、接口默认方法、静态方法可以有多个。

2、默认方法通过实例调用，静态方法通过接口名调用。

3、`default`默认方法关键字只能用在接口中。

4、默认方法可以被继承，如果继承了多个接口，多个接口都定义了多个同样的默认方法，实现类需要重写默认方法不然会报错。

5、静态方法不能被继承及覆盖，所以只被具体所在的接口调用。





