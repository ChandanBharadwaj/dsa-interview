import java.util.HashMap;
import java.util.Map;

// LeetCode 560. Subarray Sum Equals K — prefix sums + HashMap (negatives welcome)
public class SubarraySumEqualsK {

    public static int subarraySum(int[] nums, int k) {
        // How many times have we seen each prefix sum so far?
        // Seed: the empty prefix (sum 0) has been seen once — it lets a
        // subarray that starts at index 0 be counted.
        Map<Long, Integer> seen = new HashMap<>();
        seen.put(0L, 1);

        long running = 0;                  // prefix sum ending at the current index
        int count = 0;
        for (int x : nums) {
            running += x;                  // extend the prefix by one element
            // A subarray ending here sums to k  <=>  some earlier prefix equals running - k.
            // Each earlier occurrence is a distinct start index, so add them all at once.
            count += seen.getOrDefault(running - k, 0);
            // Only NOW record the current prefix — recording first would let the
            // zero-length subarray match itself when k == 0.
            seen.merge(running, 1, Integer::sum);
        }
        return count;
    }

    public static void main(String[] args) {
        assert subarraySum(new int[]{1, 1, 1}, 2) == 2;      // [1,1] twice
        assert subarraySum(new int[]{1, 2, 3}, 3) == 2;      // [1,2] and [3]
        assert subarraySum(new int[]{1, -1, 0}, 0) == 3;     // negatives: a window would fail
        assert subarraySum(new int[]{3, 4, 7, 2, -3, 1, 4, 2}, 7) == 4;
        System.out.println("SubarraySumEqualsK: OK");
    }
}
