public class MaxVowels {

    // Fixed-size window: one vowel counter, updated by the entering and
    // leaving characters only.
    public static int maxVowels(String s, int k) {
        int count = 0;
        for (int i = 0; i < k; i++)             // first window
            if (isVowel(s.charAt(i))) count++;
        int best = count;
        for (int i = k; i < s.length(); i++) {
            if (isVowel(s.charAt(i))) count++;          // entering char
            if (isVowel(s.charAt(i - k))) count--;      // leaving char
            best = Math.max(best, count);
        }
        return best;
    }

    private static boolean isVowel(char c) {
        return c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u';
    }

    public static void main(String[] args) {
        assert maxVowels("abciiidef", 3) == 3;
        assert maxVowels("aeiou", 2) == 2;
        assert maxVowels("leetcode", 3) == 2;
        assert maxVowels("rhythms", 4) == 0;
        System.out.println("MaxVowels: OK");
    }
}
