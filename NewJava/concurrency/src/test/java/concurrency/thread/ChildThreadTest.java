package concurrency.thread;

import org.junit.Test;

public class ChildThreadTest {

    /**
     * 这样运行的时候，child不会永久run，过一会以后会停止。
     * 这是使用JUnit运行的机制问题
     */
    @Test
    public void test() {
        ChildThread childThread = new ChildThread();

        childThread.childThreadNeverStop();
    }

}