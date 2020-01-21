![image](http://1ke.co/files/course/2017/03-01/145741594b4c665232.png?4.9.3)

**Netty是什么？为什么这么火？**

Netty是目前最流行的由JBOSS提供的一个Java开源框架NIO框架，Netty提供异步的、事件驱动的网络应用程序框架和工具，用以快速开发高性能、高可靠性的网络服务器和客户端程序。

相比JDK原生NIO，Netty提供了相对十分简单易用的API，非常适合网络编程。Netty是完全基于NIO实现的，所以Netty是异步的。

作为一个异步NIO框架，Netty的所有IO操作都是异步非阻塞的，通过Future-Listener机制，用户可以方便的主动获取或者通过通知机制获得IO操作结果。

Netty无疑是NIO的老大，它的健壮性、功能、性能、可定制性和可扩展性在同类框架都是首屈一指的。它已经得到成百上千的商业/商用项目验证，如Hadoop的RPC框架Avro、RocketMQ以及主流的分布式通信框架Dubbo等等。

为什么这么火，是有原因的。

**Netty的优点可以总结如下**

1、API使用简单，开发门槛低；

2、功能强大，预置了多种编解码功能，支持多种主流协议；

3、定制能力强，可以通过ChannelHandler对通信框架进行灵活地扩展；

4、性能高，通过与其他业界主流的NIO框架对比，Netty的综合性能最优；

5、成熟、稳定，Netty修复了已经发现的所有JDK NIO BUG，业务开发人员不需要再为NIO的BUG而烦恼；

6、社区活跃，版本迭代周期短，发现的BUG可以被及时修复，同时，更多的新功能会加入；

7、经历了大规模的商业应用考验，质量得到验证。在互联网、大数据、网络游戏、企业应用、电信软件等众多行业得到成功商用，证明了它已经完全能够满足不同行业的商业应用了。

**与Mina相比有什么优势？**

1、都是Trustin Lee的作品，Netty更晚；

2、Mina将内核和一些特性的联系过于紧密，使得用户在不需要这些特性的时候无法脱离，相比下性能会有所下降，Netty解决了这个设计问题；

3、Netty的文档更清晰，很多Mina的特性在Netty里都有；

4、Netty更新周期更短，新版本的发布比较快；

5、它们的架构差别不大，Mina靠apache生存，而Netty靠jboss，和jboss的结合度非常高，Netty有对google protocal buf的支持，有更完整的ioc容器支持(spring,guice,jbossmc和osgi)；

6、Netty比Mina使用起来更简单，Netty里你可以自定义的处理upstream events或/和downstream events，可以使用decoder和encoder来解码和编码发送内容；

7、Netty和Mina在处理UDP时有一些不同，Netty将UDP无连接的特性暴露出来；而Mina对UDP进行了高级层次的抽象，可以把UDP当成"面向连接"的协议，而要Netty做到这一点比较困难。

