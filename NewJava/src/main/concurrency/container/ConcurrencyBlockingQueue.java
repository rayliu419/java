package main.concurrency.container;

import com.google.common.collect.Comparators;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Comparator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ConcurrencyBlockingQueue {

    public static void main(String[] args) {
        ConcurrencyBlockingQueue test = new ConcurrencyBlockingQueue();

        // 测试阻塞队列
//        test.producerConsumerWithQueue();

        // 测试优先级队列
        test.producerConsumerWithPriorityQueue();
    }


    /**
     * 注意Java的线程跟pthread是不同的，不存在主线程的概念。
     * 实际上main函数只是JVM里面的一个普通线程，所以JVM里面还有一个主线程，main运行结束后不会kill掉这个main启动的其他线程。
     * 放入5个item，消费5个item，正好结束了。
     */
    public void producerConsumerWithQueue() {

        LinkedBlockingQueue<WorkItem> queue = new LinkedBlockingQueue();

        for (int i = 0; i < 5; i++) {
            int finalI = i;
            new Thread(() -> {
                WorkItem workItem = new WorkItem(finalI);
                try {
                    Thread.sleep(finalI * 1000);
                    System.out.println("Producer thread " + String.valueOf(finalI) +
                            " put work item " + workItem.toString());
                    queue.put(workItem);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }

        /**
         * 改成
         * for (int j = 0; j < 6; j++)
         * 就永久不会结束，因为有个消费线程会阻塞
         */
        for (int j = 0; j < 5; j++) {
            int finalJ = j;
            new Thread(() -> {
                try {
                    WorkItem item = queue.take();
                    System.out.println(" Consumer thread " + String.valueOf(finalJ) +
                            " get work item " + item.toString());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    /**
     * 演示优先级并发队列的使用
     */
    public void producerConsumerWithPriorityQueue() {
        BlockingQueue<WorkItem> queue   = new PriorityBlockingQueue();
        // 一共5个work item
        int total = 5;
        AtomicInteger count = new AtomicInteger(0);

        for (int i = 0; i < 5; i++) {
            int finalI = i;
            new Thread(() -> {
                WorkItem workItem = new WorkItem(finalI);
                try {
                    Thread.sleep(finalI * 100);
                    System.out.println("Producer thread " + String.valueOf(finalI) +
                            " put work item " + workItem.toString());
                    queue.put(workItem);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }

        /**
         * 注意下面的poll()不能替换成take()。原因是有可能一个线程连续拿走了两个item，但是另外一个一直阻塞在take，导致某个线程
         * 一直在阻塞，即使加了检测条件也不行。
         */
        for (int j = 0; j < 2; j++) {
            int finalJ = j;
            new Thread(() -> {
                while(true) {
                    try {
                        int cur = count.get();
                        if (cur == total) {
                            break;
                        }
                        WorkItem item = queue.poll(1000, TimeUnit.MILLISECONDS);
                        if (item == null) {
                            continue;
                        }
                        Thread.sleep(finalJ * 1000);
                        System.out.println(" Consumer thread " + String.valueOf(finalJ) +
                                " get work item " + item.toString());
                        count.addAndGet(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }


    @Data
    @AllArgsConstructor
    public class WorkItem implements Comparable<WorkItem>{
        public int id;

        @Override
        public int compareTo(WorkItem o) {
            return Comparator.comparing(WorkItem::getId).compare(this, o);
        }
    }
}
