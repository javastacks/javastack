
## Druid是什么

Druid是阿里开源的连接池，是Java语言中最好的数据库连接池.Druid能够提供强大的监控和扩展功能，是为监控而生的数据库连接池！

> GitHub：https://github.com/alibaba/druid/


## 添加依赖

```
<dependency>
	<groupId>com.alibaba</groupId>
	<artifactId>druid</artifactId>
	<version>1.1.2</version>
</dependency>
```

## 参考配置


```
<bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource" init-method="init" destroy-method="close"> 
      <!-- 基本属性 url、user、password -->
      <property name="url" value="${jdbc_url}" />
      <property name="username" value="${jdbc_user}" />
      <property name="password" value="${jdbc_password}" />
        
      <!-- 配置初始化大小、最小、最大 -->
      <property name="initialSize" value="1" />
      <property name="minIdle" value="1" /> 
      <property name="maxActive" value="20" />
   
      <!-- 配置获取连接等待超时的时间 -->
      <property name="maxWait" value="60000" />
   
      <!-- 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒 -->
      <property name="timeBetweenEvictionRunsMillis" value="60000" />
   
      <!-- 配置一个连接在池中最小生存的时间，单位是毫秒 -->
      <property name="minEvictableIdleTimeMillis" value="300000" />
    
      <property name="validationQuery" value="SELECT 'x'" />
      <property name="testWhileIdle" value="true" />
      <property name="testOnBorrow" value="false" />
      <property name="testOnReturn" value="false" />
   
      <!-- 打开PSCache，并且指定每个连接上PSCache的大小 -->
      <property name="poolPreparedStatements" value="true" />
      <property name="maxPoolPreparedStatementPerConnectionSize" value="20" />
   
      <!-- 配置监控统计拦截的filters -->
      <property name="filters" value="stat" /> 
</bean>
```

  
通常来说，只需要修改initialSize、minIdle、maxActive。

如果用Oracle，则把poolPreparedStatements配置为true，mysql可以配置为false。分库分表较多的数据库，建议配置为false。

## 连接池监控

在web.xml中加入以下配置：


```
<!-- Druid连接池监控 -->
<servlet>  
    <servlet-name>DruidStatView</servlet-name>  
    <servlet-class>com.alibaba.druid.support.http.StatViewServlet</servlet-class>
</servlet>  
<servlet-mapping>  
    <servlet-name>DruidStatView</servlet-name>  
    <url-pattern>/druid/*</url-pattern>  
</servlet-mapping>  
<filter>
	<filter-name>DruidWebStatFilter</filter-name>
	<filter-class>com.alibaba.druid.support.http.WebStatFilter</filter-class>
	<init-param>
		<param-name>exclusions</param-name>
		<param-value>*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*</param-value>
	</init-param>
</filter>
<filter-mapping>
	<filter-name>DruidWebStatFilter</filter-name>
	<url-pattern>/*</url-pattern>
</filter-mapping>
```

访问localhost:8080/project/druid即可打开监控页面。
