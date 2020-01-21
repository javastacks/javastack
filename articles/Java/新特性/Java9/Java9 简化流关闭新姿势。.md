
![](http://img.javastack.cn/18-2-27/87594869.jpg)

做Java开发的都知道，每个资源的打开都需要对应的关闭操作，不然就会使资源一直占用而造成资源浪费，从而降低系统性能。

关于资源的关闭操作，从JDK7-JDK9有了不少的提升及简化。

#### JDK6

在JDK6及之前，每个资源都需要我们手动写代码关闭，如：

```
FileInputStream fis = null;
byte[] buffer = new byte[1024];
try {
	fis = new FileInputStream(new File("E:\\Java技术栈.txt"));
	while (fis.read(buffer) > 0) {
		System.out.println(new String(buffer));
	}
} catch (Exception e) {
	e.printStackTrace();
} finally {
	if (fis != null) {
		try {
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
```

资源打开使用完后，必须在finally块中进行手动关闭！我们有的同事，做一个查询功能点，打开了连接查询完后没有手动关闭，最后造成连接池超出最大连接数而使系统功能堵塞。

#### JDK7

JDK7发布后，添加了新特性：try-with-resources语句。所有需要关闭的资源只要实现了`java.lang.AutoCloseable`（java.io.Closeable就实现了这个接口）接口就在会程序结束后自动关闭。

如上面的读取文件的流程序用JDK7来写：

```
byte[] buffer = new byte[1024];
try (FileInputStream fis = new FileInputStream(new File("E:\\Java技术栈.txt"))) {
	while (fis.read(buffer) > 0) {
		System.out.println(new String(buffer));
	}
} catch (Exception e) {
	e.printStackTrace();
}
```

所有的资源在`try()`里面定义，并去掉了finally模块。

下面我们来写一个自定义的流来看看是否自动关闭了。

**定义一个自定义输入输出流**

```
class MyInputStream implements AutoCloseable {

	void read(String content) {
		System.out.println("read content " + content);
	}

	@Override
	public void close() throws Exception {
		System.out.println("input stream is closed.");
	}

}

class MyOutputStream implements AutoCloseable {

	void write(String content) {
		System.out.println("write content " + content);
	}

	@Override
	public void close() throws Exception {
		System.out.println("out stream is closed.");
	}

}
```

**单个资源自动关闭**

```
try (MyInputStream mis = new MyInputStream()) {
	mis.read("7_2");
} catch (Exception e) {
	e.printStackTrace();
}
```

输出：

> read content 7_2\
> input stream is closed.

**多个资源自动关闭**

`try()`里面可以定义多个资源，它们的关闭顺序是最后在`try()`定义的资源先关闭。

```
try (MyInputStream mis = new MyInputStream(); MyOutputStream mos = new MyOutputStream()) {
	mis.read("7_3");
	mos.write("7_3");
} catch (Exception e) {
	e.printStackTrace();
}
```

输出：

> read content 7_3\
> write content 7_3\
> out stream is closed.\
> input stream is closed.

#### JDK9

JDK9发布后，又简化了try-with-resources语句的用法。

`try()`里面可以是一个变量，但必须是final的或者等同final才行。如下面的mis，mos定义成局部变量可以不用final，局部变量可以等同于final，但定义成成员变量就必须是用final修饰的，不然会编译错误。

```
MyInputStream mis = new MyInputStream();
MyOutputStream mos = new MyOutputStream();
try (mis; mos) {
	mis.read("1.9");
	mos.write("1.9");
} catch (Exception e) {
	e.printStackTrace();
}
```

输出：

> read content 1.9\
> write content 1.9\
> out stream is closed.\
> input stream is closed.

再来看个例子：

```
Connection dbCon = DriverManager.getConnection("url", "user", "password");
try (dbCon; ResultSet rs = dbCon.createStatement().executeQuery("select * from emp")) {
    while (rs.next()) {
        System.out.println("In loadDataFromDB() =====>>>>>>>>>>>> " + rs.getString(1));
    }
} catch (SQLException e) {
    System.out.println("Exception occurs while reading the data from DB ->" + e.getMessage());
}
```

dbCon和rs都能被自动关闭。

JKD9虽然简化了，但感觉还是没有什么质的变化，实际用途我们可能不希望关心资源的关闭，或者在方法结束之后如果是局部变量它就能自动关闭。或许是我站的高度不够，官方有其他的考量，但JDK9的这一点变化还是非常有用的。

更多JDK9的新功能实战陆续更新，如果觉得有用，分享到朋友圈给更多的人吧！

