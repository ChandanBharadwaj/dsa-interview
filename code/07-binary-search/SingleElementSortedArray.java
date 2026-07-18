public class SingleElementSortedArray {

    // Sorted, every element twice except one, O(log n) required.
    // Invariant: before the single element, pairs start at EVEN indices;
    // after it, they start at ODD indices. Binary search on where that flips.
    public static int singleNonDuplicate(int[] nums) {
        int lo = 0, hi = nums.length - 1;
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (mid % 2 == 1) mid--;            // align mid to a pair-start slot
            if (nums[mid] == nums[mid + 1])
                lo = mid + 2;                   // pairing intact -> single is right
            else
                hi = mid;                       // pairing broken -> at mid or left
        }
        return nums[lo];
    }

    public static void main(String[] args) {
        assert singleNonDuplicate(new int[]{1, 1, 2, 3, 3, 4, 4, 8, 8}) == 2;
        assert singleNonDuplicate(new int[]{3, 3, 7, 7, 10, 11, 11}) == 10;
        assert singleNonDuplicate(new int[]{5}) == 5;
        assert singleNonDuplicate(new int[]{1, 1, 2}) == 2;
        assert singleNonDuplicate(new int[]{0, 1, 1}) == 0;
        System.out.println("SingleElementSortedArray: OK");
    }
}
