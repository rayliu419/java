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

    /**
     * https://leetcode.com/problems/search-a-2d-matrix/
     * 二维映射成一维的方式
     * 下一行的第一个元素比上一行的元素最后一个元素大，因此如果一行一行遍历，就是纯有序的。
     */
    public boolean searchMatrix(int[][] matrix, int target) {
        int m = matrix.length;
        int n = matrix[0].length;
        int low = 0;
        int high = m * n - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            int rowIndex = mid / n;
            int columIndex = mid % n;
            if (target == matrix[rowIndex][columIndex]) {
                return true;
            } else if (target > matrix[rowIndex][columIndex]) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return false;
    }

    /**
     * https://leetcode.com/problems/search-a-2d-matrix-ii/
     * row有序
     * col有序
     * 以matrix[r][c] 为例子， 它与前面的一行和它以下的一列做成一个有序数组
     * 右上角出发
     */
    public boolean searchMatrixII(int[][] matrix, int target) {
        int m = matrix.length;
        int n = matrix[0].length;
        int r = 0;
        int c = n - 1;
        while (r < m && c >= 0) {
            if (matrix[r][c] == target) {
                return true;
            } else if (matrix[r][c] > target) {
                c--;
            } else if (matrix[r][c] < target) {
                r++;
            }
        }
        return false;
    }


    /**
     * https://leetcode.com/problems/search-in-rotated-sorted-array/
     */
    public int search(int[] nums, int target) {
        int low = 0;
        int high = nums.length - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (nums[mid] == target) {
                return mid;
            }
            if (nums[low] <= nums[mid]) {
                // 左边有序
                if (nums[low] <= target && nums[mid] > target) {
                    // target也落在了左边
                    high = mid - 1;
                } else {
                    low = mid + 1;
                }
            } else {
                // 右边有序
                if (nums[mid] < target && nums[high] >= target) {
                    // target也落在了右边
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            }
        }
        return -1;
    }

    /**
     * https://leetcode.com/problems/find-peak-element/
     * 找到peak中的一个
     */
    public int findPeakElement(int[] nums) {
        int left = 0, right = nums.length - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] > nums[mid + 1]) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }

    /**
     * https://leetcode.com/problems/find-minimum-in-rotated-sorted-array/
     */
    public int findMin(int[] nums) {
        int low = 0;
        int high = nums.length - 1;
        while (low < high) {
            int mid = low + (high - low) / 2;
            if (nums[mid] < nums[high]) {
                // 右边有序，意味着min不在右边
                high = mid;
            } else {
                // 为什么是 + 1，因为最小值一定落在右段。
                // 这里mid在左段，肯定不是最小值区间
                low = mid + 1;
            }
        }
        return nums[low];
    }
}
