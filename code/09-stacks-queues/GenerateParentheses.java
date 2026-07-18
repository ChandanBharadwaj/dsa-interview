import java.util.ArrayList;
import java.util.List;

/**
 * LeetCode 22 — Generate Parentheses. Backtracking that never builds an
 * invalid prefix (close may only follow a surplus of opens).
 * Mirrors pages/09-stacks-queues.html.
 */
public class GenerateParentheses {

    public static List<String> generateParenthesis(int n) {
        List<String> res = new ArrayList<>();
        build(res, new StringBuilder(), 0, 0, n);
        return res;
    }

    // open/close = how many of each we've placed so far.
    // Valid prefixes are exactly those where close never exceeds open.
    private static void build(List<String> res, StringBuilder sb, int open, int close, int n) {
        if (sb.length() == 2 * n) {            // used all n pairs — a complete valid string
            res.add(sb.toString());
            return;
        }
        if (open < n) {                        // can still open a new pair
            sb.append('(');
            build(res, sb, open + 1, close, n);
            sb.deleteCharAt(sb.length() - 1);  // un-choose (backtrack)
        }
        if (close < open) {                    // may close ONLY if something is open —
            sb.append(')');                    // this prune is Valid Parentheses' invariant
            build(res, sb, open, close + 1, n);
            sb.deleteCharAt(sb.length() - 1);
        }
    }

    public static void main(String[] args) {
        assert generateParenthesis(1).equals(List.of("()"));
        List<String> r3 = generateParenthesis(3);
        assert r3.size() == 5;                 // Catalan number C3
        assert r3.contains("((()))") && r3.contains("()()()");
        assert generateParenthesis(4).size() == 14;   // C4
        System.out.println("GenerateParentheses: OK");
    }
}
