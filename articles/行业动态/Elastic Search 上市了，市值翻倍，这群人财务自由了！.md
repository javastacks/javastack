国庆长假，大部分人还深浸在风花雪月之中，而就在昨天（美国时间10月5号），我们 Java 程序员所熟知的大名鼎鼎的 Elastic Search 居然在美国纽约证券交易所上市了！

![](http://img.javastack.cn/18-10-7/64836645.jpg)

当说到搜索时，大部分人可能只会说谷歌、百度等，但在企业内部的数据搜索还面临许多挑战，这就需要依赖开源的搜索技术，Elastic 公司就诞生了。

![](http://img.javastack.cn/18-10-7/60204672.jpg)

所以，其实不能说是 Elastic Search 上市，应该说是其背后的 Elastic 公司上市，Elastic Search 只是 Elastic 公司最出名的产品之一，其中还包括有分布式日志解决方案 ELK（Elastic Search、Logstash、Kibana）、Beats、ECE等。

Elastic Search 只是比其公司 Elastic 更有影响力，如果你还不知道什么是 Elastic Search, 我们来看下官网的描述。

> Elasticsearch is a distributed, RESTful search and analytics engine capable of solving a growing number of use cases. As the heart of the Elastic Stack, it centrally stores your data so you can discover the expected and uncover the unexpected.

翻译就是：

> Elasticsearch 是一个分布式的基于 RESTful 接口的搜索和分析引擎，它能够解决越来越多的使用场景。作为 Elastic Stack 的核心，它集中存储数据，可以发现预期及之外的结果。

简单的说，Elastic Search 是当前最主流最热门的开源分布式全文搜索引擎，2010 年发布，基于 Java 语言开发，以 JSON 格式文档来存储数据，并提供了 RESTful Web 服务接口访问，能以非常快的速度来检索非常大的数据量。

目前 Elastic Search 被许多大型组织使用，如 Wikipedia，StackOverflow，GitHub 等，国内更是遍地开花。做电商的同学最清楚不过了，如商品的管理和搜索工作都是用它来做的，简直就是电商系统数据存储搜索之必备良药。

Elastic 昨天上市，震惊技术界，在 Elastic 的官网博客申明中，博主也看到了最新的致谢内容，即感谢所有用户、客户及合作伙伴，并对过去和未来做了总结和展望。

![](http://img.javastack.cn/18-10-6/36130911.jpg)

> 官方致谢申明：
https://www.elastic.co/blog/ze-bell-has-rung-thank-you-users-customers-and-partners

Elastic 上市后，其股票（股票代码：ESTC）更是大涨，发行价为 36 美元，最高涨至 74.20 美元，最终收盘价为 70.00 美元，涨幅 94.44%，几乎翻倍。从公司成立到上市仅用了 8 年，超过 3.5+ 亿的产品下载，100万+ 名开发人员及 5,500+ 个客户，也是牛逼，这一上市，不知道又有多少人从此实现财务自由。。

![](http://img.javastack.cn/18-10-6/45408673.jpg)

上市后，大家可能有疑惑以后会不会收费，对开源有没有影响，这个不用多忧虑。大多开源项目（如:MongoDB），除了开源版本之外，都会额外提供收费的云服务（即：SaaS），Elastic 亦是如此，我们也可以在 Elastic 官网找到对应的收费云服务。毕竟 Elastic 是一个家公司，是公司就要生存，要吃饭，提供云付费产品也是其生存之本。

![](http://img.javastack.cn/18-10-6/31555398.jpg)

收费意味着可以有更好的安全保障及售后服务，但国内貌似都喜欢折腾，一方面也为了数据安全性，所以大多使用的是开源版本。对数据不敏感，且没有能力折腾开源版本的可以使用其收费云服务，并且官方提供免费 14 天试用期。

外国人的技术就是牛逼，随便整几个开源产品就能上市，去年 MongoDB 上市，今年就是 Elastic Search, 后面还有更多后起之秀，不说了，我先去 Github 搞个开源项目。。

