import java.util.Arrays;

/**
 * LeetCode 684 — Redundant Connection. Union-find cycle detection:
 * the first edge whose endpoints already share a root closes the cycle.
 * Mirrors pages/16-graphs-advanced.html.
 */
public class RedundantConnection {

    public static int[] findRedundantConnection(int[][] edges) {
        UnionFind uf = new UnionFind(edges.length + 1);  // nodes are labeled 1..n; index 0 unused
        for (int[] e : edges) {
            // union returns false when both endpoints already share a root —
            // adding this edge would create the cycle, so it's the redundant one.
            if (!uf.union(e[0], e[1])) return e;
        }
        return new int[0];                               // unreachable for valid input
    }

    // Union-find with path compression + union by size (template from the page).
    static class UnionFind {
        int[] parent;   // parent[x] = x's parent; a root points to itself
        int[] size;     // size[root] = number of nodes in that tree (for union by size)
        int count;      // how many disjoint sets remain

        UnionFind(int n) {
            parent = new int[n];
            size = new int[n];
            count = n;
            for (int i = 0; i < n; i++) { parent[i] = i; size[i] = 1; }
        }

        int find(int x) {
            while (parent[x] != x) {
                parent[x] = parent[parent[x]];  // path compression: skip to grandparent
                x = parent[x];
            }
            return x;
        }

        boolean union(int a, int b) {
            int ra = find(a), rb = find(b);
            if (ra == rb) return false;         // already together (this edge would close a cycle)
            if (size[ra] < size[rb]) { int t = ra; ra = rb; rb = t; }  // ra = bigger tree
            parent[rb] = ra;                    // smaller root hangs under bigger root
            size[ra] += size[rb];
            count--;                            // two sets became one
            return true;
        }
    }

    public static void main(String[] args) {
        assert Arrays.equals(
                findRedundantConnection(new int[][]{{1, 2}, {1, 3}, {2, 3}}),
                new int[]{2, 3});
        assert Arrays.equals(
                findRedundantConnection(new int[][]{{1, 2}, {2, 3}, {3, 4}, {1, 4}, {1, 5}}),
                new int[]{1, 4});
        System.out.println("RedundantConnection: OK");
    }
}
