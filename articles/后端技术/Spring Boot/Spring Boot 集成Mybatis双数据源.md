这里用到了Spring Boot + Mybatis + DynamicDataSource配置动态双数据源，可以动态切换数据源实现数据库的读写分离。

## 添加依赖

加入Mybatis启动器，这里添加了Druid连接池、Oracle数据库驱动为例。

```
<dependency>
	<groupId>org.mybatis.spring.boot</groupId>
	<artifactId>mybatis-spring-boot-starter</artifactId>
</dependency>

<dependency>
	<groupId>com.alibaba</groupId>
	<artifactId>druid</artifactId>
</dependency>

<dependency>
	<groupId>com.oracle</groupId>
	<artifactId>ojdbc6</artifactId>
</dependency>

```

## 添加启动类


```
@EnableMybatis
@EnableTransactionManagement
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(ServiceApplication.class, args);
	}

}
```


@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })：
这里用到了双数据源，需要排除数据源的自动配置，如果只有一个数据源用Spring Boot的自动配置就行。

@EnableTransactionManagement：开启事务支持。

@EnableMybatis：开启Mybatis功能

```
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(MybatisConfig.class)
public @interface EnableMybatis {

}
```

## Mybatis配置类

```
@Configuration
@MapperScan(basePackages = DSConfig.BASE_PACKAGES)
public class MybatisConfig implements DSConfig {

	@Primary
	@Bean
	public DynamicDataSource dynamicDataSource(@Qualifier(DB_MASTER) DataSource master,
			@Qualifier(DB_SLAVE) DataSource slave) {
		Map<Object, Object> dsMap = new HashMap<>();
		dsMap.put(DB_MASTER, master);
		dsMap.put(DB_MASTER, slave);

		DynamicDataSource dynamicDataSource = new DynamicDataSource();
		dynamicDataSource.setDefaultTargetDataSource(master);
		dynamicDataSource.setTargetDataSources(dsMap);
		return dynamicDataSource;
	}

	@Bean
	public PlatformTransactionManager transactionManager(DynamicDataSource dynamicDataSource) {
		return new DataSourceTransactionManager(dynamicDataSource);
	}

	@Bean
	public SqlSessionFactory sqlSessionFactory(DynamicDataSource dynamicDataSource)
			throws Exception {
		SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(dynamicDataSource);
		sessionFactory.setMapperLocations(
				((ResourcePatternResolver) new PathMatchingResourcePatternResolver())
						.getResources(DSConfig.MAPPER_LOCATIONS));
		return sessionFactory.getObject();
	}

}
```

DSConfig常量类：

```
public interface DSConfig {

	String DS_PREFIX = "spring.datasource";
	String DS_ACTIVE = "active";

	String DB_MASTER = "db-master";
	String DB_SLAVE = "db-slave";

	String DRUID = "druid";

	String DRUID_MONITOR_USERNAME = "spring.druid.username";
	String DRUID_MONITOR_PASSWORD = "spring.druid.password";
	String DRUID_MONITOR_URL = "/druid/*";
	String DRUID_FILTER_EXCLUSIONS = "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*";
	String DRUID_FILTER_URL = "/*";

	String BASE_PACKAGES = "com.example.**.mapper";
	String MAPPER_LOCATIONS = "mapper/**/*.xml";

}
```

## 连接池配置类

Druid连接池的自动配置类：

```
@Configuration
@Import({ PropertiesConfig.class })
@ConditionalOnClass(DruidDataSource.class)
@ConditionalOnProperty(prefix = DSConfig.DS_PREFIX, value = DSConfig.DS_ACTIVE, havingValue = DSConfig.DRUID)
public class DruidAutoConfig implements DSConfig {

	private Logger logger = LoggerUtils.getLogger(this);

	@Bean(name = DB_MASTER, initMethod = "init", destroyMethod = "close")
	public DataSource dataSourceMaster(DruidMasterProperties masterProperties) throws SQLException {
		logger.debug("master properties: {}", masterProperties.toString());

		DruidDataSource dds = new DruidDataSource();
		dds.setDriverClassName(masterProperties.getDriverClassName());
		dds.setUrl(masterProperties.getUrl());
		dds.setUsername(masterProperties.getUsername());
		dds.setPassword(masterProperties.getPassword());
		dds.setInitialSize(masterProperties.getInitialSize());
		dds.setMinIdle(masterProperties.getMinIdle());
		dds.setMaxActive(masterProperties.getMaxActive());
		dds.setMaxWait(masterProperties.getMaxWait());
		dds.setTimeBetweenEvictionRunsMillis(masterProperties.getTimeBetweenEvictionRunsMillis());
		dds.setMinEvictableIdleTimeMillis(masterProperties.getMinEvictableIdleTimeMillis());
		dds.setValidationQuery(masterProperties.getValidationQuery());
		dds.setTestOnBorrow(masterProperties.isTestOnBorrow());
		dds.setTestWhileIdle(masterProperties.isTestWhileIdle());
		dds.setTestOnReturn(masterProperties.isTestOnReturn());
		dds.setPoolPreparedStatements(masterProperties.isPoolPreparedStatements());
		dds.setMaxPoolPreparedStatementPerConnectionSize(
				masterProperties.getMaxPoolPreparedStatementPerConnectionSize());
		dds.setFilters(masterProperties.getFilters());

		return dds;
	}

	@Bean(name = DB_SLAVE, initMethod = "init", destroyMethod = "close")
	public DataSource dataSourceSlave(DruidSlaveProperties slaveProperties) throws SQLException {
		logger.debug("slave properties: {}", slaveProperties.toString());

		DruidDataSource dds = new DruidDataSource();
		dds.setDriverClassName(slaveProperties.getDriverClassName());
		dds.setUrl(slaveProperties.getUrl());
		dds.setUsername(slaveProperties.getUsername());
		dds.setPassword(slaveProperties.getPassword());
		dds.setInitialSize(slaveProperties.getInitialSize());
		dds.setMinIdle(slaveProperties.getMinIdle());
		dds.setMaxActive(slaveProperties.getMaxActive());
		dds.setMaxWait(slaveProperties.getMaxWait());
		dds.setTimeBetweenEvictionRunsMillis(slaveProperties.getTimeBetweenEvictionRunsMillis());
		dds.setMinEvictableIdleTimeMillis(slaveProperties.getMinEvictableIdleTimeMillis());
		dds.setValidationQuery(slaveProperties.getValidationQuery());
		dds.setTestOnBorrow(slaveProperties.isTestOnBorrow());
		dds.setTestWhileIdle(slaveProperties.isTestWhileIdle());
		dds.setTestOnReturn(slaveProperties.isTestOnReturn());
		dds.setPoolPreparedStatements(slaveProperties.isPoolPreparedStatements());
		dds.setMaxPoolPreparedStatementPerConnectionSize(
				slaveProperties.getMaxPoolPreparedStatementPerConnectionSize());
		dds.setFilters(slaveProperties.getFilters());

		return dds;
	}

	@Bean
	public ServletRegistrationBean druidServletRegistrationBean(EnvConfig env) {
		String username = env.getStringValue(DSConfig.DRUID_MONITOR_USERNAME);
		String password = env.getStringValue(DSConfig.DRUID_MONITOR_PASSWORD);
		return new ServletRegistrationBean(new DruidStatViewServlet(username, password),
				DSConfig.DRUID_MONITOR_URL);
	}

	@Bean
	public FilterRegistrationBean druidFilterRegistrationBean() {
		WebStatFilter wsf = new WebStatFilter();
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		filterRegistrationBean.setFilter(wsf);
		filterRegistrationBean.setUrlPatterns(Arrays.asList(DSConfig.DRUID_FILTER_URL));
		filterRegistrationBean.setInitParameters(
				Collections.singletonMap("exclusions", DSConfig.DRUID_FILTER_EXCLUSIONS));
		return filterRegistrationBean;
	}

}
```

根据类路径下有DruidDataSource这个类即有Druid这个jar包和配置文件中spring.datasource.active=druid才开启对Druid连接池的自动配置。

导入的配置文件：


```
@Configuration
@ComponentScan(basePackages = "com.example.common.config.properties")
public class PropertiesConfig {

}
```

DruidMasterProperties、DruidSlaveProperties属性文件读取的配置省略。

连接池监控配置类：

```
public class DruidStatViewServlet extends StatViewServlet {

	private static final long serialVersionUID = 1L;

	private String username;
	private String password;

	@Override
	public String getInitParameter(String name) {
		if ("loginUsername".equals(name)) {
			return username;
		}

		if ("loginPassword".equals(name)) {
			return password;
		}

		return super.getInitParameter(name);
	}

	public DruidStatViewServlet(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

}
```

在META-INF/spring.factories中加入Druid自动配置映射：


```
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
com.example.common.config.ds.DruidAutoConfig
```


## 切换数据源

切换数据源注解：

```
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DS {
	String value() default DSConfig.DB_MASTER;
}
```

动态数据源类：


```
public class DynamicDataSource extends AbstractRoutingDataSource {

	private final Logger logger = LoggerUtils.getLogger(this);

	@Override
	protected Object determineCurrentLookupKey() {
		logger.debug("当前数据源为{}", DataSourceContextHolder.getDS());
		return DataSourceContextHolder.getDS();
	}

}
```

动态数据源AOP实现类：

```
@Aspect
@Component
public class DynamicDataSourceAspect {

	@Before("@annotation(DS)")
	public void beforeSwitchDS(JoinPoint point) {
		Class<?> className = point.getTarget().getClass();
		String methodName = point.getSignature().getName();
		Class<?>[] argClass = ((MethodSignature) point.getSignature()).getParameterTypes();
		String dataSource = DataSourceContextHolder.DEFAULT_DS;

		try {
			Method method = className.getMethod(methodName, argClass);
			if (method.isAnnotationPresent(DS.class)) {
				DS annotation = method.getAnnotation(DS.class);
				dataSource = annotation.value();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		DataSourceContextHolder.setDS(dataSource);
	}

	@After("@annotation(DS)")
	public void afterSwitchDS(JoinPoint point) {
		DataSourceContextHolder.clearDS();
	}

}
```

绑定当前线程数据源类：

```
public class DataSourceContextHolder {

	public static final String DEFAULT_DS = DSConfig.DB_MASTER;

	private static final ThreadLocal<String> DS_HOLDER = new ThreadLocal<>();

	public static void setDS(String dbType) {
		DS_HOLDER.set(dbType);
	}

	public static String getDS() {
		return (DS_HOLDER.get());
	}

	public static void clearDS() {
		DS_HOLDER.remove();
	}
}
```


