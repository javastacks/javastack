随着对流API认识的慢慢深入，本章我们要讨论的知识点是流API里面的并行流了。

在开始讨论并行流之前，我先引发一下大家的思考，就你看到这篇文章的时间，你们是不是经常听到，Intel i7 CPU什么8核16线程，什么Android手机8核4GB这种消息，既然我们是处于一个多核处理器的时代，你们有没有想过并行地操作数组和集合框架，从而高速地执行我们对数组或者集合的一些操作呢？

或许你有想过这个问题，但是因为并行编程比较复杂，所以这些想法还停留在你的脑海当中，又或者你已经在路上了，反正你们就是最棒的（我他妈都这么夸你们了，就不能点个喜欢？）。

不管如何，在你看到这一篇文章的时候，我将带你走向并行地操作数组或者集合，当然是使用我们的并行流知识啦。

#### 并行流

并行编程可谓是十分复杂并且很容易出错的，这估计就是我们绝大部分人的拦脚石。刚好Stream流库给我们解决了这个问题，在流API库里面提供了轻松可靠的并行操作。要想并行处理流相当简单，只需要使用一个并行流就可以了。

如第二篇文章中提到的那样，我们获取一个并行流是非常简单的，只需要对流调用一下parallel()就可以获取到一个并行流了（什么你居然不知道？那么多人看了我的文章，估计你要被他们甩开几条街了，赶紧回去看吧。），第二种方式就更加简单了，我们可以使用Collection接口提供给我们parallelStream(),也是可以获取到一个并行流的。

当然，并行操作肯定是需要环境支持的，你搞了一台一核一线程的小霸王，来跑我的高大上并行流，我也只能慢慢来了。如果你不是小霸王，那我们可以开始这节课的实战了，先拿上一篇的例子来改一下先，如果你不认真观察，你都找不出他们的不同之处：

```
public class Main {

    public static void main(String[] args) {
        learnStream();
    }



    private static void learnStream() {
        List<Integer> lists = new ArrayList<>();
        lists.add(1);
        lists.add(2);
        lists.add(3);
        lists.add(4);
        lists.add(5);
        lists.add(6);

        Optional<Integer> sum = lists.parallelStream().reduce((a, b) -> a + b);//这里把stream()换成了parallelStream（）
        if (sum.isPresent()) System.out.println("list的总和为:" + sum.get());//21
        //<====> lists.stream().reduce((a, b) -> a + b).ifPresent(System.out::println);

        Integer sum2 = lists.stream().reduce(0, (a, b) -> a + b);//21
        System.out.println("list的总和为:" + sum2);

        Optional<Integer> product = lists.stream().reduce((a, b) -> a * b);
        if (product.isPresent()) System.out.println("list的积为:" + product.get());//720

        Integer product2 = lists.parallelStream().reduce(1, (a, b) -> a * b);//这里把stream()换成了parallelStream（）
        System.out.println("list的积为:" + product2);//720
    }
}
```

得到结果和上一篇文章的一模一样。但是因为乘法和加法操作是可以发生在不同的线程里面的，因此这两个例子，在数据源足够大的时候，他们的运行的时间，差别相当地大了啊。

一般来说，应用到并行流的任何操作都必须是符合缩减操作的三个约束条件，无状态，不干预，关联性！因为这三大约束确保在并行流上执行操作的结果和在顺序流上执行的结果是相同的。

我们在上一篇讲缩减操作的时候，提到了三个reduce(),但是我们只讲了两个，我就不和你们皮了，直接开讲剩下的那一个，在并行流里面，你们会发现这个版本的reduce()才是真爱啊！

```
public interface Stream<T> extends BaseStream<T, Stream<T>> {
//、、、忽略其他无关紧要的元素
<U> U reduce(U identity,
          BiFunction<U, ? super T, U> accumulator,
          BinaryOperator<U> combiner);
｝
```

在reduce()的这个版本当中，accumulator被称为累加器，combiner被称为合成器，combiner定义的函数将accumulator提到的两个值合并起来，因此，我们可以把上面的那个例子改成：

```
    private static void reduce3th() {
        List<Integer> lists = new ArrayList<>();
        lists.add(1);
        lists.add(2);
        lists.add(3);
        lists.add(4);
        lists.add(5);
        lists.add(6);

        Integer product2 = lists.parallelStream().reduce(1, (a, b) -> a * b,
                                                            (a, b) -> a * b);
        System.out.println("list的积为:" + product2);//720
    }
```

他们得到的结果还是一样的。

你们可能以为accumulator和combiner执行的操作是相同的，但其实他们是可以不同的，下面的例子，你们要认真看了：假设List里面有三个Integer类型的元素分别为1，2，3。

现在的需求是分别让List里面的每个元素都放大两倍后，再求积。这个需求的正确答案应该是48;

```
    private static void reduce3th() {
        List<Integer> lists = new ArrayList<>();
        lists.add(1);
        lists.add(2);
        lists.add(3);

        Integer product = lists.parallelStream().reduce(1, (a, b) -> a *  (b * 2),
                                                           (a, b) -> a * b);
        System.out.println("product:" + product);//48
    }
```

累加器部分是将两个元素分别放大两倍后，再相乘,合并器，是将两个部分相乘！如果能理解这里，恭喜你，你的技能有相当大的长进了！估计Stream流你就可以无往而不利了。

如果你还不能理解，就应该继续往下看了，跟着我的步伐慢慢走：

```
    累加器部分（水平向右）
        accumulator
-----------------------------›
thread-1:   1 * 1 * 2   =   2    |    合并器方向（竖直向下）
thread-2:   1 * 2 * 2   =   4    |         combiner
thread-3:   1 * 3 * 2   =   6    |   因此最终的答案是2  *  4  *  6  =   48（没毛病）
                                 ˇ
注：水平方向最前面的1就是identity的值
```

此时，accumulator和combiner执行的操作是不是一定不能相同了。理解这些，对于理解并行流是非常重要的。

如果此时的combiner还是和accumulator相同，那么结果是什么样的呢：请看：

```
    private static void reduce3th() {
        List<Integer> lists = new ArrayList<>();
        lists.add(1);
        lists.add(2);
        lists.add(3);

        Integer product = lists.parallelStream().reduce(1, (a, b) -> a *  (b * 2),
                                                           (a, b) -> a * b * 2 );
        System.out.println("product:" + product);//192
    }
```

192这个答案是怎么来的？

```
    累加器部分（水平向右）
        accumulator
-----------------------------›
thread-1:   1 * 1 * 2   =   2          |    合并器方向（竖直向下）
thread-2:   1 * 2 * 2   =   4  *  2    |         combiner
thread-3:   1 * 3 * 2   =   6  *  2    |   因此最终的答案是2  *  （ 4  *  2 ） *  （6  *  2）  =   192（没毛病）
                                       ˇ
注：水平方向最前面的1就是identity的值
```

#### 顺序流&并行流&无序流之间的切换操作

对于这三种流的切换，在BaseStream接口中提供了相应的方法，如果你还没有记住，回头再看一下第二篇文章吧。

关于使用并行流的时候，还有一个点需要记住：如果集合中或者数组中的元素是有序的，那么对应的流也是有序的。但是在使用并行流时，有时候流是无序的就能获得性能上的提升。因为如果流是无序的，那么流的每个部分都可以被单独的操作，而不需要与其他部分协调，从而提升性能。（又是无状态，说好的退休了呢）。所以当流操作的顺序不重要的时候，可以通过BaseStream接口提供的unordered()方法把流转换成一个无序流之后，再进行各种操作。

另外一点:forEach()方法不一定会保留并行流的顺序，如果在对并行流的每个元素执行操作时，也希望保留顺序，那么可以使用forEachOrdered()方法，它的用法和forEach()是一样的。因为在发布第一篇文章的时候，大家对forEach的反应比较大，很多人其实对forEach都有想法：比如调试难，等等。借这个机会，我谈一谈我对for&forEach的看法。

我们在访问一个数组元素的时候，最快的方式肯定是通过索引去访问的吧，而for循环遍历的时候就是通过下标进行的，所以效率那是相当的高，但是当我们的数据结构不是数组的时候，比如是链表的时候，可想而知，for循环的效率是有多低，但是forEach底层采用的是迭代器的方式，他对数据结构是没有要求的，不管上层的数据结构是什么，他都能保证高效地执行！

因此我的最终答案:如果数据结构是ArrayList这种数据结构，那你可以采用for,但是你的数据结构如果是LinkList那你千万别再用for,应该果断采用forEach,因为数据一多起来的，for此时的效率低得可怜，说不定你的机器就瘫痪了。这也是优化的一个小技巧吧，希望能帮助大家。

#### 小结一下

并行流学会了，你的功力，真的就增长了。效率再也不是问题了，基本上关于并行流的方方面面，这篇文章都已经说提到了，但是Stream在JDK中的变化还是挺快的，我一旦发现有什么改动，会最快地更新这篇文章。下一篇我们继续探索新知识点。
