import java.util.ArrayDeque;
import java.util.Queue;

// LeetCode 994 — Rotting Oranges
// Multi-source BFS: seed the queue with ALL initially-rotten oranges
// (they all start rotting at minute 0), then expand the rot one BFS
// layer per minute. BFS layers = elapsed minutes, because BFS explores
// strictly in order of distance from the sources.
public class RottingOranges {

    public static int orangesRotting(int[][] grid) {
        int rows = grid.length, cols = grid[0].length;
        Queue<int[]> queue = new ArrayDeque<>();
        int fresh = 0;

        // Seed: every rotten orange enters the queue simultaneously.
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] == 2) queue.offer(new int[]{r, c});
                else if (grid[r][c] == 1) fresh++;
            }
        if (fresh == 0) return 0; // nothing left to rot -> 0 minutes

        int[][] dirs = {{1,0},{-1,0},{0,1},{0,-1}};
        int minutes = 0;
        // One full LAYER of the queue = one minute of spreading.
        while (!queue.isEmpty() && fresh > 0) {
            int layerSize = queue.size(); // freeze this minute's frontier
            for (int k = 0; k < layerSize; k++) {
                int[] cell = queue.poll();
                for (int[] d : dirs) {
                    int nr = cell[0] + d[0], nc = cell[1] + d[1];
                    if (nr < 0 || nr >= rows || nc < 0 || nc >= cols
                            || grid[nr][nc] != 1) continue; // only fresh oranges rot
                    grid[nr][nc] = 2;               // mark rotten NOW ("visited")
                    fresh--;                        // one fewer fresh orange
                    queue.offer(new int[]{nr, nc}); // it spreads next minute
                }
            }
            minutes++; // a whole wavefront finished = one minute passed
        }
        return fresh == 0 ? minutes : -1; // stranded fresh orange -> impossible
    }

    public static void main(String[] args) {
        assert orangesRotting(new int[][]{{2,1,1},{1,1,0},{0,1,1}}) == 4;
        assert orangesRotting(new int[][]{{2,1,1},{0,1,1},{1,0,1}}) == -1;
        assert orangesRotting(new int[][]{{0,2}}) == 0;
        System.out.println("RottingOranges: OK");
    }
}
