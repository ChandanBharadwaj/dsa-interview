import java.util.Arrays;

/**
 * LeetCode 787 — Cheapest Flights Within K Stops. Bellman-Ford with bounded
 * rounds: after round r, dist holds the cheapest costs using <= r flights,
 * so run exactly K+1 rounds. Relax from a snapshot so several flights can't
 * chain within one round. Mirrors pages/17-shortest-paths.html.
 */
public class CheapestFlights {

    public static int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        final int INF = Integer.MAX_VALUE / 2;     // /2 so INF + price can't overflow int
        int[] dist = new int[n];
        Arrays.fill(dist, INF);
        dist[src] = 0;

        for (int round = 0; round <= k; round++) { // K stops => at most K+1 flights = K+1 rounds
            int[] next = dist.clone();             // relax from LAST round's snapshot only,
                                                   // so each round adds at most ONE flight
            boolean changed = false;
            for (int[] f : flights) {              // f = {from, to, price}
                if (dist[f[0]] + f[2] < next[f[1]]) {   // cheaper way into f[1] via f[0]?
                    next[f[1]] = dist[f[0]] + f[2];
                    changed = true;
                }
            }
            dist = next;                           // this round's results become the snapshot
            if (!changed) break;                   // stable early -> further rounds are no-ops
        }
        return dist[dst] >= INF ? -1 : dist[dst];  // never reached within the flight budget
    }

    public static void main(String[] args) {
        assert findCheapestPrice(4,
                new int[][]{{0,1,100},{1,2,100},{2,0,100},{1,3,600},{2,3,200}}, 0, 3, 1) == 700;
        assert findCheapestPrice(3,
                new int[][]{{0,1,100},{1,2,100},{0,2,500}}, 0, 2, 1) == 200;
        assert findCheapestPrice(3,
                new int[][]{{0,1,100},{1,2,100},{0,2,500}}, 0, 2, 0) == 500;  // no stops allowed
        System.out.println("CheapestFlights: OK");
    }
}
