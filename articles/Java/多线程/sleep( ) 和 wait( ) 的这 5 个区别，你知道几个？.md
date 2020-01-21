
sleep(休眠) 和 wait(等待) 方法是 Java 多线程中常用的两个方法，它们有什么区别及一些该注意的地方有哪些呢？下面给大家一一分解。

在之前的文章《[Java多线程神器：join使用及原理](https://mp.weixin.qq.com/s/at8NynjnYvqkFw61kn3Apg)》介绍了，它其实用的是 wait 实现的线程等待。

#### 区别1：使用限制

使用 sleep 方法可以让让当前线程休眠，时间一到当前线程继续往下执行，在任何地方都能使用，但需要捕获 InterruptedException 异常。

```
try {
	Thread.sleep(3000L);
} catch (InterruptedException e) {
	e.printStackTrace();
}
```

而使用 wait 方法则必须放在 synchronized 块里面，同样需要捕获 InterruptedException 异常，并且需要获取对象的锁。

```
synchronized (lock){
    try {
        lock.wait();
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
}
```

而且 wait 还需要额外的方法 notify/ notifyAll 进行唤醒，它们同样需要放在 synchronized 块里面，且获取对象的锁。。


```
synchronized (lock) {
    // 随机唤醒
    lock.notify();
    
    // 唤醒全部
    lock.notifyAll();
}
```

当然也可以使用带时间的 wait(long millis) 方法，时间一到，无需其他线程唤醒，也会重新竞争获取对象的锁继续执行。

#### 区别2：使用场景

sleep 一般用于当前线程休眠，或者轮循暂停操作，wait 则多用于多线程之间的通信。

#### 区别3：所属类

sleep 是 Thread 类的静态本地方法，wait 则是 Object 类的本地方法。

> java.lang.Thread#sleep

```
public static native void sleep(long millis) throws InterruptedException;
```

> java.lang.Object#wait

```
public final native void wait(long timeout) throws InterruptedException;
```

**为什么要这样设计呢？**

因为 sleep 是让当前线程休眠，不涉及到对象类，也不需要获得对象的锁，所以是线程类的方法。wait 是让获得对象锁的线程实现等待，前提是要楚获得对象的锁，所以是类的方法。

#### 区别4：释放锁


```
Object lock = new Object();
synchronized (lock) {
    try {
        lock.wait(3000L);
        Thread.sleep(2000L);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
}
```

如上代码所示，wait 可以释放当前线程对 lock 对象锁的持有，而 sleep 则不会。

#### 区别5：线程切换

sleep 会让出 CPU 执行时间且强制上下文切换，而 wait 则不一定，wait 后可能还是有机会重新竞争到锁继续执行的。

@程序猿 你们还知道别的吗？欢迎留言！
