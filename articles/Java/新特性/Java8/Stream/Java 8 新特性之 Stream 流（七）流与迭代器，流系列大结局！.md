恭喜你们，马上就要学完Java8 Stream流的一整系列了，其实我相信Stream流对很多使用Java的同学来说，都是一个知识盲点，因为这个原因，我才这么细致地讲解Stream流的各个知识点，通过这一整个系列，我相信只要认真看了的同学，都已掌握的差不多了，就差实战了。

其实我在工作过程中，Stream流对我的帮助真的挺大的，所以，我想和大家分享一下，于是这系列的文章就出来了。

在本系列文章发布的时候，有很多同学反映，Stream流的调试和forEach()的调试都不是特别友好，那本篇给出一个折中的调试方法，虽然不能完美解决调试的问题，但是基本上已经能解决绝大部分的调试问题了，没错，就是迭代器了，当然迭代器除了能辅助调试以外，他最重要的还是遍历功能。

这篇文章除了介绍传统的迭代器外，还会介绍Java8中新增的Spliterator，因为在并行流的场合，Spliterator相当的好用。

#### 我们先简单介绍一下传统的迭代器

迭代器是实现了Iterator接口的对象，并且Iterator接口允许遍历，获取或者移除元素。

```
public interface Iterator<E> {
  Iterator<T> iterator();
  E next();
  default void remove() {
        throw new UnsupportedOperationException("remove");
  }
  default void forEachRemaining(Consumer<? super E> action) {
        Objects.requireNonNull(action);
        while (hasNext())
            action.accept(next());
  }

}
```

使用Iterator的一般步骤：

* (1)通过iterator()方法，获取指向集合或流开头的迭代器。

* (2)建立一个hasNext()方法调用循环，只要hasNext()返回true,就继续迭代。

* (3)在循环中，通过调用next()方法获取每个元素。

```
private static void learnIterator() {
    List<String> lists = Arrays.asList("A", "B", "C", "D");

    Iterator<String> iterator = lists.stream().iterator();

    while (iterator.hasNext()) {
        System.out.println(iterator.next());
    }
}
```

但是如果我们不修改集合的情况下，使用forEach()其实更加便利的，其实两种方式本质上面是一样的，在你编译之后，forEach()会转换成迭代器的方式进行操作了。有了迭代器，相信调试就得方便起来了，即使不能直接调试，也可以通过迭代器，反推之前，可能发生了什么。

值得注意的一点是：Java8给基本类型的流提供了额外的迭代器：PrimitiveIterator.OfInt,PrimitiveIterator.OfDouble,PrimitiveIterator.OfLong,PrimitiveIterator,但这些接口都是来扩展自Iterator接口的，所以使用上面也是相同的。

#### Spliterator

Spliterator是Java8新增的一种迭代器，这种迭代器由`Spliterator`接口定义，Spliterator也有普通的遍历元素功能，这一点与刚才说的迭代器类似的，但是，但是Spliterator方法和使用迭代器的方法是不同的。

另外，它提供的功能要比Iterator多。最终要的一点，Spliterator支持并行迭代。

```
public interface Spliterator<T> {
    boolean tryAdvance(Consumer<? super T> action);
    int characteristics();
    long estimateSize();
   Spliterator<T> trySplit();
   default void forEachRemaining(Consumer<? super T> action) {
        do { } while (tryAdvance(action));
   }
}
```

将Spliterator用于基本迭代任务是非常简单的，只需要调用tryAdvance()方法，直至其返回false.如果要为序列中的每个元素应用相同的动作，那么forEachRemaining()提供了一种更加高效的替代方法。

对于这两个方法，在每次迭代中将发生的动作都由`Consumer`对象定义的操作来决定，`Consumer`也是一个函数式接口，估计大家已经知道怎么分析了，这里就不带大家分析了，他的动作是指定了在迭代中下一个元素上执行的操作。下面来一个简单的例子：

```
private static void learnIterator() {
    List<String> lists = Arrays.asList("A", "B", "C", "D");

    Spliterator<String> spliterator = lists.stream().spliterator();

    while (spliterator.tryAdvance(System.out::println));
}
```

使用forEachRemaining()方法改进这个例子：

```
private static void learnIterator() {
    List<String> lists = Arrays.asList("A", "B", "C", "D");
    lists.stream().spliterator().forEachRemaining(System.out::println);
}
```

注意，使用这个方法时，不需要提供一个循环来一次处理一个元素，而是将各个元素作为一个整体来对待，这是Spliterator的又一个优势。

Spliterator的另一个值得注意的方法是trySplit()，它将被迭代的元素划分成了两部分，返回其中一部分的新Spliterator,另一部分则通过原来的Spliterator访问。下面再给一个简单的例子

```
private static void learnIterator() {
    List<String> lists = Arrays.asList("A", "B", "C", "D");

    Spliterator<String> spliterator = lists.stream().spliterator();

    Spliterator<String> stringSpliterator = spliterator.trySplit();

    if (stringSpliterator != null) stringSpliterator.forEachRemaining(System.out::println);

    System.out.println("------------------");

    spliterator.forEachRemaining(System.out::println);
}
```

打印的结果：

```
A
B
------------------
C
D
```

这里只是给大家提供了这种方式而已，例子本身没有什么含义，但是当你对大数据集执行并行处理时，拆分可能是极有帮助的了。但更多情况下，要对流执行并行操作时，使用其他某个Stream方法更好，而不必手动处理Spliterator的这些细节，Spliterator最适合的场景是，给定的所有方法都不能满足你的要求时，才考虑。

#### 最后来一个总结

到这里，Java8 Stream流的知识，基本上已经介绍完了，缩减操作，并行流，映射，还有收集是Stream流的核心内容，但是要想掌握好这些内容，第二篇的基本知识你是跑不掉的，因此我的建议还是老老实实，从第一篇开始认真看，看完这7篇文章，你基本已经非常系统地掌握Java8的Stream流知识了。

大家也不要忘记`Collectors`类，里面提供给我们的方法，基本上能处理各种各样的收集元素问题了，如果你从第一篇文章开始看，一直看到这里了，那你以后尽量多使用Stream流来处理集合吧，只是学会这些知识是没有用的，要多在你们的项目中运用他们，这样才能更加好地理解，如果大家在使有的过程中遇到什么样的问题，欢迎来这里面留言，我看到了，一定第一时间和大家一起探索解决的方法。

如果大家对我的文章感兴趣的话，也可以关注一下，后面我估计会出一系列数据结构方面的文章，当然是Java的一整个系列数据结构啦。

如果你觉得Stream流这系列文章，你们能看得比较
懂的话，那我相信数据结构系列的文章你们也会有同样的感觉的，最后，谢谢大家，祝你们技术越来越强，生活越来越愉快，最重要的还是马上找到女朋友，哈哈哈哈。
