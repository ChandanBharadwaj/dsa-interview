import java.util.Arrays;

/**
 * LeetCode 242 — Valid Anagram. Two variations from the page:
 * sort-both O(n log n) vs a single +1/-1 count array O(n).
 * Mirrors pages/01-arrays-hashing.html.
 */
public class ValidAnagram {

    public static boolean isAnagramSort(String s, String t) {
        if (s.length() != t.length()) return false;   // cheap early exit
        char[] a = s.toCharArray(), b = t.toCharArray();
        Arrays.sort(a);                               // anagrams sort to the
        Arrays.sort(b);                               // exact same char sequence
        return Arrays.equals(a, b);
    }

    public static boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) return false;
        int[] count = new int[26];                    // 26 lowercase letters
        for (int i = 0; i < s.length(); i++) {
            count[s.charAt(i) - 'a']++;               // +1 for every letter of s
            count[t.charAt(i) - 'a']--;               // -1 for every letter of t
        }
        for (int c : count)
            if (c != 0) return false;                 // any imbalance -> not an anagram
        return true;
    }

    public static void main(String[] args) {
        assert isAnagram("anagram", "nagaram");
        assert !isAnagram("rat", "car");
        assert !isAnagram("ab", "a");
        assert isAnagramSort("listen", "silent");
        assert !isAnagramSort("aacc", "ccac");
        System.out.println("ValidAnagram: OK");
    }
}
