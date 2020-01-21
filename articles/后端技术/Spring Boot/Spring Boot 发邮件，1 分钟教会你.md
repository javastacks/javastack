Spring Boot 提供了一个发送邮件的简单抽象，使用的是下面这个接口。

> org.springframework.mail.javamail.JavaMailSender

Spring Boot 提供了一个 `starter`，并能自动配置，下面来做个小例子，顺便解析它做了什么工作。

### 0、你所需具备的基础

- [什么是 Spring Boot?](https://mp.weixin.qq.com/s/jWLcPxTg9bH3D9_7qbYbfw)
- [Spring Boot 核心配置文件详解](https://mp.weixin.qq.com/s/BzXNfBzq-2TOCbiHG3xcsQ)
- [Spring Boot 开启的 2 种方式](https://mp.weixin.qq.com/s/PYM_iV-u3dPMpP3MNz7Hig)
- [Spring Boot 自动配置原理、实战](https://mp.weixin.qq.com/s/gs2zLSH6m9ijO0-pP2sr9Q)
- [Spring Boot 2.x 启动全过程源码分析](https://mp.weixin.qq.com/s/iMPXjuKRKT5lMZ4oVSp4Ww)

更多请在Java技术栈微信公众号后台回复关键字：boot。

### 1、添加依赖

在 Maven `pom.xml` 配置文件中加入 `spring-boot-starter-mail` 依赖。

```
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-mail</artifactId>
</dependency>
```

### 2、添加配置参数

然后在 `application.properties` 文件中加入以下配置。

```
spring.mail.host=smtp.exmail.qq.com
spring.mail.username=admin@javastack.cn
spring.mail.password=123456

# 启动SSL时的配置
spring.mail.smtp.socketFactory.class=javax.net.ssl.SSLSocketFactory
spring.mail.smtp.socketFactory.fallback=false
spring.mail.smtp.socketFactory.port=465
```

### 3、一个简单的发送邮件例子

写一个控制器，写一个简单的发送邮件的小例子，发送成功返回 `true`，发送失败返回 `false`。

```
@Autowired
private JavaMailSender javaMailSender;

@RequestMapping("/sendEmail")
@ResponseBody
public boolean sendEmail() {
	SimpleMailMessage msg = new SimpleMailMessage();
	msg.setFrom("admin@javastack.cn");
	msg.setBcc();
	msg.setTo("admin@javastack.cn");
	msg.setSubject("Java技术栈投稿");
	msg.setText("技术分享");
	try {
		javaMailSender.send(msg);
	} catch (MailException ex) {
		System.err.println(ex.getMessage());
		return false;
	}
	return true;
}
```

### 4、自动配置都做了什么？

Spring Boot 发现类路径下有这个 `spring-boot-starter-mail` 包和 `spring.mail.host` 参数就会自动配置 `JavaMailSenderImpl`。

上面那些 `spring.mail.xx` 参数用来装配 `MailProperties` 这个类。

> org.springframework.boot.autoconfigure.mail.MailProperties

自动配置类：

> org.springframework.boot.autoconfigure.mail.MailSenderAutoConfiguration

![](http://img.javastack.cn/18-8-27/70236436.jpg)

> org.springframework.boot.autoconfigure.mail.MailSenderPropertiesConfiguration

![](http://img.javastack.cn/18-8-27/56485452.jpg)

其实就是用了上面装配的参数注册了一个 `JavaMailSenderImpl` 实例而已，然后你就可以注入使用了。

> 本文原创首发于微信公众号：Java技术栈（id:javastack），关注公众号在后台回复 "boot" 可获取更多，转载请原样保留本信息。