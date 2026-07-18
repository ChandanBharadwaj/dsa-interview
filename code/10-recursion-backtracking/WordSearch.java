/**
 * LeetCode 79 — Word Search. Grid DFS with mark-and-restore (the board
 * itself is the visited set). Mirrors pages/10-recursion-backtracking.html.
 */
public class WordSearch {

    public static boolean exist(char[][] board, String word) {
        for (int r = 0; r < board.length; r++)
            for (int c = 0; c < board[0].length; c++)
                if (dfs(board, word, r, c, 0)) return true;   // try every starting cell
        return false;
    }

    private static boolean dfs(char[][] b, String w, int r, int c, int i) {
        if (i == w.length()) return true;        // matched every character
        if (r < 0 || r >= b.length || c < 0 || c >= b[0].length
                || b[r][c] != w.charAt(i))       // off-grid, wrong letter, or visited
            return false;

        char saved = b[r][c];
        b[r][c] = '#';                           // choose: mark visited IN the board itself
        boolean found = dfs(b, w, r + 1, c, i + 1)   // explore all four directions;
                     || dfs(b, w, r - 1, c, i + 1)   // || short-circuits on success
                     || dfs(b, w, r, c + 1, i + 1)
                     || dfs(b, w, r, c - 1, i + 1);
        b[r][c] = saved;                         // un-choose: restore for other paths
        return found;
    }

    public static void main(String[] args) {
        char[][] board = {
            {'A', 'B', 'C', 'E'},
            {'S', 'F', 'C', 'S'},
            {'A', 'D', 'E', 'E'}
        };
        assert exist(board, "ABCCED");
        assert exist(board, "SEE");
        assert !exist(board, "ABCB");            // may not reuse the B
        assert board[0][0] == 'A';               // board restored intact
        System.out.println("WordSearch: OK");
    }
}
