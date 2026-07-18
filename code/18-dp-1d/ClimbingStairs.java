public class ClimbingStairs {

    // ---------- Top-down: recursion + memo (the "remembering" upgrade) ----------
    public static int climbMemo(int n) {
        int[] memo = new int[n + 1];          // memo[i] = ways to reach step i (0 = not computed yet)
        return waysTo(n, memo);
    }

    private static int waysTo(int i, int[] memo) {
        if (i <= 2) return i;                 // base cases: 1 way to step 1, 2 ways to step 2
        if (memo[i] != 0) return memo[i];     // already solved this subproblem? reuse the answer
        // Last move was either a 1-step from i-1 or a 2-step from i-2 — add both worlds
        memo[i] = waysTo(i - 1, memo) + waysTo(i - 2, memo);
        return memo[i];
    }

    // ---------- Bottom-up: tabulation (same recurrence, loop instead of recursion) ----------
    public static int climbTab(int n) {
        if (n <= 2) return n;
        int[] dp = new int[n + 1];            // dp[i] = number of distinct ways to reach step i
        dp[1] = 1;                            // base case: one way to stand on step 1
        dp[2] = 2;                            // base case: 1+1 or 2
        for (int i = 3; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];    // arrive via a 1-step or via a 2-step
        }
        return dp[n];
    }

    // ---------- Space-optimized: we only ever look back 2 cells, so keep 2 variables ----------
    public static int climb(int n) {
        if (n <= 2) return n;
        int prev2 = 1, prev1 = 2;             // ways to reach steps 1 and 2
        for (int i = 3; i <= n; i++) {
            int cur = prev1 + prev2;          // the recurrence, without the array
            prev2 = prev1;                    // slide the two-cell window forward
            prev1 = cur;
        }
        return prev1;
    }

    public static void main(String[] args) {
        assert climbMemo(2) == 2 && climbTab(2) == 2 && climb(2) == 2;
        assert climbMemo(3) == 3 && climbTab(3) == 3 && climb(3) == 3;
        assert climbMemo(5) == 8 && climbTab(5) == 8 && climb(5) == 8;
        assert climb(45) == 1836311903;       // fits in int; n=46 would overflow
        System.out.println("ClimbingStairs: OK");
    }
}
