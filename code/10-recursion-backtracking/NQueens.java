import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * LeetCode 51 — N-Queens. One queen per row; O(1) conflict checks via
 * three sets (columns, r-c diagonals, r+c diagonals).
 * Mirrors pages/10-recursion-backtracking.html.
 */
public class NQueens {

    public static List<List<String>> solveNQueens(int n) {
        List<List<String>> res = new ArrayList<>();
        // The three ways queens attack, each reduced to one number:
        Set<Integer> cols = new HashSet<>();     // column index
        Set<Integer> diag1 = new HashSet<>();    // r - c is constant on one diagonal family
        Set<Integer> diag2 = new HashSet<>();    // r + c is constant on the other
        int[] queenCol = new int[n];              // queenCol[r] = column of row r's queen
        place(0, n, cols, diag1, diag2, queenCol, res);
        return res;
    }

    private static void place(int r, int n, Set<Integer> cols, Set<Integer> d1,
                              Set<Integer> d2, int[] queenCol, List<List<String>> res) {
        if (r == n) {                             // a queen safely placed in every row
            res.add(render(queenCol, n));
            return;
        }
        for (int c = 0; c < n; c++) {
            // O(1) conflict check thanks to the three sets
            if (cols.contains(c) || d1.contains(r - c) || d2.contains(r + c)) continue;
            cols.add(c); d1.add(r - c); d2.add(r + c);   // choose (all three!)
            queenCol[r] = c;
            place(r + 1, n, cols, d1, d2, queenCol, res); // explore the next row
            cols.remove(c); d1.remove(r - c); d2.remove(r + c);  // un-choose symmetrically
        }
    }

    private static List<String> render(int[] queenCol, int n) {
        List<String> board = new ArrayList<>();
        for (int r = 0; r < n; r++) {
            char[] row = new char[n];
            Arrays.fill(row, '.');
            row[queenCol[r]] = 'Q';
            board.add(new String(row));
        }
        return board;
    }

    public static void main(String[] args) {
        assert solveNQueens(4).size() == 2;
        assert solveNQueens(1).equals(List.of(List.of("Q")));
        assert solveNQueens(3).isEmpty();        // no solution exists for n=3
        assert solveNQueens(8).size() == 92;     // the classic count
        System.out.println("NQueens: OK");
    }
}
