package leetcode;

import java.util.ArrayList;
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
