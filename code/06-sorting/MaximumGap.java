import java.util.*;

public class MaximumGap {

    // Pigeonhole + buckets: with n numbers spread over range R, some adjacent
    // (sorted) gap is ≥ R/(n-1). Make buckets slightly smaller than that and
    // the max gap can never be INSIDE a bucket — only between buckets. So we
    // need only each bucket's min and max: O(n), no full sort.
    public static int maximumGap(int[] nums) {
        int n = nums.length;
        if (n < 2) return 0;
        int lo = Integer.MAX_VALUE, hi = Integer.MIN_VALUE;
        for (int x : nums) {
            lo = Math.min(lo, x);
            hi = Math.max(hi, x);
        }
        if (lo == hi) return 0;                       // all equal

        int bucketSize = Math.max(1, (hi - lo) / (n - 1));
        int bucketCount = (hi - lo) / bucketSize + 1;
        int[] mins = new int[bucketCount], maxs = new int[bucketCount];
        Arrays.fill(mins, Integer.MAX_VALUE);
        Arrays.fill(maxs, Integer.MIN_VALUE);
        for (int x : nums) {
            int b = (x - lo) / bucketSize;            // which bucket x lands in
            mins[b] = Math.min(mins[b], x);
            maxs[b] = Math.max(maxs[b], x);
        }

        int best = 0, prevMax = maxs[0];              // bucket 0 holds `lo`
        for (int b = 1; b < bucketCount; b++) {
            if (mins[b] == Integer.MAX_VALUE) continue;   // empty bucket — skip
            best = Math.max(best, mins[b] - prevMax); // gap across the boundary
            prevMax = maxs[b];
        }
        return best;
    }

    public static void main(String[] args) {
        assert maximumGap(new int[]{3, 6, 9, 1}) == 3;
        assert maximumGap(new int[]{10}) == 0;
        assert maximumGap(new int[]{1, 10000000}) == 9999999;
        Random r = new Random(9);
        for (int t = 0; t < 300; t++) {               // cross-check vs sort
            int[] a = new int[2 + r.nextInt(30)];
            for (int i = 0; i < a.length; i++) a[i] = r.nextInt(1000);
            int[] s = a.clone();
            Arrays.sort(s);
            int brute = 0;
            for (int i = 1; i < s.length; i++) brute = Math.max(brute, s[i] - s[i - 1]);
            assert maximumGap(a) == brute;
        }
        System.out.println("MaximumGap: OK");
    }
}
