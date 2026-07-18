import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WordBreak {

    // dp[i] = "can the first i characters s[0..i) be segmented into dictionary words?"
    // dp[i] is true if SOME earlier true cut j exists where s[j..i) is a word.
    public static boolean wordBreak(String s, List<String> wordDict) {
        Set<String> dict = new HashSet<>(wordDict);   // O(1) word lookups
        int maxLen = 0;                               // longest dictionary word — bounds the inner scan
        for (String w : dict) maxLen = Math.max(maxLen, w.length());

        int n = s.length();
        boolean[] dp = new boolean[n + 1];
        dp[0] = true;                                 // base case: the empty prefix is always "segmented"
        for (int i = 1; i <= n; i++) {                // for every prefix length i...
            // try every possible LAST word s[j..i); no word is longer than maxLen
            for (int j = Math.max(0, i - maxLen); j < i; j++) {
                if (dp[j] && dict.contains(s.substring(j, i))) {
                    dp[i] = true;                     // prefix j was breakable + one more word reaches i
                    break;                            // one witness is enough — stop scanning
                }
            }
        }
        return dp[n];                                 // can we segment the WHOLE string?
    }

    public static void main(String[] args) {
        assert wordBreak("leetcode", Arrays.asList("leet", "code"));
        assert wordBreak("applepenapple", Arrays.asList("apple", "pen"));
        assert !wordBreak("catsandog", Arrays.asList("cats", "dog", "sand", "and", "cat"));
        System.out.println("WordBreak: OK");
    }
}
