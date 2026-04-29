package leetcode;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TwoPointerTest {

    @Test
    public void testThreeSumBasic() {
        TwoPointer twoPointer = new TwoPointer();
        int[] nums = new int[]{-1, 0, 1, 2, -1, -4};
        List<List<Integer>> result = twoPointer.threeSum(nums);
        // Expected: [[-1, -1, 2], [-1, 0, 1]]
        assertEquals(2, result.size());
        assertTrue(result.contains(Arrays.asList(-1, -1, 2)));
        assertTrue(result.contains(Arrays.asList(-1, 0, 1)));
    }

    @Test
    public void testThreeSumNoResult() {
        TwoPointer twoPointer = new TwoPointer();
        int[] nums = new int[]{1, 2, 3, 4, 5};
        List<List<Integer>> result = twoPointer.threeSum(nums);
        assertEquals(0, result.size());
    }

    @Test
    public void testThreeSumAllZeros() {
        TwoPointer twoPointer = new TwoPointer();
        int[] nums = new int[]{0, 0, 0, 0};
        List<List<Integer>> result = twoPointer.threeSum(nums);
        assertEquals(1, result.size());
        assertTrue(result.contains(Arrays.asList(0, 0, 0)));
    }

    @Test
    public void testThreeSumWithDuplicates() {
        TwoPointer twoPointer = new TwoPointer();
        int[] nums = new int[]{-1, -1, -1, 0, 0, 0, 1, 1, 1};
        List<List<Integer>> result = twoPointer.threeSum(nums);
        // Expected: [[-1, 0, 1], [0, 0, 0]]
        assertEquals(2, result.size());
        assertTrue(result.contains(Arrays.asList(-1, 0, 1)));
        assertTrue(result.contains(Arrays.asList(0, 0, 0)));
    }

    @Test
    public void testThreeSumNegativeOnly() {
        TwoPointer twoPointer = new TwoPointer();
        int[] nums = new int[]{-3, -2, -1, 0, 1, 2, 3};
        List<List<Integer>> result = twoPointer.threeSum(nums);
        // Expected: [[-3, 0, 3], [-3, 1, 2], [-2, -1, 3], [-2, 0, 2], [-1, 0, 1]]
        assertEquals(5, result.size());
        assertTrue(result.contains(Arrays.asList(-3, 0, 3)));
        assertTrue(result.contains(Arrays.asList(-3, 1, 2)));
        assertTrue(result.contains(Arrays.asList(-2, 0, 2)));
        assertTrue(result.contains(Arrays.asList(-2, -1, 3)));
        assertTrue(result.contains(Arrays.asList(-1, 0, 1)));
    }

    @Test
    public void testThreeSumEmptyArray() {
        TwoPointer twoPointer = new TwoPointer();
        int[] nums = new int[]{};
        List<List<Integer>> result = twoPointer.threeSum(nums);
        assertEquals(0, result.size());
    }

    @Test
    public void testThreeSumArrayTooShort() {
        TwoPointer twoPointer = new TwoPointer();
        int[] nums = new int[]{1, 2};
        List<List<Integer>> result = twoPointer.threeSum(nums);
        assertEquals(0, result.size());
    }

}
