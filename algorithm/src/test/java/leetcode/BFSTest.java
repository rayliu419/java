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

    @Test
    public void testOrangesRottingExample1() {
        assertEquals(4, bfs.orangesRotting(new int[][]{
                {2, 1, 1},
                {1, 1, 0},
                {0, 1, 1}
        }));
    }

    @Test
    public void testOrangesRottingImpossible() {
        assertEquals(-1, bfs.orangesRotting(new int[][]{
                {2, 1, 1},
                {0, 1, 1},
                {1, 0, 1}
        }));
    }

    @Test
    public void testOrangesRottingNoFresh() {
        assertEquals(0, bfs.orangesRotting(new int[][]{{0, 2}}));
    }

    @Test
    public void testOrangesRottingAllEmpty() {
        assertEquals(0, bfs.orangesRotting(new int[][]{
                {0, 0, 0},
                {0, 0, 0}
        }));
    }

    @Test
    public void testOrangesRottingAllFreshNoRotten() {
        assertEquals(-1, bfs.orangesRotting(new int[][]{
                {1, 1, 1},
                {1, 1, 1}
        }));
    }

    @Test
    public void testOrangesRottingAllRotten() {
        assertEquals(0, bfs.orangesRotting(new int[][]{
                {2, 2},
                {2, 2}
        }));
    }

    @Test
    public void testOrangesRottingSingleStep() {
        assertEquals(1, bfs.orangesRotting(new int[][]{{2, 1}}));
    }

    @Test
    public void testOrangesRottingSingleFresh() {
        assertEquals(-1, bfs.orangesRotting(new int[][]{{1}}));
    }

    @Test
    public void testOrangesRottingDisconnected() {
        assertEquals(-1, bfs.orangesRotting(new int[][]{
                {2, 1, 0, 1}
        }));
    }
}
