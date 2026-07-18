// LeetCode 34 — Find First and Last Position of Element in Sorted Array
// Pattern: "first true" boundary binary search, run twice.
public class SearchRange {

    public static int[] searchRange(int[] nums, int target) {
        // First index whose value is >= target  (first position of target, if present)
        int first = lowerBound(nums, target);
        // If we ran off the end, or the value there isn't target, target is absent
        if (first == nums.length || nums[first] != target) return new int[]{-1, -1};
        // First index whose value is >= target+1 sits one past the LAST target
        int last = lowerBound(nums, target + 1) - 1;
        return new int[]{first, last};
    }

    // Classic "first index where predicate is true" template.
    // Predicate here: nums[i] >= target. It's false,false,...,true,true — monotonic.
    static int lowerBound(int[] nums, int target) {
        int lo = 0, hi = nums.length;          // hi is EXCLUSIVE: answer may be nums.length ("never true")
        while (lo < hi) {                      // stop when the range is empty: lo == hi is the answer
            int mid = lo + (hi - lo) / 2;      // overflow-safe midpoint (never write (lo+hi)/2)
            if (nums[mid] >= target) {
                hi = mid;                      // mid satisfies the predicate: it COULD be the first true — keep it in range
            } else {
                lo = mid + 1;                  // mid is false: the first true must be strictly to the right
            }
        }
        return lo;                             // lo == hi == first index where nums[i] >= target
    }

    public static void main(String[] args) {
        assert java.util.Arrays.equals(searchRange(new int[]{5,7,7,8,8,10}, 8), new int[]{3,4});
        assert java.util.Arrays.equals(searchRange(new int[]{5,7,7,8,8,10}, 6), new int[]{-1,-1});
        assert java.util.Arrays.equals(searchRange(new int[]{}, 0), new int[]{-1,-1});
        assert java.util.Arrays.equals(searchRange(new int[]{1}, 1), new int[]{0,0});
        System.out.println("SearchRange: OK");
    }
}
