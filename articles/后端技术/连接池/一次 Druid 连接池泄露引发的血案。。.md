最近某个应用程序老是卡，需要重启才能解决问题，导致被各种投诉，排查问题是 Druid 连接池泄露引发的血案。。

**异常日志如下：**

```
ERROR - com.alibaba.druid.pool.GetConnectionTimeoutException: wait millis 60000, active 50, maxActive 50, creating 0
	at com.alibaba.druid.pool.DruidDataSource.getConnectionInternal(DruidDataSource.java:1512)
	at com.alibaba.druid.pool.DruidDataSource.getConnectionDirect(DruidDataSource.java:1255)
	at com.alibaba.druid.filter.FilterChainImpl.dataSource_connect(FilterChainImpl.java:5007)
	at com.alibaba.druid.filter.stat.StatFilter.dataSource_getConnection(StatFilter.java:680)
	at com.alibaba.druid.filter.FilterChainImpl.dataSource_connect(FilterChainImpl.java:5003)
	at com.alibaba.druid.pool.DruidDataSource.getConnection(DruidDataSource.java:1233)
	at com.alibaba.druid.pool.DruidDataSource.getConnection(DruidDataSource.java:1225)
	at com.alibaba.druid.pool.DruidDataSource.getConnection(DruidDataSource.java:90)
```

连接池中的连接数量已经到达最大值了，到了 60 秒还不能创建连接就超时报错了。

问题很明显，肯定是应用程序哪里用了连接但没有释放，全局排查代码又比较艰难，加了三个 Druid 连接池配置项，最终定位并解决了问题。

**添加以下配置：**

```
<!-- 是否打开强制回收连接功能 -->
<property name="removeAbandoned" value="true" />

<!-- 超时时间，单位：毫秒 -->
<property name="removeAbandonedTimeoutMillis" value="600000"/>

<!-- 连接回收时是否记录日志 -->
<property name="logAbandoned" value="true" />
```

这就是连接泄露配置项，如果连接长时间不归还，`removeAbandoned` 打开状态，超过 `removeAbandonedTimeoutMillis` 设置的超时时间，将会强制回收连接。

连接池初始化时会启动一个线程，用于检查并回收连接。

参考源码：
> com.alibaba.druid.pool.DruidDataSource#createAndStartDestroyThread

当 `logAbandoned` 打开状态时，关闭连接同时会记录当时的堆栈日志，可用于定位到哪些代码打开了连接没关闭。

```
abandon connection, owner thread: https-jsse-nio-4443-exec-9, connected at : 1573521883837, open stackTrace
	at java.lang.Thread.getStackTrace(Thread.java:1589)
	at com.alibaba.druid.pool.DruidDataSource.getConnectionDirect(DruidDataSource.java:1305)
	at com.alibaba.druid.filter.FilterChainImpl.dataSource_connect(FilterChainImpl.java:4619)
	at com.alibaba.druid.filter.stat.StatFilter.dataSource_getConnection(StatFilter.java:680)
	at com.alibaba.druid.filter.FilterChainImpl.dataSource_connect(FilterChainImpl.java:4615)
	at com.alibaba.druid.pool.DruidDataSource.getConnection(DruidDataSource.java:1225)
	at com.alibaba.druid.pool.DruidDataSource.getConnection(DruidDataSource.java:1217)
	at com.alibaba.druid.pool.DruidDataSource.getConnection(DruidDataSource.java:90)
	at org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource.getConnection(AbstractRoutingDataSource.java:162)
	...
```

这个配置项虽然可用于排查连接池释放问题，但生产环境小心使用，如果有的业务执行事务的时间比较长，会被误回收，小心引发另外一个血案。

