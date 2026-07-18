import java.util.Arrays;

/**
 * LeetCode 75 — Sort Colors (Dutch National Flag).
 * One pass, three zones, two boundaries. Mirrors pages/06-sorting.html.
 */
public class SortColors {

    public static void sortColors(int[] nums) {
        int lo = 0;                    // next slot for a 0 (zone boundary)
        int hi = nums.length - 1;      // next slot for a 2 (zone boundary)
        int i = 0;                     // current element under inspection
        while (i <= hi) {              // hi side is UNSORTED territory — stop when we meet it
            if (nums[i] == 0) {
                swap(nums, i, lo);     // send 0 to the left zone
                lo++; i++;             // the swapped-in value came from the 1-zone — safe to advance
            } else if (nums[i] == 2) {
                swap(nums, i, hi);     // send 2 to the right zone
                hi--;                  // do NOT advance i: the swapped-in value is UNSEEN — recheck it
            } else {
                i++;                   // 1 is already in the middle zone
            }
        }
    }

    private static void swap(int[] a, int i, int j) { int t = a[i]; a[i] = a[j]; a[j] = t; }

    public static void main(String[] args) {
        int[] a = {2, 0, 2, 1, 1, 0};
        sortColors(a);
        assert Arrays.equals(a, new int[]{0, 0, 1, 1, 2, 2});
        int[] b = {2, 0, 1};
        sortColors(b);
        assert Arrays.equals(b, new int[]{0, 1, 2});
        int[] c = {0};
        sortColors(c);
        assert Arrays.equals(c, new int[]{0});
        System.out.println("SortColors: OK");
    }
}
