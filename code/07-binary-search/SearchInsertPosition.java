/**
 * LeetCode 35 — Search Insert Position: lowerBound (first index >= target).
 * Mirrors pages/07-binary-search.html.
 */
public class SearchInsertPosition {

    public static int searchInsert(int[] nums, int target) {
        // Both cases collapse into one question: what is the FIRST index
        // whose value is >= target? (present -> its index; absent -> the gap)
        int lo = 0, hi = nums.length;          // hi EXCLUSIVE: "insert at end" = nums.length
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (nums[mid] >= target) hi = mid;      // mid could be the first >= — keep it
            else lo = mid + 1;                      // mid too small — answer is right of it
        }
        return lo;
    }

    public static void main(String[] args) {
        assert searchInsert(new int[]{1, 3, 5, 6}, 5) == 2;
        assert searchInsert(new int[]{1, 3, 5, 6}, 2) == 1;
        assert searchInsert(new int[]{1, 3, 5, 6}, 7) == 4;
        assert searchInsert(new int[]{1, 3, 5, 6}, 0) == 0;
        System.out.println("SearchInsertPosition: OK");
    }
}
