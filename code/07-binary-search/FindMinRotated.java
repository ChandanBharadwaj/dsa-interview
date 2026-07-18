/**
 * LeetCode 153 — Find Minimum in Rotated Sorted Array. Boundary search
 * with predicate "nums[i] <= nums[n-1]" (false in the head, true in the
 * tail). Mirrors pages/07-binary-search.html.
 */
public class FindMinRotated {

    public static int findMin(int[] nums) {
        // Predicate P(i): nums[i] <= nums[n-1] ("i is in the tail segment").
        // Before the rotation point P is false, after it P is true — monotonic!
        // The minimum is the FIRST index where P is true.
        int lo = 0, hi = nums.length - 1;      // hi starts at n-1: P(n-1) is trivially true
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (nums[mid] <= nums[nums.length - 1]) {
                hi = mid;                      // mid is in the tail — the min is mid or earlier
            } else {
                lo = mid + 1;                  // mid is in the head (big values) — min is later
            }
        }
        return nums[lo];                       // first index of the tail segment = the minimum
    }

    public static void main(String[] args) {
        assert findMin(new int[]{3, 4, 5, 1, 2}) == 1;
        assert findMin(new int[]{4, 5, 6, 7, 0, 1, 2}) == 0;
        assert findMin(new int[]{11, 13, 15, 17}) == 11;   // rotation of 0
        assert findMin(new int[]{2, 1}) == 1;
        System.out.println("FindMinRotated: OK");
    }
}
