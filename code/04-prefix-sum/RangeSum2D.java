public class RangeSum2D {

    // 2D prefix sums with a padding row/column of zeros: prefix[r][c] holds
    // the sum of the rectangle from (0,0) to (r-1,c-1) inclusive.
    private final int[][] prefix;

    public RangeSum2D(int[][] matrix) {
        int rows = matrix.length, cols = matrix[0].length;
        prefix = new int[rows + 1][cols + 1];
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                // current cell + rectangle above + rectangle left
                // − their overlap (counted twice)
                prefix[r + 1][c + 1] = matrix[r][c]
                        + prefix[r][c + 1] + prefix[r + 1][c] - prefix[r][c];
    }

    // Inclusion–exclusion: big rectangle − top strip − left strip + corner.
    public int sumRegion(int r1, int c1, int r2, int c2) {
        return prefix[r2 + 1][c2 + 1] - prefix[r1][c2 + 1]
             - prefix[r2 + 1][c1] + prefix[r1][c1];
    }

    public static void main(String[] args) {
        RangeSum2D m = new RangeSum2D(new int[][]{
                {3, 0, 1, 4, 2},
                {5, 6, 3, 2, 1},
                {1, 2, 0, 1, 5},
                {4, 1, 0, 1, 7},
                {1, 0, 3, 0, 5}});
        assert m.sumRegion(2, 1, 4, 3) == 8;
        assert m.sumRegion(1, 1, 2, 2) == 11;
        assert m.sumRegion(1, 2, 2, 4) == 12;
        assert m.sumRegion(0, 0, 4, 4) == 58;   // whole matrix
        System.out.println("RangeSum2D: OK");
    }
}
