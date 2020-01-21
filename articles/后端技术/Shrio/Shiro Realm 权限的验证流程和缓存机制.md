
我们可以定义多个Realm权限类，继承AuthenticatingRealm。

如果是这样，那Shiro验证的策略和顺序是怎样的呢？

## 策略

通过查看源码，Shiro的Spring Boot自动配置是至少一个通过策略，即有一个权限类通过就判定有权限并通过。

自动配置类：

> org.apache.shiro.spring.config.web.autoconfigure.ShiroWebAutoConfiguration


```
@Bean
@ConditionalOnMissingBean
@Override
protected AuthenticationStrategy authenticationStrategy() {
    return super.authenticationStrategy();
}
```


```
protected AuthenticationStrategy authenticationStrategy() {
    return new AtLeastOneSuccessfulStrategy();
}
```

其他还有全部通过、首个通过，更多可以查看Shiro包下面的权限策略。

> org.apache.shiro.authc.pam

## 顺序

Shiro是按在Spring Boot配置类中定义Realm Bean的顺序进行验证权限的。

## 验证流程

假设现在有R1,R2权限类，现在我们对一个方法或者路径配置了A角色，B、C权限，Shiro会在R1中找A角色，找到则继续验证其他权限，找不到根据策略决定，如果说不是全部都要通过的策略则会继续在R2中找A角色，找不到则跳到指定的未授权链接，B、C权限验证流程也是一致。


## Shiro缓存

为了权限验证的效率性能，Shiro对认证和授权是有缓存开关控制的。

需要了解的权限类层次是，每个Realm都继承AuthorizingRealm，AuthorizingRealm继承自AuthenticatingRealm。AuthenticatingRealm是认证的逻辑，AuthorizingRealm是授权的逻辑。

通过查看AuthorizingRealm和AuthenticatingRealm源码，默认的认证缓存是关闭的，授权缓存是开启的。

> authorizationCachingEnabled = true; // 授权

> authenticationCachingEnabled = false; // 认证

这里默认开启了缓存还不行，还需要设置CacheManager，如下。

```
@Bean
public CacheManager cacheManager() {
	return new MemoryConstrainedCacheManager();
}
```

具体的缓存逻辑可以翻阅以下源码。

> org.apache.shiro.realm.AuthorizingRealm#getAuthorizationInfo

> org.apache.shiro.realm.AuthenticatingRealm#getAuthenticationInfo

## 设置缓存开关

有的时候我们的权限不是固定的，需要动态的调整授权，所以希望某些Realm不需要缓存。

我们可以在当前Realm中手动关闭某它的的授权缓存。

```
this.setAuthorizationCachingEnabled(false);
```

同上，默认关闭的认证的缓存也可以通过设置进行打开。

根据具体的业务进行灵活调整。
