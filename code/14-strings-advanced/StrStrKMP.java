// LeetCode 28 — Find the Index of the First Occurrence in a String
// KMP version: O(n + m) worst case. The LPS table ("longest proper
// prefix that is also a suffix") tells us, on a mismatch, how much of
// what we already matched is REUSABLE — so the text pointer i never
// moves backwards.
public class StrStrKMP {

    public static int strStr(String haystack, String needle) {
        int n = haystack.length(), m = needle.length();
        if (m == 0) return 0;
        int[] lps = buildLps(needle);
        int j = 0; // how many pattern chars are currently matched
        for (int i = 0; i < n; i++) {           // i NEVER decreases
            // On mismatch, fall back through the LPS chain: keep the
            // longest matched prefix that is still consistent.
            while (j > 0 && haystack.charAt(i) != needle.charAt(j))
                j = lps[j - 1];
            if (haystack.charAt(i) == needle.charAt(j)) j++; // extend match
            if (j == m) return i - m + 1;       // full match; return its start
        }
        return -1; // pattern never fully matched
    }

    // lps[i] = length of the longest proper prefix of needle[0..i] that
    // is ALSO a suffix of it. Built with the same fall-back trick.
    static int[] buildLps(String p) {
        int[] lps = new int[p.length()];
        int len = 0;                       // current matched prefix length
        for (int i = 1; i < p.length(); i++) {
            while (len > 0 && p.charAt(i) != p.charAt(len))
                len = lps[len - 1];        // shrink to the next-best prefix
            if (p.charAt(i) == p.charAt(len)) len++; // prefix grows by one
            lps[i] = len;
        }
        return lps;
    }

    public static void main(String[] args) {
        assert strStr("sadbutsad", "sad") == 0;
        assert strStr("leetcode", "leeto") == -1;
        assert strStr("ababcaababcabc", "ababcabc") == 6;
        assert java.util.Arrays.equals(buildLps("ababc"), new int[]{0, 0, 1, 2, 0});
        System.out.println("StrStrKMP: OK");
    }
}
