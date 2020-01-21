
本章将讲解如何在Spring Boot和Thymeleaf中做页面模板国际化的支持，根据系统语言环境或者session中的语言来自动读取不同环境中的文字。

## 国际化自动配置

Spring Boot中已经对国际化这一块做了自动配置。

国际化自动配置类：

> org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration

查看自动配置源码有以下主要几个参数：

```
private String basename = "messages";

private Charset encoding = Charset.forName("UTF-8");

private int cacheSeconds = -1;

private boolean fallbackToSystemLocale = true;
```

`basename`：默认的扫描的国际化文件名为messages，即在resources建立messages_xx.properties文件，可以通过逗号指定多个，如果不指定包名默认从classpath下寻找。

`encoding`：默认的编码为UTF-8。

`cacheSeconds`：加载国际化文件的缓存时间，单位为秒，默认为永久缓存。

`fallbackToSystemLocale`：当找不到当前语言的资源文件时，如果为true默认找当前系统的语言对应的资源文件如messages_zh_CN.properties，如果为false即加载系统默认的如messages.properties文件。


## 国际化实战

1、国际化配置

```
spring:
    messages:
        fallbackToSystemLocale: false
        basename: i18n/common, i18n/login, i18n/index
```

2、在i18n目录下创建以下几个文件

如index.properties,index_zh_CN.properties，index.properties作为找不到定义语言的资源文件时的默认配置文件。

创建对应的key/value，如：

index_zh_CN.properties
```
index.welcome=欢迎
```

index.properties
```
index.welcome=welcome
```


3、添加语言解析器，并设置默认语言为US英文

LocaleResolver接口有许多实现，如可以从session、cookie、Accept-Language header、或者一个固定的值来判断当前的语言环境，下面是使用session来判断。

```
@Bean
public LocaleResolver localeResolver() {
	SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
	sessionLocaleResolver.setDefaultLocale(Locale.US);
	return sessionLocaleResolver;
}
```

4、添加切换语言过滤器

```
private LocaleChangeInterceptor localeChangeInterceptor() {
	LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
	localeChangeInterceptor.setParamName("lang");
	return localeChangeInterceptor;
}
```

添加以上过滤器并注册到spring mvc中


```
@Override
public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(localeChangeInterceptor());
}
```

然后页面通过访问指定的url?lang=zh_CN进行切换。

5、通过`#{}`来读取资源文件

如Thymeleaf模板文件中使用：

```
<label th:text="#{index.welcome}"></label>
```


默认会读取英文的资源文件并显示：welcome

