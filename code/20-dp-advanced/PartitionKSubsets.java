import java.util.Arrays;

/**
 * LeetCode 698 — Partition to K Equal Sum Subsets. Bucket-by-bucket
 * backtracking with duplicate-skip and can't-start-a-bucket prunes.
 * Mirrors pages/20-dp-advanced.html.
 */
public class PartitionKSubsets {

    public static boolean canPartitionKSubsets(int[] nums, int k) {
        int total = 0;
        for (int x : nums) total += x;
        if (total % k != 0) return false;        // must divide evenly
        int target = total / k;
        Arrays.sort(nums);                       // ascending
        if (nums[nums.length - 1] > target) return false;  // one number too big

        return fill(nums, new boolean[nums.length], k, 0, 0, target);
    }

    // Complete `k` buckets; `sum` is the current bucket's fill, `start` the
    // scan position within the current bucket (avoids permuting choices).
    private static boolean fill(int[] nums, boolean[] used, int k,
                                int start, int sum, int target) {
        if (k == 1) return true;                 // last bucket gets the exact remainder
        if (sum == target)                       // bucket complete — start the next one
            return fill(nums, used, k - 1, 0, 0, target);
        for (int i = start; i < nums.length; i++) {
            if (used[i] || sum + nums[i] > target) continue;
            // Prune duplicates: an unused equal neighbor already failed here
            if (i > start && nums[i] == nums[i - 1] && !used[i - 1]) continue;
            used[i] = true;                      // choose
            if (fill(nums, used, k, i + 1, sum + nums[i], target)) return true;
            used[i] = false;                     // un-choose
            if (sum == 0) return false;          // key prune: if this element can't
                                                 // START a bucket, no one can — fail fast
        }
        return false;
    }

    public static void main(String[] args) {
        assert canPartitionKSubsets(new int[]{4, 3, 2, 3, 5, 2, 1}, 4);
        assert !canPartitionKSubsets(new int[]{1, 2, 3, 4}, 3);
        assert canPartitionKSubsets(new int[]{2, 2, 2, 2}, 2);
        assert !canPartitionKSubsets(new int[]{5, 1, 1}, 2);
        System.out.println("PartitionKSubsets: OK");
    }
}
