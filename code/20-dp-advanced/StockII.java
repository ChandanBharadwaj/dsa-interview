public class StockII {

    // State-machine framing (Best Time to Buy and Sell Stock II, unlimited trades).
    // Two states after each day:
    //   hold = max cash while HOLDING a share
    //   free = max cash while NOT holding
    // Transitions each day: stay, buy (free->hold, pay price), or sell (hold->free, gain price).
    public static int maxProfit(int[] prices) {
        int hold = -prices[0];                       // day 0: bought at prices[0]
        int free = 0;                                // day 0: did nothing
        for (int d = 1; d < prices.length; d++) {
            int p = prices[d];
            // Compute both new states from YESTERDAY's values (read before write).
            int newHold = Math.max(hold, free - p);  // keep holding, or buy today
            int newFree = Math.max(free, hold + p);  // keep resting, or sell today
            hold = newHold;
            free = newFree;
        }
        return free;                                 // end without a share — holding one is wasted money
    }

    public static void main(String[] args) {
        assert maxProfit(new int[]{7, 1, 5, 3, 6, 4}) == 7;   // buy1/sell5 + buy3/sell6
        assert maxProfit(new int[]{1, 2, 3, 4, 5}) == 4;      // ride the whole rise
        assert maxProfit(new int[]{7, 6, 4, 3, 1}) == 0;      // never buy
        System.out.println("StockII: OK");
    }
}
