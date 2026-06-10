package leetcode;

import java.util.*;

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
     * https://leetcode.com/problems/climbing-stairs/
     * dp[n] = dp[n-1] + dp[n-2]
     */
    public int climbStairs(int n) {
        if (n == 1) return 1;
        if (n == 2) return 2;
        int prev2 = 1; // dp[n-2]
        int prev1 = 2; // dp[n-1]
        int cur = -1;
        for (int i = 3; i <= n; i++) {
            cur = prev2 + prev1;
            prev2 = prev1;
            prev1 = cur;
        }
        return cur;
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
     * https://leetcode.com/problems/coin-change/
     * 注意: 完全背包问题，每个硬币都可以重复使用。
     * Top-Down的解题
     */
    public int coinChange(int[] coins, int amount) {
        HashMap<Integer, Integer> cache = new HashMap<>();
        return coinChange(coins, amount, cache);
    }

    private int coinChange(int[] coins, int amount, Map<Integer, Integer> cache) {
        if (amount < 0) return -1;
        if (amount == 0) return 0;
        if (cache.containsKey(amount)) return cache.get(amount);
        // 计算当前的amount的最小值。
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < coins.length; i++) {
            int cur = coinChange(coins, amount - coins[i], cache);
            if (cur != -1) {
                min = Math.min(min, cur + 1);
            }
        }
        if (min == Integer.MAX_VALUE) {
            // amount凑不了
            cache.put(amount, -1);
            return -1;
        } else {
            cache.put(amount, min);
            return min;
        }
    }

    // Bottom-Up
    // dp[n] = dp[j] && dp[n-j]
    public int coinChange2(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        dp[0] = 0;
        Set<Integer> cache = new HashSet<>();
        for (int i = 0; i < coins.length; i++) {
            cache.add(coins[i]);
        }
        for (int i = 1; i <= amount; i++) {
            // 计算直到amount的dp数组, dp[i]依赖于dp[0], dp[1]...
            int min = Integer.MAX_VALUE;
            for (int j = 0; j < i; j++) {
                if (dp[j] != -1 && cache.contains(i-j)) {
                    min = Math.min(min, dp[j] + 1);
                }
            }
            if (min == Integer.MAX_VALUE) {
                dp[i] = -1;
            } else {
                dp[i] = min;
            }
        }
        return dp[amount];
    }


    /**
     *  Bottom-Up 优化版，O(amount * len(coins))
     *
     *  Arrays.fill(dp, amount + 1) 的含义：
     *  用 amount+1 作为"无穷大"标记，代替 Integer.MAX_VALUE。
     *  理由：硬币最小面值 >= 1，最多需要 amount 枚硬币，
     *  所以 amount+1 是一个安全的"不可能达到"的初始值，
     *  同时避免 dp[i - coin] + 1 可能溢出 Integer.MAX_VALUE 的问题。
     */
    public int coinChange3(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1);
        dp[0] = 0;
        for (int i = 1; i <= amount; i++) {
            for (int coin : coins) {
                if (i >= coin) {
                    dp[i] = Math.min(dp[i], dp[i - coin] + 1);
                }
            }
        }
        return dp[amount] > amount ? -1 : dp[amount];
    }

    /**
     * https://leetcode.com/problems/word-break/
     * 跟coin change极其类似
     * 每个word都可以重复使用。
     */
    public boolean wordBreak(String s, List<String> wordDict) {
        boolean[] dp = new boolean[s.length() + 1];
        Arrays.fill(dp, false);
        dp[0] = true;
        for (int i = 1; i < dp.length; i++) {
           for (String word : wordDict) {
                int length = word.length();
                if (i - length >= 0 && s.substring(i - length, i).equals(word) && dp[i - length]) {
                    dp[i] = true;
                    break;
                }
           }
        }
        return dp[s.length()];
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
     * dp[i][j] =
     *  if a[i] == b[j],  dp[i-1][j-1] + 1
     *  else max {dp[i][j-1], dp[i-1][j]}
     */
    public int longestCommonSubsequence(String text1, String text2) {
        if (text1.isEmpty()) return 0;
        if (text2.isEmpty()) return 0;
        int[][] dp = new int[text1.length() + 1][text2.length() + 1];
        // 不用初始化，都是0
        for (int i = 1; i <= text1.length(); i++) {
            for (int j = 1; j <= text2.length(); j++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    dp[i][j] = dp[i-1][j-1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
                }
            }
        }
        return dp[text1.length()][text2.length()];
    }

    /**
     * https://leetcode.com/problems/edit-distance
     * 还是在dp[i][j]定义没有想清楚
     * see NewJava/algorithm/src/main/java/leetcode/leetcode_summary/edit-distance.html for details.
     */
    public int minDistance(String word1, String word2) {
        int[][] dp = new int[word1.length() + 1][word2.length() + 1];
        dp[0][0] = 0;
        // 初始化
        for (int i = 0; i <= word1.length(); i++) {
            dp[i][0] = i;
        }
        for (int j = 0; j <= word2.length(); j++) {
            dp[0][j] = j;
        }
        for (int i = 1; i <= word1.length(); i++) {
            for (int j = 1;  j <= word2.length(); j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i-1][j-1];
                } else {
                    // 分别对应插入，删除和替换操作
                    dp[i][j] = Math.min(Math.min(dp[i-1][j] + 1, dp[i][j-1] + 1), dp[i-1][j-1] + 1);
                }
            }
        }
        return dp[word1.length()][word2.length()];
    }

    /**
     * https://leetcode.com/problems/longest-palindromic-substring/
     * 解法1，尝试中间点扩展
     */
    public int longestPalindrome(String s) {
        int max = 0;
        for (int i = 0; i < s.length(); i++) {
            // 奇数
            int length1 = expand(s, i, i);
            // 偶数
            int length2 = expand(s, i, i + 1);
            int curMax = Math.max(length1, length2);
            max = Math.max(curMax, max);
        }
        return max;
    }

    private int expand(String s, int loc1, int loc2) {
        if (loc1 < 0 || loc1 >= s.length()) return 0;
        if (loc2 < 0 || loc2 >= s.length()) return 0;
        int i = loc1;
        int j = loc2;
        int len = 0;
        while (i >= 0 && j < s.length() && s.charAt(i) == s.charAt(j)) {
            i--;
            j++;
            len++;
        }
        // 不能返回len，问题在于 expand 统计的是匹配的"对"数（奇数时中心自己和自己算一对），而非实际长度。
        return j - i - 1;
    }

    /**
     * DP 算法
     * dp[i][j] = s[i..j] 是否为回文 (i,j 为 0-based 索引)
     * 按长度从小到大计算，dp[i][j] 依赖于 dp[i+1][j-1]
     * 计算顺序: len=1 → len=2 → len=3 → ... → len=n
     * 每个长度层内部: i 从 0 到 n-len 遍历，j = i+len-1
     */
    public String longestPalindrome2(String s) {
        if (s == null || s.isEmpty()) return "";
        int n = s.length();
        boolean[][] dp = new boolean[n][n];
        int maxLen = 1;
        int start = 0;

        // length = 1: 单个字符
        for (int i = 0; i < n; i++) {
            dp[i][i] = true;
        }

        // length = 2: 相邻字符
        for (int i = 0; i < n - 1; i++) {
            if (s.charAt(i) == s.charAt(i + 1)) {
                dp[i][i + 1] = true;
                maxLen = 2;
                start = i;
            }
        }

        // length >= 3: 依赖内部子串
        for (int len = 3; len <= n; len++) {
            for (int i = 0; i <= n - len; i++) {
                int j = i + len - 1;
                if (s.charAt(i) == s.charAt(j) && dp[i + 1][j - 1]) {
                    dp[i][j] = true;
                    if (len > maxLen) {
                        maxLen = len;
                        start = i;
                    }
                }
            }
        }
        return s.substring(start, start + maxLen);
    }


    /**
     * 背包问题极其扩展
     * https://leetcode.com/problems/partition-equal-subset-sum
     * 每个元素只能用一次
     * 转化为：给定数组，每个元素用一次，能否凑出 target？
     *
     * 白话：
     * dp[s] → 当前数字中，能否凑出和 s？
     * 每来一个 num，问自己：之前就能凑出 s？（跳过） 或 之前能凑出 s-num？（带上）
     * 倒序更新 → 每个 num 只用一次（正序就变成完全背包了）
     */
    public boolean canPartition(int[] nums) {
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
        }
        // 奇数和不能拆分
        if (sum %2 != 0) return false;
        int target = sum / 2;

        boolean[] dp = new boolean[target + 1];
        dp[0] = true;  // 空子集，和为 0

        for (int num : nums) {
            // 每来一个 num，问自己：之前就能凑出 s？（跳过） 或 之前能凑出 s-num？（带上）
            for (int s = target; s >= num; s--) {
                // 倒序保证 dp[s - num] 始终是「上一轮」的值——当前数字还没参与过。
                // 正序时 dp[s - num] 可能是「本轮刚更新」的值——当前数字已经被用过了。
                // 前面每个数字可以用多次，或者每个word可以用多次，循环就是反着的，且dp计算是正序计算的。- 本质区别
                dp[s] = dp[s] || dp[s - num];     // 不取 || 取
            }
        }
        return dp[target];
    }

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
