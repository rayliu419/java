package datastructure;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.TreeMap;

public class MyMap {

    public static void IdentityMapBasic() {
        // HashMap使用equals比较key，IdentityHashMap使用==比较
        IdentityHashMap<String, String> identityHashMap = new IdentityHashMap<>();
        String s1 = new String("test");
        String s2 = new String("test");
        identityHashMap.put(s1, "value1");
        identityHashMap.put(s2, "value2");

        System.out.println(identityHashMap.get("test"));
        System.out.println(identityHashMap);
    }

    public static void LinkedHashMapBasic() {
        // 此数据结构的使用场景?
        // 拥有与HashMap一样的功能，但是多了一个按序访问的双向链表，可以用来实现底层Cache？
    }

    /**
     * TreeMap的使用，可以用来实现一致性hash算法
     * key访问的时候是有序的，存储的时候也是有序的。
     */
    public static void TreeMapBasic() {
        HashMap<MyItem, String> m = new HashMap<>();
        m.put(new MyItem("a", 3, 1), "a");
        m.put(new MyItem("b", 2, 2), "b");
        m.put(new MyItem("c", 1, 3), "c");

        TreeMap<MyItem, String> treeMap = new TreeMap(m);
        for (MyItem item : treeMap.keySet()) {
            System.out.println(item);
        }

    }

}
