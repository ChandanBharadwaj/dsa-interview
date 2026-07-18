import java.util.ArrayList;
import java.util.List;

/**
 * LeetCode 1584 — Min Cost to Connect All Points. "Connect ALL nodes at
 * minimum total cost" = minimum spanning tree, not shortest path. Kruskal:
 * sort all pairwise edges, greedily accept any edge joining two separate
 * groups (union-find from page 16). Mirrors pages/17-shortest-paths.html.
 */
public class MinCostConnectPoints {

    // Page 16's union-find, nested here so the file compiles standalone.
    static class UnionFind {
        int[] parent, rank;
        UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) parent[i] = i;
        }
        int find(int x) {
            if (parent[x] != x) parent[x] = find(parent[x]); // path compression
            return parent[x];
        }
        boolean union(int a, int b) {          // false = already connected (edge would close a cycle)
            int ra = find(a), rb = find(b);
            if (ra == rb) return false;
            if (rank[ra] < rank[rb]) { int t = ra; ra = rb; rb = t; }
            parent[rb] = ra;                   // attach shorter tree under taller (union by rank)
            if (rank[ra] == rank[rb]) rank[ra]++;
            return true;
        }
    }

    public static int minCostConnectPoints(int[][] points) {
        int n = points.length;
        // Step 1: complete graph -> all pairwise edges {manhattanCost, i, j}.
        List<int[]> edges = new ArrayList<>();
        for (int i = 0; i < n; i++)
            for (int j = i + 1; j < n; j++) {
                int cost = Math.abs(points[i][0] - points[j][0])
                         + Math.abs(points[i][1] - points[j][1]);
                edges.add(new int[]{cost, i, j});
            }

        // Step 2: Kruskal — cheapest edges first…
        edges.sort((a, b) -> a[0] - b[0]);
        UnionFind uf = new UnionFind(n);
        int total = 0, used = 0;
        for (int[] e : edges) {
            // …accept only if endpoints are still in different groups
            // (union returns false when the edge would close a cycle).
            if (uf.union(e[1], e[2])) {
                total += e[0];
                if (++used == n - 1) break;        // spanning tree complete: n-1 edges
            }
        }
        return total;
    }

    public static void main(String[] args) {
        assert minCostConnectPoints(new int[][]{{0,0},{2,2},{3,10},{5,2},{7,0}}) == 20;
        assert minCostConnectPoints(new int[][]{{3,12},{-2,5},{-4,1}}) == 18;
        assert minCostConnectPoints(new int[][]{{0,0}}) == 0;
        System.out.println("MinCostConnectPoints: OK");
    }
}
