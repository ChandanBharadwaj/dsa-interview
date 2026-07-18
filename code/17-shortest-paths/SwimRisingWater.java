import java.util.PriorityQueue;

/**
 * LeetCode 778 — Swim in Rising Water. Dijkstra where a path costs its
 * highest cell (the time the water must reach).
 * Mirrors pages/17-shortest-paths.html.
 */
public class SwimRisingWater {

    public static int swimInWater(int[][] grid) {
        int n = grid.length;
        boolean[][] settled = new boolean[n][n];
        // The "distance" of a path is the MAXIMUM elevation on it — the time
        // the water needs to cover every cell you cross. Minimize that max.
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]); // {time, r, c}
        pq.offer(new int[]{grid[0][0], 0, 0});         // you start swimming at t = grid[0][0]
        int[][] dirs = {{1,0},{-1,0},{0,1},{0,-1}};
        while (true) {
            int[] cur = pq.poll();
            int t = cur[0], r = cur[1], c = cur[2];
            if (settled[r][c]) continue;               // stale duplicate
            settled[r][c] = true;                      // cheapest way in is final (Dijkstra)
            if (r == n - 1 && c == n - 1) return t;    // reached the goal at the earliest time
            for (int[] d : dirs) {
                int nr = r + d[0], nc = c + d[1];
                if (nr < 0 || nr >= n || nc < 0 || nc >= n || settled[nr][nc]) continue;
                // Entering (nr,nc) needs the water at max(current time, its elevation)
                pq.offer(new int[]{Math.max(t, grid[nr][nc]), nr, nc});
            }
        }
    }

    public static void main(String[] args) {
        assert swimInWater(new int[][]{{0, 2}, {1, 3}}) == 3;
        assert swimInWater(new int[][]{
            {0, 1, 2, 3, 4},
            {24, 23, 22, 21, 5},
            {12, 13, 14, 15, 16},
            {11, 17, 18, 19, 20},
            {10, 9, 8, 7, 6}}) == 16;
        assert swimInWater(new int[][]{{3}}) == 3;
        System.out.println("SwimRisingWater: OK");
    }
}
