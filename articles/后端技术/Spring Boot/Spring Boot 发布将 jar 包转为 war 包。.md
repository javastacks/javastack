
![](http://qianniu.javastack.cn/18-2-27/88945925.jpg)

Spring Boot是支持发布jar包和war的，但它推荐的是使用jar形式发布。使用jar包比较方便，但如果是频繁修改更新的项目，需要打补丁包，那这么大的jar包上传都是问题。所以，jar包不一定合适适用所有的场景，如war包更合适，你可以尝试转为传统的war包，这样打补丁包可能更方便。

#### 如何配置打war包呢？

以下配置以Maven为说明讲解，Gradle的查看类似配置。

**1、修改Spring Boot启动类**

启动类继承`SpringBootServletInitializer`类，并覆盖`configure`方法。

下面是Spring Boot提供的示例代码。


```
@SpringBootApplication
public class Application extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
	}

}
```

**2、修改jar为war包形式**

在pom文件中，添加war包配置。


```
<packaging>war</packaging>
```

如果不添加，默认为jar包。

**3、去除Spring Boot内置Tomcat**

修改自带tomcat依赖范围为provided，防止与外部tomcat发生冲突。


```
<dependencies>
	<!-- … -->
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-tomcat</artifactId>
		<scope>provided</scope>
	</dependency>
	<!-- … -->
</dependencies>
```


**4、添加war包打包插件**

如果你用的是继承spring-boot-starter-parent的形式使用Spring Boot，那可以跳过，因为它已经帮你配置好了。如果你使用的依赖spring-boot-dependencies形式，你需要添加以下插件。

```
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-war-plugin</artifactId>
    <configuration>
        <failOnMissingWebXml>false</failOnMissingWebXml>
    </configuration>
</plugin>
```

> failOnMissingWebXml需要开启为false，不然打包会报没有web.xml错误。

#### 如何打war包？

打war包方式和打jar包方式一样，没有区别。

- 在Maven中使用`mvn clean package`命令即可打包。

- 在Idea中可以这样设置打包：

![](http://qianniu.javastack.cn/18-2-8/28070341.jpg)

改在war包后，发现打war包比打jar要上好多。

#### jar包转war包有什么影响？

1、application配置文件中的server.xx等关于容器的配置就无效了，改配置需要在外部tomcat中进行。

2、Spring Boot的升级是否需要Tomcat跟着升级？需要观察。

3、打war包比打jar明显要变慢好多。。


暂时还没遇到其他问题，大家对此有什么看法都可以在下方留言。

