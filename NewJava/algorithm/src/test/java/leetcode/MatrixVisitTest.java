package leetcode;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;

public class MatrixVisitTest {

    @Test
    public void testSpiralOrder_3x3() {
        int[][] matrix = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        List<Integer> expected = Arrays.asList(1, 2, 3, 6, 9, 8, 7, 4, 5);
        assertEquals(expected, MatrixVisit.spiralOrder(matrix));
    }

    @Test
    public void testSpiralOrder_1xN() {
        int[][] matrix = {
                {1, 2, 3, 4}
        };
        List<Integer> expected = Arrays.asList(1, 2, 3, 4);
        assertEquals(expected, MatrixVisit.spiralOrder(matrix));
    }

    @Test
    public void testSpiralOrder_Nx1() {
        int[][] matrix = {
                {1},
                {2},
                {3}
        };
        List<Integer> expected = Arrays.asList(1, 2, 3);
        assertEquals(expected, MatrixVisit.spiralOrder(matrix));
    }

    @Test
    public void testSpiralOrder_1x1() {
        int[][] matrix = {{5}};
        List<Integer> expected = List.of(5);
        assertEquals(expected, MatrixVisit.spiralOrder(matrix));
    }

    @Test
    public void testSpiralOrder_2x2() {
        int[][] matrix = {
                {1, 2},
                {3, 4}
        };
        List<Integer> expected = Arrays.asList(1, 2, 4, 3);
        assertEquals(expected, MatrixVisit.spiralOrder(matrix));
    }

    @Test
    public void testSpiralOrder_3x4() {
        int[][] matrix = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12}
        };
        List<Integer> expected = Arrays.asList(1, 2, 3, 4, 8, 12, 11, 10, 9, 5, 6, 7);
        assertEquals(expected, MatrixVisit.spiralOrder(matrix));
    }

    @Test
    public void testSpiralOrder_4x3() {
        int[][] matrix = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9},
                {10, 11, 12}
        };
        List<Integer> expected = Arrays.asList(1, 2, 3, 6, 9, 12, 11, 10, 7, 4, 5, 8);
        assertEquals(expected, MatrixVisit.spiralOrder(matrix));
    }

    @Test
    public void testSpiralOrder_5x1() {
        int[][] matrix = {
                {1}, {2}, {3}, {4}, {5}
        };
        List<Integer> expected = Arrays.asList(1, 2, 3, 4, 5);
        assertEquals(expected, MatrixVisit.spiralOrder(matrix));
    }

    private final MatrixVisit visitor = new MatrixVisit();

    @Test
    public void testSetZeroes_Normal() {
        int[][] matrix = {
                {1, 2, 3},
                {4, 0, 6},
                {7, 8, 9}
        };
        visitor.setZeroes(matrix);
        int[][] expected = {
                {1, 0, 3},
                {0, 0, 0},
                {7, 0, 9}
        };
        assertMatrixEquals(expected, matrix);
    }

    @Test
    public void testSetZeroes_ZeroAtOrigin() {
        int[][] matrix = {
                {0, 1},
                {2, 3}
        };
        visitor.setZeroes(matrix);
        int[][] expected = {
                {0, 0},
                {0, 3}
        };
        assertMatrixEquals(expected, matrix);
    }

    @Test
    public void testSetZeroes_FirstRowOnly() {
        int[][] matrix = {
                {1, 0, 2},
                {4, 5, 6},
                {7, 8, 9}
        };
        visitor.setZeroes(matrix);
        int[][] expected = {
                {0, 0, 0},
                {4, 0, 6},
                {7, 0, 9}
        };
        assertMatrixEquals(expected, matrix);
    }

    @Test
    public void testSetZeroes_FirstColOnly() {
        int[][] matrix = {
                {1, 2, 3},
                {0, 5, 6},
                {7, 8, 9}
        };
        visitor.setZeroes(matrix);
        int[][] expected = {
                {0, 2, 3},
                {0, 0, 0},
                {0, 8, 9}
        };
        assertMatrixEquals(expected, matrix);
    }

    @Test
    public void testSetZeroes_NoZero() {
        int[][] matrix = {
                {1, 2},
                {3, 4}
        };
        visitor.setZeroes(matrix);
        int[][] expected = {
                {1, 2},
                {3, 4}
        };
        assertMatrixEquals(expected, matrix);
    }

    @Test
    public void testSetZeroes_AllZero() {
        int[][] matrix = {
                {0, 0},
                {0, 0}
        };
        visitor.setZeroes(matrix);
        int[][] expected = {
                {0, 0},
                {0, 0}
        };
        assertMatrixEquals(expected, matrix);
    }

    @Test
    public void testSetZeroes_SingleElementZero() {
        int[][] matrix = {{0}};
        visitor.setZeroes(matrix);
        assertMatrixEquals(new int[][]{{0}}, matrix);
    }

    @Test
    public void testSetZeroes_SingleElementNonZero() {
        int[][] matrix = {{5}};
        visitor.setZeroes(matrix);
        assertMatrixEquals(new int[][]{{5}}, matrix);
    }

    @Test
    public void testSetZeroes_MultipleZeros() {
        int[][] matrix = {
                {0, 1, 2, 0},
                {3, 4, 5, 2},
                {1, 3, 1, 5}
        };
        visitor.setZeroes(matrix);
        int[][] expected = {
                {0, 0, 0, 0},
                {0, 4, 5, 0},
                {0, 3, 1, 0}
        };
        assertMatrixEquals(expected, matrix);
    }

    private static void assertMatrixEquals(int[][] expected, int[][] actual) {
        assertEquals("row count", expected.length, actual.length);
        for (int i = 0; i < expected.length; i++) {
            assertArrayEquals("row " + i, expected[i], actual[i]);
        }
    }
}
