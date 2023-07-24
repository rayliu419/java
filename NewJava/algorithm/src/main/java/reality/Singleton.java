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
        if (object == null) {
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
