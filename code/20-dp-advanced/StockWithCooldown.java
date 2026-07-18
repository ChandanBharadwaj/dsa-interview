public class StockWithCooldown {

    // Three states per day (the machine on the page's animation):
    //   hold = best cash while holding a share
    //   sold = best cash having sold TODAY (tomorrow is a forced cooldown)
    //   rest = best cash idle and ALLOWED to buy tomorrow
    // Transitions:  rest -> hold (buy),  hold -> sold (sell),  sold -> rest (cooldown ends).
    public static int maxProfit(int[] prices) {
        int hold = -prices[0];                          // day 0: bought
        int sold = Integer.MIN_VALUE / 2;               // day 0: can't have sold yet ("-infinity", halved to avoid overflow)
        int rest = 0;                                   // day 0: did nothing
        for (int d = 1; d < prices.length; d++) {
            int p = prices[d];
            // All three reads use YESTERDAY's values, so compute into temporaries first.
            int newHold = Math.max(hold, rest - p);     // keep holding, or buy (only from rest — not right after a sale!)
            int newSold = hold + p;                     // sell the share we were holding
            int newRest = Math.max(rest, sold);         // idle, or yesterday's cooldown just ended
            hold = newHold; sold = newSold; rest = newRest;
        }
        return Math.max(sold, rest);                    // end not holding (ending in `hold` wastes a purchase)
    }

    public static void main(String[] args) {
        assert maxProfit(new int[]{1, 2, 3, 0, 2}) == 3;   // buy 1, sell 2, cooldown, buy 0, sell 2
        assert maxProfit(new int[]{1}) == 0;
        assert maxProfit(new int[]{1, 2, 4}) == 3;         // one trade: buy 1, sell 4
        assert maxProfit(new int[]{2, 1}) == 0;
        System.out.println("StockWithCooldown: OK");
    }
}
