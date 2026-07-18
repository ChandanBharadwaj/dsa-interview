import java.util.ArrayList;
import java.util.List;

/**
 * LeetCode 17 — Letter Combinations of a Phone Number. One decision per
 * digit; candidates = that key's letters. Mirrors pages/10-recursion-backtracking.html.
 */
public class LetterCombinations {

    private static final String[] KEYS = {
        "", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"
    };

    public static List<String> letterCombinations(String digits) {
        List<String> res = new ArrayList<>();
        if (digits.isEmpty()) return res;        // spec: empty input -> empty list
        build(digits, 0, new StringBuilder(), res);
        return res;
    }

    private static void build(String digits, int i, StringBuilder sb, List<String> res) {
        if (i == digits.length()) {              // one letter chosen per digit — done
            res.add(sb.toString());
            return;
        }
        // Candidates for this level = the letters on the current digit's key
        for (char c : KEYS[digits.charAt(i) - '0'].toCharArray()) {
            sb.append(c);                        // choose
            build(digits, i + 1, sb, res);       // explore the next digit
            sb.deleteCharAt(sb.length() - 1);    // un-choose
        }
    }

    public static void main(String[] args) {
        List<String> r = letterCombinations("23");
        assert r.size() == 9 && r.contains("ad") && r.contains("cf");
        assert letterCombinations("").isEmpty();
        assert letterCombinations("2").equals(List.of("a", "b", "c"));
        System.out.println("LetterCombinations: OK");
    }
}
