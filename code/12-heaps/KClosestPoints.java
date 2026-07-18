import java.util.*;

public class KClosestPoints {

    // LeetCode 973. Return the k points closest to the origin (any order).
    public static int[][] kClosest(int[][] points, int k) {
        // THE INVERSION TRICK: to keep the k CLOSEST points, use a MAX-heap
        // ordered by distance. Its root is the WORST point we're keeping —
        // exactly the one to evict when someone better shows up.
        // (Squared distance avoids sqrt AND floating point entirely.)
        PriorityQueue<int[]> worstFirst = new PriorityQueue<>(
                (a, b) -> Long.compare(dist(b), dist(a)));  // reversed = max-heap

        for (int[] p : points) {
            worstFirst.offer(p);              // O(log k)
            if (worstFirst.size() > k) {
                worstFirst.poll();            // evict the farthest of the k+1
            }
        }
        // Whatever survived is the answer. Heap order is NOT sorted order —
        // we just drain it; the problem allows any order.
        int[][] result = new int[k][];
        for (int i = 0; i < k; i++) result[i] = worstFirst.poll();
        return result;
    }

    // Squared Euclidean distance from the origin, as long to dodge overflow.
    private static long dist(int[] p) {
        return (long) p[0] * p[0] + (long) p[1] * p[1];
    }

    public static void main(String[] args) {
        // Closest 1 of [[1,3],[-2,2]] -> [-2,2] (dist 8 < 10)
        int[][] r1 = kClosest(new int[][]{{1, 3}, {-2, 2}}, 1);
        assert r1.length == 1 && r1[0][0] == -2 && r1[0][1] == 2;
        // Closest 2 of [[3,3],[5,-1],[-2,4]] -> {[3,3],[-2,4]} in any order
        int[][] r2 = kClosest(new int[][]{{3, 3}, {5, -1}, {-2, 4}}, 2);
        assert r2.length == 2;
        boolean has33 = false, hasM24 = false;
        for (int[] p : r2) {
            if (p[0] == 3 && p[1] == 3) has33 = true;
            if (p[0] == -2 && p[1] == 4) hasM24 = true;
        }
        assert has33 && hasM24;
        System.out.println("KClosestPoints: OK");
    }
}
