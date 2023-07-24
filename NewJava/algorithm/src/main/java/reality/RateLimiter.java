package reality;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class RateLimiter {

    int maxTokenNumber;

    int tokenPerSecond;

    int curTokenNumber;

    public RateLimiter(int maxTokenNumber, int tokenPerSecond) {
        this.maxTokenNumber = maxTokenNumber;
        this.tokenPerSecond = tokenPerSecond;
        this.curTokenNumber = maxTokenNumber;
        startTokenRefillTask();
    }

    /**
     * 用单独的线程加token
     */
    private void startTokenRefillTask() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(this::addTokens, 0, 1, TimeUnit.SECONDS);
    }

    private synchronized void addTokens() {
        curTokenNumber = Math.min(curTokenNumber + tokenPerSecond, maxTokenNumber);
        System.out.println("add tokens, curTokenNumber - " + curTokenNumber);
    }

    /**
     * 获取token
     * @return
     */
    public synchronized boolean getToken() {
        if (curTokenNumber >= 1) {
            curTokenNumber -= 1;
            System.out.println("curTokenNumber - " + curTokenNumber);
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService threadPool = Executors.newFixedThreadPool(5);
        RateLimiter rateLimiter = new RateLimiter(5, 2);
        for(int i = 0; i < 5; i++) {
            threadPool.submit(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 5; j++) {
                        System.out.println(Thread.currentThread().getId() + " try acquire token");
                        boolean getToken = rateLimiter.getToken();
                        if (getToken) {
                            System.out.println(Thread.currentThread().getId() + " get token");
                        } else {
                            System.out.println(Thread.currentThread().getId() + " doesn't get token");
                        }
                        try {
                            Thread.sleep(1500);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    System.out.println(Thread.currentThread().getId() + " finish");
                }
            });
        }

        threadPool.shutdown();
        try {
            // 等待所有任务执行完成，或者等待超时（比如等待10秒）
            if (!threadPool.awaitTermination(10, TimeUnit.SECONDS)) {
                // 如果超时，尝试强制关闭所有正在执行的任务
                threadPool.shutdownNow();
                // 等待再次关闭
                if (!threadPool.awaitTermination(5, TimeUnit.SECONDS)) {
                    System.err.println("线程池未能正常关闭");
                }
            }
        } catch (InterruptedException e) {
            // 捕获InterruptedException，处理中断情况
            threadPool.shutdownNow();
            Thread.currentThread().interrupt(); // 重置线程的中断状态
        }
        System.out.println("thread pool close");
        System.exit(0);
    }
}
