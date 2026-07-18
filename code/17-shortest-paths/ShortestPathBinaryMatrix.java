import java.util.ArrayDeque;
import java.util.Deque;

/**
 * LeetCode 1091 — Shortest Path in Binary Matrix.
 * 8-directional movement means paths can curl back — no DP fill order exists,
 * so BFS rings (uniform edge cost) are the right tool. Mirrors pages/17-shortest-paths.html.
 */
public class ShortestPathBinaryMatrix {

    public static int shortestPathBinaryMatrix(int[][] grid) {
        int n = grid.length;
        // Path must START and END on an open cell — a classic missed edge case.
        if (grid[0][0] != 0 || grid[n - 1][n - 1] != 0) return -1;

        int[][] dirs = {{-1,-1},{-1,0},{-1,1},{0,-1},{0,1},{1,-1},{1,0},{1,1}};  // 8 ways
        int[][] dist = new int[n][n];              // 0 doubles as "not visited yet"
        Deque<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[]{0, 0});
        dist[0][0] = 1;                            // path length counts CELLS, so start at 1

        while (!queue.isEmpty()) {
            int[] cell = queue.poll();
            int r = cell[0], c = cell[1];
            if (r == n - 1 && c == n - 1) return dist[r][c];  // first arrival = shortest (BFS rings)
            for (int[] d : dirs) {
                int nr = r + d[0], nc = c + d[1];
                if (nr >= 0 && nr < n && nc >= 0 && nc < n     // stay on the board
                        && grid[nr][nc] == 0                   // open cell
                        && dist[nr][nc] == 0) {                // not yet reached
                    dist[nr][nc] = dist[r][c] + 1;   // record BEFORE enqueue -> no duplicates
                    queue.offer(new int[]{nr, nc});
                }
            }
        }
        return -1;                                 // queue drained without reaching the corner
    }

    public static void main(String[] args) {
        assert shortestPathBinaryMatrix(new int[][]{{0,1},{1,0}}) == 2;
        assert shortestPathBinaryMatrix(new int[][]{{0,0,0},{1,1,0},{1,1,0}}) == 4;
        assert shortestPathBinaryMatrix(new int[][]{{1,0,0},{1,1,0},{1,1,0}}) == -1; // blocked start
        assert shortestPathBinaryMatrix(new int[][]{{0}}) == 1;
        System.out.println("ShortestPathBinaryMatrix: OK");
    }
}
