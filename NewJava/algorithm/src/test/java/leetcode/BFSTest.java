package leetcode;

import infra.TreeNode;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class BFSTest {

    private final BFS bfs = new BFS();

    @Test
    public void testLargestValuesBasic() {
        TreeNode root = TreeNode.buildTree(new Integer[]{1, 3, 2, 5, 3, null, 9});
        assertEquals(Arrays.asList(1, 3, 9), bfs.largestValues(root));
    }

    @Test
    public void testLargestValuesSingleNode() {
        TreeNode root = new TreeNode(1);
        assertEquals(Arrays.asList(1), bfs.largestValues(root));
    }

    @Test
    public void testLargestValuesEmpty() {
        assertEquals(Arrays.asList(), bfs.largestValues(null));
    }

    @Test
    public void testLongestIncreasingPathNormal() {
        assertEquals(4, BFS.longestIncreasingPath(new int[][]{
                {9, 9, 4},
                {6, 6, 8},
                {2, 1, 1}
        }));
    }

    @Test
    public void testLongestIncreasingPathAnother() {
        assertEquals(4, BFS.longestIncreasingPath(new int[][]{
                {3, 4, 5},
                {3, 2, 6},
                {2, 2, 1}
        }));
    }

    @Test
    public void testLongestIncreasingPathSingleElement() {
        assertEquals(1, BFS.longestIncreasingPath(new int[][]{{1}}));
    }
}
