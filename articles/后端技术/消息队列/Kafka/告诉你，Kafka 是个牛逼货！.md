![image](http://img.orchome.com:8888/group1/M00/00/01/KmCudlf4YWCAbQPXAASxr5laXjI309.png)

**Kafka简介**

Kafka是一种高吞吐量的分布式发布订阅消息系统，是消息中间件的一种。它可以处理消费者规模的网站中的所有动作流数据，它是由Apache软件基金会开发的一个开源流处理平台，由Scala和Java编写。

kafka可用于构建实时数据管道和流应用程序，具有横向扩展、容错、wicked fast（变态快）等优点，并已在成千上万家公司运行。

> 官网：kafka.apache.org/

**Kafka相关术语**

- Broker

Kafka集群包含一个或多个服务器，这种服务器被称为broker。

- Topic

每条发布到Kafka集群的消息都有一个类别，这个类别被称为Topic。

- Partition

Partition是物理上的概念，每个Topic包含一个或多个Partition。

- Producer

负责发布消息到Kafka broker。

- Consumer

消息消费者，向Kafka broker读取消息的客户端。

- Consumer Group

每个Consumer属于一个特定的Consumer Group（可为每个Consumer指定group name，若不指定group name则属于默认的group）。

**kafka核心API**

- Producer API

应用程序使用Producer API发布消息到1个或多个topic（主题）。

- Consumer API

应用程序使用Consumer API来订阅一个或多个topic，并处理产生的消息。

- Streams API

应用程序使用Streams API充当一个流处理器，从1个或多个topic消费输入流，并生产一个输出流到1个或多个输出topic，有效地将输入流转换到输出流。

- Connector API

Connector API允许构建或运行可重复使用的生产者或消费者，将topic连接到现有的应用程序或数据系统。例如，一个关系数据库的连接器可捕获每一个变化。

![image](http://img.orchome.com:8888/group1/M00/00/01/KmCudlf7DXiAVXBMAAFScKNS-Og538.png)

Client和Server之间的通讯，是通过一条简单、高性能并且和开发语言无关的TCP协议。并且该协议保持与老版本的兼容。Kafka提供了Java Client（客户端）。除了Java Client外，还有非常多的其它编程语言的Client。

**Kakfa有什么优势？**

1、保证消息消费的有序性.

2、在多个consumer组的并发处理消息的情况下，保证有序性和负载均衡。


下面是一些关于Apache kafka 流行的使用场景。

**Kakfa的使用场景**

- 消息

kafka更好的替换传统的消息系统，消息系统被用于各种场景（解耦数据生产者，缓存未处理的消息，等），与大多数消息系统比较，kafka有更好的吞吐量，内置分区，副本和故障转移，这有利于处理大规模的消息。

根据我们的经验，消息往往用于较低的吞吐量，但需要低的端到端延迟，并需要提供强大的耐用性的保证。

在这一领域的kafka比得上传统的消息系统，如的ActiveMQ或RabbitMQ的

- 网站活动追踪

kafka原本的使用场景：用户的活动追踪，网站的活动（网页游览，搜索或其他用户的操作信息）发布到不同的话题中心，这些消息可实时处理，实时监测，也可加载到Hadoop或离线处理数据仓库。

每个用户页面视图都会产生非常高的量。

- 指标

kafka也常常用于监测数据。分布式应用程序生成的统计数据集中聚合。

- 日志聚合

使用kafka代替一个日志聚合的解决方案。

- 流处理

kafka消息处理包含多个阶段。其中原始输入数据是从kafka主题消费的，然后汇总，丰富，或者以其他的方式处理转化为新主题，例如，一个推荐新闻文章，文章内容可能从“articles”主题获取；然后进一步处理内容，得到一个处理后的新内容，最后推荐给用户。这种处理是基于单个主题的实时数据流。从0.10.0.0开始，轻量，但功能强大的流处理，就进行这样的数据处理了。

除了Kafka Streams，还有Apache Storm和Apache Samza可选择。

- 事件采集

事件采集是一种应用程序的设计风格，其中状态的变化根据时间的顺序记录下来，kafka支持这种非常大的存储日志数据的场景。

- 提交日志

kafka可以作为一种分布式的外部提交日志，日志帮助节点之间复制数据，并作为失败的节点来恢复数据重新同步，kafka的日志压缩功能很好的支持这种用法，这种用法类似于Apacha BookKeeper项目。

> 参考：http://orchome.com/kafka/index