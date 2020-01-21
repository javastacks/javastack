
使用 Spring Boot 会涉及到各种各样的配置，如开发、测试、线上就至少 3 套配置信息了。Spring Boot 可以轻松的帮助我们使用相同的代码就能使开发、测试、线上环境使用不同的配置。

**在 Spring Boot 里面，可以使用以下几种方式来加载配置。本章内容基于 Spring Boot 2.0 进行详解。**

1、properties文件；

2、YAML文件；

3、系统环境变量；

4、命令行参数；

等等……

**我们可以在 Spring Beans 里面直接使用这些配置文件中加载的值，如：**

1、使用 `@Value` 注解直接注入对应的值，这能获取到 Spring 中 `Environment` 的值；

2、使用 `@ConfigurationProperties` 注解把对应的值绑定到一个对象；

3、直接获取注入 `Environment` 进行获取；


配置属性的方式很多，Spring boot使用了一种独有的 `PropertySource` 可以很方便的覆盖属性的值。

**配置属性加载的顺序如下：**

```

1、开发者工具 `Devtools` 全局配置参数；

2、单元测试上的 `@TestPropertySource` 注解指定的参数；

3、单元测试上的 `@SpringBootTest` 注解指定的参数；

4、命令行指定的参数，如 `java -jar springboot.jar --name="Java技术栈"`；

5、命令行中的 `SPRING_APPLICATION_JSONJSON` 指定参数, 如 `java -Dspring.application.json='{"name":"Java技术栈"}' -jar springboot.jar`

6、`ServletConfig` 初始化参数；

7、`ServletContext` 初始化参数；

8、JNDI参数（如 `java:comp/env/spring.application.json`）；

9、Java系统参数（来源：`System.getProperties()`）；

10、操作系统环境变量参数；

11、`RandomValuePropertySource` 随机数，仅匹配：`ramdom.*`；

12、JAR包外面的配置文件参数（`application-{profile}.properties（YAML）`）

13、JAR包里面的配置文件参数（`application-{profile}.properties（YAML）`）

14、JAR包外面的配置文件参数（`application.properties（YAML）`）

15、JAR包里面的配置文件参数（`application.properties（YAML）`）

16、`@Configuration`配置文件上 `@PropertySource` 注解加载的参数；

17、默认参数（通过 `SpringApplication.setDefaultProperties` 指定）；

```

**数字小的优先级越高，即数字小的会覆盖数字大的参数值，我们来实践下，验证以上配置参数的加载顺序。**

1、在主应用程序中添加 Java 系统参数。

```
@Bean
public CommandLineRunner commandLineRunner() {
	return (args) -> {
		System.setProperty("name", "javastack-system-properties");
	};
}
```

2、在 application.properties 文件中添加属性。

```
name = javastack-application
```

3、在 application-dev.properties 文件中添加属性。

```
name = javastack-application-dev

```

4、添加测试类

```
@RunWith(SpringRunner.class)
@SpringBootTest(value = { "name=javastack-test", "sex=1" })
@ActiveProfiles("dev")
public class SpringBootBestPracticeApplicationTests {

	@Value("${name}")
	private String name;

	@Test
	public void test() {
		System.out.println("name is " + name);
	}

}
```

运行 test 单元测试，程序输出：

```
name is javastack-test
```

根据以上参数动态调整，发现参数会被正确被覆盖。了解了 Spring Boot 各种配置的加载顺序，如果配置被覆盖了我们就知道是什么问题了。

Spring Boot 配置加载顺序详解这一章就到这里了，更多 Spring Boot 的教程请关注我们的 **`Java技术栈`** 微信公众号。

