
SDR - Spring Data Redis的简称。

Spring Data Redis提供了从Spring应用程序轻松配置和访问Redis的功能。它提供了与商店互动的低级别和高级别抽象，使用户免受基础设施问题的困扰。

### Spring Boot 实战

#### 引用依赖

```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
    <version>${spring-boot.version}</version>
</dependency>
```

#### 添加redis配置类


```
@EnableCaching
@Configuration
public class RedisConfig extends CachingConfigurerSupport {

	@Bean
	public KeyGenerator keyGenerator() {
		return (Object target, Method method, Object... params) -> {
			StringBuilder sb = new StringBuilder();
			sb.append(target.getClass().getName());
			sb.append(method.getName());
			for (Object obj : params) {
				sb.append(obj.toString());
			}
			return sb.toString();
		};
	}

	@Bean
	public CacheManager cacheManager(RedisTemplate redisTemplate) {
		RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
		cacheManager.setDefaultExpiration(10000);
		return cacheManager;
	}

	@Bean
	public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
		StringRedisTemplate template = new StringRedisTemplate(factory);
		template.setValueSerializer(getSerializer(template));
		template.afterPropertiesSet();
		return template;
	}

	private RedisSerializer getSerializer(StringRedisTemplate template) {
		ObjectMapper om = new ObjectMapper();
		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);

		Jackson2JsonRedisSerializer serializer = new Jackson2JsonRedisSerializer(Object.class);
		serializer.setObjectMapper(om);

		return serializer;
	}

}
```

#### 添加redis配置参数：


```
spring.redis:
  database: 0 # Redis数据库索引（默认为0）
  host: 192.168.1.168
  port: 6379
  #password: 123456
  timeout: 0 # 连接超时时间（毫秒）
  pool: 
    max-active: 8 # 连接池最大连接数（使用负值表示没有限制）
    max-idle: 8 # 连接池中的最大空闲连接
    max-wait: -1 # 连接池最大阻塞等待时间（使用负值表示没有限制）
    min-idle: 0 # 连接池中的最小空闲连接
```

#### 开始使用


```
@Autowired
private RedisTemplate redisTemplate;

...
redisTemplate.opsForValue().set("test", System.currentTimeMillis());
...
```

### 通过 RedisTemplate 处理对象

大多数用户可能会使用RedisTemplate它的相应软件包org.springframework.data.redis.core-由于其丰富的功能集，模板实际上是Redis模块的中心类。该模板提供了Redis交互的高级抽象。虽然RedisConnection提供接受和返回二进制值（byte数组）的低级别方法，但模板负责序列化和连接管理，使用户无需处理这些细节。

此外，该模板提供了操作视图，它提供丰富的，通用的接口，用于针对特定类型或某些键（通过KeyBound接口）进行操作，如下所述：

##### 键类型操作：

接口 | 描述
---|---|
GeoOperations | Redis的地理空间操作，如GEOADD，GEORADIUS..
HashOperations | Redis散列类型操作
HyperLogLogOperations | Redis的HyperLogLog操作，如PFADD，PFCOUNT..
ListOperations | Redis列表操作
SetOperations | Redis集合操作
ValueOperations | Redis字符串操作
ZSetOperations | Redis有序集合操作


##### 键绑定操作：

接口 | 描述
---|---|
BoundGeoOperations | Redis的地理空间操作
BoundHashOperations | Redis散列类型键绑定操作
BoundKeyOperations | Redis键绑定操作
BoundListOperations | Redis列表键绑定操作
BoundSetOperations | Redis集合键绑定操作
BoundValueOperations | Redis字符串键绑定操作
BoundZSetOperations | Redis有序集合键绑定操作

##### 怎么使用？

Spring Boot实战Redis章节配置完成后，使用Spring直接注入即可。

```
public class Example {

  @Autowired
  private RedisTemplate<String, String> template;

  @Resource(name="redisTemplate")
  private ListOperations<String, String> listOps;

  public void addLink(String userId, URL url) {
    listOps.leftPush(userId, url.toExternalForm());
  }
}
```

RedisTemplate是线程安全的，开箱即用，可以在多个实例中重复使用。

##### RedisTemplate和StringRedisTemplate区别？

> org.springframework.data.redis.core.RedisTemplate
>
> org.springframework.data.redis.core.StringRedisTemplate

1、StringRedisTemplate继承自RedisTemplate

2、StringRedisTemplate默认使用String序列化方式，RedisTemplate默认使用jdk自带的序列化方式。

3、两者数据不互通，只能各自管理各自处理过的数据。

推荐使用StringRedisTemplate。

##### 直接与Redis对话

直接底层的与Redis对话，没有封装。默认配置只能一个数据库，如下，可以直接通过获取StringRedisConnection来切换当前操作的数据库。

```
stringRedisTemplate.execute((RedisCallback<Boolean>) connection -> {
	StringRedisConnection stringRedisConnection = (StringRedisConnection) connection;
	stringRedisConnection.select(5);
	stringRedisConnection.set("name", "zoe");
	return true;
});
```

### 序列化器

从Spring Data Redis框架本身的角度看，存放到redis的数据只是字节，虽然Redis本身支持各种类型，但大部分是指数据存储的方式，而不是它所代表的内容，由用户决定是否将字节转换为字符串或其他对象。

用户自定义类型和原始数据之间的转换由org.springframework.data.redis.serializer包中的序列化器进行处理。

这个包下面主要包含了两种类型的序列化器：

- 基于`RedisSerializer`的双向串行器。
- 元素的读写使用的RedisElementReader和RedisElementWriter。

它们的主要区别是，RedisSerializer序列化成byte[]，而后者使用的是ByteBuffer。

#### 序列化器实现类

这里有几种开箱即用的实现，其中有两种在之前的文章已经涉及过。

实现 | 描述
---|---
StringRedisSerializer | String/byte[]转换，速度快
JdkSerializationRedisSerializer | JDK自带序列化
OxmSerializer | XML序列化，占空间，速度慢
Jackson2JsonRedisSerializer | JSON序列化，需要定义JavaType
GenericJackson2JsonRedisSerializer | JSON序列化，无需定义JavaType

所以，如果只是简单的字符串类型，使用StringRedisSerializer就可以了，如果要有对象就使用Json的序列化吧，可以很方便的组装成对象。


### 事务支持

Spring Data Redis提供了对Redis的事务支持，如：multi, exec, discard命令。

Spring Data Redis提供了SessionCallback接口，在同一个连接中需要执行多个操作时使用，与使用Redis事务时一样。

#### 示例

```
@Test
public void testTransaction() {
	List<Object> txResults = (List<Object>) stringRedisTemplate
			.execute(new SessionCallback<List<Object>>() {
				public List<Object> execute(RedisOperations operations)
						throws DataAccessException {
					operations.multi();
					operations.opsForSet().add("t1", "value1");
					operations.opsForSet().add("t2", "value2");
					operations.opsForSet().add("t3", "value3");
					return operations.exec();
				}
			});
	txResults.forEach(e -> logger.info("txResults: " + e));
}
```

以上代码，是一个接受字符串值的模板，RedisTemplate会使用相应的序列化器，如果把value3换成非字符串333，那第3条会报错，前面两个也不会保存成功。

当然，exec方法也可以接受自定义的序列化器

```
List<Object> exec(RedisSerializer<?> valueSerializer);
```

## @Transactional注解支持

注解事务支持在默认情况下是禁用的，必须通过把RedisTemplate设置明确开启事务支持：setEnableTransactionSupport(true)，如果没有错误即成功，有错误就全部回滚。当前连接所有写操作都会进入操作队列，读操作会转移到一个新的连接。

#### 示例配置


```
@Configuration
public class RedisTxContextConfiguration {
  @Bean
  public StringRedisTemplate redisTemplate() {
    StringRedisTemplate template = new StringRedisTemplate(redisConnectionFactory());
    // explicitly enable transaction support
    template.setEnableTransactionSupport(true);
    return template;
  }

  @Bean
  public PlatformTransactionManager transactionManager() throws SQLException {
    return new DataSourceTransactionManager(dataSource());
  }

  @Bean
  public RedisConnectionFactory redisConnectionFactory( // jedis || lettuce);

  @Bean
  public DataSource dataSource() throws SQLException { // ... }
}
```

##### 使用约束


```
// 绑定到当前线程上的连接
template.opsForValue().set("foo", "bar");

// 读操作不参与事务
connection template.keys("*");

// 当在事务中设置的值不可见时返回null
template.opsForValue().get("foo");
```

