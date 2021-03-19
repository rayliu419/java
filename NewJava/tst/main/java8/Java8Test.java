package main.java8;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.Comparator;
import java.util.List;

public class Java8Test {

    public final List<Apple> apples = Lists.newArrayList(
            new Apple("green", 150),
            new Apple("yellow", 100),
            new Apple("red", 100)
    );

    /**
     * 下面的代码主要是使用了java8的function interface/lambda和generic的混合使用。
     * lambda主要是可以代替接口的使用，使得代码更简洁。
     */
    @Test
    public void testFilterGreenApples() {
        System.out.println(Utils.filterGreenApplesOldStyle(apples));
    }

    @Test
    public void testFilterGreenApplesWithApplePredicate() {
        System.out.println(Utils.filterApplesWithApplePredicate(apples, new AppleGreenColorPredicate()));
    }

    /**
     * 通常为了使用ApplePredicate，需要实现ApplePredicate，但是每次使用的时候都要定义新的子类，不轻量级。
     * 所以可以使用匿名接口实现，在使用的时候直接实现。
     */
    @Test
    public void testFilterApplesWithAnonymousApplePredicate() {
        List<Apple> redApples = Lists.newArrayList();
        redApples = Utils.filterApplesWithApplePredicate(apples, new ApplePredicate() {
            @Override
            public boolean test(Apple apple) {
                return "red".equals(apple.getColor());
            }
        });
        System.out.println(redApples);
    }

    /**
     * 比上述更简单的一种方法， 使用lambda替代ApplePredicate的作用。
     * filterApplesWithApplePredicate 看起来接受的是(List<Apple> apples, ApplePredicate p)
     * 实际上ApplePredicate相当于一个函数指针
     * boolean test(Apple apple);
     * lambda 实际上实现的就是上述的函数
     */
    @Test
    public void testFilterApplesWithLambda() {
        System.out.println(Utils.filterApplesWithApplePredicate(apples,
                (Apple apple) -> "red".equals(apple.getColor())));
    }

    /**
     * Generic的使用导致filter算法变成了泛型算法了。
     */
    @Test
    public void testFilterWithUtilsWithGeneric() {
        List<Apple> redApples = UtilsWithGeneric.filter(apples,
                (Apple apple) -> "red".equals(apple.getColor()));
        System.out.println(redApples);
        List<Integer> integers = Lists.newArrayList(1, 2, 3, 4, 5);
        System.out.println(UtilsWithGeneric.filter(integers, (Integer i) -> i % 2 == 0));
    }

    @Test
    public void testAppleComparator() {
        apples.sort(new AppleComparator());
        System.out.println(apples);
    }

    /**
     * 直接使用comparator接口
     */
    @Test
    public void testAppleSortWithAnonymous() {
        apples.sort(new Comparator<Apple>() {
            @Override
            public int compare(Apple o1, Apple o2) {
                return o1.getWeight().compareTo(o2.getWeight());
            }
        });
        System.out.println(apples);
    }

    /**
     * lambda，更简单的接口
     */
    @Test
    public void testAppleSortWithLambda() {
        apples.sort((a1, a2) -> a1.getWeight().compareTo(a2.getWeight()));
        System.out.println(apples);
    }

    @Test
    public void testSortWithComparing() {
        // Comparator.comparing接受Function来生成Comparator对象
        apples.sort(Comparator.comparing(Apple::getWeight));
        System.out.println(apples);

        apples.sort(Comparator.comparing(Apple::getWeight).reversed());
        System.out.println(apples);

        // thenComparing的使用
        apples.sort(Comparator.comparing(Apple::getWeight)
                .reversed()
                .thenComparing(Apple::getColor));
        System.out.println(apples);
    }
}
