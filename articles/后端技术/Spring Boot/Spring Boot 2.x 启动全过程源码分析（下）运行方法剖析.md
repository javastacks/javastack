上篇《[Spring Boot 2.x 启动全过程源码分析（一）入口类剖析](https://mp.weixin.qq.com/s/RPU5gwvuWMyzUV8wFZziIw)》我们分析了 Spring Boot 入口类 SpringApplication 的源码，并知道了其构造原理，这篇我们继续往下面分析其核心 run 方法。

[toc]

### SpringApplication 实例 run 方法运行过程

![](http://img.javastack.cn/18-7-2/34936854.jpg)

上面分析了 SpringApplication 实例对象构造方法初始化过程，下面继续来看下这个 SpringApplication 对象的 run 方法的源码和运行流程。

```
public ConfigurableApplicationContext run(String... args) {
    // 1、创建并启动计时监控类
	StopWatch stopWatch = new StopWatch();
	stopWatch.start();
	
	// 2、初始化应用上下文和异常报告集合
	ConfigurableApplicationContext context = null;
	Collection<SpringBootExceptionReporter> exceptionReporters = new ArrayList<>();
	
	// 3、设置系统属性 `java.awt.headless` 的值，默认值为：true
	configureHeadlessProperty();
	
	// 4、创建所有 Spring 运行监听器并发布应用启动事件
	SpringApplicationRunListeners listeners = getRunListeners(args);
	listeners.starting();
	
	try {
	    // 5、初始化默认应用参数类
		ApplicationArguments applicationArguments = new DefaultApplicationArguments(
				args);
				
		// 6、根据运行监听器和应用参数来准备 Spring 环境
		ConfigurableEnvironment environment = prepareEnvironment(listeners,
				applicationArguments);
		configureIgnoreBeanInfo(environment);
		
		// 7、创建 Banner 打印类
		Banner printedBanner = printBanner(environment);
		
		// 8、创建应用上下文
		context = createApplicationContext();
		
		// 9、准备异常报告器
		exceptionReporters = getSpringFactoriesInstances(
				SpringBootExceptionReporter.class,
				new Class[] { ConfigurableApplicationContext.class }, context);
				
		// 10、准备应用上下文
		prepareContext(context, environment, listeners, applicationArguments,
				printedBanner);
				
		// 11、刷新应用上下文
		refreshContext(context);
		
		// 12、应用上下文刷新后置处理
		afterRefresh(context, applicationArguments);
		
		// 13、停止计时监控类
		stopWatch.stop();
		
		// 14、输出日志记录执行主类名、时间信息
		if (this.logStartupInfo) {
			new StartupInfoLogger(this.mainApplicationClass)
					.logStarted(getApplicationLog(), stopWatch);
		}
		
		// 15、发布应用上下文启动完成事件
		listeners.started(context);
		
		// 16、执行所有 Runner 运行器
		callRunners(context, applicationArguments);
	}
	catch (Throwable ex) {
		handleRunFailure(context, ex, exceptionReporters, listeners);
		throw new IllegalStateException(ex);
	}

	try {
	    // 17、发布应用上下文就绪事件
		listeners.running(context);
	}
	catch (Throwable ex) {
		handleRunFailure(context, ex, exceptionReporters, null);
		throw new IllegalStateException(ex);
	}
	
	// 18、返回应用上下文
	return context;
}
```

所以，我们可以按以下几步来分解 run 方法的启动过程。

#### 1、创建并启动计时监控类

```
StopWatch stopWatch = new StopWatch();
stopWatch.start();
```

来看下这个计时监控类 StopWatch 的相关源码：

```
public void start() throws IllegalStateException {
	start("");
}

public void start(String taskName) throws IllegalStateException {
	if (this.currentTaskName != null) {
		throw new IllegalStateException("Can't start StopWatch: it's already running");
	}
	this.currentTaskName = taskName;
	this.startTimeMillis = System.currentTimeMillis();
}
```

首先记录了当前任务的名称，默认为空字符串，然后记录当前 Spring Boot 应用启动的开始时间。

#### 2、初始化应用上下文和异常报告集合

```
ConfigurableApplicationContext context = null;
Collection<SpringBootExceptionReporter> exceptionReporters = new ArrayList<>();
```

#### 3、设置系统属性 `java.awt.headless` 的值

```
configureHeadlessProperty();
```

设置该默认值为：true，Java.awt.headless = true 有什么作用？

> 对于一个 Java 服务器来说经常要处理一些图形元素，例如地图的创建或者图形和图表等。这些API基本上总是需要运行一个X-server以便能使用AWT（Abstract Window Toolkit，抽象窗口工具集）。然而运行一个不必要的 X-server 并不是一种好的管理方式。有时你甚至不能运行 X-server,因此最好的方案是运行 headless 服务器，来进行简单的图像处理。
> 
> 参考：www.cnblogs.com/princessd8251/p/4000016.html

#### 4、创建所有 Spring 运行监听器并发布应用启动事件

```
SpringApplicationRunListeners listeners = getRunListeners(args);
listeners.starting();
```

来看下创建 Spring 运行监听器相关的源码：

```
private SpringApplicationRunListeners getRunListeners(String[] args) {
	Class<?>[] types = new Class<?>[] { SpringApplication.class, String[].class };
	return new SpringApplicationRunListeners(logger, getSpringFactoriesInstances(
			SpringApplicationRunListener.class, types, this, args));
}

SpringApplicationRunListeners(Log log,
		Collection<? extends SpringApplicationRunListener> listeners) {
	this.log = log;
	this.listeners = new ArrayList<>(listeners);
}

```

创建逻辑和之前实例化初始化器和监听器的一样，一样调用的是 `getSpringFactoriesInstances` 方法来获取配置的监听器名称并实例化所有的类。

SpringApplicationRunListener 所有监听器配置在 `spring-boot-2.0.3.RELEASE.jar!/META-INF/spring.factories` 这个配置文件里面。

```
# Run Listeners
org.springframework.boot.SpringApplicationRunListener=\
org.springframework.boot.context.event.EventPublishingRunListener
```

#### 5、初始化默认应用参数类

```
ApplicationArguments applicationArguments = new DefaultApplicationArguments(
		args);
```

#### 6、根据运行监听器和应用参数来准备 Spring 环境

```
ConfigurableEnvironment environment = prepareEnvironment(listeners,
		applicationArguments);
configureIgnoreBeanInfo(environment);
```

下面我们主要来看下准备环境的 `prepareEnvironment` 源码：

```
private ConfigurableEnvironment prepareEnvironment(
		SpringApplicationRunListeners listeners,
		ApplicationArguments applicationArguments) {
	// 6.1) 获取（或者创建）应用环境
	ConfigurableEnvironment environment = getOrCreateEnvironment();
	
	// 6.2) 配置应用环境
	configureEnvironment(environment, applicationArguments.getSourceArgs());
	listeners.environmentPrepared(environment);
	bindToSpringApplication(environment);
	if (this.webApplicationType == WebApplicationType.NONE) {
		environment = new EnvironmentConverter(getClassLoader())
				.convertToStandardEnvironmentIfNecessary(environment);
	}
	ConfigurationPropertySources.attach(environment);
	return environment;
}
```

**6.1) 获取（或者创建）应用环境**

```
private ConfigurableEnvironment getOrCreateEnvironment() {
	if (this.environment != null) {
		return this.environment;
	}
	if (this.webApplicationType == WebApplicationType.SERVLET) {
		return new StandardServletEnvironment();
	}
	return new StandardEnvironment();
}
```

这里分为标准 Servlet 环境和标准环境。

**6.2) 配置应用环境**

```
protected void configureEnvironment(ConfigurableEnvironment environment,
		String[] args) {
	configurePropertySources(environment, args);
	configureProfiles(environment, args);
}
```

这里分为以下两步来配置应用环境。

- 配置 property sources
- 配置 Profiles

这里主要处理所有 property sources 配置和 profiles 配置。

#### 7、创建 Banner 打印类

```
Banner printedBanner = printBanner(environment);
```

这是用来打印 Banner 的处理类，这个没什么好说的。

#### 8、创建应用上下文

```
context = createApplicationContext();
```

来看下 `createApplicationContext()` 方法的源码：

```
protected ConfigurableApplicationContext createApplicationContext() {
	Class<?> contextClass = this.applicationContextClass;
	if (contextClass == null) {
		try {
			switch (this.webApplicationType) {
			case SERVLET:
				contextClass = Class.forName(DEFAULT_WEB_CONTEXT_CLASS);
				break;
			case REACTIVE:
				contextClass = Class.forName(DEFAULT_REACTIVE_WEB_CONTEXT_CLASS);
				break;
			default:
				contextClass = Class.forName(DEFAULT_CONTEXT_CLASS);
			}
		}
		catch (ClassNotFoundException ex) {
			throw new IllegalStateException(
					"Unable create a default ApplicationContext, "
							+ "please specify an ApplicationContextClass",
					ex);
		}
	}
	return (ConfigurableApplicationContext) BeanUtils.instantiateClass(contextClass);
}
```

其实就是根据不同的应用类型初始化不同的上下文应用类。

#### 9、准备异常报告器

```
exceptionReporters = getSpringFactoriesInstances(
		SpringBootExceptionReporter.class,
		new Class[] { ConfigurableApplicationContext.class }, context);
```

逻辑和之前实例化初始化器和监听器的一样，一样调用的是 `getSpringFactoriesInstances` 方法来获取配置的异常类名称并实例化所有的异常处理类。

该异常报告处理类配置在 `spring-boot-2.0.3.RELEASE.jar!/META-INF/spring.factories` 这个配置文件里面。

```
# Error Reporters
org.springframework.boot.SpringBootExceptionReporter=\
org.springframework.boot.diagnostics.FailureAnalyzers
```
		
#### 10、准备应用上下文

```
prepareContext(context, environment, listeners, applicationArguments,
		printedBanner);
```

来看下 `prepareContext()` 方法的源码：

```
private void prepareContext(ConfigurableApplicationContext context,
		ConfigurableEnvironment environment, SpringApplicationRunListeners listeners,
		ApplicationArguments applicationArguments, Banner printedBanner) {
	// 10.1）绑定环境到上下文
	context.setEnvironment(environment);
	
	// 10.2）配置上下文的 bean 生成器及资源加载器
	postProcessApplicationContext(context);
	
	// 10.3）为上下文应用所有初始化器
	applyInitializers(context);
	
	// 10.4）触发所有 SpringApplicationRunListener 监听器的 contextPrepared 事件方法
	listeners.contextPrepared(context);
	
	// 10.5）记录启动日志
	if (this.logStartupInfo) {
		logStartupInfo(context.getParent() == null);
		logStartupProfileInfo(context);
	}

	// 10.6）注册两个特殊的单例bean
	context.getBeanFactory().registerSingleton("springApplicationArguments",
			applicationArguments);
	if (printedBanner != null) {
		context.getBeanFactory().registerSingleton("springBootBanner", printedBanner);
	}

	// 10.7）加载所有资源
	Set<Object> sources = getAllSources();
	Assert.notEmpty(sources, "Sources must not be empty");
	load(context, sources.toArray(new Object[0]));
	
	// 10.8）触发所有 SpringApplicationRunListener 监听器的 contextLoaded 事件方法
	listeners.contextLoaded(context);
}
```

		
#### 11、刷新应用上下文

```
refreshContext(context);
```

这个主要是刷新 Spring 的应用上下文，源码如下，不详细说明。

```
private void refreshContext(ConfigurableApplicationContext context) {
	refresh(context);
	if (this.registerShutdownHook) {
		try {
			context.registerShutdownHook();
		}
		catch (AccessControlException ex) {
			// Not allowed in some environments.
		}
	}
}
```

#### 12、应用上下文刷新后置处理

```
afterRefresh(context, applicationArguments);
```

看了下这个方法的源码是空的，目前可以做一些自定义的后置处理操作。

```
/**
 * Called after the context has been refreshed.
 * @param context the application context
 * @param args the application arguments
 */
protected void afterRefresh(ConfigurableApplicationContext context,
		ApplicationArguments args) {
}
```

#### 13、停止计时监控类

```
stopWatch.stop();
```

```
public void stop() throws IllegalStateException {
	if (this.currentTaskName == null) {
		throw new IllegalStateException("Can't stop StopWatch: it's not running");
	}
	long lastTime = System.currentTimeMillis() - this.startTimeMillis;
	this.totalTimeMillis += lastTime;
	this.lastTaskInfo = new TaskInfo(this.currentTaskName, lastTime);
	if (this.keepTaskList) {
		this.taskList.add(this.lastTaskInfo);
	}
	++this.taskCount;
	this.currentTaskName = null;
}
```

计时监听器停止，并统计一些任务执行信息。

#### 14、输出日志记录执行主类名、时间信息

```
if (this.logStartupInfo) {
	new StartupInfoLogger(this.mainApplicationClass)
			.logStarted(getApplicationLog(), stopWatch);
}
```

#### 15、发布应用上下文启动完成事件

```
listeners.started(context);
```

触发所有 SpringApplicationRunListener 监听器的 started 事件方法。


#### 16、执行所有 Runner 运行器

```
callRunners(context, applicationArguments);
```

```
private void callRunners(ApplicationContext context, ApplicationArguments args) {
	List<Object> runners = new ArrayList<>();
	runners.addAll(context.getBeansOfType(ApplicationRunner.class).values());
	runners.addAll(context.getBeansOfType(CommandLineRunner.class).values());
	AnnotationAwareOrderComparator.sort(runners);
	for (Object runner : new LinkedHashSet<>(runners)) {
		if (runner instanceof ApplicationRunner) {
			callRunner((ApplicationRunner) runner, args);
		}
		if (runner instanceof CommandLineRunner) {
			callRunner((CommandLineRunner) runner, args);
		}
	}
}
```

执行所有 `ApplicationRunner` 和 `CommandLineRunner` 这两种运行器，不详细展开了。


#### 17、发布应用上下文就绪事件

```
listeners.running(context);
```

触发所有 SpringApplicationRunListener 监听器的 running 事件方法。

#### 18、返回应用上下文

```
return context;
```

### 总结

Spring Boot 的启动全过程源码分析至此，分析 Spring 源码真是一个痛苦的过程，希望能给大家提供一点参考和思路，也希望能给正在 Spring Boot 学习路上的朋友一点收获。

源码分析不易，点赞 + 转发支持一下吧！