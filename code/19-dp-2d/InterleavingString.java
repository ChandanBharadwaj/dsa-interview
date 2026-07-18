/**
 * LeetCode 97 — Interleaving String. dp[i][j]: can s1[0..i) + s2[0..j)
 * interleave into s3[0..i+j)? Position in s3 is forced to i+j.
 * Mirrors pages/19-dp-2d.html.
 */
public class InterleavingString {

    public static boolean isInterleave(String s1, String s2, String s3) {
        int n = s1.length(), m = s2.length();
        if (n + m != s3.length()) return false;    // lengths must add up exactly

        // dp[i][j] = can s1's first i chars + s2's first j chars interleave
        // into s3's first i+j chars? (The position in s3 is DETERMINED: i+j.)
        boolean[][] dp = new boolean[n + 1][m + 1];
        dp[0][0] = true;                           // empty + empty = empty
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= m; j++) {
                if (i == 0 && j == 0) continue;
                char need = s3.charAt(i + j - 1);  // the character to explain
                // It came from s1's i-th char...
                boolean fromS1 = i > 0 && dp[i - 1][j] && s1.charAt(i - 1) == need;
                // ...or from s2's j-th char.
                boolean fromS2 = j > 0 && dp[i][j - 1] && s2.charAt(j - 1) == need;
                dp[i][j] = fromS1 || fromS2;
            }
        }
        return dp[n][m];
    }

    public static void main(String[] args) {
        assert isInterleave("aabcc", "dbbca", "aadbbcbcac");
        assert !isInterleave("aabcc", "dbbca", "aadbbbaccc");
        assert isInterleave("", "", "");
        assert isInterleave("aa", "ab", "aaba");   // the case that defeats greedy
        assert !isInterleave("a", "b", "abx");
        System.out.println("InterleavingString: OK");
    }
}
