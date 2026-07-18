public class EditDistance {

    // dp[i][j] = minimum operations (insert/delete/replace) to turn the first i chars
    // of word1 into the first j chars of word2. The three ops are the three neighbors:
    //   delete  word1[i-1]           -> dp[i-1][j]   + 1   (cell above)
    //   insert  word2[j-1] into w1   -> dp[i][j-1]   + 1   (cell left)
    //   replace word1[i-1]->w2[j-1]  -> dp[i-1][j-1] + 1   (diagonal)
    public static int minDistance(String word1, String word2) {
        int m = word1.length(), n = word2.length();
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 0; i <= m; i++) dp[i][0] = i;   // turn i chars into "" -> i deletions
        for (int j = 0; j <= n; j++) dp[0][j] = j;   // turn "" into j chars -> j insertions
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];     // last chars agree: free move along the diagonal
                } else {
                    // 1 + cheapest of the three edits (delete, insert, replace)
                    dp[i][j] = 1 + Math.min(dp[i - 1][j - 1],
                                   Math.min(dp[i - 1][j], dp[i][j - 1]));
                }
            }
        }
        return dp[m][n];
    }

    public static void main(String[] args) {
        assert minDistance("horse", "ros") == 3;          // horse->rorse->rose->ros
        assert minDistance("intention", "execution") == 5;
        assert minDistance("", "abc") == 3;
        assert minDistance("same", "same") == 0;
        System.out.println("EditDistance: OK");
    }
}
