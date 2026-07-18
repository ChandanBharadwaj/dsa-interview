import java.util.*;

public class RansomNote {

    // Count the magazine's letters once, then spend them building the note.
    // A count going negative means the magazine ran out of that letter.
    public static boolean canConstruct(String ransomNote, String magazine) {
        int[] counts = new int[26];                 // lowercase letters only
        for (char c : magazine.toCharArray())
            counts[c - 'a']++;                      // stock the shelf
        for (char c : ransomNote.toCharArray())
            if (--counts[c - 'a'] < 0)              // spend; negative = shortage
                return false;
        return true;
    }

    public static void main(String[] args) {
        assert !canConstruct("a", "b");
        assert !canConstruct("aa", "ab");
        assert canConstruct("aa", "aab");
        System.out.println("RansomNote: OK");
    }
}
