package datastructure;

import java.util.ArrayDeque;
import java.util.PriorityQueue;

public class JavaQueue {

    // use ArrayDeque as queue.
    // ArrayDeque既可以当队列用，也可以当stack用。
    public static void queueBasic() {
        ArrayDeque<MyItem> myItems = new ArrayDeque<>();
        myItems.add(new MyItem("a", 1, 1));
        myItems.add(new MyItem("b", 2, 2));
        myItems.add(new MyItem("c", 3, 3));

        while (!myItems.isEmpty()) {
            System.out.println(myItems.peek());
            myItems.poll();
        }
    }

    /**
     * PQ使用了MyItem定义的CompareTo方法
     */
    public static void priorityQueue() {
        PriorityQueue<MyItem> priorityQueue = new PriorityQueue<>();
        priorityQueue.add(new MyItem("a", 1, 2));
        priorityQueue.add(new MyItem("a", 2, 2));
        priorityQueue.add(new MyItem("a", 2, 3));

        while (!priorityQueue.isEmpty()) {
            System.out.println(priorityQueue.peek());
            priorityQueue.poll();
        }

    }

}
