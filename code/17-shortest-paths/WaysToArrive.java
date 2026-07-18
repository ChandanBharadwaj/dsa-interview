import java.util.*;

public class WaysToArrive {

    // Dijkstra + path counting: ways[v] accumulates over every edge that
    // achieves v's (final) shortest distance. Strictly better -> reset;
    // exactly equal -> add.
    private static final int MOD = 1_000_000_007;

    public static int countPaths(int n, int[][] roads) {
        List<List<long[]>> adj = new ArrayList<>();            // {neighbor, time}
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
        for (int[] r : roads) {
            adj.get(r[0]).add(new long[]{r[1], r[2]});
            adj.get(r[1]).add(new long[]{r[0], r[2]});
        }
        long[] dist = new long[n];
        Arrays.fill(dist, Long.MAX_VALUE);
        long[] ways = new long[n];
        dist[0] = 0;
        ways[0] = 1;
        PriorityQueue<long[]> heap =                           // {dist, node}
                new PriorityQueue<>((a, b) -> Long.compare(a[0], b[0]));
        heap.offer(new long[]{0, 0});
        while (!heap.isEmpty()) {
            long[] top = heap.poll();
            long d = top[0];
            int u = (int) top[1];
            if (d > dist[u]) continue;                         // stale
            for (long[] e : adj.get(u)) {
                int v = (int) e[0];
                long nd = d + e[1];
                if (nd < dist[v]) {                            // strictly better path
                    dist[v] = nd;
                    ways[v] = ways[u];                         // restart the count
                    heap.offer(new long[]{nd, v});
                } else if (nd == dist[v]) {                    // tie: another route
                    ways[v] = (ways[v] + ways[u]) % MOD;
                }
            }
        }
        return (int) ways[n - 1];
    }

    public static void main(String[] args) {
        assert countPaths(7, new int[][]{{0, 6, 7}, {0, 1, 2}, {1, 2, 3}, {1, 3, 3},
                {6, 3, 3}, {3, 5, 1}, {6, 5, 1}, {2, 5, 1}, {0, 4, 5}, {4, 6, 2}}) == 4;
        assert countPaths(2, new int[][]{{1, 0, 10}}) == 1;
        System.out.println("WaysToArrive: OK");
    }
}
