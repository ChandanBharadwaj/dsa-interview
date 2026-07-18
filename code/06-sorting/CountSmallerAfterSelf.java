import java.util.ArrayList;
import java.util.List;

/**
 * LeetCode 315 — Count of Smaller Numbers After Self. Merge sort over
 * INDEXES, counting cross-half inversions during each merge: O(n log n).
 * Mirrors pages/06-sorting.html.
 */
public class CountSmallerAfterSelf {

    public static List<Integer> countSmallerBrute(int[] nums) {
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            int c = 0;
            for (int j = i + 1; j < nums.length; j++)
                if (nums[j] < nums[i]) c++;
            res.add(c);
        }
        return res;
    }

    public static List<Integer> countSmaller(int[] nums) {
        int n = nums.length;
        int[] counts = new int[n];
        int[] idx = new int[n];               // we sort INDEXES, not values,
        for (int i = 0; i < n; i++) idx[i] = i;   // so each count lands at its owner
        mergeCount(nums, idx, new int[n], counts, 0, n - 1);
        List<Integer> res = new ArrayList<>(n);
        for (int c : counts) res.add(c);
        return res;
    }

    private static void mergeCount(int[] a, int[] idx, int[] tmp, int[] counts, int lo, int hi) {
        if (lo >= hi) return;
        int mid = lo + (hi - lo) / 2;
        mergeCount(a, idx, tmp, counts, lo, mid);        // sort left half (by value)
        mergeCount(a, idx, tmp, counts, mid + 1, hi);    // sort right half

        // Merge, counting cross-half inversions. Both halves are sorted, so
        // when a left element is placed, every right element already placed
        // (rightPlaced of them) is a smaller element that was ORIGINALLY to
        // its right — exactly what we're counting. One merge counts a whole
        // batch of pairs at once; that's the n^2 -> n log n collapse.
        int i = lo, j = mid + 1, t = lo, rightPlaced = 0;
        while (i <= mid || j <= hi) {
            if (j > hi || (i <= mid && a[idx[i]] <= a[idx[j]])) {
                counts[idx[i]] += rightPlaced;   // credit the left element
                tmp[t++] = idx[i++];
            } else {
                rightPlaced++;                   // a right (smaller) element goes first
                tmp[t++] = idx[j++];
            }
        }
        System.arraycopy(tmp, lo, idx, lo, hi - lo + 1);
    }

    public static void main(String[] args) {
        assert countSmaller(new int[]{5, 2, 6, 1}).equals(List.of(2, 1, 1, 0));
        assert countSmaller(new int[]{-1}).equals(List.of(0));
        assert countSmaller(new int[]{-1, -1}).equals(List.of(0, 0));
        assert countSmaller(new int[]{2, 0, 1}).equals(countSmallerBrute(new int[]{2, 0, 1}));
        // randomized cross-check against brute force
        java.util.Random r = new java.util.Random(42);
        for (int t = 0; t < 20; t++) {
            int[] arr = new int[30];
            for (int i = 0; i < arr.length; i++) arr[i] = r.nextInt(50) - 25;
            assert countSmaller(arr.clone()).equals(countSmallerBrute(arr));
        }
        System.out.println("CountSmallerAfterSelf: OK");
    }
}
