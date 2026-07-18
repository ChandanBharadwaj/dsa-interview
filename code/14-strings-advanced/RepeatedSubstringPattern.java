/**
 * LeetCode 459 — Repeated Substring Pattern. Divisor-tiling brute force
 * and the (s+s) doubling trick. Mirrors pages/14-strings-advanced.html.
 */
public class RepeatedSubstringPattern {

    public static boolean repeatedSubstringPatternBrute(String s) {
        int n = s.length();
        for (int len = 1; len <= n / 2; len++) {
            if (n % len != 0) continue;              // must tile the string exactly
            String unit = s.substring(0, len);
            boolean ok = true;
            for (int i = len; i < n && ok; i += len)
                ok = s.regionMatches(i, unit, 0, len);   // compare each tile
            if (ok) return true;
        }
        return false;
    }

    public static boolean repeatedSubstringPattern(String s) {
        // Slick fact: s is periodic  <=>  s appears inside (s+s) with both
        // endpoints strictly inside — i.e. at an index other than 0 and n.
        // Chopping the first and last character removes those trivial hits.
        String doubled = (s + s).substring(1, 2 * s.length() - 1);
        return doubled.contains(s);
    }

    public static void main(String[] args) {
        assert repeatedSubstringPattern("abab");
        assert !repeatedSubstringPattern("aba");
        assert repeatedSubstringPattern("abcabcabc");
        assert !repeatedSubstringPattern("a");
        assert repeatedSubstringPatternBrute("abab") && !repeatedSubstringPatternBrute("aba");
        System.out.println("RepeatedSubstringPattern: OK");
    }
}
