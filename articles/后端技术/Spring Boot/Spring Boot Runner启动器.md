## Runner启动器

如果你想在Spring Boot启动的时候运行一些特定的代码，你可以实现接口`ApplicationRunner`或者`CommandLineRunner`，这两个接口实现方式一样，它们都只提供了一个run方法。


**CommandLineRunner**：启动获取命令行参数。

```
public interface CommandLineRunner {

	/**
	 * Callback used to run the bean.
	 * @param args incoming main method arguments
	 * @throws Exception on error
	 */
	void run(String... args) throws Exception;

}
```

**ApplicationRunner**：启动获取应用启动的时候参数。

```
public interface ApplicationRunner {

	/**
	 * Callback used to run the bean.
	 * @param args incoming application arguments
	 * @throws Exception on error
	 */
	void run(ApplicationArguments args) throws Exception;

}
```

## 使用方式


```
import org.springframework.boot.*
import org.springframework.stereotype.*

@Component
public class MyBean implements CommandLineRunner {

    public void run(String... args) {
        // Do something...
    }

}
```

或者这样


```
@Bean
public CommandLineRunner init() {

	return (String... strings) -> {
	
	};

}
```

## 启动顺序

如果启动的时候有多个ApplicationRunner和CommandLineRunner，想控制它们的启动顺序，可以实现`org.springframework.core.Ordered`接口或者使用`org.springframework.core.annotation.Order`注解。
