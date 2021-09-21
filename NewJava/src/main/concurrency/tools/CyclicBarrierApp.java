package main.concurrency.tools;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierApp {

    /**
     * 其实CyclicBarrier 与 CountDownLatch差不多，但是CountDownLatch更灵活，参考badUse()
     * @param args
     */
    public static void main(String[] args) {
        CyclicBarrierApp app = new CyclicBarrierApp();

        app.useCyclicBarrier();

        app.badUse();
    }

    public void useCyclicBarrier() {
        Runnable barrier1Action = new Runnable() {
            public void run() {
                System.out.println("BarrierAction 1 executed ");
            }
        };
        Runnable barrier2Action = new Runnable() {
            public void run() {
                System.out.println("BarrierAction 2 executed ");
            }
        };

        CyclicBarrier barrier1 = new CyclicBarrier(2, barrier1Action);
        CyclicBarrier barrier2 = new CyclicBarrier(2, barrier2Action);

        CBThread barrierRunnable1 = new CBThread(barrier1, barrier2);

        CBThread barrierRunnable2 = new CBThread(barrier1, barrier2);

        new Thread(barrierRunnable1).start();
        new Thread(barrierRunnable2).start();
    }

    public class CBThread implements Runnable{

        CyclicBarrier barrier1 = null;
        CyclicBarrier barrier2 = null;

        public CBThread(
                CyclicBarrier barrier1,
                CyclicBarrier barrier2) {

            this.barrier1 = barrier1;
            this.barrier2 = barrier2;
        }

        public void run() {
            try {
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName() +
                        " waiting at barrier 1");
                this.barrier1.await();

                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName() +
                        " waiting at barrier 2");
                this.barrier2.await();

                System.out.println(Thread.currentThread().getName() +
                        " done!");

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * CyclicBarrier maintains a count of threads whereas CountDownLatch maintains a count of tasks.
     */
    public void badUse() {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2);
        Thread t = new Thread(() -> {
            try {
                cyclicBarrier.await();
                /**
                 * Never ends.
                 * the second await() is useless. A single thread can't count down a barrier twice.
                 */
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                // error handling
            }
        });
        t.start();

        System.out.println(cyclicBarrier.getNumberWaiting());
        System.out.println(cyclicBarrier.isBroken());

    }
}
