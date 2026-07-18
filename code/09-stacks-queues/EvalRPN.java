// LeetCode 150 — Evaluate Reverse Polish Notation
// RPN needs no parentheses: operands stack up, an operator consumes the top two.
// This is literally how the JVM's operand stack executes bytecode.
public class EvalRPN {

    public static int evalRPN(String[] tokens) {
        java.util.Deque<Integer> stack = new java.util.ArrayDeque<>();
        for (String t : tokens) {
            switch (t) {
                case "+": case "-": case "*": case "/": {
                    int b = stack.pop();       // top of stack = RIGHT operand (pushed most recently)
                    int a = stack.pop();       // next = LEFT operand — order matters for - and /
                    int r = switch (t) {
                        case "+" -> a + b;
                        case "-" -> a - b;     // a - b, NOT b - a: "3 4 -" means 3 - 4
                        case "*" -> a * b;
                        default  -> a / b;     // Java int division truncates toward zero, as LC 150 specifies
                    };
                    stack.push(r);             // the result becomes an operand for later operators
                    break;
                }
                default:
                    stack.push(Integer.parseInt(t));   // a number: park it until an operator claims it
            }
        }
        return stack.pop();                    // a valid RPN expression leaves exactly one value
    }

    public static void main(String[] args) {
        assert evalRPN(new String[]{"2","1","+","3","*"}) == 9;         // (2+1)*3
        assert evalRPN(new String[]{"4","13","5","/","+"}) == 6;        // 4 + 13/5
        assert evalRPN(new String[]{"10","6","9","3","+","-11","*","/","*","17","+","5","+"}) == 22;
        assert evalRPN(new String[]{"3","4","-"}) == -1;                // operand order check
        System.out.println("EvalRPN: OK");
    }
}
