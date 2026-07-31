package concurrency.thread;

import java.util.concurrent.*;

public class ThreadPoolApp {

    public static void main(String[] args) {
        ThreadPoolApp app = new ThreadPoolApp();

        try {
            app.useThreadPool();
            app.useDefineClass();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void useThreadPool() throws ExecutionException, InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(5);

        // 要返回值得使用Callable
        Future<Integer> res1 = pool.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                Thread.sleep(1000);
                return 100;
            }

        });

        Future<Integer> res2 = pool.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                Thread.sleep(1000);
                return 200;
            }
        });

        int res11 = res1.get();
        int res22 = res2.get();

        System.out.println(res11);
        System.out.println(res22);
    }

    public void useDefineClass() {
        CalculateThread thread1 = new CalculateThread(1, 10);
        CalculateThread thread2 = new CalculateThread(11, 20);

        ExecutorService pool = Executors.newFixedThreadPool(5);

        pool.submit(thread1);
        pool.submit(thread2);

        pool.shutdown();
    }
}
