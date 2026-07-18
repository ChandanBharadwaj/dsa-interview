import java.util.*;

public class MaxPointsOnLine {

    // For each anchor point, bucket every other point by the SLOPE of the
    // line through the anchor — same slope through the same point = same
    // line. Slopes as reduced fractions dy/dx avoid float equality bugs.
    public static int maxPoints(int[][] points) {
        int n = points.length;
        if (n <= 2) return n;
        int best = 2;
        for (int i = 0; i < n; i++) {
            Map<String, Integer> slopes = new HashMap<>();
            for (int j = i + 1; j < n; j++) {
                int dx = points[j][0] - points[i][0];
                int dy = points[j][1] - points[i][1];
                int g = gcd(Math.abs(dx), Math.abs(dy));
                dx /= g;
                dy /= g;
                if (dx < 0 || (dx == 0 && dy < 0)) {   // canonical sign
                    dx = -dx;
                    dy = -dy;
                }
                int c = slopes.merge(dy + "/" + dx, 1, Integer::sum);
                best = Math.max(best, c + 1);          // +1 for the anchor itself
            }
        }
        return best;
    }

    private static int gcd(int a, int b) {
        return b == 0 ? (a == 0 ? 1 : a) : gcd(b, a % b);
    }

    public static void main(String[] args) {
        assert maxPoints(new int[][]{{1, 1}, {2, 2}, {3, 3}}) == 3;
        assert maxPoints(new int[][]{{1, 1}, {3, 2}, {5, 3}, {4, 1}, {2, 3}, {1, 4}}) == 4;
        assert maxPoints(new int[][]{{0, 0}}) == 1;
        assert maxPoints(new int[][]{{0, 0}, {0, 5}, {0, -3}}) == 3;   // vertical line
        assert maxPoints(new int[][]{{2, 3}, {3, 3}, {-5, 3}}) == 3;   // horizontal
        System.out.println("MaxPointsOnLine: OK");
    }
}
