## import使用占位符

连接池切换导入配置的代码：

```
<import resource="classpath:META-INF/spring/spring-${db.connection.pool}.xml" />
```

在配置文件添加配置

```
db.connection.pool=druid
```

启动直接报错，读取不到配置，因为属性文件的加载在import配置文件之后。

```
Caused by: java.lang.IllegalArgumentException: Could not resolve placeholder 'db.connection.pool' in value "classpath:META-INF/spring/spring-${db.connection.pool}.xml"
```

所以，要在应用启动的时候添加属性

1、添加AppContextInitializer启动类：

```
public class AppContextInitializer
		implements ApplicationContextInitializer<ConfigurableApplicationContext> {

	private Logger logger = Logger.getLogger(AppContextInitializer.class);

	@Override
	public void initialize(ConfigurableApplicationContext applicationContext) {
		ResourcePropertySource propertySource = null;
		try {
			propertySource = new ResourcePropertySource("classpath:config/db-config.properties");
		} catch (IOException e) {
			logger.error("加载配置文件[config/db-config.properties]失败");
		}
		applicationContext.getEnvironment().getPropertySources().addFirst(propertySource);
	}

}
```

2、在web.xml中添加配置：

```
context-param>  
    <param-name>contextInitializerClasses</param-name>  
    <param-value>com.example.AppContextInitializer</param-value>  
</context-param>
```

启动配置文件加载正常。