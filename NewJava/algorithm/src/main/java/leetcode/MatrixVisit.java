package leetcode;

public class MatrixVisit {

    public void visit() {
        int m[][] = new int[][]{
                {1, 2, 3},
                {4, 5, 6}
        };

        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++) {
                System.out.println(m[i][j]);
            }
        }
    }
}
