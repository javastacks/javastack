之前分篇章讲了一些JKD8中添加的新特性，还有一些新特性这里也一并讲下。

## BASE64

base64编码解码已经被加入到了jdk8中了。

```
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Base64Test {
	public static void main(String[] args) {
		String text = "hello javastack";

		String encoded = Base64.getEncoder().encodeToString(text.getBytes(StandardCharsets.UTF_8));
		System.out.println(encoded);

		String decoded = new String(Base64.getDecoder().decode(encoded), StandardCharsets.UTF_8);
		System.out.println(decoded);
	}
}
```

## Date/Time API(JSR 310)

新的时间、日期。


```
Clock clock = Clock.systemUTC();
System.out.println(clock.instant());
System.out.println(clock.millis());
```


输出：

```
2017-09-06T07:26:18.541Z
1504682778593
```

可以代替`System.currentTimeMillis()`方法。

另外，可以看下LocalDate、LocalTime、LocalDateTime、Duration的用法。

## Nashorn JavaScript引擎

可以运行js代码的引擎。


```
ScriptEngineManager manager = new ScriptEngineManager();
ScriptEngine engine = manager.getEngineByName("JavaScript");

System.out.println(engine.getClass().getName());
System.out.println("Result:" + engine.eval("function f() { return 10; }; f() * 24;"));
```
输出：

```
jdk.nashorn.api.scripting.NashornScriptEngine
Result:240.0
```

## JVM内存取消永久代

JDK8使用了Metaspace（JEP 122）替换永久代（PermGen space）。参数使用-XX:MetaSpaceSize和-XX:MaxMetaspaceSize代替原来的-XX:PermSize和-XX:MaxPermSize。

还有一些别的新特性，个人觉得某些新特性用处不是很大。
