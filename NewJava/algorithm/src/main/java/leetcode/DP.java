package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DP {

    /**
     * https://leetcode.cn/problems/permutations/ - 无重复的数
     *
     * @param root
     * @return
     * 排列的类DP解法，Bottom UP
     */
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        result.add(List.of(nums[0]));

        for (int k = 1; k < nums.length; k++) {
            List<List<Integer>> next = new ArrayList<>();
            for (List<Integer> perm : result) {
                for (int i = 0; i <= perm.size(); i++) {
                    List<Integer> copy = new ArrayList<>(perm);
                    copy.add(i, nums[k]);
                    next.add(copy);
                }
            }
            result = next;
        }
        return result;
    }

    /**
     * https://leetcode.com/problems/house-robber
     * 两个屋子不能连着偷，最多能偷多少?
     * dp[i] = 从第 0 到第 i 间房能偷的最大值
     * dp[i] = max {dp[i-1], dp[i-2] + nums[i]}
     */
    public int rob(int[] nums) {
        if (nums.length == 0) return 0;
        if (nums.length == 1) return nums[0];
        if (nums.length == 2) return Math.max(nums[0], nums[1]);
        int prev2 = nums[0];
        int prev1 = Math.max(nums[0], nums[1]);
        int max = Integer.MIN_VALUE;
        for (int i = 3; i <= nums.length; i++) {
            int cur = Math.max(prev1, prev2 + nums[i-1]);
            max = Math.max(cur, max);
            prev2 = prev1;
            prev1 = cur;
        }
        return max;
    }


    /**
     * <a href="https://leetcode.com/problems/maximum-subarray">...</a>
     * dp[i] = 以i结尾的subarray的sum
     * dp[i] = max {nums[i], dp[i-1] + nums[i]}
     * 因为dp[i]仅仅依赖于dp[i-1]，所以空间复杂度可以优化到O(1)
     * 同时max值可以边比边算
     */
    public int maxSubArray(int[] nums) {
        // cur store dp[i]
        int cur = nums[0];
        int max = cur;
        for (int i = 1; i < nums.length;i++) {
            cur = Math.max(nums[i], cur + nums[i]);
            max = Math.max(cur, max);
        }
        return max;
    }

    /**
     * <a href="https://leetcode.com/problems/maximum-product-subarray">...</a>
     * Kadane变体，维护双状态（max/min）
     * 因为负负得正，所以同时跟踪当前最大和最小
     */
    public int maxProduct(int[] nums) {
        int curMax = nums[0];
        int curMin = nums[0];
        int result = nums[0];

        for (int i = 1; i < nums.length; i++) {
            int num = nums[i];
            int prevMax = curMax;

            curMax = max3(num, prevMax * num, curMin * num);
            curMin = min3(num, prevMax * num, curMin * num);

            result = Math.max(result, curMax);
        }
        return result;
    }

    private int max3(int a, int b, int c) {
        return Math.max(a, Math.max(b, c));
    }

    private int min3(int a, int b, int c) {
        return Math.min(a, Math.min(b, c));
    }

    /**
     *  <a href="https://leetcode.com/problems/longest-increasing-subsequence">...</a>
     *  dp[i] 以i结尾的LIS的长度
     *  dp[i] = max{dp[0]..dp[i-1]} + 1  -- if nums[k] < nums[i]
     *        = 1                        -- if nums[k] < all
     *  LIS及扩展题型
     */
    public int lengthOfLIS(int[] nums) {
        if (nums.length == 0) return 0;
        int[] dp = new int[nums.length];
        Arrays.fill(dp, 1);
        int max = 1;
        for (int i = 1; i < nums.length; i++) {
            for (int j = i - 1; j >= 0; j--) {
                if (nums[i] > nums[j] && dp[j] + 1 > dp[i]) {
                        dp[i] = dp[j] + 1;
                }
            }
            max = Math.max(dp[i], max);
        }
        return max;
    }

    /**
     * https://leetcode.com/problems/longest-common-subsequence
     * LCS 及扩展类型
     */

    /**
     *
     * https://leetcode.com/problems/is-subsequence/
     *
     * 贪心算法
     *
     * @param s
     * @param t
     * @return
     */
    public boolean isSubsequence(String s, String t) {
        if (s.length() == 0 ) {
            return true;
        }
        if (t.length() == 0) {
            return false;
        }
        int i = 0;
        for (int j = 0; j < t.length(); j++) {
            if (s.charAt(i) == t.charAt(j)) {
                i++;
            }
            if (i == s.length()) {
                return true;
            }
        }
        return false;
    }
}
