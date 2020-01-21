
## 读取application文件

在application.yml或者properties文件中添加：

info.address=USA\
info.company=Spring\
info.degree=high

> ### @Value注解读取方式

```
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class InfoConfig1 {

	@Value("${info.address}")
	private String address;

	@Value("${info.company}")
	private String company;

	@Value("${info.degree}")
	private String degree;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

}
```

> ### @ConfigurationProperties注解读取方式

```
@Component
@ConfigurationProperties(prefix = "info")
public class InfoConfig2 {

	private String address;
	private String company;
	private String degree;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

}
```

## 读取指定文件

资源目录下建立config/db-config.properties:

db.username=root\
db.password=123456
  
> ### @PropertySource+@Value注解读取方式

```
@Component
@PropertySource(value = { "config/db-config.properties" })
public class DBConfig1 {

	@Value("${db.username}")
	private String username;

	@Value("${db.password}")
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
```
注意：@PropertySource不支持yml文件读取。

> ### @PropertySource+@ConfigurationProperties注解读取方式


```
@Component
@ConfigurationProperties(prefix = "db")
@PropertySource(value = { "config/db-config.properties" })
public class DBConfig2 {

	private String username;
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
```

> ### Environment读取方式

以上所有加载出来的配置都可以通过Environment注入获取到。

```
@Autowired
private Environment env;

// 获取参数
String getProperty(String key);
```

## 总结

从以上示例来看，Spring Boot可以通过@PropertySource,@Value,@Environment,@ConfigurationProperties来绑定变量。
