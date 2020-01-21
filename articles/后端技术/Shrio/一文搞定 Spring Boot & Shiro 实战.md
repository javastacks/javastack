
Spring Boot集成Shiro权限验证框架，可参考：

> https://shiro.apache.org/spring-boot.html

## 引入依赖


```
<dependency>
    <groupId>org.apache.shiro</groupId>
    <artifactId>shiro-spring-boot-web-starter</artifactId>
    <version>1.4.0</version>
</dependency>
```

## 配置Shiro

**ShiroConfig**：

```
@ConfigurationProperties(prefix = "shiro")
@Configuration
public class ShiroConfig {

	@Autowired
	private ApplicationConfig applicationConfig;

	private List<String> pathDefinitions;

	@Bean
	public ShiroFilterChainDefinition shiroFilterChainDefinition() {
		DefaultShiroFilterChainDefinition chainDefinition = new
				DefaultShiroFilterChainDefinition();

		applicationConfig.getStaticDirs()
				.forEach(s -> chainDefinition.addPathDefinition(s, "anon"));
		this.getPathDefinitions().forEach(d -> {
			String[] defArr = d.split("=");
			chainDefinition
					.addPathDefinition(StringUtils.trim(defArr[0]), StringUtils.trim(defArr[1]));
		});

		return chainDefinition;
	}

	@Bean
	public Realm systemRealm() {
		SystemRealm systemRealm = new SystemRealm();
		return systemRealm;
	}

	public List<String> getPathDefinitions() {
		return pathDefinitions;
	}

	public void setPathDefinitions(List<String> pathDefinitions) {
		this.pathDefinitions = pathDefinitions;
	}

}
```

**ApplicationConfig**：注入的是application.yml中的配置，略。

**SystemRealm：**

```
public class SystemRealm extends AuthorizingRealm {

	@Autowired
	private SysAdminMapper sysAdminMapper;

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
			throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
		token.setPassword(EcryptUtils.encode(String.valueOf(token.getPassword())).toCharArray
				());

		SysAdminDO sysAdminParams = new SysAdminDO();
		sysAdminParams.setAdminLoginName(token.getUsername());
		SysAdminDO sysAdminDO = sysAdminMapper.selectByParams(sysAdminParams);

		AuthenticationInfo authInfo = null;
		if (sysAdminDO != null) {
			authInfo = new SimpleAuthenticationInfo(sysAdminDO, sysAdminDO.getAdminLoginPass(),
					getName());
		}
		return authInfo;
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		/**
		 * 下面为静态示例，根据用户对应权限进行修改
		 * 根据用户查询对应的角色、权限
		 */
		SysAdminDO sysAdminDO = (SysAdminDO) super.getAvailablePrincipal(principalCollection);
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

		Set<String> roles = new HashSet<>();
		roles.addAll(Arrays.asList("product", "operation"));
		authorizationInfo.setRoles(roles);

		Set<String> permissions = new HashSet<>();
		permissions.addAll(Arrays.asList("product:create", "product:del", "operation:update"));
		authorizationInfo.addStringPermissions(permissions);

		return authorizationInfo;
	}

}
```



## 应用配置

application.yml中加入Shiro配置。


```
shiro:
  loginUrl: /login
  successUrl: /
  unauthorizedUrl: /error
  pathDefinitions:
    - /login/submit = anon
    - /logout = logout
    - /test = authc, roles[product], perms[operation:update]
    - /** = authc
```

**loginUrl**：没有认证的将会跳到登录页面。

**successUrl**：认证成功跳转的页面。

**unauthorizedUrl**：认证失败跳转的页面。

**pathDefinitions**：用来定义路径授权规则。


更多参数参考官网定义：

> https://shiro.apache.org/spring-boot.html#configuration-properties

## 登录服务类


```
@Override
public SysAdminDO login(LoginForm form) {
	UsernamePasswordToken token = new UsernamePasswordToken(form.getLoginName(),
			form.getLoginPassword());
	token.setRememberMe(true);
	Subject currentUser = getSubject();
	try {
		currentUser.login(token);
	} catch (Exception e) {
		logger.error("登录验证失败：", e);
	}
	return (SysAdminDO) currentUser.getPrincipal();
}
```

## 自带的过滤器

anno, authc等更多定义参考类：

```
org.apache.shiro.web.filter.mgt.DefaultFilter
```

官网定义：

> http://shiro.apache.org/web.html#default-filters
