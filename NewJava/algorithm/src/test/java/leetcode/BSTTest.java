package leetcode;

import infra.TreeNode;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class BSTTest {

    private final BST bst = new BST();

    @Test
    public void testValidBST() {
        TreeNode root = new TreeNode(5,
                new TreeNode(3, new TreeNode(2), new TreeNode(4)),
                new TreeNode(8, new TreeNode(6), new TreeNode(9))
        );
        assertTrue(bst.isValidBSTInorderRecursive(root));
        assertTrue(bst.isValidBSTInterval(root));
    }

    @Test
    public void testInvalidRightSubtree() {
        // 右子树中出现小于根的值
        TreeNode root = new TreeNode(5,
                new TreeNode(3),
                new TreeNode(7, new TreeNode(4), new TreeNode(8))
        );
        assertFalse(bst.isValidBSTInorderRecursive(root));
        assertFalse(bst.isValidBSTInterval(root));
    }

    @Test
    public void testInvalidLeftSubtree() {
        // 左子树中出现大于根的值
        TreeNode root = new TreeNode(5,
                new TreeNode(3, null, new TreeNode(6)),
                new TreeNode(8)
        );
        assertFalse(bst.isValidBSTInorderRecursive(root));
        assertFalse(bst.isValidBSTInterval(root));
    }

    @Test
    public void testBoundaryMaxValue() {
        TreeNode root = new TreeNode(Integer.MAX_VALUE);
        assertTrue(bst.isValidBSTInorderRecursive(root));
        assertTrue(bst.isValidBSTInterval(root));
    }

    @Test
    public void testNull() {
        assertTrue(bst.isValidBSTInorderRecursive(null));
        assertTrue(bst.isValidBSTInterval(null));
    }

    @Test
    public void testSingleNode() {
        TreeNode root = new TreeNode(1);
        assertTrue(bst.isValidBSTInorderRecursive(root));
        assertTrue(bst.isValidBSTInterval(root));
    }

    @Test
    public void testKthSmallest() {
        //       5
        //      / \
        //     3   8
        //    / \
        //   2   4
        TreeNode root = new TreeNode(5,
                new TreeNode(3, new TreeNode(2), new TreeNode(4)),
                new TreeNode(8)
        );
        // 中序遍历 = [2, 3, 4, 5, 8]
        int[] expected = {2, 3, 4, 5, 8};
        for (int k = 1; k <= 5; k++) {
            assertEquals(expected[k - 1],
                    (int) bst.kthSmallestInorder(root, k));
            assertEquals(expected[k - 1],
                    bst.kthSmallestIterative(root, k));
        }
    }

    @Test
    public void testKthSmallestSingleNode() {
        TreeNode root = new TreeNode(42);
        assertEquals(42, (int) bst.kthSmallestInorder(root, 1));
        assertEquals(42, bst.kthSmallestIterative(root, 1));
    }

    @Test
    public void testKthSmallestLeftSkewed() {
        //    3
        //   /
        //  2
        // /
        //1
        TreeNode root = new TreeNode(3,
                new TreeNode(2, new TreeNode(1), null),
                null
        );
        assertEquals(1, (int) bst.kthSmallestInorder(root, 1));
        assertEquals(2, bst.kthSmallestIterative(root, 2));
    }

    @Test
    public void testSortedArrayToBSTOdd() {
        // [-10, -3, 0, 5, 9]
        //        0
        //       / \
        //     -3   9
        //     /   /
        //   -10  5
        int[] nums = {-10, -3, 0, 5, 9};
        TreeNode root = bst.sortedArrayToBST(nums);
        assertTrue(bst.isValidBSTInterval(root));
        assertTrue(isBSTInorderMatch(nums, root));
    }

    @Test
    public void testSortedArrayToBSTEven() {
        // [1, 3, 5, 7]
        //      3
        //     / \
        //    1   5
        //         \
        //          7
        int[] nums = {1, 3, 5, 7};
        TreeNode root = bst.sortedArrayToBST(nums);
        assertTrue(bst.isValidBSTInterval(root));
        assertTrue(isBSTInorderMatch(nums, root));
    }

    @Test
    public void testSortedArrayToBSTSingle() {
        TreeNode root = bst.sortedArrayToBST(new int[]{42});
        assertTrue(bst.isValidBSTInterval(root));
        assertEquals(42, root.val);
    }

    @Test
    public void testSortedArrayToBSTEmpty() {
        assertNull(bst.sortedArrayToBST(new int[0]));
        assertNull(bst.sortedArrayToBST(null));
    }

    @Test
    public void testSortedArrayToBSTLarge() {
        int[] nums = new int[1000];
        for (int i = 0; i < 1000; i++) {
            nums[i] = i;
        }
        TreeNode root = bst.sortedArrayToBST(nums);
        assertTrue(bst.isValidBSTInterval(root));
        assertTrue(isBSTInorderMatch(nums, root));
    }

    // 中序遍历结果与原始有序数组一一比对
    private boolean isBSTInorderMatch(int[] expected, TreeNode root) {
        int[] idx = {0};
        int[] vals = new int[expected.length];
        collectInorder(root, vals, idx);
        for (int i = 0; i < expected.length; i++) {
            if (expected[i] != vals[i]) return false;
        }
        return true;
    }

    private void collectInorder(TreeNode node, int[] vals, int[] idx) {
        if (node == null) return;
        collectInorder(node.left, vals, idx);
        vals[idx[0]++] = node.val;
        collectInorder(node.right, vals, idx);
    }

    @Test
    public void testDuplicateValue() {
        // 重复值不合法：左孩子等于根
        TreeNode root = new TreeNode(2,
                new TreeNode(2),
                null
        );
        assertFalse(bst.isValidBSTInorderRecursive(root));
        assertFalse(bst.isValidBSTInterval(root));
    }
}
