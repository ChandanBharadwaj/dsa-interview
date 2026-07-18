public class MinPathSum {

    // dp[r][c] = cheapest total to reach cell (r,c) moving only right/down.
    // Movement is restricted -> the grid is a DAG -> plain DP works (no BFS/Dijkstra needed).
    public static int minPathSum(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        int[][] dp = new int[m][n];
        dp[0][0] = grid[0][0];                                 // start cell: no choice, pay its cost
        for (int c = 1; c < n; c++)                            // top row: can only arrive from the left
            dp[0][c] = dp[0][c - 1] + grid[0][c];
        for (int r = 1; r < m; r++)                            // left column: can only arrive from above
            dp[r][0] = dp[r - 1][0] + grid[r][0];
        for (int r = 1; r < m; r++) {
            for (int c = 1; c < n; c++) {
                // Two possible predecessors; take the cheaper one, then pay this cell's cost.
                dp[r][c] = grid[r][c] + Math.min(dp[r - 1][c], dp[r][c - 1]);
            }
        }
        return dp[m - 1][n - 1];                               // cheapest path into the bottom-right
    }

    public static void main(String[] args) {
        assert minPathSum(new int[][]{{1, 3, 1}, {1, 5, 1}, {4, 2, 1}}) == 7;   // 1->3->1->1->1
        assert minPathSum(new int[][]{{1, 2, 3}, {4, 5, 6}}) == 12;             // 1->2->3->6
        assert minPathSum(new int[][]{{5}}) == 5;
        System.out.println("MinPathSum: OK");
    }
}
