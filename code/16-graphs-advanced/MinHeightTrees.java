import java.util.*;

public class MinHeightTrees {

    // The min-height roots are the CENTER of the tree. Peel leaves layer by
    // layer (topological-sort style on degree 1); the last 1-2 nodes remain.
    public static List<Integer> findMinHeightTrees(int n, int[][] edges) {
        if (n == 1) return List.of(0);
        List<Set<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) adj.add(new HashSet<>());
        for (int[] e : edges) {
            adj.get(e[0]).add(e[1]);
            adj.get(e[1]).add(e[0]);
        }
        List<Integer> leaves = new ArrayList<>();
        for (int i = 0; i < n; i++)
            if (adj.get(i).size() == 1) leaves.add(i);

        int remaining = n;
        while (remaining > 2) {                  // center is at most 2 nodes
            remaining -= leaves.size();
            List<Integer> next = new ArrayList<>();
            for (int leaf : leaves) {
                int nb = adj.get(leaf).iterator().next();   // a leaf's only neighbor
                adj.get(nb).remove(leaf);        // peel the leaf off
                if (adj.get(nb).size() == 1)     // neighbor became a leaf
                    next.add(nb);
            }
            leaves = next;
        }
        return leaves;
    }

    public static void main(String[] args) {
        assert new HashSet<>(findMinHeightTrees(4, new int[][]{{1, 0}, {1, 2}, {1, 3}}))
                .equals(Set.of(1));
        assert new HashSet<>(findMinHeightTrees(6,
                new int[][]{{3, 0}, {3, 1}, {3, 2}, {3, 4}, {5, 4}}))
                .equals(Set.of(3, 4));
        assert findMinHeightTrees(1, new int[][]{}).equals(List.of(0));
        assert new HashSet<>(findMinHeightTrees(2, new int[][]{{0, 1}})).equals(Set.of(0, 1));
        System.out.println("MinHeightTrees: OK");
    }
}
