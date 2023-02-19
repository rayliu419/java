package leetcode;

import leetcode.PrintInOrder;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class PrintInOrderTest {

    @Test
    public void test() {
        PrintInOrder printInOrder = new PrintInOrder();

        printInOrder.runInOrder();
    }
}
