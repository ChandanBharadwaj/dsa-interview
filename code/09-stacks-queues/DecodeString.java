import java.util.*;

public class DecodeString {

    // Two stacks — pending counts and pending prefixes. '[' saves the
    // context, ']' restores it and multiplies what was built inside.
    public static String decodeString(String s) {
        Deque<Integer> counts = new ArrayDeque<>();
        Deque<StringBuilder> prefixes = new ArrayDeque<>();
        StringBuilder curr = new StringBuilder();
        int num = 0;
        for (char c : s.toCharArray()) {
            if (c >= '0' && c <= '9') {
                num = num * 10 + (c - '0');       // multi-digit counts
            } else if (c == '[') {
                counts.push(num);                 // save the pending context
                prefixes.push(curr);
                num = 0;
                curr = new StringBuilder();       // start the inner segment fresh
            } else if (c == ']') {
                StringBuilder inner = curr;
                curr = prefixes.pop();            // restore the outer segment
                int k = counts.pop();
                for (int i = 0; i < k; i++)       // append k copies of the inner
                    curr.append(inner);
            } else {
                curr.append(c);
            }
        }
        return curr.toString();
    }

    public static void main(String[] args) {
        assert decodeString("3[a]2[bc]").equals("aaabcbc");
        assert decodeString("3[a2[c]]").equals("accaccacc");
        assert decodeString("2[abc]3[cd]ef").equals("abcabccdcdcdef");
        assert decodeString("abc").equals("abc");
        assert decodeString("10[a]").equals("aaaaaaaaaa");
        System.out.println("DecodeString: OK");
    }
}
