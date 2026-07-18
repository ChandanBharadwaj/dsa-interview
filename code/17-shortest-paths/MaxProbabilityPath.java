import java.util.*;

public class MaxProbabilityPath {

    // Dijkstra flipped: MAXIMIZE a product of probabilities instead of
    // minimizing a sum. Works because multiplying by values <= 1 never
    // increases the score — the same "no improvement later" property that
    // non-negative weights give the classic version.
    public static double maxProbability(int n, int[][] edges, double[] succProb,
                                        int start, int end) {
        List<List<double[]>> adj = new ArrayList<>();          // {neighbor, prob}
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
        for (int i = 0; i < edges.length; i++) {
            adj.get(edges[i][0]).add(new double[]{edges[i][1], succProb[i]});
            adj.get(edges[i][1]).add(new double[]{edges[i][0], succProb[i]});
        }
        double[] best = new double[n];
        best[start] = 1.0;                                     // certain to be here
        PriorityQueue<double[]> heap =                         // {prob, node}, max first
                new PriorityQueue<>((a, b) -> Double.compare(b[0], a[0]));
        heap.offer(new double[]{1.0, start});
        while (!heap.isEmpty()) {
            double[] top = heap.poll();
            double p = top[0];
            int u = (int) top[1];
            if (u == end) return p;                            // settled: optimal
            if (p < best[u]) continue;                         // stale entry
            for (double[] e : adj.get(u)) {
                int v = (int) e[0];
                double np = p * e[1];
                if (np > best[v]) {
                    best[v] = np;
                    heap.offer(new double[]{np, v});
                }
            }
        }
        return 0.0;
    }

    public static void main(String[] args) {
        assert maxProbability(3, new int[][]{{0, 1}, {1, 2}, {0, 2}},
                new double[]{0.5, 0.5, 0.2}, 0, 2) == 0.25;
        assert maxProbability(3, new int[][]{{0, 1}, {1, 2}, {0, 2}},
                new double[]{0.5, 0.5, 0.3}, 0, 2) == 0.3;
        assert maxProbability(3, new int[][]{{0, 1}}, new double[]{0.5}, 0, 2) == 0.0;
        System.out.println("MaxProbabilityPath: OK");
    }
}
