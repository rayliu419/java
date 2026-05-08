package infra;

import lombok.Data;

@Data
public class TreeNode {

    public int val;
    public TreeNode left;
    public TreeNode right;

    public TreeNode(int val) {
        this.val = val;
        left = null;
        right = null;
    }

    public TreeNode(int val, TreeNode left, TreeNode right) {
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
