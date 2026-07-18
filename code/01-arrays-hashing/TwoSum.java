import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

// LeetCode 1 — Two Sum
// Pattern: complement lookup. Trade O(n) space for O(1) lookups
// so we never rescan the array.
public class TwoSum {

    public static int[] twoSum(int[] nums, int target) {
        // value -> index of every number we've already walked past
        Map<Integer, Integer> seen = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            // The partner nums[i] needs to reach the target
            int need = target - nums[i];
            // O(1) average lookup — this replaces an O(n) rescan of the array
            if (seen.containsKey(need)) {
                // Partner was seen earlier, so its index comes first
                return new int[]{seen.get(need), i};
            }
            // Add AFTER the check — prevents pairing an element with itself
            // (e.g. target 6 must not match a single 3 twice)
            seen.put(nums[i], i);
        }
        // The problem guarantees exactly one answer, so we never get here
        return new int[]{-1, -1};
    }
    // Time O(n): one pass, O(1) work per element.
    // Space O(n): the map can hold every element.

    public static void main(String[] args) {
        assert Arrays.equals(twoSum(new int[]{2, 7, 11, 15}, 9), new int[]{0, 1});
        assert Arrays.equals(twoSum(new int[]{3, 2, 4}, 6), new int[]{1, 2});
        assert Arrays.equals(twoSum(new int[]{3, 3}, 6), new int[]{0, 1});
        System.out.println("TwoSum: OK");
    }
}
