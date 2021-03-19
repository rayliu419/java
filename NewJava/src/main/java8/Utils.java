package main.java8;

import com.google.common.collect.Lists;

import java.util.List;

public class Utils {

    /**
     * 传统的Java写法，比较笨重。
     */
    public static List<Apple> filterGreenApplesOldStyle(List<Apple> apples) {
        List<Apple> result = Lists.newArrayList();
        for (Apple apple : apples) {
            if ("green".equals(apple.getColor())) {
                result.add(apple);
            }
        }
        return result;
    }

    /**
     * 使用Function Interface来过滤
     *
     * @param apples
     * @param p
     * @return
     */
    public static List<Apple> filterApplesWithApplePredicate(List<Apple> apples, ApplePredicate p) {
        List<Apple> result = Lists.newArrayList();
        for (Apple apple : apples) {
            if (p.test(apple)) {
                result.add(apple);
            }
        }
        return result;
    }

}
