import java.util.ArrayList;
import java.util.List;

/**
 * LeetCode 417 — Pacific Atlantic Water Flow. Reverse the flow: DFS
 * uphill from each ocean's coastline; answer = cells both mark.
 * Mirrors pages/15-graphs-basics.html.
 */
public class PacificAtlantic {

    public static List<List<Integer>> pacificAtlantic(int[][] heights) {
        int m = heights.length, n = heights[0].length;
        // reachable[ocean][r][c]: can water from (r,c) reach that ocean?
        // We compute it BACKWARDS: walk uphill from the coastlines.
        boolean[][] pac = new boolean[m][n];
        boolean[][] atl = new boolean[m][n];

        for (int r = 0; r < m; r++) {
            climb(heights, pac, r, 0);          // Pacific: left edge
            climb(heights, atl, r, n - 1);      // Atlantic: right edge
        }
        for (int c = 0; c < n; c++) {
            climb(heights, pac, 0, c);          // Pacific: top edge
            climb(heights, atl, m - 1, c);      // Atlantic: bottom edge
        }

        List<List<Integer>> res = new ArrayList<>();
        for (int r = 0; r < m; r++)
            for (int c = 0; c < n; c++)
                if (pac[r][c] && atl[r][c])     // reaches BOTH oceans
                    res.add(List.of(r, c));
        return res;
    }

    // DFS marking every cell that can drain to this ocean. Because we walk
    // FROM the ocean, the move condition flips: we may only step UPHILL
    // (neighbor height >= current) — the reverse of water flowing down.
    private static void climb(int[][] h, boolean[][] seen, int r, int c) {
        if (seen[r][c]) return;
        seen[r][c] = true;
        int[][] dirs = {{1,0},{-1,0},{0,1},{0,-1}};
        for (int[] d : dirs) {
            int nr = r + d[0], nc = c + d[1];
            if (nr < 0 || nr >= h.length || nc < 0 || nc >= h[0].length) continue;
            if (h[nr][nc] >= h[r][c])           // uphill or flat — water could
                climb(h, seen, nr, nc);         // have flowed the other way
        }
    }

    public static void main(String[] args) {
        int[][] heights = {
            {1, 2, 2, 3, 5},
            {3, 2, 3, 4, 4},
            {2, 4, 5, 3, 1},
            {6, 7, 1, 4, 5},
            {5, 1, 1, 2, 4}
        };
        List<List<Integer>> res = pacificAtlantic(heights);
        assert res.size() == 7;
        assert res.contains(List.of(0, 4)) && res.contains(List.of(2, 2))
            && res.contains(List.of(3, 0)) && res.contains(List.of(4, 0));
        System.out.println("PacificAtlantic: OK");
    }
}
