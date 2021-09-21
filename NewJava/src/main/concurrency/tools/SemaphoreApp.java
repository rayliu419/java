package main.concurrency.tools;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class SemaphoreApp {

    public static void main(String[] args) {
        SemaphoreApp app = new SemaphoreApp();

//        app.useSemaphore();

        app.useCounterWithMutex();
    }

    public void useSemaphore() {
        // Semaphore with 2 permits
        Semaphore s = new Semaphore(2);
        ExecutorService ex = Executors.newFixedThreadPool(4);
        // Executing 6 times with a pool of 4 threads
        for (int i = 0; i < 6; i++) {
            ex.execute(new HeavyDuty(s));
        }
        ex.shutdown();
    }

    public void useCounterWithMutex() {
        int count = 5;
        ExecutorService executorService = Executors.newFixedThreadPool(count);
        CounterUsingMutex counter = new CounterUsingMutex();
        IntStream.range(0, count)
                .forEach(user -> executorService.execute(() -> {
                    try {
                        counter.increase();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }));
        // 这个操作不会阻塞，也不会等待线程结束。但是告诉线程池不接受新任务。
        executorService.shutdown();
        // shutdown + awaitTermination 是标准的等待所有task结束的方法。
        // shutdown先不接受新task，否则awaitTermination可能由于task新加入永不停止。
        try {
            executorService.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 返回当前的阻塞的队列
        System.out.println(counter.hasQueuedThreads());
        System.out.println(counter.getCount());
    }

    class HeavyDuty implements Runnable {
        private Semaphore s;

        HeavyDuty(Semaphore s) {
            this.s = s;
        }

        @Override
        public void run() {
            try {
                s.acquire();
                System.out.println("Permit ACQUIRED by " + Thread.currentThread().getName());
                doProcessing();
                System.out.println("Permit released by " + Thread.currentThread().getName());
                s.release();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        private void doProcessing() throws InterruptedException {
            System.out.println("doing heavy computation processing ");
            Thread.sleep(5000);
        }
    }

    /**
     * 演示二元信号量作为互斥锁使用
     */
    class CounterUsingMutex {

        private Semaphore mutex;
        private int count;

        CounterUsingMutex() {
            mutex = new Semaphore(1);
            count = 0;
        }

        void increase() throws InterruptedException {
            mutex.acquire();
            System.out.println("increase");
            this.count = this.count + 1;
            Thread.sleep(1000);
            mutex.release();

        }

        int getCount() {
            return this.count;
        }

        boolean hasQueuedThreads() {
            return mutex.hasQueuedThreads();
        }
    }
}
