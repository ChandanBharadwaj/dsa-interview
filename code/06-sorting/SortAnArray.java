import java.util.*;

public class SortAnArray {

    // Variation 1: merge sort — stable, guaranteed O(n log n), O(n) scratch.
    public static void mergeSort(int[] a) {
        if (a.length < 2) return;
        int[] tmp = new int[a.length];
        mergeSort(a, tmp, 0, a.length - 1);
    }

    private static void mergeSort(int[] a, int[] tmp, int lo, int hi) {
        if (lo >= hi) return;
        int mid = lo + (hi - lo) / 2;
        mergeSort(a, tmp, lo, mid);         // sort left half
        mergeSort(a, tmp, mid + 1, hi);     // sort right half
        int i = lo, j = mid + 1, w = lo;
        while (i <= mid && j <= hi)         // merge: smaller head wins
            tmp[w++] = a[i] <= a[j] ? a[i++] : a[j++];
        while (i <= mid) tmp[w++] = a[i++];
        while (j <= hi) tmp[w++] = a[j++];
        System.arraycopy(tmp, lo, a, lo, hi - lo + 1);
    }

    // Variation 2: quicksort with a RANDOM pivot — average O(n log n), in
    // place. The shuffle-by-randomness is what defuses adversarial inputs
    // (LeetCode's tests include the sorted + all-equal killers).
    private static final Random RAND = new Random();

    public static void quickSort(int[] a) { quickSort(a, 0, a.length - 1); }

    private static void quickSort(int[] a, int lo, int hi) {
        if (lo >= hi) return;
        // three-way partition (Dutch flag) so runs of equal pivots
        // don't degrade to O(n²)
        int pivot = a[lo + RAND.nextInt(hi - lo + 1)];
        int lt = lo, gt = hi, i = lo;
        while (i <= gt) {
            if (a[i] < pivot) swap(a, lt++, i++);
            else if (a[i] > pivot) swap(a, i, gt--);
            else i++;                       // equal: leave in the middle band
        }
        quickSort(a, lo, lt - 1);           // strictly-less zone
        quickSort(a, gt + 1, hi);           // strictly-greater zone
    }

    private static void swap(int[] a, int i, int j) {
        int t = a[i]; a[i] = a[j]; a[j] = t;
    }

    // Variation 3: heap sort — O(n log n) worst case, O(1) extra, not stable.
    public static void heapSort(int[] a) {
        int n = a.length;
        for (int i = n / 2 - 1; i >= 0; i--)   // heapify: bottom-up sift-downs
            siftDown(a, i, n);
        for (int end = n - 1; end > 0; end--) {
            swap(a, 0, end);                   // max to its final slot
            siftDown(a, 0, end);               // restore heap on the prefix
        }
    }

    private static void siftDown(int[] a, int i, int size) {
        while (true) {
            int l = 2 * i + 1, r = l + 1, big = i;
            if (l < size && a[l] > a[big]) big = l;
            if (r < size && a[r] > a[big]) big = r;
            if (big == i) return;              // heap property restored
            swap(a, i, big);
            i = big;
        }
    }

    public static void main(String[] args) {
        Random r = new Random(42);
        for (int t = 0; t < 200; t++) {
            int n = r.nextInt(60);
            int[] base = new int[n];
            for (int i = 0; i < n; i++) base[i] = r.nextInt(21) - 10;
            int[] expect = base.clone();
            Arrays.sort(expect);
            int[] m = base.clone(), q = base.clone(), h = base.clone();
            mergeSort(m); quickSort(q); heapSort(h);
            assert Arrays.equals(m, expect) && Arrays.equals(q, expect) && Arrays.equals(h, expect);
        }
        System.out.println("SortAnArray: OK");
    }
}
