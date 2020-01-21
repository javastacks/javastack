本章讲解SpringMVC中怎么通过注解对表单参数进行验证。

## SpringBoot配置

使用springboot，`spring-boot-starter-web`会自动引入`hiberante-validator`,`validation-api`依赖。

在`WebMvcConfigurerAdapter`实现类里面添加验证器及国际化指定资源文件。

```
@Override
public Validator getValidator() {
	LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
	validator.setValidationMessageSource(messageSource());
	return validator;
}

@Bean
public MessageSource messageSource() {
	ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
	messageSource.setBasename("i18n/ValidationMessages");
	messageSource.setDefaultEncoding(StandardCharsets.US_ASCII.name());
	return messageSource;
}
```

## 验证器使用

接收参数的表单类：

```
public class LoginForm {

	@NotNull(message = "{login.loginName.length}")
	@Size(min = 4, max = 20, message = "{login.loginName.length}")
	private String loginName;

	@NotNull(message = "{login.loginPassword.length}")
	@Size(min = 8, max = 20, message = "{login.loginPassword.length}")
	private String loginPassword;

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getLoginPassword() {
		return loginPassword;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}

}
```

在要验证的字段上面加入验证注解，更多参考`validation-api`jar包下的`javax.validation.constraints`包。

SpringMVC控制器：

```
@PostMapping(value = "/login")
public String login(@Validated LoginForm form) {
...
}
```

使用`@Validated`注解表示该参数需要验证。


## 国际化

message里面`{}`引用的是国际化的资源。

添加国际化资源文件：

ValidationMessages.properties
ValidationMessages_zh_CN.properties

需要指定编码为`ASCII`，不然会乱码。

## 异常处理

异常全局处理时进行处理：

```
@ControllerAdvice
public class ExceptionResolver {

	@ExceptionHandler(Throwable.class)
	@ResponseBody
	Object handleEntityException(HttpServletRequest request, Throwable ex) {
	    ...
		if (ex instanceof BindException) {
			BindException c = (BindException) ex;
			List<ObjectError> errors = c.getBindingResult().getAllErrors();
			StringBuilder errorMsg = new StringBuilder();
			errors.forEach(x -> errorMsg.append(x.getDefaultMessage()).append(","));
			
		}
		...
	}

}
```

或者在验证的类后面加`BindingResult`，错误信息都会放在该对象里面，而不会抛出异常。如：

```
@PostMapping(value = "/login")
public String login(@Validated LoginForm form, BindingResult bindingResult) {
...
}
```