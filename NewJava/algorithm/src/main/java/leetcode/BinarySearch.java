package leetcode;

import java.util.Arrays;

public class BinarySearch {

    public  static void main(String[] args) {
//        testAllBinarySearch();
        testFindLeastGreater();
    }

    /**
     * Test cases.
     *
     */
    public static void testAllBinarySearch() {
        // binarySearch
        System.out.println("binarySearch");
        int[] nums1 = new int[]{0, 1};
        int[] nums2 = new int[]{0, 1, 2};
        System.out.println(binarySearch(nums1, 0));
        System.out.println(binarySearch(nums1, 1));
        System.out.println(binarySearch(nums2, 0));
        System.out.println(binarySearch(nums2, 1));
        System.out.println(binarySearch(nums2, 2));
        System.out.println(binarySearch(nums2, 3));
        System.out.println(binarySearch(nums2, -1));
        System.out.println("=============================================");
        // binarySearchFirstOccurrence
        System.out.println("binarySearchFirstOccurrence");
        int[] nums3 = new int[]{0, 0, 0};
        int[] nums4 = new int[]{0, 1, 1};
        int[] nums5 = new int[]{0, 0, 1};
        System.out.println(binarySearchFirstOccurrence(nums1, 0));
        System.out.println(binarySearchFirstOccurrence(nums1, 1));
        System.out.println(binarySearchFirstOccurrence(nums2, 0));
        System.out.println(binarySearchFirstOccurrence(nums2, 1));
        System.out.println(binarySearchFirstOccurrence(nums2, 2));
        System.out.println(binarySearchFirstOccurrence(nums3, 0));
        System.out.println(binarySearchFirstOccurrence(nums4, 1));
        System.out.println(binarySearchFirstOccurrence(nums5, 1));
        System.out.println(binarySearchFirstOccurrence(nums5, 2));
        System.out.println(binarySearchFirstOccurrence(nums5, -1));
        System.out.println("=============================================");
    }

    /**
     * 基本题型
     * @param nums
     * @param target
     * @return
     */
    public static int binarySearch(int[] nums, int target) {
        int low = 0;
        int high = nums.length - 1;
        while(low <= high) {
            int mid = low + (high - low) / 2;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] > target) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return -1;
    }

    /**
     * 重复时，返回第一个出现的位置
     * @param nums
     * @param target
     * @return
     */
    public static int binarySearchFirstOccurrence(int[] nums, int target) {
        int ans = -1;
        int low = 0;
        int high = nums.length - 1;
        while(low <= high) {
            int mid = low + (high - low) / 2;
            if (nums[mid] == target) {
                // 找到一个解，但是可能存在更优解
                // 存储当前的解，并修改high来尝试找更优的解，如果有，下一轮会覆盖ans；如果没有，则会用这个解来返回。
                ans = mid;
                high = mid - 1;
            } else if (nums[mid] > target) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return ans;
    }

    public static void testFindLeastGreater() {
        System.out.println(findLeastGreater(new int[]{1, 2, 3, 5, 7, 9}, 2));  // ans = 2, value = 3
        System.out.println(findLeastGreater(new int[]{1, 3, 5, 7, 9}, 2));  // ans = 1, value = 3
        System.out.println(findLeastGreater(new int[]{2}, 1));  // ans = 0, value = 2
        System.out.println(findLeastGreater(new int[]{2}, 3));  // ans = -1, value = NA
    }

    /**
     *
     * @param nums
     * @param key
     * @return
     */
    public static int findLeastGreater(int[] nums, int key) {
        int low = 0;
        int high = nums.length - 1;
        int ans = -1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (nums[mid] == key) {
                // 相等，需要往右边找
                low = mid + 1;
            } else if (nums[mid] > key) {
                // 找到一个解，期望找到新的
                ans = mid;
                high = mid - 1;
            } else {
                // 当前值比key还小，不满足解，往更高的值上找
                low = mid + 1;
            }
        }
        return ans;
        // [2], key = 1
        // [2], key = 3
    }

    /***
     * https://leetcode.com/problems/search-insert-position/
     *
     */
    public static int searchInsert(int[] nums, int target) {
        int low = 0;
        int high = nums.length - 1;
        if (nums[0] > target) {
            return 0;
        }
        if (nums[nums.length - 1] < target) {
            return nums.length;
        }
        while(low <= high) {
            int mid = low + (high - low) / 2;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] > target) {
                // 当前的值大于target，找到一个可行的插入位置，但是可能有更往前的插入位置
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        if (nums[low] > target) {
            return low;
        } else {
            return low - 1;
        }
    }


    /**
     *
     * https://leetcode.com/problems/single-element-in-a-sorted-array/
     *
     * @param nums
     * @return
     *
     * nums = [1,1,2,3,3,4,4,8,8] low = 0, high = 8
     *
     * nums = [3,3,7,7,10,11,11] low = 0, high = 6
     *
     * nums = [1,2,2]
     */

}
