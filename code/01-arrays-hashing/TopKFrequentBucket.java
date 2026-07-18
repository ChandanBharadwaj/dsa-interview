import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * LeetCode 347 — Top K Frequent Elements. Two variations from the page:
 * count + sort-by-frequency O(n log n) vs bucket-by-frequency O(n).
 * (The size-k min-heap variation lives in code/12-heaps/TopKFrequent.java.)
 * Mirrors pages/01-arrays-hashing.html.
 */
public class TopKFrequentBucket {

    public static int[] topKFrequentSort(int[] nums, int k) {
        Map<Integer, Integer> freq = new HashMap<>();
        for (int x : nums) freq.merge(x, 1, Integer::sum);       // value -> count

        // Sort the distinct values by their count, biggest first
        List<Integer> vals = new ArrayList<>(freq.keySet());
        vals.sort((a, b) -> freq.get(b) - freq.get(a));
        int[] res = new int[k];
        for (int i = 0; i < k; i++) res[i] = vals.get(i);
        return res;
    }

    @SuppressWarnings("unchecked")
    public static int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> freq = new HashMap<>();
        for (int x : nums) freq.merge(x, 1, Integer::sum);

        // Key idea: a count can never exceed n, so use the COUNT as an array
        // index. bucket[c] = all values that occur exactly c times.
        List<Integer>[] bucket = new List[nums.length + 1];
        for (Map.Entry<Integer, Integer> e : freq.entrySet()) {
            int c = e.getValue();
            if (bucket[c] == null) bucket[c] = new ArrayList<>();
            bucket[c].add(e.getKey());
        }

        // Walk buckets from the highest count down, collecting k values
        int[] res = new int[k];
        int idx = 0;
        for (int c = nums.length; c >= 1 && idx < k; c--)
            if (bucket[c] != null)
                for (int v : bucket[c]) {
                    res[idx++] = v;
                    if (idx == k) break;
                }
        return res;
    }

    public static void main(String[] args) {
        int[] r = topKFrequent(new int[]{1, 1, 1, 2, 2, 3}, 2);
        Arrays.sort(r);
        assert Arrays.equals(r, new int[]{1, 2});
        assert Arrays.equals(topKFrequent(new int[]{1}, 1), new int[]{1});
        int[] r2 = topKFrequentSort(new int[]{4, 4, 4, 6, 6, 5}, 2);
        Arrays.sort(r2);
        assert Arrays.equals(r2, new int[]{4, 6});
        System.out.println("TopKFrequentBucket: OK");
    }
}
