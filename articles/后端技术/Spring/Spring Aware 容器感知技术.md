## Spring Aware是什么

Spring提供Aware接口能让Bean感知Spring容器的存在，即让Bean可以使用Spring容器所提供的资源。

## Spring Aware的分类

几种常用的Aware接口如下。

Aware接口 | 说明
---|---
ApplicationContextAware | 能获取Application Context调用容器的服务
ApplicationEventPublisherAware | 应用事件发布器，可以用来发布事件
BeanClassLoaderAware | 能获取加载当前Bean的类加载器
BeanFactoryAware | 能获取Bean Factory调用容器的服务
BeanNameAware | 能获取当前Bean的名称
EnvironmentAware | 能获取当前容器的环境属性信息
MessageSourceAware | 能获取国际化文本信息
ResourceLoaderAware | 获取资源加载器读取资源文件
ServletConfigAware | 能获取到ServletConfig
ServletContextAware | 能获取到ServletContext

更多的可以看它的继承图。

## Spring Aware的使用

如要获取容器中的某个Bean，可以继承ApplicationContextAware，让这个Bean拥有调用容器服务的能力。

```
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringAppContext implements ApplicationContextAware {

	private static ApplicationContext applicationContext = null;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		if (SpringAppContext.applicationContext == null) {
			SpringAppContext.applicationContext = applicationContext;
		}
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public static Object getBean(String name) {
		return getApplicationContext().getBean(name);

	}

	public static <T> T getBean(Class<T> clazz) {
		return getApplicationContext().getBean(clazz);
	}

	public static <T> T getBean(String name, Class<T> clazz) {
		return getApplicationContext().getBean(name, clazz);
	}

}
```

欢迎大家转发到朋友圈，和朋友们一起提升自己。