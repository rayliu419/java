package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TwoPointer {

    public int maxArea(int[] height) {
        // 两个指针从最左和最右开始，是因为宽度越大面积潜力越大。
        // 从最宽开始，然后逐步缩小宽度，每次只移动短板，
        // 确保不会遗漏可能更大的面积。
        int i = 0, j = height.length - 1, max = 0;
        while (i < j) {
            // 考虑能形成解的i, j位置。
            int area = Math.min(height[i], height[j]) * (j - i);
            max = Math.max(max, area);
            // 面积由短板决定，移动长板不可能得到更大面积：
            //   - 宽度 (j-i) 减小
            //   - 新高度 ≤ 原短板高度
            // 所以只移动短板（或相等时同时移动）
            if (height[i] == height[j]) {
                // 两边都是短板，i 和 j 都不可能参与构造更大面积
                i++;
                j--;
            } else if (height[i] < height[j]) {
                // 短板在左，移动 i 才有可能遇到更高的柱子
                // 其实这里可以进一步优化，即可以一直向有边走，直到遇到比当前的柱子还高的才有机会形成更优解，但是这种写法不如这种不优化的整洁
                // 优化的方法其实也应该会，因为老是会有类似在循环里也多次移动的，主要是内部移动的，容易造成死循环。
                i++;
            } else {
                // 短板在右，移动 j
                j--;
            }
        }
        return max;
    }

    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        Arrays.sort(nums);
        for (int i = 0; i < nums.length - 2; i++) {
            // i如果相同，其他的数也会是相同的，忽略
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            int j = i + 1;
            int k = nums.length - 1;
            while (j < k) {
                int sum = nums[i] + nums[j] + nums[k];
                if (sum == 0) {
                    ans.add(Arrays.asList(nums[i], nums[j], nums[k]));
                    // j如果是重复的，就过滤掉
                    while (j < k && nums[j] == nums[j + 1]) {
                        j++;
                    }
                    // k同样
                    while (j < k && nums[k] == nums[k - 1]) {
                        k--;
                    }
                    j++;
                    k--;
                } else if (sum < 0) {
                    // Sum is less than zero, increment j to increase the sum
                    j++;
                } else {
                    // Sum is greater than zero, decrement k to decrease the sum
                    k--;
                }
            }
        }
        return ans;
    }

    public int findContentChildren(int[] children, int[] cookies) {
        Arrays.sort(children);
        Arrays.sort(cookies);
        int i = 0, j = 0;
        while (i < children.length && j < cookies.length) {
            if (cookies[j] >= children[i]) {
                i++;
            }
            j++;
        }
        return i;
    }

    /**
     * https://leetcode.com/problems/backspace-string-compare
     * @param s
     * @param t
     * @return
     */
    public boolean backspaceCompare(String s, String t) {
        String trimS = simplify(s);
        String trimT = simplify(t);
        return trimS.equals(trimT);
    }

    private String simplify(String s) {
        int i = s.length() - 1;
        StringBuilder sb = new StringBuilder();
        int skip = 0;
        while (i >= 0) {
            if (s.charAt(i) == '#') {
                skip++;
            } else {
                if (skip != 0) skip--;
                else sb.append(s.charAt(i));
            }
            i--;
        }
        return sb.reverse().toString();
    }

    /**
     * https://leetcode.com/problems/best-time-to-buy-and-sell-stock/
     *
     * 只需一次遍历：记录历史最低价格，每天计算如果当天卖出能赚多少，
     * 不断更新最大利润。潜在的最低价格可能出现在未来，所以还要继续看。
     */
    public int maxProfit(int[] prices) {
        int minPrice = Integer.MAX_VALUE;
        int maxProfit = 0;
        for (int price : prices) {
            if (price < minPrice) {
                minPrice = price;
            } else {
                maxProfit = Math.max(maxProfit, price - minPrice);
            }
        }
        return maxProfit;
    }

    /**
     * https://leetcode.com/problems/best-time-to-buy-and-sell-stock-ii/
     *
     * 累加正差价法（也叫"抓所有上升段"）。
     *
     * 核心思想：
     * - 无限次交易意味着可以把一次长持有拆成多段短持有，总和不变。
     *   例如 [1,3,5]：1买5卖=4；  1买3卖(2) + 3买5卖(2)=4  → 一样
     * - 所以只要价格今天比昨天高，就当作昨天买今天卖，累加这个差价。
     * - 价格下跌时不做任何操作（差价是负的，跳过即可）。
     *
     * 累加正差价的好处：
     * 1. 不需要显式找 valley 和 peak，代码极简
     * 2. 一次遍历 O(n)，O(1) 空间，和 peak-valley 完全等价
     * 3. 不需要数组额外空间，也不需要 DP 数组
     * 4. 边界的自然处理：数组为空或长度为1 → 循环直接不执行，返回0
     *
     * 为什么在局部峰卖出不会错过后面的更高峰？
     *
     * 分两种情况：
     *
     * 情况 A — 中间无回撤（单调上升）：
     *   [1, 3, 5]
     *   在 3 卖(赚2)，再在 5 卖(赚2)，总利润 = 4
     *   持有到 5 卖，利润 = 4
     *   两者相等。因为 (3-1)+(5-3) = (5-1)，中间的买卖正好抵消。
     *
     * 情况 B — 中间有回撤（实际更常见）：
     *   [1, 3, 2, 5]
     *   若在 3 卖出（赚2），跌到 2 时买入，涨到 5 卖出（赚3），总利润 = 5
     *   若在 1 买入一直持有到 5（不卖在 3），利润 = 4  ← 反而更少了！
     *   因为中间的回撤(3→2)导致利润回吐了 1，而先卖再买能避开这段下跌。
     *
     *   更直观地说，每一次峰→谷的下跌不参与交易，你就避免了亏损；
     *   每一次谷→峰的上涨你都参与了，利润就累加起来。
     *
     * 所以"在局部峰卖出"本质上是贪婪最优解：
     * - 后面没有更高峰 → 卖对了，落袋为安
     * - 后面有更高峰但无回撤 → 差价拆分，总利润不变
     * - 后面有更高峰但有回撤 → 卖出规避下跌，还能在更低点再接回来
     *
     * 累加正差价是上述逻辑最简单直接的数学表达。
     */
    public int maxProfitII(int[] prices) {
        int profit = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > prices[i - 1]) {
                profit += prices[i] - prices[i - 1];
            }
        }
        return profit;
    }

}
