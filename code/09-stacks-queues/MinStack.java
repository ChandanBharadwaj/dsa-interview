import java.util.ArrayDeque;
import java.util.Deque;

/**
 * LeetCode 155 — Min Stack. Parallel min-stack recording "the min as of
 * each push"; popping both rewinds history. All ops O(1).
 * Mirrors pages/09-stacks-queues.html.
 */
public class MinStack {

    private final Deque<Integer> stack = new ArrayDeque<>();
    private final Deque<Integer> minStack = new ArrayDeque<>();

    public void push(int val) {
        stack.push(val);
        // The min after this push: the smaller of val and the previous min
        int min = minStack.isEmpty() ? val : Math.min(val, minStack.peek());
        minStack.push(min);
    }

    public void pop() {
        stack.pop();
        minStack.pop();          // discard that era's minimum along with the value
    }

    public int top() { return stack.peek(); }

    public int getMin() { return minStack.peek(); }   // min of everything still in the stack

    public static void main(String[] args) {
        MinStack ms = new MinStack();
        ms.push(-2); ms.push(0); ms.push(-3);
        assert ms.getMin() == -3;
        ms.pop();
        assert ms.top() == 0;
        assert ms.getMin() == -2;
        System.out.println("MinStack: OK");
    }
}
