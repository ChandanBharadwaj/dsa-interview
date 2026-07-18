import java.util.*;

public class SortDiagonally {

    // Every ↘ diagonal shares the same (row − col) value. Bucket cells by
    // that key, sort each bucket, write back along the same diagonals.
    public static int[][] diagonalSort(int[][] mat) {
        int m = mat.length, n = mat[0].length;
        Map<Integer, List<Integer>> diag = new HashMap<>();
        for (int r = 0; r < m; r++)
            for (int c = 0; c < n; c++)
                diag.computeIfAbsent(r - c, k -> new ArrayList<>()).add(mat[r][c]);

        for (List<Integer> d : diag.values())
            d.sort(Collections.reverseOrder());   // pop cheap from the tail below

        for (int r = 0; r < m; r++)
            for (int c = 0; c < n; c++) {
                List<Integer> d = diag.get(r - c);
                mat[r][c] = d.remove(d.size() - 1);   // smallest first, in order
            }
        return mat;
    }

    public static void main(String[] args) {
        int[][] got = diagonalSort(new int[][]{{3, 3, 1, 1}, {2, 2, 1, 2}, {1, 1, 1, 2}});
        assert java.util.Arrays.deepEquals(got,
                new int[][]{{1, 1, 1, 1}, {1, 2, 2, 2}, {1, 2, 3, 3}});
        assert java.util.Arrays.deepEquals(diagonalSort(new int[][]{{5}}), new int[][]{{5}});
        System.out.println("SortDiagonally: OK");
    }
}
