package concurrency.container;

import java.util.HashMap;
import java.util.UUID;

public class ConcurrentMap {

    /**
     * 找到一个使用普通hashmap出错的例子？
     * 一般来说，是因为resize函数被同时调用发生的。
     */
    public void normalMap() {
        HashMap<String, String> testMap = new HashMap<>();

        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                testMap.put(Thread.currentThread().getName(), UUID.randomUUID().toString());
            }).start();
        }
    }

    public static void main(String[] args) {
        ConcurrentMap concurrentMap = new ConcurrentMap();

        concurrentMap.normalMap();
    }
}
