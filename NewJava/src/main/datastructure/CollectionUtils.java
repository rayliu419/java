package main.datastructure;

import java.util.*;
import java.util.stream.Collectors;

public class CollectionUtils {

    public static void sortIntArray() {
        int x[] = new int[]{3, 2, 1, 4, 6, 7};
        // 调用默认的int的比较
        Arrays.sort(x);
        Arrays.stream(x).forEach(e -> System.out.println(e));
    }

    public static void sortMyItemObjectArray() {
        MyItem items[] = new MyItem[]{
                new MyItem("a", 1, 2),
                new MyItem("a", 2, 2),
                new MyItem("a", 2, 3)
        };
        // 调用默认的int的比较
        Arrays.sort(items);
        Arrays.stream(items).forEach(e -> System.out.println(e));
    }

    public static void sortMyItemArrayList() {
        ArrayList<MyItem> items = new ArrayList<>();
        items.add(new MyItem("a", 1, 2));
        items.add(new MyItem("a", 2, 2));
        items.add(new MyItem("a", 2, 3));
        // 调用MyItem的CompareTo方法
        Collections.sort(items);
        items.forEach(e -> System.out.println(e));
    }

    /**
     * 演示int[], Integer[], ArrayList<Integer>的转换
     * 1. Arrays.stream() 将T[] 转换成Stream<T>，然后在Stream上可以使用filter/map等函数了。
     * 对于基本类型，返回的稍有不同IntStream等
     * 2. 将Stream<T>转化成Stream<R>通过Stream的map。对于int/Integer有不同,
     *  IntStream -> Stream<Integer> : boxed
     *  Integer<Stream> -> IntStream : mapToInt
     * 3. 将Stream<T>转到T[] 用toArray
     *  对于基本类型，使用的是mapToXXX转成类似IntStream。
     *  对与复杂类型，使用的是类似这样的：
     *  Person[] men = people
     *                  .stream()
     *                  .filter(p -> p.getGender() == MALE)
     *                  .toArray(Person[]::new);
     *  即在toArray()中使用ClassName[]::new
     *  4. Collection的子类都有Stream的方法
     *
     */
    public static void transferType() {
        // int[] 转成Integer[] 和ArrayList<Integer>

        int x[] = new int[]{1, 2, 3, 4};
        Integer xx[] = Arrays
                // 获取IntStream
                .stream(x)
                // 转化成Stream<Integer>
                .boxed()
                // Stream<T> 转成R[]
                .toArray(Integer[]::new);
        List<Integer> xxx = Arrays
                .stream(x)
                .boxed()
                .collect(Collectors.toList());
        ArrayList<Integer> xxxx = new ArrayList(xxx);

        Arrays.stream(x).forEach(e -> System.out.println(e));
        Arrays.stream(xx).forEach(e -> System.out.println(e));
        xxx.stream().forEach(e -> System.out.println(e));
        xxxx.stream().forEach(e-> System.out.println(e));

        // Integer[] 转成int[] 和 ArrayList<Integer>
        int y[] = Arrays.stream(xx).mapToInt(Integer::valueOf).toArray();
        List<Integer> yy = Arrays.stream(xx).collect(Collectors.toList());

        Arrays.stream(y).forEach(e -> System.out.println(e));
        yy.stream().forEach(e -> System.out.println(e));

        // ArrayList<Integer> 转成 Integer[] 和 int[]
        Integer z[] = xxxx.stream().toArray(Integer[]::new);
        int zz[] = xxx.stream().mapToInt(Integer::valueOf).toArray();
    }

    /**
     * index = -2?是怎么回事
     */
    public static void search() {
        ArrayList<MyItem> myItems = new ArrayList<>();
        MyItem items[] = new MyItem[]{
                new MyItem("a", 1, 2),
                new MyItem("a", 2, 2),
                new MyItem("a", 2, 3)
        };
        myItems.add(items[0]);
        myItems.add(items[1]);
        myItems.add(items[2]);

        MyItem searchItem = new MyItem("a", 2, 3);
        int index = Collections.binarySearch(myItems, searchItem);
        System.out.println(index);
    }

}
