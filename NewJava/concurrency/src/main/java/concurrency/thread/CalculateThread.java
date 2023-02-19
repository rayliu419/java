package concurrency.thread;

import java.util.concurrent.Callable;

public class CalculateThread implements Callable<Integer> {

    private int start;

    private int end;

    public CalculateThread(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public Integer call() throws Exception {
        int res = (start + end) * (end - start + 1) / 2;
        System.out.println(res);
        return res;
    }
}
