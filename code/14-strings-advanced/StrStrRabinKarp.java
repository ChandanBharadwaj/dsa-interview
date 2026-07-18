// LeetCode 28 again — Rabin-Karp version.
// Polynomial rolling hash: after hashing the first window, every next
// m-length window of the text is hashed in O(1) (remove left char, add
// right char). Compare hashes; verify char-by-char on a hash hit,
// because hashes CAN collide. Expected O(n + m).
public class StrStrRabinKarp {

    public static int strStr(String haystack, String needle) {
        int n = haystack.length(), m = needle.length();
        if (m == 0) return 0;
        if (m > n) return -1;

        final long MOD = 1_000_000_007L; // large prime keeps collisions rare
        final long BASE = 31;            // treat chars as digits in base 31

        // Highest power of BASE inside a window: BASE^(m-1) mod MOD.
        long pow = 1;
        for (int i = 0; i < m - 1; i++) pow = pow * BASE % MOD;

        // Hash of the needle and of the first text window.
        long target = 0, window = 0;
        for (int i = 0; i < m; i++) {
            target = (target * BASE + needle.charAt(i)) % MOD;
            window = (window * BASE + haystack.charAt(i)) % MOD;
        }

        for (int start = 0; ; start++) {
            // Hashes equal? Verify for real — collisions are possible.
            if (window == target
                    && haystack.regionMatches(start, needle, 0, m))
                return start;
            if (start + m >= n) return -1; // no window left to slide into
            // Roll: remove the leftmost char, shift, append the next char.
            long out = haystack.charAt(start) * pow % MOD;
            window = (window - out + MOD) % MOD;  // +MOD avoids going negative
            window = (window * BASE + haystack.charAt(start + m)) % MOD;
        }
    }

    public static void main(String[] args) {
        assert strStr("sadbutsad", "sad") == 0;
        assert strStr("leetcode", "leeto") == -1;
        assert strStr("hello", "ll") == 2;
        assert strStr("aaaaa", "bba") == -1;
        System.out.println("StrStrRabinKarp: OK");
    }
}
