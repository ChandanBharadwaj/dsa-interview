/**
 * LeetCode 647 — Palindromic Substrings. Expand-around-center, counting
 * one palindrome per successful expansion step.
 * Mirrors pages/14-strings-advanced.html.
 */
public class PalindromicSubstrings {

    public static int countSubstrings(String s) {
        int count = 0;
        for (int center = 0; center < s.length(); center++) {
            count += countFrom(s, center, center);     // odd-length palindromes
            count += countFrom(s, center, center + 1); // even-length palindromes
        }
        return count;
    }

    // Each successful expansion step IS one more palindrome — count them all.
    private static int countFrom(String s, int l, int r) {
        int count = 0;
        while (l >= 0 && r < s.length() && s.charAt(l) == s.charAt(r)) {
            count++;                                   // s[l..r] is a palindrome
            l--;
            r++;
        }
        return count;
    }

    public static void main(String[] args) {
        assert countSubstrings("abc") == 3;
        assert countSubstrings("aaa") == 6;
        assert countSubstrings("a") == 1;
        assert countSubstrings("abba") == 6;   // a,b,b,a,bb,abba
        System.out.println("PalindromicSubstrings: OK");
    }
}
