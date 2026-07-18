import java.util.*;

public class DetectSquares {

    // Count of each point ever added. Keyed "x,y"; duplicates count multiple
    // squares, so we store counts, not a set.
    private final Map<String, Integer> counts = new HashMap<>();
    private final List<int[]> points = new ArrayList<>();

    public void add(int[] point) {
        counts.merge(point[0] + "," + point[1], 1, Integer::sum);
        points.add(point);
    }

    // For the query point q, try every stored point p as the DIAGONAL corner:
    // |dx| == |dy| != 0 makes (q,p) a diagonal, and the other two corners are
    // then forced — look up how many copies of each exist.
    public int count(int[] q) {
        int total = 0;
        for (int[] p : points) {
            int dx = p[0] - q[0], dy = p[1] - q[1];
            if (dx == 0 || Math.abs(dx) != Math.abs(dy)) continue;  // not a diagonal
            // remaining corners: (q.x, p.y) and (p.x, q.y)
            total += counts.getOrDefault(q[0] + "," + p[1], 0)
                   * counts.getOrDefault(p[0] + "," + q[1], 0);
        }
        return total;
    }

    public static void main(String[] args) {
        DetectSquares ds = new DetectSquares();
        ds.add(new int[]{3, 10});
        ds.add(new int[]{11, 2});
        ds.add(new int[]{3, 2});
        assert ds.count(new int[]{11, 10}) == 1;   // one axis-aligned square
        assert ds.count(new int[]{14, 8}) == 0;
        ds.add(new int[]{11, 2});                  // duplicate corner doubles the count
        assert ds.count(new int[]{11, 10}) == 2;
        System.out.println("DetectSquares: OK");
    }
}
