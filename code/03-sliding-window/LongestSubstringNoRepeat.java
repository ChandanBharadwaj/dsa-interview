import java.util.Arrays;

// LeetCode 3 — Longest Substring Without Repeating Characters
// Pattern: variable-size window. Invariant: the window [left..right]
// never contains a repeated character.
public class LongestSubstringNoRepeat {

    public static int lengthOfLongestSubstring(String s) {
        // last[c] = most recent index where character c appeared; -1 = never.
        // An int[128] covers ASCII and beats a HashMap on constant factors.
        int[] last = new int[128];
        Arrays.fill(last, -1);

        int best = 0;
        int left = 0;                          // window start
        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);
            // If c was last seen INSIDE the current window, the invariant
            // would break — jump left just past that old occurrence.
            // (If last[c] < left, the old copy is already outside: ignore it.)
            if (last[c] >= left) {
                left = last[c] + 1;
            }
            last[c] = right;                   // record newest position of c
            // Window [left..right] is now duplicate-free — measure it
            best = Math.max(best, right - left + 1);
        }
        return best;
    }
    // Time O(n): right scans once; left only ever jumps forward.
    // Space O(1): fixed 128-slot array regardless of input size.

    public static void main(String[] args) {
        assert lengthOfLongestSubstring("abcabcbb") == 3;   // "abc"
        assert lengthOfLongestSubstring("bbbbb") == 1;      // "b"
        assert lengthOfLongestSubstring("pwwkew") == 3;     // "wke"
        assert lengthOfLongestSubstring("") == 0;
        System.out.println("LongestSubstringNoRepeat: OK");
    }
}
