import java.util.*;

public class BasicCalculatorII {

    // Precedence via a stack of resolved terms: +/- push (signed), *//
    // apply immediately to the previous term. Sum at the end.
    public static int calculate(String s) {
        Deque<Integer> terms = new ArrayDeque<>();
        int num = 0;
        char pendingOp = '+';                    // op waiting to apply to `num`
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= '0' && c <= '9')
                num = num * 10 + (c - '0');
            // On an operator or the final char, resolve the pending op:
            if ((c != ' ' && (c < '0' || c > '9')) || i == s.length() - 1) {
                switch (pendingOp) {
                    case '+': terms.push(num); break;
                    case '-': terms.push(-num); break;
                    case '*': terms.push(terms.pop() * num); break;  // binds now
                    case '/': terms.push(terms.pop() / num); break;  // binds now
                }
                pendingOp = c;
                num = 0;
            }
        }
        int sum = 0;
        for (int t : terms) sum += t;            // only +/- remain: just add
        return sum;
    }

    public static void main(String[] args) {
        assert calculate("3+2*2") == 7;
        assert calculate(" 3/2 ") == 1;
        assert calculate(" 3+5 / 2 ") == 5;
        assert calculate("14-3/2") == 13;
        assert calculate("1*2-3/4+5*6-7*8+9/10") == -24;
        System.out.println("BasicCalculatorII: OK");
    }
}
