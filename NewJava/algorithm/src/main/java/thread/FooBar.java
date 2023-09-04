package thread;

import java.util.concurrent.Semaphore;

/**
 * https://leetcode.cn/problems/print-foobar-alternately
 * 一个很好的解
 * https://leetcode.cn/problems/print-foobar-alternately/solutions/542996/duo-xian-cheng-liu-mai-shen-jian-ni-xue-d220n/
 *
 */
public class FooBar {

    private int n;

    Semaphore semaphorePrintFoo = new Semaphore(1);

    Semaphore semaphorePrintBar = new Semaphore(0);

    public FooBar(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {

        for (int i = 0; i < n; i++) {
            semaphorePrintFoo.acquire();
            // printFoo.run() outputs "foo". Do not change or remove this line.
            printFoo.run();
            semaphorePrintBar.release();
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {

        for (int i = 0; i < n; i++) {
            semaphorePrintBar.acquire();
            // printBar.run() outputs "bar". Do not change or remove this line.
            printBar.run();
            semaphorePrintFoo.release();
        }
    }

}
