package leetcode;


public class Exam {

    public int[] searchRange(int[] nums, int target) {
        int low = 0, high = nums.length - 1;
        int ans1, ans2;
        ans1 = ans2 = -1;
        int[] result = new int[2];
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (target == nums[mid]) {
                ans1 = mid;
                high = mid - 1;
            } else if (target > nums[mid]) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        result[0] = ans1;
        low = 0;
        high = nums.length - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (target == nums[mid]) {
                ans2 = mid;
                low = mid + 1;
            } else if (target > nums[mid]) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        result[1] = ans2;
        return result;
    }
}
