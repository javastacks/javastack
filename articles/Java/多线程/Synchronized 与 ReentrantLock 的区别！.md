> 来源：cnblogs.com/baizhanshi/p/7211802.html

之前栈长分享了重入锁的概念：《[到底什么是重入锁，拜托，一次搞清楚！](https://mp.weixin.qq.com/s/i1dA5gnhC9I-Zj1l0HDqKA)》，今天现来深入了解下 Synchronized 与 ReentrantLock 的区别！

**可重入性：**

从名字上理解，ReenTrantLock的字面意思就是再进入的锁，其实synchronized关键字所使用的锁也是可重入的，两者关于这个的区别不大。两者都是同一个线程没进入一次，锁的计数器都自增1，所以要等到锁的计数器下降为0时才能释放锁。


**锁的实现：**

Synchronized是依赖于JVM实现的，而ReenTrantLock是JDK实现的，有什么区别，说白了就类似于操作系统来控制实现和用户自己敲代码实现的区别。前者的实现是比较难见到的，后者有直接的源码可供阅读。


**性能的区别：**

在Synchronized优化以前，synchronized的性能是比ReenTrantLock差很多的，但是自从Synchronized引入了偏向锁，轻量级锁（自旋锁）后，两者的性能就差不多了，在两种方法都可用的情况下，官方甚至建议使用synchronized，其实synchronized的优化我感觉就借鉴了ReenTrantLock中的CAS技术。都是试图在用户态就把加锁问题解决，避免进入内核态的线程阻塞。


**功能区别：**

便利性：很明显Synchronized的使用比较方便简洁，并且由编译器去保证锁的加锁和释放，而ReenTrantLock需要手工声明来加锁和释放锁，为了避免忘记手工释放锁造成死锁，所以最好在finally中声明释放锁。

锁的细粒度和灵活度：很明显ReenTrantLock优于Synchronized


**ReenTrantLock独有的能力：**

1.ReenTrantLock可以指定是公平锁还是非公平锁。而synchronized只能是非公平锁。所谓的公平锁就是先等待的线程先获得锁。

2.ReenTrantLock提供了一个Condition（条件）类，用来实现分组唤醒需要唤醒的线程们，而不是像synchronized要么随机唤醒一个线程要么唤醒全部线程。

3.ReenTrantLock提供了一种能够中断等待锁的线程的机制，通过lock.lockInterruptibly()来实现这个机制。


**ReenTrantLock实现的原理：**

在网上看到相关的源码分析，本来这块应该是本文的核心，但是感觉比较复杂就不一一详解了，简单来说，ReenTrantLock的实现是一种自旋锁，通过循环调用CAS操作来实现加锁。它的性能比较好也是因为避免了使线程进入内核态的阻塞状态。想尽办法避免线程进入内核的阻塞状态是我们去分析和理解锁设计的关键钥匙。


**什么情况下使用ReenTrantLock：**

答案是，如果你需要实现ReenTrantLock的三个独有功能时。


最后，大家也可以关注微信公众号：Java技术栈，栈长将继续分享更多 Java 系列干货，在公众号后台回复：Java，可以获取栈长已经整理好的历史 Java 系列干货文章。

觉得有用，转发分享下朋友圈给更多的人看吧，另外，给个好看，谢谢老板~