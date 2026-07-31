package concurrency.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LockApp {

    public static void main(String[] args) {
        LockApp app = new LockApp();
    }

    /**
     * 使用读写锁来封装Map。
     */
    public class SynchronizedHashMapWithReadWriteLock {

        Map<String, String> syncHashMap = new HashMap<>();
        ReadWriteLock lock = new ReentrantReadWriteLock();
        // ...
        Lock writeLock = lock.writeLock();
        Lock readLock = lock.readLock();

        public void put(String key, String value) {
            try {
                writeLock.lock();
                syncHashMap.put(key, value);
            } finally {
                // 必须有释放操作，否则死锁
                writeLock.unlock();
            }
        }

        public String remove(String key) {
            try {
                writeLock.lock();
                return syncHashMap.remove(key);
            } finally {
                writeLock.unlock();
            }
        }

        public String get(String key) {
            try {
                readLock.lock();
                return syncHashMap.get(key);
            } finally {
                readLock.unlock();
            }
        }

        public boolean containsKey(String key) {
            try {
                readLock.lock();
                return syncHashMap.containsKey(key);
            } finally {
                readLock.unlock();
            }
        }

    }
}
