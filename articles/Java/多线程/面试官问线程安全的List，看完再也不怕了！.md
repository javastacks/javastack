最近在Java技术栈知识星球里面有球友问到了线程安全的 List：

![](http://img.javastack.cn/20190927184141.png)

扫码查看答案或加入知识星球

栈长在之前的文章《[出场率比较高的一道多线程安全面试题](https://mp.weixin.qq.com/s/oA-uEbzNYA4KYwLtRWXRVw)》里面讲过 ArrayList 的不安全性。

那么面试官会问你，既然 ArrayList 是线程不安全的，怎么保证它的线程安全性呢？或者有什么替代方案？

往下看，看我如何碾压他！

大部分人会脱口而出：用Vector，这样只会让面试官鄙视！除了Vector，你还会别的吗？

你至少还得说得上这种：

> java.util.Collections.SynchronizedList

它能把所有 List 接口的实现类转换成线程安全的List，比 Vector 有更好的扩展性和兼容性，SynchronizedList的构造方法如下：

```
final List<E> list;

SynchronizedList(List<E> list) {
    super(list);
    this.list = list;
}
```

SynchronizedList的部分方法源码如下：

```
public E get(int index) {
    synchronized (mutex) {return list.get(index);}
}
public E set(int index, E element) {
    synchronized (mutex) {return list.set(index, element);}
}
public void add(int index, E element) {
    synchronized (mutex) {list.add(index, element);}
}
public E remove(int index) {
    synchronized (mutex) {return list.remove(index);}
}
```

很可惜，它所有方法都是带同步对象锁的，和 Vector 一样，它不是性能最优的。即使你能说到这里，面试官还会继续往下追问，比如在读多写少的情况，SynchronizedList这种集合性能非常差，还有没有更合适的方案？

介绍两个并发包里面的并发集合类：

> java.util.concurrent.CopyOnWriteArrayList
> java.util.concurrent.CopyOnWriteArraySet

CopyOnWrite集合类也就这两个，Java 1.5 开始加入，你要能说得上这两个才能让面试官信服。

#### CopyOnWriteArrayList

CopyOnWrite（简称：COW）：即复制再写入，就是在添加元素的时候，先把原 List 列表复制一份，再添加新的元素。


先来看下它的 add 方法源码：

```
public boolean add(E e) {
    // 加锁
    final ReentrantLock lock = this.lock;
    lock.lock();
    try {
        // 获取原始集合
        Object[] elements = getArray();
        int len = elements.length;
        
        // 复制一个新集合
        Object[] newElements = Arrays.copyOf(elements, len + 1);
        newElements[len] = e;
        
        // 替换原始集合为新集合
        setArray(newElements);
        return true;
    } finally {
        // 释放锁
        lock.unlock();
    }
}
```

添加元素时，先加锁，再进行复制替换操作，最后再释放锁。

再来看下它的 get 方法源码：

```
private E get(Object[] a, int index) {
    return (E) a[index];
}

public E get(int index) {
    return get(getArray(), index);
}
```

可以看到，获取元素并没有加锁。

这样做的好处是，在高并发情况下，读取元素时就不用加锁，写数据时才加锁，大大提升了读取性能。

#### CopyOnWriteArraySet

CopyOnWriteArraySet逻辑就更简单了，就是使用 CopyOnWriteArrayList 的 addIfAbsent 方法来去重的，添加元素的时候判断对象是否已经存在，不存在才添加进集合。

```
/**
 * Appends the element, if not present.
 *
 * @param e element to be added to this list, if absent
 * @return {@code true} if the element was added
 */
public boolean addIfAbsent(E e) {
    Object[] snapshot = getArray();
    return indexOf(e, snapshot, 0, snapshot.length) >= 0 ? false :
        addIfAbsent(e, snapshot);
}
```

这两种并发集合，虽然牛逼，但只适合于读多写少的情况，如果写多读少，使用这个就没意义了，因为每次写操作都要进行集合内存复制，性能开销很大，如果集合较大，很容易造成内存溢出。

#### 总结

下次面试官问你线程安全的 List，你可以从 Vector > SynchronizedList > CopyOnWriteArrayList 这样的顺序依次说上来，这样才有带入感，也能体现你对知识点的掌握程度。

看完有没有收获呢？下次面试应该能秒杀面试官了吧！

大家也可以关注微信公众号：Java技术栈，栈长将继续分享更多多线程在工作中的实战用法，请关注后续文章，或者在公众号后台回复：多线程，栈长已经写了好多多线程文章，都是接地气干货。

觉得有用，转发分享下朋友圈给更多的人看吧~

本文原创首发于微信公众号：Java技术栈（id:javastack），转载请原样保留本信息。
