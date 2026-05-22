package leetcode;

import infra.TreeNode;

import java.time.temporal.Temporal;

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

    /**
     * https://leetcode.com/problems/insert-into-a-binary-search-tree/
     */
    public TreeNode insertIntoBST(TreeNode root, int val) {
        if (root == null) {
            TreeNode cur = new TreeNode(val);
            return cur;
        }
        if (root.val > val) {
            root.left = insertIntoBST(root.left, val);
        } else {
            root.right = insertIntoBST(root.right, val);
        }
        return root;
    }

    /**
     * https://leetcode.com/problems/delete-node-in-a-bst/
     * 删除的三种情况：
     * 1. 删除节点是叶子节点，可以直接删除。
     * 2. 删除节点只有一个子节点，就用这个子节点替代删除节点
     * 3. 删除节点有两个子节点，需要去右子树找到替代的节点，把那个节点的值复制过来。然后相当于再在右子树删除那个替代的val.
     *
     * 这里关键点就是定义正确的递归语义: 了解这个递归函数的语义，才能知道怎么实现这个递归函数
     * 返回已经调整好顺序的以root为根节点的子树
     */
    public TreeNode deleteNode(TreeNode root, int key) {
        if (root == null) {
            // 如果都走到了null路径了，说明这棵树没有这个key
            return null;
        }
        if (root.val > key) {
            root.left = deleteNode(root.left, key);
        } else if (root.val < key) {
            root.right = deleteNode(root.right, key);
        } else {
            if (root.left == null && root.right == null) {
                // case 1
                // 要把这个节点的父节点相应的孩子置空，所以要返回某个东西给上层
                // 因为删除了，所以直接返回null
                return null;
            }
            // case 2
            if (root.left == null) {
                return root.right;
            }
            if (root.right == null) {
                return root.left;
            }
            // case 3
            TreeNode next = root.right;
            while (next.left != null) {
                next = next.left;
            }
            root.val = next.val;
            root.right = deleteNode(root.right, next.val);
        }
        return root;
    }

    /**
     *  https://leetcode.com/problems/convert-sorted-array-to-binary-search-tree/
     *
     */
    public TreeNode sortedArrayToBST(int[] nums) {
        if (nums == null || nums.length == 0) {
            return null;
        }
        return buildTree(nums, 0, nums.length - 1);
    }

    private TreeNode buildTree(int[] nums, int low, int high) {
        if (low > high) {
            return null;
        }
        int mid = low + (high - low) / 2;
        TreeNode root = new TreeNode(nums[mid]);
        root.left = buildTree(nums, low, mid - 1);
        root.right = buildTree(nums, mid + 1, high);
        return root;
    }

    /**
     * https://leetcode.com/problems/flatten-binary-tree-to-linked-list
     *
     */
    public void flatten(TreeNode root) {
        flattenTree(root);
    }

    private TreeNode flattenTree(TreeNode root) {
        if (root == null) {
            return null;
        }
        root.left = flattenTree(root.left);
        root.right = flattenTree(root.right);
        if (root.left == null && root.right == null) {
            return root;
        }
        if (root.left == null) {
            return root;
        }
        if (root.right == null) {
            root.right = root.left;
            root.left = null;
            return root;
        }
        // root.left != null && root.right != null
        TreeNode next = root.left;
        while (next.right != null) {
            next = next.right;
        }
        next.right = root.right;
        root.right = root.left;
        root.left = null;
        return root;
    }

    /**
     * https://leetcode.com/problems/serialize-and-deserialize-binary-tree/
     */
    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        serialize(root, sb);
        return sb.toString();
    }

    private void serialize(TreeNode root, StringBuilder sb) {
        if (root == null) {
            sb.append("#").append(" ");
            return;
        }
        sb.append(root.val).append(" ");
        serialize(root.left, sb);
        serialize(root.right, sb);
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        // index[0] = 0
        int[] index = new int[1];
        String[] array = data.split("\\s+");
        return deserialize(array, index);
    }

    private TreeNode deserialize(String[] data, int[] index) {
        String cur = data[index[0]];
        if (cur.equals("#")) {
            // 消耗一个
            index[0]++;
            return null;
        }
        TreeNode root = new TreeNode(Integer.parseInt(cur));
        index[0]++;
        root.left = deserialize(data, index);
        root.right = deserialize(data, index);
        return root;
    }
}
