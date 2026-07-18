/**
 * LeetCode 877 — Stone Game. Interval DP over the score DIFFERENCE:
 * dp[l][r] = (current player) - (opponent) on piles l..r.
 * Mirrors pages/20-dp-advanced.html.
 */
public class StoneGame {

    public static boolean stoneGame(int[] piles) {
        int n = piles.length;
        int[][] dp = new int[n][n];
        for (int l = n - 1; l >= 0; l--) {
            dp[l][l] = piles[l];                 // one pile: take it, lead by its value
            for (int r = l + 1; r < n; r++) {
                // Take left end: gain piles[l], then opponent plays [l+1..r]
                int takeLeft = piles[l] - dp[l + 1][r];
                // Take right end: gain piles[r], then opponent plays [l..r-1]
                int takeRight = piles[r] - dp[l][r - 1];
                dp[l][r] = Math.max(takeLeft, takeRight);
            }
        }
        return dp[0][n - 1] > 0;                 // Alice leads on the full row
    }

    public static void main(String[] args) {
        assert stoneGame(new int[]{5, 3, 4, 5});
        assert stoneGame(new int[]{3, 7, 2, 3});
        assert stoneGame(new int[]{7, 8, 8, 10});   // even count: Alice always wins
        System.out.println("StoneGame: OK");
    }
}
