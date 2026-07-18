public class LongestCommonSubsequence {

    // dp[i][j] = length of the LCS of the first i chars of a and the first j chars of b.
    // The master two-string recurrence:
    //   chars match  -> extend the diagonal:      dp[i][j] = dp[i-1][j-1] + 1
    //   no match     -> skip one char, best side: dp[i][j] = max(dp[i-1][j], dp[i][j-1])
    public static int longestCommonSubsequence(String a, String b) {
        int m = a.length(), n = b.length();
        int[][] dp = new int[m + 1][n + 1];        // row 0 / col 0 = empty prefix = 0 (free base case)
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (a.charAt(i - 1) == b.charAt(j - 1)) {
                    // Last chars match: they surely belong to some LCS — count them, shrink both.
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    // They can't both end the LCS: drop a's last char OR b's — keep the better world.
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[m][n];                           // LCS of the two full strings
    }

    public static void main(String[] args) {
        assert longestCommonSubsequence("abcde", "ace") == 3;      // "ace"
        assert longestCommonSubsequence("abc", "abc") == 3;
        assert longestCommonSubsequence("abc", "def") == 0;
        assert longestCommonSubsequence("ABCBDAB", "BDCAB") == 4;  // "BCAB" — the page's worked table
        System.out.println("LongestCommonSubsequence: OK");
    }
}
