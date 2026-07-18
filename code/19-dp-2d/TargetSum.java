import java.util.*;

public class TargetSum {

    // Assign + or - to every number to hit target. Let P = the positives.
    // P - (total - P) = target  =>  P = (total + target) / 2 — so it's
    // "count subsets summing to P": 0/1 knapsack counting.
    public static int findTargetSumWays(int[] nums, int target) {
        int total = 0;
        for (int x : nums) total += x;
        // Infeasible: parity mismatch, or |target| beyond total.
        if ((total + target) % 2 != 0 || total + target < 0) return 0;
        int p = (total + target) / 2;

        int[] dp = new int[p + 1];              // dp[s] = subsets summing to s
        dp[0] = 1;
        for (int x : nums)
            for (int s = p; s >= x; s--)        // backwards: each num used once
                dp[s] += dp[s - x];
        return dp[p];
    }

    public static void main(String[] args) {
        assert findTargetSumWays(new int[]{1, 1, 1, 1, 1}, 3) == 5;
        assert findTargetSumWays(new int[]{1}, 1) == 1;
        assert findTargetSumWays(new int[]{1}, 2) == 0;
        assert findTargetSumWays(new int[]{0, 0, 1}, 1) == 4;   // zeros double ways
        assert findTargetSumWays(new int[]{1, 2, 1}, 0) == 2;   // parity ok: +1-2+1, -1+2-1
        System.out.println("TargetSum: OK");
    }
}
