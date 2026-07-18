import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// LeetCode 49 — Group Anagrams
// Pattern: group-by-canonical-key. All anagrams share one canonical
// form; use it as a HashMap key and the groups build themselves.
public class GroupAnagrams {

    public static List<List<String>> groupAnagrams(String[] strs) {
        // canonical key -> all words that share it (i.e. one anagram group)
        Map<String, List<String>> groups = new HashMap<>();
        for (String s : strs) {
            // Canonical key = letter counts. Anagrams have identical counts,
            // and building this is O(L) vs O(L log L) for a sorted-chars key.
            int[] count = new int[26];
            for (char c : s.toCharArray()) count[c - 'a']++;
            // Serialize counts into a string key like "1#0#0#...".
            // The '#' separator matters: without it, counts (1,12) and (11,2)
            // could collide as "112".
            StringBuilder sb = new StringBuilder();
            for (int n : count) sb.append(n).append('#');
            String key = sb.toString();
            // computeIfAbsent creates the group list on first sight, then appends
            groups.computeIfAbsent(key, k -> new ArrayList<>()).add(s);
        }
        // The map's values ARE the answer — one list per anagram group
        return new ArrayList<>(groups.values());
    }
    // Time O(n * L) for n words of average length L (each word scanned once).
    // Space O(n * L) to store every word plus its key.

    public static void main(String[] args) {
        List<List<String>> res =
            groupAnagrams(new String[]{"eat", "tea", "tan", "ate", "nat", "bat"});
        assert res.size() == 3;              // {eat,tea,ate} {tan,nat} {bat}
        int total = 0;
        for (List<String> g : res) total += g.size();
        assert total == 6;                   // every word placed exactly once
        assert groupAnagrams(new String[]{""}).size() == 1;
        assert groupAnagrams(new String[]{"a"}).get(0).get(0).equals("a");
        System.out.println("GroupAnagrams: OK");
    }
}
