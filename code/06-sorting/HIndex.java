import java.util.*;

public class HIndex {

    // Variation 1: sort descending (via ascending + index math) and find the
    // largest h where the h-th best paper has ≥ h citations.
    public static int hIndexSort(int[] citations) {
        Arrays.sort(citations);                  // ascending
        int n = citations.length;
        for (int i = 0; i < n; i++) {
            int papersAtLeast = n - i;           // papers with ≥ citations[i]
            if (citations[i] >= papersAtLeast)   // first time it qualifies,
                return papersAtLeast;            // that count IS the h-index
        }
        return 0;
    }

    // Variation 2: counting buckets — h never exceeds n, so citations above
    // n can be clamped into one bucket. O(n) time, no sort.
    public static int hIndex(int[] citations) {
        int n = citations.length;
        int[] buckets = new int[n + 1];
        for (int c : citations)
            buckets[Math.min(c, n)]++;           // clamp: 1000 citations acts like n
        int papers = 0;
        for (int h = n; h >= 0; h--) {           // walk h downward,
            papers += buckets[h];                // accumulating papers with ≥ h
            if (papers >= h) return h;           // first h that h papers support
        }
        return 0;
    }

    public static void main(String[] args) {
        assert hIndex(new int[]{3, 0, 6, 1, 5}) == 3;
        assert hIndex(new int[]{1, 3, 1}) == 1;
        assert hIndex(new int[]{0}) == 0;
        assert hIndex(new int[]{100}) == 1;
        Random r = new Random(5);
        for (int t = 0; t < 300; t++) {          // both variants must agree
            int[] a = new int[1 + r.nextInt(20)];
            for (int i = 0; i < a.length; i++) a[i] = r.nextInt(25);
            assert hIndex(a.clone()) == hIndexSort(a.clone());
        }
        System.out.println("HIndex: OK");
    }
}
