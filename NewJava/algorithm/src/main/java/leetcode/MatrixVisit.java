package leetcode;

import java.util.ArrayList;
import java.util.List;

public class MatrixVisit {

    public static List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> result = new ArrayList<>();
        int rowStart, rowEnd, colStart, colEnd;
        rowStart = 0;
        rowEnd = matrix.length - 1;
        colStart = 0;
        colEnd = matrix[0].length - 1;
        while (rowStart <= rowEnd) {
            // -->
            for (int j = colStart; j <= colEnd; j++) {
                result.add(matrix[rowStart][j]);
            }
            rowStart++;
            if (rowStart > rowEnd) break;

            // |
            for (int i = rowStart; i <= rowEnd; i++) {
                result.add(matrix[i][colEnd]);
            }
            colEnd--;
            if (colStart > colEnd) break;

            // <--
            for (int j = colEnd; j >= colStart; j--) {
                result.add(matrix[rowEnd][j]);
            }
            rowEnd--;
            if (rowStart > rowEnd) break;

            // |
            for (int i = rowEnd; i >= rowStart; i--) {
                result.add(matrix[i][colStart]);
            }
            colStart++;
            if (colStart > colEnd) break;
        }
        return result;
    }

    /**
     * https://leetcode.com/problems/transpose-matrix/description/
     * 矩阵转置，基本题型
     * 方阵转置，原地置换
     */
    public int[][] transpose(int[][] matrix) {
        int[][] result = new int[matrix[0].length][matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            // j = i + 1, 中轴线不用
            for (int j = 0; j < matrix[0].length; j++) {
                result[j][i] = matrix[i][j];
            }
        }
        return result;
    }

    /**
     * 矩阵旋转 - 转置上的改进
     *  顺时针90° = 转置 + 每行反转；逆时针 = 转置 + 每列反转
     */
    public void rotate(int[][] matrix) {
        // 方阵转置
        for(int i = 0; i < matrix.length; i++) {
            // 注意方阵转置j = i + 1
            for (int j = i + 1; j < matrix[0].length; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }
        // 每行里的数组做交换
        for (int i = 0; i < matrix.length; i++) {
            int low = 0;
            int high = matrix[0].length - 1;
            while (low < high) {
                int temp = matrix[i][low];
                matrix[i][low] = matrix[i][high];
                matrix[i][high] = temp;
                low++;
                high--;
            }
        }
    }

    /**
     * https://leetcode.com/problems/set-matrix-zeroes/description/
     * in place set row/col zeroes
     *
     * 易错点: 第0行和第0列被用作标记区, 遍历标记清零时必须跳过 (j=1/i=1 开始),
     * 否则 matrix[0][0] 为0时会提前将整列清0, 导致标记区被破坏, 进而误清其他行列.
     * 第0行/列本身通过 setRowZero/setColZero 标志在最后单独处理.
     */
    public void setZeroes(int[][] matrix) {
        boolean setRowZero = false;
        boolean setColZero = false;
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[i][0] == 0) {
                setColZero = true;
            }
        }
        for (int j = 0; j < matrix[0].length; j++) {
            if (matrix[0][j] == 0) {
                setRowZero = true;
            }
        }
        // 用第一行和第一列标注0 (跳过标记区本身)
        for (int i = 1; i < matrix.length; i++) {
            for (int j = 1; j < matrix[0].length; j++) {
                if (matrix[i][j] == 0) {
                    matrix[i][0] = 0;
                    matrix[0][j] = 0;
                }
            }
        }
        // 根据标记清零, 从1开始避免破坏标记区
        // IMPORTANT: i/j 必须从1开始。如果从0开始，第一个循环会干扰到第二个循环。
        // 第0列清0会导致后面的每一行都被清0。
        for (int j = 1; j < matrix[0].length; j++) {
            if (matrix[0][j] == 0) {
                setColZero(matrix, j);
            }
        }
        for (int i = 1; i < matrix.length; i++) {
            if (matrix[i][0] == 0) {
                setRowZero(matrix, i);
            }
        }
        if (setRowZero) {
            setRowZero(matrix, 0);
        }
        if (setColZero) {
            setColZero(matrix, 0);
        }
    }

    private static void setRowZero(int[][] matrics, int row) {
        for (int j = 0; j < matrics[0].length; j++) {
            matrics[row][j] = 0;
        }
    }

    private static void setColZero(int[][] matrics, int col) {
        for (int i = 0; i < matrics.length; i++) {
            matrics[i][col] = 0;
        }
    }
}
