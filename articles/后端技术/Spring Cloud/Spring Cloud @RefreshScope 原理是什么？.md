
#### 要清楚RefreshScope,先要了解Scope

Scope(org.springframework.beans.factory.config.Scope)是Spring 2.0开始就有的核心的概念

RefreshScope(org.springframework.cloud.context.scope.refresh)是spring cloud提供的一种特殊的scope实现，用来实现配置、实例热加载。

Scope -> GenericScope -> RefreshScope

![](http://img.javastack.cn/20191214133725.png)

**Scope与ApplicationContext生命周期**

AbstractBeanFactory#doGetBean创建Bean实例

```
protected <T> T doGetBean(...){
    final RootBeanDefinition mbd = ...
    if (mbd.isSingleton()) {
        ...
    } else if (mbd.isPrototype())
       ...
    } else {
          String scopeName = mbd.getScope();
          final Scope scope = this.scopes.get(scopeName);
          Object scopedInstance = scope.get(beanName, new ObjectFactory<Object>() {...});
          ...
    }
    ...
}
```

Singleton和Prototype是硬编码的，并不是Scope子类。 

Scope实际上是自定义扩展的接口，Scope Bean实例交由Scope自己创建，例如SessionScope是从Session中获取实例的，ThreadScope是从ThreadLocal中获取的，而RefreshScope是在内建缓存中获取的。

**@Scope 对象的实例化**

@RefreshScope 是scopeName="refresh"的 @Scope

```
...
@Scope("refresh")
public @interface RefreshScope {
  ...
}
```
  
@Scope 的注册 AnnotatedBeanDefinitionReader#registerBean

```
public void registerBean(...){
...
ScopeMetadata scopeMetadata = this.scopeMetadataResolver.resolveScopeMetadata(abd);
  abd.setScope(scopeMetadata.getScopeName());
  ...
  definitionHolder = AnnotationConfigUtils.applyScopedProxyMode(scopeMetadata, definitionHolder, this.registry);
}
```
  
读取@Scope元数据， AnnotationScopeMetadataResolver#resolveScopeMetadata

```
public ScopeMetadata resolveScopeMetadata(BeanDefinition definition) {
  AnnotationAttributes attributes = AnnotationConfigUtils.attributesFor(
  annDef.getMetadata(), Scope.class);
  if (attributes != null) {
      metadata.setScopeName(attributes.getString("value"));
      ScopedProxyMode proxyMode = attributes.getEnum("proxyMode");
      if (proxyMode == null || proxyMode == ScopedProxyMode.DEFAULT) {
          proxyMode = this.defaultProxyMode;
      }
      metadata.setScopedProxyMode(proxyMode);
  }
}
```

Scope实例对象通过ScopedProxyFactoryBean创建，其中通过AOP使其实现ScopedObject接口，这里不再展开。

#### 说RefreshScope是如何实现配置和实例刷新的

**RefreshScope注册**

> RefreshAutoConfiguration#RefreshScopeConfiguration

```
@Component
@ConditionalOnMissingBean(RefreshScope.class)
protected static class RefreshScopeConfiguration implements BeanDefinitionRegistryPostProcessor{
...
  registry.registerBeanDefinition("refreshScope",
  BeanDefinitionBuilder.genericBeanDefinition(RefreshScope.class)
                      .setRole(BeanDefinition.ROLE_INFRASTRUCTURE)
                      .getBeanDefinition());
...
}
```

RefreshScope extends GenericScope, 大部分逻辑在 GenericScope 中。

GenericScope#postProcessBeanFactory 中向AbstractBeanFactory注册自己

```
public class GenericScope implements Scope, BeanFactoryPostProcessor...{
  @Override
  public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory)
      throws BeansException {
      beanFactory.registerScope(this.name/*refresh*/, this/*RefreshScope*/);
      ...
  }
}
```

**RefreshScope 刷新过程**

入口在ContextRefresher#refresh

```
refresh() {
  Map<String, Object> before = ①extract(
          this.context.getEnvironment().getPropertySources());
  ②addConfigFilesToEnvironment();
  Set<String> keys = ④changes(before,
          ③extract(this.context.getEnvironment().getPropertySources())).keySet();
  this.context.⑤publishEvent(new EnvironmentChangeEvent(keys));
  this.scope.⑥refreshAll();
 }
```

①提取标准参数(SYSTEM,JNDI,SERVLET)之外所有参数变量

②把原来的Environment里的参数放到一个新建的Spring Context容器下重新加载，完事之后关闭新容器

③提起更新过的参数(排除标准参数)

④比较出变更项

⑤发布环境变更事件,接收：EnvironmentChangeListener／LoggingRebinder

⑥RefreshScope用新的环境参数重新生成Bean，重新生成的过程很简单，清除refreshscope缓存幷销毁Bean，下次就会重新从BeanFactory获取一个新的实例（该实例使用新的配置）

RefreshScope#refreshAll

```
public void refreshAll() {
      <b>super.destroy();</b>
      this.context.publishEvent(new RefreshScopeRefreshedEvent());
}
```

GenericScope#destroy

```
public void destroy() {
  ...
  Collection<BeanLifecycleWrapper> wrappers = <b>this.cache.clear()</b>;
  for (BeanLifecycleWrapper wrapper : wrappers) {
      <b>wrapper.destroy();</b>
  }
}
```

**Spring Cloud Bus 如何触发 Refresh**

BusAutoConfiguration#BusRefreshConfiguration 发布一个RefreshBusEndpoint

```
@Configuration
@ConditionalOnClass({ Endpoint.class, RefreshScope.class })
protected static class BusRefreshConfiguration {

  @Configuration
  @ConditionalOnBean(ContextRefresher.class)
  @ConditionalOnProperty(value = "endpoints.spring.cloud.bus.refresh.enabled", matchIfMissing = true)
  protected static class BusRefreshEndpointConfiguration {
      @Bean
      public RefreshBusEndpoint refreshBusEndpoint(ApplicationContext context,
              BusProperties bus) {
          return new RefreshBusEndpoint(context, bus.getId());
      }
  }
}
```

RefreshBusEndpoint 会从http端口触发广播RefreshRemoteApplicationEvent事件

```
@Endpoint(id = "bus-refresh")
public class RefreshBusEndpoint extends AbstractBusEndpoint {
   public void busRefresh() {
      publish(new RefreshRemoteApplicationEvent(this, getInstanceId(), null));
  }
}
```

BusAutoConfiguration#refreshListener 负责接收事件(所有配置bus的节点)

```
@Bean
@ConditionalOnProperty(value = "spring.cloud.bus.refresh.enabled", matchIfMissing = true)
@ConditionalOnBean(ContextRefresher.class)
public RefreshListener refreshListener(ContextRefresher contextRefresher) {
  return new RefreshListener(contextRefresher);
}
```
RefreshListener#onApplicationEvent 触发 ContextRefresher

```
public void onApplicationEvent(RefreshRemoteApplicationEvent event) {
  Set<String> keys = contextRefresher.refresh();
}
```

**大部分需要更新的服务需要打上@RefreshScope, EurekaClient是如何配置更新的**

> EurekaClientAutoConfiguration#RefreshableEurekaClientConfiguration

```
@Configuration
@ConditionalOnRefreshScope
protected static class RefreshableEurekaClientConfiguration{
  @Bean
  @RefreshScope
  public EurekaClient eurekaClient(...) {
      return new CloudEurekaClient(manager, config, this.optionalArgs,
              this.context);
  }
  
  @Bean
  @RefreshScope
  public ApplicationInfoManager eurekaApplicationInfoManager(...) {
      ...
      return new ApplicationInfoManager(config, instanceInfo);
  }
  
}
```

> 作者：黄大海
https://www.jianshu.com/p/188013dd3d02

关注微信公众号：Java技术栈，Spring Cloud 干货第一时间推送。在公众号后台回复：cloud，还能获取栈长整理的 Spring Cloud 系列教程，都是实战干货。

![](http://img.javastack.cn/wx_search_javastack.png)
