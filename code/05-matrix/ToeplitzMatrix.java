public class ToeplitzMatrix {

    // A matrix is Toeplitz iff every cell equals its up-left neighbor —
    // checking that local rule everywhere proves every full diagonal constant.
    public static boolean isToeplitzMatrix(int[][] matrix) {
        for (int r = 1; r < matrix.length; r++)
            for (int c = 1; c < matrix[0].length; c++)
                if (matrix[r][c] != matrix[r - 1][c - 1])
                    return false;
        return true;
    }

    public static void main(String[] args) {
        assert isToeplitzMatrix(new int[][]{{1, 2, 3, 4}, {5, 1, 2, 3}, {9, 5, 1, 2}});
        assert !isToeplitzMatrix(new int[][]{{1, 2}, {2, 2}});
        assert isToeplitzMatrix(new int[][]{{7}});
        System.out.println("ToeplitzMatrix: OK");
    }
}
