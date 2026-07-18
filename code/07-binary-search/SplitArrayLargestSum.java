import java.util.*;

public class SplitArrayLargestSum {

    // Search on the answer: "can we split into <= k parts, each summing
    // <= cap?" is monotonic in cap. Find the smallest feasible cap.
    public static int splitArray(int[] nums, int k) {
        int lo = 0, hi = 0;
        for (int x : nums) {
            lo = Math.max(lo, x);   // cap can't be below the largest element
            hi += x;                // one part holding everything
        }
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (canSplit(nums, k, mid))
                hi = mid;           // feasible -> try a tighter cap
            else
                lo = mid + 1;       // infeasible -> must allow more per part
        }
        return lo;
    }

    // Greedy feasibility check: pack left to right, cut only when forced.
    // Fewest possible parts under the cap — if even that exceeds k, fail.
    private static boolean canSplit(int[] nums, int k, int cap) {
        int parts = 1;
        long sum = 0;
        for (int x : nums) {
            if (sum + x > cap) {
                parts++;            // forced cut before x
                sum = 0;
                if (parts > k) return false;
            }
            sum += x;
        }
        return true;
    }

    public static void main(String[] args) {
        assert splitArray(new int[]{7, 2, 5, 10, 8}, 2) == 18;   // [7,2,5] [10,8]
        assert splitArray(new int[]{1, 2, 3, 4, 5}, 2) == 9;     // [1,2,3] [4,5]
        assert splitArray(new int[]{1, 4, 4}, 3) == 4;
        assert splitArray(new int[]{10}, 1) == 10;
        Random r = new Random(13);
        for (int t = 0; t < 200; t++) {          // cross-check vs DP brute force
            int n = 1 + r.nextInt(8);
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = r.nextInt(20);
            int k = 1 + r.nextInt(n);
            assert splitArray(a, k) == bruteDP(a, k);
        }
        System.out.println("SplitArrayLargestSum: OK");
    }

    // O(n²k) interval DP used only as a test oracle.
    private static int bruteDP(int[] a, int k) {
        int n = a.length;
        long[] pre = new long[n + 1];
        for (int i = 0; i < n; i++) pre[i + 1] = pre[i] + a[i];
        long[][] dp = new long[k + 1][n + 1];
        for (long[] row : dp) Arrays.fill(row, Long.MAX_VALUE);
        dp[0][0] = 0;
        for (int parts = 1; parts <= k; parts++)
            for (int i = 1; i <= n; i++)
                for (int j = parts - 1; j < i; j++)
                    if (dp[parts - 1][j] != Long.MAX_VALUE)
                        dp[parts][i] = Math.min(dp[parts][i],
                                Math.max(dp[parts - 1][j], pre[i] - pre[j]));
        long best = Long.MAX_VALUE;
        for (int parts = 1; parts <= k; parts++)
            best = Math.min(best, dp[parts][n]);
        return (int) best;
    }
}
