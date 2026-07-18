import java.util.ArrayDeque;
import java.util.Deque;

/**
 * LeetCode 785 — Is Graph Bipartite? 2-coloring by BFS; fails exactly on
 * an odd cycle. Mirrors pages/16-graphs-advanced.html.
 */
public class IsBipartite {

    public static boolean isBipartite(int[][] graph) {
        int n = graph.length;
        int[] color = new int[n];               // 0 = uncolored, 1 / -1 = the two groups
        Deque<Integer> queue = new ArrayDeque<>();

        for (int start = 0; start < n; start++) {   // graph may be disconnected
            if (color[start] != 0) continue;
            color[start] = 1;                   // arbitrary first color
            queue.offer(start);
            while (!queue.isEmpty()) {
                int u = queue.poll();
                for (int v : graph[u]) {
                    if (color[v] == 0) {
                        color[v] = -color[u];   // neighbor takes the OPPOSITE color
                        queue.offer(v);
                    } else if (color[v] == color[u]) {
                        return false;           // same-colored neighbors: odd cycle
                    }
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        assert isBipartite(new int[][]{{1, 3}, {0, 2}, {1, 3}, {0, 2}});      // 4-cycle
        assert !isBipartite(new int[][]{{1, 2, 3}, {0, 2}, {0, 1, 3}, {0, 2}}); // triangle
        assert isBipartite(new int[][]{{}, {}, {}});                          // no edges
        System.out.println("IsBipartite: OK");
    }
}
