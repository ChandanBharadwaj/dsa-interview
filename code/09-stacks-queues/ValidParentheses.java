// LeetCode 20 — Valid Parentheses
// The canonical stack problem: the most recent unclosed opener must close first — that's LIFO.
public class ValidParentheses {

    public static boolean isValid(String s) {
        java.util.Deque<Character> stack = new java.util.ArrayDeque<>();  // ArrayDeque, never java.util.Stack
        for (char c : s.toCharArray()) {
            if (c == '(' || c == '[' || c == '{') {
                stack.push(c);                 // opener: remember it — it's now the most recent debt to repay
            } else {
                if (stack.isEmpty()) return false;      // a closer with nothing open — e.g. ")(" — invalid
                char open = stack.pop();                // the most recent unclosed opener MUST match this closer
                if (c == ')' && open != '(') return false;   // mismatched pair like "(]"
                if (c == ']' && open != '[') return false;
                if (c == '}' && open != '{') return false;
            }
        }
        return stack.isEmpty();                // leftovers like "((" mean unclosed openers — invalid
    }

    public static void main(String[] args) {
        assert isValid("()[]{}");
        assert !isValid("(]");
        assert !isValid("([)]");
        assert isValid("{[]}");
        assert !isValid("(");
        assert !isValid(")(");
        System.out.println("ValidParentheses: OK");
    }
}
