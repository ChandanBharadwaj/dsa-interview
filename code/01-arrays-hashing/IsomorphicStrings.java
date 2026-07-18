import java.util.*;

public class IsomorphicStrings {

    // Two maps enforce the substitution in BOTH directions: s->t must be a
    // function (one image per letter) AND injective (no two letters sharing
    // an image). One map alone misses the second half — "badc" vs "baba".
    public static boolean isIsomorphic(String s, String t) {
        Map<Character, Character> fwd = new HashMap<>();
        Map<Character, Character> back = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            char a = s.charAt(i), b = t.charAt(i);
            Character seenB = fwd.putIfAbsent(a, b);   // existing image of a, if any
            Character seenA = back.putIfAbsent(b, a);  // existing preimage of b
            if (seenB != null && seenB != b) return false;  // a mapped elsewhere before
            if (seenA != null && seenA != a) return false;  // b already claimed
        }
        return true;
    }

    public static void main(String[] args) {
        assert isIsomorphic("egg", "add");
        assert !isIsomorphic("foo", "bar");
        assert isIsomorphic("paper", "title");
        assert !isIsomorphic("badc", "baba");   // the injectivity trap
        System.out.println("IsomorphicStrings: OK");
    }
}
