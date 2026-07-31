package leetcode;

import java.util.HashMap;
import java.util.Map;

public class SlideWindow {

    /**
     * LeetCode 3 — 无重复字符最长子串
     *
     * 模板：最大窗口
     * 窗口范围: [left, right]
     * 窗口状态: charCount (每个字符出现频次)
     * 窗口扩张: charCount.put(c, getOrDefault(c,0)+1)
     * 窗口收缩: charCount.put(leftChar, get(leftChar)-1); left++
     * 触发收缩的条件: 当前字符 c 的频次 > 1（有重复）
     * 答案更新时机: 收缩后窗口合法时, Math.max(answer, right - left + 1)
     */
    public int lengthOfLongestSubstring(String s) {
        Map<Character, Integer> charCount = new HashMap<>();
        int left = 0;
        int answer = 0;
        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);
            charCount.put(c, charCount.getOrDefault(c, 0) + 1);
            while (charCount.get(c) > 1) {
                char leftChar = s.charAt(left);
                charCount.put(leftChar, charCount.get(leftChar) - 1);
                left++;
            }
            answer = Math.max(answer, right - left + 1);
        }
        return answer;
    }

    /**
     * LeetCode 340 — 最多包含 K 个不同字符的最长子串
     *
     * 模板：最大窗口
     * 窗口范围: [left, right]
     * 窗口状态: charCount (每个字符出现频次)
     * 窗口扩张: charCount.put(c, getOrDefault(c,0)+1)
     * 窗口收缩: charCount.put(leftChar, get(leftChar)-1); 频次归零则 remove(leftChar); left++
     * 触发收缩的条件: charCount.size() > K（超过 K 种不同字符）
     * 答案更新时机: 收缩后窗口合法时, Math.max(answer, right - left + 1)
     */
    public int lengthOfLongestSubstringKDistinct(String s, int k) {
        if (k == 0) return 0;
        Map<Character, Integer> charCount = new HashMap<>();
        int left = 0;
        int answer = 0;
        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);
            charCount.put(c, charCount.getOrDefault(c, 0) + 1);
            while (charCount.size() > k) {
                char leftChar = s.charAt(left);
                int leftCount = charCount.get(leftChar);
                if (leftCount == 1) {
                    charCount.remove(leftChar);
                } else {
                    charCount.put(leftChar, leftCount - 1);
                }
                left++;
            }
            answer = Math.max(answer, right - left + 1);
        }
        return answer;
    }

    /**
     * LeetCode 904 — Fruit Into Baskets
     *
     * 本质上就是 K=2 的「最多包含 K 个不同字符的最长子串」
     * 区别在于输入是 int[] 而非 String
     * 模板：最大窗口
     * 窗口范围: [left, right]
     * 窗口状态: fruitCount (每种水果数量)
     * 窗口扩张: fruitCount.put(f, getOrDefault(f,0)+1)
     * 窗口收缩: fruitCount.put(leftFruit, get(leftFruit)-1); 数量归零则 remove(leftFruit); left++
     * 触发收缩的条件: fruitCount.size() > 2（超过 2 个篮子）
     * 答案更新时机: 收缩后窗口合法时, Math.max(answer, right - left + 1)
     */
    public int totalFruit(int[] fruits) {
        Map<Integer, Integer> fruitCount = new HashMap<>();
        int left = 0;
        int answer = 0;
        for (int right = 0; right < fruits.length; right++) {
            int f = fruits[right];
            fruitCount.put(f, fruitCount.getOrDefault(f, 0) + 1);
            while (fruitCount.size() > 2) {
                int leftFruit = fruits[left];
                int leftCount = fruitCount.get(leftFruit);
                if (leftCount == 1) {
                    fruitCount.remove(leftFruit);
                } else {
                    fruitCount.put(leftFruit, leftCount - 1);
                }
                left++;
            }
            answer = Math.max(answer, right - left + 1);
        }
        return answer;
    }

    /**
     * LeetCode 209 — Minimum Size Subarray Sum
     *
     * 模板：最小窗口
     * 窗口范围: [left, right]
     * 窗口状态: sum（当前窗口的和）
     * 窗口扩张: sum += nums[right]
     * 窗口收缩: sum -= nums[left]; left++
     * 触发收缩的条件: sum >= target（窗口满足目标）
     * 答案更新时机: 收缩循环内, Math.min(answer, right - left + 1)
     */
    public int minSubArrayLen(int target, int[] nums) {
        int left = 0;
        int sum = 0;
        int answer = Integer.MAX_VALUE;
        for (int right = 0; right < nums.length; right++) {
            sum += nums[right];
            while (sum >= target) {
                answer = Math.min(answer, right - left + 1);
                sum -= nums[left];
                left++;
            }
        }
        return answer == Integer.MAX_VALUE ? 0 : answer;
    }

    /**
     * LeetCode 76 — Minimum Window Substring
     *
     * 模板：最小窗口
     * 窗口范围: [left, right]
     * 窗口状态: target（t 所需频次）, window（当前窗口频次）, formed（达标字符数）, required（所需字符种类数）
     * 窗口扩张: window.put(c, window.getOrDefault(c, 0) + 1); 如果字符频次达标则 formed++
     * 窗口收缩: window.put(leftChar, window.get(leftChar) - 1); 如果频次跌破达标线则 formed--; left++
     * 触发收缩的条件: formed == required（窗口覆盖了 t）
     * 答案更新时机: 收缩循环内, Math.min(answer, right - left + 1)
     */
    public String minWindow(String s, String t) {
        Map<Character, Integer> target = new HashMap<>();
        for (int i = 0; i < t.length(); i++) {
            target.put(t.charAt(i), target.getOrDefault(t.charAt(i), 0) + 1);
        }
        Map<Character, Integer> window = new HashMap<>();
        // 最重要的是formed和required的设置，是的能快速的计算windowIsValid()
        int required = target.size();
        int formed = 0;
        int left = 0;
        int minLen = Integer.MAX_VALUE;
        int minLeft = 0;

        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);
            window.put(c, window.getOrDefault(c, 0) + 1);
            if (target.containsKey(c) && window.get(c).intValue() == target.get(c).intValue()) {
                formed++;
            }
            while (left <= right && formed == required) {
                if (right - left + 1 < minLen) {
                    minLen = right - left + 1;
                    minLeft = left;
                }
                char leftChar = s.charAt(left);
                window.put(leftChar, window.get(leftChar) - 1);
                if (target.containsKey(leftChar)
                        && window.get(leftChar).intValue() < target.get(leftChar).intValue()) {
                    formed--;
                }
                left++;
            }
        }
        return minLen == Integer.MAX_VALUE ? "" : s.substring(minLeft, minLeft + minLen);
    }

    /**
     * https://leetcode.com/problems/permutation-in-string/description/?envType=problem-list-v2&envId=sliding-window
     * 难点主要是cnt数组的使用
     */
    public boolean checkInclusion(String s1, String s2) {
        if (s2.length() < s1.length()) return false;

        // cnt[c]  = 当前窗口还缺多少个字符 c（负 = 多了，零 = 刚好）
        int[] cnt = new int[26];
        for (char c : s1.toCharArray()) cnt[c - 'a']++;

        int k = s1.length();           // 固定窗口大小
        int need = k;                   // 还缺多少个字符才能凑齐 s1

        for (int i = 0; i < k; i++) {
            char c = s2.charAt(i);
            cnt[c - 'a']--;               // 窗口拥有了这个字符 → 需求量减 1
            if (cnt[c - 'a'] >= 0) need--; // cnt>=0 说明s1确实需要这个字符
        }
        if (need == 0) return true;

        // need可以继续使用
        for (int i = k; i < s2.length(); i++) {
            // --- add(i)：右边界 s2[i] 进入窗口 ---
            char add = s2.charAt(i);
            cnt[add - 'a']--;              // 多了一个，差额减 1
            if (cnt[add - 'a'] >= 0) need--; // 减之前 cnt>0（还缺），现在少缺一个

            // --- remove(i - k)：左边界 s2[i-k] 离开窗口 ---
            char rem = s2.charAt(i - k);
            cnt[rem - 'a']++;              // 少了一个，差额加 1
            if (cnt[rem - 'a'] > 0) need++; // 加之后 cnt>0（变缺了），多缺一个

            if (need == 0) return true;
        }

        return false;
    }
}
