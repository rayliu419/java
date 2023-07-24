package leetcode;

import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

public class DFS {

    public int rob(TreeNode root) {
        Map<TreeNode, Integer> cache = new HashMap<>();
        return doRob(root, cache);
    }

    /**
     *
     * 返回的是以node为根，canRob为某个flag的最大可抢价值
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
        int[][] directions = new int[][] {
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

    private boolean isValidPos(int x, int y, int m, int n){
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

    public static void main(String[] args) {
//        String s = "leetcode";
//        List<String> list = List.of("leet", "code");
//        System.out.println(new DFS().wordBreak(s, list));

        Map<String, Integer> map = new HashMap<>();
        map.put("test", 2);
        map.replace("test", 1);
        System.out.println("here");
    }

}
