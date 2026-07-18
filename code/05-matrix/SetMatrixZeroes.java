import java.util.Arrays;

// LeetCode 73. Set Matrix Zeroes — O(1) extra space using row 0 / column 0 as flag storage
public class SetMatrixZeroes {

    public static void setZeroes(int[][] m) {
        int rows = m.length, cols = m[0].length;

        // Row 0 and column 0 will store the flags, so first remember
        // whether THEY need zeroing themselves (before we overwrite them).
        boolean zeroRow0 = false, zeroCol0 = false;
        for (int c = 0; c < cols; c++) if (m[0][c] == 0) zeroRow0 = true;
        for (int r = 0; r < rows; r++) if (m[r][0] == 0) zeroCol0 = true;

        // Scan the interior: a zero at (r,c) marks its row head and column head.
        for (int r = 1; r < rows; r++)
            for (int c = 1; c < cols; c++)
                if (m[r][c] == 0) { m[r][0] = 0; m[0][c] = 0; }

        // Zero interior cells whose row head or column head is flagged.
        // (Interior first: the flag cells themselves must survive until read.)
        for (int r = 1; r < rows; r++)
            for (int c = 1; c < cols; c++)
                if (m[r][0] == 0 || m[0][c] == 0) m[r][c] = 0;

        // Finally settle row 0 and column 0 from the saved booleans.
        if (zeroRow0) Arrays.fill(m[0], 0);
        if (zeroCol0) for (int r = 0; r < rows; r++) m[r][0] = 0;
    }

    public static void main(String[] args) {
        int[][] a = {{1, 1, 1}, {1, 0, 1}, {1, 1, 1}};
        setZeroes(a);
        assert Arrays.deepEquals(a, new int[][]{{1, 0, 1}, {0, 0, 0}, {1, 0, 1}});
        int[][] b = {{0, 1, 2, 0}, {3, 4, 5, 2}, {1, 3, 1, 5}};
        setZeroes(b);
        assert Arrays.deepEquals(b, new int[][]{{0, 0, 0, 0}, {0, 4, 5, 0}, {0, 3, 1, 0}});
        int[][] c = {{1, 2}, {3, 4}};
        setZeroes(c);
        assert Arrays.deepEquals(c, new int[][]{{1, 2}, {3, 4}});   // no zeros: untouched
        System.out.println("SetMatrixZeroes: OK");
    }
}
