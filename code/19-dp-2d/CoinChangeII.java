public class CoinChangeII {

    // Count the COMBINATIONS of coins that make `amount` (order doesn't matter).
    // dp[a] = number of ways to build amount a with the coins processed SO FAR.
    // Loop order is the whole trick:
    //   coins OUTER, amounts inner  -> each way is built in one fixed coin order = combinations
    //   amounts outer, coins inner  -> 1+2 and 2+1 both counted = permutations (a different problem!)
    public static int change(int amount, int[] coins) {
        int[] dp = new int[amount + 1];
        dp[0] = 1;                                 // one way to make 0: use no coins
        for (int coin : coins) {                   // commit to coins one at a time...
            for (int a = coin; a <= amount; a++) { // FORWARD loop = unbounded (coin reusable)
                dp[a] += dp[a - coin];             // ways that use at least one more of this coin
            }
        }
        return dp[amount];
    }

    public static void main(String[] args) {
        assert change(5, new int[]{1, 2, 5}) == 4;   // 5, 2+2+1, 2+1+1+1, 1x5
        assert change(3, new int[]{2}) == 0;
        assert change(10, new int[]{10}) == 1;
        System.out.println("CoinChangeII: OK");
    }
}
