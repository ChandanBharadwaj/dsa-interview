public class IslandPerimeter {

    // Count contributions: every land cell starts with 4 sides; each pair of
    // touching land cells hides 2 sides (one from each). No traversal needed.
    public static int islandPerimeter(int[][] grid) {
        int perimeter = 0;
        for (int r = 0; r < grid.length; r++)
            for (int c = 0; c < grid[0].length; c++) {
                if (grid[r][c] == 0) continue;
                perimeter += 4;
                if (r > 0 && grid[r - 1][c] == 1) perimeter -= 2;  // shared top edge
                if (c > 0 && grid[r][c - 1] == 1) perimeter -= 2;  // shared left edge
            }
        return perimeter;
    }

    public static void main(String[] args) {
        assert islandPerimeter(new int[][]{
                {0, 1, 0, 0}, {1, 1, 1, 0}, {0, 1, 0, 0}, {1, 1, 0, 0}}) == 16;
        assert islandPerimeter(new int[][]{{1}}) == 4;
        assert islandPerimeter(new int[][]{{1, 1}}) == 6;
        System.out.println("IslandPerimeter: OK");
    }
}
