// LeetCode 5 — Longest Palindromic Substring
// Expand-around-center: every palindrome has a center — either one char
// (odd length) or the gap between two chars (even length). There are
// only 2n-1 centers, and expanding each costs at most O(n) -> O(n^2)
// total with O(1) extra space. This is THE interview-expected solution.
public class LongestPalindromicSubstring {

    public static String longestPalindrome(String s) {
        if (s == null || s.isEmpty()) return "";
        int bestStart = 0, bestLen = 1; // any single char is a palindrome
        for (int center = 0; center < s.length(); center++) {
            // Odd-length palindromes: center is the single char at `center`.
            int odd = expand(s, center, center);
            // Even-length: center is the GAP between center and center+1.
            int even = expand(s, center, center + 1);
            int len = Math.max(odd, even);
            if (len > bestLen) {
                bestLen = len;
                // Recover the start index from the center and the length.
                bestStart = center - (len - 1) / 2;
            }
        }
        return s.substring(bestStart, bestStart + bestLen);
    }

    // Grow outward while the two ends match; return the palindrome length.
    private static int expand(String s, int l, int r) {
        while (l >= 0 && r < s.length() && s.charAt(l) == s.charAt(r)) {
            l--; // step the left end outward
            r++; // step the right end outward
        }
        // The loop overshot by one on each side: length = (r-1)-(l+1)+1.
        return r - l - 1;
    }

    public static void main(String[] args) {
        String a = longestPalindrome("babad");
        assert a.equals("bab") || a.equals("aba"); // both are valid answers
        assert longestPalindrome("cbbd").equals("bb"); // even-length center
        assert longestPalindrome("a").equals("a");
        assert longestPalindrome("forgeeksskeegfor").equals("geeksskeeg");
        System.out.println("LongestPalindromicSubstring: OK");
    }
}
