package leetcode;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TwoPointerTest {

    private final TwoPointer twoPointer = new TwoPointer();

    @Test
    public void testMaxAreaBasic() {
        assertEquals(49, twoPointer.maxArea(new int[]{1, 8, 6, 2, 5, 4, 8, 3, 7}));
    }

    @Test
    public void testMaxAreaTwoElements() {
        assertEquals(1, twoPointer.maxArea(new int[]{1, 2}));
    }

    @Test
    public void testMaxAreaTwoEqualElements() {
        assertEquals(1, twoPointer.maxArea(new int[]{1, 1}));
    }

    @Test
    public void testMaxAreaAscending() {
        assertEquals(6, twoPointer.maxArea(new int[]{1, 2, 3, 4, 5}));
    }

    @Test
    public void testMaxAreaDescending() {
        assertEquals(6, twoPointer.maxArea(new int[]{5, 4, 3, 2, 1}));
    }

    @Test
    public void testMaxAreaAllEqual() {
        assertEquals(9, twoPointer.maxArea(new int[]{3, 3, 3, 3}));
    }

    @Test
    public void testMaxAreaSingleElement() {
        assertEquals(0, twoPointer.maxArea(new int[]{1}));
    }

    @Test
    public void testMaxAreaEmpty() {
        assertEquals(0, twoPointer.maxArea(new int[]{}));
    }

    @Test
    public void testMaxAreaPeakInMiddle() {
        assertEquals(8, twoPointer.maxArea(new int[]{1, 2, 4, 8, 4, 2, 1}));
    }

    @Test
    public void testMaxAreaValleyInMiddle() {
        assertEquals(32, twoPointer.maxArea(new int[]{8, 4, 1, 4, 8}));
    }

    @Test
    public void testThreeSumBasic() {
        int[] nums = new int[]{-1, 0, 1, 2, -1, -4};
        List<List<Integer>> result = twoPointer.threeSum(nums);
        assertEquals(2, result.size());
        assertTrue(result.contains(Arrays.asList(-1, -1, 2)));
        assertTrue(result.contains(Arrays.asList(-1, 0, 1)));
    }

    @Test
    public void testThreeSumNoResult() {
        assertTrue(twoPointer.threeSum(new int[]{1, 2, 3, 4, 5}).isEmpty());
    }

    @Test
    public void testThreeSumAllZeros() {
        List<List<Integer>> result = twoPointer.threeSum(new int[]{0, 0, 0, 0});
        assertEquals(1, result.size());
        assertTrue(result.contains(Arrays.asList(0, 0, 0)));
    }

    @Test
    public void testThreeSumWithDuplicates() {
        List<List<Integer>> result = twoPointer.threeSum(new int[]{-1, -1, -1, 0, 0, 0, 1, 1, 1});
        assertEquals(2, result.size());
        assertTrue(result.contains(Arrays.asList(-1, 0, 1)));
        assertTrue(result.contains(Arrays.asList(0, 0, 0)));
    }

    @Test
    public void testThreeSumNegativeOnly() {
        List<List<Integer>> result = twoPointer.threeSum(new int[]{-3, -2, -1, 0, 1, 2, 3});
        assertEquals(5, result.size());
        assertTrue(result.contains(Arrays.asList(-3, 0, 3)));
        assertTrue(result.contains(Arrays.asList(-3, 1, 2)));
        assertTrue(result.contains(Arrays.asList(-2, 0, 2)));
        assertTrue(result.contains(Arrays.asList(-2, -1, 3)));
        assertTrue(result.contains(Arrays.asList(-1, 0, 1)));
    }

    @Test
    public void testThreeSumEmptyArray() {
        assertTrue(twoPointer.threeSum(new int[]{}).isEmpty());
    }

    @Test
    public void testThreeSumArrayTooShort() {
        assertTrue(twoPointer.threeSum(new int[]{1, 2}).isEmpty());
    }

    // ----- findContentChildren test cases -----

    @Test
    public void testFindContentChildrenExample1() {
        assertEquals(1, twoPointer.findContentChildren(new int[]{1, 2, 3}, new int[]{1, 1}));
    }

    @Test
    public void testFindContentChildrenExample2() {
        assertEquals(2, twoPointer.findContentChildren(new int[]{1, 2}, new int[]{1, 2, 3}));
    }

    @Test
    public void testFindContentChildrenNoChildSatisfied() {
        assertEquals(0, twoPointer.findContentChildren(new int[]{2, 3}, new int[]{1, 1}));
    }

    @Test
    public void testFindContentChildrenAllSatisfied() {
        assertEquals(2, twoPointer.findContentChildren(new int[]{1, 2}, new int[]{2, 3}));
    }

    @Test
    public void testFindContentChildrenEmptyChildren() {
        assertEquals(0, twoPointer.findContentChildren(new int[]{}, new int[]{1, 2, 3}));
    }

    @Test
    public void testFindContentChildrenEmptyCookies() {
        assertEquals(0, twoPointer.findContentChildren(new int[]{1, 2}, new int[]{}));
    }

    @Test
    public void testFindContentChildrenBothEmpty() {
        assertEquals(0, twoPointer.findContentChildren(new int[]{}, new int[]{}));
    }

    @Test
    public void testFindContentChildrenSameGreed() {
        assertEquals(2, twoPointer.findContentChildren(new int[]{2, 2, 2}, new int[]{1, 2, 3}));
    }

    @Test
    public void testFindContentChildrenSingleMatch() {
        assertEquals(1, twoPointer.findContentChildren(new int[]{5}, new int[]{5}));
    }

    @Test
    public void testFindContentChildrenSingleTooSmall() {
        assertEquals(0, twoPointer.findContentChildren(new int[]{5}, new int[]{1}));
    }

    @Test
    public void testFindContentChildrenAllCookiesMatch() {
        assertEquals(3, twoPointer.findContentChildren(new int[]{1, 1, 1}, new int[]{1, 1, 1}));
    }

    @Test
    public void testFindContentChildrenGreedTooHigh() {
        assertEquals(0, twoPointer.findContentChildren(new int[]{10, 10, 10}, new int[]{1, 2, 3}));
    }

    @Test
    public void testFindContentChildrenUnsortedInput() {
        assertEquals(3, twoPointer.findContentChildren(new int[]{3, 1, 2}, new int[]{2, 3, 1}));
    }

    @Test
    public void testFindContentChildrenMoreCookiesThanNeeded() {
        assertEquals(3, twoPointer.findContentChildren(new int[]{1, 2, 3}, new int[]{1, 2, 3, 4, 5}));
    }

    @Test
    public void testFindContentChildrenPartialMatch() {
        assertEquals(2, twoPointer.findContentChildren(new int[]{1, 2, 5, 6}, new int[]{2, 3, 4}));
    }
}
