import java.util.Arrays;

/**
 * LeetCode 733 — Flood Fill: DFS recolor where painting doubles as the
 * visited marker. Mirrors pages/15-graphs-basics.html.
 */
public class FloodFill {

    public static int[][] floodFill(int[][] image, int sr, int sc, int color) {
        int original = image[sr][sc];
        // Edge case that catches everyone: if the new color EQUALS the old,
        // recursion would re-visit painted cells forever. Nothing to do.
        if (original != color) fill(image, sr, sc, original, color);
        return image;
    }

    private static void fill(int[][] img, int r, int c, int from, int to) {
        if (r < 0 || r >= img.length || c < 0 || c >= img[0].length
                || img[r][c] != from)     // off-grid or not part of the region
            return;
        img[r][c] = to;                   // paint = mark visited, in one move
        fill(img, r + 1, c, from, to);
        fill(img, r - 1, c, from, to);
        fill(img, r, c + 1, from, to);
        fill(img, r, c - 1, from, to);
    }

    public static void main(String[] args) {
        int[][] img = {{1, 1, 1}, {1, 1, 0}, {1, 0, 1}};
        floodFill(img, 1, 1, 2);
        assert Arrays.deepEquals(img, new int[][]{{2, 2, 2}, {2, 2, 0}, {2, 0, 1}});
        int[][] same = {{0, 0}, {0, 0}};
        floodFill(same, 0, 0, 0);          // new color == old color: unchanged, no hang
        assert Arrays.deepEquals(same, new int[][]{{0, 0}, {0, 0}});
        System.out.println("FloodFill: OK");
    }
}
