package leetcode;

public class SumOfLeft {

    public int sumOfLeftLeaves(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return sum(root.left, true) + sum(root.right, false);
    }

    public int sum(TreeNode node, boolean isLeft) {
        if (node == null) {
            return 0;
        }
        if (node.left == null && node.right == null && isLeft) {
            return node.val;
        }
        return sum(node.left, true) + sum(node.right, false);
    }
}
