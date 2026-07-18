/**
 * LeetCode 121 — Best Time to Buy and Sell Stock. Two variations:
 * brute-force pairs O(n^2) vs min-so-far one pass O(n)/O(1).
 * Mirrors pages/03-sliding-window.html.
 */
public class BestTimeToBuySellStock {

    public static int maxProfitBrute(int[] prices) {
        int best = 0;
        for (int buy = 0; buy < prices.length; buy++)
            for (int sell = buy + 1; sell < prices.length; sell++)
                best = Math.max(best, prices[sell] - prices[buy]);
        return best;
    }

    public static int maxProfit(int[] prices) {
        int minSoFar = Integer.MAX_VALUE;   // cheapest buy seen up to today
        int best = 0;
        for (int p : prices) {
            minSoFar = Math.min(minSoFar, p);      // today could be the new best buy...
            best = Math.max(best, p - minSoFar);   // ...or the best sell against the
        }                                          //    cheapest earlier buy
        return best;
    }

    public static void main(String[] args) {
        assert maxProfit(new int[]{7, 1, 5, 3, 6, 4}) == 5;
        assert maxProfit(new int[]{7, 6, 4, 3, 1}) == 0;
        assert maxProfitBrute(new int[]{2, 4, 1}) == 2;
        assert maxProfit(new int[]{1}) == 0;
        System.out.println("BestTimeToBuySellStock: OK");
    }
}
