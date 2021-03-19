package main.completablefuture;

import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletablefutureTest {

    @Test
    public void testExplicitComplete() throws ExecutionException, InterruptedException {
        CompletableFutureApp completableFutureApp = new CompletableFutureApp();
        completableFutureApp.blockCompletableFuture();;
    }

    @Test
    public void testCompletableFutureRunAsync() throws ExecutionException,
            InterruptedException, IllegalAccessException {
        CompletableFutureApp completableFutureApp = new CompletableFutureApp();
        CompletableFuture future = completableFutureApp.completableFutureRunAsync();
//        future.get();
        System.out.println("main thread");
    }
}
