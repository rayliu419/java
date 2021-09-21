package main.concurrency.thread;

import java.util.concurrent.*;

public class CompletableFutureApp {

    /**
     * 演示Future和CompletableFuture的差别
     * CompletableFuture也有很多第三方库实现，例如Guava的ListenFuture
     * @param args
     */
    public static void main(String[] args) {
        CompletableFutureApp app = new CompletableFutureApp();

//        app.futureRun();

        app.completeFutureRun();
    }


    public void futureRun() {
        ExecutorService pool = Executors.newCachedThreadPool();

        Future<String> res = (Future<String>) pool.submit(() -> {
            Thread.sleep(3000);
            return "finish";
        });
        System.out.println("do something else");
        doSomething();

        System.out.println("back to original job");

        long start = System.currentTimeMillis();
        while (true) {
            if (res.isDone()) {
                break;
            }
        }

        try {
            String result = res.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        pool.shutdown();
    }

    /**
     * CompleteFuture提供了在异步执行以后的连续动作的方式，在异步返回以后可以直接指示后序要做的动作。
     * 下面的场景如果用上面的Future，就会比较难。
     */
    public void completeFutureRun() {
        ExecutorService pool = Executors.newCachedThreadPool();
        CompletableFuture<String> step1 = new CompletableFuture<>();

        Future<String> res = (Future<String>) pool.submit(() -> {
            Thread.sleep(500);
            step1.complete("step 1 finish");
            return "Thread finish";
        });


        CompletableFuture<String> step2 = step1.whenComplete((s, Throwable) -> {
            System.out.println("receive : " + s);
            System.out.println("step 2 finish");
        });

        CompletableFuture<String> step3 = step2.thenApply(s -> {
            System.out.println("receive : " + s);
            System.out.println("step 3 finish");
            return s;
        });

        System.out.println("do something else");
        doSomething();

        System.out.println("back to original job");

        System.out.println("wait for step 3");

        try {
            step3.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        pool.shutdown();
    }

    private void doSomething() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
