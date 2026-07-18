/**
 * LeetCode 547 — Number of Provinces. Count connected components with
 * union-find: no traversal, just union every directly-connected pair.
 * Mirrors pages/16-graphs-advanced.html.
 */
public class NumberOfProvinces {

    public static int findCircleNum(int[][] isConnected) {
        int n = isConnected.length;
        UnionFind uf = new UnionFind(n);         // n cities start as n separate provinces
        for (int i = 0; i < n; i++)
            for (int j = i + 1; j < n; j++)      // upper triangle only: matrix is symmetric
                if (isConnected[i][j] == 1)
                    uf.union(i, j);              // direct link -> same province; count-- inside
        return uf.count;                         // survivors = number of disjoint sets
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
        assert findCircleNum(new int[][]{{1, 1, 0}, {1, 1, 0}, {0, 0, 1}}) == 2;
        assert findCircleNum(new int[][]{{1, 0, 0}, {0, 1, 0}, {0, 0, 1}}) == 3;
        assert findCircleNum(new int[][]{{1, 1}, {1, 1}}) == 1;
        System.out.println("NumberOfProvinces: OK");
    }
}
