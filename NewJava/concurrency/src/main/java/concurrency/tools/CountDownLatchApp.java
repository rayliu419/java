package concurrency.tools;

import java.util.concurrent.CountDownLatch;


/**
 * 比如读取excel表格，需要把execl表格中的多个sheet进行数据汇总,为了提高汇总的效率我们一般会开启多个线程，
 * 每个线程去读取一个sheet，可是线程执行是异步的，我们不知道什么时候数据处理结束了。
 * 那么这个时候我们就可以运用CountDownLatch,有几个sheet就把state初始化几。每个线程执行完就调用countDown()方法，
 * 在汇总的地方加上await()方法,当所有线程执行完了，就可以进行数据的汇总了。
 */
public class CountDownLatchApp {

    public static void main(String[] args) {
        CountDownLatchApp app = new CountDownLatchApp();

        app.useCountDownLatch();
    }

    public void useCountDownLatch() {
        CountDownLatch latch = new CountDownLatch(3);

        Waiter waiter = new Waiter(latch);
        Decrementer decrementer = new Decrementer(latch);

        new Thread(waiter).start();
        new Thread(decrementer).start();

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public class Waiter implements Runnable {

        CountDownLatch latch = null;

        public Waiter(CountDownLatch latch) {
            this.latch = latch;
        }

        public void run() {
            try {
                System.out.println("wait");
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Waiter Released");
        }
    }

    public class Decrementer implements Runnable {

        CountDownLatch latch = null;

        public Decrementer(CountDownLatch latch) {
            this.latch = latch;
        }

        public void run() {

            try {
                Thread.sleep(1000);
                this.latch.countDown();
                System.out.println("count down");

                Thread.sleep(1000);
                this.latch.countDown();
                System.out.println("count down");

                Thread.sleep(1000);
                this.latch.countDown();
                System.out.println("count down");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
