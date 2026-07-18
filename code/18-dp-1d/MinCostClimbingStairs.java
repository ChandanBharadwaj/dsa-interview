public class MinCostClimbingStairs {

    // dp[i] = cheapest total cost to STAND ON step i (you pay cost[i] when you leave it,
    // so we fold the payment into arriving: dp[i] = cost[i] + min(dp[i-1], dp[i-2])).
    public static int minCostClimbingStairs(int[] cost) {
        int n = cost.length;
        // Only the last two dp values matter — keep two variables instead of an array.
        int prev2 = cost[0];                       // cheapest way to be on step 0
        int prev1 = cost[1];                       // cheapest way to be on step 1
        for (int i = 2; i < n; i++) {
            int cur = cost[i] + Math.min(prev1, prev2);  // arrive from i-1 or i-2, whichever is cheaper
            prev2 = prev1;
            prev1 = cur;
        }
        // The "top" is one past the last step; we can jump there from either of the last two steps.
        return Math.min(prev1, prev2);
    }

    public static void main(String[] args) {
        assert minCostClimbingStairs(new int[]{10, 15, 20}) == 15;
        assert minCostClimbingStairs(new int[]{1, 100, 1, 1, 1, 100, 1, 1, 100, 1}) == 6;
        System.out.println("MinCostClimbingStairs: OK");
    }
}
