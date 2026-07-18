/**
 * LeetCode 14 — Longest Common Prefix, vertical scan.
 * Mirrors pages/13-tries.html.
 */
public class LongestCommonPrefix {

    public static String longestCommonPrefix(String[] strs) {
        // Column by column: compare character i of EVERY string before i+1.
        for (int i = 0; i < strs[0].length(); i++) {
            char c = strs[0].charAt(i);
            for (int j = 1; j < strs.length; j++) {
                // Stop at the first string that ends here or disagrees
                if (i == strs[j].length() || strs[j].charAt(i) != c)
                    return strs[0].substring(0, i);
            }
        }
        return strs[0];                      // strs[0] itself prefixes everything
    }

    public static void main(String[] args) {
        assert longestCommonPrefix(new String[]{"flower", "flow", "flight"}).equals("fl");
        assert longestCommonPrefix(new String[]{"dog", "racecar", "car"}).equals("");
        assert longestCommonPrefix(new String[]{"solo"}).equals("solo");
        System.out.println("LongestCommonPrefix: OK");
    }
}
