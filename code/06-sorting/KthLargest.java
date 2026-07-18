import java.util.Random;

/**
 * LeetCode 215 — Kth Largest Element in an Array, via quickselect.
 * Average O(n) instead of a full O(n log n) sort. Mirrors pages/06-sorting.html.
 */
public class KthLargest {

    private static final Random RAND = new Random();

    public static int findKthLargest(int[] nums, int k) {
        int target = nums.length - k;              // index the answer occupies in sorted order
        int lo = 0, hi = nums.length - 1;
        while (true) {
            int p = partition(nums, lo, hi);       // pivot lands at its FINAL sorted index p
            if (p == target) return nums[p];       // pivot is exactly the K-th largest — done
            if (p < target) lo = p + 1;            // answer lies right of p — discard left side
            else hi = p - 1;                       // answer lies left of p — discard right side
        }
    }

    // Lomuto partition with a random pivot: returns the pivot's final index
    private static int partition(int[] a, int lo, int hi) {
        int r = lo + RAND.nextInt(hi - lo + 1);    // random pivot defeats sorted-input worst case
        swap(a, r, hi);                            // park pivot at the end
        int pivot = a[hi];
        int i = lo;                                // a[lo..i-1] = the "< pivot" zone
        for (int j = lo; j < hi; j++)
            if (a[j] < pivot) swap(a, i++, j);     // grow the smaller-zone by one
        swap(a, i, hi);                            // drop pivot just after the smaller-zone
        return i;                                  // pivot's final resting index
    }

    private static void swap(int[] a, int i, int j) { int t = a[i]; a[i] = a[j]; a[j] = t; }

    public static void main(String[] args) {
        assert findKthLargest(new int[]{3, 2, 1, 5, 6, 4}, 2) == 5;
        assert findKthLargest(new int[]{3, 2, 3, 1, 2, 4, 5, 5, 6}, 4) == 4;
        assert findKthLargest(new int[]{1}, 1) == 1;
        System.out.println("KthLargest: OK");
    }
}
