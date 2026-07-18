public class DistinctSubsequences {

    // dp[i][j] = ways to form t's first j chars from s's first i chars.
    // Skip s[i-1] always; additionally use it when it matches t[j-1].
    public static int numDistinct(String s, String t) {
        int m = s.length(), n = t.length();
        long[][] dp = new long[m + 1][n + 1];   // long: counts explode fast
        for (int i = 0; i <= m; i++)
            dp[i][0] = 1;                       // empty t: one way (take nothing)
        for (int i = 1; i <= m; i++)
            for (int j = 1; j <= n; j++) {
                dp[i][j] = dp[i - 1][j];        // drop s's char
                if (s.charAt(i - 1) == t.charAt(j - 1))
                    dp[i][j] += dp[i - 1][j - 1];   // or consume both
            }
        return (int) dp[m][n];
    }

    public static void main(String[] args) {
        assert numDistinct("rabbbit", "rabbit") == 3;
        assert numDistinct("babgbag", "bag") == 5;
        assert numDistinct("abc", "abc") == 1;
        assert numDistinct("abc", "abd") == 0;
        assert numDistinct("aaa", "a") == 3;
        assert numDistinct("", "") == 1;
        System.out.println("DistinctSubsequences: OK");
    }
}
