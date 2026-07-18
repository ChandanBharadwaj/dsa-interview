/**
 * LeetCode 516 — Longest Palindromic Subsequence. Two routes: LCS with
 * the reverse, and interval DP over [i..j].
 * Mirrors pages/19-dp-2d.html.
 */
public class LongestPalindromicSubseq {

    public static int longestPalindromeSubseqLCS(String s) {
        // A palindromic subsequence of s reads the same in s and reverse(s) —
        // so it IS a common subsequence of the two.
        String r = new StringBuilder(s).reverse().toString();
        return lcs(s, r);
    }

    private static int lcs(String a, String b) {
        int n = a.length(), m = b.length();
        int[][] dp = new int[n + 1][m + 1];
        for (int i = 1; i <= n; i++)
            for (int j = 1; j <= m; j++)
                dp[i][j] = a.charAt(i - 1) == b.charAt(j - 1)
                    ? dp[i - 1][j - 1] + 1
                    : Math.max(dp[i - 1][j], dp[i][j - 1]);
        return dp[n][m];
    }

    public static int longestPalindromeSubseq(String s) {
        int n = s.length();
        // dp[i][j] = longest palindromic subsequence within s[i..j].
        int[][] dp = new int[n][n];
        for (int i = n - 1; i >= 0; i--) {         // i sweeps RIGHT-to-LEFT so
            dp[i][i] = 1;                          // dp[i+1][...] is ready when needed
            for (int j = i + 1; j < n; j++) {
                if (s.charAt(i) == s.charAt(j))
                    // Matching ends wrap the best interior answer
                    dp[i][j] = dp[i + 1][j - 1] + 2;
                else
                    // Drop one end or the other — keep the better
                    dp[i][j] = Math.max(dp[i + 1][j], dp[i][j - 1]);
            }
        }
        return dp[0][n - 1];                       // the whole string
    }

    public static void main(String[] args) {
        assert longestPalindromeSubseq("bbbab") == 4;
        assert longestPalindromeSubseq("cbbd") == 2;
        assert longestPalindromeSubseqLCS("bbbab") == 4;
        assert longestPalindromeSubseqLCS("cbbd") == 2;
        assert longestPalindromeSubseq("a") == 1;
        System.out.println("LongestPalindromicSubseq: OK");
    }
}
