import java.util.*;

public class FindAllAnagrams {

    // Fixed window of length |p| sliding over s, comparing letter histograms.
    // `matches` counts how many of the 26 letters currently agree, so each
    // slide is O(1) instead of O(26).
    public static List<Integer> findAnagrams(String s, String p) {
        List<Integer> res = new ArrayList<>();
        if (p.length() > s.length()) return res;

        int[] need = new int[26], have = new int[26];
        for (int i = 0; i < p.length(); i++) {
            need[p.charAt(i) - 'a']++;
            have[s.charAt(i) - 'a']++;
        }
        int matches = 0;
        for (int c = 0; c < 26; c++)
            if (need[c] == have[c]) matches++;

        for (int i = p.length(); ; i++) {
            if (matches == 26) res.add(i - p.length());   // window is an anagram
            if (i == s.length()) break;

            int in = s.charAt(i) - 'a';                   // letter entering
            if (have[in] == need[in]) matches--;          // was agreeing, changes
            have[in]++;
            if (have[in] == need[in]) matches++;          // agrees again?

            int out = s.charAt(i - p.length()) - 'a';     // letter leaving
            if (have[out] == need[out]) matches--;
            have[out]--;
            if (have[out] == need[out]) matches++;
        }
        return res;
    }

    public static void main(String[] args) {
        assert findAnagrams("cbaebabacd", "abc").equals(Arrays.asList(0, 6));
        assert findAnagrams("abab", "ab").equals(Arrays.asList(0, 1, 2));
        assert findAnagrams("a", "ab").isEmpty();
        System.out.println("FindAllAnagrams: OK");
    }
}
