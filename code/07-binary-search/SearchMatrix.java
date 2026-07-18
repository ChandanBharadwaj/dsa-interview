public class SearchMatrix {

    // Rows sorted AND each row starts after the previous ends -> the matrix
    // is one sorted list in disguise. Binary search on the flat index,
    // translating k -> (k / cols, k % cols) on the fly.
    public static boolean searchMatrix(int[][] matrix, int target) {
        int rows = matrix.length, cols = matrix[0].length;
        int lo = 0, hi = rows * cols - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            int v = matrix[mid / cols][mid % cols];   // flat index -> 2D
            if (v == target) return true;
            if (v < target) lo = mid + 1;
            else hi = mid - 1;
        }
        return false;
    }

    public static void main(String[] args) {
        int[][] m = {{1, 3, 5, 7}, {10, 11, 16, 20}, {23, 30, 34, 60}};
        assert searchMatrix(m, 3);
        assert !searchMatrix(m, 13);
        assert searchMatrix(m, 1) && searchMatrix(m, 60);
        assert !searchMatrix(m, 0) && !searchMatrix(m, 61);
        System.out.println("SearchMatrix: OK");
    }
}
