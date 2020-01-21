我们前面的五篇文章基本都是在说将一个集合转成一个流，然后对流进行操作，其实这种操作是最多的，但有时候我们也是需要从流中收集起一些元素，并以集合的方式返回，我们把这种反向操作称为收集。

流API也给我们提供了相应的方法。

#### 如何在流中使用收集功能？

我们先看一看流API给我们提供的方法：

```
public interface Stream<T> extends BaseStream<T, Stream<T>> {
//...忽略那些不重要的东西
<R> R collect(Supplier<R> supplier,
                  BiConsumer<R, ? super T> accumulator,
                  BiConsumer<R, R> combiner);
<R, A> R collect(Collector<? super T, A, R> collector);
｝
```

流API中给我们提供了两种，我给大家分析一下

**<R, A> R collect(Collector<? super T, A, R> collector);**

其中R指定结果的类型，T指定了调用流的元素类型。内部积累的类型由A指定。collectorFunc是一个收集器，指定收集过程如何执行，collect()方法是一个终端方法。

虽然我们基本上很少会用到自定义的collectorFunc,但是了为扩展大家的知识面，我们还是简单地聊一聊Collector，Because it's my style! 

`Collector`接口位于`java.util.stream`包中的声明，它的容颜是这样的：

```
package java.util.stream;
public interface Collector<T, A, R> {
      Supplier<A> supplier();
      BiConsumer<A, T> accumulator();
      BinaryOperator<A> combiner();
      Function<A, R> finisher();
}
```

其中T、A、R的含义和上面是一样的`其中R指定结果的类型，T指定了调用流的元素类型。内部积累的类型由A指定`。

但是这一篇我们不实现他们，因为JDK已经给我们提供了很强大的方法了，他们位于`java.util.stream`下面的`Collectors`类，我们本篇也主要是使用`Collectors`来实现收集的功能。

`Collectors`类是一个最终类，里面提供了大量的静态的收集器方法，借助他，我们基本可以实现各种复杂的功能了。

我们来看一下toList和toSet方法:

```
public static <T>  Collector<T, ?, List<T>> toList()
public static <T> Collector<T, ?, Set<T>> toSet()
```

其中`Collectors#toList()`返回的收集器可以把流中元素收集到一个List中，`Collectors#toSet()`返回的收集器可以把流中的元素收集到一个Set中。比如：如果你想把元素收集到List中，你可以这样用，`steam.collect(Collectors.toList)`。

接下来，我们把我们的王者荣耀团队经济例子修改一下，把明星玩家和当前获得的金币数收集到一个List里面，把出场的英雄收集到一个Set里面：

```
#玩家使用的英雄以及当前获得的金币数
public class HeroPlayerGold {
    /** 使用的英雄名字 */
    private String hero;
    /** 玩家的ID */
    private String player;
    /** 获得的金币数 */
    private int gold;


    public HeroPlayerGold(String hero, String player, int gold) {
        this.hero = hero;
        this.player = player;
        this.gold = gold;
    }

    @Override
    public String toString() {
        return "HeroPlayerGold{" +
                "hero='" + hero + '\'' +
                ", player='" + player + '\'' +
                ", gold=" + gold +
                '}';
    }
//省略get/set
}

#出场的英雄
public class Hero {
    /** 使用的英雄名字 */
    private String hero;

    public Hero(String hero) {
        this.hero = hero;
    }

    @Override
    public String toString() {
        return "Hero{" +
                "hero='" + hero + '\'' +
                '}';
    }
//省略get/set
}

#测试类
public class Main {
    public static void main(String[] args) {
        learnCollect();
    }

    private static void learnCollect() {
        List<HeroPlayerGold> lists = new ArrayList<>();
        lists.add(new HeroPlayerGold("盖伦", "RNG-Letme", 100));
        lists.add(new HeroPlayerGold("诸葛亮", "RNG-Xiaohu", 300));
        lists.add(new HeroPlayerGold("露娜", "RNG-MLXG", 300));
        lists.add(new HeroPlayerGold("狄仁杰", "RNG-UZI", 500));
        lists.add(new HeroPlayerGold("牛头", "RNG-Ming", 500));

        List<PlayerGold> playerGolds = lists.stream()
                .map(plary -> new PlayerGold(plary.getPlayer(), plary.getGold()))
                .collect(Collectors.toList());
        System.out.println("============PlayerGold begin==============");
        playerGolds.forEach(System.out::println);
        System.out.println("============PlayerGold end================\n");



        Set<Hero> heroes = lists.stream().map(player -> new Hero(player.getHero())).collect(Collectors.toSet());
        System.out.println("============Hero begin==============");
        heroes.forEach(System.out::println);
        System.out.println("============Hero end================");
    }
}
```

输出的日志：

```
============PlayerGold begin==============
PlayerGold{player='RNG-Letme', gold=100}
PlayerGold{player='RNG-Xiaohu', gold=300}
PlayerGold{player='RNG-MLXG', gold=300}
PlayerGold{player='RNG-UZI', gold=500}
PlayerGold{player='RNG-Ming', gold=500}
============PlayerGold end================

============Hero begin==============
Hero{hero='露娜'}
Hero{hero='牛头'}
Hero{hero='盖伦'}
Hero{hero='狄仁杰'}
Hero{hero='诸葛亮'}
============Hero end================
```

看到这里，大家有感受到流API的威力了吗？提示一下，封装一个工具类，然后结合一FastJson这种东西一起使用！是真的好用啊！其实将数据从集合移到流中，或者将数据从流移回集合的能力，是流API给我们提供的一个强大特性，因为这允许通过流来操作集合，然后把流重新打包成集合。此外，条件合适的时候，让流操作并行发生，提高效率。

接下来我们分析第二个方法，

```
 <R> R collect(Supplier<R> supplier,
                  BiConsumer<R, ? super T> accumulator,
                  BiConsumer<R, R> combiner);
```

我们第二个版本的收集方法，主要是可以在收集的过程中，给予更多的控制。其中supplier指定如何创建用于保存结果的对象，比如，要使用ArrayList作为结果的集合，需要指定它的构造函数，accumulator函数是将一个元素添加到结果中，而combiner函数合并两个部分的结果。

大家应该发现了吧，他的工作方式和我们第三篇介绍缩减操作时的reduce方法是很像的。它们都必须是无状态和不干预的，并且必须有关联性，三个约束条件缺一不可。

`Supplier`也是`java.util.function`包中的一个函数式接口:

```
@FunctionalInterface
public interface Supplier<T> {
    T get();
}
```

只有一个get()，并且是没有参数的，在collect()方法返回一个R类型的对象，并且get()方法返回一个指向集合的引用。

而accumulator,combiner的类型是`BiConsumer`，他们也是`java.util.function`包中的一个函数式接口:

```
@FunctionalInterface
public interface BiConsumer<T, U> {
    void accept(T t, U u);
}
```

其中t,u执行某种类型的操作，对于accumulator来说，t指定了目标集合，u指定了要添加到该集合的元素。对于combiner来说，t和u指定的是两个要被合并的集合。

我们把前面的例子改变一下，然后也详细地说一下，在没有用lambda和使用lambda之后的区别：

这个是没有使用lambda前的：

```
private static void learnCollect() {
        List<HeroPlayerGold> lists = new ArrayList<>();
        lists.add(new HeroPlayerGold("盖伦", "RNG-Letme", 100));
        lists.add(new HeroPlayerGold("诸葛亮", "RNG-Xiaohu", 300));
        lists.add(new HeroPlayerGold("露娜", "RNG-MLXG", 300));
        lists.add(new HeroPlayerGold("狄仁杰", "RNG-UZI", 500));
        lists.add(new HeroPlayerGold("牛头", "RNG-Ming", 500));


        lists.stream().collect(new Supplier<HashSet<HeroPlayerGold>>() {
                                   @Override
                                   public HashSet<HeroPlayerGold> get() {
                                       return new HashSet<>();
                                   }
                               },//第一个参数
                new BiConsumer<HashSet<HeroPlayerGold>, HeroPlayerGold>() {
                    @Override
                    public void accept(HashSet<HeroPlayerGold> heroPlayerGolds, HeroPlayerGold heroPlayerGold) {
                        heroPlayerGolds.add(heroPlayerGold);
                    }
                },//第二个参数
                new BiConsumer<HashSet<HeroPlayerGold>, HashSet<HeroPlayerGold>>() {
                    @Override
                    public void accept(HashSet<HeroPlayerGold> heroPlayerGolds, HashSet<HeroPlayerGold> heroPlayerGolds2) {
                        heroPlayerGolds.addAll(heroPlayerGolds2);
                    }
                }//第三个参数
        ).forEach(System.out::println);
    }
```

在没有使用lambda前，虽然看起来的让人眼花缭乱的，但不得不说，他其实能帮助我们实现非常强大的功能，我们自定义的收集过程，全部都可以交给这个家伙，我们用lambda整理一下：

```
private static void learnCollect() {
        List<HeroPlayerGold> lists = new ArrayList<>();
        lists.add(new HeroPlayerGold("盖伦", "RNG-Letme", 100));
        lists.add(new HeroPlayerGold("诸葛亮", "RNG-Xiaohu", 300));
        lists.add(new HeroPlayerGold("露娜", "RNG-MLXG", 300));
        lists.add(new HeroPlayerGold("狄仁杰", "RNG-UZI", 500));
        lists.add(new HeroPlayerGold("牛头", "RNG-Ming", 500));


        lists.stream().collect(() -> new HashSet<>(),
                                (set,elem)->set.add(elem),
                                (setA,setB)->setA.addAll(setB)
        ).forEach(System.out::println);
        
}
```

大家以为到这里就结束了吗？其实还可以使用方法引用和构造函数引用来简化：

```
private static void learnCollect() {
        List<HeroPlayerGold> lists = new ArrayList<>();
        lists.add(new HeroPlayerGold("盖伦", "RNG-Letme", 100));
        lists.add(new HeroPlayerGold("诸葛亮", "RNG-Xiaohu", 300));
        lists.add(new HeroPlayerGold("露娜", "RNG-MLXG", 300));
        lists.add(new HeroPlayerGold("狄仁杰", "RNG-UZI", 500));
        lists.add(new HeroPlayerGold("牛头", "RNG-Ming", 500));


        lists.stream().collect(HashSet::new,
                               HashSet::add,
                               HashSet::addAll
        ).forEach(System.out::println);
}
```

#### 小结一下

本篇带大家入门了Stream的收集操作，但是有了些这入门操作，我相信，你在我的演变过程中已经发现了扩展点了，不管是supplier，accumulator还是combiner，都可以在里面放一些特别的操作进去，从而满足你们的各种要求。

另外一个点，大家一定不要忘记了Collectors这个最终类，里面已经提供了很多很强大的静态方法，如果你们遇到一些特别的需求，首先要想到的应该是Collectors，如果里面的方法都不能实现你的要求，再考虑通过第二个版本的collect()方法实现你的自定义收集过程吧。





