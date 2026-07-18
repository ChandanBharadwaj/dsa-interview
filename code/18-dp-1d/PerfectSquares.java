public class PerfectSquares {

    // Coin Change with the coin set {1, 4, 9, 16, ...}: fewest squares
    // summing to n. dp[x] = 1 + min over squares s <= x of dp[x - s].
    public static int numSquares(int n) {
        int[] dp = new int[n + 1];
        for (int x = 1; x <= n; x++) {
            dp[x] = Integer.MAX_VALUE;
            for (int root = 1; root * root <= x; root++)
                dp[x] = Math.min(dp[x], dp[x - root * root] + 1);
        }
        return dp[n];
    }

    public static void main(String[] args) {
        assert numSquares(12) == 3;      // 4 + 4 + 4
        assert numSquares(13) == 2;      // 4 + 9
        assert numSquares(1) == 1;
        assert numSquares(7) == 4;       // 4+1+1+1
        assert numSquares(16) == 1;
        // Lagrange's four-square theorem: answer is always <= 4
        for (int i = 1; i <= 500; i++)
            assert numSquares(i) <= 4;
        System.out.println("PerfectSquares: OK");
    }
}
