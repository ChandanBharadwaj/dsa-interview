import java.util.Arrays;

/**
 * LeetCode 452 — Minimum Number of Arrows to Burst Balloons. Sort by end;
 * shoot each first-unpopped balloon at its end.
 * Mirrors pages/21-greedy-intervals.html.
 */
public class MinArrows {

    public static int findMinArrowShots(int[][] points) {
        if (points.length == 0) return 0;
        // Sort by END (overflow-safe compare — coordinates hit +-2^31).
        Arrays.sort(points, (a, b) -> Integer.compare(a[1], b[1]));

        int arrows = 1;
        long arrowX = points[0][1];        // greedy: shoot at the earliest end
        for (int[] p : points) {
            if (p[0] > arrowX) {           // this balloon starts after the arrow -> missed
                arrows++;                  // need a fresh arrow...
                arrowX = p[1];             // ...again at the new balloon's end
            }
        }
        return arrows;
    }

    public static void main(String[] args) {
        assert findMinArrowShots(new int[][]{{10, 16}, {2, 8}, {1, 6}, {7, 12}}) == 2;
        assert findMinArrowShots(new int[][]{{1, 2}, {3, 4}, {5, 6}, {7, 8}}) == 4;
        assert findMinArrowShots(new int[][]{{1, 2}, {2, 3}, {3, 4}, {4, 5}}) == 2;
        assert findMinArrowShots(new int[][]{{-2147483646, -2147483645},
                                             {2147483646, 2147483647}}) == 2;  // overflow guard
        System.out.println("MinArrows: OK");
    }
}
