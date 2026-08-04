package leetcode;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 多字段排序（Multi-key Sort），面试高频，从 engineering 包迁移而来。
 *
 * 难点：待排序的 class 有多个字段，其中既有可直接比较的基本字段（int value），
 * 又有需要转换后才能比较的处理字段（String time "xx:yy:zz" → 秒）。
 *
 * 综合拼装一个 Comparator 的推荐方式 —— 「外围连接」：
 *   1. 按字段拆分：每个字段写一个独立的小比较器，只管自己的规则；
 *   2. 转换内聚：需要处理字段时，转换逻辑（time → 秒）封装在该字段比较器内部；
 *   3. 链式串联：用 thenComparing 按优先级把各字段比较器连成"字典序"。
 *
 * thenComparing 语义：前一个比较器返回 0 时才进入下一个，天然实现"先按 value、
 * 相等再按 time"的多级排序，无需手写嵌套 if-else。
 */
public class MultiSort {

    /** 待排序对象：value 基本字段直接比较，time 处理字段需转秒 */
    static class Item {
        private int value;
        private String time;

        public int getValue() { return value; }
        public void setValue(int value) { this.value = value; }
        public String getTime() { return time; }
        public void setTime(String time) { this.time = time; }
    }

    /** "xx:yy:zz" → 秒；转换函数独立成方法，比较器内部调用，便于复用与单测 */
    public static int getTime(String time) {
        String[] res = time.split(":");
        return Integer.parseInt(res[0]) * 3600
                + Integer.parseInt(res[1]) * 60
                + Integer.parseInt(res[2]);
    }

    /**
     * 外围连接：value 用 JDK 提供的 comparingInt 直接比较；
     * time 字段单独写一个比较器（内部调用 getTime 完成转换），
     * thenComparing 把二者串联。
     */
    public static void multiSort(Item[] items) {
        Comparator<Item> timeComparator = (o1, o2) -> getTime(o1.getTime()) - getTime(o2.getTime());

        Comparator<Item> c = Comparator.comparingInt(Item::getValue)
                .thenComparing(timeComparator);

        Arrays.sort(items, c);
    }
}
