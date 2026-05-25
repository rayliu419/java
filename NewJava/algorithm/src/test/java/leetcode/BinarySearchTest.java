package leetcode;

import junit.framework.TestCase;
import org.junit.Test;

public class BinarySearchTest extends TestCase {

    @Test
    public void testFindPeakElement() {
        BinarySearch binarySearch = new BinarySearch();

        // 峰值在中间
        assertEquals(2, binarySearch.findPeakElement(new int[]{1, 2, 3, 1}));

        // 递增数组，峰值在末尾
        assertEquals(3, binarySearch.findPeakElement(new int[]{1, 2, 3, 4}));

        // 递减数组，峰值在开头
        assertEquals(0, binarySearch.findPeakElement(new int[]{4, 3, 2, 1}));

        // 只有一个元素
        assertEquals(0, binarySearch.findPeakElement(new int[]{1}));

        // 多个峰值，返回任意一个
        int idx = binarySearch.findPeakElement(new int[]{1, 2, 1, 3, 5, 6, 4});
        assertTrue(validPeak(new int[]{1, 2, 1, 3, 5, 6, 4}, idx));

        // 只有两个元素
        assertEquals(0, binarySearch.findPeakElement(new int[]{2, 1}));
        assertEquals(1, binarySearch.findPeakElement(new int[]{1, 2}));
    }

    private boolean validPeak(int[] nums, int idx) {
        int n = nums.length;
        boolean leftOk = (idx == 0) || nums[idx] > nums[idx - 1];
        boolean rightOk = (idx == n - 1) || nums[idx] > nums[idx + 1];
        return leftOk && rightOk;
    }

    @Test
    public void testSearch() {
        BinarySearch binarySearch = new BinarySearch();

        // 标准旋转数组
        assertEquals(4, binarySearch.search(new int[]{4,5,6,7,0,1,2}, 0));
        assertEquals(1, binarySearch.search(new int[]{4,5,6,7,0,1,2}, 5));

        // target 不存在
        assertEquals(-1, binarySearch.search(new int[]{4,5,6,7,0,1,2}, 3));

        // 长度为 2
        assertEquals(0, binarySearch.search(new int[]{3,1}, 3));
        assertEquals(1, binarySearch.search(new int[]{3,1}, 1));

        // 单元素
        assertEquals(0, binarySearch.search(new int[]{1}, 1));
        assertEquals(-1, binarySearch.search(new int[]{1}, 0));

        // 未旋转
        assertEquals(2, binarySearch.search(new int[]{1,2,3,4,5}, 3));

        // target 在边界
        assertEquals(0, binarySearch.search(new int[]{4,5,6,7,0,1,2}, 4));
        assertEquals(6, binarySearch.search(new int[]{4,5,6,7,0,1,2}, 2));
    }

    @Test
    public void testFindMin() {
        BinarySearch binarySearch = new BinarySearch();

        assertEquals(0, binarySearch.findMin(new int[]{3,4,5,1,2}));
        assertEquals(0, binarySearch.findMin(new int[]{4,5,6,7,0,1,2}));
        assertEquals(1, binarySearch.findMin(new int[]{2,1}));
        assertEquals(1, binarySearch.findMin(new int[]{1}));
        assertEquals(1, binarySearch.findMin(new int[]{1,2,3,4,5}));
    }

    @Test
    public void test() {
        BinarySearch binarySearch = new BinarySearch();
        int index = binarySearch.searchInsert(new int[]{1, 3, 5, 6}, 5);
        System.out.println(index);

        int index2 = binarySearch.searchInsert(new int[]{1, 3, 5, 6}, 2);
        System.out.println(index2);

        int index3 = binarySearch.searchInsert(new int[]{1, 3, 5, 6}, 7);
        System.out.println(index3);
    }

    @Test
    public void test2() {
        BinarySearch binarySearch = new BinarySearch();

//        int number = binarySearch.singleNonDuplicate(new int[]{1, 2, 2});
//        System.out.println(number);
//
//        int number2 = binarySearch.singleNonDuplicate(new int[]{1, 1, 2, 3, 3, 4, 4, 8, 8});
//        System.out.println(number2);
//
//        int number3 = binarySearch.singleNonDuplicate(new int[]{3, 3, 7, 7, 10, 11, 11});
//        System.out.println(number3);
//
//        int number4 = binarySearch.singleNonDuplicate(new int[]{1, 1, 2});
//        System.out.println(number4);
//
//        int number5 = binarySearch.singleNonDuplicate(new int[]{1, 1, 2, 3, 3});
//        System.out.println(number5);
    }

}