package leetcode;


import infra.TreeNode;

import java.util.LinkedList;
import java.util.List;

/**
 * 比较难的是后序遍历的。
 *
 */
public class Tree {

    class Diameter {

        int diameter;
    }

    /**
     * https://leetcode.cn/problems/diameter-of-binary-tree/
     *
     * @return
     */
    public int diameterOfBinaryTree(TreeNode root) {
        Diameter diameter = new Diameter();
        diameter.diameter = -1;
        doDiameterOfBinaryTree(root, diameter);
        return diameter.diameter;
    }

    /**
     * 计算以当前节点大打通的直径长度，是左孩子最长路径+当前节点+右孩子最长路径
     *
     * 同时返回以当前根为节点的最长路径
     *
     * @param node
     * @param diameter
     * @return
     */
    public int doDiameterOfBinaryTree(TreeNode node, Diameter diameter) {
        if (node == null) {
            return 0;
        }
        int leftHeight = doDiameterOfBinaryTree(node.left, diameter);
        int rightHeight = doDiameterOfBinaryTree(node.right, diameter);
        int curDiameter = 0;
        if (node.left != null) {
            curDiameter += leftHeight;
        }
        if (node.right != null) {
            curDiameter += rightHeight;
        }
        if (curDiameter > diameter.diameter) {
            diameter.diameter = curDiameter;
        }
        return Math.max(leftHeight, rightHeight) + 1;
    }

    /**
     *
     * https://leetcode.cn/problems/all-nodes-distance-k-in-binary-tree/
     *
     * @param root
     * @param target
     * @param k
     * @return
     */
    public List<Integer> distanceK(TreeNode root, TreeNode target, int k) {
        if (root == null) {
            return new LinkedList<>();
        }
        List<Integer> result = new LinkedList<>();
        doDistanceK(root, target, k, result);
        return result;
    }

    /**
     * 尝试向上返回与node相距的距离
     *
     * @param node
     * @param target
     * @param k
     * @param result
     * @return
     */
    private int doDistanceK(TreeNode node, TreeNode target, int k, List<Integer> result) {
        if (node == null) {
            return -1;
        }
        if (k < 0) {
            return -1;
        }
        if (node.val == target.val) {
            distanceFromRoot(node, k, result);
            return 1;
        }
        int leftDistance = doDistanceK(node.left, target, k, result);
        if (leftDistance != -1) {
            if (k - leftDistance == 0) {
                result.add(node.val);
                return -1;
            } else {
                distanceFromRoot(node.right, k - 2, result);
                return leftDistance - 1;
            }
        }
        int rightDistance = doDistanceK(node.right, target, k, result);
        if (rightDistance != -1) {
            if (k - rightDistance == 0) {
                result.add(node.val);
                return -1;
            } else {
                distanceFromRoot(node.left, k - 2, result);
                return rightDistance - 1;
            }
        }
        return -1;
    }

    private void distanceFromRoot(TreeNode root, int k, List<Integer> result) {
        if (root == null) {
            return ;
        }
        if (k < 0) {
            return ;
        }
        if (k == 0) {
            result.add(root.val);
        }
        distanceFromRoot(root.left, k - 1, result);
        distanceFromRoot(root.right, k - 1, result);
    }
}
