// LeetCode 200 — Number of Islands
// The grid IS a graph: each land cell is a node, edges connect
// 4-directional land neighbors. Count connected components: scan every
// cell; each time we find un-visited land, that's a NEW island — flood
// fill (DFS) sinks the whole component so we never count it again.
public class NumberOfIslands {

    public static int numIslands(char[][] grid) {
        int rows = grid.length, cols = grid[0].length;
        int islands = 0;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] == '1') { // fresh land -> a brand-new component
                    islands++;
                    sink(grid, r, c);    // erase the whole island
                }
            }
        }
        return islands;
    }

    // DFS flood fill: mark this cell as water, then recurse in 4 directions.
    // Mutating the grid doubles as our "visited" set (no extra structure).
    private static void sink(char[][] grid, int r, int c) {
        if (r < 0 || r >= grid.length || c < 0 || c >= grid[0].length
                || grid[r][c] != '1') // off the map, water, or already sunk
            return;
        grid[r][c] = '0';             // mark visited BEFORE recursing
        sink(grid, r + 1, c);         // down
        sink(grid, r - 1, c);         // up
        sink(grid, r, c + 1);         // right
        sink(grid, r, c - 1);         // left
    }

    public static void main(String[] args) {
        char[][] g1 = {
            {'1','1','1','1','0'},
            {'1','1','0','1','0'},
            {'1','1','0','0','0'},
            {'0','0','0','0','0'}
        };
        assert numIslands(g1) == 1;
        char[][] g2 = {
            {'1','1','0','0','0'},
            {'1','1','0','0','0'},
            {'0','0','1','0','0'},
            {'0','0','0','1','1'}
        };
        assert numIslands(g2) == 3;
        assert numIslands(new char[][]{{'0'}}) == 0;
        System.out.println("NumberOfIslands: OK");
    }
}
