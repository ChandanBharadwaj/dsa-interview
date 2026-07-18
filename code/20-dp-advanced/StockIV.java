import java.util.Arrays;

/**
 * LeetCode 188 — Best Time to Buy and Sell Stock IV. The hold/free state
 * machine indexed by transaction count; collapses to greedy when k is
 * effectively unlimited. Mirrors pages/20-dp-advanced.html.
 */
public class StockIV {

    public static int maxProfit(int k, int[] prices) {
        int n = prices.length;
        if (n == 0 || k == 0) return 0;
        // k >= n/2 means "effectively unlimited" — collapse to Stock II's greedy
        // (a transaction needs 2 days, so more than n/2 can never all be used).
        if (k >= n / 2) {
            int profit = 0;
            for (int i = 1; i < n; i++)
                if (prices[i] > prices[i - 1]) profit += prices[i] - prices[i - 1];
            return profit;
        }

        // hold[t] = best cash while holding, having STARTED t transactions.
        // free[t] = best cash while not holding, having COMPLETED t transactions.
        int[] hold = new int[k + 1];
        int[] free = new int[k + 1];
        Arrays.fill(hold, Integer.MIN_VALUE / 2);   // can't hold before buying
        for (int p : prices) {
            for (int t = k; t >= 1; t--) {          // backward: each day adds <=1 action per t
                free[t] = Math.max(free[t], hold[t] + p);     // sell: completes transaction t
                hold[t] = Math.max(hold[t], free[t - 1] - p); // buy: starts transaction t
            }
        }
        return free[k];                              // best with <= k completed transactions
    }

    public static void main(String[] args) {
        assert maxProfit(2, new int[]{3, 2, 6, 5, 0, 3}) == 7;
        assert maxProfit(2, new int[]{2, 4, 1}) == 2;
        assert maxProfit(1, new int[]{7, 6, 4, 3, 1}) == 0;
        assert maxProfit(100, new int[]{1, 2, 3, 4, 5}) == 4;   // unlimited collapse
        System.out.println("StockIV: OK");
    }
}
