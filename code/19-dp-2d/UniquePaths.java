public class UniquePaths {

    // dp[c] (rolling 1D row) = number of paths from the top-left to the current cell.
    // Full 2D recurrence: paths(r,c) = paths(r-1,c) + paths(r,c-1)  — arrive from above or from the left.
    public static int uniquePaths(int m, int n) {
        int[] dp = new int[n];                 // dp holds ONE row of the m x n table
        java.util.Arrays.fill(dp, 1);          // top row: only one way to any cell (keep going right)
        for (int r = 1; r < m; r++) {          // build each following row from the previous one
            for (int c = 1; c < n; c++) {
                // dp[c] currently holds "paths from above" (previous row, same column);
                // dp[c-1] already holds "paths from the left" (current row, previous column).
                dp[c] = dp[c] + dp[c - 1];
            }
            // dp[0] stays 1: the left column has exactly one path (straight down)
        }
        return dp[n - 1];                      // bottom-right cell
    }

    public static void main(String[] args) {
        assert uniquePaths(3, 7) == 28;
        assert uniquePaths(3, 2) == 3;
        assert uniquePaths(1, 1) == 1;
        assert uniquePaths(3, 3) == 6;
        System.out.println("UniquePaths: OK");
    }
}
