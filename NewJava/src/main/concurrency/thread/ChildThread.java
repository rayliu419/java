package main.concurrency.thread;

public class ChildThread {

    /**
     * 这里演示的是，作为JUnitTest和main函数的行为不一样
     * main函数不会停止
     * test会停止。
     *
     */
    public void childThreadNeverStop() {

        new Thread(() -> {
            while(true) {
                System.out.println("child thread is running");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        System.out.println("main thread ends");
    }

    public static void main(String[] args) {
        ChildThread childThread = new ChildThread();
        childThread.childThreadNeverStop();
    }
}
