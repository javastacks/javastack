
#### 1. 什么是伪共享

CPU 缓存系统中是以缓存行（cache line）为单位存储的。目前主流的 CPU Cache 的 Cache Line 大小都是 64 Bytes。在多线程情况下，如果需要修改“共享同一个缓存行的变量”，就会无意中影响彼此的性能，这就是伪共享（False Sharing）。

#### 2. 缓存行

由于共享变量在 CPU 缓存中的存储是以缓存行为单位，一个缓存行可以存储多个变量（存满当前缓存行的字节数）；而CPU对缓存的修改又是以缓存行为最小单位的，那么就会出现上诉的伪共享问题。

Cache Line 可以简单的理解为 CPU Cache 中的最小缓存单位，今天的 CPU 不再是按字节访问内存，而是以 64 字节为单位的块(chunk)拿取，称为一个缓存行(cache line)。当你读一个特定的内存地址，整个缓存行将从主存换入缓存，并且访问同一个缓存行内的其它值的开销是很小的。

#### 3. CPU 的三级缓存

由于 CPU 的速度远远大于内存速度，所以 CPU 设计者们就给 CPU 加上了缓存(CPU Cache)。 以免运算被内存速度拖累。（就像我们写代码把共享数据做Cache不想被DB存取速度拖累一样），CPU Cache 分成了三个级别：L1，L2，L3。越靠近CPU的缓存越快也越小。所 以L1 缓存很小但很快，并且紧靠着在使用它的 CPU 内核。L2 大一些，也慢一些，并且仍然只能被一个单独的 CPU 核使用。L3 在现代多核机器中更普遍，仍然更大，更慢，并且被单个插槽上的所有 CPU 核共享。最后，你拥有一块主存，由全部插槽上的所有 CPU 核共享。

当 CPU 执行运算的时候，它先去L1查找所需的数据，再去L2，然后是L3，最后如果这些缓存中都没有，所需的数据就要去主内存拿。走得越远，运算耗费的时间就越长。所以如果你在做一些很频繁的事，你要确保数据在L1缓存中。

![](http://img.javastack.cn/18-5-31/91078691.jpg)

#### 4. 缓存关联性

目前常用的缓存设计是N路组关联(N-Way Set Associative Cache)，他的原理是把一个缓存按照N个 Cache Line 作为一组（Set），缓存按组划为等分。每个内存块能够被映射到相对应的set中的任意一个缓存行中。比如一个16路缓存，16个 Cache Line 作为一个Set，每个内存块能够被映射到相对应的 Set 中的16个 CacheLine 中的任意一个。一般地，具有一定相同低bit位地址的内存块将共享同一个Set。 

下图为一个2-Way的Cache。由图中可以看到 Main Memory 中的 Index 0,2,4 都映射在Way0的不同 CacheLine 中，Index 1,3,5都映射在Way1的不同 CacheLine 中。

![](http://img.javastack.cn/18-5-31/71458405.jpg)

#### 5. MESI 协议

多核 CPU 都有自己的专有缓存（一般为L1，L2），以及同一个 CPU 插槽之间的核共享的缓存（一般为L3）。不同核心的CPU缓存中难免会加载同样的数据，那么如何保证数据的一致性呢，就是 MESI 协议了。

在 MESI 协议中，每个 Cache line 有4个状态，可用 2 个 bit 表示，它们分别是： 
M(Modified)：这行数据有效，数据被修改了，和内存中的数据不一致，数据只存在于本 Cache 中； 
E(Exclusive)：这行数据有效，数据和内存中的数据一致，数据只存在于本 Cache 中； 
S(Shared)：这行数据有效，数据和内存中的数据一致，数据存在于很多 Cache 中； 
I(Invalid)：这行数据无效。

那么，假设有一个变量i=3（应该是包括变量i的缓存块，块大小为缓存行大小）；已经加载到多核（a,b,c）的缓存中，此时该缓存行的状态为S；此时其中的一个核a改变了变量i的值，那么在核a中的当前缓存行的状态将变为M，b,c核中的当前缓存行状态将变为I。如下图：

![](http://img.javastack.cn/18-5-31/66429246.jpg)

#### 6. 解决原理

为了避免由于 false sharing 导致 Cache Line 从 L1,L2,L3 到主存之间重复载入，我们可以使用数据填充的方式来避免，即单个数据填充满一个CacheLine。这本质是一种空间换时间的做法。

#### 7. Java 对于伪共享的传统解决方案

```
/***
* 微信公众号：Java技术栈
**/
import java.util.concurrent.atomic.AtomicLong;

public final class FalseSharing
    implements Runnable
{
    public final static int NUM_THREADS = 4; // change
    public final static long ITERATIONS = 500L * 1000L * 1000L;
    private final int arrayIndex;

    private static VolatileLong[] longs = new VolatileLong[NUM_THREADS];
    static
    {
        for (int i = 0; i < longs.length; i++)
        {
            longs[i] = new VolatileLong();
        }
    }

    public FalseSharing(final int arrayIndex)
    {
        this.arrayIndex = arrayIndex;
    }

    public static void main(final String[] args) throws Exception
    {
        final long start = System.nanoTime();
        runTest();
        System.out.println("duration = " + (System.nanoTime() - start));
    }

    private static void runTest() throws InterruptedException
    {
        Thread[] threads = new Thread[NUM_THREADS];

        for (int i = 0; i < threads.length; i++)
        {
            threads[i] = new Thread(new FalseSharing(i));
        }

        for (Thread t : threads)
        {
            t.start();
        }

        for (Thread t : threads)
        {
            t.join();
        }
    }

    public void run()
    {
        long i = ITERATIONS + 1;
        while (0 != --i)
        {
            longs[arrayIndex].set(i);
        }
    }

    public static long sumPaddingToPreventOptimisation(final int index)
    {
        VolatileLong v = longs[index];
        return v.p1 + v.p2 + v.p3 + v.p4 + v.p5 + v.p6;
    }

    //jdk7以上使用此方法(jdk7的某个版本oracle对伪共享做了优化)
    public final static class VolatileLong
    {
        public volatile long value = 0L;
        public long p1, p2, p3, p4, p5, p6;
    }

    // jdk7以下使用此方法
    public final static class VolatileLong
    {
        public long p1, p2, p3, p4, p5, p6, p7; // cache line padding
        public volatile long value = 0L;
        public long p8, p9, p10, p11, p12, p13, p14; // cache line padding

    }
}
```


#### 8. Java 8 中的解决方案

Java 8 中已经提供了官方的解决方案，Java 8 中新增了一个注解：`@sun.misc.Contended`。加上这个注解的类会自动补齐缓存行，需要注意的是此注解默认是无效的，需要在 jvm 启动时设置 `-XX:-RestrictContended` 才会生效。


```
@sun.misc.Contended
public final static class VolatileLong {
    public volatile long value = 0L;
    //public long p1, p2, p3, p4, p5, p6;
```


#### 参考文献

> http://igoro.com/archive/gallery-of-processor-cache-effects/
> http://ifeve.com/false-sharing/ 
> http://blog.csdn.net/muxiqingyang/article/details/6615199 
> https://yq.aliyun.com/articles/62865

@码农们，你们是怎么理解和解决伪共享的？欢迎留言！

