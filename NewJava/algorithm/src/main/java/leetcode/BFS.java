package leetcode;

import infra.TreeNode;

import java.util.*;

public class BFS {

    /**
     * 返回每一层的最大值。基础题
     *
     * @param root
     * @return
     */
    public static List<Integer> largestValues(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }
        Queue<TreeNode> curLevel = new ArrayDeque<>();
        Queue<TreeNode> nextLevel = new ArrayDeque<>();
        List<Integer> result = new ArrayList<>();
        curLevel.offer(root);
        int maxValue = Integer.MIN_VALUE;
        while (!curLevel.isEmpty()) {
            TreeNode cur = curLevel.poll();
            if (cur.val > maxValue) {
                maxValue = cur.val;
            }
            if (cur.left != null) {
                nextLevel.offer(cur.left);
            }
            if (cur.right != null) {
                nextLevel.offer(cur.right);
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


    /**
     * Leetcode: <a href="https://leetcode.com/problems/longest-increasing-path-in-a-matrix/description/?envType=problem-list-v2&envId=breadth-first-search">Longest Increasing Path in a Matrix</a>
     * TODO: 这种题目用BFS做，visited矩阵的作用是不一样的!!!!
     */

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

    private static int calculateMaxLength(int i, int j, int[][] matrix) {
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

    private static boolean isValidPos(int i, int j, int[][] visited, int[][] matrix) {
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



}


