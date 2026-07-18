public class BurstBalloons {

    // Interval DP with the key inversion: pick the LAST balloon to burst in each range.
    // Pad with 1s: balloons[0..n+1], with virtual 1-balloons at both ends.
    // dp[l][r] = max coins from bursting EVERYTHING strictly between walls l and r.
    // If balloon k pops LAST inside (l,r), its neighbors at that moment are exactly l and r
    // (everything else in the range is already gone) — that's what makes subproblems independent.
    public static int maxCoins(int[] nums) {
        int n = nums.length;
        int[] b = new int[n + 2];                       // padded array
        b[0] = 1; b[n + 1] = 1;                         // virtual boundary balloons
        for (int i = 0; i < n; i++) b[i + 1] = nums[i];

        int[][] dp = new int[n + 2][n + 2];             // defaults to 0: empty ranges score 0
        for (int len = 2; len <= n + 1; len++) {        // solve small gaps first (range length = r-l)
            for (int l = 0; l + len <= n + 1; l++) {
                int r = l + len;
                for (int k = l + 1; k < r; k++) {       // choose which balloon pops LAST in (l,r)
                    int coins = dp[l][k]                // first clear everything left of k...
                              + dp[k][r]                // ...then everything right of k...
                              + b[l] * b[k] * b[r];     // ...then k pops between walls l and r
                    dp[l][r] = Math.max(dp[l][r], coins);
                }
            }
        }
        return dp[0][n + 1];                            // burst everything between the two virtual walls
    }

    public static void main(String[] args) {
        assert maxCoins(new int[]{3, 1, 5, 8}) == 167;  // burst 1,5,3,8 -> 15+120+24+8
        assert maxCoins(new int[]{1, 5}) == 10;         // 1*1*5 + 1*5*1
        assert maxCoins(new int[]{7}) == 7;
        System.out.println("BurstBalloons: OK");
    }
}
