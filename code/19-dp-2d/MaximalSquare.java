public class MaximalSquare {

    // dp[r][c] = side of the largest all-1s square whose BOTTOM-RIGHT corner
    // is (r,c). A square extends iff its three neighbors (up, left, up-left)
    // support it: side = 1 + min of the three.
    public static int maximalSquare(char[][] matrix) {
        int rows = matrix.length, cols = matrix[0].length;
        int[][] dp = new int[rows + 1][cols + 1];   // padded: row/col 0 are 0
        int best = 0;
        for (int r = 1; r <= rows; r++)
            for (int c = 1; c <= cols; c++)
                if (matrix[r - 1][c - 1] == '1') {
                    dp[r][c] = 1 + Math.min(dp[r - 1][c - 1],
                               Math.min(dp[r - 1][c], dp[r][c - 1]));
                    best = Math.max(best, dp[r][c]);
                }
        return best * best;                          // area, not side
    }

    public static void main(String[] args) {
        assert maximalSquare(new char[][]{
                {'1', '0', '1', '0', '0'},
                {'1', '0', '1', '1', '1'},
                {'1', '1', '1', '1', '1'},
                {'1', '0', '0', '1', '0'}}) == 4;
        assert maximalSquare(new char[][]{{'0', '1'}, {'1', '0'}}) == 1;
        assert maximalSquare(new char[][]{{'0'}}) == 0;
        assert maximalSquare(new char[][]{{'1', '1'}, {'1', '1'}}) == 4;
        System.out.println("MaximalSquare: OK");
    }
}
