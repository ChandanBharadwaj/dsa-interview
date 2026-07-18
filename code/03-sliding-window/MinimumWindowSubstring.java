// LeetCode 76 — Minimum Window Substring (hard)
// Pattern: variable window, "expand until valid, then shrink while valid".
// The `required` counter makes validity checks O(1) instead of O(26).
public class MinimumWindowSubstring {

    public static String minWindow(String s, String t) {
        if (t.length() > s.length()) return "";

        // need[c] > 0  => window still owes that many copies of c.
        // need[c] < 0  => window holds surplus copies of c.
        int[] need = new int[128];
        for (char c : t.toCharArray()) need[c]++;
        int required = t.length();      // total characters still missing

        int bestLen = Integer.MAX_VALUE, bestStart = 0;
        int left = 0;
        for (int right = 0; right < s.length(); right++) {
            char in = s.charAt(right);
            // Taking `in`: it only fills a real gap if we still owed one
            if (need[in] > 0) required--;
            need[in]--;                 // may go negative = surplus copy

            // Window covers all of t — shrink from the left while it still does
            while (required == 0) {
                // Record the window if it's the smallest so far
                if (right - left + 1 < bestLen) {
                    bestLen = right - left + 1;
                    bestStart = left;
                }
                // Give the leftmost char back before moving left forward
                char out = s.charAt(left);
                need[out]++;
                // If that char is now owed again, the window just broke —
                // exit the shrink loop and go back to expanding
                if (need[out] > 0) required++;
                left++;
            }
        }
        return bestLen == Integer.MAX_VALUE
                ? ""
                : s.substring(bestStart, bestStart + bestLen);
    }
    // Time O(|s| + |t|): each pointer moves forward at most |s| times.
    // Space O(1): fixed 128-slot array.

    public static void main(String[] args) {
        assert minWindow("ADOBECODEBANC", "ABC").equals("BANC");
        assert minWindow("a", "a").equals("a");
        assert minWindow("a", "aa").equals("");     // not enough a's anywhere
        assert minWindow("ab", "b").equals("b");
        System.out.println("MinimumWindowSubstring: OK");
    }
}
