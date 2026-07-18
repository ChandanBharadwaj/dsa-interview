public class SpiralMatrixII {

    // Spiral Matrix in reverse: same four shrinking boundaries, but we WRITE
    // 1..n² instead of reading.
    public static int[][] generateMatrix(int n) {
        int[][] out = new int[n][n];
        int top = 0, bottom = n - 1, left = 0, right = n - 1;
        int v = 1;
        while (v <= n * n) {
            for (int c = left; c <= right; c++) out[top][c] = v++;      // → along top
            top++;
            for (int r = top; r <= bottom; r++) out[r][right] = v++;    // ↓ down right
            right--;
            for (int c = right; c >= left; c--) out[bottom][c] = v++;   // ← along bottom
            bottom--;
            for (int r = bottom; r >= top; r--) out[r][left] = v++;     // ↑ up left
            left++;
        }
        return out;
    }

    public static void main(String[] args) {
        assert java.util.Arrays.deepEquals(generateMatrix(3),
                new int[][]{{1, 2, 3}, {8, 9, 4}, {7, 6, 5}});
        assert java.util.Arrays.deepEquals(generateMatrix(1), new int[][]{{1}});
        int[][] g = generateMatrix(4);
        assert g[0][0] == 1 && g[1][1] == 13 && g[3][0] == 10 && g[2][1] == 16;
        System.out.println("SpiralMatrixII: OK");
    }
}
