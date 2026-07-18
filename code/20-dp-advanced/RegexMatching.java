public class RegexMatching {

    // Full-string match with '.' (any char) and '*' (zero or more of the
    // PRECEDING element). dp[i][j] = does s[0..i) match p[0..j)?
    public static boolean isMatch(String s, String p) {
        int m = s.length(), n = p.length();
        boolean[][] dp = new boolean[m + 1][n + 1];
        dp[0][0] = true;                        // empty matches empty
        // Empty s vs patterns like a*, a*b*: '*' can erase its element.
        for (int j = 2; j <= n; j++)
            if (p.charAt(j - 1) == '*')
                dp[0][j] = dp[0][j - 2];

        for (int i = 1; i <= m; i++)
            for (int j = 1; j <= n; j++) {
                char pc = p.charAt(j - 1);
                if (pc == '*') {
                    char prev = p.charAt(j - 2);
                    dp[i][j] = dp[i][j - 2]     // zero copies of prev
                        || ((prev == '.' || prev == s.charAt(i - 1))
                            && dp[i - 1][j]);   // one more copy consumes s's char
                } else {
                    dp[i][j] = (pc == '.' || pc == s.charAt(i - 1))
                        && dp[i - 1][j - 1];    // plain char: both advance
                }
            }
        return dp[m][n];
    }

    public static void main(String[] args) {
        assert !isMatch("aa", "a");
        assert isMatch("aa", "a*");
        assert isMatch("ab", ".*");
        assert !isMatch("mississippi", "mis*is*p*.");
        assert isMatch("mississippi", "mis*is*ip*.");
        assert isMatch("", "a*b*c*");
        assert isMatch("aab", "c*a*b");
        assert !isMatch("ab", ".*c");
        System.out.println("RegexMatching: OK");
    }
}
