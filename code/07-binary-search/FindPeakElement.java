public class FindPeakElement {

    // No sortedness at all — yet binary search works, because the slope at
    // mid tells you a peak's direction: rising means a peak lies right
    // (climb until you must stop), falling means one lies left (or at mid).
    public static int findPeakElement(int[] nums) {
        int lo = 0, hi = nums.length - 1;
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (nums[mid] < nums[mid + 1])
                lo = mid + 1;       // uphill to the right -> peak that way
            else
                hi = mid;           // downhill (or peak) -> keep mid in play
        }
        return lo;                  // lo == hi: the peak
    }

    public static void main(String[] args) {
        assert findPeakElement(new int[]{1, 2, 3, 1}) == 2;
        int p = findPeakElement(new int[]{1, 2, 1, 3, 5, 6, 4});
        assert p == 1 || p == 5;    // either peak is valid
        assert findPeakElement(new int[]{5}) == 0;
        assert findPeakElement(new int[]{1, 2}) == 1;
        assert findPeakElement(new int[]{2, 1}) == 0;
        System.out.println("FindPeakElement: OK");
    }
}
