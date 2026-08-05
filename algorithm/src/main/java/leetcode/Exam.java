package leetcode;


import infra.TreeNode;

public class Exam {

    public boolean isValidBST(TreeNode root) {
        TreeNode[] pre = new TreeNode[1];
        pre[0] = null;
        return isValidBST(root, pre);
    }

    private boolean isValidBST(TreeNode root, TreeNode[] pre) {
        if (root == null) return true;
        boolean ans = isValidBST(root.left, pre);
        if (!ans) return false;
        if (pre[0] != null && root.val <= pre[0].val) return false;
        pre[0] = root;
        return isValidBST(root.right, pre);
    }
}
