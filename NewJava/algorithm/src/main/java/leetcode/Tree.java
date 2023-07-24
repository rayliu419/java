package leetcode;


import java.util.ArrayList;
import java.util.Date;
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
     * 将二叉树打成一个链表
     *
     * @param root
     */
    public void flatten(TreeNode root) {
        if (root == null) {
            return;
        }
        doFlatten(root);
    }

    private TreeNode doFlatten(TreeNode node) {
        if (node == null) {
            return null;
        }
        TreeNode leftChild = null;
        TreeNode rightChild = null;
        if (node.left != null) {
            leftChild = doFlatten(node.left);
        }
        if (node.right != null) {
            rightChild = doFlatten(node.right);
        }
        if (leftChild != null) {
            TreeNode temp = leftChild;
            while (temp.right != null) {
                temp = temp.right;
            }
            temp.right = rightChild;
            node.left = null;
            node.right = leftChild;
        } else {
            node.right = rightChild;
        }
        return node;
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

    public static void testDiameterOfBinaryTree() {
        TreeNode root = new TreeNode(1,
                new TreeNode(2,
                        new TreeNode(4, null, null),
                        new TreeNode(5, null, null)
                ),
                new TreeNode(3, null, null)
        );
        System.out.println(new Tree().diameterOfBinaryTree(root));

        TreeNode root2 = new TreeNode(1, null, null);
        System.out.println(new Tree().diameterOfBinaryTree(root2));
    }

    /**
     *
     * https://leetcode.cn/problems/lowest-common-ancestor-of-a-binary-tree/
     *
     * 这个核心的解法是：存储临时计算的结果，假设n1和n2一定存在，则找到n1或者n2直接返回。
     * 如果后续在上层做了调整，说明发现了一个更好的结果，如果没有，则以前那个就是解。
     * 这比我原始的发现了一个节点，然后在以此根为节点的树下查找另外一个节点的性能要高的多，避免了
     * 不必要的递归调用。
     * 这个修正解的思路非常值的学习。
     *
     * 此函数只有在根返回时才能保证解是对的，跟一般函数的语义定义还不一样。
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) {
            return null;
        }
        if (root.val == p.val) {
            return root;
        }
        if (root.val == q.val) {
            return root;
        }
        TreeNode findLeft = lowestCommonAncestor(root.left, p, q);
        TreeNode findRight = lowestCommonAncestor(root.right, p, q);
        if (findLeft != null && findRight != null) {
            // 修正解，左右都找到了，当前节点才是最后的解
            return root;
        }
        if (findLeft != null && findRight == null) {
            // 往上传递当前找到的解
            return findLeft;
        }
        if (findRight != null && findLeft == null) {
            return findRight;
        }
        return null;
    }


    /**
     *  二叉树中查找LCA。
     *  这里的核心是比普通二叉树减少搜索路径。
     *
     * @param root
     * @param p
     * @param q
     * @return
     */
    public TreeNode lowestCommonAncestorBST(TreeNode root, TreeNode p, TreeNode q) {
        // 前面的逻辑跟普通二叉树一样
        if (root == null) {
            return null;
        }
        if (root.val == p.val) {
            return root;
        }
        if (root.val == q.val) {
            return root;
        }
        int maxValue = Math.max(p.val, q.val);
        int minValue = Math.min(p.val, q.val);
        if (root.val < minValue) {
            // 两个待查找都小于curNode，结果在左子树中
            return lowestCommonAncestor(root.right, p, q);
        }
        if (root.val > maxValue) {
            // 两个待查找都大于curNode，结果在右子树中
            return lowestCommonAncestor(root.left, p, q);
        }
        // 一个大于，一个小于。直接返回，这里返回的实际上由于是后序遍历，返回的是底层第一次分开两个待查节点的祖先，直接作为结果返回即可。
        return root;
    }

    public static void testLowestCommonAncestor() {
        TreeNode root = new TreeNode(3,
                new TreeNode(5,
                        new TreeNode(6, null, null),
                        new TreeNode(2,
                                new TreeNode(7, null, null),
                                new TreeNode(4, null, null))
                ),
                new TreeNode(1,
                        new TreeNode(0, null, null),
                        new TreeNode(8, null, null)
                )
        );

        System.out.println(new Tree().lowestCommonAncestor(root, new TreeNode(0, null, null), new TreeNode(8, null, null)));

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

    public static void main(String[] args) {
//        TreeNode root = new TreeNode(3,
//                new TreeNode(5,
//                        new TreeNode(6, null, null),
//                        new TreeNode(2,
//                                new TreeNode(7, null, null),
//                                new TreeNode(4, null, null)
//                        )),
//                new TreeNode(1,
//                        new TreeNode(0, null, null),
//                        new TreeNode(8, null, null)
//                )
//        );
//        List<Integer> result = new Tree().distanceK(root, new TreeNode(5, null, null), 2);
//        System.out.println("pause");

        TreeNode root2 = new TreeNode(0,
                new TreeNode(1,
                        new TreeNode(3, null, null),
                        new TreeNode(2, null, null)),
                null);
        List<Integer> result2 = new Tree().distanceK(root2, new TreeNode(2, null, null), 1);
        System.out.println("pause");
    }

}
