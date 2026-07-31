package leetcode;

import infra.TreeNode;
import org.checkerframework.checker.units.qual.A;

import java.util.*;

public class DFS {

    /**
     * https://leetcode.cn/problems/permutations/ - nums里面无重复的数
     *
     * @param root
     * @return
     */
    // 标准模版法解题
    public List<List<Integer>> permute(int[] nums) {
        boolean[] used = new boolean[nums.length];
        List<List<Integer>> finalResult = new ArrayList<>();
        List<Integer> cur = new ArrayList<>();
        int count = nums.length;
        permute(finalResult, cur, nums, used, count);
        return finalResult;
    }

    private void permute(List<List<Integer>> finalResult, List<Integer> cur, int[] nums, boolean[] used, int count) {
        if (count == 0) {
            // 很重要，Java的语义需要完整copy，否则可能会报错
            finalResult.add(new ArrayList<>(cur));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (used[i]) continue;
            cur.add(nums[i]);
            used[i] = true;
            permute(finalResult, cur, nums, used, count - 1);
            cur.remove(cur.size() - 1);
            used[i] = false;
        }
    }

    /**
     * https://leetcode.com/problems/subsets/
     * @param root
     * @return
     */
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> finalResult = new ArrayList<>();
        List<Integer> cur = new ArrayList<>();
        subsets(finalResult, cur, nums, 0);
        return finalResult;
    }

    private void subsets(List<List<Integer>> finalResult, List<Integer> cur, int[] nums, int index) {
        if (index == nums.length) {
            List<Integer> temp = new ArrayList<>(cur);
            finalResult.add(temp);
            return;
        }
        subsets(finalResult, cur, nums, index + 1);
        cur.add(nums[index]);
        subsets(finalResult, cur, nums, index + 1);
        cur.remove(cur.size() - 1);
    }

    /**
     * https://leetcode.com/problems/subsets-ii/
     * 选/不选解法（解法B）
     * nums可能有重复
     * 不能返回重复的子集
     * 选：直接加入当前元素
     * 不选：跳过后面的所有重复值，避免同层重复
     */
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        List<List<Integer>> finalResult = new ArrayList<>();
        List<Integer> cur = new ArrayList<>();
        Arrays.sort(nums);
        subsetsWithDup(finalResult, cur, nums, 0);
        return finalResult;
    }

    private void subsetsWithDup(List<List<Integer>> finalResult, List<Integer> cur, int[] nums, int pos) {
        if (pos == nums.length) {
            finalResult.add(new ArrayList<>(cur));
            return;
        }
        // 选
        cur.add(nums[pos]);
        subsetsWithDup(finalResult, cur, nums, pos + 1);
        cur.remove(cur.size() - 1);
        // 不选 — 跳过所有重复值，避免进入同层重复分支
        // 如果不跳过，选 index=0 的 1 + 不选 index=1 的 1 与
        // 不选 index=0 的 1 + 选 index=1 的 1 会产生相同子集
        while (pos + 1 < nums.length && nums[pos] == nums[pos + 1]) pos++;
        subsetsWithDup(finalResult, cur, nums, pos + 1);
    }

    /**
     * https://leetcode.com/problems/combination-sum/ - nums是没有重复
     * 1. 即使nums没有重复，也需要引入startIndex去重。
     * 2. 如果有重复，则更复杂。
     * @param root
     * @return
     */
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> finalResult = new ArrayList<>();
        List<Integer> cur = new ArrayList<>();
        combinationSum(finalResult, cur, candidates, target, 0);
        return finalResult;
    }

    private void combinationSum(List<List<Integer>> finalResult, List<Integer> cur, int[] candidates, int target, int startIndex) {
        if (target == 0) {
            List<Integer> res = new ArrayList<>(cur);
            finalResult.add(res);
            return ;
        }
        for (int i = startIndex; i < candidates.length; i++) {
            if (target - candidates[i] < 0) continue;
            cur.add(candidates[i]);
            combinationSum(finalResult, cur, candidates, target - candidates[i], i);
            cur.remove(cur.size() - 1);
        }
    }

    /**
     * https://leetcode.com/problems/combination-sum-ii
     * nums可能包含重复的数
     * 每个数只能用一次，"每个数字只能用一次" 指的是每个索引位置上的元素只能用一次，而不是每个数值只能用一次。
     */
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> finalResult = new ArrayList<>();
        List<Integer> cur = new ArrayList<>();
        Arrays.sort(candidates);
        combinationSum2(finalResult, cur, candidates, target, 0);
        return finalResult;
    }

    private void combinationSum2(List<List<Integer>> finalResult, List<Integer> cur, int[] candidates, int target, int startIndex) {
        if (target == 0) {
            List<Integer> res = new ArrayList<>(cur);
            finalResult.add(res);
            return ;
        }
        for (int i = startIndex; i < candidates.length; i++) {
            if (target - candidates[i] < 0) continue;
            // i > startIndex !
            if (i > startIndex && candidates[i] == candidates[i - 1]) continue;
            cur.add(candidates[i]);
            combinationSum2(finalResult, cur, candidates, target - candidates[i], i + 1);
            cur.remove(cur.size() - 1);
        }
    }


    /**
     * https://leetcode.com/problems/generate-parentheses
     * @param root
     * @return
     */
    public List<String> generateParenthesis(int n) {
        List<String> result = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        generateParenthesis(result, sb, 0, 0, n);
        return result;
    }

    private void generateParenthesis(List<String> result, StringBuilder sb, int left, int right, int n) {
        if (right > left || left > n || right > n) return;
        if (left == n && right == n) {
            result.add(sb.toString());
            return;
        }
        sb.append("(");
        generateParenthesis(result, sb, left + 1, right, n);
        sb.deleteCharAt(sb.length() - 1);
        sb.append(")");
        generateParenthesis(result, sb, left, right + 1, n);
        sb.deleteCharAt(sb.length()- 1);
    }

    /**
     * https://leetcode.com/problems/letter-combinations-of-a-phone-number
     * @param root
     * @return
     */
    public List<String> letterCombinations(String digits) {
        Map<Integer, String> number2Chars = new HashMap<>();
        number2Chars.put(2, "abc");
        number2Chars.put(3, "def");
        number2Chars.put(4, "ghi");
        number2Chars.put(5, "jkl");
        number2Chars.put(6, "mno");
        number2Chars.put(7, "pqrs");
        number2Chars.put(8, "tuv");
        number2Chars.put(9, "wxyz");
        List<String> result = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        letterCombinations(result, sb, digits, 0, number2Chars);
        return result;
    }

    private void letterCombinations(List<String> result, StringBuilder sb, String digit, int index,
                                    Map<Integer, String> number2Chars) {
        if (index == digit.length()) {
            result.add(sb.toString());
            return;
        }
        int cur = digit.charAt(index) - '0';
        String s = number2Chars.get(cur);
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            sb.append(c);
            letterCombinations(result, sb, digit, index + 1, number2Chars);
            sb.deleteCharAt(sb.length() - 1);
        }
    }

    public int rob(TreeNode root) {
        Map<TreeNode, Integer> cache = new HashMap<>();
        return doRob(root, cache);
    }

    /**
     * 返回的是以node为根，canRob为某个flag的最大可抢价值
     *
     * @param node
     * @return
     */
    private int doRob(TreeNode node, Map<TreeNode, Integer> cache) {
        if (node == null) {
            return 0;
        }
        if (cache.containsKey(node)) {
            // 已经计算过了
            return cache.get(node);
        }
        int selected, notSelected;
        selected = notSelected = 0;
        selected += node.val;
        if (node.left != null) {
            selected += doRob(node.left.left, cache);
            selected += doRob(node.left.right, cache);
        }
        if (node.right != null) {
            selected += doRob(node.right.left, cache);
            selected += doRob(node.right.right, cache);
        }
        notSelected += doRob(node.left, cache);
        notSelected += doRob(node.right, cache);

        int maxValue = Math.max(selected, notSelected);
        cache.put(node, maxValue);
        return maxValue;
    }


    /**
     * https://leetcode.cn/problems/word-search/
     *
     * @param board
     * @param word
     * @return
     */
    public boolean exist(char[][] board, String word) {
        int pos = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (doExist(board, i, j, word, pos)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean doExist(char[][] board, int x, int y, String word, int pos) {
        int[][] directions = new int[][]{
                {-1, 0},
                {1, 0},
                {0, -1},
                {0, 1},
        };
        char c = board[x][y];
        if (c == word.charAt(pos)) {
            if (pos == word.length() - 1) {
                return true;
            }
            board[x][y] = '-';
            for (int i = 0; i < directions.length; i++) {
                // 尝试4个方向
                int nextX = x + directions[i][0];
                int nextY = y + directions[i][1];
                if (isValidPos(nextX, nextY, board.length - 1, board[0].length - 1)) {
                    if (doExist(board, nextX, nextY, word, pos + 1)) {
                        return true;
                    }
                }
            }
            board[x][y] = c;
        } else {
            return false;
        }
        return false;
    }

    private boolean isValidPos(int x, int y, int m, int n) {
        if (x < 0 || y < 0 || x > m || y > n) {
            return false;
        }
        return true;
    }

    public void testExist() {
        char[][] board = new char[][]{
                {'a', 'b', 'c', 'e'},
                {'s', 'f', 'c', 's'},
                {'a', 'd', 'e', 'e'},
        };
        String word = "abcced";
        System.out.println(new DFS().exist(board, word));
    }


    public boolean wordBreak(String s, List<String> wordDict) {
        Set<String> dict = new HashSet<>();
        dict.addAll(wordDict);
        boolean[] dp = new boolean[s.length() + 1];
        dp[0] = true;
        for (int i = 1; i <= s.length(); i++) {
            for (int j = 0; j < i; j++) {
                if (dp[j]) {
                    String subString = s.substring(j, i);
                    if (dict.contains(subString)) {
                        dp[i] = true;
                    }
                }
            }
            if (!dp[i]) {
                dp[i] = false;
            }
        }
        return dp[s.length()];
    }
}
