### Kafka集群安装、配置和启动

Kafka需要依赖zookeeper，并且自身集成了zookeeper，zookeeper至少需要3个节点保证集群高可用，下面是在单机linux下创建kafka3个节点伪集群模式。

**1、下载包**

> 下载地址：http://kafka.apache.org/downloads

**2、解压包**

> tar -zxvf kafka_2.11-1.0.0.tgz\
> mv kafka_2.11-1.0.0 kafka1\
> mv kafka_2.11-1.0.0 kafka2\
> mv kafka_2.11-1.0.0 kafka3

**3、创建ZK集群**

修改ZK配置文件：kafka1-3/config/zookeeper.properties分别修改对应的参数。

```
dataDir=/usr/local/kafka/zookeeper1
dataLogDir=/usr/local/kafka/zookeeper/log
clientPort=2181
maxClientCnxns=0
tickTime=2000
initLimit=100
syncLimit=5
server.1=127.0.0.1:2888:3888
server.2=127.0.0.1:4888:5888
server.3=127.0.0.1:6888:7888
```

/usr/local/kafka/zookeeper1-3目录下分别创建myid文件，内容对应1~3

启动ZK，分别进行Kafka1-3目录:

> bin/zookeeper-server-start.sh config/zookeeper.properties &

启动报文件失败，需要手动创建文件目录并赋予对应的权限。

**4、创建Kafka集群**

配置文件：kafka1-3/config/server.properties分别修改对应的参数。

```
broker.id=1 
zookeeper.connect=localhost:2181,localhost:2182,localhost:2183
listeners=PLAINTEXT://192.168.12.11:9091 
log.dirs=/tmp/kafka-logs-1
```

启动Kafka，分别进行Kafka1-3目录:

> bin/kafka-server-start.sh config/server.properties &

启动报文件失败，需要手动创建文件目录并赋予对应的权限。

**5、集群测试**

在kafka1上面发送消息：

> bin/kafka-console-producer.sh --broker-list localhost:9091 --topic test 

在kafka2、kafka3消费消息：

> bin/kafka-console-consumer.sh --zookeeper localhost:2181 --from-beginning --topic my-replicated-topic

### Spring Boot 集成 Kafka 实战

**1、添加spring-kafka依赖**

```
<spring-kafka.version>2.1.0.RELEASE</spring-kafka.version>

<!-- spring-kafka-->
<dependency>
    <groupId>org.springframework.kafka</groupId>
    <artifactId>spring-kafka</artifactId>
    <version>${spring-kafka.version}</version>
</dependency>
```

**2、添加Spring Boot的自动配置**

自动配置类：

> org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration

配置属性类：
> org.springframework.boot.autoconfigure.kafka.KafkaProperties

```
Spring:
  kafka:
    bootstrap-servers:
      - 192.168.101.137:9091
      - 192.168.101.137:9092
      - 192.168.101.137:9093
    producer:
      retries: 0
      batch-size: 16384
      buffer-memory: 33554432
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      value-serializer: org.apache.kafka.common.serialization.StringSerializer
    consumer:
      group-id: foo
      auto-offset-reset: earliest
      enable-auto-commit: true
      auto-commit-interval: 100
      key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
      value-deserializer: org.apache.kafka.common.serialization.StringDeserializer
```

**3、发送消息**


```
@Autowired
private KafkaTemplate kafkaTemplate;

@GetMapping("/send")
public Object send(String msg) {
	kafkaTemplate.send("test", "name", msg);
	return "send ok";
}

```

**4、接收消息**

在任何bean里面，添加@KafkaListener，支持消息接收。


```
@KafkaListener(topics = "test")
public void processMessage(String content) {
	logger.info("收到消息, topic:test, msg:{}", content);
}
```

**5、参考资料**

Spring Boot & Kafka官方文档：

> https://docs.spring.io/spring-boot/docs/current-SNAPSHOT/reference/htmlsingle/#boot-features-kafka

Spring for Apache Kafka官方文档：

> https://docs.spring.io/spring-kafka/reference/htmlsingle/

