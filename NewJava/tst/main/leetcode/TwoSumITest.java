package main.leetcode;

import main.leetcode.TwoSumI;
import org.junit.Test;

import java.util.Arrays;

public class TwoSumITest {

    @Test
    public void twoSum() {
        TwoSumI twoSumI = new TwoSumI();
        int[] numbers = new int[]{2, 7, 11, 15};
        int[] res = twoSumI.twoSum(numbers, 9);
        Arrays.stream(res).forEach(e -> System.out.println(e));
    }
}