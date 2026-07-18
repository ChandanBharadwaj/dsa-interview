// LeetCode 33 — Search in Rotated Sorted Array
// Key insight: at every mid, AT LEAST ONE half is perfectly sorted.
// Check the sorted half's endpoints to decide where target can live.
public class SearchRotated {

    public static int search(int[] nums, int target) {
        int lo = 0, hi = nums.length - 1;      // inclusive range this time — we're hunting an exact index
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;      // overflow-safe midpoint
            if (nums[mid] == target) return mid;

            if (nums[lo] <= nums[mid]) {
                // Left half [lo..mid] is sorted (no rotation point inside it)
                if (nums[lo] <= target && target < nums[mid]) {
                    hi = mid - 1;              // target sits inside the sorted left half — search there
                } else {
                    lo = mid + 1;              // otherwise it can only be in the (possibly rotated) right half
                }
            } else {
                // Right half [mid..hi] is sorted
                if (nums[mid] < target && target <= nums[hi]) {
                    lo = mid + 1;              // target sits inside the sorted right half
                } else {
                    hi = mid - 1;              // otherwise it's in the rotated left half
                }
            }
        }
        return -1;                             // range emptied without a hit — not present
    }

    public static void main(String[] args) {
        assert search(new int[]{4,5,6,7,0,1,2}, 0) == 4;
        assert search(new int[]{4,5,6,7,0,1,2}, 3) == -1;
        assert search(new int[]{1}, 1) == 0;
        assert search(new int[]{5,1,3}, 5) == 0;
        System.out.println("SearchRotated: OK");
    }
}
