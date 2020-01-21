
#### 添加配置信息

```
spring.redis:
  database: 0 # Redis数据库索引（默认为0）
  #host: 192.168.1.8
  #port: 6379
  password: 123456
  timeout: 10000 # 连接超时时间（毫秒）  
  pool: 
    max-active: 8 # 连接池最大连接数（使用负值表示没有限制）
    max-idle: 8 # 连接池中的最大空闲连接
    max-wait: -1 # 连接池最大阻塞等待时间（使用负值表示没有限制）
    min-idle: 0 # 连接池中的最小空闲连接
  cluster:
    nodes:
      - 192.168.1.8:9001
      - 192.168.1.8:9002
      - 192.168.1.8:9003
```

只需要添加3个master节点，3个slave节点不需要添加。

你要做的也只有这些配置了，其他的spring boot都自动配置好了。

现在就可以像使用单机一样使用集群，redis会自动按key分片到不同的集群实例。

#### 遇到的问题


```
Caused by: redis.clients.jedis.exceptions.JedisConnectionException: Could not get a resource from the pool
    at redis.clients.util.Pool.getResource(Pool.java:53)
    at redis.clients.jedis.JedisPool.getResource(JedisPool.java:226)
    at redis.clients.jedis.JedisSlotBasedConnectionHandler.getConnectionFromSlot(JedisSlotBasedConnectionHandler.java:66)
    at redis.clients.jedis.JedisClusterCommand.runWithRetries(JedisClusterCommand.java:116)
    at redis.clients.jedis.JedisClusterCommand.runWithRetries(JedisClusterCommand.java:141)
    at redis.clients.jedis.JedisClusterCommand.runWithRetries(JedisClusterCommand.java:141)
    at redis.clients.jedis.JedisClusterCommand.runWithRetries(JedisClusterCommand.java:141)
    at redis.clients.jedis.JedisClusterCommand.runWithRetries(JedisClusterCommand.java:141)
    at redis.clients.jedis.JedisClusterCommand.runBinary(JedisClusterCommand.java:60)
    at redis.clients.jedis.BinaryJedisCluster.set(BinaryJedisCluster.java:77)
    at org.springframework.data.redis.connection.jedis.JedisClusterConnection.set(JedisClusterConnection.java:618)
    ... 36 more
Caused by: redis.clients.jedis.exceptions.JedisConnectionException: java.net.ConnectException: Connection refused: connect
    at redis.clients.jedis.Connection.connect(Connection.java:207)
    at redis.clients.jedis.BinaryClient.connect(BinaryClient.java:93)
    at redis.clients.jedis.BinaryJedis.connect(BinaryJedis.java:1767)
    at redis.clients.jedis.JedisFactory.makeObject(JedisFactory.java:106)
    at org.apache.commons.pool2.impl.GenericObjectPool.create(GenericObjectPool.java:868)
    at org.apache.commons.pool2.impl.GenericObjectPool.borrowObject(GenericObjectPool.java:435)
    at org.apache.commons.pool2.impl.GenericObjectPool.borrowObject(GenericObjectPool.java:363)
    at redis.clients.util.Pool.getResource(Pool.java:49)
    ... 46 more
Caused by: java.net.ConnectException: Connection refused: connect
    at java.net.DualStackPlainSocketImpl.waitForConnect(Native Method)
    at java.net.DualStackPlainSocketImpl.socketConnect(DualStackPlainSocketImpl.java:85)
    at java.net.AbstractPlainSocketImpl.doConnect(AbstractPlainSocketImpl.java:350)
    at java.net.AbstractPlainSocketImpl.connectToAddress(AbstractPlainSocketImpl.java:206)
    at java.net.AbstractPlainSocketImpl.connect(AbstractPlainSocketImpl.java:188)
    at java.net.PlainSocketImpl.connect(PlainSocketImpl.java:172)
    at java.net.SocksSocketImpl.connect(SocksSocketImpl.java:392)
    at java.net.Socket.connect(Socket.java:589)
    at redis.clients.jedis.Connection.connect(Connection.java:184)
    ... 53 more
```

尝试往redis写数据的时候，报不能获取连接异常，跟踪了半天代码，发现连接的是127.0.0.1，而不是配置的192.168.1.8，这就奇怪了，继续跟踪代码发现是往redis服务器获取的返回的集群实例列表，真是坑！

![image](https://i.stack.imgur.com/aqRpc.png)

源码：

> redis.clients.jedis.Jedis#clusterSlots

```
@Override
public List<Object> clusterSlots() {
    checkIsInMultiOrPipeline();
    client.clusterSlots();
    return client.getObjectMultiBulkReply();
}
```

就是这里获取返回的集群列表，返回的就是127.0.0.1，而不是配置的192.168.1.8。

最后修改各个集群节点的配置文件redis.conf，添加：

```
bind 192.168.1.8
```

重启集群节点之后，读写正常。

