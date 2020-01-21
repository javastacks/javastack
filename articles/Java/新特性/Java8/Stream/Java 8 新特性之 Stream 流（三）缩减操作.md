和前面两篇文章一起服用，效果会更佳。通过对流API的基础体验Demo和关键知识点的讲解，相信大家对流API都有一定的认识了，但是流API强大的功能，可不仅仅像前面两篇文章中说的那样简单，大家应该注意到，在第二篇中，我对Stream接口进行介绍的时候，并没有把他的全部方法都进行了解析说明。没错，从这一篇开始，那些还没有讲解的方法，很可能就开始变成我们的主角了，大家从题目上面应该知道了，本期我们要讲的是流API的缩减操作。

#### 何为缩减操作？

我们先考虑一下min()和max()，这两个方法我们在第一篇和第二篇中均有提到，其中min()是返回流中的最小值，而max()返回流中最大值，前提是他们存在。他们之间的特点是什么？①都返回了一个值②由一可知，他们是终端操作。如果我们用流API的术语来形容前面这两种特性的结合体的话，它们代表了缩减操作。因为每个缩减操作都把一个流缩减为一个值，好比最大值，最小值。当然流API，把min()和max()，count()这些操作称为特例缩减。即然说到了特例，肯定就有泛化这种概念了，他就是reduce()方法了，其实第二篇当中，他已经出现过了，只是当时我没有去强调他。
```
public interface Stream<T> extends BaseStream<T, Stream<T>> {
//、、、忽略其他无关紧要的元素
T reduce(T identity, BinaryOperator<T> accumulator);
Optional<T> reduce(BinaryOperator<T> accumulator);
<U> U reduce(U identity,
          BiFunction<U, ? super T, U> accumulator,
          BinaryOperator<U> combiner);
｝
``` 
Stream接口定义了三个版本的reduce()，我们先使用前面两个,
```
T reduce(T identity, BinaryOperator<T> accumulator);//1
Optional<T> reduce(BinaryOperator<T> accumulator);//2
```
第一个版本返回的是一个T类型的对象，T代表的是流中的元素类型！第二个版本是返回一个Optional类型对象。对于这两种形式，accumulator是一个操作两个值并得到结果的函数。在第一个版本当中，identity是这样一个值，对于涉及identity和流中任意的累积操作，得到的结果就是元素自身，没有任何改变。比如，如果是加法，他就是0，如果是乘法他就是1。

其中的accumulator是一个BinaryOperator<T>的类型，他是java.util.function包中声明的函数式接口，它扩展了BiFunction函数式接口.

```
@FunctionalInterface
public interface BinaryOperator<T> extends BiFunction<T,T,T> {
}

@FunctionalInterface
public interface BiFunction<T, U, R> {
   R apply(T t, U u);//notice
}
```

BiFunction接口中的apply()方法的原型在*`//notice`*。其中R指定了结果的类型，T，U分别是第一参数的类型和第二个参数的类型，因此apply()对他的两个操作数(t,u)应用到同一个函数上，并返回结果，而对BinaryOperator<T>来说，他在扩展 BiFunction时，指定了所有的类型参数都是相同的T，因此对于BinaryOperator<T>函数式接口的apply来说，他也就变成了  T apply(T t, T u),此外，还有一个需要注意的地方是，在应用reduce()时，apply()的第一个参数t,包含的是一个结果，u包含的是下一个元素。在第一次调用时，将取决于使用reduce()的版本，t可能是单位值，或者是前一个元素。

#### 缩减操作的三个约束

* **无状态**
* **不干预**
* **关联性**

无状态，这里可不是LOL的那个无状态，毕竟他退役了。相信读过第二篇文章的同学已经很容易理解了，简单来说无状态就是每个元素都被单独地处理，他和流中的其它元素是没有任何依赖关系的。不干预是指操作数不会改变数据源。最后，操作必须具有关联性，这里的关联性是指标准的数学含义，即，给定一个关联运算符，在一系列操作中使用该运算符，先处理哪一对操作数是无关紧要的。比如，(1 * 2) * 3  <===> 1 * (2 * 3)。`其中关联性，在并行流中，是至关重要的。`下面我用一个简单的例子带着大家实战一下泛化缩减操作reduce()的使用。

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

        Optional<Integer> sum = lists.stream().reduce((a, b) -> a + b);
        if (sum.isPresent()) System.out.println("list的总和为:" + sum.get());//21
        //<====> lists.stream().reduce((a, b) -> a + b).ifPresent(System.out::println);

        Integer sum2 = lists.stream().reduce(0, (a, b) -> a + b);//21
        System.out.println("list的总和为:" + sum2);

        Optional<Integer> product = lists.stream().reduce((a, b) -> a * b);
        if (product.isPresent()) System.out.println("list的积为:" + product.get());//720

        Integer product2 = lists.stream().reduce(1, (a, b) -> a * b);
        System.out.println("list的积为:" + product2);//720
    }
}
```

这个Demo主要是计算了一个list里面的总和，积的操作，大家可以和传统的算总和，积的方法进行对照，比一比衡量一下就有自己的答案了。但是如果你以为流API仅此而已，那你就错了。越是后面的东西，就越装B，我在刚知道他们的时候，反正是被吓了一跳的，但这些都是后话了，现在我们来详解一下Demo,并给出扩展的方向：我们这个例子主要是用了lambda表达式对list进行了求和，求积，对于第一个版本为说，求和的时候，identity的值为0，求积的时候它的值为1，强烈建议你们自己感受一下identity的变化对整个结果的变化产生什么 的影响，改变一下identity的值，再运行一下，你就有结果了，另一个扩展点是：

```
 Integer product3 = lists.stream().reduce(1, (a, b) -> {
            if (b % 2 == 0) return a * b; else return a;//这里你可以为所欲为!
 });
 System.out.println("list的偶数的积为:" + product3);//48
```
#### 小结一下

对于流的缩减操作来说,主要要知道,他只返回一个值,并且它是一个终端操作,然后还有的就是要知道缩减操作的三个约束了,其实最重要的就是无状态性和关联性了.这一小节要说的,也就这么多了,应该很容易就把他收到自己的技能树上面了。
