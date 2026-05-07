package leetcode;

import java.util.HashMap;
import java.util.Map;

public class SlideWindow {

    /**
     * https://leetcode.com/problems/longest-substring-without-repeating-characters/
     *
     * @param s
     * @return
     */
    public int lengthOfLongestSubstring(String s) {
        Map<Character, Integer> charCount = new HashMap<>();
        int left = 0;
        int right = 0;
        int maxLength = 0;
        while (right < s.length()) {
            int curCount = charCount.getOrDefault(s.charAt(right), 0);
            int updateCount = curCount + 1;
            charCount.put(s.charAt(right), updateCount);
            if (updateCount == 1) {
                maxLength = Math.max(maxLength, right - left + 1);
                right++;
            } else {
                while (left < right) {
                    if (s.charAt(left) != s.charAt(right)) {
                        charCount.merge(s.charAt(left), -1, Integer::sum);
                        left++;
                    } else {
                        charCount.merge(s.charAt(left), -1, Integer::sum);
                        left++;
                        break;
                    }
                }
                right++;
            }
        }
        return maxLength;
    }

    /**
     * https://leetcode.com/problems/minimum-size-subarray-sum/
     * target = 3, nums = [4, 1, 2, 3]
     * target = 3, nums = [1, 2, 3]
     *
     */
    public int minSubArrayLen(int target, int[] nums) {
        int curSum = 0;
        int left, right;
        left = right = 0;
        int minLength = Integer.MAX_VALUE;
        while (right < nums.length) {
            curSum = curSum + nums[right];
            if (curSum < target) {
                right++;
            } else {
                // 获取一个解
                minLength = Math.min(minLength, right - left + 1);
                while (left < right) {
                    if (curSum - nums[left] >= target) {
                        curSum = curSum - nums[left];
                        left++;
                        minLength = Math.min(minLength, right - left + 1);
                    } else {
                        break;
                    }
                }
                right++;
            }
        }
        return minLength == Integer.MAX_VALUE ? 0 : minLength;
    }

    /**
     * https://leetcode.cn/problems/minimum-window-substring
     * 这种方法明显更好
     *
     */
    public String minWindow(String s, String t) {
        // target: 记录 t 中每个字符需要出现多少次
        Map<Character, Integer> target = new HashMap<>();
        buildTarget(t, target);
        // window: 记录当前窗口中每个字符的实际出现次数
        Map<Character, Integer> window = new HashMap<>();

        int required = target.size();   // t 中有几种不同的字符
        int formed = 0;                 // 当前有几种字符的频次已达 target 要求

        int left = 0, right = 0;
        int minLen = Integer.MAX_VALUE;
        int minLeft = 0;  // 记录最优窗口的起始位置，避免频繁 substring

        while (right < s.length()) {
            // ----- 1. 扩张右边界 -----
            char c = s.charAt(right);
            // 不在target也一起增加，不影响
            window.merge(c, 1, Integer::sum);

            // 如果当前字符是目标字符，且频次刚好达标，formed++
            if (target.containsKey(c) && window.get(c).intValue() == target.get(c).intValue()) {
                formed++;
            }

            // ----- 2. 窗口已覆盖 t，尝试收缩左边界 -----
            while (left <= right && formed == required) {
                // 记录当前窗口
                int curLen = right - left + 1;
                if (curLen < minLen) {
                    minLen = curLen;
                    minLeft = left;
                }

                char leftChar = s.charAt(left);
                // 收缩：移除 leftChar
                window.merge(leftChar, -1, Integer::sum);
                // 如果移除了一个"恰好达标"的目标字符，formed--
                if (target.containsKey(leftChar) && window.get(leftChar).intValue() < target.get(leftChar).intValue()) {
                    formed--;
                }
                left++;
            }

            // 继续扩张右边界
            right++;
        }

        return minLen == Integer.MAX_VALUE ? "" : s.substring(minLeft, minLeft + minLen);
    }

    private void buildTarget(String t, Map<Character, Integer> target) {
        for (int i = 0; i < t.length(); i++) {
            target.merge(t.charAt(i), 1, Integer::sum);
        }
    }
}
