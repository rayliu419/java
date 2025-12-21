package leetcode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class BFS {

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }

        public static TreeNode buildTree(Integer[] values) {
            if (values == null || values.length == 0) {
                return null;
            }
            return buildTree(values, 0);
        }

        private static TreeNode buildTree(Integer[] values, int index) {
            if (index >= values.length || values[index] == null) {
                return null;
            }
            TreeNode node = new TreeNode(values[index]);
            node.left = buildTree(values, index * 2 + 1);
            node.right = buildTree(values, index * 2 + 2);
            return node;
        }

    }

    /**
     * LeetCode: <a href="https://leetcode.com/problems/find-largest-value-in-each-tree-row/?envType=problem-list-v2&envId=breadth-first-search">Find Largest Value in Each Tree Row</a>
     */
    static class FindLargestValues {
        public static void testLargestValues() {
            TreeNode root = TreeNode.buildTree(new Integer[]{1, 3, 2, 5, 3, null, 9});
            List<Integer> result = largestValues(root);
            result.forEach(System.out::println);
        }

        public static List<Integer> largestValues(TreeNode root) {
            if (root == null) {
                return new ArrayList<>();
            }
            Deque<TreeNode> curLevel = new ArrayDeque<>();
            Deque<TreeNode> nextLevel = new ArrayDeque<>();
            List<Integer> result = new ArrayList<>();
            curLevel.add(root);
            int maxValue = Integer.MIN_VALUE;
            while (!curLevel.isEmpty()) {
                TreeNode cur = curLevel.pop();
                if (cur.val > maxValue) {
                    maxValue = cur.val;
                }
                if (cur.left != null) {
                    nextLevel.add(cur.left);
                }
                if (cur.right != null) {
                    nextLevel.add(cur.right);
                }
                if (curLevel.isEmpty()) {
                    result.add(maxValue);
                    maxValue = Integer.MIN_VALUE;
                    curLevel.addAll(nextLevel);
                    nextLevel.clear();
                }
            }
            return result;
        }
    }

    /**
     * Leetcode: <a href="https://leetcode.com/problems/longest-increasing-path-in-a-matrix/description/?envType=problem-list-v2&envId=breadth-first-search">Longest Increasing Path in a Matrix</a>
     * TODO: 这种题目用BFS做，visited矩阵的作用是不一样的!!!!
     */
    static class LongestIncreasePath {

        public static int longestIncreasingPath(int[][] matrix) {
            int maxPathLength = Integer.MIN_VALUE;
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[0].length; j++) {
                    int pathLength = calculateMaxLength(i, j, matrix);
                    if (pathLength > maxPathLength) {
                        maxPathLength = pathLength;
                    }
                }
            }
            return maxPathLength;
        }

        public static int calculateMaxLength(int i, int j, int[][] matrix) {
            int[][] visited = new int[matrix.length][matrix[0].length];
            Deque<int[]> curLevel = new ArrayDeque<>();
            Deque<int[]> nextLevel = new ArrayDeque<>();
            curLevel.add(new int[]{i, j});
            int[][] directions = new int[][]{
                    {-1, 0}, // up
                    {1, 0},
                    {0, -1},
                    {0, 1}
            };
            int result = 0;
            visited[i][j] = 1;
            while (!curLevel.isEmpty()) {
                int[] cur = curLevel.pop();
                int curValue = matrix[cur[0]][cur[1]];
                for (int[] direction : directions) {
                    int nextI = cur[0] + direction[0];
                    int nextJ = cur[1] + direction[1];
                    // TODO: visited[nextI][nextJ] = 1; wrong to set here, why?
                    // Important!!
                    // visited 到底应该代表什么？
                    // visited 的作用不是“这个格子我看过一眼”
                    // 这个格子已经通过一条严格递增路径被加入过队列（将要或已经被层序访问）
                    /**
                     * 考虑测试测试矩阵：     {9, 9, 4},
                     *                     {6, 6, 8},
                     *                     {2, 1, 1}
                     * 在从i = 2, j = 1 位置探索时，i = 1, j = 0的6位置：
                     * 1->2->6->9 的路线会因为 前一条探索路线 1->6->6 打断。
                     * 可以认为在同一层探索时，必须得使用才能设置visited。
                     * 即使只有一条路线被探索，最终结果也可以得到保存。
                     */
                    if (isValidPos(nextI, nextJ, visited, matrix)) {
                        if (matrix[nextI][nextJ] > curValue) {
                            nextLevel.add(new int[]{nextI, nextJ});
                            visited[nextI][nextJ] = 1;
                        }
                    }
                }
                if (curLevel.isEmpty()) {
                    result++;
                    curLevel.addAll(nextLevel);
                    nextLevel.clear();
                }
            }
            return result;
        }

        public static boolean isValidPos(int i, int j, int[][] visited, int[][] matrix) {
            if (i < 0 || i >= matrix.length) {
                return false;
            }
            if (j < 0 || j >= matrix[0].length) {
                return false;
            }
            if (visited[i][j] == 1) {
                return false;
            }
            return true;
        }

        public static void testLongestIncreasingPath() {
            System.out.println(longestIncreasingPath(new int[][]{
                    {9, 9, 4},
                    {6, 6, 8},
                    {2, 1, 1}
            }));

            System.out.println(longestIncreasingPath(new int[][]{
                    {3,4,5},{3,2,6},{2,2,1}
            }));
        }

    }


    public static void main(String[] args) {
//        FindLargestValues.testLargestValues();

        LongestIncreasePath.testLongestIncreasingPath();
    }


}
