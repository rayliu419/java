package leetcode;

import infra.TreeNode;

public class BST {

    /**
     * https://leetcode.com/problems/validate-binary-search-tree 思路一：中序遍历（递归版）+ 数组传参
     *
     * 递归进行中序遍历，用数组持有 prev 引用（绕过 Java 值传递），
     * 在遍历过程中检查是否严格递增。
     */
    public boolean isValidBSTInorderRecursive(TreeNode root) {
        TreeNode[] prev = {null};
        return doInorderCheck(root, prev);
    }

    private boolean doInorderCheck(TreeNode node, TreeNode[] prev) {
        if (node == null) {
            return true;
        }
        if (!doInorderCheck(node.left, prev)) {
            return false;
        }
        if (prev[0] != null && prev[0].val >= node.val) {
            return false;
        }
        prev[0] = node;
        return doInorderCheck(node.right, prev);
    }

    /**
     * https://leetcode.com/problems/validate-binary-search-tree 思路二：区间约束递归
     *
     * 向子树传递 (min, max) 开区间约束，每个节点的值必须落在区间内。
     * 左子树更新上界为当前节点值，右子树更新下界为当前节点值。
     * 用 null 表示无边界，避免 int 边界值的困扰。
     */
    public boolean isValidBSTInterval(TreeNode root) {
        return doValidate(root, null, null);
    }

    private boolean doValidate(TreeNode node, Integer min, Integer max) {
        if (node == null) {
            return true;
        }
        if (min != null && node.val <= min) {
            return false;
        }
        if (max != null && node.val >= max) {
            return false;
        }
        return doValidate(node.left, min, node.val)
                && doValidate(node.right, node.val, max);
    }

    /**
     * https://leetcode.com/problems/kth-smallest-element-in-a-bst 方式一：递归 + 数组传参
     *
     * 利用数组绕过 Java 值传递的限制，在递归调用链中共享计数器。
     * 不依赖类变量，线程安全。
     */
    public Integer kthSmallestInorder(TreeNode root, int k) {
        int[] count = {0};
        int[] result = {Integer.MIN_VALUE};
        doKthInorder(root, k, count, result);
        return result[0];
    }

    private void doKthInorder(TreeNode node, int k, int[] count, int[] result) {
        if (node == null) {
            return;
        }
        doKthInorder(node.left, k, count, result);
        count[0]++;
        if (count[0] == k) {
            result[0] = node.val;
            return;
        }
        doKthInorder(node.right, k, count, result);
    }

    /**
     * https://leetcode.com/problems/kth-smallest-element-in-a-bst 方式二：迭代中序遍历（显式栈）
     *
     * 完全无需类变量和数组 Hack，用显式栈模拟中序遍历，走到第 k 个就停。
     */
    public int kthSmallestIterative(TreeNode root, int k) {
        java.util.Deque<TreeNode> stack = new java.util.ArrayDeque<>();
        TreeNode cur = root;
        int count = 0;

        while (cur != null || !stack.isEmpty()) {
            while (cur != null) {
                stack.push(cur);
                cur = cur.left;
            }
            cur = stack.pop();
            count++;
            if (count == k) {
                return cur.val;
            }
            cur = cur.right;
        }
        return -1;
    }
}
