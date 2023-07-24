package reality;

public class Singleton {

    // 多线程避免使用了cpu cache，所以使用volatile
    // static类型，因为被static函数调用，必须是static类型
    private volatile static Object object;

    /**
     * 不这样写的原因是:
     * 虽然这样可以避免并发访问，但是所有调用getInstance2()的都需要加锁(synchronized需要加锁)，性能损耗严重
     * @return
     */
    public synchronized static Object getInstance2() {
        if (object == null) {
            object = new Object();
        }
        return object;
    }

    /**
     *
     * @return
     */
    public static Object getInstance() {
        // 前面的判断在object != null的时候，不需要加锁，直接返回
        if (object == null) {
            // 后面的synchronized是为了防止多个线程同时访问
            synchronized (Singleton.class) {
                if (object == null) {
                    object = new Object();
                }
            }
        }
        return object;
    }

    public static void main(String[] args) {
        Object object = Singleton.getInstance();
    }
}
