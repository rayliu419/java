package leetcode;

public class BinarySearch {

    public int search(int[] nums, int target) {
        int ans = -1;
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
        return ans;
    }

    /***
     * https://leetcode.com/problems/search-insert-position/
     *
     */
    public int searchInsert(int[] nums, int target) {
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
     * https://leetcode.com/problems/heaters/
     *
     * 标准二分法
     *
     * @param houses
     * @param heaters
     * @return
     */
    public int findRadius(int[] houses, int[] heaters) {
        return -1;
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
