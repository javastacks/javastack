
## 先解释下什么是序列化

我们的对象并不只是存在内存中，还需要传输网络，或者保存起来下次再加载出来用，所以需要Java序列化技术。

Java序列化技术正是将对象转变成一串由二进制字节组成的数组，可以通过将二进制数据保存到磁盘或者传输网络，磁盘或者网络接收者可以在对象的属类的模板上来反序列化类的对象，达到对象持久化的目的。

更多序列化请参考：《[关于Java序列化你应该知道的一切](https://mp.weixin.qq.com/s/wHmK1kKyne6gCkIxt0NERQ)》这篇文章。

## 什么是 transient？

简单来说就是，被 transient 修饰的变量不能被序列化。

**具体来看下面的示例1**

```
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * @author 微信公众号：Java技术栈
 */
public class TransientTest {

	public static void main(String[] args) throws Exception {

		User user = new User();
		user.setUsername("Java技术栈");
		user.setId("javastack");

		System.out.println("\n序列化之前");
		System.out.println("username: " + user.getUsername());
		System.out.println("id: " + user.getId());

		ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("d:/user.txt"));
		os.writeObject(user);
		os.flush();
		os.close();

		ObjectInputStream is = new ObjectInputStream(new FileInputStream("d:/user.txt"));
		user = (User) is.readObject();
		is.close();

		System.out.println("\n序列化之后");
		System.out.println("username: " + user.getUsername());
		System.out.println("id: " + user.getId());

	}
}

/**
 * @author 微信公众号：Java技术栈
 */
class User implements Serializable {

	private static final long serialVersionUID = 1L;

	private String username;
	private transient String id;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
```

**输出结果：**

```
序列化之前
username: Java技术栈
id: javastack

序列化之后
username: Java技术栈
id: null
```

示例1在 id 字段上加了 transient 关键字修饰，反序列化出来之后值为 null，说明了被 transient 修饰的变量不能被序列化。

## 静态变量能被序列化吗？

这个话题也是最近栈长的Java技术栈vip群里面讨论的，大家对这个知识点比较模糊，我就写了这篇文章测试总结一下。

![](http://img.javastack.cn/微信图片_20190214162351.png)

> 如果你也想加入我们的Java技术栈vip群和各位大牛一起讨论技术，那点击[这个链接](https://mp.weixin.qq.com/s/iqCLAduVzDqt19L6D4FCUQ)了解加入吧。

那么，到底静态变量能被序列化吗？废话少说，先动手测试下吧！

示例2：

```
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * @author 微信公众号：Java技术栈
 */
public class TransientStaticTest {

	public static void main(String[] args) throws Exception {

		User2 user = new User2();
		User2.username = "Java技术栈1";
		user.setId("javastack");

		System.out.println("\n序列化之前");
		System.out.println("username: " + user.getUsername());
		System.out.println("id: " + user.getId());

		ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("d:/user.txt"));
		os.writeObject(user);
		os.flush();
		os.close();
		
		// 在反序列化出来之前，改变静态变量的值
		User2.username = "Java技术栈2";

		ObjectInputStream is = new ObjectInputStream(new FileInputStream("d:/user.txt"));
		user = (User2) is.readObject();
		is.close();

		System.out.println("\n序列化之后");
		System.out.println("username: " + user.getUsername());
		System.out.println("id: " + user.getId());

	}
}

/**
 * @author 微信公众号：Java技术栈
 */
class User2 implements Serializable {

	private static final long serialVersionUID = 1L;

	public static String username;
	private transient String id;

	public String getUsername() {
		return username;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
```

**输出结果：**

```
序列化之前
username: Java技术栈1
id: javastack

序列化之后
username: Java技术栈2
id: null
```

示例2把 username 改为了 public static, 并在反序列化出来之前改变了静态变量的值，结果可以看出序列化之后的值并非序列化进去时的值。

由以上结果分析可知，静态变量不能被序列化，示例2读取出来的是 username 在 JVM 内存中存储的值。

## transient 真不能被序列化吗？

继续来看示例3：

```
import java.io.Externalizable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

/**
 * @author 微信公众号：Java技术栈
 */
public class ExternalizableTest {

	public static void main(String[] args) throws Exception {

		User3 user = new User3();
		user.setUsername("Java技术栈");
		user.setId("javastack");
		ObjectOutput objectOutput = new ObjectOutputStream(new FileOutputStream(new File("javastack")));
		objectOutput.writeObject(user);

		ObjectInput objectInput = new ObjectInputStream(new FileInputStream(new File("javastack")));
		user = (User3) objectInput.readObject();

		System.out.println(user.getUsername());
		System.out.println(user.getId());

		objectOutput.close();
		objectInput.close();
	}

}

/**
 * @author 微信公众号：Java技术栈
 */
class User3 implements Externalizable {

	private static final long serialVersionUID = 1L;

	public User3() {

	}

	private String username;
	private transient String id;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeObject(id);
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException, ClassNotFoundException {
		id = (String) objectInput.readObject();
	}

}
```

**输出结果：**

```
null
javastack
```

示例3的 id 被 transient 修改了，为什么还能序列化出来？那是因为 User3 实现了接口 Externalizable，而不是 Serializable。

在 Java 中有两种实现序列化的方式，Serializable 和 Externalizable，可能大部分人只知道 Serializable 而不知道 Externalizable。

这两种序列化方式的区别是：实现了 Serializable 接口是自动序列化的，实现 Externalizable 则需要手动序列化，通过 writeExternal 和 readExternal 方法手动进行，这也是为什么上面的 username 为 null 的原因了。

## transient 关键字总结

1）transient修饰的变量不能被序列化；

2）transient只作用于实现 Serializable 接口；

3）transient只能用来修饰普通成员变量字段；

4）不管有没有 transient 修饰，静态变量都不能被序列化；

好了，栈长花了半天时间，终于整理完了。如果对你有帮助，那就转发分享一下吧！如果你也想加入我们的Java技术栈vip群和各位大牛一起讨论技术，那点击[这个链接](https://mp.weixin.qq.com/s/iqCLAduVzDqt19L6D4FCUQ)了解加入吧

另外，栈长已经整理了大量 Java 系列核心技术知识点文章，关注Java技术栈微信公众号，在后台回复关键字：java，即可获取最新版。

> 本文原创首发于微信公众号：Java技术栈（id:javastack），关注公众号在后台回复 "java" 可获取更多，转载请原样保留本信息。

