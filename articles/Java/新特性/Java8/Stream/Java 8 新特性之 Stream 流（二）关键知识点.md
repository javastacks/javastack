我们的第一篇文章，主要是通过一个Demo，让大家体验了一下使用流API的那种酣畅淋漓的感觉。如果你没有实践，我还是再次呼吁你动手敲一敲，自己实实在跑一遍上一篇的Demo。相信你的感受和理解也会随之加深的。继续探索流API的高级功能之前，我们先从接口级别全面了解一下流API，这个对于我们来说是至关重要的。接下来，我给大家准备了一张流API关键知识点的UML图。但是大家只需要花一两分钟，整理看一下就可以了，不需要记住，先有个印象，后面我给大家讲解一些关键的方法:

#### 流API UML

![流API UML](https://upload-images.jianshu.io/upload_images/1640787-129cbee787eef3b4.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

我先整体介绍一下:流API定义的几个接口，都是在java.util.stream包中的.其中上图中的BaseStream接口是最基础的接口，它提供了所有流都可以使用的基本功能:

```
public interface BaseStream<T, S extends BaseStream<T, S>> extends AutoCloseable {
//....先忽略这些具体的细节
}
```

由这个接口的定义我们得知，BaseStream是一个泛型接口,它有两个类型参数T和S， 其中T指定了流中的元素的类型,并且由<S extends BaseStream<T, S>>可以知道S必须为BaseStream或BaseStream子类(换句话说,就是S必须是扩展自BaseStream的)。BaseStream继承了AutoCloseable接口。这个接口主要是简化了关闭资源的操作。但是像平时我们操作的集合或数组，基本上都不会出现关闭流的情况。

```
//由BaseStream接口派生出的流接口包括IntStream ，LongStream，DoubleStream ，Stream<T>
public interface IntStream extends BaseStream<Integer, IntStream> {
}
public interface LongStream extends BaseStream<Long, LongStream> {
}
public interface DoubleStream extends BaseStream<Double, DoubleStream> {
}

//这是最具代表性的接口
public interface Stream<T> extends BaseStream<T, Stream<T>> {
//....先忽略这些具体的细节
}
```

由于Stream接口是最具代表性的，所以我们就选择它来讲解，其实在我们学完Stream接口，其它的三个接口，在使用上基本是一致的了，我们上一篇的Demo基本上也是使用Stream接口来做的练习。我们回想一下:在上一个Demo中我们通过集合框架的stream()方法，就能返回一个流了，它的返回类型就是Stream<T>，比如我们Stream<Integer>，由此得知，Stream接口里的类型参数T就是流中的元素的类型。木有错，就是这样滴。到这里，整个系列你们已经全部学会了，下课。

![](https://upload-images.jianshu.io/upload_images/1640787-cac539e2ac3e760f.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

战斗才刚刚开始！

![](https://upload-images.jianshu.io/upload_images/1640787-65d8e79c87d2ec76.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

现在是时候开始记忆一些知识了.

#### BaseStream详解:

```
public interface BaseStream<T, S extends BaseStream<T, S>> extends AutoCloseable {
     Iterator<T> iterator();//line2
     Spliterator<T> spliterator();//line3
     boolean isParallel();//line4
     S sequential();//line5
     S parallel();//line6
     S unordered();//line7
     S onClose(Runnable closeHandler);//line8
     @Override
     void close();//line10
}
```

* **Iterator<T> iterator();**  *`//line2`*
获得流的迭代器，并返回对该迭代器的引用(终端操作)
* **Spliterator<T> spliterator();** *`//line3`*
获取流的spliterator，并返回其引用(终端操作)
* **boolean isParallel();** *`//line4`*
如果调用流是一个并行流，则返回true;如果调用流是一个顺序流，则返回false。
* **S sequential();** *`//line5`*
基于调用流，返回一个顺序流。如果调用流已经是顺序流了，就返回该流。(中间操作)
* **S parallel();** *`//line6`*
基于调用流，返回一个并行流。如果调用流已经是并行流了，就返回该流。(中间操作)
* **S unordered();** *`//line7`*
基于调用流，返回一个无序流。如果调用流已经是无序流了，就返回该流。(中间操作)
* **S onClose(Runnable closeHandler);** *`//line8`*
返回一个新流，closeHandler指定了该流的关闭处理程序，当关闭该流时，将调用这个处理程序。(中间操作)
* **void close();** *`//line10`*
从AutoCloseable继承来的，调用注册关闭处理程序，关闭调用流(很少会被使用到)

#### "终端操作"&"中间操作"

细心的同学应该注意到了，BaseStream接口里面的很多方法都在最后标识了(终端操作)和(中间操作)，它们之间的区别是非常重要的。
* **终端操作**   会消费流，这种操作会产生一个结果的，比如上面的 iterator()和 spliterator()，以及上一篇中提到的min()和max()，或者是执行某一种操作，比如上一篇的forEach()，如果一个流被消费过了，那它就不能被重用的。

* **中间操作**   中间操作会产生另一个流。因此中间操作可以用来创建执行一系列动作的管道。一个特别需要注意的点是:中间操作不是立即发生的。相反，当在中间操作创建的新流上执行完终端操作后，中间操作指定的操作才会发生。所以中间操作是延迟发生的，中间操作的延迟行为主要是让流API能够更加高效地执行。

#### "中间操作"的状态

流的中间操作，可以为分`无状态操作`和`有状态操作`两种，在无状态操作中，在处理流中的元素时，会对当前的元素进行单独处理。比如:谓词过滤操作，因为每个元素都是被单独进行处理的，所有它和流中的其它元素无关，因此被称为无状态操作;而在有状态操作中，某个元素的处理可能依赖于其他元素。比如查找最小值，最大值，和排序，因为他们都依赖于其他的元素。因此为称为有状态操作。`当需要进行并行处理流时，有状态的操作和无状态的区别是非常重要的，因为有状态操作可能需要几次处理才能完成，后面的文章我将会给大家详细地讲，现在只需要正常学习下去就可以了`

另外，指出一点，如果大家了解泛型的话，应该知道，泛型的类型参数只能是引用类型，因此Stream操作的对象只能是引用类型的，不能用于基本类型。当然官方早已考虑到这一点了，前面你们看到的IntStream，LongStream，DoubleStream就是官方给我们提供的处理基本类型的流了。此处是不是应该给他们掌声！

#### Stream详解

在有了前面的那些知识作铺垫之后，学Stream接口应该会顺风顺水了。还是先看看Stream的详情先:

```
public interface Stream<T> extends BaseStream<T, Stream<T>> {
    Stream<T> filter(Predicate<? super T> predicate);//line2
    <R> Stream<R> map(Function<? super T, ? extends R> mapper);//line3
    IntStream mapToInt(ToIntFunction<? super T> mapper);//line4
    LongStream mapToLong(ToLongFunction<? super T> mapper);
    DoubleStream mapToDouble(ToDoubleFunction<? super T> mapper);
    <R> Stream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper);
    IntStream flatMapToInt(Function<? super T, ? extends IntStream> mapper);
    LongStream flatMapToLong(Function<? super T, ? extends LongStream> mapper);
    DoubleStream flatMapToDouble(Function<? super T, ? extends DoubleStream> mapper);
    Stream<T> distinct();
    Stream<T> sorted();//line12
    Stream<T> sorted(Comparator<? super T> comparator);//line13
    Stream<T> peek(Consumer<? super T> action);
    Stream<T> limit(long maxSize);
    Stream<T> skip(long n);
    void forEach(Consumer<? super T> action);//line17
    void forEachOrdered(Consumer<? super T> action);//line18
    Object[] toArray();
    <A> A[] toArray(IntFunction<A[]> generator);
    T reduce(T identity, BinaryOperator<T> accumulator);
    Optional<T> reduce(BinaryOperator<T> accumulator);
    <U> U reduce(U identity,
                 BiFunction<U, ? super T, U> accumulator,
                 BinaryOperator<U> combiner);
    <R> R collect(Supplier<R> supplier,
                  BiConsumer<R, ? super T> accumulator,
                  BiConsumer<R, R> combiner);
    <R, A> R collect(Collector<? super T, A, R> collector);
    Optional<T> min(Comparator<? super T> comparator);//line30
    Optional<T> max(Comparator<? super T> comparator);//line31
    long count();
    boolean anyMatch(Predicate<? super T> predicate);
    boolean allMatch(Predicate<? super T> predicate);
    boolean noneMatch(Predicate<? super T> predicate);
    Optional<T> findFirst();
    Optional<T> findAny();

    // Static factories

    public static<T> Builder<T> builder() {
        return new Streams.StreamBuilderImpl<>();
    }


    public static<T> Stream<T> empty() {
        return StreamSupport.stream(Spliterators.<T>emptySpliterator(), false);
    }


    public static<T> Stream<T> of(T t) {
        return StreamSupport.stream(new Streams.StreamBuilderImpl<>(t), false);
    }


    @SafeVarargs
    @SuppressWarnings("varargs") // Creating a stream from an array is safe
    public static<T> Stream<T> of(T... values) {
        return Arrays.stream(values);
    }


    public static<T> Stream<T> iterate(final T seed, final UnaryOperator<T> f) {
        Objects.requireNonNull(f);
        final Iterator<T> iterator = new Iterator<T>() {
            @SuppressWarnings("unchecked")
            T t = (T) Streams.NONE;

            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public T next() {
                return t = (t == Streams.NONE) ? seed : f.apply(t);
            }
        };
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(
                iterator,
                Spliterator.ORDERED | Spliterator.IMMUTABLE), false);
    }


    public static<T> Stream<T> generate(Supplier<T> s) {
        Objects.requireNonNull(s);
        return StreamSupport.stream(
                new StreamSpliterators.InfiniteSupplyingSpliterator.OfRef<>(Long.MAX_VALUE, s), false);
    }


    public static <T> Stream<T> concat(Stream<? extends T> a, Stream<? extends T> b) {
        Objects.requireNonNull(a);
        Objects.requireNonNull(b);

        @SuppressWarnings("unchecked")
        Spliterator<T> split = new Streams.ConcatSpliterator.OfRef<>(
                (Spliterator<T>) a.spliterator(), (Spliterator<T>) b.spliterator());
        Stream<T> stream = StreamSupport.stream(split, a.isParallel() || b.isParallel());
        return stream.onClose(Streams.composedClose(a, b));
    }
}
```

* **Stream<T> filter(Predicate<? super T> predicate);**  *`//line2`*
产生一个新流，其中包含调用流中满足predicate指定的谓词元素(中间操作)
* **<R> Stream<R> map(Function<? super T, ? extends R> mapper);**  *`//line3`*
产生一个新流，对调用流中的元素应用mapper，新流中包含这些元素。(中间操作)
* **IntStream mapToInt(ToIntFunction<? super T> mapper);** *`//line4`*
对调用流中元素应用mapper，产生包含这些元素的一个新IntStream流。(中间操作)
* **Stream<T> sorted();** *`//line12`*
* **Stream<T> sorted(Comparator<? super T> comparator);** *`//line13`*`
产生一个自然顺序排序或者指定排序条件的新流(中间操作)
* **void forEach(Consumer<? super T> action);**  *`//line17`*
* **void forEachOrdered(Consumer<? super T> action);** *`//line18`*
遍历了流中的元素(终端操作)
* **Optional<T> min(Comparator<? super T> comparator)**  *`//line30`*
* **Optional<T> max(Comparator<? super T> comparator);** *`//line31`*
获得流中最大最小值，比较器可以由自己定义，也可以使用JDK提供的(终端操作)

#### 小结一下

这一篇主要是介绍了流API的一些关键方法，和一些关键的概念，虽然稍微枯燥了一点，但是，不能否认，全面地学习流API，会让你对流API的认识会更加的深刻，所以如果时间允许，请再认真读读这一篇文章吧，当然，也可以在实践中慢慢认识它们，但是，对于这些基本概念的知识，你越早掌握，对你的益处是更加大的。到此为止，流API的基础知识已经学完了，后面的几篇文章我们就要开始更加深入地理解和运用他们实现一起强有力的功能了！
