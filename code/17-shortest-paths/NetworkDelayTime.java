import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

/**
 * LeetCode 743 — Network Delay Time. Dijkstra with lazy deletion:
 * settle the closest unsettled node, relax its out-edges, skip stale
 * priority-queue entries. Mirrors pages/17-shortest-paths.html.
 */
public class NetworkDelayTime {

    public static int networkDelayTime(int[][] times, int n, int k) {
        // Step 1: adjacency list (nodes are 1..n, so size n+1 and ignore index 0).
        List<List<int[]>> adj = new ArrayList<>();
        for (int i = 0; i <= n; i++) adj.add(new ArrayList<>());
        for (int[] t : times) adj.get(t[0]).add(new int[]{t[1], t[2]});   // u -> {v, w}

        // Step 2: Dijkstra from k.
        int[] dist = new int[n + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[k] = 0;
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);  // {dist, node}
        pq.offer(new int[]{0, k});
        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int d = cur[0], u = cur[1];
            if (d > dist[u]) continue;             // lazy deletion: stale entry, skip it
            for (int[] e : adj.get(u)) {           // u settled -> relax each edge u -> v
                int v = e[0], nd = d + e[1];
                if (nd < dist[v]) {                // cheaper route into v found
                    dist[v] = nd;
                    pq.offer(new int[]{nd, v});    // add new entry; old one dies at the guard
                }
            }
        }

        // Step 3: the signal has arrived everywhere when the FARTHEST node gets it.
        int ans = 0;
        for (int i = 1; i <= n; i++) ans = Math.max(ans, dist[i]);
        return ans == Integer.MAX_VALUE ? -1 : ans;   // some node unreachable -> -1
    }

    public static void main(String[] args) {
        assert networkDelayTime(new int[][]{{2,1,1},{2,3,1},{3,4,1}}, 4, 2) == 2;
        assert networkDelayTime(new int[][]{{1,2,1}}, 2, 1) == 1;
        assert networkDelayTime(new int[][]{{1,2,1}}, 2, 2) == -1;  // node 1 unreachable
        System.out.println("NetworkDelayTime: OK");
    }
}
