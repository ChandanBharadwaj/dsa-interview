import java.util.HashSet;
import java.util.Set;

/**
 * LeetCode 36 — Valid Sudoku. One pass with per-row/column/box seen-sets;
 * the box index formula is (r/3)*3 + c/3.
 * Mirrors pages/05-matrix.html.
 */
public class ValidSudoku {

    @SuppressWarnings("unchecked")
    public static boolean isValidSudoku(char[][] board) {
        // seen sets: one per row, per column, per 3x3 box
        Set<Character>[] rows = new HashSet[9];
        Set<Character>[] cols = new HashSet[9];
        Set<Character>[] boxes = new HashSet[9];
        for (int i = 0; i < 9; i++) {
            rows[i] = new HashSet<>();
            cols[i] = new HashSet<>();
            boxes[i] = new HashSet<>();
        }

        for (int r = 0; r < 9; r++)
            for (int c = 0; c < 9; c++) {
                char d = board[r][c];
                if (d == '.') continue;              // empty cells impose nothing
                // THE index formula: which 3x3 box does (r,c) belong to?
                // r/3 picks the band (0..2), c/3 the stack (0..2).
                int box = (r / 3) * 3 + (c / 3);
                // add() returns false on a repeat -> immediate conflict
                if (!rows[r].add(d) || !cols[c].add(d) || !boxes[box].add(d))
                    return false;
            }
        return true;
    }

    public static void main(String[] args) {
        char[][] valid = {
            {'5','3','.','.','7','.','.','.','.'},
            {'6','.','.','1','9','5','.','.','.'},
            {'.','9','8','.','.','.','.','6','.'},
            {'8','.','.','.','6','.','.','.','3'},
            {'4','.','.','8','.','3','.','.','1'},
            {'7','.','.','.','2','.','.','.','6'},
            {'.','6','.','.','.','.','2','8','.'},
            {'.','.','.','4','1','9','.','.','5'},
            {'.','.','.','.','8','.','.','7','9'}
        };
        assert isValidSudoku(valid);
        valid[0][0] = '8';               // now column 0 and box 0 both have two 8s
        assert !isValidSudoku(valid);
        System.out.println("ValidSudoku: OK");
    }
}
