package thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;


// https://leetcode.com/problems/print-in-order/

public class PrintInOrder {

    Semaphore runPrintSecond = new Semaphore(0);
    Semaphore runPrintThird = new Semaphore(0);

    public void runInOrder() {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        executor.execute(() -> {
            try {
                runPrintSecond.acquire();
                System.out.println("printSecond");
                runPrintThird.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        executor.execute(() -> {
            try {
                runPrintThird.acquire();
                System.out.println("printThird");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        executor.execute(() -> {
            System.out.println("printFirst");
            runPrintSecond.release();
        });


        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
