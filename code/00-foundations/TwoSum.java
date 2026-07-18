import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * LeetCode 1 — Two Sum. The canonical space-for-time trade:
 * brute force O(n^2)/O(1) vs one-pass HashMap O(n)/O(n).
 * Mirrors pages/00-foundations.html.
 */
public class TwoSum {

    // Brute force: O(n^2) time, O(1) space — check every pair
    public static int[] twoSumBrute(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++)
            for (int j = i + 1; j < nums.length; j++)
                if (nums[i] + nums[j] == target) return new int[]{i, j};
        return new int[]{};
    }

    // HashMap: O(n) time, O(n) space — one pass
    public static int[] twoSum(int[] nums, int target) {
        // value -> index of every number we've already walked past
        Map<Integer, Integer> seen = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int need = target - nums[i];        // the partner we're hoping exists
            if (seen.containsKey(need))         // O(1) lookup instead of an O(n) rescan
                return new int[]{seen.get(need), i};
            seen.put(nums[i], i);               // remember current number for the future
        }
        return new int[]{};
    }

    public static void main(String[] args) {
        assert Arrays.equals(twoSum(new int[]{2, 7, 11, 15}, 9), new int[]{0, 1});
        assert Arrays.equals(twoSum(new int[]{3, 2, 4}, 6), new int[]{1, 2});
        assert Arrays.equals(twoSumBrute(new int[]{3, 3}, 6), new int[]{0, 1});
        System.out.println("TwoSum: OK");
    }
}
