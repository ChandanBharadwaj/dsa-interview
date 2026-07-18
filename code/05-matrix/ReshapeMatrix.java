public class ReshapeMatrix {

    // Both shapes share one linear order: element k lives at (k/cols, k%cols).
    // Walk k once and translate coordinates for each shape.
    public static int[][] matrixReshape(int[][] mat, int r, int c) {
        int m = mat.length, n = mat[0].length;
        if (m * n != r * c) return mat;         // element counts must match

        int[][] out = new int[r][c];
        for (int k = 0; k < m * n; k++)
            out[k / c][k % c] = mat[k / n][k % n];   // same k, two shapes
        return out;
    }

    public static void main(String[] args) {
        int[][] a = {{1, 2}, {3, 4}};
        assert java.util.Arrays.deepEquals(matrixReshape(a, 1, 4),
                new int[][]{{1, 2, 3, 4}});
        assert java.util.Arrays.deepEquals(matrixReshape(a, 4, 1),
                new int[][]{{1}, {2}, {3}, {4}});
        assert matrixReshape(a, 2, 4) == a;      // impossible -> original returned
        System.out.println("ReshapeMatrix: OK");
    }
}
