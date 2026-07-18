import java.util.ArrayList;
import java.util.List;

/**
 * LeetCode 131 — Palindrome Partitioning. Decision = where the next piece
 * ends; the palindrome test is the prune. Mirrors pages/10-recursion-backtracking.html.
 */
public class PalindromePartitioning {

    public static List<List<String>> partition(String s) {
        List<List<String>> res = new ArrayList<>();
        dfs(s, 0, new ArrayList<>(), res);
        return res;
    }

    private static void dfs(String s, int start, List<String> path, List<List<String>> res) {
        if (start == s.length()) {               // the whole string is consumed —
            res.add(new ArrayList<>(path));      // path holds one valid partition
            return;
        }
        for (int end = start + 1; end <= s.length(); end++) {
            String piece = s.substring(start, end);
            if (!isPalindrome(piece)) continue;  // prune: never extend an invalid piece
            path.add(piece);                     // choose this cut
            dfs(s, end, path, res);              // partition the rest
            path.remove(path.size() - 1);        // un-choose
        }
    }

    private static boolean isPalindrome(String p) {   // two pointers
        for (int l = 0, r = p.length() - 1; l < r; l++, r--)
            if (p.charAt(l) != p.charAt(r)) return false;
        return true;
    }

    public static void main(String[] args) {
        List<List<String>> r = partition("aab");
        assert r.size() == 2;
        assert r.contains(List.of("a", "a", "b")) && r.contains(List.of("aa", "b"));
        assert partition("a").equals(List.of(List.of("a")));
        System.out.println("PalindromePartitioning: OK");
    }
}
