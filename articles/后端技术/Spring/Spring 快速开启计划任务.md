Spring3.1开始让计划任务变得非常简单，只需要几个注解就能快速开启计划任务的支持。

## @EnableScheduling

```
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(SchedulingConfiguration.class)
@Documented
public @interface EnableScheduling {

}
```

@EnableScheduling、@Configuration两个同时使用开启计划任务支持。


```
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@Configuration
public class TaskConfiguration {

}
```


## @Scheduled

在要使用计划任务的方法上使用Scheduled，fixedRate表示固定频率，cron即自定义执行表达式，更多用法参考注解@Scheduled参数。

```
@Service
public class TestTask {

	protected Logger logger = LoggerUtils.getLogger(this);

	@Scheduled(fixedRate = 5000)
	public void runPerFiveSeconds() {
		logger.info("fix");
	}

	@Scheduled(cron = "0/10 * 9 * * ?")
	public void runCron() {
		logger.info("cron");
	}

}
```

