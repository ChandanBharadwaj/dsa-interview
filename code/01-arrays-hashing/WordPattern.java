import java.util.*;

public class WordPattern {

    // Isomorphic Strings scaled up: letters map to whole words, and the
    // bijection must again hold in both directions.
    public static boolean wordPattern(String pattern, String s) {
        String[] words = s.split(" ");
        if (words.length != pattern.length()) return false;  // lengths must agree first

        Map<Character, String> letterToWord = new HashMap<>();
        Map<String, Character> wordToLetter = new HashMap<>();
        for (int i = 0; i < pattern.length(); i++) {
            char p = pattern.charAt(i);
            String w = words[i];
            String seenW = letterToWord.putIfAbsent(p, w);
            Character seenP = wordToLetter.putIfAbsent(w, p);
            if (seenW != null && !seenW.equals(w)) return false;  // letter reused differently
            if (seenP != null && seenP != p) return false;        // word already claimed
        }
        return true;
    }

    public static void main(String[] args) {
        assert wordPattern("abba", "dog cat cat dog");
        assert !wordPattern("abba", "dog cat cat fish");
        assert !wordPattern("aaaa", "dog cat cat dog");
        assert !wordPattern("abba", "dog dog dog dog");  // needs the reverse map
        System.out.println("WordPattern: OK");
    }
}
