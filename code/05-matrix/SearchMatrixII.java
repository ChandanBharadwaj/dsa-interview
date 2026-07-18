import java.util.*;

public class SearchMatrixII {

    // Rows sorted left→right, columns sorted top→bottom. Start at the
    // TOP-RIGHT corner: each comparison eliminates a whole row or column.
    public static boolean searchMatrix(int[][] matrix, int target) {
        int r = 0, c = matrix[0].length - 1;
        while (r < matrix.length && c >= 0) {
            int v = matrix[r][c];
            if (v == target) return true;
            if (v > target) c--;        // everything below v in this column is bigger
            else r++;                   // everything left of v in this row is smaller
        }
        return false;                   // walked off the edge
    }

    public static void main(String[] args) {
        int[][] m = {
                {1, 4, 7, 11, 15},
                {2, 5, 8, 12, 19},
                {3, 6, 9, 16, 22},
                {10, 13, 14, 17, 24},
                {18, 21, 23, 26, 30}};
        assert searchMatrix(m, 5);
        assert !searchMatrix(m, 20);
        assert searchMatrix(m, 30) && searchMatrix(m, 1);
        Random rand = new Random(3);
        for (int t = 0; t < 100; t++) {          // cross-check vs linear scan
            int target = rand.nextInt(35);
            boolean found = false;
            for (int[] row : m) for (int x : row) if (x == target) found = true;
            assert searchMatrix(m, target) == found;
        }
        System.out.println("SearchMatrixII: OK");
    }
}
