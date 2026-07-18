public class LongestIncreasingPath {

    // "Strictly increasing" means no cycles are possible — the grid is a DAG,
    // so memoized DFS is DP in topological order without computing the order.
    private static final int[][] DIRS = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    public static int longestIncreasingPath(int[][] matrix) {
        int rows = matrix.length, cols = matrix[0].length;
        int[][] memo = new int[rows][cols];     // 0 = not computed yet
        int best = 0;
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                best = Math.max(best, dfs(matrix, r, c, memo));
        return best;
    }

    private static int dfs(int[][] m, int r, int c, int[][] memo) {
        if (memo[r][c] != 0) return memo[r][c];
        int best = 1;                           // the cell alone
        for (int[] d : DIRS) {
            int nr = r + d[0], nc = c + d[1];
            if (nr >= 0 && nr < m.length && nc >= 0 && nc < m[0].length
                    && m[nr][nc] > m[r][c])     // strictly uphill only
                best = Math.max(best, 1 + dfs(m, nr, nc, memo));
        }
        memo[r][c] = best;
        return best;
    }

    public static void main(String[] args) {
        assert longestIncreasingPath(new int[][]{{9, 9, 4}, {6, 6, 8}, {2, 1, 1}}) == 4;
        assert longestIncreasingPath(new int[][]{{3, 4, 5}, {3, 2, 6}, {2, 2, 1}}) == 4;
        assert longestIncreasingPath(new int[][]{{1}}) == 1;
        assert longestIncreasingPath(new int[][]{{1, 2, 3, 4, 5}}) == 5;
        System.out.println("LongestIncreasingPath: OK");
    }
}
