利用Spring MVC的过滤器及token传递验证来实现表单防重复提交。

## 创建注解

```
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Token {

	boolean create() default false;

	boolean remove() default false;

}
```

在跳转页面的方法上加上：@Token(create = true)\
在提交的action方法上加上：@Token(remove = true)

## 创建过滤器

```
public class TokenInterceptor extends HandlerInterceptorAdapter {

	private Logger logger = Logger.getLogger(TokenInterceptor.class);

	private static final String TOKEN = "token";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws Exception {
		if (handler instanceof HandlerMethod) {
			Method method = ((HandlerMethod) handler).getMethod();
			Token annotation = method.getAnnotation(Token.class);
			if (annotation != null) {
				HttpSession session = request.getSession();

				// 创建token
				boolean create = annotation.create();
				if (create) {
					session.setAttribute(TOKEN, UUID.randomUUID().toString());
					return true;
				}

				// 删除token
				boolean remove = annotation.remove();
				if (remove) {
					if (isRepeatSubmit(request)) {
						logger.warn("表单不能重复提交:" + request.getRequestURL());
						return false;
					}
					session.removeAttribute(TOKEN);
				}
			}
		} else {
			return super.preHandle(request, response, handler);
		}
		return true;
	}

	private boolean isRepeatSubmit(HttpServletRequest request) {
		String token = (String) request.getSession().getAttribute(TOKEN);
		if (token == null) {
			return true;
		}

		String reqToken = request.getParameter(TOKEN);
		if (reqToken == null) {
			return true;
		}

		if (!token.equals(reqToken)) {
			return true;
		}

		return false;
	}

}
```

## 配置拦截器

```
<!--配置拦截器 -->  
<mvc:interceptors> 
    <mvc:interceptor>  
    	<mvc:mapping path="/**" /> 
    	<bean class="com.example.web.interceptor.TokenInterceptor"/>    
    </mvc:interceptor>
</mvc:interceptors>
```

## 表单添加token

```
<input type="hidden" id="token" name="token" value="$!{session.getAttribute('token')}"/>
```

在form表单里面添加token域，提交表单时需要传过去。