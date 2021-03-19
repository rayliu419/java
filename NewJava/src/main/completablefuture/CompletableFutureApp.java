package main.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class CompletableFutureApp {

    /**
     * 需要好好研究一下这个用法。
     * 跟想象的不大一样。
     */

    /**
     * 阻塞调用
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public void blockCompletableFuture() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        String result = completableFuture.get();
        completableFuture.complete("Future result");
    }

    public CompletableFuture completableFutureRunAsync() throws IllegalAccessException, InterruptedException {
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                System.out.println("InterruptedException");
                return;
            }
        });
        System.out.println("I'll run in a separated thread than the main thread");
        return future;
    }
}
