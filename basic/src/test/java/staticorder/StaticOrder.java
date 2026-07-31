package staticorder;

import org.junit.Test;

public class StaticOrder {

    /**
     * 测试static的各种执行顺序
     */
    @Test
    public void testStaticOrder() {
        staticorder.StaticB staticB = new StaticB();
    }
}
