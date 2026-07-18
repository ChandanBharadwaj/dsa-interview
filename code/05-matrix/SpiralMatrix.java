import java.util.ArrayList;
import java.util.List;

// LeetCode 54. Spiral Matrix — four shrinking boundaries
public class SpiralMatrix {

    public static List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> out = new ArrayList<>();
        // The unvisited region is always the rectangle [top..bottom] x [left..right].
        int top = 0, bottom = matrix.length - 1;
        int left = 0, right = matrix[0].length - 1;

        while (top <= bottom && left <= right) {
            // 1) walk the top row left -> right, then retire that row
            for (int c = left; c <= right; c++) out.add(matrix[top][c]);
            top++;
            // 2) walk the right column top -> bottom, then retire that column
            for (int r = top; r <= bottom; r++) out.add(matrix[r][right]);
            right--;
            // 3) bottom row right -> left — but only if a row remains
            //    (guards against double-walking a single leftover row)
            if (top <= bottom) {
                for (int c = right; c >= left; c--) out.add(matrix[bottom][c]);
                bottom--;
            }
            // 4) left column bottom -> top — only if a column remains
            if (left <= right) {
                for (int r = bottom; r >= top; r--) out.add(matrix[r][left]);
                left++;
            }
        }
        return out;
    }

    public static void main(String[] args) {
        assert spiralOrder(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}})
                .equals(List.of(1, 2, 3, 6, 9, 8, 7, 4, 5));
        assert spiralOrder(new int[][]{{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}})
                .equals(List.of(1, 2, 3, 4, 8, 12, 11, 10, 9, 5, 6, 7));
        assert spiralOrder(new int[][]{{7}}).equals(List.of(7));           // 1x1
        assert spiralOrder(new int[][]{{1}, {2}, {3}}).equals(List.of(1, 2, 3)); // single column
        System.out.println("SpiralMatrix: OK");
    }
}
