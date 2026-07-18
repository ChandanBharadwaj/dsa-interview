public class UniquePathsII {

    // Unique Paths with obstacles: an obstacle cell contributes 0 paths.
    // One rolling row suffices.
    public static int uniquePathsWithObstacles(int[][] grid) {
        int cols = grid[0].length;
        int[] dp = new int[cols];               // dp[c] = paths to (row, c)
        dp[0] = grid[0][0] == 1 ? 0 : 1;        // start may itself be blocked
        for (int[] row : grid) {
            for (int c = 0; c < cols; c++) {
                if (row[c] == 1)
                    dp[c] = 0;                  // obstacle: nothing gets through
                else if (c > 0)
                    dp[c] += dp[c - 1];         // up (old dp[c]) + left (dp[c-1])
            }
        }
        return dp[cols - 1];
    }

    public static void main(String[] args) {
        assert uniquePathsWithObstacles(new int[][]{
                {0, 0, 0}, {0, 1, 0}, {0, 0, 0}}) == 2;
        assert uniquePathsWithObstacles(new int[][]{{0, 1}, {0, 0}}) == 1;
        assert uniquePathsWithObstacles(new int[][]{{1}}) == 0;
        assert uniquePathsWithObstacles(new int[][]{{0, 0}, {0, 1}}) == 0;
        System.out.println("UniquePathsII: OK");
    }
}
