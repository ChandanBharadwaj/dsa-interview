/**
 * LeetCode 695 — Max Area of Island: flood fill that returns the size it
 * consumed. Mirrors pages/15-graphs-basics.html.
 */
public class MaxAreaIsland {

    public static int maxAreaOfIsland(int[][] grid) {
        int best = 0;
        for (int r = 0; r < grid.length; r++)
            for (int c = 0; c < grid[0].length; c++)
                if (grid[r][c] == 1)
                    best = Math.max(best, area(grid, r, c));
        return best;
    }

    // Like sink(), but each call RETURNS the size of what it consumed:
    // this cell (1) plus everything reachable in the four directions.
    private static int area(int[][] g, int r, int c) {
        if (r < 0 || r >= g.length || c < 0 || c >= g[0].length || g[r][c] != 1)
            return 0;                          // contributes nothing
        g[r][c] = 0;                           // consume the cell
        return 1 + area(g, r + 1, c) + area(g, r - 1, c)
                 + area(g, r, c + 1) + area(g, r, c - 1);
    }

    public static void main(String[] args) {
        int[][] g = {
            {0,0,1,0,0,0,0,1,0,0,0,0,0},
            {0,0,0,0,0,0,0,1,1,1,0,0,0},
            {0,1,1,0,1,0,0,0,0,0,0,0,0},
            {0,1,0,0,1,1,0,0,1,0,1,0,0},
            {0,1,0,0,1,1,0,0,1,1,1,0,0},
            {0,0,0,0,0,0,0,0,0,0,1,0,0},
            {0,0,0,0,0,0,0,1,1,1,0,0,0},
            {0,0,0,0,0,0,0,1,1,0,0,0,0}
        };
        assert maxAreaOfIsland(g) == 6;
        assert maxAreaOfIsland(new int[][]{{0, 0, 0}}) == 0;
        System.out.println("MaxAreaIsland: OK");
    }
}
