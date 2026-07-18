public class NetworkConnected {

    // Union-find: count components and spare (redundant) cables. Connecting
    // k components needs k-1 moves, possible iff you have k-1 spares.
    private static int[] parent;

    public static int makeConnected(int n, int[][] connections) {
        if (connections.length < n - 1) return -1;   // not enough cables at all

        parent = new int[n];
        for (int i = 0; i < n; i++) parent[i] = i;
        int components = n;
        for (int[] c : connections)
            if (union(c[0], c[1]))
                components--;                        // useful cable merged two
        return components - 1;                       // moves = merges still needed

    }

    private static int find(int x) {
        while (parent[x] != x) {
            parent[x] = parent[parent[x]];           // path halving
            x = parent[x];
        }
        return x;
    }

    private static boolean union(int a, int b) {
        int ra = find(a), rb = find(b);
        if (ra == rb) return false;                  // redundant cable
        parent[ra] = rb;
        return true;
    }

    public static void main(String[] args) {
        assert makeConnected(4, new int[][]{{0, 1}, {0, 2}, {1, 2}}) == 1;
        assert makeConnected(6, new int[][]{{0, 1}, {0, 2}, {0, 3}, {1, 2}, {1, 3}}) == 2;
        assert makeConnected(6, new int[][]{{0, 1}, {0, 2}, {0, 3}, {1, 2}}) == -1;
        assert makeConnected(5, new int[][]{{0, 1}, {0, 2}, {3, 4}, {2, 3}}) == 0;
        System.out.println("NetworkConnected: OK");
    }
}
