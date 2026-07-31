package thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * https://leetcode.com/problems/print-in-order/
 * 使用信号量控制打印顺序
 *
 */
public class PrintInOrder {

    Semaphore runPrintSecond = new Semaphore(0);
    Semaphore runPrintThird = new Semaphore(0);

    public PrintInOrder() {

    }

    public void first(Runnable printFirst) throws InterruptedException {

        // printFirst.run() outputs "first". Do not change or remove this line.
        printFirst.run();
        runPrintSecond.release();
    }

    public void second(Runnable printSecond) throws InterruptedException {
        runPrintSecond.acquire();
        // printSecond.run() outputs "second". Do not change or remove this line.
        printSecond.run();
        runPrintThird.release();
    }

    public void third(Runnable printThird) throws InterruptedException {
        runPrintThird.acquire();
        // printThird.run() outputs "third". Do not change or remove this line.
        printThird.run();
    }
}
