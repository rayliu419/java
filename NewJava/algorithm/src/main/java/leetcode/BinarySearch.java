package leetcode;

import java.util.Arrays;

public class BinarySearch {

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
     * https://leetcode.com/problems/find-first-and-last-position-of-element-in-sorted-array/
     *
     */
    public int[] searchRange(int[] nums, int target) {
        int first = binarySearchFirstOccurrence(nums, target);
        int last = binarySearchLastOccurrence(nums, target);
        return new int[]{first, last};
    }

    /**
     * 重复时，返回第一个出现的位置
     * @param nums
     * @param target
     * @return
     */
    private static int binarySearchFirstOccurrence(int[] nums, int target) {
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

    private static int binarySearchLastOccurrence(int[] nums, int target) {
        int ans = -1;
        int low = 0;
        int high = nums.length - 1;
        while(low <= high) {
            int mid = low + (high - low) / 2;
            if (nums[mid] == target) {
                // 找到一个解，但是可能存在更优解
                // 向右搜索，尝试找到更好的解，如果有，会覆盖。
                ans = mid;
                low = mid + 1;
            } else if (nums[mid] > target) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return ans;
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
     * https://leetcode.com/problems/sqrtx
     */
    public int mySqrt(int x) {
        if (x < 2) return x;
        int low = 1;
        int high = x;
        int ans = - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (mid == x / mid) {
                return mid;
            } else if (mid < x / mid) {
                // 根据题目的意思，需要记录这个结果，因为它有可能是最后的解了
                ans = mid;
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return ans;
    }
}
