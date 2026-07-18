public class CharacterReplacement {

    // Window invariant: (window size) − (count of its most frequent letter)
    // = letters we'd have to replace. Legal while that's ≤ k.
    public static int characterReplacement(String s, int k) {
        int[] count = new int[26];
        int best = 0, left = 0, maxFreq = 0;
        for (int right = 0; right < s.length(); right++) {
            maxFreq = Math.max(maxFreq, ++count[s.charAt(right) - 'A']);
            // Too many replacements needed? Slide (not collapse) the window.
            // maxFreq is deliberately NOT recomputed on shrink: a stale
            // (too-high) maxFreq only makes the check lenient, and the best
            // answer was already recorded when maxFreq was genuinely that high.
            if (right - left + 1 - maxFreq > k)
                count[s.charAt(left++) - 'A']--;
            best = Math.max(best, right - left + 1);
        }
        return best;
    }

    public static void main(String[] args) {
        assert characterReplacement("ABAB", 2) == 4;
        assert characterReplacement("AABABBA", 1) == 4;
        assert characterReplacement("AAAA", 0) == 4;
        assert characterReplacement("ABCDE", 1) == 2;
        System.out.println("CharacterReplacement: OK");
    }
}
