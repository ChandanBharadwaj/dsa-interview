import java.util.*;

public class TopKFrequent {

    // LeetCode 347. Return the k most frequent elements (heap version).
    public static int[] topKFrequent(int[] nums, int k) {
        // Step 1: frequency map — value -> how many times it appears (page 01).
        Map<Integer, Integer> count = new HashMap<>();
        for (int x : nums) count.merge(x, 1, Integer::sum);

        // Step 2: keep a size-k MIN-heap of (value, freq), ordered by freq.
        // Counter-intuitive but key: for the k LARGEST frequencies we use a
        // MIN-heap, because its root is the weakest member of our top-k club —
        // the only one a newcomer ever needs to beat.
        PriorityQueue<int[]> minFreqFirst =
                new PriorityQueue<>((a, b) -> Integer.compare(a[1], b[1]));

        for (Map.Entry<Integer, Integer> e : count.entrySet()) {
            minFreqFirst.offer(new int[]{e.getKey(), e.getValue()}); // O(log k)
            if (minFreqFirst.size() > k) {
                minFreqFirst.poll();  // evict the least frequent of the k+1
            }
        }
        // The heap never grows past k+1: n entries * O(log k) = O(n log k),
        // beating the O(n log n) full sort when k is small.
        int[] result = new int[k];
        for (int i = 0; i < k; i++) result[i] = minFreqFirst.poll()[0];
        return result;
    }

    public static void main(String[] args) {
        int[] r1 = topKFrequent(new int[]{1, 1, 1, 2, 2, 3}, 2);
        Set<Integer> s1 = new HashSet<>();
        for (int x : r1) s1.add(x);
        assert s1.equals(Set.of(1, 2)) : "expected {1,2}, got " + s1;

        int[] r2 = topKFrequent(new int[]{4}, 1);
        assert r2.length == 1 && r2[0] == 4;

        int[] r3 = topKFrequent(new int[]{5, 5, 6, 6, 7}, 3);
        Set<Integer> s3 = new HashSet<>();
        for (int x : r3) s3.add(x);
        assert s3.equals(Set.of(5, 6, 7));
        System.out.println("TopKFrequent: OK");
    }
}
