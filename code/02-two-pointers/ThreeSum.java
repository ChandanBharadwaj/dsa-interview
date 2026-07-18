import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// LeetCode 15 — 3Sum
// Pattern: sort, fix one anchor, then run converging pointers (Two Sum II)
// on the remainder. Sorting also makes duplicate-skipping trivial.
public class ThreeSum {

    public static List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);   // O(n log n) — enables two pointers AND duplicate skips
        List<List<Integer>> res = new ArrayList<>();
        for (int i = 0; i < nums.length - 2; i++) {
            // Sorted, so if the smallest of the three is positive,
            // no triple from here on can sum to zero — stop early.
            if (nums[i] > 0) break;
            // Same anchor value as last iteration would rebuild the same
            // triples — skip it (this is why the answer has no duplicates).
            if (i > 0 && nums[i] == nums[i - 1]) continue;
            // Now solve Two Sum II on nums[i+1..end] with target -nums[i]
            int l = i + 1, r = nums.length - 1;
            while (l < r) {
                int sum = nums[i] + nums[l] + nums[r];
                if (sum < 0) {
                    l++;              // too small — need a bigger left value
                } else if (sum > 0) {
                    r--;              // too big — need a smaller right value
                } else {
                    res.add(Arrays.asList(nums[i], nums[l], nums[r]));
                    l++;
                    r--;
                    // Skip over duplicate values so the SAME triple
                    // isn't emitted again with equal elements
                    while (l < r && nums[l] == nums[l - 1]) l++;
                    while (l < r && nums[r] == nums[r + 1]) r--;
                }
            }
        }
        return res;
    }
    // Time O(n^2): n anchors, each with an O(n) two-pointer sweep
    // (the O(n log n) sort is dominated).
    // Space O(1) extra beyond the output (sort is in-place for primitives).

    public static void main(String[] args) {
        List<List<Integer>> r1 = threeSum(new int[]{-1, 0, 1, 2, -1, -4});
        assert r1.size() == 2;
        assert r1.contains(Arrays.asList(-1, -1, 2));
        assert r1.contains(Arrays.asList(-1, 0, 1));
        assert threeSum(new int[]{0, 1, 1}).isEmpty();
        assert threeSum(new int[]{0, 0, 0}).equals(List.of(Arrays.asList(0, 0, 0)));
        System.out.println("ThreeSum: OK");
    }
}
