import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * LeetCode 1631 — Path With Minimum Effort. Dijkstra where a path's cost
 * is the MAX step difference instead of the sum.
 * Mirrors pages/17-shortest-paths.html.
 */
public class PathMinEffort {

    public static int minimumEffortPath(int[][] heights) {
        int m = heights.length, n = heights[0].length;
        int[][] effort = new int[m][n];
        for (int[] row : effort) Arrays.fill(row, Integer.MAX_VALUE);
        effort[0][0] = 0;
        // Same skeleton as Network Delay Time — only the relaxation changes:
        // a path's cost is the MAX step seen, not the SUM of steps.
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]); // {effort, r, c}
        pq.offer(new int[]{0, 0, 0});
        int[][] dirs = {{1,0},{-1,0},{0,1},{0,-1}};
        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int e = cur[0], r = cur[1], c = cur[2];
            if (r == m - 1 && c == n - 1) return e;   // settled the target: done
            if (e > effort[r][c]) continue;            // lazy deletion (stale entry)
            for (int[] d : dirs) {
                int nr = r + d[0], nc = c + d[1];
                if (nr < 0 || nr >= m || nc < 0 || nc >= n) continue;
                // New route's effort: the worse of (route so far, this step)
                int ne = Math.max(e, Math.abs(heights[nr][nc] - heights[r][c]));
                if (ne < effort[nr][nc]) {
                    effort[nr][nc] = ne;
                    pq.offer(new int[]{ne, nr, nc});
                }
            }
        }
        return -1;                                     // unreachable (never for valid grids)
    }

    public static void main(String[] args) {
        assert minimumEffortPath(new int[][]{{1,2,2},{3,8,2},{5,3,5}}) == 2;
        assert minimumEffortPath(new int[][]{{1,2,3},{3,8,4},{5,3,5}}) == 1;
        assert minimumEffortPath(new int[][]{
            {1,2,1,1,1},{1,2,1,2,1},{1,2,1,2,1},{1,2,1,2,1},{1,1,1,2,1}}) == 0;
        assert minimumEffortPath(new int[][]{{7}}) == 0;
        System.out.println("PathMinEffort: OK");
    }
}
