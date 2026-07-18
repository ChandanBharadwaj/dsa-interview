import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

/**
 * LeetCode 542 — 01 Matrix. Multi-source BFS from ALL zeros at once:
 * distance-to-nearest-zero for every cell in one pass.
 * Mirrors pages/15-graphs-basics.html.
 */
public class ZeroOneMatrix {

    public static int[][] updateMatrix(int[][] mat) {
        int m = mat.length, n = mat[0].length;
        int[][] dist = new int[m][n];
        Queue<int[]> queue = new ArrayDeque<>();

        // Seed EVERY zero at distance 0; mark unknowns with -1 ("unvisited").
        for (int r = 0; r < m; r++)
            for (int c = 0; c < n; c++) {
                if (mat[r][c] == 0) queue.offer(new int[]{r, c});
                else dist[r][c] = -1;
            }

        int[][] dirs = {{1,0},{-1,0},{0,1},{0,-1}};
        while (!queue.isEmpty()) {
            int[] cell = queue.poll();
            for (int[] d : dirs) {
                int nr = cell[0] + d[0], nc = cell[1] + d[1];
                if (nr < 0 || nr >= m || nc < 0 || nc >= n
                        || dist[nr][nc] != -1) continue;   // out of grid or settled
                // First arrival wins: BFS guarantees this is the shortest way in
                dist[nr][nc] = dist[cell[0]][cell[1]] + 1;
                queue.offer(new int[]{nr, nc});
            }
        }
        return dist;
    }

    public static void main(String[] args) {
        assert Arrays.deepEquals(
            updateMatrix(new int[][]{{0,0,0},{0,1,0},{0,0,0}}),
            new int[][]{{0,0,0},{0,1,0},{0,0,0}});
        assert Arrays.deepEquals(
            updateMatrix(new int[][]{{0,0,0},{0,1,0},{1,1,1}}),
            new int[][]{{0,0,0},{0,1,0},{1,2,1}});
        System.out.println("ZeroOneMatrix: OK");
    }
}
