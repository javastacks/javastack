
#### 什么是Redisson

Redisson是Redis官方推荐的一个高级的分布式协调Redis客服端，

Redisson在基于NIO的Netty框架上，充分的利用了Redis键值数据库提供的一系列优势，在Java实用工具包中常用接口的基础上，为使用者提供了一系列具有分布式特性的常用工具类。使得原本作为协调单机多线程并发程序的工具包获得了协调分布式多机多线程并发系统的能力，大大降低了设计和研发大规模分布式系统的难度。同时结合各富特色的分布式服务，更进一步简化了分布式环境中程序相互之间的协作。

兼容 Redis 2.6+ and JDK 1.6+，使用Apache License 2.0授权协议.

> 官网：https://redisson.org/\
> Github：https://github.com/redisson/redisson

#### 适用场景

分布式应用，缓存，分布式会话，分布式任务/服务/延迟执行服务，Redis客户端等。

#### 主要特性

云Redis管理、多样Redis配置支持、丰富连接方式、分布式对象、分布式集合、分布式锁、分布式服务、多种序列化方式、三方框架整合、完善工具等。

- 支持云托管服务模式（同时支持亚马逊云的ElastiCache Redis和微软云的Azure Redis Cache）:
1. 自动发现主节点变化

- 支持Redis集群模式（同时支持亚马逊云的ElastiCache Redis Cluster和微软云的Azure RedisCache）:
1. 自动发现主从节点
1. 自动更新状态和组态拓扑
1. 自动发现槽的变化

- 支持Redis哨兵模式:
1. 自动发现主、从和哨兵节点
1. 自动更新状态和组态拓扑

- 支持Redis主从模式

- 支持Redis单节模式

- 多节点模式均支持读写分离：从读主写，主读主写，主从混读主写

- 所有对象和接口均支持异步操作

- 自行管理的弹性异步连接池

- 所有操作线程安全

- 支持LUA脚本

- 提供分布式对象

通用对象桶（Object Bucket）、二进制流（Binary Stream）、地理空间对象桶（Geospatial Bucket）、BitSet、原子整长形（AtomicLong）、原子双精度浮点数（AtomicDouble）、话题（订阅分发）、布隆过滤器（Bloom Filter）和基数估计算法（HyperLogLog）

- 提供分布式集合
　　映射（Map）、多值映射（Multimap）、集（Set）、列表（List）、有序集（SortedSet）、计分排序集（ScoredSortedSet）、字典排序集（LexSortedSet）、列队（Queue）、双端队列（Deque）、阻塞队列（Blocking Queue）、有界阻塞列队（Bounded Blocking Queue）、阻塞双端列队（Blocking Deque）、阻塞公平列队（Blocking Fair Queue）、延迟列队（Delayed Queue）、优先队列（Priority Queue）和优先双端队列（Priority Deque）

- 提供分布式锁和同步器

可重入锁（Reentrant Lock）、公平锁（Fair Lock）、联锁（MultiLock）、红锁（RedLock）、读写锁（ReadWriteLock）、信号量（Semaphore）、可过期性信号量（PermitExpirableSemaphore）和闭锁（CountDownLatch）

- 提供分布式服务

分布式远程服务（Remote Service, RPC）、分布式实时对象（Live Object）服务、分布式执行服务（Executor Service）、分布式调度任务服务（Scheduler Service）和分布式映射归纳服务（MapReduce）

- 支持Spring框架

- 提供Spring Cache集成

- 提供Hibernate Cache集成

- 提供JCache实现

- 提供Tomcat Session Manager

- 提供Spring Session集成

- 支持异步流方式执行操作

- 支持Redis管道操作（批量执行）

- 支持安卓（Andriod）系统

- 支持断线自动重连

- 支持命令发送失败自动重试

- 支持OSGi

- 支持采用多种方式自动序列化和反序列化（Jackson JSON,Avro,Smile,CBOR,MsgPack,Kryo,FST,LZ4,Snappy和JDK序列化）

- 超过1000个单元测试

#### 与Jedis对比

Jedis是Redis的Java实现的客户端，其API提供了比较全面的Redis命令的支持；Redisson实现了分布式和可扩展的Java数据结构，和Jedis相比，功能较为简单，不支持字符串操作，不支持排序、事务、管道、分区等Redis特性。Redisson的宗旨是促进使用者对Redis的关注分离，从而让使用者能够将精力更集中地放在处理业务逻辑上。

#### 快速开始

**Maven**

```
<!-- JDK 1.8+ compatible -->
<dependency>
   <groupId>org.redisson</groupId>
   <artifactId>redisson</artifactId>
   <version>3.5.5</version>
</dependency>  

<!-- JDK 1.6+ compatible -->
<dependency>
   <groupId>org.redisson</groupId>
   <artifactId>redisson</artifactId>
   <version>2.10.5</version>
</dependency>
```

**Java**

```
// 1. Create config object
Config = ...

// 2. Create Redisson instance
RedissonClient redisson = Redisson.create(config);

// 3. Get object you need
RMap<MyKey, MyValue> map = redisson.getMap("myMap");

RLock lock = redisson.getLock("myLock");

RExecutorService executor = redisson.getExecutorService("myExecutorService");

// over 30 different objects and services ...
```
