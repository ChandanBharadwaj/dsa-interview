import java.util.*;

public class EventualSafeStates {

    // A node is safe iff NO path from it can reach a cycle. Three-color DFS:
    // gray means "on the current path" — meeting gray again means a cycle.
    private static final int WHITE = 0, GRAY = 1, BLACK = 2;   // black = proven safe

    public static List<Integer> eventualSafeNodes(int[][] graph) {
        int n = graph.length;
        int[] color = new int[n];
        List<Integer> safe = new ArrayList<>();
        for (int u = 0; u < n; u++)
            if (dfs(graph, u, color))
                safe.add(u);
        return safe;
    }

    private static boolean dfs(int[][] g, int u, int[] color) {
        if (color[u] != WHITE)
            return color[u] == BLACK;       // gray -> in-progress -> cycle
        color[u] = GRAY;                    // entering the current path
        for (int v : g[u])
            if (!dfs(g, v, color))
                return false;               // some path reaches a cycle: unsafe
        color[u] = BLACK;                   // every exit proved safe
        return true;
    }

    public static void main(String[] args) {
        assert eventualSafeNodes(new int[][]{{1, 2}, {2, 3}, {5}, {0}, {5}, {}, {}})
                .equals(Arrays.asList(2, 4, 5, 6));
        assert eventualSafeNodes(new int[][]{{1, 2, 3, 4}, {1, 2}, {3, 4}, {0, 4}, {}})
                .equals(Arrays.asList(4));
        assert eventualSafeNodes(new int[][]{{}}).equals(Arrays.asList(0));
        System.out.println("EventualSafeStates: OK");
    }
}
