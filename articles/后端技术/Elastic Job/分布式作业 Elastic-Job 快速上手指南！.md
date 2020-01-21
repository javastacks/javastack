
Elastic-Job支持 JAVA API 和 Spring 配置两种方式配置任务，这里我们使用 JAVA API 的形式来创建一个简单的任务入门，现在都是 Spring Boot 时代了，所以不建议使用 Spring 配置文件的形式。

Elastic-Job 需要依赖 Zookeeper 中间件，用于注册和协调作业分布式行为的组件，目前仅支持 Zookeeper。我们已经创建了 Zookeeper 集群！

#### 环境要求

1、Java 请使用 JDK 1.7 及其以上版本。

2、Zookeeper 请使用 Zookeeper 3.4.6 及其以上版本。

3、Maven 请使用 Maven 3.0.4 及其以上版本。

#### 引入maven依赖

```
<dependency>
    <groupId>com.dangdang</groupId>
    <artifactId>elastic-job-lite-core</artifactId>
    <version>2.1.5</version>
</dependency>
```

这里有一个坑，这个依赖里面会包含有两个不同版本的 curator-client，导致调用里面方法的时候会找不到方法，所以需要单独引入 curator-client 的依赖包。


```
<dependency>
	<groupId>org.apache.curator</groupId>
	<artifactId>curator-client</artifactId>
	<version>2.11.1</version>
</dependency>
```

#### 创建作业

Elastic-Job 提供 Simple、Dataflow 和 Script 3种作业类型。

方法参数 shardingContext 包含作业配置、片和运行时信息。可通过 getShardingTotalCount(), getShardingItem() 等方法分别获取分片总数，运行在本作业服务器的分片序列号等。

这里我们创建一个简单（Simple）作业。

```
public class MyElasticJob implements SimpleJob {

	@Override
	public void execute(ShardingContext context) {
		switch (context.getShardingItem()) {
			case 0: {
				System.out.println("MyElasticJob - 0");
				break;
			}
			case 1: {
				System.out.println("MyElasticJob - 1");
				break;
			}
			case 2: {
				System.out.println("MyElasticJob - 2");
				break;
			}
			default: {
				System.out.println("MyElasticJob - default");
			}
		}
	}
}

```

**上面的0-2涉及分布式作业框架中分片的概念**

任务的分布式执行，需要将一个任务拆分为多个独立的任务项，然后由分布式的服务器分别执行某一个或几个分片项。

例如：有一个遍历数据库某张表的作业，现有2台服务器。为了快速的执行作业，那么每台服务器应执行作业的50%。为满足此需求，可将作业分成2片，每台服务器执行1片。作业遍历数据的逻辑应为：服务器A遍历ID以奇数结尾的数据；服务器B遍历ID以偶数结尾的数据。如果分成10片，则作业遍历数据的逻辑应为：每片分到的分片项应为ID%10，而服务器A被分配到分片项0,1,2,3,4；服务器B被分配到分片项5,6,7,8,9，直接的结果就是服务器A遍历ID以0-4结尾的数据；服务器B遍历ID以5-9结尾的数据。

> 作业分片策略：http://elasticjob.io/docs/elastic-job-lite/02-guide/job-sharding-strategy/

#### 配置作业

**Elastic-Job 配置分为3个层级，分别是 Core, Type 和 Root，每个层级使用相似于装饰者模式的方式装配。**

Core 对应 JobCoreConfiguration，用于提供作业核心配置信息，如：作业名称、分片总数、CRON表达式等。

Type 对应 JobTypeConfiguration，有3个子类分别对应 SIMPLE, DATAFLOW 和 SCRIPT 类型作业，提供3种作业需要的不同配置，如：DATAFLOW 类型是否流式处理或 SCRIPT 类型的命令行等。

Root 对应 JobRootConfiguration，有2个子类分别对应 Lite 和 Cloud 部署类型，提供不同部署类型所需的配置，如：Lite类型的是否需要覆盖本地配置或 Cloud 占用 CPU 或 Memory 数量等。

在 Spring Boot 启动类里面加作业配置代码。

```
private static CoordinatorRegistryCenter createRegistryCenter() {
    CoordinatorRegistryCenter regCenter = new ZookeeperRegistryCenter(new ZookeeperConfiguration("192.168.10.31:2181,192.168.10.32:2181,192.168.10.33:2181", "elastic-job-demo"));
    regCenter.init();
    return regCenter;
}

private static LiteJobConfiguration createJobConfiguration() {
    // 定义作业核心配置
    JobCoreConfiguration simpleCoreConfig = JobCoreConfiguration.newBuilder("demoSimpleJob", "0/15 * * * * ?", 10).build();
    
    // 定义SIMPLE类型配置
    SimpleJobConfiguration simpleJobConfig = new SimpleJobConfiguration(simpleCoreConfig, MyElasticJob.class.getCanonicalName());
    
    // 定义Lite作业根配置
    LiteJobConfiguration simpleJobRootConfig = LiteJobConfiguration.newBuilder(simpleJobConfig).build();
}

@Bean
public CommandLineRunner commandLineRunner() {
	return (String... args) -> {
		new JobScheduler(createRegistryCenter(), createJobConfiguration()).init();
	};
}
```

SimpleJobConfiguration 实现了JobTypeConfiguration接口。

LiteJobConfiguration 实现了JobRootConfiguration接口。

使用CommandLineRunner，可以等 Spring Boot 启动后再启动 Elastic-Job 作业。

其他的最基础的 Spring Boot 的配置就不说了，不懂的可以去公众号菜单 Spring Boot 专题中学习。

> 更多作业的配置请参考官方文档：http://elasticjob.io/docs/elastic-job-lite/02-guide/config-manual/

#### 启动作业

在工具里面使用 maven 命令 spring-boot:run 启动即可。

程序输出：

```
MyElasticJob - 0
MyElasticJob - 1
MyElasticJob - 2
MyElasticJob - default
MyElasticJob - default
MyElasticJob - default
MyElasticJob - default
MyElasticJob - default
MyElasticJob - default
MyElasticJob - default
```

由于是单个实例，所有 10 个分片都在一个实例输出来了，现在我们把它打成 jar 包，然后再用另外一个端口启动看下是否分片成功。

两边分别输出：


```
MyElasticJob - 0
MyElasticJob - 1
MyElasticJob - 2
MyElasticJob - default
MyElasticJob - default
```

和

```
MyElasticJob - default
MyElasticJob - default
MyElasticJob - default
MyElasticJob - default
MyElasticJob - default
```

上面的输出信息说明分片成功了，然后停掉一个项目后发现又自动触发分片，所有的都在同一个输出来了。

可以看出分片功能真的非常实用，作业开发起来真的很方便，整个架构也很清晰，推荐大家使用。

后面还更多的 Elastic-Job 实战干货请继续关注，觉得有用就动手分享鼓励一下我们吧！

