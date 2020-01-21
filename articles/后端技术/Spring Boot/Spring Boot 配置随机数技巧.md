
Spring Boot支持在系统加载的时候配置随机数。

添加config/random.properties文件，添加以下内容：

```
#随机32位MD5字符串
user.random.secret=${random.value}

#随机int数字
user.random.intNumber=${random.int}

#随机long数字
user.random.longNumber=${random.long}

#随便uuid
user.random.uuid=${random.uuid}

#随机10以内的数字
user.random.lessTen=${random.int(10)}

#随机1024~65536之内的数字
user.random.range=${random.int[1024,65536]}
```

添加绑定类：

```
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 微信公众号：Java技术栈
 */
@Component
@ConfigurationProperties(prefix = "user.random")
@PropertySource(value = { "config/random.properties" })
public class RandomConfig {

	private String secret;
	private int intNumber;
	private int lessTen;
	private int range;
	private long longNumber;
	private String uuid;

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public int getIntNumber() {
		return intNumber;
	}

	public void setIntNumber(int intNumber) {
		this.intNumber = intNumber;
	}

	public int getLessTen() {
		return lessTen;
	}

	public void setLessTen(int lessTen) {
		this.lessTen = lessTen;
	}

	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
	}

	public long getLongNumber() {
		return longNumber;
	}

	public void setLongNumber(long longNumber) {
		this.longNumber = longNumber;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
}
```

输出如下：


```
secret=83a5c3402ef936a37842dc6de3d1af0f
intNumber=1816149855
lessTen=1
range=37625
longNumber=8449008776720010146
uuid=e5bc2091-1599-45b1-abd7-e3721ac77e6b
```

具体的生成细节可以参考Spring Boot的配置类：

`org.springframework.boot.context.config.RandomValuePropertySource`

来看下它的源码，实现其实很简单。

```
public RandomValuePropertySource(String name) {
	super(name, new Random());
}

private Object getRandomValue(String type) {
	if (type.equals("int")) {
		return getSource().nextInt();
	}
	if (type.equals("long")) {
		return getSource().nextLong();
	}
	String range = getRange(type, "int");
	if (range != null) {
		return getNextIntInRange(range);
	}
	range = getRange(type, "long");
	if (range != null) {
		return getNextLongInRange(range);
	}
	if (type.equals("uuid")) {
		return UUID.randomUUID().toString();
	}
	return getRandomBytes();
}
```

其实就是使用了 Java 自带的 `java.util.Random` 和 `java.util.UUID` 等工具类，实现很简单，这里就不再详细解析了，大家可以自己去看下这个类的实现。

随机数的生成配置就是这么点了，我知道的是可以随机生成应用程序端口，其他的还真没用到。

@程序猿 你们还知道其他的随机数应用么？

