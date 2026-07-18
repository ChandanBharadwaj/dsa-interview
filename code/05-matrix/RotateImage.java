import java.util.Arrays;

// LeetCode 48. Rotate Image — 90 degrees clockwise, in place: transpose, then reverse each row
public class RotateImage {

    public static void rotate(int[][] m) {
        int n = m.length;
        // Step 1: transpose — mirror across the main diagonal (swap m[r][c] with m[c][r]).
        // Only touch the upper triangle (c > r) or every pair would swap twice (a no-op).
        for (int r = 0; r < n; r++) {
            for (int c = r + 1; c < n; c++) {
                int tmp = m[r][c];
                m[r][c] = m[c][r];
                m[c][r] = tmp;
            }
        }
        // Step 2: reverse each row — the transpose turned rows into columns;
        // flipping left/right then lands every cell at its rotated position.
        for (int[] row : m) {
            for (int i = 0, j = n - 1; i < j; i++, j--) {
                int tmp = row[i];
                row[i] = row[j];
                row[j] = tmp;
            }
        }
    }

    public static void main(String[] args) {
        int[][] a = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        rotate(a);
        assert Arrays.deepEquals(a, new int[][]{{7, 4, 1}, {8, 5, 2}, {9, 6, 3}});
        int[][] b = {{1, 2}, {3, 4}};
        rotate(b);
        assert Arrays.deepEquals(b, new int[][]{{3, 1}, {4, 2}});
        int[][] c = {{42}};
        rotate(c);
        assert Arrays.deepEquals(c, new int[][]{{42}});
        System.out.println("RotateImage: OK");
    }
}
