public class SurroundedRegions {

    // Invert the question: an O is safe iff it connects to the BORDER.
    // Flood from every border O marking survivors, then flip the rest.
    public static void solve(char[][] board) {
        int rows = board.length, cols = board[0].length;
        for (int r = 0; r < rows; r++) {           // flood from border cells
            mark(board, r, 0);
            mark(board, r, cols - 1);
        }
        for (int c = 0; c < cols; c++) {
            mark(board, 0, c);
            mark(board, rows - 1, c);
        }
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++) {
                if (board[r][c] == 'O') board[r][c] = 'X';   // trapped: capture
                else if (board[r][c] == 'S') board[r][c] = 'O';  // safe: restore
            }
    }

    private static void mark(char[][] b, int r, int c) {
        if (r < 0 || r >= b.length || c < 0 || c >= b[0].length || b[r][c] != 'O')
            return;
        b[r][c] = 'S';                              // safe — connected to border
        mark(b, r + 1, c);
        mark(b, r - 1, c);
        mark(b, r, c + 1);
        mark(b, r, c - 1);
    }

    public static void main(String[] args) {
        char[][] b = {
                {'X', 'X', 'X', 'X'},
                {'X', 'O', 'O', 'X'},
                {'X', 'X', 'O', 'X'},
                {'X', 'O', 'X', 'X'}};
        solve(b);
        assert b[1][1] == 'X' && b[1][2] == 'X' && b[2][2] == 'X';  // captured
        assert b[3][1] == 'O';                       // touches the border: survives
        char[][] single = {{'O'}};
        solve(single);
        assert single[0][0] == 'O';
        System.out.println("SurroundedRegions: OK");
    }
}
