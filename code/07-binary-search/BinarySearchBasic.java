/**
 * LeetCode 704 — Binary Search (Template A, exact match).
 * Mirrors pages/07-binary-search.html.
 */
public class BinarySearchBasic {

    public static int search(int[] nums, int target) {
        int lo = 0, hi = nums.length - 1;      // inclusive search range
        while (lo <= hi) {                     // range still non-empty
            int mid = lo + (hi - lo) / 2;      // overflow-safe midpoint
            if (nums[mid] == target) return mid;
            if (nums[mid] < target) lo = mid + 1;   // left half (incl. mid) too small
            else hi = mid - 1;                      // right half (incl. mid) too big
        }
        return -1;                             // range emptied: not present
    }

    public static void main(String[] args) {
        assert search(new int[]{-1, 0, 3, 5, 9, 12}, 9) == 4;
        assert search(new int[]{-1, 0, 3, 5, 9, 12}, 2) == -1;
        assert search(new int[]{5}, 5) == 0;
        assert search(new int[]{}, 1) == -1;
        System.out.println("BinarySearchBasic: OK");
    }
}
