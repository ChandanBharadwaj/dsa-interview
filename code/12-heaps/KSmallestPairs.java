import java.util.*;

public class KSmallestPairs {

    // Both arrays sorted. Think of the pair grid (i, j): the smallest pair is
    // (0,0), and each pair's successors are (i+1, j) and (i, j+1). A min-heap
    // frontier explores in sum order — never building the full n*m grid.
    public static List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        List<List<Integer>> res = new ArrayList<>();
        PriorityQueue<int[]> heap =               // {i, j}, ordered by pair sum
                new PriorityQueue<>(Comparator.comparingLong(
                        p -> (long) nums1[p[0]] + nums2[p[1]]));
        // Seed with (i, 0) for each i: every column starts at j = 0, and
        // (i, j+1) is pushed only when (i, j) pops — no visited-set needed.
        for (int i = 0; i < Math.min(k, nums1.length); i++)
            heap.offer(new int[]{i, 0});
        while (!heap.isEmpty() && res.size() < k) {
            int[] p = heap.poll();
            res.add(Arrays.asList(nums1[p[0]], nums2[p[1]]));
            if (p[1] + 1 < nums2.length)
                heap.offer(new int[]{p[0], p[1] + 1});
        }
        return res;
    }

    public static void main(String[] args) {
        assert kSmallestPairs(new int[]{1, 7, 11}, new int[]{2, 4, 6}, 3)
                .equals(List.of(List.of(1, 2), List.of(1, 4), List.of(1, 6)));
        List<List<Integer>> r = kSmallestPairs(new int[]{1, 1, 2}, new int[]{1, 2, 3}, 2);
        assert r.equals(List.of(List.of(1, 1), List.of(1, 1)));
        assert kSmallestPairs(new int[]{1}, new int[]{1}, 5).size() == 1;  // k > total
        System.out.println("KSmallestPairs: OK");
    }
}
