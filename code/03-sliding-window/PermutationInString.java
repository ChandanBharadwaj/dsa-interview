import java.util.Arrays;

// LeetCode 567 — Permutation in String
// Pattern: FIXED-size window + frequency counts. "s2 contains a
// permutation of s1" = some window of length |s1| has s1's exact counts.
public class PermutationInString {

    public static boolean checkInclusion(String s1, String s2) {
        int k = s1.length();
        if (k > s2.length()) return false;     // can't fit — fail fast

        // need = target letter counts; window = counts of current k-slice
        int[] need = new int[26];
        int[] window = new int[26];
        for (char c : s1.toCharArray()) need[c - 'a']++;

        for (int i = 0; i < s2.length(); i++) {
            // Slide right edge in: count the new character
            window[s2.charAt(i) - 'a']++;
            // Once the window exceeds size k, evict the char falling off
            // the left edge — O(1) update instead of recounting k chars
            if (i >= k) {
                window[s2.charAt(i - k) - 'a']--;
            }
            // Window is full-size from i == k-1 onward; compare counts.
            // Arrays.equals over 26 ints is O(26) = O(1).
            if (i >= k - 1 && Arrays.equals(window, need)) {
                return true;
            }
        }
        return false;
    }
    // Time O(n * 26) = O(n) for n = |s2| (26-int compare per slide).
    // Space O(1): two fixed 26-slot arrays.

    public static void main(String[] args) {
        assert checkInclusion("ab", "eidbaooo");        // "ba" is a permutation of "ab"
        assert !checkInclusion("ab", "eidboaoo");       // 'b' and 'a' never adjacent
        assert checkInclusion("adc", "dcda");           // "cda"
        assert !checkInclusion("hello", "hi");          // pattern longer than text
        System.out.println("PermutationInString: OK");
    }
}
