public class ValidParenthesisString {

    // Track a RANGE of possible open-paren counts: '*' widens it both ways.
    // Valid iff the range can hit exactly 0 at the end.
    public static boolean checkValidString(String s) {
        int lo = 0;                 // min opens if '*'s help aggressively
        int hi = 0;                 // max opens if '*'s all become '('
        for (char c : s.toCharArray()) {
            if (c == '(') {
                lo++;
                hi++;
            } else if (c == ')') {
                lo--;
                hi--;
            } else {                // '*': ')' , '(' or empty
                lo--;               // as ')'
                hi++;               // as '('
            }
            if (hi < 0) return false;   // too many ')' even with all-'(' stars
            lo = Math.max(lo, 0);       // counts can't go negative for real
        }
        return lo == 0;             // zero opens is inside the feasible range
    }

    public static void main(String[] args) {
        assert checkValidString("()");
        assert checkValidString("(*)");
        assert checkValidString("(*))");
        assert !checkValidString(")(");
        assert !checkValidString("(((*)");
        assert checkValidString("");
        assert !checkValidString("*(");
        System.out.println("ValidParenthesisString: OK");
    }
}
