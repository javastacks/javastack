## Spring Boot自动配置原理

Spring Boot的自动配置注解是@EnableAutoConfiguration，
从上面的@Import的类可以找到下面自动加载自动配置的映射。


```
org.springframework.core.io.support.SpringFactoriesLoader.loadFactoryNames(Class<?>, ClassLoader)
```


```
public static List<String> loadFactoryNames(Class<?> factoryClass, ClassLoader classLoader) {
	String factoryClassName = factoryClass.getName();
	try {
		Enumeration<URL> urls = (classLoader != null ? classLoader.getResources(FACTORIES_RESOURCE_LOCATION) :
				lassLoader.getSystemResources(FACTORIES_RESOURCE_LOCATION));
		List<String> result = new ArrayList<String>();
		while (urls.hasMoreElements()) {
			URL url = urls.nextElement();
			Properties properties = PropertiesLoaderUtils.loadProperties(new UrlResource(url));
			String factoryClassNames = properties.getProperty(factoryClassName);
			result.addAll(Arrays.asList(StringUtils.commaDelimitedListToStringArray(factoryClassNames)));
		}
		return result;
	}
	catch (IOException ex) {
		throw new IllegalArgumentException("Unable to load [" + factoryClass.getName() +
				"] factories from location [" + FACTORIES_RESOURCE_LOCATION + "]", ex);
	}
}
```
这个方法会加载类路径及所有jar包下META-INF/spring.factories配置中映射的自动配置的类。

```
/**
 * The location to look for factories.
 * <p>Can be present in multiple JAR files.
 */
public static final String FACTORIES_RESOURCE_LOCATION = "META-INF/spring.factories";
```

查看Spring Boot自带的自动配置的包：
spring-boot-autoconfigure-1.5.6.RELEASE.jar，打开其中的META-INF/spring.factories文件会找到自动配置的映射。

```
# Auto Configure
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
org.springframework.boot.autoconfigure.admin.SpringApplicationAdminJmxAutoConfiguration,\
org.springframework.boot.autoconfigure.aop.AopAutoConfiguration,\
org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration,\
org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration,\
org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration,\
org.springframework.boot.autoconfigure.cassandra.CassandraAutoConfiguration,\
org.springframework.boot.autoconfigure.cloud.CloudAutoConfiguration,\
...
```

再来看看数据源自动配置的实现注解


```
@Configuration
@ConditionalOnClass({ DataSource.class, EmbeddedDatabaseType.class })
@EnableConfigurationProperties(DataSourceProperties.class)
@Import({ Registrar.class, DataSourcePoolMetadataProvidersConfiguration.class })
public class DataSourceAutoConfiguration {
...
```
@Configuration,@ConditionalOnClass就是自动配置的核心，首先它得是一个配置文件，其次根据类路径下是否有这个类去自动配置。

## 自动配置实战

所以，了解了自动配置的原理，来自己实现一个自动配置的玩意其实很简单。

添加配置类：

```

public class EnvConfig implements EnvironmentAware {

	private final Logger logger = LoggerUtils.getLogger(this);

	private Environment env;

	public String getStringValue(String key) {
		return env.getProperty(key);
	}

	public Long getLongValue(String key) {
		String value = getStringValue(key);
		try {
			return Long.parseLong(value);
		} catch (Exception e) {
			logger.error("字符串转换Long失败：{} = {}", key, value);
		}
		return 0L;
	}

	public int getIntValue(String key) {
		return getLongValue(key).intValue();
	}

	@Override
	public void setEnvironment(Environment environment) {
		this.env = environment;
	}

}
```

添加自动配置类：


```
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.PropertyResolver;

@Configuration
@ConditionalOnClass(PropertyResolver.class)
public class EnvAutoConfig {

	@Bean
	public EnvConfig envConfig() {
		return new EnvConfig();
	}

}
```

创建META-INF/spring.factories文件，添加自动配置映射：

```
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\\
cn.javastack.config.EnvAutoConfig
```
这样就搞定了。

## 查看自动配置报告

怎么查看自己加的自动配置类有没有被加载，或者查看所有自动配置激活的和未激活的可以通过以下几种试查看。

1、spring-boot:run运行的在对话框Enviroment中加入debug=true变量 \
2、java -jar xx.jar --debug\
3、main方法运行，在VM Argumanets加入-Ddebug\
4、直接在application文件中加入debug=true\
5、如果集成了spring-boot-starter-actuator监控，通过autoconfig端点也可以查看。

启动后会在控制台看到以下自动配置报告信息：

```
=========================
AUTO-CONFIGURATION REPORT
=========================


Positive matches:
-----------------

   AopAutoConfiguration matched:
      - @ConditionalOnClass found required classes 'org.springframework.context.annotation.EnableAspectJAutoProxy', 'org.aspectj.lang.annotation.Aspect', 'org.aspectj.lang.reflect.Advice'; @ConditionalOnMissingClass did not find unwanted class (OnClassCondition)
      - @ConditionalOnProperty (spring.aop.auto=true) matched (OnPropertyCondition)
	  
   ...

   EnvAutoConfig matched:
      - @ConditionalOnClass found required class 'org.springframework.core.env.PropertyResolver'; @ConditionalOnMissingClass did not find unwanted class (OnClassCondition)

   ErrorMvcAutoConfiguration matched:
      - @ConditionalOnClass found required classes 'javax.servlet.Servlet', 'org.springframework.web.servlet.DispatcherServlet'; @ConditionalOnMissingClass did not find unwanted class (OnClassCondition)
      - @ConditionalOnWebApplication (required) found StandardServletEnvironment (OnWebApplicationCondition)

   ErrorMvcAutoConfiguration#basicErrorController matched:
      - @ConditionalOnMissingBean (types: org.springframework.boot.autoconfigure.web.ErrorController; SearchStrategy: current) did not find any beans (OnBeanCondition)

   ...


Negative matches:
-----------------

   ActiveMQAutoConfiguration:
      Did not match:
         - @ConditionalOnClass did not find required classes 'javax.jms.ConnectionFactory', 'org.apache.activemq.ActiveMQConnectionFactory' (OnClassCondition)

   AopAutoConfiguration.JdkDynamicAutoProxyConfiguration:
      Did not match:
         - @ConditionalOnProperty (spring.aop.proxy-target-class=false) found different value in property 'proxy-target-class' (OnPropertyCondition)

   ArtemisAutoConfiguration:
      Did not match:
         - @ConditionalOnClass did not find required classes 'javax.jms.ConnectionFactory', 'org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory' (OnClassCondition)

   BatchAutoConfiguration:
      Did not match:
         - @ConditionalOnClass did not find required class 'org.springframework.batch.core.launch.JobLauncher' (OnClassCondition)
		 
   ...
```

Positive matches：已经启用的自动配置\
Negative matches：未启用的自动配置

从报告中看到自己添加的EnvAutoConfig已经自动配置了。