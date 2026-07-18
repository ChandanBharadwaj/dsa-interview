import java.util.Arrays;

/**
 * LeetCode 289 — Game of Life. Two variations: snapshot copy O(mn) space
 * vs two-bits-per-cell in-place O(1) space.
 * Mirrors pages/05-matrix.html.
 */
public class GameOfLife {

    public static void gameOfLifeCopy(int[][] board) {
        int m = board.length, n = board[0].length;
        int[][] old = new int[m][n];
        for (int r = 0; r < m; r++) old[r] = board[r].clone();  // frozen snapshot

        for (int r = 0; r < m; r++)
            for (int c = 0; c < n; c++) {
                int live = countLive(old, r, c);       // read ONLY the snapshot
                board[r][c] = (old[r][c] == 1)
                    ? ((live == 2 || live == 3) ? 1 : 0)   // survival rule
                    : ((live == 3) ? 1 : 0);               // birth rule
            }
    }

    private static int countLive(int[][] b, int r, int c) {
        int live = 0;
        for (int dr = -1; dr <= 1; dr++)
            for (int dc = -1; dc <= 1; dc++) {
                if (dr == 0 && dc == 0) continue;
                int nr = r + dr, nc = c + dc;
                if (nr >= 0 && nr < b.length && nc >= 0 && nc < b[0].length)
                    live += b[nr][nc] & 1;
            }
        return live;
    }

    public static void gameOfLife(int[][] board) {
        int m = board.length, n = board[0].length;
        // Encode BOTH generations in one int:
        //   bit 0 = current state, bit 1 = next state.
        // Neighbors read the current state with (value & 1), which stays
        // intact until the final pass shifts everything right.
        for (int r = 0; r < m; r++)
            for (int c = 0; c < n; c++) {
                int live = countLive(board, r, c);
                int cur = board[r][c] & 1;
                if ((cur == 1 && (live == 2 || live == 3)) || (cur == 0 && live == 3))
                    board[r][c] |= 2;                      // set bit 1: alive next gen
            }
        // Commit: next generation becomes the current one
        for (int r = 0; r < m; r++)
            for (int c = 0; c < n; c++)
                board[r][c] >>= 1;
    }

    public static void main(String[] args) {
        int[][] a = {{0,1,0},{0,0,1},{1,1,1},{0,0,0}};
        gameOfLife(a);
        assert Arrays.deepEquals(a, new int[][]{{0,0,0},{1,0,1},{0,1,1},{0,1,0}});
        int[][] b = {{1,1},{1,0}};
        gameOfLife(b);
        assert Arrays.deepEquals(b, new int[][]{{1,1},{1,1}});
        int[][] c = {{0,1,0},{0,0,1},{1,1,1},{0,0,0}};
        gameOfLifeCopy(c);
        assert Arrays.deepEquals(c, new int[][]{{0,0,0},{1,0,1},{0,1,1},{0,1,0}});
        System.out.println("GameOfLife: OK");
    }
}
