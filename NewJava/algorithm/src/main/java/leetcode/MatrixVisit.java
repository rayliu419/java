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

    public static void main(String[] args) {
        int[][] matrix = new int[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        List<Integer> result = spiralOrder(matrix);
    }
}
