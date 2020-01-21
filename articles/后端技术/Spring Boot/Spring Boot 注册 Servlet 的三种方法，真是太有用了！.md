本文栈长教你如何在 Spring Boot 注册 Servlet、Filter、Listener。

## 你所需具备的基础

- [什么是 Spring Boot?](https://mp.weixin.qq.com/s/jWLcPxTg9bH3D9_7qbYbfw)
- [Spring Boot 核心配置文件详解](https://mp.weixin.qq.com/s/BzXNfBzq-2TOCbiHG3xcsQ)
- [Spring Boot 开启的 2 种方式](https://mp.weixin.qq.com/s/PYM_iV-u3dPMpP3MNz7Hig)
- [Spring Boot 自动配置原理、实战](https://mp.weixin.qq.com/s/gs2zLSH6m9ijO0-pP2sr9Q)
- [Spring Boot 2.x 启动全过程源码分析](https://mp.weixin.qq.com/s/iMPXjuKRKT5lMZ4oVSp4Ww)

更多请在Java技术栈微信公众号后台回复关键字：boot。

## 一、Spring Boot 注册

Spring Boot 提供了 `ServletRegistrationBean`, `FilterRegistrationBean`, `ServletListenerRegistrationBean` 三个类分别用来注册 Servlet, Filter, Listener，下面是 Servlet 的示例代码。

```
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Java技术栈
 */
public class RegisterServlet extends HttpServlet {

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String name = getServletConfig().getInitParameter("name");
		String sex = getServletConfig().getInitParameter("sex");

		resp.getOutputStream().println("name is " + name);
		resp.getOutputStream().println("sex is " + sex);
	}

}

@Bean
public ServletRegistrationBean registerServlet() {
	ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(
			new RegisterServlet(), "/registerServlet");
	servletRegistrationBean.addInitParameter("name", "javastack");
	servletRegistrationBean.addInitParameter("sex", "man");
	return servletRegistrationBean;
}
```

## 二、组件扫描注册

Servlet 3.0 之前，Servlet、Filter、Listener 这些组件都需要在 `web.xml` 中进行配置，3.0 之后开始不再需要 `web.xml` 这个配置文件了，所有的组件都可以通过代码配置或者注解来达到目的。

如下图所示，截图自 Servlet 3.1。

![](http://img.javastack.cn/18-8-28/48900950.jpg)

Servlet 3.0 开始提供了这 3 个注解来代替。

**@WebServlet** => 代替 servlet 配置

**@WebFilter** => 代替 filter 配置

**@WebListener** => 代替 listener 配置

#### 配置 Servlet 示例

```
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Java技术栈
 */
@WebServlet(name = "javaServlet", urlPatterns = "/javastack.cn", asyncSupported = true,
		initParams = {
		@WebInitParam(name = "name", value = "javastack"),
		@WebInitParam(name = "sex", value = "man") })
public class JavaServlet extends HttpServlet {

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String name = getServletConfig().getInitParameter("name");
		String sex = getServletConfig().getInitParameter("sex");

		resp.getOutputStream().println("name is " + name);
		resp.getOutputStream().println("sex is " + sex);
	}

}
```

#### 配置 Filter 示例

```
/**
 * @author Java技术栈
 */
@WebFilter(filterName = "javaFilter", urlPatterns = "/*", initParams = {
		@WebInitParam(name = "name", value = "javastack"),
		@WebInitParam(name = "code", value = "123456") })
public class JavaFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("java filter init.");
		String name = filterConfig.getInitParameter("name");
		String code = filterConfig.getInitParameter("code");
		System.out.println("name is " + name);
		System.out.println("code is " + code);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("java filter processing.");
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		System.out.println("java filter destroy.");
	}

}
```

Listener 配置方式类似，上面的示例代码一看就懂，这里不再详述。

需要注意的是，为了安全考虑，内嵌服务器不会直接执行 Servlet 3.0 里面的 `javax.servlet.ServletContainerInitializer` 接口，或者 Spring 中的 `org.springframework.web.WebApplicationInitializer` 接口，否则会导致终止 Spring Boot 应用。

所以，如果使用的是 Spring Boot 内嵌服务器，需要在配置类上面添加额外的 `@ServletComponentScan` 注解来开启 Servlet 组件扫描功能，如果使用的是独立的服务器，则不需要添加，会使用服务器内部的自动发现机制。

## 三、动态注册

如果你想在 Spring Boot 中完成 Servlet、Filter、Listener 的初始化操作，你需要在 Spring 中实现下面这个接口，并注册为一个 bean。

> org.springframework.boot.web.servlet.ServletContextInitializer

ServletContext 提供了几个动态注册的方法，如下所示。

![](http://img.javastack.cn/18-8-28/86108280.jpg)

以下为动态添加 Servlet 示例代码。

```
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Java技术栈
 */
@WebServlet(name = "javaServlet", urlPatterns = "/javastack.cn", asyncSupported = true,
		initParams = {
		@WebInitParam(name = "name", value = "javastack"),
		@WebInitParam(name = "sex", value = "man") })
public class JavaServlet extends HttpServlet {

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String name = getServletConfig().getInitParameter("name");
		String sex = getServletConfig().getInitParameter("sex");

		resp.getOutputStream().println("name is " + name);
		resp.getOutputStream().println("sex is " + sex);
	}

}

import cn.javastack.springbootbestpractice.servlet.InitServlet;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

/**
 * @author Java技术栈
 */
@Component
public class ServletConfig implements ServletContextInitializer {

	@Override
	public void onStartup(ServletContext servletContext) {
		ServletRegistration initServlet = servletContext
				.addServlet("initServlet", InitServlet.class);
		initServlet.addMapping("/initServlet");
		initServlet.setInitParameter("name", "javastack");
		initServlet.setInitParameter("sex", "man");
	}

}

```

## 总结

本文介绍了在 Spring Boot 下的 3 种注册 Servlet、Filter、Listener 的方式，大家灵活运用。

看完有收获，点赞转发一下吧！

好了，今天的分享就到这里，更多 Spring Boot 文章正在撰写中，关注Java技术栈微信公众号获取第一时间推送。在公众号后台回复：boot，还能获取栈长整理的往期 Spring Boot 教程，都是实战干货，以下仅为部分预览。

- Spring Boot 读取配置的几种方式
- Spring Boot 如何做参数校验？
- Spring Boot 最核心的 25 个注解！
- Spring Boot 2.x 启动全过程源码分析
- Spring Boot 2.x 新特性总结及迁移指南
- ……

![](http://img.javastack.cn/wx_search_javastack.png)