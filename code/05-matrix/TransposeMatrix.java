import java.util.Arrays;

/**
 * LeetCode 867 — Transpose Matrix: rows become columns.
 * Mirrors pages/05-matrix.html.
 */
public class TransposeMatrix {

    public static int[][] transpose(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        int[][] t = new int[n][m];              // dimensions swap: n rows, m columns
        for (int r = 0; r < m; r++)
            for (int c = 0; c < n; c++)
                t[c][r] = matrix[r][c];         // (r,c) lands at (c,r)
        return t;
    }

    public static void main(String[] args) {
        assert Arrays.deepEquals(
            transpose(new int[][]{{1, 2, 3}, {4, 5, 6}}),
            new int[][]{{1, 4}, {2, 5}, {3, 6}});
        assert Arrays.deepEquals(
            transpose(new int[][]{{1, 2}, {3, 4}}),
            new int[][]{{1, 3}, {2, 4}});
        System.out.println("TransposeMatrix: OK");
    }
}
