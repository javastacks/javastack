
#### 简介
Elastic-Job是一个分布式调度解决方案，由两个相互独立的子项目Elastic-Job-Lite和Elastic-Job-Cloud组成。

Elastic-Job-Lite定位为轻量级无中心化解决方案，使用jar包的形式提供分布式任务的协调服务。

#### 功能列表

**1、任务分片**

- 将整体任务拆解为多个子任务
- 可通过服务器的增减弹性伸缩任务处理能力
- 分布式协调，任务服务器上下线的全自动发现与处理

**2、 多任务类型**

- 基于时间驱动的任务
- 基于数据驱动的任务（TBD）
- 同时支持常驻任务和瞬时任务
- 多语言任务支持

**3、云原生**

- 完美结合Mesos或Kubernetes等调度平台
- 任务不依赖于IP、磁盘、数据等有状态组件
- 合理的资源调度，基于Netflix的Fenzo进行资源分配

**4、容错性**

- 支持定时自我故障检测与自动修复
- 分布式任务分片唯一性保证
- 支持失效转移和错过任务重触发

**5、任务聚合**

- 相同任务聚合至相同的执行器统一处理
- 节省系统资源与初始化开销
- 动态调配追加资源至新分配的任务

**6、易用性**

- 完善的运维平台
- 提供任务执行历史数据追踪能力
- 注册中心数据一键dump用于备份与调试问题

#### 整体架构图

**Elastic-Job-Lite**

![image](http://img.javastack.cn/18-2-24/59132374.jpg)

**Elastic-Job-Cloud**

![image](http://ovfotjrsi.bkt.clouddn.com/docs/img/architecture/elastic_job_cloud.png)

#### 参考

> 官网：http://elasticjob.io/index_zh.html\
> 码云：https://gitee.com/elasticjob\
> GitHub：https://github.com/elasticjob/elastic-job\
> 采用公司：http://elasticjob.io/docs/elastic-job-lite/00-overview/company

