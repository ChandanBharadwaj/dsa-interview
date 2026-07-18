import java.util.Arrays;

public class CoinChange {

    // dp[a] = fewest coins that sum to amount a (unbounded: each coin reusable).
    // Recurrence: dp[a] = 1 + min over coins c of dp[a - c].
    public static int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1);            // "infinity" sentinel: no way found yet
        dp[0] = 0;                              // base case: zero coins make amount 0
        for (int a = 1; a <= amount; a++) {     // solve every amount from small to large
            for (int c : coins) {
                if (c <= a && dp[a - c] + 1 < dp[a]) {
                    // Use one coin c on top of the best solution for the remainder.
                    dp[a] = dp[a - c] + 1;
                }
            }
        }
        // If dp[amount] still holds the sentinel, the amount is unreachable.
        return dp[amount] > amount ? -1 : dp[amount];
    }

    public static void main(String[] args) {
        assert coinChange(new int[]{1, 2, 5}, 11) == 3;  // 5+5+1
        assert coinChange(new int[]{2}, 3) == -1;        // odd amount, only even coins
        assert coinChange(new int[]{1, 3, 4}, 6) == 2;   // 3+3 — greedy (4+1+1 = 3 coins) is WRONG
        assert coinChange(new int[]{1}, 0) == 0;
        System.out.println("CoinChange: OK");
    }
}
