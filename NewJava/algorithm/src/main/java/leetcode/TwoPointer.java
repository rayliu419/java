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
}
