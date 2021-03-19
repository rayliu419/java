package main.java8;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.groupingBy;

public class JavaStream {

    /**
     * 演示reduce的使用
     *
     * @param list
     * @return
     */
    public int sum(List<Integer> list) {
        Optional<Integer> sum = list.stream().reduce((a, b) -> a + b);
        return sum.get();
    }

    public int max(List<Integer> list) {
        Optional<Integer> sum = list.stream().reduce(Integer::max);
        return sum.get();
    }


    /**
     * 演示groupby
     * @param menu
     * @return
     */
    public Map<CaloricLevel, List<Dish>> getDishesByCaloricLevel(List<Dish> menu) {
        Map<CaloricLevel, List<Dish>> dishesByCaloricLevel = menu
                .stream()
                .collect(groupingBy(dish -> {
                            if (dish.getCalorics() <= 400) return CaloricLevel.DIET;
                            else if (dish.getCalorics() <= 700) return CaloricLevel.NORMAL;
                            else return CaloricLevel.FAT;
                        }
                ));
        return dishesByCaloricLevel;
    }

    /**
     * 二级分类
     * @param menu
     * @return
     */
    public Map<String, Map<CaloricLevel, List<Dish>>> getWithTwoLevel(List<Dish> menu) {
        Map<String, Map<CaloricLevel, List<Dish>>> result = menu
                .stream()
                .collect(groupingBy(Dish::getType,
                        // 二级分类
                        groupingBy(dish -> {
                            if (dish.getCalorics() <= 400) return CaloricLevel.DIET;
                            else if (dish.getCalorics() <= 700) return CaloricLevel.NORMAL;
                            else return CaloricLevel.FAT;
                        }
                )));
        return result;
    }
}
