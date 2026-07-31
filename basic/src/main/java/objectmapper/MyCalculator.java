package objectmapper;

import lombok.Data;

/**
 * 测试@Data产生后的class，Build->Build Project，然后去target文件夹下去寻找。
 */
@Data
public class MyCalculator {
    public int x;

    public int add(int a, int b) {
        return a + b;
    }
}
