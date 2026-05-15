package leetcode;

import infra.TreeNode;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BSTTest {

    private final BST bst = new BST();

    @Test
    public void testValidBST() {
        TreeNode root = new TreeNode(5,
                new TreeNode(3, new TreeNode(2), new TreeNode(4)),
                new TreeNode(8, new TreeNode(6), new TreeNode(9))
        );
        assertTrue(bst.isValidBSTInorder(root));
        assertTrue(bst.isValidBSTInterval(root));
    }

    @Test
    public void testInvalidRightSubtree() {
        // 右子树中出现小于根的值
        TreeNode root = new TreeNode(5,
                new TreeNode(3),
                new TreeNode(7, new TreeNode(4), new TreeNode(8))
        );
        assertFalse(bst.isValidBSTInorder(root));
        assertFalse(bst.isValidBSTInterval(root));
    }

    @Test
    public void testInvalidLeftSubtree() {
        // 左子树中出现大于根的值
        TreeNode root = new TreeNode(5,
                new TreeNode(3, null, new TreeNode(6)),
                new TreeNode(8)
        );
        assertFalse(bst.isValidBSTInorder(root));
        assertFalse(bst.isValidBSTInterval(root));
    }

    @Test
    public void testBoundaryMaxValue() {
        TreeNode root = new TreeNode(Integer.MAX_VALUE);
        assertTrue(bst.isValidBSTInorder(root));
        assertTrue(bst.isValidBSTInterval(root));
    }

    @Test
    public void testNull() {
        assertTrue(bst.isValidBSTInorder(null));
        assertTrue(bst.isValidBSTInterval(null));
    }

    @Test
    public void testSingleNode() {
        TreeNode root = new TreeNode(1);
        assertTrue(bst.isValidBSTInorder(root));
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
            assertEquals(expected[k - 1],
                    bst.kthSmallestBST(root, k));
        }
    }

    @Test
    public void testKthSmallestSingleNode() {
        TreeNode root = new TreeNode(42);
        assertEquals(42, (int) bst.kthSmallestInorder(root, 1));
        assertEquals(42, bst.kthSmallestIterative(root, 1));
        assertEquals(42, bst.kthSmallestBST(root, 1));
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
        assertEquals(3, bst.kthSmallestBST(root, 3));
    }

    @Test
    public void testDuplicateValue() {
        // 重复值不合法：左孩子等于根
        TreeNode root = new TreeNode(2,
                new TreeNode(2),
                null
        );
        assertFalse(bst.isValidBSTInorder(root));
        assertFalse(bst.isValidBSTInterval(root));
    }
}
