public class SudokuSolver {

    // Classic constraint backtracking: find an empty cell, try 1-9 under the
    // row/column/box rules, recurse; on dead end, erase and try the next.
    public static boolean solveSudoku(char[][] board) {
        for (int r = 0; r < 9; r++)
            for (int c = 0; c < 9; c++) {
                if (board[r][c] != '.') continue;
                for (char d = '1'; d <= '9'; d++) {
                    if (valid(board, r, c, d)) {
                        board[r][c] = d;             // choose
                        if (solveSudoku(board))      // solved downstream?
                            return true;
                        board[r][c] = '.';           // unchoose (dead end)
                    }
                }
                return false;    // empty cell with no legal digit -> backtrack
            }
        return true;             // no empty cells left: solved
    }

    private static boolean valid(char[][] b, int r, int c, char d) {
        for (int i = 0; i < 9; i++) {
            if (b[r][i] == d) return false;                      // row
            if (b[i][c] == d) return false;                      // column
            if (b[3 * (r / 3) + i / 3][3 * (c / 3) + i % 3] == d)
                return false;                                    // 3x3 box
        }
        return true;
    }

    public static void main(String[] args) {
        char[][] board = {
                "53..7....".toCharArray(), "6..195...".toCharArray(),
                ".98....6.".toCharArray(), "8...6...3".toCharArray(),
                "4..8.3..1".toCharArray(), "7...2...6".toCharArray(),
                ".6....28.".toCharArray(), "...419..5".toCharArray(),
                "....8..79".toCharArray()};
        assert solveSudoku(board);
        // fully filled and every unit valid
        for (int r = 0; r < 9; r++)
            for (int c = 0; c < 9; c++) {
                assert board[r][c] != '.';
                char d = board[r][c];
                board[r][c] = '.';
                assert valid(board, r, c, d);
                board[r][c] = d;
            }
        assert board[0][2] == '4';   // known solution spot-checks
        assert board[8][0] == '3';
        System.out.println("SudokuSolver: OK");
    }
}
